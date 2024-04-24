package com.example.tp2;


import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Test extends Application{

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        Button root = new Button("f");
        ImageView v = new ImageView();



        Scene s = new Scene(root, 500, 500);

        stage.setScene(s);
        stage.show();

        FileChooser f = new FileChooser();
        f.showOpenDialog(stage);
    }
    public static void main(String[] args) {
        launch();
    }
}

/*
StackPane s = new StackPane();

        FileInputStream i = new FileInputStream("C:\\Users\\2268130\\Pictures\\titre.png");
        Image m = new Image(i);
        ImageView v = new ImageView();

        v.setImage(null);

        s.getChildren().add(v);

        Scene c = new Scene(s, 800, 800);
        stage.setScene(c);
        stage.show();
 */
