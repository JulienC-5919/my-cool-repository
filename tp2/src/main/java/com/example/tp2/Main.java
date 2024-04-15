package com.example.tp2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {


    TableView tvObjets;

    FlowPane barreOutils;

    TextField txfRecherche;



    @Override
    public void start(Stage stage) throws IOException {
        //genererTableView();
        genererBarreOutils();
        BorderPane root = new BorderPane();

        root.setTop(barreOutils);

        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void genererBarreOutils() {
        barreOutils = new FlowPane();

        barreOutils.setHgap(5);

        Button btnAjouterObjet = new Button();
        Button btnSupprimerObjet = new Button();

        ToggleButton tbtnOutil = new ToggleButton();
        ToggleButton tbtnLivre = new ToggleButton();
        ToggleButton tbtnJeu = new ToggleButton();

        ChoiceBox<String> choixEtat = new ChoiceBox<String>();
        choixEtat.getItems().addAll("Tous", "En possession", "Prêté", "Perdu");

        txfRecherche = new TextField();
        txfRecherche.setPromptText("Recherche");

        barreOutils.getChildren().addAll(
                btnAjouterObjet, btnSupprimerObjet, new Separator(Orientation.VERTICAL), tbtnOutil, tbtnLivre, tbtnJeu,
                new Separator(Orientation.VERTICAL), new Label("Filtre"), choixEtat, txfRecherche
        );
    }
}