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

public class TaskListController{
    @FXML
    Label name;
    HealthProfessional currentUser = LogInController.user;
    @FXML
    TableView<Task> table;
    @FXML
    private TableColumn<Task, String> descriptionColumn;
    @FXML
    private TableColumn<Task, String> priorityColumn;
    public static ObservableList<Task> taskObservableList;
    public static Task selectedTask;
    public static Stack<UndoAction> undoStack = new Stack<>();
    public static Stack<Task> editStack = new Stack<>();
    public static Stack<UndoAction> redoStack = new Stack<>();
    public static Stack<Task> redoEditStack = new Stack<>();

    public void onAdd(ActionEvent e) throws IOException, EmptyException {
        Menu.dataBase.delete(currentUser);
        SceneLoader.loadScene("AddTask.fxml", e);
    }

    public void onDelete(ActionEvent e) throws EmptyException, IOException {
        selectedTask = table.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            Menu.dataBase.delete(currentUser);
            currentUser.taskList.delete(selectedTask);
            taskObservableList.remove(selectedTask);
            table.getItems().remove(selectedTask);
            undoStack.push(new UndoAction(ActionType.DELETE, selectedTask));
            Menu.dataBase.add(currentUser);
        } else {
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "No appointment selected.");
        }
    }
    public void onEdit(ActionEvent e) throws Exception {
        selectedTask = table.getSelectionModel().getSelectedItem();
        editStack.push(selectedTask);
        if (selectedTask != null){
            Menu.dataBase.delete(currentUser);
            SceneLoader.loadScene("EditTask.fxml", e);
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
        Task task = (Task)undoAction.getObject();

        switch (actionType) {
            case ADD:
                // Undo the add operation
                if (task != null) {
                    redoStack.push(new UndoAction(ActionType.ADD, task));
                    currentUser.taskList.delete(task);
                    taskObservableList.remove(task);
                }
                break;
            case DELETE:
                // Undo the delete operation
                if (task != null) {
                    redoStack.push(new UndoAction(ActionType.DELETE, task));
                    currentUser.taskList.add(task);
                    taskObservableList.add(task);
                }
                break;
            case EDIT:
                // Undo the edit operation
                if (task != null) {
                    Task prevTask = editStack.pop();
                    redoEditStack.push(task);
                    redoStack.push(new UndoAction(ActionType.EDIT, prevTask));
                    currentUser.taskList.edit(prevTask, task);
                    taskObservableList.remove(task);
                    taskObservableList.add(prevTask);
                }
                break;
        }
        Menu.dataBase.add(currentUser);
        table.setItems(taskObservableList);
    }
    public void onRedo(ActionEvent e) throws EmptyException, IOException {
        if(redoStack.isEmpty()){
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "Nothing to redo");
            return;
        }
        Menu.dataBase.delete(currentUser);
        UndoAction redoAction = redoStack.pop();
        ActionType actionType = redoAction.getActionType();
        Task task = (Task) redoAction.getObject();

        switch (actionType) {
            case ADD:
                // Undo the add operation
                if (task != null) {
                    currentUser.taskList.add(task);
                    taskObservableList.add(task);
                }
                break;
            case DELETE:
                // Undo the delete operation
                if (task != null) {
                    currentUser.taskList.delete(task);
                    taskObservableList.remove(task);
                }
                break;
            case EDIT:
                // Undo the edit operation
                if (task != null) {
                    Task prevTask = redoEditStack.pop();
                    currentUser.taskList.edit(prevTask, task);
                    taskObservableList.remove(task);
                    taskObservableList.add(prevTask);
                }
                break;
        }
        Menu.dataBase.add(currentUser);
        table.setItems(taskObservableList);
    }
    public void onGoToDiary(ActionEvent e) throws IOException {
        SceneLoader.loadScene("Diary.fxml", e);
    }
    public void onMachineBooking(ActionEvent e) throws IOException {
        SceneLoader.loadScene("MachineBooking.fxml", e);
    }
    @FXML
    public void initialize() {
        if(currentUser.diary.appointments != null){
            taskObservableList = FXCollections.observableArrayList();
            for (int i = 0; i < currentUser.taskList.taskLinkedList.size(); i++){
                taskObservableList.add(currentUser.taskList.taskLinkedList.get(i).value);
            }
        }
        try {
            priorityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPriority().toString()));
            descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
            if(taskObservableList != null) {
                table.setItems(taskObservableList);
            }
            name.setText("Hi!, " + currentUser.getName());
            table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        } catch (Exception e){
            System.out.println("error");
        }
    }
}
