import java.util.Random;

public class Ennemi extends Combattant {
    public Ennemi(String nom) {
        Random r = new Random();
        super.nom = nom;
        super.attaque = (byte) (r.nextInt(6) + 15);
        super.defense = (byte) (r.nextInt(7) + 2);
        super.pv = (byte) (r.nextInt(8) + 3);
        super.degats = (byte) (r.nextInt(5) + 1);
    }

    @Override
    public void speciale(Combattant ennemi) {
    }
}
