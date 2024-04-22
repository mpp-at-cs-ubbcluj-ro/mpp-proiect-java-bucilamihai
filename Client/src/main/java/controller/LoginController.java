package controller;

import domain.OfficeResponsable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import services.Service;
import services.ServiceException;

import java.io.IOException;

public class LoginController {
    private Service officeService;
    private OfficeController officeController;
    private OfficeResponsable loggedOfficeResponsable;


    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button login;

    public void setOfficeService(Service officeService) {
        this.officeService = officeService;
    }

    public void handleLogin() throws IOException {
        String username = this.username.getText();
        String password = this.password.getText();
        loggedOfficeResponsable = new OfficeResponsable(username, password);
        try {
            officeService.login(loggedOfficeResponsable, officeController);
            this.username.clear();
            this.password.clear();
            initOfficeView();
        }
        catch (ServiceException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Failed login!");
            alert.setContentText("You failed to login!");
            alert.showAndWait();
        }
    }

    private void initOfficeView() throws IOException, ServiceException {
        FXMLLoader officeLoader = new FXMLLoader();
        officeLoader.setLocation(getClass().getResource("/views/office-view.fxml"));
        StackPane officeLayout = officeLoader.load();
        Stage officeStage = new Stage();
        officeStage.setScene(new Scene(officeLayout));

        officeController = officeLoader.getController();
        officeController.setLoggedOfficeResponsable(loggedOfficeResponsable);
        officeController.setOfficeService(officeService);

        officeStage.setWidth(1000);
        officeStage.setHeight(600);
        officeStage.show();
    }
}
