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

    }

    public void SwitchScene3 (ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene3.fxml"));
        Parent root = (Parent) loader.load();

        GenerateController controller = (GenerateController) loader.getController();

        controller.loadData(CV_INFORMATION);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Your CV");
        stage.setScene(scene);
        stage.show();

    }


}
