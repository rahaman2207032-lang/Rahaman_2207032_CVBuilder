package com.example.cv_builder;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GenerateController {

    @FXML private VBox Edge;
    @FXML private VBox Top;
    @FXML private VBox Bottom;

    public void initialize() {

        Top.setStyle("-fx-background-color: LightGrey;" +"-fx-padding: 20 10 10 10;");
        Top.setAlignment(Pos.CENTER);

        Edge.setStyle(
                "-fx-background-color: #808080;" +
                        "-fx-padding: 20;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;"
        );
        Edge.setAlignment(Pos.TOP_CENTER);
        Edge.setSpacing(20);

        Bottom.setStyle("-fx-padding: 20;");
        Bottom.setSpacing(30);
        Bottom.setAlignment(Pos.TOP_CENTER);
    }


    public void loadData(java.util.List<CV_INFORMATION> list) {

        for (CV_INFORMATION c : list) {

            Label nameLabel = new Label(c.getName());
            nameLabel.setStyle(
                    "-fx-font-size: 32px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #1A1A1A;"
            );
            Top.getChildren().add(nameLabel);

            VBox l = new VBox(15);
            l.setAlignment(Pos.CENTER_LEFT);

            l.getChildren().addAll(
                    createHeading("E-mail:"),
                    createContent(c.getEmail()),
                    createHeading("Phone No:"),
                    createContent(c.getPhone()),
                    createHeading("Address:"),
                    createContent(c.getAddress())
            );

            Edge.getChildren().add(l);

            VBox r = new VBox(20);
            r.setAlignment(Pos.TOP_LEFT);

            r.getChildren().addAll(
                    createSection("Educational Qualifications", c.getEd_Qualification()),
                    createSection("Skills", c.getSkills()),
                    createSection("Work Experiences", c.getWork_experience()),
                    createSection("Projects", c.getProjects())
            );

            Bottom.getChildren().add(r);
        }
    }



    private Label createHeading(String text) {
        Label lbl = new Label(text);
        lbl.setStyle(
                "-fx-font-size: 15px;" + "-fx-font-weight: bold;" + "-fx-text-fill: white;");
        return lbl;
    }

    private Label createContent(String text) {
        Label lbl = new Label(text);
        lbl.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-text-fill: white;"
        );
        lbl.setWrapText(true);
        return lbl;
    }

    private VBox createSection(String title, String info) {

        VBox box = new VBox(5);
        box.setPadding(new Insets(12));
        box.setStyle(
                "-fx-background-color: #F5F5F5;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-color: #CCCCCC;" +
                        "-fx-border-radius: 8;" +
                        "-fx-border-width: 1;"
        );

        Label titleLabel = new Label(title);
        titleLabel.setStyle(
                "-fx-font-size: 17px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #2E2E2E;"
        );

        Label contentLabel = new Label(info);
        contentLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333333;");
        contentLabel.setWrapText(true);

        box.getChildren().addAll(titleLabel, contentLabel);
        return box;
    }
}
