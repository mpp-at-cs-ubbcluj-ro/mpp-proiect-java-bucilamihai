package json;
import com.google.gson.Gson;
import domain.Challenge;
import domain.Child;
import domain.OfficeResponsable;
import dto.ChallengeDTO;
import dto.ChildDTO;
import dto.DTOUtils;
import services.Observer;
import services.Service;
import services.ServiceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServiceJsonProxy implements Service {
    private String host;
    private int port;

    private Observer client;

    private BufferedReader input;
    private PrintWriter output;
    private Gson gsonFormatter;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    public ServiceJsonProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<Response>();
    }

    @Override
    public void login(OfficeResponsable officeResponsable, Observer client) throws ServiceException {
        initializeConnection();

        Request request = JsonProtocolUtils.createLoginRequest(officeResponsable);
        sendRequest(request);
        Response response = readResponse();
        if(response.getType() == ResponseType.OK) {
            this.client = client;
            return;
        }
        if(response.getType() == ResponseType.ERROR) {
            String error = response.getErrorMessage();
            closeConnection();
            throw new ServiceException(error);
        }
    }

    @Override
    public Collection<Challenge> getAllChallenges(OfficeResponsable loggedOfficeResponsable) throws ServiceException {
        initializeConnection();

        Request request = JsonProtocolUtils.createAllChallengesRequest(loggedOfficeResponsable);
        sendRequest(request);
        Response response = readResponse();
        if(response.getType() == ResponseType.ERROR) {
            String error = response.getErrorMessage();
            throw new ServiceException(error);
        }
        ChallengeDTO[] challengeDTO = response.getChallenges();
        Challenge[] challenges = DTOUtils.getFromDTO(challengeDTO);
        return List.of(challenges);
    }

    @Override
    public Collection<Child> getChildrenByChallengeNameAndGroupAge(String challengeName, String groupAge) throws ServiceException {
        Challenge challenge = new Challenge(challengeName, groupAge, 0);
        Request request = JsonProtocolUtils.createGetChildrenByChallengeNameAndGroupAgeRequest(challenge);
        sendRequest(request);
        Response response = readResponse();
        if(response.getType() == ResponseType.ERROR) {
            String error = response.getErrorMessage();
            throw new ServiceException(error);
        }
        ChildDTO[] childrenDTO = response.getChildren();
        Child[] children = DTOUtils.getFromDTO(childrenDTO);
        return List.of(children);
    }

    @Override
    public void enrollChild(Long cnp, String name, int age, String challengeName) throws ServiceException {
        Child child = new Child(cnp, name, age);
        Request request = JsonProtocolUtils.createEnrollChildRequest(child, challengeName);
        sendRequest(request);
        Response response = JsonProtocolUtils.createUpdateChallenges();
        if(response.getType() == ResponseType.ERROR) {
            String error = response.getErrorMessage();
            throw new ServiceException(error);
        }
    }

    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRequest(Request request) throws ServiceException {
        String reqLine = gsonFormatter.toJson(request);
        try {
            output.println(reqLine);
            output.flush();
        } catch (Exception e) {
            throw new ServiceException("Error sending object "+e);
        }
    }

    private Response readResponse() {
       Response response = null;
        try{
            response = qresponses.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void initializeConnection() {
        try {
            gsonFormatter = new Gson();
            connection = new Socket(host,port);
            output = new PrintWriter(connection.getOutputStream());
            output.flush();
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }


    private void handleUpdate(Response response){
        if(response.getType() == ResponseType.UPDATE_CHALLENGES) {
            System.out.println("(handleUpdate) - update challenges");
            client.updateEnrolledChildren();
        }
    }

    private boolean isUpdate(Response response){
        return response.getType() == ResponseType.UPDATE_CHALLENGES;
    }

    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    String responseLine = input.readLine();
                    System.out.println("response received " + responseLine);
                    Response response = gsonFormatter.fromJson(responseLine, Response.class);
                    if (isUpdate(response)){
                        handleUpdate(response);
                    }
                    else
                    {
                        try {
                            qresponses.put(response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                catch (IOException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}
