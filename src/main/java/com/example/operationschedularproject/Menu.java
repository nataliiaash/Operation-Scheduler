package com.example.operationschedularproject;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class Menu extends Application{
    public static DataBase dataBase = new DataBase();
    public void start(Stage stage) throws Exception {
        Parent root =  FXMLLoader.load(getClass().getResource("LogInScreen.fxml"));
        Scene scene = new Scene(root,400,500);
        String css = this.getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        //stage.setOnCloseRequest(event -> {
//            try {
//                event.consume();
//                onExit(stage);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}