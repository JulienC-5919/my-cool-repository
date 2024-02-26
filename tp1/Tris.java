import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random r = new Random();
        int[] ints = new int[64];
        for (int i = 0; i < 64; i++) {
            ints[i] = r.nextInt(8192);
        }
        quickSort(ints);
        System.out.println(Arrays.toString(ints));
    }

    public static void quickSort(int[] entiers) {
        quickSort(entiers, 0, entiers.length - 1);
    }

    public static void quickSort(int[] entiers, int debut, int fin) {
        boolean indexADeplacer = true; //true pour l'index 2, false pour l'index 1
        int index1 = debut;
        int index2 = fin;

        while (index1 != index2) {
            if (entiers[index1] > entiers[index2])
            {
                    entiers[index1] ^= entiers[index2];
                    entiers[index2] ^= entiers[index1];
                    entiers[index1] ^= entiers[index2];
                    indexADeplacer = !indexADeplacer;
            }
            else {
                if (indexADeplacer) {
                    index2--;
                }
                else {
                    index1++;
                }
            }
        }

        if (index1 != debut) {
            quickSort(entiers, debut, index1 - 1);
        }
        if (index2 != fin) {
            quickSort(entiers, index2 + 1, fin);
        }
    }

    public static boolean estTrie(int[] entiers) {
        int i = 0;
        boolean aucunMauvaisNombreTrouve = true;
        while (i < entiers.length - 1 && aucunMauvaisNombreTrouve) {
            aucunMauvaisNombreTrouve = entiers[i] <= entiers[i+1];
            i++;
        }
        return aucunMauvaisNombreTrouve;
    }
}