public class Jeu extends Objet {

    public String getDescription() {
        return console + ", " + nbJoueurs + " joueurs, " + annee + ", " + developpement;
    }
    private String console;
    private int nbJoueurs;
    private String developpement;
    private short annee;

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public int getNbJoueurs() {
        return nbJoueurs;
    }

    public void setNbJoueurs(int nbJoueurs) {
        this.nbJoueurs = nbJoueurs;
    }

    public String getDeveloppement() {
        return developpement;
    }

    public void setDeveloppement(String developpement) {
        this.developpement = developpement;
    }

    public short getEdition() {
        return annee;
    }

    public void setEdition(short annee) {
        this.annee = annee;
    }
}
