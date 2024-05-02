package com.example.tp2;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
@Deprecated
public final class CorrecteursTextFields{
    private CorrecteursTextFields() {
        throw new UnsupportedOperationException("Cette classe n'est pas instanciable.");
    }

    public static void ajouterCorrecteurEntier(TextField textField) {
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                String texte = textField.getText(); //Texte du TextField avant modification

                if (!t1 && texte.matches("\\d{1,9}")) {

                    textField.setText(texte.replaceAll("\\D", "").substring(0, 8));
                }
            }
        });
    }

    public static void ajouterCorrecteurAnnee(TextField textField) {
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                String texte = textField.getText(); //Texte du TextField avant modification

                if (!t1 && texte.matches("\\d{1,4}")) {

                    textField.setText(texte.replaceAll("\\D", "").substring(0, 3));
                }
            }
        });
    }

    public static void ajouterCorrecteurPrix(TextField textField) {
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                String texte = textField.getText(); //Texte du TextField avant modification

                if (!(t1 || texte.matches("\\d+,\\d{2}\\$"))) {

                    byte decimales = 0; // Chiffres trouvés après la virgule
                    int i = 0; //Itération dans la String
                    StringBuilder builder = new StringBuilder();

                    while (i < texte.length() && builder.length() < 8 && texte.charAt(i) != '.' && texte.charAt(i) != ',') {
                        if (texte.charAt(i) >= '0' && texte.charAt(i) <= '9') {
                            builder.append(texte.charAt(i));
                        }

                        i++;
                    }
                    //Si la valeur est trop grande, la mettre au maximum.
                    if (builder.length() == 8) {

                        textField.setText("9999999,99$");

                    } else {
                        if (builder.isEmpty()) {
                            builder.append('0');
                        }
                        builder.append(',');
                        i++;
                        while (i < texte.length() && decimales < 2) {
                            if (texte.charAt(i) >= '0' && texte.charAt(i) <= '9') {
                                builder.append(texte.charAt(i));
                                decimales++;
                            }

                            i++;
                        }
                        builder.append("0".repeat(2 - decimales)).append('$');
                        textField.setText(builder.toString());
                    }
                }
            }
        });
    }

    public static void ajouterCorrecteurPrix2(TextField textField) {
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!t1) {

                }
            }
        });
    }
}
