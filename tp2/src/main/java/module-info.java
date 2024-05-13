module com.example.tp {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jdi;


    opens com.example.tp2 to javafx.fxml;
    exports com.example.tp2;
}