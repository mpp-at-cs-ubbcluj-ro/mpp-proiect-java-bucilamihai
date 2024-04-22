package controller;

import domain.Challenge;
import domain.Child;
import domain.OfficeResponsable;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import services.Observer;
import services.Service;
import services.ServiceException;

import java.util.Collection;

public class OfficeController implements Observer {
    private Service officeService;
    private OfficeResponsable loggedOfficeResponsable;

    ObservableList<Challenge> challengesModel = FXCollections.observableArrayList();
    ObservableList<Child> childrenModel = FXCollections.observableArrayList();

    @FXML
    private TableView<Challenge> tableViewChallenge;

    @FXML
    private TableColumn<Challenge, Integer> challengeNameColumn;

    @FXML
    private TableColumn<Challenge, String> groupAgeColumn;

    @FXML
    private TableColumn<Challenge, Integer> numberOfParticipantsColumn;

    @FXML
    private TextField challengeName;
    @FXML
    private ComboBox<String> challengeGroupAge;
    @FXML
    private Button searchEnrolledChildren;

    @FXML
    private TableView<Child> tableViewChild;
    @FXML
    private TableColumn<Child, Integer> childNameColumn;
    @FXML
    private TableColumn<Child, Integer> ageColumn;


    @FXML
    private TextField childCnp;
    @FXML
    private TextField childName;
    @FXML
    private Spinner<Integer> childAge;
    @FXML
    private ComboBox<String> challengeNames;

    public void setOfficeService(Service officeService) throws ServiceException {
        this.officeService = officeService;
        initModel();
    }

    public void setLoggedOfficeResponsable(OfficeResponsable officeResponsable) {
        this.loggedOfficeResponsable = officeResponsable;
    }

    @FXML
    public void initialize() {
        challengeNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        groupAgeColumn.setCellValueFactory(new PropertyValueFactory<>("groupAge"));
        numberOfParticipantsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfParticipants"));
        tableViewChallenge.setItems(challengesModel);

        // TODO - to avoid name column repetition, create a listview witH all name challenges,
        //  and when selects a listview item, set items to tableview

        childNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        tableViewChild.setItems(childrenModel);

        ObservableList<String> groupAgeList = FXCollections.observableArrayList();
        groupAgeList.add("6-8");
        groupAgeList.add("9-11");
        groupAgeList.add("12-15");
        challengeGroupAge.setItems(groupAgeList);

        ObservableList<String> challengeNameList = FXCollections.observableArrayList();
        challengeNameList.add("drawing");
        challengeNameList.add("treasure hunt");
        challengeNameList.add("poetry");
        challengeNames.setItems(challengeNameList);
    }

    private void initModel() throws ServiceException {
        this.challengesModel.setAll(officeService.getAllChallenges(loggedOfficeResponsable));
    }

    public void handleEnrolledChildrenSearch() throws ServiceException {
        String clgName = challengeName.getText();
        String clgGroupAge = challengeGroupAge.getValue();
        childrenModel.setAll(officeService.getChildrenByChallengeNameAndGroupAge(clgName, clgGroupAge));
    }

    public void handleEnrollChild() throws ServiceException {
        Long cnp = Long.valueOf(childCnp.getText());
        String name = childName.getText();
        int age = childAge.getValue();
        String challengeName = challengeNames.getValue();
        officeService.enrollChild(cnp, name, age, challengeName);
        //initModel();
    }

    public void updateEnrolledChildren() {
        Platform.runLater(() -> {
            try {
                initModel();
            } catch (ServiceException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
