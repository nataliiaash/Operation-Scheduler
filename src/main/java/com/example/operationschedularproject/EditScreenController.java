package com.example.operationschedularproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditScreenController extends DiaryController implements Initializable {
    @FXML
    TextField patientNameField, startField, endField, appointmentTypeField;
    @FXML
    DatePicker dateField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled.");
            return;
        }
        Appointment editedAppointment = new Appointment(patientName, type, date, startTime,endTime);
        currentUser.diary.edit(editedAppointment, selectedAppointment);
        appointmentObservableList.remove(selectedAppointment);
        appointmentObservableList.add(editedAppointment);
        undoStack.push(new UndoAction(ActionType.EDIT, editedAppointment));
        SceneLoader.loadScene("Diary.fxml", e);
    }
}
