package com.example.tp2;


import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.event.EventType;
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


import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Test extends Application{
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage){
        File f = new File("/home/pixldragon64/Images/path17-5.png");
        byte[] g;

        try {
            g = new FileInputStream(f).readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        ByteArrayInputStream l = new ByteArrayInputStream(g);

        StackPane rootFacture = new StackPane();
        ImageView imageFacture;
            imageFacture = new ImageView(new Image(l));
        Scene scene = new Scene(rootFacture);

        imageFacture.setFitWidth(600);
        imageFacture.setFitHeight(600);
        imageFacture.setPreserveRatio(true);

        rootFacture.getChildren().add(imageFacture);

        stage.setScene(scene);
        stage.setResizable(false);


        stage.show();
    }
}