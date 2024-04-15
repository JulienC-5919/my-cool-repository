package com.example.tp2;

import java.math.BigDecimal;

enum etatObjet {EN_POSSESSION, PRETE, PERDU}

public abstract class Objet {
    private String nom;
    private BigDecimal prix;
    private int quantite;

}
