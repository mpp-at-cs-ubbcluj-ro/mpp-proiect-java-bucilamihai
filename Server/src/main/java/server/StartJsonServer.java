package server;

import repository.database.ChallengeDBRepository;
import repository.database.ChildDBRepository;
import repository.database.EnrollmentDBRepository;
import repository.database.OfficeResponsableDBRepository;
import repository.interfaces.ChallengeRepository;
import repository.interfaces.ChildRepository;
import repository.interfaces.EnrollmentRepository;
import repository.interfaces.OfficeResponsableRepository;
import services.Service;
import utils.AbstractServer;
import utils.JsonConcurrentServer;
import utils.ServerException;

import java.io.IOException;
import java.util.Properties;

public class StartJsonServer {
    private static int defaultPort = 55555;

    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(StartRpcServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            properties.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find server.properties "+e);
            return;
        }
        OfficeResponsableRepository officeResponsableRepository = new OfficeResponsableDBRepository(properties);
        ChallengeRepository challengeRepository = new ChallengeDBRepository(properties);
        ChildRepository childRepository = new ChildDBRepository(properties);
        EnrollmentRepository enrollmentRepository = new EnrollmentDBRepository(properties);
        Service officeService = new OfficeService(
                officeResponsableRepository, challengeRepository, childRepository, enrollmentRepository
        );
        int chatServerPort = defaultPort;
        try {
            chatServerPort = Integer.parseInt(properties.getProperty("server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong Port Number" + nef.getMessage());
            System.err.println("Using default port " + defaultPort);
        }
        System.out.println("Starting server on port: " + chatServerPort);
        AbstractServer server = new JsonConcurrentServer(chatServerPort, officeService);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }
    }

}
