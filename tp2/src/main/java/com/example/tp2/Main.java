package com.example.tp2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {


    TableView<Objet> tvObjets;

    HBox barreOutils;
    VBox barreHaut;

    TextField txfRecherche;



    @Override
    public void start(Stage stage) throws IOException {
        //genererTableView();
        genererBarreOutils();
        genererBarreHaut();
        genererTableau();
        BorderPane root = new BorderPane();

        root.setTop(barreOutils);

        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("2268130 TP2");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void genererBarreOutils() {
        barreOutils = new HBox();

        barreOutils.setSpacing(5);

        Button btnAjouterObjet = new Button("➕");
        Button btnSupprimerObjet = new Button("➖");

        ToggleButton tbtnOutil = new ToggleButton("\uD83D\uDD27");
        ToggleButton tbtnLivre = new ToggleButton("\uD83D\uDCD5");
        ToggleButton tbtnJeu = new ToggleButton("\uD83C\uDFAE");

        ChoiceBox<String> choixEtat = new ChoiceBox<String>();
        choixEtat.getItems().addAll("Tous", "En possession", "Prêté", "Perdu");

        txfRecherche = new TextField();
        txfRecherche.setPromptText("Recherche");

        barreOutils.getChildren().addAll(
                btnAjouterObjet, btnSupprimerObjet, new Separator(Orientation.VERTICAL), tbtnOutil, tbtnLivre, tbtnJeu,
                new Separator(Orientation.VERTICAL), new Label("Filtre"), choixEtat, txfRecherche
        );
    }

    private void genererBarreHaut() {
        barreHaut = new VBox();
        //todo fichier
    }

    private void genererTableau() {
        //todo tvObjets
    }
}