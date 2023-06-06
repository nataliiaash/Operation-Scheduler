package com.example.operationschedularproject;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddScreenController extends MainScreenController {
    Parent thisRoot;
    Stage thisStage;
    Scene thisScene;
    @FXML
    TextField patientNameField, appointmentTypeField, startField, endField;
    @FXML
    DatePicker dateField;

    public void onAddAppointment(ActionEvent e) throws IOException {
        String patientName = patientNameField.getText();
        String type = appointmentTypeField.getText();
        String startTime = startField.getText();
        String endTime = endField.getText();
        String date = dateField.getValue().toString();

        if(patientName.isEmpty() || startTime.isEmpty() || endTime.isEmpty() || date.isEmpty()|| type.isEmpty()){
            showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled.");
            return;
        }
        Appointment appointment = new Appointment(patientName, type, date, startTime, endTime);
        currentUser.diary.add(appointment);

        undoStack.push(new UndoAction(ActionType.ADD, appointment));

        appointmentObservableList = FXCollections.observableArrayList();
        for (int i = 0; i < currentUser.diary.appointments.size(); i++){
            appointmentObservableList.add(currentUser.diary.appointments.get(i).value);
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        thisRoot = loader.load();
        thisStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        thisScene = new Scene(thisRoot);
//                String css = this.getClass().getResource("style.css").toExternalForm();
//                scene.getStylesheets().add(css);
        thisStage.setScene(thisScene);
        thisStage.sizeToScene();
        thisStage.show();

    }
    public void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
