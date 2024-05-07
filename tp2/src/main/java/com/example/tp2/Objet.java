package com.example.tp2;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Stocke les valeurs d'un objet d'inventaire
 */
public abstract class Objet implements Serializable {

    public enum etat {
        EN_POSSESSION("En possession"), PRETE("Prêté"), PERDU("Perdu");

        private final String displayName;

        public String getDisplayName(){
            return displayName;
        }

        etat(String displayName) {
            this.displayName = displayName;
        }
    }

    private String nom;
    private double prix;
    private int quantite;
    private LocalDate dateAchat;
    private String emplacement;
    private etat etat;
    private byte[] octetsImageFacture;

    protected void setNom(String nom) {
        this.nom = nom;
    }
    public String getNom() {
        return nom;
    }

    protected void setPrix(double prix) {this.prix = prix;}
    public double getPrix() {
        return prix;
    }

    public LocalDate getDateAchat() {
        return dateAchat;
    }

    protected void setDateAchat(LocalDate dateAchat) {
        this.dateAchat = dateAchat;
    }

    protected void setQuantite(int quantite) {
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

    public byte[] getOctetsImageFacture() {
        return octetsImageFacture;
    }

    public void setOctetsImageFacture(byte[] octets) {
        this.octetsImageFacture = octets;
    }

    public abstract String getDescription();

}
