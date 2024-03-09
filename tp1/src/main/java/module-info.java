module com.example.applications {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.applications to javafx.fxml;
    exports com.example.applications;
}