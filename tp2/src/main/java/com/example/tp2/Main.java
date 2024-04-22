package com.example.tp2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    GridPane gpContenu;
    GridPane gpSectionOutil;
    GridPane gpSectionJeu;

    final NouvelObjetHaut sectionHautNouvelObjet = new NouvelObjetHaut();
    final NouvelObjetBas sectionBasNouvelObjet = new NouvelObjetBas();
    final SectionGenerale sectionGenerale = new SectionGenerale();
    final SectionLivre sectionLivre = new SectionLivre();
    final SectionOutil sectionOutil = new SectionOutil();
    final SectionJeu sectionJeu = new SectionJeu();



    ObservableList<Objet> objets = FXCollections.observableArrayList();

    public Main() {


        //genererTableView();
        preparerBarreOutils();
        preparerBarreFichier();
        preparerBarreHaut();

        preparerTableView();
        vbMenuGauche = new VBox();
        vbMenuGauche.setMinWidth(300);
    }


    @Override
    public void start(Stage stage) throws IOException {
        BorderPane root = new BorderPane();

        root.setTop(vbBarreHaut);
        root.setCenter(tvObjets);
        root.setRight(vbMenuGauche);

        vbMenuGauche.getChildren().add(sectionHautNouvelObjet.recharger());
        vbMenuGauche.getChildren().add(sectionBasNouvelObjet.recharger());
        //vbMenuGauche.getChildren().add(sectionGenerale.getContenu());
        //vbMenuGauche.getChildren().add(sectionLivre.getContenu());
        //vbMenuGauche.getChildren().add(sectionOutil.getContenu());

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

        ChoiceBox<String> cbEtat = new ChoiceBox<String>(
                FXCollections.observableArrayList("Tous", "En possession", "Prêté", "Perdu")
        );
        cbEtat.getSelectionModel().select(0);

        txfRecherche = new TextField();

        txfRecherche.setPromptText("Recherche");

        btnAjouterObjet.setOnAction(e -> nouvelObjet());
        btnSupprimerObjet.setOnAction(e -> supprimerObjet());

        hbBarreOutils.getChildren().addAll(
                btnAjouterObjet, btnSupprimerObjet, new Separator(Orientation.VERTICAL), tbtnOutil, tbtnLivre, tbtnJeu,
                new Separator(Orientation.VERTICAL), new Label("Filtre"), cbEtat, txfRecherche
        );
    }

    private void preparerBarreHaut() {
        vbBarreHaut = new VBox();

        vbBarreHaut.setSpacing(5);
        //todo remove barreHaut.setPadding(new Insets(0, 0, 5, 0));

        vbBarreHaut.getChildren().addAll(mbFichier, hbBarreOutils, new Separator(Orientation.HORIZONTAL));
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



    private void preparerTableView() {
        tvObjets = new TableView<Objet>();

        TableColumn<Objet, String> tcNom = new TableColumn<Objet, String>("Nom");
        TableColumn<Objet, String> tcDescription = new TableColumn<Objet, String>("Description");
        //TableColumn<Objet,> tcEtat = new TableColumn();
        TableColumn<Objet, LocalDate> tcDateAchat = new TableColumn<Objet, LocalDate>("Date d'achat");
        TableColumn<Objet, String> tcPrix = new TableColumn<Objet, String>("Prix");

        tcNom.setCellValueFactory(new PropertyValueFactory<Objet, String>("nom"));
        tcDescription.setCellValueFactory(new PropertyValueFactory<Objet, String>("description"));
        tcDateAchat.setCellValueFactory(new PropertyValueFactory<Objet, LocalDate>("dateAchat"));
        tcPrix.setCellValueFactory(new PropertyValueFactory<Objet, String>("prix"));

        tvObjets.getColumns().addAll(tcNom, tcDescription, tcDateAchat, tcPrix);

        tvObjets.setItems(objets);
    }

    private static class SectionGenerale {
        private final GridPane gpContenu = new GridPane();

        private final TextField txfNom = new TextField();
        private final TextField txfPrix = new TextField();
        private final Spinner<Integer> spQuantite = new Spinner<>();
        private final DatePicker dpAchat = new DatePicker();
        private final ChoiceBox<String> cbEtat = new ChoiceBox<String>(
                FXCollections.observableArrayList("En possession", "Prêté", "Perdu")
        );
        private final TextField txfEmplacement = new TextField();
        private File facture;
        private SectionGenerale() {
            spQuantite.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE) {
            });

            Label entete = new Label("Secton générale");
            entete.setFont(new Font(20));

            gpContenu.add(entete, 0, 0, 2, 1);

            gpContenu.add(new Label("Nom:"), 0, 1);
            gpContenu.add(new Label("Prix:"), 0, 2);
            gpContenu.add(new Label("Quantité:"), 0, 3);
            gpContenu.add(new Label("Date d'achat:"), 0, 4);
            gpContenu.add(new Label("Image facture:"), 0, 5);
            gpContenu.add(new Label("État:"), 0, 6);
            gpContenu.add(new Label("Emplacement:"), 0, 7);

            gpContenu.add(txfNom, 1, 1);
            gpContenu.add(txfPrix, 1, 2);
            gpContenu.add(spQuantite, 1, 3);
            gpContenu.add(dpAchat, 1, 4);
            //gpSectionGenerale.add(selecteurFacture(),1,5);
            gpContenu.add(cbEtat, 1, 6);
            gpContenu.add(txfEmplacement, 1, 7);
        }

        private GridPane recharger() {
            txfNom.clear();
            txfPrix.clear();
            spQuantite.getValueFactory().setValue(1);
            dpAchat.getEditor().clear();
            cbEtat.getSelectionModel().select(-1);
            txfEmplacement.clear();
            //todo facture

            return gpContenu;
        }

        private String getNom() {
            return txfNom.getText();
        }

        private String getPrix() {
            return txfPrix.getText();
        }

        private int getQuantite() {
            return spQuantite.getValue();
        }

        private LocalDate getDateAchat() {
            return dpAchat.getValue();
        }

        private File getFacture() {
            return facture;
        }

        private int getNumeroEtat() {
            return cbEtat.getSelectionModel().getSelectedIndex();
        }

        private String getEmplacement() {
            return txfEmplacement.getText();
        }
    }

    private static class SectionLivre {
        private final GridPane gpContenu = new GridPane();;

        private final TextField txfAuteur = new TextField();
        private final TextField txfMaisonEdition = new TextField();
        private final TextField txfAnneeEcrture = new TextField();
        private final TextField txfAnneePublication = new TextField();
        private SectionLivre() {
            Label entete = new Label("Secton livre");
            entete.setFont(new Font(20));

            gpContenu.add(entete, 0, 0, 2, 1);

            gpContenu.add(new Label("Auteur:"), 0, 1);
            gpContenu.add(new Label("Maison d'édition:"), 0, 2);
            gpContenu.add(new Label("Année d'écriture:"), 0, 3);
            gpContenu.add(new Label("Année de publication:"), 0, 4);

            gpContenu.add(txfAuteur, 1, 1);
            gpContenu.add(txfMaisonEdition, 1, 2);
            gpContenu.add(txfAnneeEcrture, 1, 3);
            gpContenu.add(txfAnneePublication, 1, 4);
        }

        private GridPane recharger() {
            txfAuteur.clear();
            txfMaisonEdition.clear();
            txfAnneeEcrture.clear();
            txfAnneePublication.clear();

            return gpContenu;
        }
        private String getAuteur() {
            return txfAuteur.getText();
        }
        private String getMaisonEdition() {
            return txfMaisonEdition.getText();
        }
        private short getAnneeEcriture() {
            return Short.parseShort(txfAnneeEcrture.getText());
        }
        private short getAnneePublication() {
            return Short.parseShort(txfAnneePublication.getText());
        }

    }

    private static class SectionOutil {
        private final GridPane gpContenu = new GridPane();
        private final TextField txfmarque = new TextField();
        private final TextField txfNumeroSerie = new TextField();
        private SectionOutil() {
            Label entete = new Label("Secton outil");
            entete.setFont(new Font(20));

            gpContenu.add(entete, 0, 0, 2, 1);

            gpContenu.add(new Label("Marque:"), 0, 1);
            gpContenu.add(new Label("Numéro de série:"), 0, 2);

            gpContenu.add(txfmarque, 1, 1);
            gpContenu.add(txfNumeroSerie, 1, 2);
        }

        private GridPane recharger() {
            txfmarque.clear();
            txfNumeroSerie.clear();

            return gpContenu;
        }

        private String getMarque() {
            return txfmarque.getText();
        }

        private int getNumeroSerie() {
            return Short.parseShort(txfNumeroSerie.getText());
        }
    }

    private static class SectionJeu {
        private final GridPane gpContenu = new GridPane();

        private final TextField txfConsole = new TextField();
        private final TextField txfNbJoueurs = new TextField();
        private final TextField txfDeveloppement = new TextField();
        private final TextField txfAnneeSortie = new TextField();
        private SectionJeu() {
            Label entete = new Label("Secton jeu");
            entete.setFont(new Font(20));

            gpContenu.add(entete, 0, 0, 2, 1);

            gpContenu.add(new Label("Console:"), 0, 1);
            gpContenu.add(new Label("Nombre de joueurs:"), 0, 2);
            gpContenu.add(new Label("Développement:"), 0, 3);
            gpContenu.add(new Label("Année de sortie:"), 0, 4);

            gpContenu.add(txfConsole, 1, 1);
            gpContenu.add(txfNbJoueurs, 1, 2);
            gpContenu.add(txfDeveloppement, 1, 3);
            gpContenu.add(txfAnneeSortie, 1, 4);
        }

        private GridPane recharger() {
            txfConsole.clear();
            txfNbJoueurs.clear();
            txfDeveloppement.clear();
            txfAnneeSortie.clear();

            return gpContenu;
        }
        private String getConsole() {
            return txfConsole.getText();
        }
        private int getNbJoueurs() {
            return Integer.parseInt(txfNbJoueurs.getText());
        }
        private String getDeveloppement() {
            return txfDeveloppement.getText();
        }
        private short getAnneeSortie() {
            return Short.parseShort(txfAnneeSortie.getText());
        }
    }

    private class NouvelObjetHaut {
        private final GridPane gpContenu = new GridPane();
        private final ChoiceBox<String> cbTypeObjet = new ChoiceBox<String>(
                FXCollections.observableArrayList("Livre", "Outil", "Jeu")
        );

        private NouvelObjetHaut() {
            Label entete = new Label("Nouvel objet d'inventaire");

            cbTypeObjet.setOnAction(e -> chargerSection(cbTypeObjet.getSelectionModel().getSelectedIndex()));

            entete.setFont(new Font(20));

            gpContenu.add(entete, 0, 0, 2, 1);

            gpContenu.add(new Label("Type d'objet:"), 0, 1);

            gpContenu.add(cbTypeObjet, 1, 1);
        }
        private GridPane recharger() {
            cbTypeObjet.getSelectionModel().select(-1);
            return gpContenu;
        }
        private int getSelection() {
            return cbTypeObjet.getSelectionModel().getSelectedIndex();
        }
    }

    private class NouvelObjetBas {
        private final HBox hbContenu = new HBox();
        private final Button btnAjouter = new Button("Ajouter");
        private NouvelObjetBas() {
            Button btnAnnuler = new Button("Annuler");

            btnAjouter.setOnAction(e -> creerObjetSelonType());
            btnAnnuler.setOnAction(e -> vbMenuGauche.getChildren().clear());

            hbContenu.getChildren().addAll(btnAjouter, btnAnnuler);
        }

        private HBox recharger() {
            btnAjouter.setDisable(true);
            return hbContenu;
        }
    }

    private void chargerSection(int numeroSection) {
        sectionBasNouvelObjet.btnAjouter.setDisable(false);

        if (vbMenuGauche.getChildren().size() == 2) {
            vbMenuGauche.getChildren().add(1, sectionGenerale.recharger());

            switch (numeroSection) {
                case 0 -> {
                    vbMenuGauche.getChildren().add(2, sectionLivre.recharger());
                }
                case 1 -> {
                    vbMenuGauche.getChildren().add(2, sectionOutil.recharger());
                }
                default -> {
                    vbMenuGauche.getChildren().add(2, sectionJeu.recharger());
                }
            }
        }
        else {

            switch (numeroSection) {
                case 0 -> {
                    vbMenuGauche.getChildren().set(2, sectionLivre.recharger());
                }
                case 1 -> {
                    vbMenuGauche.getChildren().set(2, sectionOutil.recharger());
                }
                case 2 -> {
                    vbMenuGauche.getChildren().set(2, sectionJeu.recharger());
                }
            }
        }
    }

    private void creerObjetSelonType() {
        switch (sectionHautNouvelObjet.getSelection()) {
            case 0 -> {
                Livre livre = new Livre();

                livre.setAuteur(sectionLivre.getAuteur());
                livre.setMaisonEdition(sectionLivre.getMaisonEdition());
                livre.setAnneeEcriture(sectionLivre.getAnneeEcriture());
                livre.setAnneePublication(sectionLivre.getAnneePublication());

                terminerCreation(livre);
            }

            case 1 -> {
                Outil outil = new Outil();

                outil.setMarque(sectionOutil.getMarque());
                outil.setNumeroSerie(sectionOutil.getNumeroSerie());

                terminerCreation(outil);
            }

            default -> {
                Jeu jeu = new Jeu();

                jeu.setConsole(sectionJeu.getConsole());
                jeu.setNbJoueurs(sectionJeu.getNbJoueurs());
                jeu.setDeveloppement(sectionJeu.getDeveloppement());
                jeu.setAnneeSortie(sectionJeu.getAnneeSortie());

                terminerCreation(jeu);
            }
        }
    }

    private void terminerCreation(Objet objet) {
        objet.setNom(sectionGenerale.getNom());
        objet.setPrix(sectionGenerale.getPrix());
        objet.setQuantite(sectionGenerale.getQuantite());
        objet.setDateAchat(sectionGenerale.getDateAchat());
        //todo setfacture
        switch (sectionGenerale.getNumeroEtat()) {
            case 0 -> {
                objet.setEtat(Objet.etat.EN_POSSESSION);
            }
            case 1 -> {
                objet.setEtat(Objet.etat.PRETE);
            }
            default -> {
                objet.setEtat(Objet.etat.PERDU);
            }
        }
        objet.setEmplacement(sectionGenerale.getEmplacement());

        objets.add(objet);

        vbMenuGauche.getChildren().clear();
    }

    private void supprimerObjet() {
        int selection = tvObjets.getSelectionModel().getSelectedIndex();

        if (selection < 0) {
            if (objets.isEmpty()) {
                afficherErreur(
                        "Aucun objet à supprimer",
                        "Impossible de supprimer un objet car la liste est vide."
                );
            }
            else {
                afficherErreur(
                        "Aucun objet sélectionné",
                        "Veuillez sélectionner un objet à supprimer dans la liste."
                );
            }
        }
        else {
            objets.remove(tvObjets.getSelectionModel().getSelectedIndex());
        }
    }

    private void afficherErreur(String entete, String details) {
        Alert alert = new Alert(Alert.AlertType.ERROR, details);
        alert.setHeaderText(entete);
        alert.showAndWait();
    }

    private void nouvelObjet() {
        vbMenuGauche.getChildren().clear();
        vbMenuGauche.getChildren().addAll(sectionHautNouvelObjet.recharger(), sectionBasNouvelObjet.recharger());
    }
}