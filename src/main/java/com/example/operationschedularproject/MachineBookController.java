package com.example.operationschedularproject;

import com.example.operationschedularproject.LinkedList.EmptyException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.crypto.Mac;
import java.io.IOException;
import java.util.Stack;

/**
 * This class is similar to th diaryController class
 */
public class MachineBookController {

    @FXML
    Label name;
    HealthProfessional currentUser = LogInController.user;
    @FXML
    TableView<Machine> table;
    @FXML
    private TableColumn<Machine, String> machineColumn;
    @FXML
    private TableColumn<Machine, String> dateColumn;
    @FXML
    private TableColumn<Machine, String> startTimeColumn;
    @FXML
    private TableColumn<Machine, String> endTimeColumn;
    public static ObservableList<Machine> machinesObservableList;
    public static Machine selectedMachine;
    public static Stack<UndoAction> undoStack = new Stack<>();
    public static Stack<Machine> editStack = new Stack<>();
    public static Stack<UndoAction> redoStack = new Stack<>();
    public static Stack<Machine> redoEditStack = new Stack<>();

    public void onAdd(ActionEvent e) throws IOException, EmptyException {
        Menu.dataBase.delete(currentUser);
        SceneLoader.loadScene("AddMachine.fxml", e);
    }

    public void onDelete(ActionEvent e) throws EmptyException, IOException {
        selectedMachine = table.getSelectionModel().getSelectedItem();
        if (selectedMachine != null) {
            Menu.dataBase.delete(currentUser);
            currentUser.machineBooker.delete(selectedMachine);
            machinesObservableList.remove(selectedMachine);
            table.getItems().remove(selectedMachine);
            Menu.dataBase.add(currentUser);
            undoStack.push(new UndoAction(ActionType.DELETE, selectedMachine));
        } else {
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "No booking selected.");
        }
    }
    public void onEdit(ActionEvent e) throws IOException, EmptyException {
        selectedMachine = table.getSelectionModel().getSelectedItem();
        editStack.push(selectedMachine);
        if (selectedMachine != null){
            Menu.dataBase.delete(currentUser);
            SceneLoader.loadScene("EditMachine.fxml", e);
        } else {
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "No appointment selected.");
        }
    }
    public void onUndo(ActionEvent e) throws EmptyException, IOException {
        if(undoStack.isEmpty()){
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "Nothing to undo");
            return;
        }
        Menu.dataBase.delete(currentUser);
        UndoAction undoAction = undoStack.pop();
        ActionType actionType = undoAction.getActionType();
        Machine machine = (Machine) undoAction.getObject();

        switch (actionType) {
            case ADD:
                // Undo the add operation
                if (machine != null) {
                    redoStack.push(new UndoAction(ActionType.ADD, machine));
                    currentUser.machineBooker.delete(machine);
                    machinesObservableList.remove(machine);
                }
                break;
            case DELETE:
                // Undo the delete operation
                if (machine != null) {
                    redoStack.push(new UndoAction(ActionType.DELETE, machine));
                    currentUser.machineBooker.add(machine);
                    machinesObservableList.add(machine);
                }
                break;
            case EDIT:
                // Undo the edit operation
                if (machine != null) {
                    Machine prevMachine = editStack.pop();
                    redoEditStack.push(machine);
                    redoStack.push(new UndoAction(ActionType.EDIT, prevMachine));
                    currentUser.machineBooker.edit(prevMachine, machine);
                    machinesObservableList.remove(machine);
                    machinesObservableList.add(prevMachine);
                }
                break;
        }
        Menu.dataBase.add(currentUser);
        table.setItems(machinesObservableList);
    }
    public void onRedo(ActionEvent e) throws EmptyException, IOException {
        if(redoStack.isEmpty()){
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "Nothing to redo");
            return;
        }
        Menu.dataBase.delete(currentUser);
        UndoAction redoAction = redoStack.pop();
        ActionType actionType = redoAction.getActionType();
        Machine machine = (Machine) redoAction.getObject();

        switch (actionType) {
            case ADD:
                // Undo the add operation
                if (machine != null) {
                    currentUser.machineBooker.add(machine);
                    machinesObservableList.add(machine);
                }
                break;
            case DELETE:
                // Undo the delete operation
                if (machine != null) {
                    currentUser.machineBooker.delete(machine);
                    machinesObservableList.remove(machine);
                }
                break;
            case EDIT:
                // Undo the edit operation
                if (machine != null) {
                    Machine prevMachine = redoEditStack.pop();
                    currentUser.machineBooker.edit(prevMachine, machine);
                    machinesObservableList.remove(machine);
                    machinesObservableList.add(prevMachine);
                }
                break;
        }
        Menu.dataBase.add(currentUser);
        table.setItems(machinesObservableList);
    }
    public void onTaskList(ActionEvent e) throws IOException {
        SceneLoader.loadScene("TaskList.fxml",e);
    }
    public void onDiary(ActionEvent e) throws IOException {
        SceneLoader.loadScene("Diary.fxml",e);
    }
    @FXML
    public void initialize() {
        if(currentUser.diary.appointments != null){
            machinesObservableList = FXCollections.observableArrayList();
            for (int i = 0; i < currentUser.machineBooker.getMachineLinkedList().size(); i++){
                machinesObservableList.add(currentUser.machineBooker.getMachineLinkedList().get(i).value);
            }
        }
        try {
            machineColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
            dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
            startTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartTime()));
            endTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndTime()));
            if(machinesObservableList != null) {
                table.setItems(machinesObservableList);
            }
            name.setText("Hi!, " + currentUser.getName());
            table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        } catch (Exception e){
            System.out.println("error");
        }
    }


}
