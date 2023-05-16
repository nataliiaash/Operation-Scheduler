package com.example.operationschedularproject;

import com.example.operationschedularproject.LinkedList.LinkedList;
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

public class CreateAccController extends Menu {
    @FXML
    TextField fullName, address, position, username, password;
    @FXML
    Label message;
    Parent root;
    Stage stage;
    Scene scene;

    public void onCreate(ActionEvent e) throws IOException {
        String inName = fullName.getText();
        String inAddress = address.getText();
        String inPosition = position.getText();
        String inUsername = position.getText();
        String inPass = password.getText();
        if(!isUsernameValid(inUsername)){
            message.setText("Username invalid!");
        } else if(!isPasswordValid(inPass)){
            message.setText("Please enter a strong password");
        } else {
            HealthProfessional newUser = new HealthProfessional(inName, inPosition, inUsername, inPass);
            newUser.setLocation(inAddress);
            dataBase.add(newUser);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            String css = this.getClass().getResource("style.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        }
    }
    private boolean isUsernameValid(String username){
        for (int i = 0; i < username.length(); i++) {

            if ((!Character.isLetterOrDigit(username.charAt(i)))) {
                return false;
            }
        }
        return true;
    }
    private boolean isPasswordValid(String password){
        int UpperCaseLetter = 0;
        int SpecialChar = 0;
        int Digit = 0;
        int LowerCaseLetter = 0;
        if (password.length() < 8 || password.contains(" ")) return false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i)))
                UpperCaseLetter++;
            else if (Character.isLowerCase(password.charAt(i)))
                LowerCaseLetter++;
            else if (Character.isDigit(password.charAt(i)))
                Digit++;
            else if (!Character.isLetterOrDigit(password.charAt(i)) && !Character.isWhitespace(password.charAt(i)))
                SpecialChar++;
            if (UpperCaseLetter>0 && LowerCaseLetter>0 && SpecialChar>0 && Digit>0)
                return true;
        }
        return false;


    }
}

