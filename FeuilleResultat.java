package com.example.applications;

public class FeuilleResultat {
    private int da;
    private byte tp1;
    private byte tp2;
    private byte exa1;
    private byte exa2;
    public FeuilleResultat(int da, byte tp1, byte tp2, byte exa1, byte exa2) {
        this.da = da;
        this.exa1 = exa1;
        this.exa2 = exa2;
        this.tp1 = tp1;
        this.tp2 = tp2;
    }

    public FeuilleResultat(int da) {
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
        this.tp1 = tp1;
    }
    public void setTp2(byte tp1) {
        this.tp1 = tp1;
    }
    public void setExa1(byte tp1) {
        this.tp1 = tp1;
    }
    public void setExa2(byte tp1) {
        this.tp1 = tp1;
    }
}
