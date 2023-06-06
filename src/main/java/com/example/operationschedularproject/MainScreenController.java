package com.example.operationschedularproject;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainScreenController extends AlertDisplay {
@FXML
    Label name;
    HealthProfessional currentUser = LogInController.user;
    @FXML
    TableView<Appointment> table;
    Parent root;
    Stage stage;
    Scene scene;
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

   public void onAdd(ActionEvent e) throws IOException {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("AddAppointment.fxml"));
       root = loader.load();
       stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
       scene = new Scene(root);
//                String css = this.getClass().getResource("style.css").toExternalForm();
//                scene.getStylesheets().add(css);
       stage.setScene(scene);
       stage.sizeToScene();
       stage.show();
   }

    public void onDelete(ActionEvent e){
        selectedAppointment = table.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            currentUser.diary.delete(selectedAppointment);
            appointmentObservableList.remove(selectedAppointment);
            table.getItems().remove(selectedAppointment);
            undoStack.push(new UndoAction(ActionType.DELETE,selectedAppointment));
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "No appointment selected.");
        }
    }
    public void onEdit(ActionEvent e) throws IOException {
       selectedAppointment = table.getSelectionModel().getSelectedItem();
        undoStack.push(new UndoAction(ActionType.EDIT,selectedAppointment));
        if (selectedAppointment != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditAppointment.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
//                String css = this.getClass().getResource("style.css").toExternalForm();
//                scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "No appointment selected.");
        }
    }
    public void onUndo(ActionEvent e){
      if(undoStack.isEmpty()){
          showAlert(Alert.AlertType.ERROR, "Error", "Nothing to undo");
          return;
      }
        UndoAction undoAction = undoStack.pop();
        ActionType actionType = undoAction.getActionType();
        Appointment appointment = undoAction.getAppointment();

        switch (actionType) {
            case ADD:
                // Undo the add operation
                if (appointment != null) {
                    currentUser.diary.delete(appointment);
                    appointmentObservableList.remove(appointment);
                }
                break;
            case DELETE:
                // Undo the delete operation
                if (appointment != null) {
                    currentUser.diary.add(appointment);
                    appointmentObservableList.add(appointment);
                }
                break;
            case EDIT:
                // Undo the edit operation
                if (appointment != null) {
                    currentUser.diary.delete(appointment);
                    appointmentObservableList.remove(appointment);
                    Appointment prevAppointment = undoStack.pop().getAppointment();
                    currentUser.diary.add(prevAppointment);
                    appointmentObservableList.add(prevAppointment);
                }
                break;
        }
        table.setItems(appointmentObservableList);
    }
    @FXML
    public void initialize() {

       try {
           patientNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().patient));
           typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTreatmentType()));
           dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
           startTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartTime()));
           endTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndTime()));
           if(appointmentObservableList != null) {
               table.setItems(appointmentObservableList);
           }
           name.setText("Hi!, " + currentUser.getName());
           table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
       } catch (Exception e){
           System.out.println("error");
       }
    }
}


