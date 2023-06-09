package com.example.operationschedularproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInController extends Menu {
    @FXML
    private Label message;
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    Parent root;
    Scene scene;
    Stage stage;
    static HealthProfessional user;

    /**
     * on login button
     * @param e
     * @throws IOException
     */
    @FXML
    public void onLogin(ActionEvent e) throws IOException {
        String inUserName = username.getText();
        String inPass = password.getText();
        //validating username and password and displaying message
        if(inUserName.length() == 0 ){
            message.setText("Enter a valid Username");
        }
        if (inPass.length() == 0 ){
            message.setText("Enter a valid Password");
        }
        if (!isLoginValid(inUserName, inPass)) {
            message.setText("Username or Password not correct");
        }
         else {
             //if username and pasword mathces then giving user access to the app
             SceneLoader.loadScene("Diary.fxml", e);
        }
    }

    /**
     * on create account button
     * @param e
     * @throws IOException
     */
    public void onCreate(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateUserScreen.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String css = this.getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    /**
     * method to check is username and password matches or not
     * @param username
     * @param password
     * @return
     */
    private boolean isLoginValid(String username, String password){
        if(username.equals("admin") && password.equals("admin")){
            user = new HealthProfessional("admin","admin","admin","admin");
            return true;
        }
        int size = Menu.dataBase.getHealthProfessionalDB().size();
        for(int i = 0; i < size ; i++){
           user = Menu.dataBase.getHealthProfessionalDB().get(i).value;
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
}