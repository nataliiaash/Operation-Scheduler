package com.example.operationschedularproject;

import com.example.operationschedularproject.LinkedList.EmptyException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.util.*;

public class DiaryController {
    @FXML
    Label name;
    HealthProfessional currentUser = LogInController.user;
    @FXML
    TableView<Appointment> table;
    @FXML
    private TableColumn<Appointment, String> patientNameColumn;
    @FXML
    private TableColumn<Appointment, String> dateColumn;
    @FXML
    private TableColumn<Appointment, String> typeColumn;
    @FXML
    private TableColumn<Appointment, String> startTimeColumn;
    @FXML
    private TableColumn<Appointment, String> endTimeColumn;
    public static ObservableList<Appointment> appointmentObservableList;
    public static Appointment selectedAppointment;
    public static Stack<UndoAction> undoStack = new Stack<>();
    public static Stack<Appointment> editStack = new Stack<>();
    public static Stack<UndoAction> redoStack = new Stack<>();
    public static Stack<Appointment> redoEditStack = new Stack<>();

    /**
     * on add button in diary screen
     * @param e
     * @throws IOException
     * @throws EmptyException
     */
   public void onAdd(ActionEvent e) throws IOException, EmptyException {
       Menu.dataBase.delete(currentUser);
       SceneLoader.loadScene("AddAppointment.fxml", e);
   }

    /**
     * on delete button in diary screen
     * @param e
     * @throws EmptyException
     * @throws IOException
     */
    public void onDelete(ActionEvent e) throws EmptyException, IOException {
        selectedAppointment = table.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            currentUser.diary.delete(selectedAppointment);
            Menu.dataBase.delete(currentUser);
            appointmentObservableList.remove(selectedAppointment);
            table.getItems().remove(selectedAppointment);
            undoStack.push(new UndoAction(ActionType.DELETE,selectedAppointment));
            Menu.dataBase.add(currentUser);
        } else {
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "No appointment selected.");
        }
    }

    /**
     * on edit button in diary screen
     * @param e
     * @throws IOException
     * @throws EmptyException
     */
    public void onEdit(ActionEvent e) throws IOException, EmptyException {
       selectedAppointment = table.getSelectionModel().getSelectedItem();
       editStack.push(selectedAppointment);
        if (selectedAppointment != null){
            Menu.dataBase.delete(currentUser);
           SceneLoader.loadScene("EditAppointment.fxml", e);
        } else {
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "No appointment selected.");
        }
    }

    /**
     * on undo button in diary screen
     * @param e
     * @throws IOException
     * @throws EmptyException
     */
    public void onUndo(ActionEvent e) throws IOException, EmptyException {
      if(undoStack.isEmpty()){
          AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "Nothing to undo");
          return;
      }
        Menu.dataBase.delete(currentUser);
        UndoAction undoAction = undoStack.pop();
        ActionType actionType = undoAction.getActionType();
        Appointment appointment = (Appointment) undoAction.getObject();

        switch (actionType) {
            case ADD:
                // Undo the add operation
                if (appointment != null) {
                    redoStack.push(new UndoAction(ActionType.ADD, appointment));
                    currentUser.diary.delete(appointment);
                    appointmentObservableList.remove(appointment);
                }
                break;
            case DELETE:
                // Undo the delete operation
                if (appointment != null) {
                    redoStack.push(new UndoAction(ActionType.DELETE, appointment));
                    currentUser.diary.add(appointment);
                    appointmentObservableList.add(appointment);
                }
                break;
            case EDIT:
                // Undo the edit operation
                if (appointment != null) {
                    Appointment prevAppointment = editStack.pop();
                    redoEditStack.push(appointment);
                    redoStack.push(new UndoAction(ActionType.EDIT, prevAppointment));
                    currentUser.diary.edit(prevAppointment, appointment);
                    appointmentObservableList.remove(appointment);
                    appointmentObservableList.add(prevAppointment);
                }
                break;
        }
        Menu.dataBase.add(currentUser);
        table.setItems(appointmentObservableList);
    }

    /**
     * on redo button in diary screen
     * @param e
     * @throws EmptyException
     * @throws IOException
     */
    public void onRedo(ActionEvent e) throws EmptyException, IOException {
        if(redoStack.isEmpty()){
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "Nothing to redo");
            return;
        }
        Menu.dataBase.delete(currentUser);
        UndoAction redoAction = redoStack.pop();
        ActionType actionType = redoAction.getActionType();
        Appointment appointment = (Appointment) redoAction.getObject();

        switch (actionType) {
            case ADD:
                // Redo the add operation
                if (appointment != null) {
                    currentUser.diary.add(appointment);
                    appointmentObservableList.add(appointment);
                }
                break;
            case DELETE:
                // redo the delete operation
                if (appointment != null) {
                    currentUser.diary.delete(appointment);
                    appointmentObservableList.remove(appointment);
                }
                break;
            case EDIT:
                // REdo the edit operation
                if (appointment != null) {
                    Appointment prevAppointment = redoEditStack.pop();
                    currentUser.diary.edit(prevAppointment, appointment);
                    appointmentObservableList.remove(appointment);
                    appointmentObservableList.add(prevAppointment);
                }
                break;
        }
        //updating the database
        Menu.dataBase.add(currentUser);
        //updating the table
        table.setItems(appointmentObservableList);
    }

    /**
     * on taskList button in diary screen switches to taskList screen
     * @param e
     * @throws IOException
     */
    public void onTaskList(ActionEvent e) throws IOException {
       SceneLoader.loadScene("TaskList.fxml",e);
    }
    /**
     * on machine booking button in diary screen switches to machine booking screen
     * @param e
     * @throws IOException
     */
    public void onMachineBookings(ActionEvent e) throws IOException {
        SceneLoader.loadScene("MachineBooking.fxml",e);
    }
    /**
     * on search button in diary screen switches to search screen
     * @param e
     * @throws IOException
     */
    public void onSearch(ActionEvent e) throws IOException {
        SceneLoader.loadScene("Search.fxml",e);
    }
    @FXML
    public void initialize() {
        //if user has previous appointments then adding them to the observablelist
       if(currentUser.diary.appointments != null){
           appointmentObservableList = FXCollections.observableArrayList();
           for (int i = 0; i < currentUser.diary.appointments.size(); i++){
               appointmentObservableList.add(currentUser.diary.appointments.get(i).value);
           }
       }
       try {
           //setting the cell values of the table
           patientNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().patient));
           typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTreatmentType()));
           dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
           startTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartTime()));
           endTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndTime()));
           if(appointmentObservableList != null) {
               //populating table with items
               table.setItems(appointmentObservableList);
           }
           name.setText("Hi!, " + currentUser.getName());
           table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
       } catch (Exception e){
           System.out.println("error");
       }
    }
}


