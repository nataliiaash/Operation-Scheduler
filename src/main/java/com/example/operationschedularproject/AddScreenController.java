package com.example.operationschedularproject;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class AddScreenController extends MainScreenController {
    @FXML
    TextField patientNameField, appointmentTypeField, startField, endField;
    @FXML
    DatePicker dateField;
    @FXML
    ChoiceBox<String> choiceBox;
    String[] choices = new String[]{TaskPriority.LOW.toString(), TaskPriority.MEDIUM.toString(), TaskPriority.HIGH.toString()};
    @FXML
    TextArea description;

    public void onAddAppointment(ActionEvent e) throws IOException {
        String patientName = patientNameField.getText();
        String type = appointmentTypeField.getText();
        String startTime = startField.getText();
        String endTime = endField.getText();
        String date;
        try {
            date = dateField.getValue().toString();
        } catch (NullPointerException exception){
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "Date Format is invalid");
            return;
        }

        if(patientName.isEmpty() || startTime.isEmpty() || endTime.isEmpty() || date.isEmpty()|| type.isEmpty()){
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled.");
            return;
        }
        if(!InputValidator.timeValidation(startTime) || !InputValidator.timeValidation(endTime)){
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "Time format is invalid");
            return;
        }
        Appointment appointment = new Appointment(patientName, type, date, startTime, endTime);
        if(!InputValidator.isAddAppointmentValid(currentUser,appointment)){
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "You can't add this appointment as this will overlap with your another appointment");
            SceneLoader.loadScene("MainScreen.fxml", e);
            return;
        }
        currentUser.diary.add(appointment);

        undoStack.push(new UndoAction(ActionType.ADD, appointment));

        appointmentObservableList = FXCollections.observableArrayList();
        for (int i = 0; i < currentUser.diary.appointments.size(); i++){
            appointmentObservableList.add(currentUser.diary.appointments.get(i).value);
        }
        SceneLoader.loadScene("MainScreen.fxml", e);
    }
    public void onAddTask(ActionEvent e) throws IOException {
        String taskDescription = description.getText();
        String taskPriority = choiceBox.getValue().toString();
        if(taskPriority.isEmpty() || taskDescription.isEmpty()){
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled.");
            return;
        }
        Task task = new Task(taskDescription, taskPriority);

        currentUser.taskList.add(task);

        TaskListController.undoStack.push(new UndoAction(ActionType.ADD, task));

        TaskListController.taskObservableList = FXCollections.observableArrayList();
        for (int i = 0; i < currentUser.taskList.taskLinkedList.size(); i++){
            TaskListController.taskObservableList.add(currentUser.taskList.taskLinkedList.get(i).value);
        }
        SceneLoader.loadScene("TaskList.fxml", e);
    }

    @FXML
    public void initialize() {
        try {
            choiceBox.getItems().addAll(choices);
        }catch (NullPointerException nullPointerException){
        }
    }
}
