package com.example.operationschedularproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class EditTaskController extends TaskListController {
    @FXML
    TextArea description;
    @FXML
    ChoiceBox<String> choiceBox;
    String[] choices = new String[]{TaskPriority.LOW.toString(), TaskPriority.MEDIUM.toString(), TaskPriority.HIGH.toString()};
    @FXML
    public void initialize(){
        choiceBox.getItems().addAll(choices);
        System.out.println(selectedTask.getDescription());
        description.setText(selectedTask.getDescription());
        choiceBox.setValue(selectedTask.getPriority());
    }

    public void onEditTask(ActionEvent e) throws IOException {
        String newPriority = choiceBox.getValue().toString();
        String newDescription = description.getText();

        if(newPriority.isEmpty() || newDescription.isEmpty()){
            AlertDisplay.showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled.");
            return;
        }
        Task editedTask = new Task(newDescription,newPriority);
        currentUser.taskList.edit(selectedTask, editedTask);
       taskObservableList.remove(selectedTask);
       taskObservableList.add(editedTask);
        undoStack.push(new UndoAction(ActionType.EDIT, editedTask));
        SceneLoader.loadScene("TaskList.fxml", e);
    }
}
