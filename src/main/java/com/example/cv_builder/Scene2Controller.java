package com.example.cv_builder;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Scene2Controller {
    private Stage stage;
    private Scene scene;
   @FXML
   TextField Name;
    @FXML
    TextField Email;
    @FXML
    TextField Phone;
    @FXML
    TextField Address;
    @FXML
    TextField Ed_Qualification;
    @FXML
    TextField Skills;
    @FXML
    TextField Work_experience;
    @FXML
    TextField Projects;

    private ObservableList<CV_INFORMATION> CV_INFORMATION = FXCollections.observableArrayList();
    @FXML
    public void Add() {
        CV_INFORMATION.add(new CV_INFORMATION(Name.getText(),Email.getText(), Phone.getText(), Address.getText(),Ed_Qualification.getText(),Skills.getText(),Work_experience.getText(),Projects.getText()));
    }

    public void SwitchScene3 (ActionEvent event) throws IOException {
        Add();
        if(isEmpty(Name)) return;
        if(isEmpty(Email)) return;
        if(isEmpty(Phone)) return;
        if(isEmpty(Address)) return;
        if(isEmpty(Skills)) return;
        if(isEmpty(Work_experience)) return;
        if(isEmpty(Projects)) return;
        if(isEmpty(Ed_Qualification)) return;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene3.fxml"));
        Parent root = (Parent) loader.load();

        GenerateController controller = loader.getController();

        controller.loadData(CV_INFORMATION);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Your CV");
        stage.setScene(scene);
        stage.show();

    }
    private boolean isEmpty(TextField tf){
        if(tf.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Enter  all the required Informations");
            alert.showAndWait();
            return true;
        }
        return false;
}


}
