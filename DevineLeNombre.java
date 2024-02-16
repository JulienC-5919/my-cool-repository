package com.example.applications;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.control.Button;



public class DevineLeNombre extends Application {
    public void start(Stage stage){
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 500, 700);

        GridPane grilleBoutons = new GridPane();

        Label labEssaisRestants = new Label("Essais restants: 8");
        labEssaisRestants.setAlignment(Pos.TOP_CENTER);

        grilleBoutons.setHgap(10);
        grilleBoutons.setVgap(10);
        grilleBoutons.setPadding(new Insets(10));
        grilleBoutons.setAlignment(Pos.CENTER);

        Button[] boutons = new Button[100];

        for (int i = 0; i < 100; i++) {
            boutons[i] = new Button(String.valueOf(i + 1));
            boutons[i].setPrefSize(40, 40);

            grilleBoutons.add(boutons[i], i%10, i/10);
        }

        root.getChildren().add(labEssaisRestants);
        root.getChildren().add(grilleBoutons);

        stage.setTitle("Titre");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private static void essayerNombre(int nombre) {
        System.out.println(nombre);
    }
}
