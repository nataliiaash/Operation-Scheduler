module com.example.operationschedularproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;


    opens com.example.operationschedularproject to javafx.fxml;
    exports com.example.operationschedularproject;
}