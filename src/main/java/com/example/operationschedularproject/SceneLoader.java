package com.example.operationschedularproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneLoader {
    private static Parent root;
    private static Stage stage;
    private static Scene scene;
    public static void loadScene(String fxmlPath, ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource(fxmlPath));
        root = loader.load();
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
//                String css = this.getClass().getResource("style.css").toExternalForm();
//                scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
    }
}
