package com.example.applications;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TableauNotes extends Application {
    public void start(Stage stage){
        GridPane root = new GridPane();


        TableView tvNotes = new TableView();
        VBox vbOptions = new VBox();
        TilePane tpComparaison = new TilePane();
        Button btnQuitter = new Button("Quitter");

        GridPane gpAjouterNotes = new GridPane();
        HBox boxBoutons = new HBox();

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

        boxBoutons.getChildren().add(btnAjouter);
        boxBoutons.getChildren().add(btnModifier);
        boxBoutons.getChildren().add(btnSupprimmer);

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

        tpComparaison.setPrefColumns(5);
        tpComparaison.getChildren().add(labMoyenne);
        //for (int i = 0; i < 4; i++) {
        //    tpComparaison.getChildren().add(txfMoyennes[i]);
        //}
        //tpComparaison.getChildren().add(labMinimum);
        //for (int i = 0; i < 4; i++) {
        //    tpComparaison.getChildren().add(txfMinimums[i]);
        //}
        //tpComparaison.getChildren().add(labMaximum);
        //for (int i = 0; i < 4; i++) {
        //    tpComparaison.getChildren().add(txfMaximums[i]);
        //}

        vbOptions.getChildren().add(gpAjouterNotes);
        vbOptions.getChildren().add(boxBoutons);

        root.add(tvNotes, 0, 0);
        root.add(vbOptions, 1, 0);
        //root.add(tpComparaison, 0, 1);
        root.add(btnQuitter, 1, 1);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(50);
        root.getColumnConstraints().add(columnConstraints);


        root.setPrefWidth(Double.MAX_VALUE);
        root.setMaxWidth(Double.MAX_VALUE);
        vbOptions.setAlignment(Pos.TOP_RIGHT);
        vbOptions.setStyle("-fx-background-color: cornsilk;");
        GridPane.setFillWidth(vbOptions, true);
        GridPane.setHalignment(vbOptions, HPos.RIGHT);

        Scene scene = new Scene(root, 500, 500);
        stage.setTitle("Notes");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}
