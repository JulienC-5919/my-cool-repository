package com.example.tp2;

import java.io.*;

public class Tmp implements Serializable {
    private byte[] bytes = null;

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}