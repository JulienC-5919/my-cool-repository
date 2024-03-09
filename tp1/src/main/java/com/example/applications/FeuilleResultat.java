package com.example.applications;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class FeuilleResultat {
    private final int da;
    private byte tp1;
    private byte tp2;
    private byte exa1;
    private byte exa2;
    public FeuilleResultat( int da, byte exa1, byte exa2, byte tp1, byte tp2) {

        if (da <= 0) {
            throw new IllegalArgumentException("Le numéro de demande d'admission doit être un entier positif.");
        }

        this.da = da;

        setExa1(exa1);
        setExa2(exa2);
        setTp1(tp1);
        setTp2(tp2);

    }

    public FeuilleResultat(int da) {

        if (da <= 0) {
            throw new IllegalArgumentException("Le numéro de demande d'admission doit être un entier positif.");
        }

        this.da = da;

        this.exa1 = 0;
        this.exa2 = 0;
        this.tp1 = 0;
        this.tp2 = 0;
    }

    public int getDa() {
        return da;
    }
    public byte getTp1() {
        return tp1;
    }
    public byte getTp2() {
        return tp2;
    }
    public byte getExa1() {
        return exa1;
    }
    public byte getExa2() {
        return exa2;
    }

    public void setTp1(byte tp1) {
        if (tp1 < 0 || tp1 > 100) {
            throw new IllegalArgumentException("La note du travail pratique 1 doit être entre 0 et 100.");
        }

        this.tp1 = tp1;
    }
    public void setTp2(byte tp2) {
        if (tp2 < 0 || tp2 > 100) {
            throw new IllegalArgumentException("La note du travail pratique 2 doit être entre 0 et 100.");
        }

        this.tp2 = tp2;
    }
    public void setExa1(byte exa1) {
        if (exa1 < 0 || exa1 > 100) {
            throw new IllegalArgumentException("La note de l'examen 1 doit être entre 0 et 100.");
        }

        this.exa1 = exa1;
    }
    public void setExa2(byte exa2) {
        if (exa2 < 0 || exa2 > 100) {
            throw new IllegalArgumentException("La note de l'examen 2 doit être entre 0 et 100.");
        }

        this.exa2 = exa2;
    }
}
