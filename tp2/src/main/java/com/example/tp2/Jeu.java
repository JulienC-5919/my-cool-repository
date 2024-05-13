package com.example.tp2;

public class Jeu extends Objet {

    public String getDescription() {
        return console + ", " + nbJoueurs + " joueurs, " + developpement + ", " + anneeSortie;
    }
    private String console;
    private int nbJoueurs;
    private String developpement;
    private short anneeSortie;

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

    public short getAnneeSortie() {
        return anneeSortie;
    }

    public void setAnneeSortie(short anneeSortie) {
        this.anneeSortie = anneeSortie;
    }
}
