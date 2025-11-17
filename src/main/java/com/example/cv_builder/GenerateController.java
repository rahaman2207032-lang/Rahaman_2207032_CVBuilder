package com.example.cv_builder;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class GenerateController {
    @FXML

    private VBox root;
    @FXML
    private VBox Edge;
    @FXML
    private VBox Top;
    @FXML
    private VBox Bottom;


    public void loadData(java.util.List<CV_INFORMATION> list) {
        for (CV_INFORMATION c : list) {
            Top.getChildren().add(new Label(c.getName()));
            VBox l = new VBox(3);
            l.getChildren().addAll(new Label("E-mail: \n" + c.getEmail()),
                    new Label("Phone No: \n" + c.getEmail()),
                    new Label("Address: \n" + c.getAddress()));
            Edge.getChildren().add(l);

            VBox r = new VBox(3);
            r.getChildren().addAll(new Label("Educational Qualifications: \n" + c.getSkills()),
                    new Label("Skills: \n" + c.getSkills()),
                    new Label("Work Experiences: \n" + c.getWork_experience()),
                    new Label("Projects: \n" + c.getProjects()));
            Bottom.getChildren().add(r);
        }
    }
}
