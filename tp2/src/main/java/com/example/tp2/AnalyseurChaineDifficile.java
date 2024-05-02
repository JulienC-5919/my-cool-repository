package com.example.tp2;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Analyse des chaînes de caractères (String) qui peuvent contenir des erreurs puis les convertit le mieux possible
 * en la valeur demandée, sans lancer d'exception
 */
public final class AnalyseurChaineDifficile {
    private AnalyseurChaineDifficile() {
        throw new UnsupportedOperationException("Cette classe n'est pas instanciable");
    }

    /**
     * Convertit une String difficile en int positif
     * @param s String à convertir
     * @return s converti en int
     */
    public static int parseIntPositif(String s) {
        int retour; // Entier à retourner

        s = s.replaceAll("\\D", "");

        if (s.isEmpty()) {
            retour = 0;
        } else {
            try {
                retour = Integer.parseInt(s);

            //Si l'exception est lancée, le s est trop grand pour un int, alors remplacer par la valeur maximum
            } catch (NumberFormatException e) {
                retour = Integer.MAX_VALUE;
            }
        }

        return retour;
    }

    /**
     * Convertit une String difficile en double, max 2 chiffres après la virgule.
     * @param s String à convertir
     * @return s converti en double, arrondi au centième
     */
    public static double parseDoubleArgent(String s) {
        StringBuilder builder = new StringBuilder(); //Contient les chiffres valides et la virgule
        byte decimales = 0; // Chiffres trouvés après la virgule
        int i = 0; //Itération dans la String



        while (i < s.length() && s.charAt(i) != '.' && s.charAt(i) != ',') {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                builder.append(s.charAt(i));
            }

            i++;
        }

        if (builder.isEmpty()) {
            builder.append('0');
        }
        builder.append('.');
        i++;
        while (i < s.length() && decimales < 2) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                builder.append(s.charAt(i));
                decimales++;
            }

            i++;
        }
        return Double.parseDouble(builder.toString());
    }

    /**
     * Convertit une String difficile en LocalDate
     * Les dates avant Jésus Christ ne sont pas supportées.
     * @param s String à convertir
     * @return s converti en LocalDate
     */
    public static LocalDate parseLocalDate(String s) {
        s = s.replaceAll("[^0-9-]", "");
        LocalDate retour; //Date à retourner

        try {
            retour = separerDateTirets(s);
        } catch (IllegalArgumentException e) {
            retour = genererDateChiffres(s);
        }

        return retour;
    }

    private static LocalDate separerDateTirets(String s) {

        ArrayList<String> separation; // Séparation année / mois / jour de la chaîne
        Short annee;
        byte mois;
        byte jour;

        byte jourMax; // Dernier jour du mois

        separation = new ArrayList<String>(Arrays.asList(s.split("-")));
        separation.removeIf(String::isEmpty);

        if (separation.size() >= 3) {
            try {

                annee = Short.parseShort(separation.getFirst());

                if (annee > 9999) {
                    annee = 9999;
                }
            } catch (NumberFormatException e) {
                annee = 9999;
            }

            try {

                mois = Byte.parseByte(separation.get(1));

                if (mois < 1) {
                    mois = 1;
                } else if (mois > 12) {
                    mois = 12;
                }
            } catch (NumberFormatException e) {
                mois = 12;
            }

            jourMax = dernierJourMois(annee, mois);

            try {
                jour = Byte.parseByte(separation.get(2));
                if (jour < 1) {
                  jour = 1;
                } else if (jour > jourMax) {
                    jour = jourMax;
                }
            } catch (NumberFormatException e) {
                jour = jourMax;
            }

        } else {
            throw new IllegalArgumentException();
        }

        return LocalDate.of(annee, mois, jour);
    }

    private static LocalDate genererDateChiffres(String s) {
        short annee;
        byte mois;
        byte jour;

        byte jourMax;

        s = s.replaceAll("-", "");

        if (s.length() > 8) {
            s = s.substring(0, 8);
        } else if (s.length() < 8) {
            s = "0".repeat(8 - s.length()) + s;
        }

        annee = Short.parseShort(s.substring(0, 4));

        mois = Byte.parseByte(s.substring(4, 6));
        if (mois < 1) {
          mois = 1;
        } else if (mois > 12) {
            mois = 12;
        }

        jourMax = dernierJourMois(annee, mois);

        jour = Byte.parseByte(s.substring(6, 8));
        if (jour < 1) {
            jour = 1;
        } else if (jour > jourMax) {
            jour = jourMax;
        }

        return LocalDate.of(annee, mois, jour);
    }

    /**
     * Donne le dernier jour du mois, donné, supporte les années bissextiles
     * @param annee Année dont le mois fait partie
     * @param mois mois dont on veut savoir le dernier jour
     * @return Dernier jour du mois donné
     */
    private static byte dernierJourMois(Short annee, byte mois) {
        byte jourMax;

        if (mois == 4 || mois == 6 || mois == 9 || mois == 11) {
            jourMax = 31;
        }
        else if (mois == 2) {
            if ((annee & 3) == 0) {
                jourMax = 29;
            } else {
                jourMax = 28;
            }
        } else {
            jourMax = 30;
        }

        return jourMax;
    }
}
