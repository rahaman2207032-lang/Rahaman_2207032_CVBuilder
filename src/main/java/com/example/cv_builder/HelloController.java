package com.example.cv_builder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;


import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;
    private Stage stage;
    private Scene scene;

    @FXML
   public void ClickButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("CV-Builder");
        stage.setScene(scene);
        stage.show();

    }
}
