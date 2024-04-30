package com.example.tp2;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.Serializable;
import java.time.LocalDate;


public abstract class Objet implements Serializable {
    public FileInputStream getFacture() {
        return facture;
    }

    public void setFacture(FileInputStream facture) {
        this.facture = facture;
    }

    public static enum etat {
        EN_POSSESSION("En possession"), PRETE("Prêté"), PERDU("Perdu");

        private final String displaName;

        public String getDisplaName(){
            return displaName;
        }

        private etat(String displaName) {
            this.displaName = displaName;
        }
    }

    private String nom;
    private String prix;
    private int quantite;
    private LocalDate dateAchat;
    //private image facture
    private String emplacement;
    private etat etat;
    private FileInputStream facture;

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
    protected void setEtat(etat etat) {
        this.etat = etat;
    }
    public etat getEtat() {
        return etat;
    }

    public abstract String getDescription();

}
