package com.example.tp2;

public class Outil extends Objet {
    private String numeroSerie;
    private String modele;
    private String marque;


    public String getDescription() {
        return "Marque " + marque + ", Nº de série: " + numeroSerie;
    }
    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
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
