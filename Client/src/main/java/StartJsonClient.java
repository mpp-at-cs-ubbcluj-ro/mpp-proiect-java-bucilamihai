import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import json.ServiceJsonProxy;
import services.Service;

import java.io.IOException;
import java.util.Properties;

public class StartJsonClient extends Application {
    private static int defaultPort = 55555;
    private static String defaultServer = "localhost";


    public void start(Stage stage) throws Exception {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartJsonClient.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find client.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("server.host", defaultServer);
        int serverPort = defaultPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        Service server = new ServiceJsonProxy(serverIP, serverPort);

        FXMLLoader loginLoader = new FXMLLoader();
        loginLoader.setLocation(getClass().getResource("views/login-view.fxml"));
        StackPane loginLayout = loginLoader.load();
        stage.setScene(new Scene(loginLayout));

        LoginController loginController = loginLoader.getController();
        loginController.setOfficeService(server);
//        ctrl.setServer(server);

//        FXMLLoader cloader = new FXMLLoader(
//                getClass().getClassLoader().getResource("ChatW.fxml"));
//        Parent croot=cloader.load();
//
//
//        ChatController chatCtrl =
//                cloader.<ChatController>getController();
//        chatCtrl.setServer(server);
//
//        ctrl.setChatController(chatCtrl);
//        ctrl.setParent(croot);

        stage.setWidth(1000);
        stage.setHeight(600);
        stage.setTitle("Children contest management system");
        stage.show();
    }


}
