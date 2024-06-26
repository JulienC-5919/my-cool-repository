package com.example.tp2;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public class Main extends Application {

    // true si toutes les modifications sont sauvegardées, ou si l'utilisateur souhaite quitter sans sauvegarder.
    private boolean sauvegarde = true;
    private final DecimalFormat formatteurPrix = new DecimalFormat("0.00$");

    private Stage stage;
    private TableView<Objet> tvObjets;

    private HBox hbBarreOutils;
    private VBox vbBarreHaut; // Contient la barre de menu fichier et la barre de recherche
    private final VBox vbMenuDroit; //Menu pour créer / voir / modifier des objets

    private TextField txfRecherche;

    private MenuBar mbFichier;

    // Classes privées pour mieux gérer les section du menu droit.
    private final NouvelObjetHaut sectionHautNouvelObjet = new NouvelObjetHaut();
    private final NouvelObjetBas sectionBasNouvelObjet = new NouvelObjetBas();
    private final SectionGenerale sectionGenerale = new SectionGenerale();
    private final SectionLivre sectionLivre = new SectionLivre();
    private final SectionOutil sectionOutil = new SectionOutil();
    private final SectionJeu sectionJeu = new SectionJeu();

    //Classe privée sérialisable contenant tous les objets
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

    /**
     * À l'instantiation, préparer chaque division de la fenêtre principale
     */
    public Main() {
        preparerBarreRecherche();
        preparerBarreFichier();
        preparerBarreHaut();
        preparerSectionsDroite();
        preparerFormatteurPrix();

        preparerTableView();
        vbMenuDroit = new VBox();
        vbMenuDroit.setMinWidth(360);
        vbMenuDroit.setSpacing(15);
        vbMenuDroit.setPadding(new Insets(5));

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier DAT (*.dat)","*.dat"));
    }

    /**
     * Prépare la fenêtre principale puis l'affiche
     * @param stage Fenêtre
     */
    @Override
    public void start(Stage stage) {
        Separator s = new Separator(Orientation.VERTICAL);
        s.setPrefSize(50, 0);

        this.stage = stage;

        stage.setOnCloseRequest(event -> {
            confirmerSauvegarder();
            if (!sauvegarde) {
                event.consume();
            }
        });

        root.setTop(vbBarreHaut);
        root.setCenter(tvObjets);
        root.setRight(new HBox(s, vbMenuDroit));

        stage.setMinWidth(1350);
        stage.setMinHeight(750);

        Scene scene = new Scene(root);

        stage.setTitle("2268130 TP2");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Prépare la barre de recherche
     */
    private void preparerBarreRecherche() {
        hbBarreOutils = new HBox();

        hbBarreOutils.setSpacing(5);

        hbBarreOutils.setAlignment(Pos.CENTER_LEFT);

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

    /**
     * Regroupe la barre de recherche et le menu fichier dans vbBarreHaut
     */
    private void preparerBarreHaut() {
        vbBarreHaut = new VBox();

        vbBarreHaut.setSpacing(5);

        vbBarreHaut.getChildren().addAll(mbFichier, hbBarreOutils, new Separator(Orientation.HORIZONTAL));
    }

    /**
     *Prépare la barre de menus Fichier
     */
    private void preparerBarreFichier() {
        mbFichier = new MenuBar();
        Menu menuFichier = new Menu("Fichier");

        MenuItem miOuvrir = new MenuItem("Ouvrir");
        MenuItem miSauvegarder = new MenuItem("Sauvegarder");
        MenuItem miSauvegarderSous = new MenuItem("Sauvegarder sous");
        MenuItem miExporter = new MenuItem("Exporter");
        MenuItem miNouveau = new MenuItem("Nouveau");

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


    /**
     *Prépare le TableView et les colonnes
     */
    private void preparerTableView() {
        tvObjets = new TableView<Objet>();

        TableColumn<Objet, String> tcNom = new TableColumn<Objet, String>("Nom");
        TableColumn<Objet, String> tcDescription = new TableColumn<Objet, String>("Description");
        TableColumn<Objet, String> tcEtat = new TableColumn<Objet, String>("État");
        TableColumn<Objet, LocalDate> tcDateAchat = new TableColumn<Objet, LocalDate>("Date d'achat");
        TableColumn<Objet, String> tcPrix = new TableColumn<Objet, String>("Prix");

        tcNom.setResizable(false);
        tcDescription.setResizable(false);
        tcEtat.setResizable(false);
        tcDateAchat.setResizable(false);
        tcPrix.setResizable(false);

        tcNom.setPrefWidth(150);
        tcDescription.setPrefWidth(500);
        tcEtat.setPrefWidth(100);
        tcDateAchat.setPrefWidth(100);

        tcNom.setCellValueFactory(new PropertyValueFactory<Objet, String>("nom"));
        tcDescription.setCellValueFactory(new PropertyValueFactory<Objet, String>("description"));
        tcEtat.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEtat().getDisplayName()));
        tcDateAchat.setCellValueFactory(new PropertyValueFactory<Objet, LocalDate>("dateAchat"));
        tcPrix.setCellValueFactory(cellData -> new SimpleStringProperty(
                formatteurPrix.format(cellData.getValue().getPrix()))
        );

        tvObjets.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                int index = t1.intValue();

                if (index >= 0) {
                    afficherObjet(objets.get(index));
                }
            }
        });

        tvObjets.getColumns().addAll(tcNom, tcDescription, tcEtat, tcDateAchat, tcPrix);

        tvObjets.setItems(objets);
    }

    /**
     * Prépare le VBox des sections du menu à droite puis les boutons modifier / fermer du menu de modification
     */
    private void preparerSectionsDroite() {
        Button btnModifier = new Button("Modfier");
        Button btnFermer = new Button("Fermer");

        btnModifier.setOnAction(event -> modifierObjet());
        btnFermer.setOnAction(event -> fermerObjet());

        hbModifierObjet.setSpacing(5);

        hbModifierObjet.getChildren().addAll(btnModifier, btnFermer);

        labObjetInventaire.setFont(new Font(20));
    }

    /**
     * Charge une section dans le menu à droite selon un numéro donné
     * @param numeroSection Numéro de la section à afficher
     */
    private void chargerSection(int numeroSection) {
        sectionBasNouvelObjet.btnAjouter.setDisable(false);

        if (vbMenuDroit.getChildren().size() == 2) {
            vbMenuDroit.getChildren().add(1, sectionGenerale.charger());

            switch (numeroSection) {
                case 0 -> {
                    vbMenuDroit.getChildren().add(2, sectionLivre.charger());
                }
                case 1 -> {
                    vbMenuDroit.getChildren().add(2, sectionOutil.charger());
                }
                default -> {
                    vbMenuDroit.getChildren().add(2, sectionJeu.charger());
                }
            }
        }
        else {

            switch (numeroSection) {
                case 0 -> {
                    vbMenuDroit.getChildren().set(2, sectionLivre.charger());
                }
                case 1 -> {
                    vbMenuDroit.getChildren().set(2, sectionOutil.charger());
                }
                case 2 -> {
                    vbMenuDroit.getChildren().set(2, sectionJeu.charger());
                }
            }
        }
    }

    /**
     * Crée un objet selon le type décidé (Livre / Outil / Jeu) puis l'ajoute à la List correspondante
     */
    private void creerObjetSelonType() {
        try {
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

            vbMenuDroit.getChildren().clear();
            rechargerObjets();
            sauvegarde = false;
        } catch (IllegalArgumentException e) {
            afficherErreur("Donnée manquante", e.getMessage());
        }
    }

    /**
     * Remplit les valeurs d'un Objet depuis la section générale
     * @param objet Objet dont il faut remplir les valeurs
     */
    private void remplirValeursObjet(Objet objet) {
        objet.setNom(sectionGenerale.getNom());
        objet.setPrix(sectionGenerale.getPrix());
        objet.setQuantite(sectionGenerale.getQuantite());
        objet.setDateAchat(sectionGenerale.getDateAchat());
        objet.setOctetsImageFacture(sectionGenerale.getOctetsImageFacture());

        objet.setEtat(sectionGenerale.getEtat());
        objet.setEmplacement(sectionGenerale.getEmplacement());
    }

    /**
     * Remplit les valeurs spécifiques à un Outil depuis la section outil
     * @param outil Outil dont il faut remplir les valeurs
     */
    private void remplirValeursOutil(Outil outil) {
        outil.setMarque(sectionOutil.getMarque());
        outil.setModele(sectionOutil.getModele());
        outil.setNumeroSerie(sectionOutil.getNumeroSerie());
    }

    /**
     * Remplit les valeurs spécifiques à un Livre depuis la section livre
     * @param livre Livre dont il faut remplir les valeurs
     */
    private void remplirValeursLivre(Livre livre) {
        livre.setAuteur(sectionLivre.getAuteur());
        livre.setMaisonEdition(sectionLivre.getMaisonEdition());
        livre.setAnneeEcriture(sectionLivre.getAnneeEcriture());
        livre.setAnneePublication(sectionLivre.getAnneePublication());
    }

    /**
     * Remplit les valeurs spécifiques à un Jeu depuis la section jeu
     * @param jeu Jeu dont il faut remplir les valeurs
     */
    private void remplirValeursJeu(Jeu jeu) {
        jeu.setConsole(sectionJeu.getConsole());
        jeu.setNbJoueurs(sectionJeu.getNbJoueurs());
        jeu.setDeveloppement(sectionJeu.getDeveloppement());
        jeu.setAnneeSortie(sectionJeu.getAnneeSortie());
    }

    /**
     * Supprime l'objet sélectionné dans le TableView
     */
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

    /**
     * Affiche un pop-up erreur personnalisé
     * @param entete Texte de l'en-tête
     * @param details texte des détails
     */
    private void afficherErreur(String entete, String details) {
        Alert alert = new Alert(Alert.AlertType.ERROR, details);
        alert.setHeaderText(entete);
        alert.showAndWait();
    }

    /**
     * Prépare le menu droit pour créer un nouvel objet
     */
    private void nouvelObjet() {
        vbMenuDroit.getChildren().clear();
        vbMenuDroit.getChildren().addAll(sectionHautNouvelObjet.charger(), sectionBasNouvelObjet.charger());
    }

    /**
     * Charge dans le TableView les objets qui correspondent aux filtres
     */
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

    /**
     * Charge un objet dans le TableView si au moins une de ses valeurs contient le texte entré dans txfRecherche
     * @param objet objet à tester & charger
     */
    private void chargerSiDansRecherche(Objet objet) {
        String recherche = txfRecherche.getText().toLowerCase();

        if (
                objet.getNom().toLowerCase().contains(recherche) ||
                        objet.getDateAchat().toString().contains(recherche) ||
                        objet.getDescription().toLowerCase().contains(recherche) ||
                        objet.getEmplacement().toLowerCase().contains(recherche) ||
                        String.valueOf(objet.getPrix()).contains(recherche) ||
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

    /**
     * Sauvegarde les listes d'objets dans un fichier sérialisé.
     */
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

    /**
     * Demande à l'utilisateur dans quel fichier sauvegarder les listes d'objets, puis sauvegarde
     */
    private void sauvegarderSous() {
        File fichierChoisi;

        fichierChoisi = fileChooser.showSaveDialog(stage);
        if (fichierChoisi != null) {
            fichier = fichierChoisi;
            sauvegarder();
        }
    }

    /**
     * Choisir dans quel fichier sauvegarder si ce n'est pas déjà fait, ensuite sauvegarder
     */
    private void miSauvegarderAction() {
        if (fichier == null) {
            sauvegarderSous();
        }
        else {
            sauvegarder();
        }
    }

    /**
     * Si les dernières modifications n'ont pas été sauvegardées et seront perdues après la prochaine opération,
     * demander à l'utilisateur s'il veut les sauvegarder avant de continuer.
     */
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

    /**
     * Demande à l'utilisateur depuis quel fichier charger les données de sauvegarde, puis le charge
     */
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

    /**
     * Charge les listes d'objets depuis un fichier sérialisé
     */
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

    /**
     * Affiche les détails de l'objet sélectionné du TableView dans le menu droit
     * @param objet objet à afficher
     */
    private void afficherObjet(Objet objet) {
        vbMenuDroit.getChildren().clear();
        vbMenuDroit.getChildren().add(labObjetInventaire);
        vbMenuDroit.getChildren().add(sectionGenerale.charger(objet));

        if (objet.getClass() == Livre.class) {
            vbMenuDroit.getChildren().add(sectionLivre.charger((Livre) objet));
        } else if (objet.getClass() == Outil.class) {
            vbMenuDroit.getChildren().add(sectionOutil.charger((Outil) objet));
        } else {
            vbMenuDroit.getChildren().add(sectionJeu.charger((Jeu) objet));
        }

        vbMenuDroit.getChildren().add(hbModifierObjet);
    }

    /**
     * Modifie les valeurs de l'objet sélectionné du TableView avec les nouvelles valeurs données dans le menu droit.
     */
    private void modifierObjet() {
        Objet objet = objets.get(tvObjets.getSelectionModel().getSelectedIndex());

        remplirValeursObjet(objet);

        try {
            if (objet.getClass() == Livre.class) {
                remplirValeursLivre((Livre) objet);
            } else if (objet.getClass() == Outil.class) {
                remplirValeursOutil((Outil) objet);
            } else {
                remplirValeursJeu((Jeu) objet);
            }

            tvObjets.refresh();
        } catch (IllegalArgumentException e) {
            afficherErreur("Donnée manquante", e.getMessage());
        }
    }

    private void fermerObjet() {
        vbMenuDroit.getChildren().clear();
        tvObjets.getSelectionModel().clearSelection();
    }

    /**
     * Oublie le fichier de sauvegarde et supprime tous les objets
     */
    private void reinitialiser() {
        miRecharger.setDisable(true);

        fichier = null;
        listesObjets.livres.clear();
        listesObjets.outils.clear();
        listesObjets.jeux.clear();

        reinitialiserFiltres();
    }

    /**
     * Remet les filtres à leurs valeurs initiales
     */
    private void reinitialiserFiltres() {
        tbtnLivre.setSelected(true);
        tbtnOutil.setSelected(true);
        tbtnJeu.setSelected(true);

        txfRecherche.clear();
    }

    /**
     * Confirme si l'utilisateur veut vraiment recharger les objets depuis la dernière sauvegarde.
     */
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

    /**
     * Prépare formatteurPrix
     */
    private void preparerFormatteurPrix() {
        formatteurPrix.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.CANADA_FRENCH));
        formatteurPrix.setGroupingSize(3);
        formatteurPrix.setGroupingUsed(true);
    }

    /**
     * Exporte les objets en fichier txt
     */
    private void exporter() {
        File fichierExportation;
        FileChooser fileChooserExportation = new FileChooser();

        Writer writer;
        BufferedWriter buffWriter;

        fileChooserExportation.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier texte (*.txt)", "*.txt"));

        fichierExportation = fileChooserExportation.showSaveDialog(stage);
        if (fichierExportation != null) {

            try {
                writer = new FileWriter(fichierExportation);
                buffWriter = new BufferedWriter(writer);

                if (!listesObjets.livres.isEmpty()) {
                    buffWriter.write("Livres:\n");
                    listesObjets.livres.forEach(livre -> {
                        try {
                            imprimerLivre(buffWriter, livre);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }

                if (!listesObjets.outils.isEmpty()) {
                    buffWriter.write("Outils:\n");
                    listesObjets.outils.forEach(outil -> {
                        try {
                            imprimerOutil(buffWriter, outil);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }

                if (!listesObjets.jeux.isEmpty()) {
                    buffWriter.write("Jeux:\n");
                    listesObjets.jeux.forEach(jeu -> {
                        try {
                            imprimerJeu(buffWriter, jeu);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }

                buffWriter.close();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Classe sérialisable qui contient les listes d'objets
     */
    private static class ListesObjets implements Serializable {
        public final ArrayList<Livre> livres = new ArrayList<>();
        public final ArrayList<Outil> outils = new ArrayList<>();
        public final ArrayList<Jeu> jeux = new ArrayList<>();
    }

    /**
     * Imprime le nom et les caractéristiques générales d'un objet dans un BufferedWriter
     * @param buffWriter BufferedWriter où écrire les caractéristiques de l'objet
     * @param objet Objet dont il faut écrire les caractéristiques
     * @throws IOException si le BufferedWriter est null
     */
    private void imprimerSectionGenerale(BufferedWriter buffWriter, Objet objet) throws IOException {
        buffWriter.write("\n    " + objet.getNom());
        buffWriter.write("\n        Prix: " + formatteurPrix.format(objet.getPrix()));
        buffWriter.write("\n        Quantité: " + objet.getQuantite());
        buffWriter.write("\n        Date d'achat: " + objet.getDateAchat());
        buffWriter.write("\n        État: " + objet.getEtat().getDisplayName());
        buffWriter.write("\n        Emplacement: " + objet.getEmplacement());
    }

    /**
     * Imprime toutes les caractéristiques d'un  dans un BufferedWriter
     * @param buffWriter BufferedWriter où écrire les caractéristiques d
     * @param livre  dont il faut écrire les caractéristiques
     * @throws IOException si le BufferedWriter est null
     */
    private void imprimerLivre(BufferedWriter buffWriter, Livre livre) throws IOException {
        imprimerSectionGenerale(buffWriter, livre);

        buffWriter.write("\n        Auteur: " + livre.getAuteur());
        buffWriter.write("\n        Maison d'édition: " + livre.getMaisonEdition());
        buffWriter.write("\n        Année d'écriture: " + livre.getAnneeEcriture());
        buffWriter.write("\n        Année de publication: " + livre.getAnneePublication() + "\n\n");
    }

    /**
     * Imprime toutes les caractéristiques d'un outil dans un BufferedWriter
     * @param buffWriter BufferedWriter où écrire les caractéristiques de l'outil
     * @param outil outil dont il faut écrire les caractéristiques
     * @throws IOException si le BufferedWriter est null
     */
    private void imprimerOutil(BufferedWriter buffWriter, Outil outil) throws IOException{
        imprimerSectionGenerale(buffWriter, outil);

        buffWriter.write("\n        Marque: " + outil.getMarque());
        buffWriter.write("\n        Modèle: " + outil.getModele());
        buffWriter.write("\n        Numéro de série: " + outil.getNumeroSerie() + "\n\n");
    }

    /**
     * Imprime toutes les caractéristiques d'un jeu dans un BufferedWriter
     * @param buffWriter BufferedWriter où écrire les caractéristiques du jeu
     * @param jeu jeu dont il faut écrire les caractéristiques
     * @throws IOException si le BufferedWriter est null
     */
    private void imprimerJeu(BufferedWriter buffWriter, Jeu jeu) throws IOException {
        imprimerSectionGenerale(buffWriter, jeu);

        buffWriter.write("\n        Console: " + jeu.getConsole());
        buffWriter.write("\n        Développement: " + jeu.getDeveloppement());
        buffWriter.write("\n        Nombre de joueurs: " + jeu.getNbJoueurs());
        buffWriter.write("\n        Année de sortie: " + jeu.getAnneeSortie() + "\n\n");
    }

////////////////////////////////// Classes private des sections du tableau de droite ///////////////////////////////////

    /**
     * Affiche les propriétés générales d'un objet telles que le nom, le prix etc.
     */
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

        /**
         * Place l'en-tête puis chaque champ dans un GridPane.
         * Installe les listeners
         */
        private SectionGenerale() {

            gpContenu.setVgap(5);
            gpContenu.getColumnConstraints().add(new ColumnConstraints(150));

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

            txfPrix.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                    if (!(t1 || txfPrix.getText().isEmpty())) {
                        formatterPrixString();
                    }
                }
            });

            spCadreFacture.getChildren().add(ivFacture);
            spQuantite.setEditable(true);

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

        /**
         * Charge le GridPane avec les champs vides.
         * @return gridpane qui contient les champs de la section générale
         */
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

        /**
         * Charge le GridPane avec les propriétés d'un Objet affichées dans les champs.
         * @param objet Objet dont il faut afficher les propriétés
         * @return gridpane qui contient les champs de la section générale
         */
        private GridPane charger(Objet objet) {

            octetsImageFacture = objet.getOctetsImageFacture();

            txfNom.setText(objet.getNom());
            txfPrix.setText(formatteurPrix.format(objet.getPrix()));
            spQuantite.getValueFactory().setValue(objet.getQuantite());
            dpAchat.setValue(objet.getDateAchat());
            cbEtat.getSelectionModel().select(objet.getEtat().ordinal());
            txfEmplacement.setText(objet.getEmplacement());

            ivFacture.setImage(new Image(new ByteArrayInputStream(octetsImageFacture)));

            return gpContenu;
        }

        /**
         * Utilise le FileChooser pour choisir l'image de la facture
         */
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

        /**
         * Affiche l'image de la facture dans une deuxième fenêtre.
         */
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

        /**
         * Rend la bordure d'un Pane grise
         * @param panneau Pane dont il faut changer la bordure
         */
        private void bordureGrise(Pane panneau) {
            panneau.setBorder(new Border(
                    new BorderStroke(Color.LIGHTGRAY,
                            BorderStrokeStyle.SOLID,
                            CornerRadii.EMPTY,
                            new BorderWidths(2))
            ));
        }

        /**
         * Rend la bordure d'un Pane bleue
         * @param panneau Pane dont il faut changer la bordure
         */
        private void bordureBleue(Pane panneau) {
            panneau.setBorder(new Border(
                new BorderStroke(Color.DODGERBLUE,
                        BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        new BorderWidths(2))
                )
            );
        }

        /**
         * Corrige les erreurs du mieux possible dans txfPrix puis affiche le prix en dollars
         */
        private void formatterPrixString() {
            String prix = txfPrix.getText();
            StringBuilder builder = new StringBuilder();

            int i = 0;
            int decimales;

            //Chiffres avant la virgule

            while (i < prix.length() && prix.charAt(i) != '.' && prix.charAt(i) != ',') {
                if (prix.charAt(i) >= '0' && prix.charAt(i) <= '9') {
                    builder.append(prix.charAt(i));
                }

                i++;
            }

            if (builder.isEmpty()) {
                builder.append('0');
            }

            //virgule

            builder.append(',');
            decimales = 0;

            i++;

            //Chiffres après la virgule

            while (i < prix.length() && decimales < 2) {
                if (prix.charAt(i) >= '0' && prix.charAt(i) <= '9') {
                    builder.append(prix.charAt(i));
                    decimales++;
                }

                i++;
            }
            builder.append("0".repeat(2 - decimales));

            //Espaces

            i = builder.length() - 6;

            while (i > 0) {
                builder.insert(i, ' ');

                i -= 3;
            }

            txfPrix.setText(builder.append('$').toString());
        }

        private String getNom() {
            if (txfNom.getText().isEmpty()) {
                throw new IllegalArgumentException("Veuillez entrer un nom.");
            }
            return txfNom.getText();
        }

        private double getPrix() {
            if (txfPrix.getText().isEmpty()) {
                throw new IllegalArgumentException("Veuillez entrer un prix.");
            }
            return Double.parseDouble(txfPrix.getText().replaceAll("[ $]", "").replace(',', '.'));
        }

        private int getQuantite() {
            return spQuantite.getValue();
        }

        private LocalDate getDateAchat() {
            if (dpAchat.getValue() == null) {
                throw new IllegalArgumentException("Veuillez entrer une date d'achat valide.");
            }
            return dpAchat.getValue();
        }

        // L'image est stockée en tableau d'octets pour pouvoir être sérialisée.
        private byte[] getOctetsImageFacture() {
            return octetsImageFacture;
        }

        private Objet.etat getEtat() {
            if (cbEtat.getSelectionModel().getSelectedIndex() < 0) {
                throw new IllegalArgumentException("Veuillez sélectionner un état.");
            }
            return Objet.etat.values()[cbEtat.getSelectionModel().getSelectedIndex()];
        }

        private String getEmplacement() {
            if (txfEmplacement.getText().isEmpty()) {
                throw new IllegalArgumentException("Veuillez entrer un emplacement.");
            }
            return txfEmplacement.getText();
        }
    }

    private static class SectionLivre {
        private final GridPane gpContenu = new GridPane();;

        private final TextField txfAuteur = new TextField();
        private final TextField txfMaisonEdition = new TextField();
        private final TextField txfAnneeEcriture = new TextField();
        private final TextField txfAnneePublication = new TextField();

        /**
         * Place l'en-tête puis chaque champ dans un GridPane.
         * Installe les listeners
         */
        private SectionLivre() {
            gpContenu.setVgap(5);
            gpContenu.getColumnConstraints().add(new ColumnConstraints(150));

            Label entete = new Label("Secton livre");
            entete.setFont(new Font(20));

            txfAnneeEcriture.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                    if (!(t1 || txfAnneeEcriture.getText().isEmpty())) {
                        String annee = txfAnneeEcriture.getText().replaceAll("\\D", "");
                        if (annee.length() > 4) {
                            txfAnneeEcriture.setText("9999");
                        } else if (annee.isEmpty()) {
                            txfAnneeEcriture.setText("0");
                        } else {
                            txfAnneeEcriture.setText(annee);
                        }
                    }
                }
            });

            txfAnneePublication.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                    if (!(t1 || txfAnneePublication.getText().isEmpty())) {
                        String annee = txfAnneePublication.getText().replaceAll("\\D", "");
                        if (annee.length() > 4) {
                            txfAnneePublication.setText("9999");
                        } else if (annee.isEmpty()) {
                            txfAnneePublication.setText("0");
                        } else {
                            txfAnneePublication.setText(annee);
                        }
                    }
                }
            });

            gpContenu.add(entete, 0, 0, 2, 1);

            gpContenu.add(new Label("Auteur:"), 0, 1);
            gpContenu.add(new Label("Maison d'édition:"), 0, 2);
            gpContenu.add(new Label("Année d'écriture:"), 0, 3);
            gpContenu.add(new Label("Année de publication:"), 0, 4);

            gpContenu.add(txfAuteur, 1, 1);
            gpContenu.add(txfMaisonEdition, 1, 2);
            gpContenu.add(txfAnneeEcriture, 1, 3);
            gpContenu.add(txfAnneePublication, 1, 4);
        }

        /**
         * Charge le GridPane avec les champs vides.
         * @return gridpane qui contient les champs de la section livre
         */
        private GridPane charger() {
            txfAuteur.clear();
            txfMaisonEdition.clear();
            txfAnneeEcriture.clear();
            txfAnneePublication.clear();

            return gpContenu;
        }

        /**
         * Charge le GridPane avec les propriétés d'un Livre affichées dans les champs.
         * @param livre Livre dont il faut afficher les propriétés
         * @return gridpane qui contient les champs de la section livre
         */
        private GridPane charger(Livre livre) {
            txfAuteur.setText(livre.getAuteur());
            txfMaisonEdition.setText(livre.getMaisonEdition());
            txfAnneeEcriture.setText(String.valueOf(livre.getAnneeEcriture()));
            txfAnneePublication.setText(String.valueOf(livre.getAnneePublication()));

            return gpContenu;
        }

        private String getAuteur() {
            if (txfAuteur.getText().isEmpty()) {
                throw new IllegalArgumentException("Veuillez entrer l'auteur.");
            }
            return txfAuteur.getText();
        }
        private String getMaisonEdition() {
            if (txfMaisonEdition.getText().isEmpty()) {
                throw new IllegalArgumentException("Veuillez entrer la maison d'édition.");
            }
            return txfMaisonEdition.getText();
        }
        private short getAnneeEcriture() {
            if (txfAnneeEcriture.getText().isEmpty()) {
                throw new IllegalArgumentException("Veuillez entrer l'année d'écriture.");
            }
            return Short.parseShort(txfAnneeEcriture.getText());
        }
        private short getAnneePublication() {
            if (txfAnneePublication.getText().isEmpty()) {
                throw new IllegalArgumentException("Veuillez entrer l'année de publication.");
            }
            return Short.parseShort(txfAnneePublication.getText());
        }

    }

    private static class SectionOutil {
        private final GridPane gpContenu = new GridPane();
        private final TextField txfMarque = new TextField();
        private final TextField txfModele = new TextField();
        private final TextField txfNumeroSerie = new TextField();

        /**
         * Place l'en-tête puis chaque champ dans un GridPane.
         * Installe les listeners
         */
        private SectionOutil() {

            gpContenu.setVgap(5);
            gpContenu.getColumnConstraints().add(new ColumnConstraints(150));

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

        /**
         * Charge le GridPane avec les champs vides.
         * @return gridpane qui contient les champs de la section outil
         */
        private GridPane charger() {
            txfMarque.clear();
            txfNumeroSerie.clear();

            return gpContenu;
        }

        /**
         * Charge le GridPane avec les propriétés d'un Outil affichées dans les champs.
         * @param outil Outil dont il faut afficher les propriétés
         * @return gridpane qui contient les champs de la section outil
         */
        private GridPane charger(Outil outil) {
            txfMarque.setText(outil.getMarque());
            txfNumeroSerie.setText(outil.getNumeroSerie());
            txfMarque.setText(outil.getMarque());

            return gpContenu;
        }

        private String getMarque() {
            if (txfMarque.getText().isEmpty()) {
                throw new IllegalArgumentException("Veuillez entrer la marque.");
            }
            return txfMarque.getText();
        }

        private String getNumeroSerie() {
            if (txfNumeroSerie.getText().isEmpty()) {
                throw new IllegalArgumentException("Veuillez entrer le numéro de série.");
            }
            return txfNumeroSerie.getText();
        }

        private String getModele() {
            if (txfModele.getText().isEmpty()) {
                throw new IllegalArgumentException("Veuillez entrer le modèle.");
            }
            return txfModele.getText();
        }
    }

    private static class SectionJeu {
        private final GridPane gpContenu = new GridPane();

        private final TextField txfConsole = new TextField();
        private final Spinner<Integer> spNbJoueurs = new Spinner<Integer>();
        private final TextField txfDeveloppement = new TextField();
        private final TextField txfAnneeSortie = new TextField();

        /**
         * Place l'en-tête puis chaque champ dans un GridPane.
         * Installe les listeners
         */
        private SectionJeu() {

            gpContenu.setVgap(5);
            gpContenu.getColumnConstraints().add(new ColumnConstraints(150));

            Label entete = new Label("Secton jeu");
            entete.setFont(new Font(20));

            spNbJoueurs.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE));

            txfAnneeSortie.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                    if (!(t1 || txfAnneeSortie.getText().isEmpty())) {
                        String annee = txfAnneeSortie.getText().replaceAll("\\D", "");
                        if (annee.length() > 4) {
                            txfAnneeSortie.setText("9999");
                        } else if (annee.isEmpty()) {
                            txfAnneeSortie.setText("0");
                        } else {
                            txfAnneeSortie.setText(annee);
                        }
                    }
                }
            });

            gpContenu.add(entete, 0, 0, 2, 1);

            gpContenu.add(new Label("Console:"), 0, 1);
            gpContenu.add(new Label("Nombre de joueurs:"), 0, 2);
            gpContenu.add(new Label("Développement:"), 0, 3);
            gpContenu.add(new Label("Année de sortie:"), 0, 4);

            gpContenu.add(txfConsole, 1, 1);
            gpContenu.add(spNbJoueurs, 1, 2);
            gpContenu.add(txfDeveloppement, 1, 3);
            gpContenu.add(txfAnneeSortie, 1, 4);
        }

        /**
         * Charge le GridPane avec les champs vides.
         * @return gridpane qui contient les champs de la section jeu
         */
        private GridPane charger() {
            txfConsole.clear();
            spNbJoueurs.getValueFactory().setValue(1);
            txfDeveloppement.clear();
            txfAnneeSortie.clear();

            return gpContenu;
        }

        /**
         * Charge le GridPane avec les propriétés d'un Jeu affichées dans les champs.
         * @param jeu Jeu dont il faut afficher les propriétés
         * @return gridpane qui contient les champs de la section jeu
         */
        private GridPane charger(Jeu jeu) {

            txfConsole.setText(jeu.getConsole());
            spNbJoueurs.getValueFactory().setValue(jeu.getNbJoueurs());
            txfDeveloppement.setText(String.valueOf(jeu.getDeveloppement()));
            txfAnneeSortie.setText(String.valueOf(jeu.getAnneeSortie()));

            return gpContenu;
        }

        private String getConsole() {
            if (txfConsole.getText().isEmpty()) {
                throw new IllegalArgumentException("Veuillez entrer la console.");
            }
            return txfConsole.getText();
        }
        private int getNbJoueurs() {
            return spNbJoueurs.getValue();
        }
        private String getDeveloppement() {
            if (txfDeveloppement.getText().isEmpty()) {
                throw new IllegalArgumentException("Veuillez entrer la compagnie de développement.");
            }
            return txfDeveloppement.getText();
        }
        private short getAnneeSortie() {
            if (txfAnneeSortie.getText().isEmpty()) {
                throw new IllegalArgumentException("Veuillez entrer l'année de sortie.");
            }
            return Short.parseShort(txfAnneeSortie.getText());
        }
    }

    private class NouvelObjetHaut {
        private final GridPane gpContenu = new GridPane();
        private final ChoiceBox<String> cbTypeObjet = new ChoiceBox<String>(
                FXCollections.observableArrayList("Livre", "Outil", "Jeu")
        );

        private NouvelObjetHaut() {

            gpContenu.setVgap(5);
            gpContenu.setHgap(5);

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

    private class NouvelObjetBas {
        private final HBox hbContenu = new HBox(5);
        private final Button btnAjouter = new Button("Ajouter");
        private NouvelObjetBas() {
            Button btnAnnuler = new Button("Annuler");

            btnAjouter.setOnAction(e -> creerObjetSelonType());
            btnAnnuler.setOnAction(e -> vbMenuDroit.getChildren().clear());

            hbContenu.getChildren().addAll(btnAjouter, btnAnnuler);
        }

        private HBox charger() {
            btnAjouter.setDisable(true);
            return hbContenu;
        }
    }
}