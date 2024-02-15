package com.example.applications;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.text.DecimalFormat;

public class Diviser extends Application {
    /**
     * Affiche une fenêtre qui permet de diviser rapidement
     * @param stage
     */
    public void start(Stage stage){
        StackPane root = new StackPane(); //Panneau principal
        HBox principale = new HBox(); //HBox dans laquelle toutes les autres composantes seront placées
        TextField dividende = new TextField(); //Champ dans lequel on entre la dividende
        TextField diviseur = new TextField(); //Champ dans lequel on entre le diviseur
        TextField resultat = new TextField(); //Champ dans lequel le résultat de la division est donné
        Label labSigne = new Label(" ÷ "); //Signe de division à placer entre la dividende et le diviseur
        Label labEgale = new Label(" = "); //Signe d'égalité à placer entre le calcul et le résultat

        resultat.setEditable(false);

        diviseur.textProperty().addListener(e -> diviser(dividende, diviseur, resultat));
        dividende.textProperty().addListener(e -> diviser(dividende, diviseur, resultat));

        principale.getChildren().add(dividende);
        principale.getChildren().add(labSigne);
        principale.getChildren().add(diviseur);
        principale.getChildren().add(labEgale);
        principale.getChildren().add(resultat);

        root.getChildren().add(principale);

        Scene scene = new Scene(root, 500, 50);
        stage.setTitle("Diviser");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }

    /**
     * Prend les valeurs de deux champs de texte, les divise puis affiche le résultat dans un autre champ de texte.
     * @param dividende Champ de texte qui contient la dividende
     * @param diviseur Champ de texte qui contient le diviseur
     * @param resultat Chaamp de texte dans lequel écrie le résultat
     */
    private void diviser(TextField dividende, TextField diviseur, TextField resultat) {
        double dividendeDouble; //Valeur du champ de texte de la dividende, converti en double.
        double diviseurDouble; //Valeur du champ de texte de la aleur du diviseur, convertie en double.

        DecimalFormat df = new DecimalFormat(); //Pour afficher le résultat avec 2 chiffres après la virgule
        df.setMaximumFractionDigits(2);

        if (diviseur.getText().isEmpty() || dividende.getText().isEmpty()) {
            resultat.setText("");
        }
        else {

            try {
                dividendeDouble = Double.parseDouble(dividende.getText());
                diviseurDouble = Double.parseDouble(diviseur.getText());

                if (diviseurDouble == 0.0) {
                    resultat.setText("Erreur Division par 0");
                }
                else {
                    resultat.setText(String.valueOf(dividendeDouble / diviseurDouble));
                }
            }
            catch (NumberFormatException e) {
                resultat.setText("Erreur Nombre invalide");
            }
        }
    }
}
