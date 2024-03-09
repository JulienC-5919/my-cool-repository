package com.example.applications;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;


class Utils {

    /**
     * Calcule la moyenne de tous les entiers sur une TableColumn
     * @param liste TableColumn qui contient les entiers
     * @return moyenne de tous les nombres
     */
    public static int moyenneEval(TableColumn liste) {
        int total = 0;
        int longueur = liste.getTableView().getItems().size();

        for (int i = 0; i < longueur; i++) {
            total += Integer.parseInt(liste.getCellData(i).toString());
        }

        return total / longueur;
    }

    /**
     * Donne la plus petite valeur trouvée dans les cellules d'une TableColumn
     * @param liste TableColumn où chercher la valeur minimum
     * @return plus petite valeur trouvée dans la TableColumn
     */
    public static int minEval(TableColumn liste) {
        byte minimum = Byte.parseByte(liste.getCellData(0).toString()); //Plus petit résultat trouvé
        byte element; //Élément à comparer

        for (int i = 1; i < liste.getTableView().getItems().size(); i++) {
            element = Byte.parseByte(liste.getCellData(i).toString());

            if (element < minimum) {
                minimum = element;
            }
        }

        return minimum;
    }
    /**
     * Donne la plus grande valeur trouvée dans les cellules d'une TableColumn
     * @param liste TableColumn où chercher la valeur maximum
     * @return plus grande valeur trouvée dans la TableColumn
     */

    public static int maxEval(TableColumn liste) {
        byte maximum = Byte.parseByte(liste.getCellData(0).toString()); //Plus grand résultat trouvé
        byte element; //Élément à comparer

        for (int i = 1; i < liste.getTableView().getItems().size(); i++) {
            element = Byte.parseByte(liste.getCellData(i).toString());

            if (element > maximum) {
                maximum = element;
            }
        }

        return maximum;
    }

    /**
     * Effectue une fouille dichotomique pour trouver l'indice d'un élément dans un tableau d'entiers trié
     * @param tableau tableau d'entiers où chercher la valeur
     * @param element valeur à trouver
     * @return indice de la valeur donnée
     */
    public static int fouilleDichoCol(int[] tableau, int element) {
        int positionMinimum = -1; //Position minimum exclue
        int centre = tableau.length / 2;
        int positionMaximum = tableau.length; //Position maximum exclue
        boolean pasTrouve = true;

        while (positionMaximum > positionMinimum + 1 && pasTrouve) {
            if (element < tableau[centre]) {
                positionMaximum = centre;
            }
            else if (element > tableau[centre]) {
                positionMinimum = centre;
            }
            else {
                pasTrouve = false;
            }

            if (pasTrouve) {
            centre = (positionMaximum - positionMinimum) / 2 + positionMinimum;
            }
        }

        if (pasTrouve) {
            centre = -1;
        }
        return centre;
    }

    /**
     * Insertion dichotomique d'une feuille de résultats dans une ObservableList, triée par DA
     * @param liste ObservableList dans laquelle ajouter la nouvelle feuille de notes
     * @param resultat feuille de notes à ajouter
     * @return indice où la feuille de notes a été ajoutée
     */
    public static int insererEnOrdre(ObservableList<FeuilleResultat> liste, FeuilleResultat resultat) {
        int positionMinimum = -1;
        int positionMaximum = liste.size();
        int centre = (positionMaximum + 1) / 2 - 1;

        while (positionMaximum > positionMinimum + 1) {
            if (resultat.getDa() < liste.get(centre).getDa()) {
                positionMaximum = centre;
            }
            else if (resultat.getDa() > liste.get(centre).getDa()) {
                positionMinimum = centre;
            }
            else {
                throw new IllegalArgumentException("Cette DA existe déjà.");
            }
            centre = (positionMaximum - positionMinimum) / 2 + positionMinimum;
        }

        liste.add(positionMaximum, resultat);

        return positionMaximum;
    }

    /**
     * Fait un quicksort d'un tableau d'entiers puis fait une fouille dichotomique pour voir si un élément est présent
     * @param tableau tableau à trier et fouiller
     * @param element élément à vérifier s'il est présent dans le tableau
     * @return true si l'élément est dans le tableau, false sinon.
     */
    public static boolean isPresentCol(int[] tableau, int element) {
        quickSort(tableau);
        return fouilleDichoCol(tableau, element) != -1;
    }

    /**
     * Trie un tableau d'entiers par l'algorithme quicksort
     * @param entiers tableau à trier
     */
    public static void quickSort(int[] entiers) {
        quickSort(entiers, 0, entiers.length - 1);
    }

    /**
     * Trie une partie d'un tableau d'entiers par l'algorithme quicksort
     * @param entiers tableau à trier
     * @param debut indice du premier élément à trier
     * @param fin indice du dernier élément à trier
     */
    public static void quickSort(int[] entiers, int debut, int fin) {
        if (debut < fin) {
            boolean indexADeplacer = true; //true pour l'index 2, false pour l'index 1
            int index1 = debut;
            int index2 = fin;

            while (index1 != index2) {
                if (entiers[index1] > entiers[index2]) {
                    entiers[index1] ^= entiers[index2];
                    entiers[index2] ^= entiers[index1];
                    entiers[index1] ^= entiers[index2];
                    indexADeplacer = !indexADeplacer;
                } else {
                    if (indexADeplacer) {
                        index2--;
                    } else {
                        index1++;
                    }
                }
            }

            quickSort(entiers, debut, index1 - 1);

            quickSort(entiers, index2 + 1, fin);
        }
    }
}
