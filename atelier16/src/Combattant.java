import java.util.Random;

public abstract class Combattant {
    protected String nom;
    protected byte attaque;
    protected byte defense;
    protected byte pvMax;
    protected byte pv;
    protected byte degats;
    protected byte rafraichissement = 0;
    protected boolean specialeViseSoiMeme;

    void lancerAttaque(Combattant cible) {
        Random r = new Random();
        byte resultatDe;
        byte degatsInfliges;

        resultatDe = (byte) (r.nextInt(20) + 1);

        System.out.println(nom + " attaque et roule " + resultatDe + "!\n");

        if (resultatDe >= cible.defense - attaque) {
            cible.infligerDegats((byte) (r.nextInt(degats) + 1));
        }
        else System.out.println("L'attaque est ratée!");

        if (rafraichissement > 0) {
            rafraichissement--;
        }
    }

    void infligerDegats(byte degats) {
        pv -= degats;
        System.out.println(nom + " subit " + degats + " dégâts!");
    }

    public void reinitialiserPV(){
        pv = pvMax;
        rafraichissement = 0;
    }

    abstract void speciale(Combattant ennemi);

    public String getNom() {
        return nom;
    }

    public byte getAttaque() {
        return attaque;
    }

    public byte getDefense() {
        return defense;
    }

    public byte getPV() {
        return pv;
    }

    public byte getPvMax() {
        return pvMax;
    }
    public byte getRafraichissement() {
        return rafraichissement;
    }
    
    public void soigner(byte soin) {
        pv = (byte) Math.min(pv + soin, pvMax);
        System.out.println(nom + " guérit de " + soin + " PV!");
    }
}
