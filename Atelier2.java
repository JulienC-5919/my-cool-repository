import java.util.InputMismatchException;
import java.util.Scanner;

public class Atelier2 {
    public static double demanderNombre(String question) {
        Scanner scan = new Scanner(System.in);
        boolean nombreInvalide = true; //Si l'utilisateur a entré un entier invalide
        double nombre = 0.0; //Entier entré par l'utilisateur
        do {
            try {
                System.out.print(question);
                nombre = scan.nextDouble();
                nombreInvalide = false;
            }
            catch (InputMismatchException e) {
                System.out.println("Entier invalide");
            }
        } while (nombreInvalide);
        scan.close();
        return nombre;
    }

    public static double diviser() {
        double dividende = demanderNombre("Entrez une dividende: ");
        double diviseur = demanderNombre("Entrez un diviseur");
        return dividende / diviseur;
    }
}
