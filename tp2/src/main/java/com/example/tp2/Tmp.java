package com.example.tp2;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.Locale;
@Deprecated
public class Tmp {
    public static void main(String[] args) {

    }


    public static double parseDouble(String s){
        return Double.parseDouble(s.replaceAll("[ $]", "").replace(',', '.'));
    }
}