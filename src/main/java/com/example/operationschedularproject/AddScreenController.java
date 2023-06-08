package com.example.operationschedularproject;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class AddScreenController extends DiaryController {
    @FXML
    TextField patientNameField, startField, endField;
    public static String[] machines = new String[]{"X-RAY", "MRI", "Ultra Scan", "ECG", "Ultra Sound"};
    public static String[] appointmentTypes = new String[]{"Consultation",
            "Follow-up",
            "Check-up",
            "Prescription",
            "Surgery",
            "Counseling",
            "Results",
            "Referral",
            "Lab tests"};
    @FXML
    DatePicker dateField;
    @FXML
    ChoiceBox<String> choiceBox, machineChoiceBox, appointmentTypeField;
    String[] choices = new String[]{TaskPriority.LOW.toString(), TaskPriority.MEDIUM.toString(), TaskPriority.HIGH.toString()};
    @FXML
    TextArea description;

    public void onAddAppointment(ActionEvent e) throws IOException {
        String patientName = patientNameField.getText();
        String type;
        String startTime = startField.getText();
        String endTime = endField.getText();
        String date;
        try {
            date = dateField.getValue().toString();
        } catch (NullPointerException exception){
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "Date Format is invalid");
            return;
        }
        try{
            type = appointmentTypeField.getValue().toString();
        } catch (NullPointerException exception){
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "You must enter an appointment type");
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
            Menu.dataBase.add(currentUser);
            SceneLoader.loadScene("Diary.fxml", e);
            return;
        }
        currentUser.diary.add(appointment);
        Menu.dataBase.add(currentUser);
        undoStack.push(new UndoAction(ActionType.ADD, appointment));

        appointmentObservableList = FXCollections.observableArrayList();
        for (int i = 0; i < currentUser.diary.appointments.size(); i++){
            appointmentObservableList.add(currentUser.diary.appointments.get(i).value);
        }
        SceneLoader.loadScene("Diary.fxml", e);
    }
    public void onAddTask(ActionEvent e) throws IOException {
        String taskDescription = description.getText();
        String taskPriority;
        try{
            taskPriority = choiceBox.getValue().toString();
        } catch (NullPointerException exception){
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "You must enter Task Priority");
            return;
        }
        if(taskPriority.isEmpty() || taskDescription.isEmpty()){
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled.");
            return;
        }
        Task task = new Task(taskDescription, taskPriority);

        currentUser.taskList.add(task);
        Menu.dataBase.add(currentUser);

        TaskListController.undoStack.push(new UndoAction(ActionType.ADD, task));

        TaskListController.taskObservableList = FXCollections.observableArrayList();
        for (int i = 0; i < currentUser.taskList.taskLinkedList.size(); i++){
            TaskListController.taskObservableList.add(currentUser.taskList.taskLinkedList.get(i).value);
        }
        SceneLoader.loadScene("TaskList.fxml", e);
    }
    public void onAddMachine(ActionEvent e) throws IOException {
        String machineName;
        String startTime = startField.getText();
        String endTime = endField.getText();
        String date;
        try {
            date = dateField.getValue().toString();
        } catch (NullPointerException exception){
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "Date Format is invalid");
            return;
        }
        try{
            machineName = machineChoiceBox.getValue().toString();
        } catch (NullPointerException exception){
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "You must choose a machine");
            return;
        }

        if(machineName.isEmpty() || startTime.isEmpty() || endTime.isEmpty() || date.isEmpty()){
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled.");
            return;
        }
        if(!InputValidator.timeValidation(startTime) || !InputValidator.timeValidation(endTime)){
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "Time format is invalid");
            Menu.dataBase.add(currentUser);
            return;
        }
       Machine machine = new Machine(machineName, date, startTime, endTime);
        if(!InputValidator.isAddMachineValid(currentUser, machine)){
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "You can't add this appointment as this will overlap with your another appointment");
            Menu.dataBase.add(currentUser);
            SceneLoader.loadScene("MachineBooking.fxml", e);
            return;
        }

        currentUser.machineBooker.add(machine);
        Menu.dataBase.add(currentUser);

        MachineBookController.undoStack.push(new UndoAction(ActionType.ADD, machine));

        MachineBookController.machinesObservableList = FXCollections.observableArrayList();
        for (int i = 0; i < currentUser.machineBooker.getMachineLinkedList().size(); i++){
            MachineBookController.machinesObservableList.add(currentUser.machineBooker.getMachineLinkedList().get(i).value);
        }
        SceneLoader.loadScene("MachineBooking.fxml", e);
    }
    public void onAddAppointmentBack(ActionEvent e) throws Exception {
        Menu.dataBase.add(currentUser);
        SceneLoader.loadScene("Diary.fxml", e);
    }
    public void onAddTaskBack(ActionEvent e) throws Exception {
        Menu.dataBase.add(currentUser);
        SceneLoader.loadScene("TaskList.fxml", e);
    }
    public void onAddMachineBack(ActionEvent e) throws Exception {
        Menu.dataBase.add(currentUser);
        SceneLoader.loadScene("MachineBooking.fxml", e);
    }

    @FXML
    public void initialize() {
        try {
            appointmentTypeField.getItems().addAll(appointmentTypes);
        }catch (NullPointerException nullPointerException){
        }
        try {
            machineChoiceBox.getItems().addAll(machines);
        } catch (NullPointerException nullPointerException){
        }
        try {
            choiceBox.getItems().addAll(choices);
        }catch (NullPointerException nullPointerException){
        }
    }

}
