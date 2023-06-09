package com.example.operationschedularproject;

import javafx.scene.control.Alert;

public class AlertDisplay {
    /**
     * shows alert on the screen
     * @param alertType
     * @param title
     * @param message
     */
    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
