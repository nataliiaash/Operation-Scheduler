package com.example.operationschedularproject;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

public class EditAppointmentController extends MainScreenController implements Initializable {
    @FXML
    TextField patientNameField, startField, endField, appointmentTypeField;
    @FXML
    DatePicker dateField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        undoStack.push(new UndoAction(ActionType.EDIT, selectedAppointment));
        patientNameField.setText(selectedAppointment.patient);
        LocalDate date = LocalDate.parse(selectedAppointment.getDate());
        dateField.setValue(date);
        startField.setText(selectedAppointment.getStartTime());
        endField.setText(selectedAppointment.getEndTime());
        appointmentTypeField.setText(selectedAppointment.getTreatmentType());
    }

    public void onEditAppointment(ActionEvent e) throws IOException {
        String patientName = patientNameField.getText();
        String type = appointmentTypeField.getText();
        String startTime = startField.getText();
        String endTime = endField.getText();
        String date = dateField.getValue().toString();
        
        if(patientName.isEmpty() || startTime.isEmpty() || endTime.isEmpty() || date.isEmpty()|| type.isEmpty()){
            showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled.");
            return;
        }
        Appointment editedAppointment = new Appointment(patientName, type, date, startTime,endTime);
        currentUser.diary.delete(selectedAppointment);
        appointmentObservableList.remove(selectedAppointment);
        currentUser.diary.add(editedAppointment);
        appointmentObservableList.add(editedAppointment);
        undoStack.push(new UndoAction(ActionType.EDIT, editedAppointment));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
//                String css = this.getClass().getResource("style.css").toExternalForm();
//                scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}
