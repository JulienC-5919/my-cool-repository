public class Livre extends Objet{

    private String auteur;
    private String maisonEdition;
    private short anneeEcriture;
    private short anneePublication;
    @Override
    public String getDescription() {

        return "Auteur: " + auteur + ", Maison d'édition: " + maisonEdition
                + ", Écrit en " + anneeEcriture + ", publié en " + anneePublication;
    }

    public String getAuteur() {
        return auteur;
    }
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getMaisonEdition() {
        return maisonEdition;
    }
    public void setMaisonEdition(String maisonEdition) {
        this.maisonEdition = maisonEdition;
    }

    public short getAnneeEcriture() {
        return anneeEcriture;
    }
    public void setAnneeEcriture(short anneeEcriture) {
        this.anneeEcriture = anneeEcriture;
    }

    public short getAnneePublication() {
        return anneePublication;
    }
    public void setAnneePublication(short anneePublication) {
        this.anneePublication = anneePublication;
    }


}
