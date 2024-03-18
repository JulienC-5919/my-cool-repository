package com.example.applications;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;


public class Utils {

    /**
     * Empêche la classe d'être instanciée
     */
    private Utils(){
        throw new UnsupportedOperationException("Cette classe ne peut pas être instanciée");
    }

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
     * Fait un quicksort d'un tableau d'entiers puis fait une fouille dichotomique pour voir si un élément est présent
     * @param tableau tableau à trier et fouiller
     * @param element élément à vérifier s'il est présent dans le tableau
     * @return true si l'élément est dans le tableau, false sinon.
     */
    public static boolean isPresentCol(ObservableList<FeuilleResultat> tableau, int element) {
        quickSort(tableau);
        return fouilleDichoCol(tableauEntiersDA(tableau), element) != -1;
    }

    /**
     * Trie un tableau d'entiers par l'algorithme quicksort
     * @param entiers tableau à trier
     */
    public static void quickSort(ObservableList<FeuilleResultat> entiers) {
        quickSort(entiers, 0, entiers.size() - 1);
    }

    /**
     * Trie une partie d'un tableau d'entiers par l'algorithme quicksort
     * @param entiers tableau à trier
     * @param debut indice du premier élément à trier
     * @param fin indice du dernier élément à trier
     */
    public static void quickSort(ObservableList<FeuilleResultat> entiers, int debut, int fin) {
        if (debut < fin) {
            boolean indexADeplacer = true; //true pour l'index 2, false pour l'index 1
            int index1 = debut;
            int index2 = fin;

            FeuilleResultat tmp; //Emplacement temporaire pour échanger les feuilles de DA

            while (index1 != index2) {
                if (entiers.get(index1).getDa() > entiers.get(index2).getDa()) {

                    tmp = entiers.get(index1);
                    entiers.set(index1, entiers.get(index2));
                    entiers.set(index2, tmp);

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

    /**
     * Donne une liste de toutes les DA
     * @return liste de DA
     */
    public static int[] tableauEntiersDA(ObservableList<FeuilleResultat> resultats) {
        int[] tableau = new int[resultats.size()];
        for (int i = 0; i < tableau.length; i++) {
            tableau[i] = resultats.get(i).getDa();
        }
        return tableau;
    }
}
