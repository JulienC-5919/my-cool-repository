import javax.swing.*;
import java.util.*;

public class Main{

    static Combattant personnage;

    public static void main(String[] args) {
        String entree; //Ligne entrée par le joueur

        Scanner scan = new Scanner(System.in);
        boolean invalide;
        personnage = null; //Personnage contrôlé par le joueur
        int eliminations = 0; //Ennemis tués par le joueur
        boolean continuerCombat = true; //Si le joueur ne fuit pas le combat

        int nbEnnemis = 1; //Nombre d'ennemis au début du combat
        ArrayList<Combattant> ennemis = new ArrayList<Combattant>();

        invalide = true; //Si le joueur entre une lettre invalide
        do {
            System.out.println("              ┌─────────────┐");
            System.out.println(" ──────────── │JAVA & DRAGON│ ───────────");
            System.out.println("┌─────────────┴─────────────┴────────────┐");
            System.out.println("│        Choisissez votre classe:        │");
            System.out.println("├─────────────┬─────────────┬────────────┤");
            System.out.println("│ (G)uerrier: │(M)age:      │ (C)lerc:   │");
            System.out.println("│             │             │            │");
            System.out.println("│ Attaque: 16 │ Attaque: 19 │ Attaque: 17│");
            System.out.println("│ Défense: 2  │ Défense: 8  │ Défense: 4 │");
            System.out.println("│ PV: 10      │ PV: 5       │ PV: 8      │");
            System.out.println("│ Dégâts: 1-8 │ Dégâts: 1-4 │ Dégâts: 1-6│");
            System.out.println("└─────────────┴─────────────┴────────────┘");
            System.out.println();
            System.out.print("->");

            entree = scan.nextLine();

            if (!entree.isEmpty()) {
            switch (entree.charAt(0)) {
                    case 'g' | 'G' -> {
                        personnage = new Guerrier("Guerrier");
                        invalide = false;
                    }
                    case 'm' | 'M' -> {
                        personnage = new Mage("Mage");
                        invalide = false;
                    }
                    case 'c' | 'C' -> {
                        personnage = new Clerc("Clerc");
                        invalide = false;
                    }

                }
            }
        }
        while (invalide);

        do {
            continuerCombat = true;
            personnage.reinitialiserPV();
            for (int i = 0; i < nbEnnemis; i++) {
                ennemis.add(new Ennemi("Ennemi " + (i + 1)));
            }
            do {
                invalide = true;
                do {
                    System.out.println("┌────────────────────────┬──────────────────────────┐");
                    System.out.println(
                            "│ PV: " + remplir(personnage.getPV() + "/" + personnage.getPvMax()  + " |"
                                    + remplir("█".repeat(personnage.getPV()), personnage.getPvMax()) + "| ", 19)
                                    + "│ Éliminations: " + remplir(String.valueOf(eliminations), 11) + "│"
                    );

                    System.out.println("├────────────────────────┴──────────────────────────┤");
                    System.out.println("│ (A)ttaque    │ Attaque (S)péciale   │ (F)uir      │");
                    System.out.print("│              │ ");
                    if (personnage.getRafraichissement() == 0) {
                        System.out.print("                    ");
                    }
                    else {
                        System.out.print("(non-disponible [" + personnage.getRafraichissement() + "])");
                    }
                    System.out.println(" │             │");
                    System.out.println("└───────────────────────────────────────────────────┘");
                    System.out.println();
                    System.out.print("-> ");

                    entree = scan.nextLine();

                    if (!entree.isEmpty()) {
                        switch (entree.charAt(0)) {
                            case 'a' | 'A' -> {
                                personnage.lancerAttaque(selectionnerParmiListe(ennemis));
                                invalide = false;
                            }

                            case 's' | 'S' -> {
                                if (personnage.getRafraichissement() == 0) {
                                    if (personnage.specialeViseSoiMeme) {
                                        personnage.speciale(null);
                                    }
                                    else {
                                        personnage.speciale(selectionnerParmiListe(ennemis));
                                    }
                                    invalide = false;
                                }
                            }

                            case 'f' | 'F' -> {
                                continuerCombat = false;
                                invalide = false;
                            }
                        }
                    }
                } while (invalide);

                int i = 0;
                while (i < ennemis.size()) {
                    if (ennemis.get(i).getPV() <= 0) {
                        System.out.println(ennemis.get(i).getNom() + " est mort!");
                        ennemis.remove(i);
                    }
                    else {
                        i++;
                    }
                }

                ennemis.forEach(ennemi -> {
                    ennemi.lancerAttaque(personnage);
                });
            } while (!ennemis.isEmpty() && continuerCombat && personnage.getPV() > 0);
            nbEnnemis++;
        } while (personnage.getPV() > 0);
    }

    private static String remplir(String chaine, int taille) {
        if (taille > chaine.length()) {
            chaine = chaine + " ".repeat(taille - chaine.length());
        }
        return chaine;
    }

    private static Combattant selectionnerParmiListe(List<Combattant> equipe) {
        Scanner scan = new Scanner(System.in);

        int indiceCombattant; //Indice du combattant sélectionné par le joueur
        do {
            System.out.println("┌─────────────────────────────┐");
            for (int i = 0; i < equipe.size(); i++) {
                System.out.println(
                        "│(" + remplir((i + 1) + ")", 11)
                                + " " + remplir(equipe.get(i).getNom(), 16) + "│"
                );
            }
            System.out.println("└─────────────────────────────┘");
            System.out.println();
            System.out.print("-> ");

            try {
                indiceCombattant = scan.nextInt();
            }
            catch (InputMismatchException e) {
                indiceCombattant = -1;
                scan.nextLine();
            }
        } while (indiceCombattant < 1 || indiceCombattant > equipe.size());

        return equipe.get(indiceCombattant - 1);
    }
}