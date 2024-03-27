import java.util.Random;

public class Mage extends Combattant{
    public Mage(String nom) {
        super.nom = nom;
        super.attaque = 19;
        super.defense = 8;
        super.pvMax = 5;
        super.pv = 5;
        super.degats = 4;
        super.specialeViseSoiMeme = false;
    }

    @Override
    public void speciale(Combattant ennemi) {

        Random r = new Random();
        System.out.println(nom + " utilise Attaque spéciale!");
        ennemi.infligerDegats((byte) (r.nextInt(7) + 4));

        rafraichissement = 3;
    }
}
