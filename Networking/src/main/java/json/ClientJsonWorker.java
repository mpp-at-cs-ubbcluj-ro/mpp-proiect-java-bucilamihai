package json;

import com.google.gson.Gson;
import domain.Challenge;
import domain.Child;
import domain.OfficeResponsable;
import dto.ChallengeDTO;
import dto.ChildDTO;
import dto.DTOUtils;
import dto.OfficeResponsableDTO;
import services.Observer;
import services.Service;
import services.ServiceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ClientJsonWorker implements Runnable, Observer {
    private Service server;
    private Socket connection;

    private BufferedReader input;
    private PrintWriter output;
    private Gson gsonFormatter;
    private volatile boolean connected;

    public ClientJsonWorker(Service server, Socket connection) {
        this.server = server;
        this.connection = connection;
        gsonFormatter = new Gson();
        try {
            output = new PrintWriter(connection.getOutputStream());
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (connected) {
            try {
                String requestLine = input.readLine();
                Request request = gsonFormatter.fromJson(requestLine, Request.class);
                Response response = handleRequest(request);
                if (response != null) {
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error " + e);
        }
    }

    private static Response okResponse = JsonProtocolUtils.createOkResponse();

    private Response handleRequest(Request request) {
        Response response = null;
        if (request.getType() == RequestType.LOGIN) {
            System.out.println("Login request ..." + request.getType());
            OfficeResponsableDTO officeResponsableDTO = request.getOfficeResponsable();
            OfficeResponsable officeResponsable = DTOUtils.getFromDTO(officeResponsableDTO);
            try {
                server.login(officeResponsable, this);
                return okResponse;
            } catch (ServiceException e) {
                connected = false;
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.GET_ALL_CHALLENGES) {
            System.out.println("Get all challenges request ...");
            OfficeResponsableDTO officeDTO = request.getOfficeResponsable();
            OfficeResponsable loggedOfficeResponsable = DTOUtils.getFromDTO(officeDTO);

            try {
                Collection<Challenge> challenges = server.getAllChallenges(loggedOfficeResponsable);
                Challenge[] challengesArray = challenges.toArray(new Challenge[0]);
                response = JsonProtocolUtils.createAllChallengesResponse(challengesArray);
            } catch (ServiceException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if(request.getType() == RequestType.GET_CHILDREN_BY_CHALLENGE_NAME_AND_GROUP_AGE) {
            System.out.println("Get children by challenge name and group age request ..." + request.getType());
            ChallengeDTO challengeDTO = request.getChallenge();
            Challenge challenge = DTOUtils.getFromDTO(challengeDTO);
            try {
                Collection<Child> children = server.getChildrenByChallengeNameAndGroupAge(challenge.getName(), challenge.getGroupAge());
                Child[] childrenArray = children.toArray(new Child[0]);
                response = JsonProtocolUtils.createAllChildrenByChallengeNameAndGroupAgeResponse(childrenArray);
            } catch (ServiceException e) {
                throw new RuntimeException(e);
            }
        }
        if(request.getType() == RequestType.ENROLL_CHILD) {
            System.out.println("Enroll child request ..." + request.getType());
            try {
                ChildDTO childDTO = request.getChild();
                Child child = DTOUtils.getFromDTO(childDTO);
                String challengeName = request.getChallengeName();
                server.enrollChild(child.getCnp(), child.getName(), child.getAge(), challengeName);
                return JsonProtocolUtils.createUpdateChallenges();
            }
            catch (ServiceException e) {
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        return response;
    }

    private void sendResponse(Response response) throws IOException {
        String responseLine = gsonFormatter.toJson(response);
        System.out.println("sending response " + responseLine);
        synchronized (output) {
            output.println(responseLine);
            output.flush();
        }
    }

    public void updateEnrolledChildren() {

    }
}
