package com.example.tp2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TableViewRepair extends Application {
    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();

        TableView<Tmp> t = new TableView<Tmp>();
        TableColumn<Tmp, String> c = new TableColumn<Tmp, String>();
        c.setCellValueFactory(new PropertyValueFactory<Tmp, String>("nom"));

        ObservableList<Tmp> o = FXCollections.observableArrayList();

        t.setItems(o);

        t.getColumns().add(c);

        root.getChildren().add(t);

        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        Livre l= new Livre();
        l.setNom("f");
        //o.add(new Tmp("p"));

    }

    public static void main(String[] args) {
        launch();
    }
}