package com.example.tp2;

import java.math.BigDecimal;
import java.time.LocalDate;


public abstract class Objet {



    private String nom;
    private String prix;
    private int quantite;
    private LocalDate dateAchat;
    //private image facture
    private String emplacement;
    String etat;

    protected void setNom(String nom) {
        this.nom = nom;
    }
    protected String getNom() {
        return nom;
    }

    protected void setPrix(String prix) {
        this.prix = prix;
    }
    protected String getPrix() {
        return prix;
    }

    protected LocalDate getDateAchat() {
        return dateAchat;
    }

    protected void setDateAchat(LocalDate dateAchat) {
        this.dateAchat = dateAchat;
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
    protected void setEtat(String etat) {
        this.etat = etat;
    }
    protected String getEtat() {
        return etat;
    }

    protected abstract String getDescription();

}
