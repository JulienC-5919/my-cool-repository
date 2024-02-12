package com.example.test;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class testApp extends Application {

    public void start(Stage stage){
        StackPane root = new StackPane();
        VBox realFortnite = new VBox();
        GridPane fortnite = new GridPane();
        FlowPane floss = new FlowPane();
        floss.setHgap(5);
        fortnite.setVgap(5);
        fortnite.setHgap(25);
        realFortnite.setSpacing(10);

        Label labNom = new Label("Nom d'utilisateur:");
        Label labMotDePasse = new Label("Mot de passe:");

        TextField txfNom = new TextField();
        PasswordField txfMotDePasse = new PasswordField();

        Button btnConnecter = new Button("Se connecter");
        Button btnEffacer = new Button("Effacer");
        btnEffacer.setOnMouseClicked(e -> System.out.println("fff"));

        CheckBox ckbSouvenir = new CheckBox("Se souvenir de moi");

        floss.getChildren().add(btnEffacer);
        floss.getChildren().add(ckbSouvenir);
        floss.getChildren().add(btnConnecter);

        realFortnite.getChildren().add(fortnite);
        realFortnite.getChildren().add(floss);

        fortnite.addRow(0, labNom, txfNom);
        fortnite.addRow(1, labMotDePasse, txfMotDePasse);

        root.getChildren().add(realFortnite);






        Scene scene = new Scene(root, 280, 100);
        stage.setTitle("Surtout pas en train de voler ton mot de passe ;)");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
