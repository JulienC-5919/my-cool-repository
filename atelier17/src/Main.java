import java.util.*;

public class Main {
    public static void main(String[] args) {
        testsArrayLists();
        testsHashSets();
        testsHashMaps();
    }

    private static void testsArrayLists() {
        ArrayList<String> listFrameworks;
        ArrayList<String> listLangages;
        ArrayList<Integer> arrayListEntiers;

        int[] tab = {1,2,3,4,5,6,7,8,9};

        listFrameworks = new ArrayList<String>(Arrays.asList("Ada", "C#", "Java", "Php"));
        listFrameworks.sort(Comparator.naturalOrder());
        System.out.println(listFrameworks);

        listFrameworks = new ArrayList<String>(Arrays.asList("Ada", "C#", "Java", "Php"));
        Collections.reverse(listFrameworks);
        System.out.println(listFrameworks);


        arrayListEntiers = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6));
        displayIndexImpair(arrayListEntiers);
        displayIndexImpair(listFrameworks);

        listLangages = new ArrayList<String>(listFrameworks);
        System.out.println(listLangages);

        listFrameworks = new ArrayList<String>(Arrays.asList("String", ".NET", "JavaFX", "Laravel"));
        System.out.println(listFrameworks);

        Collections.shuffle(listLangages);
        System.out.println(listLangages);

        System.out.println(arrayListEntiers.subList(3, 6));

        arrayListEntiers.clear();
        for (int j : tab) {
            arrayListEntiers.add(j);
        }

        System.out.println(arrayListEntiers);
        displayPair(arrayListEntiers);
    }

    private static void testsHashSets() {
        HashSet<String> setCouleurs1;
        String[] tabCouleurs1;
        ArrayList<String> listCouleurs1;
        HashSet<String> setCouleurs2;

        setCouleurs1 = new HashSet<String>();
        setCouleurs1.add("Rouge");
        setCouleurs1.add("Vert");
        setCouleurs1.add("Noir");
        setCouleurs1.add("Blanc");
        setCouleurs1.add("Rose");
        setCouleurs1.add("Jaune");

        setCouleurs1.iterator().forEachRemaining(couleur -> {
            System.out.print(couleur + " ");
        });
        System.out.println();

        System.out.println(setCouleurs1.size());

        tabCouleurs1 = new String[setCouleurs1.size()];
        setCouleurs1.toArray(tabCouleurs1);
        System.out.println(Arrays.toString(tabCouleurs1));

        listCouleurs1 = new ArrayList<String>(setCouleurs1);
        System.out.println(listCouleurs1);

        setCouleurs2 = new HashSet<String>();
        setCouleurs2.add("Bleu");
        setCouleurs2.add("Gris");
        setCouleurs2.add("Orange");
        setCouleurs2.add("Vert");
        setCouleurs2.add("Jaune");
        System.out.println(setCouleurs2);

        setCouleurs1.retainAll(setCouleurs2);
        System.out.println(setCouleurs1);

        setCouleurs1.clear();
    }

    private static void testsHashMaps() {
        HashMap<Integer, String> mapEmpld = new HashMap<Integer, String >();
        mapEmpld.put(123, "Jean");
        mapEmpld.put(456, "Marie");
        mapEmpld.put(789, "Paul");
        System.out.println(mapEmpld);

        System.out.println(mapEmpld.get(456));

        mapEmpld.putIfAbsent(111, "Julie");
        mapEmpld.putIfAbsent(456, "Toto");
        System.out.println(mapEmpld);

        mapEmpld.replace(456, "MarieJo");

        if (mapEmpld.get(789).equals("Paul")) {
            mapEmpld.replace(789, "Toto");
            System.out.println(mapEmpld);
        }

        verifierPresence(mapEmpld, "Toto");
        verifierPresence(mapEmpld, "Pierre");

        mapEmpld.forEach((numero, nom) -> {
            System.out.println(numero + "=" + nom);
        });

        mapEmpld.forEach((numero, nom) -> {
            System.out.println("L'employé " + nom + "a le no: " + numero);
        });
    }

    private static void displayIndexImpair(ArrayList arrayList) {
        for (int i = 1; i < arrayList.size(); i+= 2) {
            System.out.print(arrayList.get(i) + " ");
        }
        System.out.println();
    }

    private static void displayPair(ArrayList<Integer> arrayList) {
        arrayList.forEach(entier -> {
            if ((entier & 1) == 0) {
                System.out.print(entier + " ");
            }
        });
        System.out.println();
    }

    private static void verifierPresence(HashMap<Integer, String> hashMap, String nom) {
        if (hashMap.containsValue(nom)) {
            System.out.println(nom + " est présent.");
        }
        else {
            System.out.println(nom + "n'est pas présent.");
        }
    }
}