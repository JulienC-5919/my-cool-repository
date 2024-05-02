package com.example.tp2;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class Main extends Application {

    // false si des modifications non sauvegardées risquent d'être perdues accidentellement.
    private boolean sauvegarde = true;
    private Stage stage;
    private TableView<Objet> tvObjets;

    private HBox hbBarreOutils;
    private VBox vbBarreHaut;
    private final VBox vbMenuGauche;

    private TextField txfRecherche;

    private MenuBar mbFichier;

    private final NouvelObjetHaut sectionHautNouvelObjet = new NouvelObjetHaut();
    private final NouvelObjetBas sectionBasNouvelObjet = new NouvelObjetBas();
    private final SectionGenerale sectionGenerale = new SectionGenerale();
    private final SectionLivre sectionLivre = new SectionLivre();
    private final SectionOutil sectionOutil = new SectionOutil();
    private final SectionJeu sectionJeu = new SectionJeu();

    private ListesObjets listesObjets = new ListesObjets();

    private final ToggleButton tbtnOutil = new ToggleButton("\uD83D\uDD27");
    private final ToggleButton tbtnLivre = new ToggleButton("\uD83D\uDCD5");
    private final ToggleButton tbtnJeu = new ToggleButton("\uD83C\uDFAE");

    private final MenuItem miRecharger = new MenuItem("Recharger");

    private final ChoiceBox<String> cbEtat = new ChoiceBox<>(
            FXCollections.observableArrayList("Tous", "En possession", "Prêté", "Perdu")
    );

    private final BorderPane root = new BorderPane();

    private File fichier = null;
    private final FileChooser fileChooser = new FileChooser();

    private final Label labObjetInventaire = new Label("Objet d'inventaire");
    private final HBox hbModifierObjet = new HBox();

    ObservableList<Objet> objets = FXCollections.observableArrayList();

    public Main() {
        preparerBarreOutils();
        preparerBarreFichier();
        preparerBarreHaut();
        preparerSectionsGauche();

        preparerTableView();
        vbMenuGauche = new VBox();
        vbMenuGauche.setMinWidth(300);

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier DAT (*.dat)","*.dat"));
    }


    @Override
    public void start(Stage stage) {
        this.stage = stage;

        stage.setOnCloseRequest(event -> {
            confirmerSauvegarder();
            if (!sauvegarde) {
                event.consume();
            }
        });

        root.setMinHeight(650);
        root.setMinWidth(1000);

        root.setTop(vbBarreHaut);
        root.setCenter(tvObjets);
        root.setRight(vbMenuGauche);

        stage.setMinWidth(1000);
        stage.setMinHeight(650);

        Scene scene = new Scene(root);

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

        txfRecherche = new TextField();

        cbEtat.getSelectionModel().select(0);

        tbtnLivre.setSelected(true);
        tbtnOutil.setSelected(true);
        tbtnJeu.setSelected(true);

        tbtnLivre.setOnAction(e -> rechargerObjets());
        tbtnOutil.setOnAction(e -> rechargerObjets());
        tbtnJeu.setOnAction(e -> rechargerObjets());

        cbEtat.setOnAction(e -> rechargerObjets());
        
        txfRecherche.textProperty().addListener(e -> rechargerObjets());
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
        MenuItem miNouveau = new MenuItem("Nouveau");

        //todo ajouter actions
        miOuvrir.setOnAction(e -> ouvrirFichier());

        miSauvegarder.setOnAction(e -> miSauvegarderAction());
        miSauvegarderSous.setOnAction(e -> sauvegarderSous());
        miExporter.setOnAction(e -> exporter());

        miNouveau.setOnAction(e -> {
            confirmerSauvegarder();
            if (sauvegarde) {
                reinitialiser();
            }
        });

        miRecharger.setOnAction(e -> confirmerRecharger());

        miRecharger.setDisable(true);

        menuFichier.getItems().addAll(miOuvrir, miSauvegarder, miSauvegarderSous, miExporter, miNouveau, miRecharger);
        mbFichier.getMenus().add(menuFichier);
    }



    private void preparerTableView() {
        tvObjets = new TableView<Objet>();

        TableColumn<Objet, String> tcNom = new TableColumn<Objet, String>("Nom");
        TableColumn<Objet, String> tcDescription = new TableColumn<Objet, String>("Description");
        TableColumn<Objet, String> tcEtat = new TableColumn(); //todo état
        TableColumn<Objet, LocalDate> tcDateAchat = new TableColumn<Objet, LocalDate>("Date d'achat");
        TableColumn<Objet, String> tcPrix = new TableColumn<Objet, String>("Prix");

        tcNom.setCellValueFactory(new PropertyValueFactory<Objet, String>("nom"));
        tcDescription.setCellValueFactory(new PropertyValueFactory<Objet, String>("description"));
        tcDateAchat.setCellValueFactory(new PropertyValueFactory<Objet, LocalDate>("dateAchat"));
        tcPrix.setCellValueFactory(new PropertyValueFactory<Objet, String>("prix"));

        tvObjets.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                int index = t1.intValue();

                if (index >= 0) {
                    afficherObjet(objets.get(index));
                }
            }
        });

        tvObjets.getColumns().addAll(tcNom, tcDescription, tcDateAchat, tcPrix);

        tvObjets.setItems(objets);
    }

    private class SectionGenerale {
        private final GridPane gpContenu = new GridPane();
        private final TextField txfNom = new TextField();
        private final TextField txfPrix = new TextField();
        private final Spinner<Integer> spQuantite = new Spinner<>();
        private final DatePicker dpAchat = new DatePicker();
        private final ChoiceBox<String> cbEtat = new ChoiceBox<String>(
                FXCollections.observableArrayList("En possession", "Prêté", "Perdu")
        );
        private final TextField txfEmplacement = new TextField();

        private final ImageView ivFacture = new ImageView();

        private byte[] octetsImageFacture;

        private SectionGenerale() {
            HBox hbSelecteurFacture = new HBox();
            StackPane spCadreFacture = new StackPane();

            Button btnChoisirFacture = new Button("\uD83D\uDCC4");

            spQuantite.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE) {
            });

            Label entete = new Label("Secton générale");
            entete.setFont(new Font(20));

            bordureGrise(spCadreFacture);

            ivFacture.setFitWidth(100);
            ivFacture.setFitHeight(100);
            ivFacture.setPreserveRatio(true);

            CorrecteursTextFields.ajouterCorrecteurPrix(txfPrix);

            btnChoisirFacture.setOnAction(e -> choisirFacture());
            ivFacture.setOnMouseClicked(e -> afficherFacture());
            ivFacture.hoverProperty().addListener(e -> {
                if (ivFacture.isHover()) {
                    bordureBleue(spCadreFacture);
                }
                else {
                    bordureGrise(spCadreFacture);
                }
            }
            );

            spCadreFacture.getChildren().add(ivFacture);
            spQuantite.setEditable(true);
            //todo spQuantite.getEditor().textProperty().addListener(e -> System.out.println(true));

            hbSelecteurFacture.getChildren().addAll(spCadreFacture, btnChoisirFacture);

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
            gpContenu.add(hbSelecteurFacture,1,5);
            gpContenu.add(cbEtat, 1, 6);
            gpContenu.add(txfEmplacement, 1, 7);
        }

        private GridPane charger() {
            txfNom.clear();
            txfPrix.clear();
            spQuantite.getValueFactory().setValue(1);
            dpAchat.getEditor().clear();
            cbEtat.getSelectionModel().select(-1);
            txfEmplacement.clear();
            ivFacture.setImage(null);

            return gpContenu;
        }

        private GridPane charger(Objet objet) {

            octetsImageFacture = objet.getOctetsImageFacture();

            txfNom.setText(objet.getNom());
            txfPrix.setText(objet.getPrix());
            spQuantite.getValueFactory().setValue(objet.getQuantite());
            dpAchat.setValue(objet.getDateAchat());
            //todo cbEtat.getSelectionModel().select(objet.getEtat().);
            txfEmplacement.setText(objet.getEmplacement());

            ivFacture.setImage(new Image(new ByteArrayInputStream(octetsImageFacture)));

            return gpContenu;
        }

        private void choisirFacture() {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Tous les fichiers images", "*.bmp", "*.gif", "*.jpeg", "*.png"),
                    new FileChooser.ExtensionFilter("BMP (*.bmp)", "*.bmp"),
                    new FileChooser.ExtensionFilter("GIF (*.gif)", "*.gif"),
                    new FileChooser.ExtensionFilter("JPEG (*.jpeg)", "*.jpeg"),
                    new FileChooser.ExtensionFilter("PNG (*.png)", "*.png"),
                    new FileChooser.ExtensionFilter("Tous les fichiers", "*")
            );
            try {
                File fichierChoisi = fileChooser.showOpenDialog(stage);

                if (fichierChoisi != null) {
                    octetsImageFacture = new FileInputStream(fichierChoisi).readAllBytes();
                    ivFacture.setImage(new Image(new ByteArrayInputStream(octetsImageFacture)));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void afficherFacture() {
            StackPane rootFacture = new StackPane();
            ImageView imageFacture;

            imageFacture = new ImageView(new Image(new ByteArrayInputStream(octetsImageFacture)));

            Scene scene = new Scene(rootFacture);
            Stage stage = new Stage();

            imageFacture.setFitWidth(600);
            imageFacture.setFitHeight(600);
            imageFacture.setPreserveRatio(true);

            rootFacture.getChildren().add(imageFacture);

            stage.setScene(scene);
            stage.setResizable(false);

            root.setDisable(true);
            stage.showAndWait();
            root.setDisable(false);
        }

        private void bordureGrise(Pane panneau) {
            panneau.setBorder(new Border(
                    new BorderStroke(Color.LIGHTGRAY,
                            BorderStrokeStyle.SOLID,
                            CornerRadii.EMPTY,
                            new BorderWidths(2))
            ));
        }
        private void bordureBleue(Pane panneau) {
            panneau.setBorder(new Border(
                    new BorderStroke(Color.DODGERBLUE,
                            BorderStrokeStyle.SOLID,
                            CornerRadii.EMPTY,
                            new BorderWidths(2))
            ));
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

        private byte[] getOctetsImageFacture() {
            return octetsImageFacture;
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

        private GridPane charger() {
            txfAuteur.clear();
            txfMaisonEdition.clear();
            txfAnneeEcrture.clear();
            txfAnneePublication.clear();

            return gpContenu;
        }
        private GridPane charger(Livre livre) {
            txfAuteur.setText(livre.getAuteur());
            txfMaisonEdition.setText(livre.getAuteur());
            txfAnneeEcrture.setText(String.valueOf(livre.getAnneeEcriture()));
            txfAnneePublication.setText(String.valueOf(livre.getAnneePublication()));

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
        private final TextField txfMarque = new TextField();
        private final TextField txfModele = new TextField();
        private final TextField txfNumeroSerie = new TextField();
        private SectionOutil() {
            Label entete = new Label("Secton outil");
            entete.setFont(new Font(20));

            gpContenu.add(entete, 0, 0, 2, 1);

            gpContenu.add(new Label("Marque:"), 0, 1);
            gpContenu.add(new Label("Modèle:"), 0, 2);
            gpContenu.add(new Label("Numéro de série:"), 0, 3);

            gpContenu.add(txfMarque, 1, 1);
            gpContenu.add(txfModele, 1, 2);
            gpContenu.add(txfNumeroSerie, 1, 3);
        }

        private GridPane charger() {
            txfMarque.clear();
            txfNumeroSerie.clear();

            return gpContenu;
        }

        private GridPane charger(Outil outil) {
            txfMarque.setText(outil.getMarque());
            txfNumeroSerie.setText(String.valueOf(outil.getNumeroSerie()));
            txfMarque.setText(outil.getMarque());

            return gpContenu;
        }

        private String getMarque() {
            return txfMarque.getText();
        }

        private int getNumeroSerie() {
            return Short.parseShort(txfNumeroSerie.getText());
        }

        private String getModele() {
            return txfModele.getText();
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

            //todo spinner joueurs

            gpContenu.add(entete, 0, 0, 2, 1);

            gpContenu.add(new Label("Console:"), 0, 1);
            gpContenu.add(new Label("Nombre de joueurs:"), 0, 2);
            gpContenu.add(new Label("Développement:"), 0, 3);
            gpContenu.add(new Label("Année de sortie:"), 0, 4);

            gpContenu.add(txfConsole, 1, 1);
            gpContenu.add(txfNbJoueurs, 1, 2);
            gpContenu.add(txfDeveloppement, 1, 3);
            gpContenu.add(txfAnneeSortie, 1, 4);
            txfConsole.focusedProperty().addListener(new ChangeListener<Boolean>() {//todo remove
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                    System.out.println("rrrrrrrrr");
                }
            });
        }

        private GridPane recharger() {
            txfConsole.clear();
            txfNbJoueurs.clear();
            txfDeveloppement.clear();
            txfAnneeSortie.clear();

            return gpContenu;
        }

        private GridPane charger(Jeu jeu) {
            txfConsole.setText(jeu.getConsole());
            txfNbJoueurs.setText(String.valueOf(jeu.getNbJoueurs()));
            txfDeveloppement.setText(String.valueOf(jeu.getDeveloppement()));
            txfAnneeSortie.setText(String.valueOf(jeu.getanneeSortie()));

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
        private GridPane charger() {
            cbTypeObjet.getSelectionModel().select(-1);
            return gpContenu;
        }
        private int getSelection() {
            return cbTypeObjet.getSelectionModel().getSelectedIndex();
        }
    }
    //todo aucune valeur à aller chercher, convertir en variable?
    private class NouvelObjetBas {
        private final HBox hbContenu = new HBox();
        private final Button btnAjouter = new Button("Ajouter");
        private NouvelObjetBas() {
            Button btnAnnuler = new Button("Annuler");

            btnAjouter.setOnAction(e -> creerObjetSelonType());
            btnAnnuler.setOnAction(e -> vbMenuGauche.getChildren().clear());

            hbContenu.getChildren().addAll(btnAjouter, btnAnnuler);
        }

        private HBox charger() {
            btnAjouter.setDisable(true);
            return hbContenu;
        }
    }

    private void chargerSection(int numeroSection) {
        sectionBasNouvelObjet.btnAjouter.setDisable(false);

        if (vbMenuGauche.getChildren().size() == 2) {
            vbMenuGauche.getChildren().add(1, sectionGenerale.charger());

            switch (numeroSection) {
                case 0 -> {
                    vbMenuGauche.getChildren().add(2, sectionLivre.charger());
                }
                case 1 -> {
                    vbMenuGauche.getChildren().add(2, sectionOutil.charger());
                }
                default -> {
                    vbMenuGauche.getChildren().add(2, sectionJeu.recharger());
                }
            }
        }
        else {

            switch (numeroSection) {
                case 0 -> {
                    vbMenuGauche.getChildren().set(2, sectionLivre.charger());
                }
                case 1 -> {
                    vbMenuGauche.getChildren().set(2, sectionOutil.charger());
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

                remplirValeursObjet(livre);

                remplirValeursLivre(livre);

                listesObjets.livres.add(livre);
            }

            case 1 -> {
                Outil outil = new Outil();

                remplirValeursObjet(outil);

                remplirValeursOutil(outil);

                listesObjets.outils.add(outil);
            }

            default -> {
                Jeu jeu = new Jeu();

                remplirValeursObjet(jeu);

                remplirValeursJeu(jeu);

                listesObjets.jeux.add(jeu);
            }
        }

        rechargerObjets();
        sauvegarde = false;
    }

    private static class ListesObjets implements Serializable {
        public final ArrayList<Livre> livres = new ArrayList<>();
        public final ArrayList<Outil> outils = new ArrayList<>();
        public final ArrayList<Jeu> jeux = new ArrayList<>();
    }

    private void remplirValeursObjet(Objet objet) {
        objet.setNom(sectionGenerale.getNom());
        objet.setPrix(sectionGenerale.getPrix());
        objet.setQuantite(sectionGenerale.getQuantite());
        objet.setDateAchat(sectionGenerale.getDateAchat());
        objet.setOctetsImageFacture(sectionGenerale.getOctetsImageFacture());
        
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

        vbMenuGauche.getChildren().clear();
    }

    private void remplirValeursOutil(Outil outil) {
        outil.setMarque(sectionOutil.getMarque());
        outil.setModele(sectionOutil.getModele());
        outil.setNumeroSerie(sectionOutil.getNumeroSerie());
    }

    private void remplirValeursLivre(Livre livre) {
        livre.setAuteur(sectionLivre.getAuteur());
        livre.setMaisonEdition(sectionLivre.getMaisonEdition());
        livre.setAnneeEcriture(sectionLivre.getAnneeEcriture());
        livre.setAnneePublication(sectionLivre.getAnneePublication());
    }

    private void remplirValeursJeu(Jeu jeu) {
        jeu.setConsole(sectionJeu.getConsole());
        jeu.setNbJoueurs(sectionJeu.getNbJoueurs());
        jeu.setDeveloppement(sectionJeu.getDeveloppement());
        jeu.setAnneeSortie(sectionJeu.getAnneeSortie());
    }

    private void supprimerObjet() {
        Objet objet; //Objet à supprimer
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
            objet = objets.get(tvObjets.getSelectionModel().getSelectedIndex());

            if (objet.getClass() == Livre.class) {
                listesObjets.livres.remove(objet);
            }
            else if (objet.getClass() == Outil.class) {
                listesObjets.outils.remove(objet);
            }
            else {
                listesObjets.jeux.remove(objet);
            }

            rechargerObjets();
            sauvegarde = false;
        }
    }

    private void afficherErreur(String entete, String details) {
        Alert alert = new Alert(Alert.AlertType.ERROR, details);
        alert.setHeaderText(entete);
        alert.showAndWait();
    }

    private void nouvelObjet() {
        vbMenuGauche.getChildren().clear();
        vbMenuGauche.getChildren().addAll(sectionHautNouvelObjet.charger(), sectionBasNouvelObjet.charger());
    }

    private void rechargerObjets() {
        objets.clear();

        if (tbtnLivre.isSelected()) {
            listesObjets.livres.forEach(this::chargerSiDansRecherche);
        }
        if (tbtnOutil.isSelected()) {
            listesObjets.outils.forEach(this::chargerSiDansRecherche);
        }
        if (tbtnJeu.isSelected()) {
            listesObjets.jeux.forEach(this::chargerSiDansRecherche);
        }
    }
    private void chargerSiDansRecherche(Objet objet) {
        String recherche = txfRecherche.getText().toLowerCase();

        if (
                objet.getNom().toLowerCase().contains(recherche) ||
                objet.getDateAchat().toString().contains(recherche) ||
                objet.getDescription().toLowerCase().contains(recherche) ||
                objet.getEmplacement().toLowerCase().contains(recherche) ||
                objet.getPrix().contains(recherche) ||
                String.valueOf(objet.getQuantite()).contains(recherche)
        ) {
            switch (cbEtat.getSelectionModel().getSelectedIndex()) {
                case 0 -> {
                    objets.add(objet);
                }
                case 1 -> {
                    if (objet.getEtat() == Objet.etat.EN_POSSESSION) {
                        objets.add(objet);
                    }
                }
                case 2 -> {
                    if (objet.getEtat() == Objet.etat.PRETE) {
                        objets.add(objet);
                    }
                }
                default -> {
                    if (objet.getEtat() == Objet.etat.PERDU) {
                        objets.add(objet);
                    }
                }
            }
        }
    }

    private void sauvegarder() {
        try {
            FileOutputStream fileOut = new FileOutputStream(fichier);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            objectOut.writeObject(listesObjets);

            objectOut.close();
            fileOut.close();
            sauvegarde = true;
            miRecharger.setDisable(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sauvegarderSous() {
        File fichierChoisi;

        fichierChoisi = fileChooser.showSaveDialog(stage);
        if (fichierChoisi != null) {
            fichier = fichierChoisi;
            sauvegarder();
            miRecharger.setDisable(false);
        }
    }

    private void miSauvegarderAction() {
        if (fichier == null) {
            sauvegarderSous();
        }
        else {
            sauvegarder();
        }
    }

    private void confirmerSauvegarder() {
        if (!sauvegarde) {
            ButtonType btnSauvegarder = new ButtonType("Sauvegarder", ButtonBar.ButtonData.YES);
            ButtonType btnPasSauvegarder = new ButtonType("Ne pas sauvegarder", ButtonBar.ButtonData.NO);
            ButtonType btnAnnuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

            Alert fenetreSauvegarder = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Voulez-vous sauvegarder les modifications?",
                    btnSauvegarder,
                    btnPasSauvegarder,
                    btnAnnuler
            );
            fenetreSauvegarder.setTitle("Modifications non sauvegardées");
            fenetreSauvegarder.setHeaderText("Modifications non sauvegardées");

            fenetreSauvegarder.showAndWait().ifPresent(type -> {
                if (type == btnPasSauvegarder) {
                    sauvegarde = true;
                }
                else if (type == btnSauvegarder) {
                    miSauvegarderAction();
                }
            });

        }
    }

    private void ouvrirFichier() {
        File fichierChoisi;

        fichierChoisi = fileChooser.showOpenDialog(stage);

        if (fichierChoisi != null) {
            confirmerSauvegarder();
            if (sauvegarde) {
                fichier = fichierChoisi;
                chargerFichier();
            }
        }
    }
    private void chargerFichier() {
        try {
            FileInputStream fileIn = new FileInputStream(fichier);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            listesObjets = (ListesObjets) objectIn.readObject();
            miRecharger.setDisable(false);

            reinitialiserFiltres();
        } catch (IOException | ClassNotFoundException e) {
            afficherErreur("Erreur d'ouverture","Le ficher est non supporté ou corrompu.");
        }
    }
    private void exporter() {
        //todo exporter
    }

    private void afficherObjet(Objet objet) {
        vbMenuGauche.getChildren().clear();
        vbMenuGauche.getChildren().add(labObjetInventaire);
        vbMenuGauche.getChildren().add(sectionGenerale.charger(objet));

        if (objet.getClass() == Livre.class) {
            vbMenuGauche.getChildren().add(sectionLivre.charger((Livre) objet));
        } else if (objet.getClass() == Outil.class) {
            vbMenuGauche.getChildren().add(sectionOutil.charger((Outil) objet));
        } else {
            vbMenuGauche.getChildren().add(sectionJeu.charger((Jeu) objet));
        }

        vbMenuGauche.getChildren().add(hbModifierObjet);
    }

    private void preparerSectionsGauche() {
        Button btnModifier = new Button("Modfier");
        Button btnFermer = new Button("Fermer");

        btnModifier.setOnAction(event -> modifierObjet());
        btnFermer.setOnAction(event -> {
            vbMenuGauche.getChildren().clear();
            tvObjets.getSelectionModel().clearSelection();
        });

        hbModifierObjet.getChildren().addAll(btnModifier, btnFermer);

        labObjetInventaire.setFont(new Font(20));
    }

    private void modifierObjet() {
        Objet objet = objets.get(tvObjets.getSelectionModel().getSelectedIndex());

        remplirValeursObjet(objet);

        if (objet.getClass() == Livre.class) {
            remplirValeursLivre((Livre) objet);
        } else if (objet.getClass() == Outil.class) {
            remplirValeursOutil((Outil) objet);
        } else {
            remplirValeursJeu((Jeu) objet);
        }

        tvObjets.refresh();
    }

    private void reinitialiser() {
        miRecharger.setDisable(true);

        fichier = null;
        listesObjets.livres.clear();
        listesObjets.outils.clear();
        listesObjets.jeux.clear();

        reinitialiserFiltres();
    }

    private void reinitialiserFiltres() {
        tbtnLivre.setSelected(true);
        tbtnOutil.setSelected(true);
        tbtnJeu.setSelected(true);

        txfRecherche.clear();
    }

    private void confirmerRecharger() {
        if (!sauvegarde) {
            ButtonType btnRecharger = new ButtonType("Recharger", ButtonBar.ButtonData.YES);
            ButtonType btnAnnuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

            Alert fenetreSauvegarder = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Toutes les modifications seront perdues.",
                    btnRecharger,
                    btnAnnuler
            );
            fenetreSauvegarder.setTitle("Recharger?");
            fenetreSauvegarder.setHeaderText("Recharger?");

            fenetreSauvegarder.showAndWait().ifPresent(type -> {
                if (type == btnRecharger) {
                    sauvegarde = true;
                    chargerFichier();
                }
            });

        }
    }

    private static void selectionnerTexteQuandClique()
}