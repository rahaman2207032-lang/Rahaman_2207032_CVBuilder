package com.example.cv_builder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class Scene4Controller {


    @FXML private TextArea allCVsArea;


    @FXML private TextField fetchIdField;
    @FXML private TextArea fetchResultArea;


    @FXML private TextField updateIdField;
    @FXML private TextField updateNameField;
    @FXML private TextField updateEmailField;
    @FXML private TextField updatePhoneField;
    @FXML private TextField updateAddressField;
    @FXML private TextField updateEducationField;
    @FXML private TextField updateSkillsField;
    @FXML private TextField updateExperienceField;
    @FXML private TextField updateProjectsField;

    // Delete Section
    @FXML private TextField deleteIdField;
    @FXML private Label deleteResultLabel;

    private Stage stage;
    private Scene scene;


    @FXML
    public void handleFetchAll() {
        try {
            List<CV_INFORMATION> cvList = DB.getAllCVs();

            if (cvList.isEmpty()) {
                allCVsArea.setText("❌ No CVs found in database.");
                return;
            }

            StringBuilder result = new StringBuilder();
            result.append("========================================\n");
            result.append("       ALL CVs IN DATABASE\n");
            result.append("========================================\n\n");

            Connection conn = DB.getConnection();
            String query = "SELECT id, name, email, phone FROM users ORDER BY id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            int count = 0;
            while (rs.next()) {
                count++;
                result.append(String.format("ID: %d\n", rs.getInt("id")));
                result.append(String.format("Name: %s\n", rs.getString("name")));
                result.append(String.format("Email: %s\n", rs.getString("email")));
                result.append(String.format("Phone: %s\n", rs.getString("phone")));
                result.append("----------------------------------------\n");
            }

            result.append("\nTotal CVs: ").append(count);
            allCVsArea.setText(result.toString());

            showSuccess("Fetched " + count + " CVs successfully!");

        } catch (Exception e) {
            allCVsArea.setText("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    public void handleFetchById() {
        String idText = fetchIdField.getText().trim();

        if (idText.isEmpty()) {
            showError("Please enter a CV ID");
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            CV_INFORMATION cv = getCVById(id);

            if (cv == null) {
                fetchResultArea.setText("❌ No CV found with ID: " + id);
                showError("CV not found!");
                return;
            }

            StringBuilder result = new StringBuilder();
            result.append("========================================\n");
            result.append("       CV DETAILS (ID: ").append(id).append(")\n");
            result.append("========================================\n\n");
            result.append("Name:           ").append(cv.getName()).append("\n");
            result.append("Email:          ").append(cv.getEmail()).append("\n");
            result.append("Phone:          ").append(cv.getPhone()).append("\n");
            result.append("Address:        ").append(cv.getAddress()).append("\n");
            result.append("Education:      ").append(cv.getEd_Qualification()).append("\n");
            result.append("Skills:         ").append(cv.getSkills()).append("\n");
            result.append("Experience:     ").append(cv.getWork_experience()).append("\n");
            result.append("Projects:       ").append(cv.getProjects()).append("\n");
            result.append("========================================\n");

            fetchResultArea.setText(result.toString());
            showSuccess("CV fetched successfully!");

        } catch (NumberFormatException e) {
            showError("Invalid ID format. Please enter a number.");
        } catch (Exception e) {
            fetchResultArea.setText("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    public void handleLoadForUpdate() {
        String idText = updateIdField.getText().trim();

        if (idText.isEmpty()) {
            showError("Please enter a CV ID to load");
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            CV_INFORMATION cv = getCVById(id);

            if (cv == null) {
                showError("No CV found with ID: " + id);
                return;
            }

            updateNameField.setText(cv.getName());
            updateEmailField.setText(cv.getEmail());
            updatePhoneField.setText(cv.getPhone());
            updateAddressField.setText(cv.getAddress());
            updateEducationField.setText(cv.getEd_Qualification());
            updateSkillsField.setText(cv.getSkills());
            updateExperienceField.setText(cv.getWork_experience());
            updateProjectsField.setText(cv.getProjects());

            showSuccess("CV data loaded! Modify fields and click 'Update CV'");

        } catch (NumberFormatException e) {
            showError("Invalid ID format");
        } catch (Exception e) {
            showError("Error loading CV: " + e.getMessage());
        }
    }

    @FXML
    public void handleUpdate() {
        String idText = updateIdField.getText().trim();

        if (idText.isEmpty()) {
            showError("Please enter a CV ID");
            return;
        }

        if (updateNameField.getText().trim().isEmpty()) {
            showError("Name cannot be empty");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Update");
        confirmAlert.setHeaderText("Update CV with ID: " + idText);
        confirmAlert.setContentText("Are you sure you want to update this CV?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            return;
        }

        try {
            int id = Integer.parseInt(idText);


            CV_INFORMATION updatedCV = new CV_INFORMATION(
                    updateNameField.getText().trim(),
                    updateEmailField.getText().trim(),
                    updatePhoneField.getText().trim(),
                    updateAddressField.getText().trim(),
                    updateEducationField.getText().trim(),
                    updateSkillsField.getText().trim(),
                    updateExperienceField.getText().trim(),
                    updateProjectsField.getText().trim()
            );

            boolean success = DB.updateCV(id, updatedCV);

            if (success) {
                showSuccess("✅ CV updated successfully!");
                clearUpdateFields();
            } else {
                showError("Failed to update CV. ID might not exist.");
            }

        } catch (NumberFormatException e) {
            showError("Invalid ID format");
        } catch (Exception e) {
            showError("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    public void handleDelete() {
        String idText = deleteIdField.getText().trim();

        if (idText.isEmpty()) {
            showError("Please enter a CV ID to delete");
            return;
        }

        try {
            int id = Integer.parseInt(idText);


            CV_INFORMATION cv = getCVById(id);
            if (cv == null) {
                deleteResultLabel.setText("❌ No CV found with ID: " + id);
                deleteResultLabel.setStyle("-fx-text-fill: red;");
                return;
            }


            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Delete");
            confirmAlert.setHeaderText("Delete CV: " + cv.getName());
            confirmAlert.setContentText("ID: " + id + "\nEmail: " + cv.getEmail() +
                    "\n\nThis action cannot be undone!");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isEmpty() || result.get() != ButtonType.OK) {
                return;
            }


            boolean success = DB.deleteCV(id);

            if (success) {
                deleteResultLabel.setText("✅ CV deleted successfully! (ID: " + id + ")");
                deleteResultLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                deleteIdField.clear();
                showSuccess("CV deleted!");
            } else {
                deleteResultLabel.setText("❌ Failed to delete CV");
                deleteResultLabel.setStyle("-fx-text-fill: red;");
            }

        } catch (NumberFormatException e) {
            showError("Invalid ID format. Please enter a number.");
        } catch (Exception e) {
            deleteResultLabel.setText("❌ Error: " + e.getMessage());
            deleteResultLabel.setStyle("-fx-text-fill: red;");
            e.printStackTrace();
        }
    }


    @FXML
    public void handleBackToHome(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private CV_INFORMATION getCVById(int id) throws SQLException {
        Connection conn = DB.getConnection();
        String query = "SELECT * FROM users WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            return new CV_INFORMATION(
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("education"),
                    rs.getString("skills"),
                    rs.getString("work_experience"),
                    rs.getString("projects")
            );
        }
        return null;
    }

    private void clearUpdateFields() {
        updateIdField.clear();
        updateNameField.clear();
        updateEmailField.clear();
        updatePhoneField.clear();
        updateAddressField.clear();
        updateEducationField.clear();
        updateSkillsField.clear();
        updateExperienceField.clear();
        updateProjectsField.clear();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}