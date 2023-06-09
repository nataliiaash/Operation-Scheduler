package com.example.operationschedularproject;

import com.example.operationschedularproject.LinkedList.EmptyException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Stack;

public class SearchController {
    HealthProfessional currentUser = LogInController.user;
    @FXML
    Label name, label, timeTaken;
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
    @FXML
    ChoiceBox<String> choiceBox, searchType;
    @FXML
    TextField searchBar;
    @FXML
    DatePicker searchDate;
    private String selectedOption;
    private String[] searchOptions = new String[]{"Patient Name","Appointment Type", "Date", "Start Time", "End Time" };
    public static ObservableList<Appointment> searchObservableList = FXCollections.observableArrayList();;
    public static Stack<UndoAction> undoStack = new Stack<>();
    public static Stack<Appointment> editStack = new Stack<>();
    public static Stack<UndoAction> redoStack = new Stack<>();
    public static Stack<Appointment> redoEditStack = new Stack<>();
    static Appointment selectedAppointment;
    long startTime;
    long endTime;
    long elapsedTime;
    @FXML
    public void initialize() {
        searchDate.setVisible(false);
        searchType.setVisible(false);
        choiceBox.setValue("Patient Name");
        label.setText("Search Bar");
        choiceBox.getItems().addAll(searchOptions);
        // Event listener for choiceBox selection change
        choiceBox.setOnAction(event -> {
            selectedOption = choiceBox.getValue();
            getSearchType(selectedOption);
        });
        try {
            patientNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().patient));
            typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTreatmentType()));
            dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
            startTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartTime()));
            endTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndTime()));
            if(searchObservableList != null) {
                table.setItems(searchObservableList);
            }
            name.setText("Hi!, " + currentUser.getName());
            table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        } catch (Exception e){
            System.out.println("error");
        }
    }
    public void onDelete(ActionEvent e) throws EmptyException, IOException {
        selectedAppointment = table.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            currentUser.diary.delete(selectedAppointment);
            Menu.dataBase.delete(currentUser);
            searchObservableList.remove(selectedAppointment);
            table.getItems().remove(selectedAppointment);
            undoStack.push(new UndoAction(ActionType.DELETE,selectedAppointment));
            Menu.dataBase.add(currentUser);
        } else {
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "No appointment selected.");
        }
    }
    public void onEdit(ActionEvent e) throws IOException, EmptyException {
        selectedAppointment = table.getSelectionModel().getSelectedItem();
        editStack.push(selectedAppointment);
        if (selectedAppointment != null){
            Menu.dataBase.delete(currentUser);
            SceneLoader.loadScene("EditAppointmentFromSearch.fxml", e);
        } else {
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "No appointment selected.");
        }
    }
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
                    searchObservableList.remove(appointment);
                }
                break;
            case DELETE:
                // Undo the delete operation
                if (appointment != null) {
                    redoStack.push(new UndoAction(ActionType.DELETE, appointment));
                    currentUser.diary.add(appointment);
                    searchObservableList.add(appointment);
                }
                break;
            case EDIT:
                // Undo the edit operation
                if (appointment != null) {
                    Appointment prevAppointment = editStack.pop();
                    redoEditStack.push(appointment);
                    redoStack.push(new UndoAction(ActionType.EDIT, prevAppointment));
                    currentUser.diary.edit(prevAppointment, appointment);
                    searchObservableList.remove(appointment);
                    searchObservableList.add(prevAppointment);
                }
                break;
        }
        Menu.dataBase.add(currentUser);
        table.setItems(searchObservableList);
    }
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
                // Undo the add operation
                if (appointment != null) {
                    currentUser.diary.add(appointment);
                    searchObservableList.add(appointment);
                }
                break;
            case DELETE:
                // Undo the delete operation
                if (appointment != null) {
                    currentUser.diary.delete(appointment);
                    searchObservableList.remove(appointment);
                }
                break;
            case EDIT:
                // Undo the edit operation
                if (appointment != null) {
                    Appointment prevAppointment = redoEditStack.pop();
                    currentUser.diary.edit(prevAppointment, appointment);
                    searchObservableList.remove(appointment);
                    searchObservableList.add(prevAppointment);
                }
                break;
        }
        Menu.dataBase.add(currentUser);
        table.setItems(searchObservableList);
    }
    public void onSearch(ActionEvent e){
        String searchText;
        selectedOption = choiceBox.getValue();
        if(selectedOption.equals("Patient Name")){
            searchText = searchBar.getText();
            startTime = System.nanoTime();
            searchByName(searchText);
            endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
        } else if(selectedOption.equals("Appointment Type")){
            searchText = searchType.getValue();
            startTime = System.nanoTime();
            searchByType(searchText);
            endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
        }else if(selectedOption.equals("Date")){
            try {
                searchText = searchDate.getValue().toString();
            } catch (NullPointerException nullPointerException){
                AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "Date format is invalid");
                return;
            }
            startTime = System.nanoTime();
            searchByDate(searchText);
            endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
        }else if(selectedOption.equals("Start Time")){
            searchText = searchBar.getText();
            if(!InputValidator.timeValidation(searchText)){
                AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "Time format is invalid");
                return;
            }
            startTime = System.nanoTime();
            searchByStartTime(searchText);
            endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
        }else if(selectedOption.equals("End Time")){
            searchText = searchBar.getText();
            if(!InputValidator.timeValidation(searchText)){
                AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "Time format is invalid");
                return;
            }
            startTime = System.nanoTime();
            searchByEndTime(searchText);
            endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
        }
        timeTaken.setText("Search took " + elapsedTime + "nano seconds");
        table.setItems(searchObservableList);
    }
    public void searchByName(String searchText){
        // Clear the previous search results
        searchObservableList.clear();
        // Iterate over the appointments and add matching ones to the searchObservableList
        for(int i = 0; i < currentUser.diary.appointments.size(); i++){
            Appointment appointment = currentUser.diary.appointments.get(i).value;
            if(appointment.patient.equals(searchText)){
                searchObservableList.add(appointment);
            }
        }
    }
    public void searchByType(String searchText){
        // Clear the previous search results
        searchObservableList.clear();
        // Iterate over the appointments and add matching ones to the searchObservableList
        for(int i = 0; i < currentUser.diary.appointments.size(); i++){
            Appointment appointment = currentUser.diary.appointments.get(i).value;
            if(appointment.getTreatmentType().equals(searchText)){
                searchObservableList.add(appointment);
            }
        }
    }
    public void onDiary(ActionEvent e) throws IOException {
        SceneLoader.loadScene("Diary.fxml",e);
    }
    public void onTaskList(ActionEvent e) throws IOException {
        SceneLoader.loadScene("TaskList.fxml",e);
    }
    public void onMachineBook(ActionEvent e) throws IOException {
        SceneLoader.loadScene("MachineBooking.fxml",e);
    }
    public void searchByDate(String searchText){
        // Clear the previous search results
        searchObservableList.clear();
        // Iterate over the appointments and add matching ones to the searchObservableList
        for(int i = 0; i < currentUser.diary.appointments.size(); i++){
            Appointment appointment = currentUser.diary.appointments.get(i).value;
            if(appointment.date.equals(searchText)){
                searchObservableList.add(appointment);
            }
        }
    }
    public void searchByStartTime(String searchText){
        // Clear the previous search results
        searchObservableList.clear();
        // Iterate over the appointments and add matching ones to the searchObservableList
        for(int i = 0; i < currentUser.diary.appointments.size(); i++){
            Appointment appointment = currentUser.diary.appointments.get(i).value;
            if(appointment.startTime.equals(searchText)){
                searchObservableList.add(appointment);
            }
        }
    }
    public void searchByEndTime(String searchText){
        // Clear the previous search results
        searchObservableList.clear();
        // Iterate over the appointments and add matching ones to the searchObservableList
        for(int i = 0; i < currentUser.diary.appointments.size(); i++){
            Appointment appointment = currentUser.diary.appointments.get(i).value;
            if(appointment.endTime.equals(searchText)){
                searchObservableList.add(appointment);
            }
        }
    }
    public void getSearchType(String selectedOption){
        if(selectedOption.equals("Patient Name")){
            searchDate.setVisible(false);
            searchType.setVisible(false);
            searchBar.setVisible(true);
        } else if(selectedOption.equals("Appointment Type")){
            searchDate.setVisible(false);
            searchBar.setVisible(false);
            searchType.getItems().addAll(AddScreenController.appointmentTypes);
            searchType.setVisible(true);
        }else if(selectedOption.equals("Date")){
            searchType.setVisible(false);
            searchBar.setVisible(false);
            searchDate.setVisible(true);
        }else if(selectedOption.equals("Start Time")){
            searchDate.setVisible(false);
            searchType.setVisible(false);
            searchBar.setVisible(true);
        }else if(selectedOption.equals("End Time")){
            searchDate.setVisible(false);
            searchType.setVisible(false);
            searchBar.setVisible(true);
        }

    }
}
