import controller.LoginController;
import controller.OfficeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import repository.database.ChallengeDBRepository;
import repository.database.ChildDBRepository;
import repository.database.EnrollmentDBRepository;
import repository.database.OfficeResponsableDBRepository;
import repository.interfaces.ChallengeRepository;
import repository.interfaces.ChildRepository;
import repository.interfaces.EnrollmentRepository;
import repository.interfaces.OfficeResponsableRepository;
import service.OfficeService;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {

    private OfficeService officeService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        initializeService();
        initView(stage);
        stage.setWidth(1000);
        stage.setHeight(600);
        stage.setTitle("Children contest management system");
        stage.show();
    }

    private void initView(Stage stage) throws IOException {
        FXMLLoader loginLoader = new FXMLLoader();
        loginLoader.setLocation(getClass().getResource("views/login-view.fxml"));
        StackPane loginLayout = loginLoader.load();
        stage.setScene(new Scene(loginLayout));

        LoginController loginController = loginLoader.getController();
        loginController.setOfficeService(officeService);
    }

    private void initializeService() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("bd.config"));
        }
        catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }
        OfficeResponsableRepository officeResponsableRepository = new OfficeResponsableDBRepository(properties);
        ChallengeRepository challengeRepository = new ChallengeDBRepository(properties);
        ChildRepository childRepository = new ChildDBRepository(properties);
        EnrollmentRepository enrollmentRepository = new EnrollmentDBRepository(properties);
        officeService = new OfficeService(officeResponsableRepository, challengeRepository, childRepository, enrollmentRepository);
    }
}