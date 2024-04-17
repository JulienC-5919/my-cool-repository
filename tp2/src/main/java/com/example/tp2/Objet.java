package com.example.tp2;

import java.math.BigDecimal;

public abstract class Objet {

    enum etats {EN_POSSESSION, PRETE, PREDU, BRISE}
    private String nom;
    private BigDecimal prix;
    private int quantite;
    //private date dateAchat
    //private image facture
    private String emplacement;
    etats etat;

    protected void setNom(String nom) {
        this.nom = nom;
    }
    protected String getNom() {
        return nom;
    }

    protected void setPrix(BigDecimal prix) {
        if (prix.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le prix ne peut pas être négatif.");
        }
        this.prix = prix;
    }
    protected BigDecimal getPrix() {
        return prix;
    }

    protected void setQuantite(int quantite) {
        if (quantite < 0) {
            throw new IllegalArgumentException("La quantité ne peut pas être négative");
        }
        this.quantite = quantite;
    }
    protected int getQuantite() {
        return quantite;
    }

    protected void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }
    protected String getEmplacement() {
        return emplacement;
    }
    protected void setEtat(etats etat) {
        this.etat = etat;
    }
    protected etats getEtat() {
        return etat;
    }

    protected abstract String getDescription();

}
