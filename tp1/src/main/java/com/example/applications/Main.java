package com.example.applications;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Main extends Application {
    ObservableList<FeuilleResultat> resultats;
    TextField txfDa;
    TextField txfExa1;
    TextField txfExa2;
    TextField txfTp1;
    TextField txfTp2;

    TextField[] txfMoyennes;
    TextField[] txfMinimums;
    TextField[] txfMaximums;
    TableColumn tcExa1;
    TableColumn tcExa2;
    TableColumn tcTP1;
    TableColumn tcTP2;

    TableView<FeuilleResultat> tvNotes;

    /**
     * Prépare et affiche la fenêtre graphique
     * @param stage stage dans lequel mettre la scène
     */
    public void start(Stage stage){

        ///////////////////////////////////////////////INITIALISATIONS//////////////////////////////////////////////////
        GridPane root = new GridPane();

        tvNotes = new TableView<FeuilleResultat>();
        VBox vbOptions = new VBox(5);
        TilePane tpComparaison = new TilePane();
        Button btnQuitter = new Button("Quitter");
        GridPane.setHalignment(btnQuitter, HPos.RIGHT);
        GridPane.setValignment(btnQuitter, VPos.BOTTOM);

        TableColumn tcDA = new TableColumn("DA");
        tcExa1 = new TableColumn("Exa1");
        tcExa2 = new TableColumn("Exa2");
        tcTP1 = new TableColumn("TP1");
        tcTP2 = new TableColumn("TP1");

        GridPane gpAjouterNotes = new GridPane();
        HBox hbBoutons = new HBox(5);

        Label labMoyenne = new Label("Moyenne: ");
        Label labMinimum = new Label("Min: ");
        Label labMaximum = new Label("max: ");

        txfMoyennes = new TextField[4];
        txfMinimums = new TextField[4];
        txfMaximums = new TextField[4];

        Label labDa = new Label("DA");
        txfDa = new TextField();

        Label labExam1 = new Label("Exa1");
        txfExa1 = new TextField();

        Label labExam2 = new Label("Exa2");
        txfExa2 = new TextField();

        Label labTp1 = new Label("TP1");
        txfTp1 = new TextField();

        Label labTp2 = new Label("TP2");
        txfTp2 = new TextField();

        resultats = FXCollections.observableArrayList();

        Button btnAjouter = new Button("Ajouter");
        Button btnModifier = new Button("Modifier");
        Button btnSupprimmer = new Button("Supprimer");

        ///////////////////////////////////////PRÉPARATION DES TABLECOLUMNS/////////////////////////////////////////////

        tcDA.setCellValueFactory(new PropertyValueFactory<FeuilleResultat, Integer>("da"));
        tcExa1.setCellValueFactory(new PropertyValueFactory<FeuilleResultat, Byte>("exa1"));
        tcExa2.setCellValueFactory(new PropertyValueFactory<FeuilleResultat, Byte>("exa2"));
        tcTP1.setCellValueFactory(new PropertyValueFactory<FeuilleResultat, Byte>("tp1"));
        tcTP2.setCellValueFactory(new PropertyValueFactory<FeuilleResultat, Byte>("tp2"));

        //////////////////////////////////////AJUSTEMENT DES TAILLES & POSITIONS////////////////////////////////////////

        tcDA.setPrefWidth(100);
        tcDA.setResizable(false);
        tcDA.setReorderable(false);
        tcDA.setSortable(false);

        tcExa1.setPrefWidth(100);
        tcExa1.setResizable(false);
        tcExa1.setReorderable(false);
        tcExa1.setSortable(false);

        tcExa2.setPrefWidth(100);
        tcExa2.setResizable(false);
        tcExa2.setReorderable(false);
        tcExa2.setSortable(false);

        tcTP1.setPrefWidth(100);
        tcTP1.setResizable(false);
        tcTP1.setReorderable(false);
        tcTP1.setSortable(false);

        tcTP2.setPrefWidth(100);
        tcTP2.setResizable(false);
        tcTP2.setReorderable(false);
        tcTP2.setSortable(false);

        gpAjouterNotes.setHgap(5);
        gpAjouterNotes.setVgap(5);

        tpComparaison.setPrefTileWidth(100);
        tpComparaison.setMaxWidth(600);
        tpComparaison.setPrefRows(3);
        tpComparaison.setPrefColumns(5);
        tpComparaison.setPadding(new Insets(10));

        btnQuitter.setAlignment(Pos.BOTTOM_RIGHT);

        ////////////////////////////////////////////////LISTENERS///////////////////////////////////////////////////////
        
        btnAjouter.setOnAction(e -> ajouterResultat());
        btnModifier.setOnAction(e -> modifierRangee());
        btnSupprimmer.setOnAction(e -> supprimmerRangee());
        btnQuitter.setOnAction(e -> demanderSauvegarder(stage));

        txfDa.textProperty().addListener(e -> rechercherDa());

        tvNotes.getSelectionModel().selectedIndexProperty().addListener(e -> remplirChamps());

        ///////////////////////////////AJOUT DES ÉLÉMENTS DANS LES COMPOSANTES DE LAYOUT///////////////////////////////

        tvNotes.setItems(resultats);
        tvNotes.getColumns().addAll(tcDA, tcExa1, tcExa2, tcTP1, tcTP2);

        hbBoutons.getChildren().add(btnAjouter);
        hbBoutons.getChildren().add(btnModifier);
        hbBoutons.getChildren().add(btnSupprimmer);

        gpAjouterNotes.add(labDa, 0, 0);
        gpAjouterNotes.add(txfDa, 1, 0);
        gpAjouterNotes.add(labExam1, 0, 1);
        gpAjouterNotes.add(txfExa1,1 ,1);
        gpAjouterNotes.add(labExam2, 0, 2);
        gpAjouterNotes.add(txfExa2, 1, 2);
        gpAjouterNotes.add(labTp1, 0, 3);
        gpAjouterNotes.add(txfTp1, 1, 3);
        gpAjouterNotes.add(labTp2, 0, 4);
        gpAjouterNotes.add(txfTp2, 1, 4);


        vbOptions.getChildren().add(gpAjouterNotes);
        vbOptions.getChildren().add(hbBoutons);

        vbOptions.getChildren().add(btnQuitter);


        root.add(tvNotes, 0, 0);
        root.add(vbOptions, 1, 0);
        root.add(tpComparaison, 0, 1);
        root.add(btnQuitter, 1, 1);

        root.setHgap(200);
        root.setPrefWidth(Double.MAX_VALUE);
        root.setMaxWidth(Double.MAX_VALUE);
        vbOptions.setAlignment(Pos.TOP_RIGHT);
        GridPane.setFillWidth(vbOptions, true);
        GridPane.setHalignment(vbOptions, HPos.RIGHT);

        root.setPadding(new Insets(20));
        GridPane.setVgrow(tvNotes, Priority.ALWAYS);
        GridPane.setHgrow(tvNotes, Priority.ALWAYS);

        tpComparaison.getChildren().add(labMoyenne);

        ////////////////////////////////////////TABLEAUX DE TEXTFIELDS//////////////////////////////////////////////////

        for (int i = 0; i < 4; i++) {
            txfMoyennes[i] = new TextField();
            txfMoyennes[i].setEditable(false);
            tpComparaison.getChildren().add(txfMoyennes[i]);
        }
        tpComparaison.getChildren().add(labMinimum);
        for (int i = 0; i < 4; i++) {
            txfMinimums[i] = new TextField();
            txfMinimums[i].setEditable(false);
            tpComparaison.getChildren().add(txfMinimums[i]);
        }
        tpComparaison.getChildren().add(labMaximum);
        for (int i = 0; i < 4; i++) {
            txfMaximums[i] = new TextField();
            txfMaximums[i].setEditable(false);
            tpComparaison.getChildren().add(txfMaximums[i]);
        }

        ////////////////////////////////////////////////DÉMARRAGE///////////////////////////////////////////////////////

        chargerNotes();

        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Julien Clermont 2268130");
        stage.setMinHeight(500);
        stage.setMinWidth(1000);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Ajoute une feuille de résultats au tableau
     */
    private void ajouterResultat() {
        FeuilleResultat feuille = null; //Nouvelle feuille à ajouter
        boolean aucuneErreur = true; //Si aucune donnée invalide n'a été entrée

        try {
            feuille = new FeuilleResultat(Integer.parseInt(txfDa.getText()));

        } catch (IllegalArgumentException e) {
            afficherErreur("Nombre invalide", "La DA doit être un entier positif.");
            aucuneErreur = false;
        }

        if (aucuneErreur) {
            try {

                feuille.setExa1(Byte.parseByte(txfExa1.getText()));
                feuille.setExa2(Byte.parseByte(txfExa2.getText()));
                feuille.setTp1(Byte.parseByte(txfTp1.getText()));
                feuille.setTp2(Byte.parseByte(txfTp2.getText()));

            } catch (IllegalArgumentException e) {
                afficherErreur(
                        "Nombre invalide",
                        "Les notes des TP et examens doivent être entre 0 et 100."
                );
                aucuneErreur = false;
            }

            if (aucuneErreur) {
                try {
                    tvNotes.getSelectionModel().select(Utils.insererEnOrdre(resultats, feuille));

                    calculerMoyennes();

                    effacerChamps();
                } catch (IllegalArgumentException e) {
                    afficherErreur("Nombre invalide", "Cette DA existe déjà.");
                }
            }
        }
    }

    /**
     *Supprime la rangée sélectionnée
     */
    private void supprimmerRangee() {
        tvNotes.getItems().remove(tvNotes.getSelectionModel().getSelectedIndex());
        calculerMoyennes();
        effacerChamps();
    }

    /**
     * Efface le contenu de tous les champs de texte pour entrer une nouvelle feuille de résultats
     */
    private void effacerChamps() {
        txfDa.clear();
        txfExa1.clear();
        txfExa2.clear();
        txfTp1.clear();
        txfTp2.clear();
    }

    /**
     * Modifie les valeurs de la feuille de résultats sélectionnée
     */
    private void modifierRangee() {
        FeuilleResultat feuille; //feuille où il faut changer les résultats
        int indexFeuille = tvNotes.getSelectionModel().getSelectedIndex(); //Index de la feuille de résultats à modifier

        if (indexFeuille != -1) {
            feuille = tvNotes.getItems().get(indexFeuille);

            try {
                feuille.setExa1(Byte.parseByte(txfExa1.getText()));
                feuille.setExa1(Byte.parseByte(txfExa2.getText()));
                feuille.setExa1(Byte.parseByte(txfTp1.getText()));
                feuille.setExa1(Byte.parseByte(txfTp2.getText()));

                calculerMoyennes();
            } catch (IllegalArgumentException e) {
                afficherErreur(
                        "Nombre invalide",
                        "Les notes des TP et examens doivent être entre 0 et 100."
                );
            }
        }
    }

    /**
     * Remplit les champs de texte lorsqu'une feuille de notes est sélectionnée
     */
    private void remplirChamps() {

        txfDa.setText(String.valueOf(resultats.get(tvNotes.getSelectionModel().getSelectedIndex()).getDa()));

        txfExa1.setText(String.valueOf(resultats.get(tvNotes.getSelectionModel().getSelectedIndex()).getExa1()));
        txfExa2.setText(String.valueOf(resultats.get(tvNotes.getSelectionModel().getSelectedIndex()).getExa2()));
        txfTp1.setText(String.valueOf(resultats.get(tvNotes.getSelectionModel().getSelectedIndex()).getTp1()));
        txfTp2.setText(String.valueOf(resultats.get(tvNotes.getSelectionModel().getSelectedIndex()).getTp2()));
    }

    /**
     * Donne une liste de toutes les DA
     * @return liste de DA
     */
    private int[] tableauDA() {
        int[] tableau = new int[resultats.size()];
        for (int i = 0; i < tableau.length; i++) {
            tableau[i] = resultats.get(i).getDa();
        }
        return tableau;
    }

    /**
     * Recherche le numéro de DA du champ de texte dans le tableau et le sélectionne si trouvé
     */
    private void rechercherDa() {

        int da;

        try {
            da = Integer.parseInt(txfDa.getText());
            if (Utils.isPresentCol(tableauDA(), da)) {
                tvNotes.getSelectionModel().select(Utils.fouilleDichoCol(tableauDA(), da));
            }
        } catch (NumberFormatException ignored) {
        }
    }

    private void calculerMoyennes() {
        if (resultats.isEmpty()) {
            for (int i = 0; i < 4; i++) {
                txfMoyennes[i].clear();
            }

            for (int i = 0; i < 4; i++) {
                txfMinimums[i].clear();
            }

            for (int i = 0; i < 4; i++) {
                txfMaximums[i].clear();
            }
        }

        else {
        txfMoyennes[0].setText(String.valueOf(Utils.moyenneEval(tcExa1)));
        txfMoyennes[1].setText(String.valueOf(Utils.moyenneEval(tcExa2)));
        txfMoyennes[2].setText(String.valueOf(Utils.moyenneEval(tcTP1)));
        txfMoyennes[3].setText(String.valueOf(Utils.moyenneEval(tcTP2)));

        txfMinimums[0].setText(String.valueOf(Utils.minEval(tcExa1)));
        txfMinimums[1].setText(String.valueOf(Utils.minEval(tcExa2)));
        txfMinimums[2].setText(String.valueOf(Utils.minEval(tcTP1)));
        txfMinimums[3].setText(String.valueOf(Utils.minEval(tcTP2)));

        txfMaximums[0].setText(String.valueOf(Utils.maxEval(tcExa1)));
        txfMaximums[1].setText(String.valueOf(Utils.maxEval(tcExa2)));
        txfMaximums[2].setText(String.valueOf(Utils.maxEval(tcTP1)));
        txfMaximums[3].setText(String.valueOf(Utils.maxEval(tcTP2)));
        }
    }

    /**
     * Affiche un popup d'erreur puur un nombre invalide
     * @param message message d'erreur
     */
    private void afficherErreur(String enTete, String message) {
        Alert popup = new Alert(Alert.AlertType.ERROR, message);
        popup.setHeaderText(enTete);
        popup.showAndWait();
    }

    /**
     * Charge les notes du fichier notes.txt dans le tableau
     */
    private void chargerNotes() {
        File fichierNotes = new File("notes.txt");
        Scanner scan;
        FeuilleResultat feuille; //Feuille de résultats à construire à partir d'une ligne du fichier notes.txt

        String[] ligne; //Ligne du fichier notes.txt à ajouter

        try {
            scan = new Scanner(fichierNotes);

            while (scan.hasNextLine()) {

                ligne = scan.nextLine().split(" ");

                feuille = new FeuilleResultat(
                        Integer.parseInt(ligne[0]),
                        Byte.parseByte(ligne[1]),
                        Byte.parseByte(ligne[2]),
                        Byte.parseByte(ligne[3]),
                        Byte.parseByte(ligne[4])
                );
                resultats.add(feuille);
            }

            scan.close();

        } catch (FileNotFoundException | ArrayIndexOutOfBoundsException | IllegalArgumentException e) {

            afficherErreur(
                    "Fichier notes.txt corrompu",
                    "Les notes n'ont pas pu être chargées car \"notes.txt\" est illisible ou manquant"
            );
        }
        calculerMoyennes();
    }

    /**
     * Demande à l'utilisateur s'il veut sauvegarder les notes avant de quitter
     * @param parent Scène qui sera fermée pour quitter
     */
    private void demanderSauvegarder(Stage parent) {
        ButtonType btnSauvegarder = new ButtonType("Sauvegarder", ButtonBar.ButtonData.YES);
        ButtonType btnPasSauvegarder = new ButtonType("Quitter sans sauvegarder", ButtonBar.ButtonData.NO);
        ButtonType btnAnnuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert fenetreSauvegarder = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Voulez-vous sauvegarder les modifications avant de quitter?",
                btnSauvegarder,
                btnPasSauvegarder,
                btnAnnuler
        );

        fenetreSauvegarder.setHeaderText("Sauvegarder?");
        fenetreSauvegarder.showAndWait().ifPresent(e -> {
            if (e != btnAnnuler) {

                if (e == btnSauvegarder) {
                    sauvegarderNotes();
                }

                parent.close();
            }
        });
    }

    /**
     * Sauvegarde les notes du tableau dans le fichier notes.txt
     */
    public void sauvegarderNotes() {
        try {
            PrintWriter printWriter = new PrintWriter(new File("notes.txt"));

            for (FeuilleResultat resultat : resultats) {
                printWriter.println(

                        resultat.getDa() + " " +
                        resultat.getExa1() + " " +
                        resultat.getExa2() + " " +
                        resultat.getTp1() + " " +
                        resultat.getTp2() + " "
                );
            }

            printWriter.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Méthode qui sera exécutée pour démarrer le programme
     * @param args Les arguments ne sont pas pris en compte.
     */
    public static void main(String[] args) {
        launch();
    }

}
