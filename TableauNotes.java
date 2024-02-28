package com.example.applications;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class TableauNotes extends Application {
    public void start(Stage stage){
        GridPane root = new GridPane();
        root.setHgap(200);

        TableView tvNotes = new TableView();
        VBox vbOptions = new VBox(5);
        TilePane tpComparaison = new TilePane();
        Button btnQuitter = new Button("Quitter");
        GridPane.setHalignment(btnQuitter, HPos.RIGHT);
        GridPane.setValignment(btnQuitter, VPos.BOTTOM);

        TableColumn tcDA = new TableColumn("DA");
        TableColumn tcExa1 = new TableColumn("Exa1");
        TableColumn tcExa2 = new TableColumn("Exa2");
        TableColumn tcTP1 = new TableColumn("TP1");
        TableColumn tcTP2 = new TableColumn("TP1");

        GridPane gpAjouterNotes = new GridPane();
        HBox hbBoutons = new HBox(5);

        Label labMoyenne = new Label("Moyenne: ");
        Label labMinimum = new Label("Min: ");
        Label labMaximum = new Label("max: ");

        TextField[] txfMoyennes = new TextField[4];
        TextField[] txfMinimums = new TextField[4];
        TextField[] txfMaximums = new TextField[4];

        Label labDa = new Label("DA");
        TextField txfDa = new TextField();

        Label labExam1 = new Label("Exam1");
        TextField txfExam1 = new TextField();

        Label labExam2 = new Label("Exam2");
        TextField txfExam2 = new TextField();

        Label labTp1 = new Label("TP1");
        TextField txfTp1 = new TextField();

        Label labTp2 = new Label("TP2");
        TextField txfTp2 = new TextField();

        Button btnAjouter = new Button("Ajouter");
        Button btnModifier = new Button("Modifier");
        Button btnSupprimmer = new Button("Supprimer");

        tcDA.setPrefWidth(100);
        tcExa1.setPrefWidth(100);
        tcExa2.setPrefWidth(100);
        tcTP1.setPrefWidth(100);
        tcTP2.setPrefWidth(100);

        tvNotes.getColumns().addAll(tcDA, tcExa1, tcExa2, tcTP1, tcTP2);

        hbBoutons.getChildren().add(btnAjouter);
        hbBoutons.getChildren().add(btnModifier);
        hbBoutons.getChildren().add(btnSupprimmer);

        gpAjouterNotes.setHgap(5);
        gpAjouterNotes.setVgap(5);
        gpAjouterNotes.add(labDa, 0, 0);
        gpAjouterNotes.add(txfDa, 1, 0);
        gpAjouterNotes.add(labExam1, 0, 1);
        gpAjouterNotes.add(txfExam1,1 ,1);
        gpAjouterNotes.add(labExam2, 0, 2);
        gpAjouterNotes.add(txfExam2, 1, 2);
        gpAjouterNotes.add(labTp1, 0, 3);
        gpAjouterNotes.add(txfTp1, 1, 3);
        gpAjouterNotes.add(labTp2, 0, 4);
        gpAjouterNotes.add(txfTp2, 1, 4);

        tpComparaison.setPrefTileWidth(100);
        tpComparaison.setMaxWidth(600);
        tpComparaison.setPrefRows(3);
        tpComparaison.setPrefColumns(5);
        tpComparaison.setPadding(new Insets(10));
        tpComparaison.getChildren().add(labMoyenne);
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

        vbOptions.getChildren().add(gpAjouterNotes);
        vbOptions.getChildren().add(hbBoutons);
        btnQuitter.setAlignment(Pos.BOTTOM_RIGHT);
        vbOptions.getChildren().add(btnQuitter);


        root.add(tvNotes, 0, 0);
        root.add(vbOptions, 1, 0);
        root.add(tpComparaison, 0, 1);
        root.add(btnQuitter, 1, 1);

        root.setPrefWidth(Double.MAX_VALUE);
        root.setMaxWidth(Double.MAX_VALUE);
        vbOptions.setAlignment(Pos.TOP_RIGHT);
        GridPane.setFillWidth(vbOptions, true);
        GridPane.setHalignment(vbOptions, HPos.RIGHT);

        root.setPadding(new Insets(20));
        GridPane.setVgrow(tvNotes, Priority.ALWAYS);
        GridPane.setHgrow(tvNotes, Priority.ALWAYS);

        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Notes");
        stage.setMinHeight(500);
        stage.setMinWidth(1000);
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}
