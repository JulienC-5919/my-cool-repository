package com.example.tp2;

public class Outil extends Objet {
    private int numeroSerie;
    private String modele;
    private String marque;


    public String getDescription() {
        return "Marque " + marque + ", " + numeroSerie;
    }
    public int getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(int numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }
}
