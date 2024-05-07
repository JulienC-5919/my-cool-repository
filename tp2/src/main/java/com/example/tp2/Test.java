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

@Deprecated
public class Test extends Application{
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage){

        VBox rootFacture = new VBox();
        TextField imageFacture;
        imageFacture = new TextField();
        TextField r = new TextField();
        Scene scene = new Scene(rootFacture, 200, 200);

        //CorrecteursTextFields.ajouterCorrecteurPrix(r);

        rootFacture.getChildren().addAll(imageFacture,r);

        stage.setScene(scene);
        stage.setResizable(true);


        stage.show();


    }
}