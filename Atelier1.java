public class Atelier1 {
    /**
     * Trie un tableau d'entiers en ordre croissant, tri par sélection
     * @param tableau tableau à trier
     */
    public static void trieCroissantSSS(int[] tableau) {
        int imin; // Index du plus petit entier trouvé parmi ceux qui n'ont pas encore été triés

        for (int i = 0; i < tableau.length - 1; i++) {
            imin = i;

            for (int j = i + 1; j < tableau.length; j++) {
                if (tableau[j] < tableau[imin]) {
                    imin = j;
                }
            }

            if (imin != i) {
                tableau[i] ^= tableau[imin];
                tableau[imin] ^= tableau[i];
                tableau[i] ^= tableau[imin];
            }
        }
    }
    /**
     * Trie un tableau de chaînes en ordre croissant, tri par sélection
     * @param tableau tableau à trier
     */
    public static void trieCroissantSSS(String[] tableau) {
        int imin; // Index du plus petit entier trouvé parmi ceux qui n'ont pas encore été triés
        String tmp;

        for (int i = 0; i < tableau.length - 1; i++) {
            imin = i;

            for (int j = i + 1; j < tableau.length; j++) {
                if (vaAvant(tableau[j], tableau[imin])) {
                    imin = j;
                }
            }

            if (imin != i) {
                tmp = tableau[i];
                tableau[i] = tableau[imin];
                tableau[imin] = tmp;
            }
        }
    }
    /**
     * Trie un tableau d'entiers en ordre décroissant, tri par sélection
     * @param tableau tableau à trier
     */
    public static void trieDecroissantSSS(int[] tableau) {
        int imax; // Index du plus grand entier trouvé parmi ceux qui n'ont pas encore été triés, rien à voir avec les cinémas.

        for (int i = 0; i < tableau.length - 1; i++) {
            imax = i;

            for (int j = i + 1; j < tableau.length; j++) {
                if (tableau[j] > tableau[imax]) {
                    imax = j;
                }
            }

            if (imax != i) {
                tableau[i] ^= tableau[imax];
                tableau[imax] ^= tableau[i];
                tableau[i] ^= tableau[imax];
            }
        }
    }
    /**
     * Trie un tableau de chaînes en ordre décroissant, tri par sélection
     * @param tableau tableau à trier
     */
    public static void trieDecroissantSSS(String[] tableau) {
        int imax; // Index du plus grand entier trouvé parmi ceux qui n'ont pas encore été triés, rien à voir avec les cinémas.
        String tmp;

        for (int i = 0; i < tableau.length - 1; i++) {
            imax = i;

            for (int j = i + 1; j < tableau.length; j++) {
                if (vaAvant(tableau[imax], tableau[j])) {
                    imax = j;
                }
            }

            if (imax != i) {
                tmp = tableau[i];
                tableau[i] = tableau[imax];
                tableau[imax] = tmp;
            }
        }
    }

    public static int maximum(int[] tableau) {
        int maximum = tableau[0]; //plus grand nombre trouvé
        for (int i = 1; i < tableau.length; i++) {
            if (tableau[i] > maximum) {
                maximum = tableau[i];
            }
        }
        return maximum;
    }

    public static int minimum(int[] tableau) {
        int minimum = tableau[0]; //plus petit nombre trouvé
        for (int i = 1; i < tableau.length; i++) {
            if (tableau[i] > minimum) {
                minimum = tableau[i];
            }
        }
        return minimum;
    }

    public static int somme(int[] tableau) {
        int somme = 0; // Somme de tous les entiers du tableau
        for (int j : tableau) {
            somme += j;
        }

        return somme;
    }

    public static int moyenne(int[] tableau) {
        return somme(tableau) / tableau.length;
    }

    public static int fouillleSeq(int[] tableau, int entier) {
        boolean pasTrouve = true; // Si l'entier n'a pas encore été trouvé dans le tableau
        int i = 0;
        while (i < tableau.length && pasTrouve) {
            pasTrouve = tableau[i] != entier;
            i++;
        }

        if (pasTrouve) {
            return -1;
        }
        else {
            return i - 1;
        }
    }

    public static int fouillleSeq(String[] tableau, String chaine) {
        boolean pasTrouve = true; // Si la chaîne n'a pas encore été trouvée dans le tableau
        int i = 0;
        while (i < tableau.length && pasTrouve) {
            pasTrouve = !tableau[i].equals(chaine);
            i++;
        }

        if (pasTrouve) {
            return -1;
        }
        else {
            return i - 1;
        }
    }

    private static boolean vaAvant(String string1, String string2) {
        boolean continuer = true; //Si on ne sait pas encore quelle chaîne doit être placée en premier
        boolean vaAvant = false; //Détermine si la première chaîne va avant la deuxième en ordre ASCII.
        int i = 0;
        while (i < Math.min(string1.length(), string2.length()) && continuer) {
            if (string1.charAt(i) < string2.charAt(i)) {
                vaAvant = true;
                continuer = false;
            }
            else if (string1.charAt(i) > string2.charAt(i)) {
                continuer = false;
            }
            i++;
        }
        if (continuer) {
            return string1.length() < string2.length();
        }
        else return vaAvant;
    }
}