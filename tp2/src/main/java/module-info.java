module com.example.tp2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tp2 to javafx.fxml;
    exports com.example.tp2;
}