import java.util.Random;

public class Clerc extends Combattant{
    public Clerc(String nom) {
        super.nom = nom;
        super.attaque = 17;
        super.defense = 4;
        super.pvMax = 8;
        super.pv = 8;
        super.degats = 6;
        super.specialeViseSoiMeme = true;
    }
    @Override
    void speciale(Combattant cible) {
        Random r = new Random();
        byte soin = (byte) (r.nextInt(5) + 2);

        System.out.println(nom + " utilise Attaque spéciale!");
        soigner(soin);
        rafraichissement = 3;
    }
}
