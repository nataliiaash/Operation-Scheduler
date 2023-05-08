module com.example.operationschedularproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.operationschedularproject to javafx.fxml;
    exports com.example.operationschedularproject;
}