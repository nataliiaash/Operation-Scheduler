package com.example.operationschedularproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainScreenController{
@FXML
    Label name;
    HealthProfessional currentUser = LogInController.user;

    @FXML
    public void initialize(){
    name.setText("Hi!, " + currentUser.getName());
  }

}


