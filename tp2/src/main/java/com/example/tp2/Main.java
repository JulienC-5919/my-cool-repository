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
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class Main extends Application {

    TableView<Objet> tvObjets;

    HBox hbBarreOutils;
    VBox vbBarreHaut;
    VBox vbMenuGauche;

    TextField txfRecherche;

    MenuBar mbFichier;
    GridPane gpSectionGenerale;
    GridPane gpSectionLivre;
    GridPane gpSectionOutil;
    GridPane gpSectionJeu;

    TextField taNom;
    TextField taPrix;
    Spinner<Integer> spQuantite;
    DatePicker dpAchat;
    File facture;

    ObservableList<Objet> objets = FXCollections.observableArrayList();

    public Main() {


        //genererTableView();
        preparerBarreOutils();
        preparerBarreFichier();
        preparerBarreHaut();

        preparerTableView();

        vbMenuGauche = new VBox();
        preparerChampsSectionGenerale();
        preparerSectionGenerale();
    }


    @Override
    public void start(Stage stage) throws IOException {
        BorderPane root = new BorderPane();

        root.setTop(vbBarreHaut);
        root.setLeft(tvObjets);
        root.setCenter(new Separator(Orientation.VERTICAL));
        root.setRight(vbMenuGauche);

        vbMenuGauche.getChildren().add(gpSectionGenerale);

        Scene scene = new Scene(root, 500, 240);
        stage.setTitle("2268130 TP2");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void preparerBarreOutils() {
        hbBarreOutils = new HBox();

        hbBarreOutils.setSpacing(5);

        Button btnAjouterObjet = new Button("➕");
        Button btnSupprimerObjet = new Button("➖");

        ToggleButton tbtnOutil = new ToggleButton("\uD83D\uDD27");
        ToggleButton tbtnLivre = new ToggleButton("\uD83D\uDCD5");
        ToggleButton tbtnJeu = new ToggleButton("\uD83C\uDFAE");

        ChoiceBox<String> choixEtat = new ChoiceBox<String>();
        choixEtat.getItems().addAll("Tous", "En possession", "Prêté", "Perdu");

        txfRecherche = new TextField();
        txfRecherche.setPromptText("Recherche");

        hbBarreOutils.getChildren().addAll(
                btnAjouterObjet, btnSupprimerObjet, new Separator(Orientation.VERTICAL), tbtnOutil, tbtnLivre, tbtnJeu,
                new Separator(Orientation.VERTICAL), new Label("Filtre"), choixEtat, txfRecherche
        );
    }

    private void preparerBarreHaut() {
        vbBarreHaut = new VBox();

        vbBarreHaut.setSpacing(5);
        //todo remove barreHaut.setPadding(new Insets(0, 0, 5, 0));

        vbBarreHaut.getChildren().add(mbFichier);
        vbBarreHaut.getChildren().add(hbBarreOutils);

    }

    private void preparerBarreFichier() {
        mbFichier = new MenuBar();
        Menu menuFichier = new Menu("Fichier");

        MenuItem miOuvrir = new MenuItem("Ouvrir");
        MenuItem miSauvegarder = new MenuItem("Sauvegarder");
        MenuItem miSauvegarderSous = new MenuItem("Sauvegarder sous");
        MenuItem miExporter = new MenuItem("Exporter");

        //todo ajouter actions



        menuFichier.getItems().addAll(miOuvrir, miSauvegarder, miSauvegarderSous, miExporter);
        mbFichier.getMenus().add(menuFichier);
    }

    private void preparerSectionGenerale() {
        gpSectionGenerale = new GridPane();

        Label entete = new Label("Secton générale");
        entete.setFont(new Font(20));

        gpSectionGenerale.add(entete, 0, 0, 2, 1);

        gpSectionGenerale.add(new Label("Nom:"), 0, 1);
        gpSectionGenerale.add(new Label("Prix:"), 0, 2);
        gpSectionGenerale.add(new Label("Quantité:"), 0, 3);
        gpSectionGenerale.add(new Label("Date d'achat:"), 0, 4);
        gpSectionGenerale.add(new Label("Image facture:"), 0, 5);
        gpSectionGenerale.add(new Label("État:"), 0, 6);
        gpSectionGenerale.add(new Label("Emplacement:"), 0, 7);

        gpSectionGenerale.add(taNom, 1, 1);
        gpSectionGenerale.add(taPrix, 1, 2);
        gpSectionGenerale.add(spQuantite, 1, 3);
        gpSectionGenerale.add(dpAchat, 1, 4);
        //gpSectionGenerale.add(selecteurFacture(),1,5);
        //gpSectionGenerale.add(ChoiceBoxEtat(), 1, 6);
    }



    private void preparerChampsSectionGenerale() {
        taNom = new TextField();

        spQuantite = new Spinner<>();
        spQuantite.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE) {
        });

        taPrix = new TextField();
        dpAchat = new DatePicker();
    }

    private void preparerSectionLivre() {
        gpSectionLivre = new GridPane();

        Label entete = new Label("Secton livre");
        entete.setFont(new Font(20));

        gpSectionGenerale.add(entete, 0, 0, 2, 1);

        gpSectionLivre.add(new Label("Auteur:"), 0, 1);
        gpSectionLivre.add(new Label("Maison d'édition:"), 0, 2);
        gpSectionLivre.add(new Label("Année d'écriture:"), 0, 3);
        gpSectionLivre.add(new Label("Année de publication:"), 0, 4);
    }

    private void preparerTableView() {
        tvObjets = new TableView<Objet>();

        TableColumn<Objet, String> tcNom = new TableColumn("Nom");
        TableColumn<Objet, String> tcDescription = new TableColumn("Description");
        //TableColumn<Objet> tcEtat = new TableColumn();
        TableColumn<Objet, LocalDate> tcDateAchat = new TableColumn("Date d'achat");
        TableColumn<Objet, String> tcPrix = new TableColumn("Prix");

        tcNom.setCellValueFactory(new PropertyValueFactory<Objet, String>("nom"));
        tcDescription.setCellValueFactory(new PropertyValueFactory<Objet, String>("description"));
        tcDateAchat.setCellValueFactory(new PropertyValueFactory<Objet, LocalDate>("dateAchat"));
        tcPrix.setCellValueFactory(new PropertyValueFactory<Objet, String>("prix"));

        tvObjets.getColumns().addAll(tcNom, tcDescription, tcDateAchat, tcPrix);

        tvObjets.setItems(objets);

        Livre tmp = new Livre();
    }
}