package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import service.OfficeService;

import java.io.IOException;

public class LoginController {
    private OfficeService officeService;

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button login;

    public void setOfficeService(OfficeService officeService) {
        this.officeService = officeService;
    }

    public void handleLogin() throws IOException {
        String username = this.username.getText();
        String password = this.password.getText();
        if(officeService.existsOfficeResponsable(username, password)) {
            initOfficeView();
            this.username.clear();
            this.password.clear();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Failed login!");
            alert.setContentText("You failed to login!");
            alert.showAndWait();
        }
    }

    private void initOfficeView() throws IOException {
        FXMLLoader officeLoader = new FXMLLoader();
        officeLoader.setLocation(getClass().getResource("/views/office-view.fxml"));
        StackPane officeLayout = officeLoader.load();
        Stage officeStage = new Stage();
        officeStage.setScene(new Scene(officeLayout));

        OfficeController officeController = officeLoader.getController();
        officeController.setOfficeService(officeService);

        officeStage.setWidth(1000);
        officeStage.setHeight(600);
        officeStage.show();
    }
}
