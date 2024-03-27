import java.util.Random;

public class Guerrier extends Combattant{
    public Guerrier(String nom) {
        super.nom = nom;
        super.attaque = 16;
        super.defense = 2;
        super.pvMax = 10;
        super.pv = 10;
        super.degats = 8;
        super.specialeViseSoiMeme = false;
    }
    @Override
    void speciale(Combattant ennemi) {

        Random r = new Random();
        System.out.println(nom + " utilise Attaque spéciale!");
        ennemi.infligerDegats((byte) (r.nextInt(6) + 1));

        rafraichissement = 3;
    }
}
