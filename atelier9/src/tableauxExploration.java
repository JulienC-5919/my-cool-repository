public class tableauxExploration {
    public static void initTen(int[][] tableau) {
        for (int i = 0; i < tableau.length; i++) {
            for (int j = 0; j < tableau[i].length; j++) {
                tableau[i][j] = 10;
            }
        }
    }

    public static void initPosition(String[][] tableau) {
        for (int i = 0; i < tableau.length; i++) {
            for (int j = 0; j < tableau[i].length; j++) {
                tableau[i][j] = i + ":" + j;
            }
        }
    }

    public static boolean isTabCarre(int[][] tableau) {
        return tableau.length == tableau[0].length;
    }

    public static boolean isTabDimEgal(int[][] tab1, int[][] tab2) {
        return tab1.length == tab2.length && tab1[0].length == tab2[0].length;
    }
}