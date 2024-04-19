package com.example.tp2;

import java.math.BigDecimal;
import java.time.LocalDate;


public abstract class Objet {
    public static enum etat {EN_POSSESSION, PRETE, PERDU}


    private String nom;
    private String prix;
    private int quantite;
    private LocalDate dateAchat;
    //private image facture
    private String emplacement;
    private static String etat;

    protected void setNom(String nom) {
        this.nom = nom;
    }
    public String getNom() {
        return nom;
    }

    protected void setPrix(String prix) {
        this.prix = prix;
    }
    public String getPrix() {
        return prix;
    }

    public LocalDate getDateAchat() {
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
    public int getQuantite() {
        return quantite;
    }

    protected void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }
    public String getEmplacement() {
        return emplacement;
    }
    protected void setEtat(String etat) {
        this.etat = etat;
    }
    public String getEtat() {
        return etat;
    }

    public abstract String getDescription();

}
