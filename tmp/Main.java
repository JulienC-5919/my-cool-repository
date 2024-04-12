public class Main {
    public static void main(String[] args) {
        Livre l = new Livre();
        l.setEtat(Objet.etats.EN_POSSESSION);
        Objet.etats f = l.getEtat();
        Objet p = new Livre();
        if (p.getClass().equals(Livre.class)) System.out.println("e");
    }
}