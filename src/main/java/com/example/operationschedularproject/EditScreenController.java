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
    TextField patientNameField, startField, endField;
    @FXML
    DatePicker dateField;
    @FXML
    TextArea description;
    @FXML
    ChoiceBox<String> choiceBox, machineChoiceBox, appointmentTypeField;
    String[] choices = new String[]{TaskPriority.LOW.toString(), TaskPriority.MEDIUM.toString(), TaskPriority.HIGH.toString()};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * initializing every field in edit screen with the details of the appointment/task/booking the user selected
         */
        try {
            choiceBox.getItems().addAll(choices);
            System.out.println(TaskListController.selectedTask.getDescription());
            description.setText(TaskListController.selectedTask.getDescription());
            choiceBox.setValue(TaskListController.selectedTask.getPriority());
        } catch (NullPointerException nullPointerException){
        }
        try {
            appointmentTypeField.getItems().addAll(AddScreenController.appointmentTypes);
            patientNameField.setText(selectedAppointment.patient);
            LocalDate date = LocalDate.parse(selectedAppointment.getDate());
            dateField.setValue(date);
            startField.setText(selectedAppointment.getStartTime());
            endField.setText(selectedAppointment.getEndTime());
            appointmentTypeField.setValue(selectedAppointment.getTreatmentType());
        }catch (NullPointerException nullPointerException){
        }
        try {
            appointmentTypeField.getItems().addAll(AddScreenController.appointmentTypes);
            patientNameField.setText(SearchController.selectedAppointment.patient);
            LocalDate date = LocalDate.parse(SearchController.selectedAppointment.getDate());
            dateField.setValue(date);
            startField.setText(SearchController.selectedAppointment.getStartTime());
            endField.setText(SearchController.selectedAppointment.getEndTime());
            appointmentTypeField.setValue(SearchController.selectedAppointment.getTreatmentType());
        }catch (NullPointerException nullPointerException){
        }
        try {
            machineChoiceBox.getItems().addAll(AddScreenController.machines);
            machineChoiceBox.setValue(MachineBookController.selectedMachine.getName());
            LocalDate date = LocalDate.parse(MachineBookController.selectedMachine.getDate());
            dateField.setValue(date);
            startField.setText(MachineBookController.selectedMachine.getStartTime());
            endField.setText(MachineBookController.selectedMachine.getEndTime());
        }catch (NullPointerException nullPointerException){
        }

    }

    /**
     * on edit appointment button
     * @param e
     * @throws IOException
     */
    public void onEditAppointment(ActionEvent e) throws IOException {
        editAppointment(e);
        SceneLoader.loadScene("Diary.fxml", e);
    }

    /**
     * on edit appointment button
     * @param e
     * @throws IOException
     */
    public void onEditAppointmentFromSearch(ActionEvent e) throws IOException {
        /**
         * this method and the edit appointment method is 100% same they just appear in two different pages
         */
        editAppointment(e);
        SceneLoader.loadScene("Search.fxml", e);
    }

    /**
     * edit appointment method
     * @throws IOException
     */
    private void editAppointment (ActionEvent e) throws IOException {
        /**
         * this method is similar to add appointment method with only a few changes
         */
        String patientName = patientNameField.getText();
        String type = appointmentTypeField.getValue().toString();
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

        Appointment editedAppointment = new Appointment(patientName, type, date, startTime,endTime);
        if(!InputValidator.isAddAppointmentValid(currentUser,editedAppointment)){
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "You can't edit this appointment as this will overlap with your another appointment");
            Menu.dataBase.add(currentUser);
            SceneLoader.loadScene("Diary.fxml", e);
            return;
        }
        //editing the appointment
        currentUser.diary.edit(editedAppointment, selectedAppointment);
        Menu.dataBase.add(currentUser);
        //removing old appointment from the observable list and adding the new one
        appointmentObservableList.remove(selectedAppointment);
        appointmentObservableList.add(editedAppointment);
        undoStack.push(new UndoAction(ActionType.EDIT, editedAppointment));
    }

    /**
     * on edit task button
     * @param e
     * @throws IOException
     */
    public void onEditTask(ActionEvent e) throws IOException {
        /**
         * this method's logic is similar to edit appointment method
         */
        String newPriority = choiceBox.getValue().toString();
        String newDescription = description.getText();

        if(newPriority.isEmpty() || newDescription.isEmpty()){
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled.");
            return;
        }
        Task editedTask = new Task(newDescription,newPriority);
        currentUser.taskList.edit(TaskListController.selectedTask, editedTask);
        Menu.dataBase.add(currentUser);
        TaskListController.taskObservableList.remove(TaskListController.selectedTask);
        TaskListController.taskObservableList.add(editedTask);
        undoStack.push(new UndoAction(ActionType.EDIT, editedTask));
        SceneLoader.loadScene("TaskList.fxml", e);
    }

    /**
     * on edit machine booking button
     * @param e
     * @throws IOException
     */
    public void onEditMachine(ActionEvent e) throws IOException {
        /**
         * this method's logic is similar to edit appointment method
         */
        String machineName = machineChoiceBox.getValue().toString();
        String startTime = startField.getText();
        String endTime = endField.getText();
        String date = dateField.getValue().toString();

        if(machineName.isEmpty() || startTime.isEmpty() || endTime.isEmpty() || date.isEmpty()){
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled.");
            return;
        }
        Machine editedMachine = new Machine(machineName, date, startTime,endTime);
        currentUser.machineBooker.edit(MachineBookController.selectedMachine, editedMachine);
        Menu.dataBase.add(currentUser);
        MachineBookController.machinesObservableList.remove(MachineBookController.selectedMachine);
        MachineBookController.machinesObservableList.add(editedMachine);
        undoStack.push(new UndoAction(ActionType.EDIT, editedMachine));
        SceneLoader.loadScene("MachineBooking.fxml", e);
    }

    /**
     * on back button
     * @param e
     * @throws Exception
     */
    public void onEditAppointmentBack(ActionEvent e) throws Exception {
        Menu.dataBase.add(currentUser);
        SceneLoader.loadScene("Diary.fxml", e);
    }
    /**
     * on back button
     * @param e
     * @throws Exception
     */
    public void onEditAppointmentFromSearchBack(ActionEvent e) throws Exception {
        Menu.dataBase.add(currentUser);
        SceneLoader.loadScene("Search.fxml", e);
    }
    /**
     * on back button
     * @param e
     * @throws Exception
     */
    public void onEditTaskBack(ActionEvent e) throws Exception {
        Menu.dataBase.add(currentUser);
        SceneLoader.loadScene("TaskList.fxml", e);
    }
    /**
     * on back button
     * @param e
     * @throws Exception
     */
    public void onEditMachineBack(ActionEvent e) throws Exception {
        Menu.dataBase.add(currentUser);
        SceneLoader.loadScene("MachineBooking.fxml", e);
    }
}
