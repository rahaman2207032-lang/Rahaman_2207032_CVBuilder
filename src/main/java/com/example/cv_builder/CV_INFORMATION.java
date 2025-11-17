package com.example.cv_builder;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CV_INFORMATION {
    private final StringProperty Name;
    private final StringProperty Email;

    private final StringProperty Phone;

    private final StringProperty Address;

    private final StringProperty Ed_Qualification;

    private final StringProperty Skills;

    private final StringProperty Work_experience;

    private final StringProperty Projects;

    public CV_INFORMATION(String name, String email, String phone, String address, String edQualification, String skills, String workExperience, String projects) {
       this.Name = new SimpleStringProperty(name);
       this.Email = new SimpleStringProperty(email);
       this.Phone = new SimpleStringProperty(phone);
        this.Address = new SimpleStringProperty(address);
        this.Ed_Qualification = new SimpleStringProperty(edQualification);
        this.Skills = new SimpleStringProperty(skills);
        this.Work_experience = new SimpleStringProperty(workExperience);
        this.Projects = new SimpleStringProperty(projects);
    }

    public String getName() {
        return Name.get();
    }
    public String getEmail() {
        return Email.get();
    }
    public String getPhone() {
        return Phone.get();
    }
    public String Address() {
        return Address.get();
    }
    public String Ed_Qualification() {
        return Ed_Qualification.get();
    }
    public String getSkills() {
        return Skills.get();
    }
    public String getWork_experience() {
        return Work_experience.get();
    }
    public String getProjects() {
        return Projects.get();
    }

    public StringProperty nameProperty() {
        return Name;
    }

    public StringProperty EmailProperty() { return Email}

    public StringProperty PhoneProperty() {
        return Phone;
    }

    public StringProperty AddressProperty() {
        return Address;
    }

    public StringProperty Ed_QualificationsProperty() {
        return Ed_Qualification;
    }

    public StringProperty SkillsProperty() {
        return Skills;
    }

    public StringProperty Work_experienceProperty() {
        return Work_experience;
    }

    public StringProperty ProjectsProperty() {
        return Projects;
    }
}
