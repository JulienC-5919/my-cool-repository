package com.example.applications;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.control.Button;

import java.util.Random;
import java.util.jar.JarFile;


public class DevineLeNombre extends Application {

    private int nombreAleatoire;

    private Button[] boutons;
    private byte essaisRestants;
    private Label labEssaisRestants;
    private Label labMauvaisNombre;

    public void start(Stage stage){

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 500, 700);

        GridPane grilleBoutons = new GridPane();

        labEssaisRestants = new Label("Essais restants: 8");
        labEssaisRestants.setFont(new Font(40));
        BorderPane.setAlignment(labEssaisRestants, Pos.CENTER);

        labMauvaisNombre = new Label();
        labMauvaisNombre.setFont(new Font(40));
        BorderPane.setAlignment(labMauvaisNombre, Pos.CENTER);

        grilleBoutons.setHgap(10);
        grilleBoutons.setVgap(10);
        grilleBoutons.setPadding(new Insets(10));
        grilleBoutons.setAlignment(Pos.CENTER);

        boutons = new Button[100];

        for (int i = 0; i < 100; i++) {
            boutons[i] = new Button(String.valueOf(i + 1));
            boutons[i].setPrefSize(40, 40);

            int finalI = i;
            boutons[i].setOnAction(e -> essayerNombre(finalI));

            grilleBoutons.add(boutons[i], i%10, i/10);
        }

        nouveauNombreAleatore();

        root.setTop(labMauvaisNombre);
        root.setCenter(grilleBoutons);
        root.setBottom(labEssaisRestants);

        stage.setTitle("Titre");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        Popup p = new Popup();
        p.getContent().add(new Button("no"));
        p.show(stage);
        Stage stage2 = new Stage();
        stage.initOwner(stage2.getScene().getWindow());
    }

    private void essayerNombre(int nombre) {
        System.out.println(nombre);
        if (nombre < nombreAleatoire) {

            for (int i = 0; i <= nombre; i++) {
                boutons[i].setDisable(true);
            }
            essaisRestants--;
            labEssaisRestants.setText("Essais restants: " + essaisRestants);
            labMauvaisNombre.setText(nombre + 1 + ": Trop bas");

        } else if (nombre > nombreAleatoire) {

            for (int i = nombre; i < 100; i++) {
                boutons[i].setDisable(true);
            }
            essaisRestants--;
            labEssaisRestants.setText("Essais restants: " + essaisRestants);
            labMauvaisNombre.setText(nombre + 1 + ": Trop haut");

        }
    }

    private void nouveauNombreAleatore() {
        Random r = new Random();
        nombreAleatoire = r.nextInt(100);

        essaisRestants = 8;
        labEssaisRestants.setText("Essais restants : 8");
    }

    public static void main(String[] args) {
        launch();
    }
}
