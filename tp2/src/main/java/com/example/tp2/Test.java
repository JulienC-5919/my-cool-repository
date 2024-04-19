package com.example.tp2;


import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.TextAlignment;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

public class Test {

    public static void main(String[] args) {
        TableColumn<Livre, String> t = new TableColumn();

        TableColumn tcDA = new TableColumn("DA");
        tcDA.setCellValueFactory(new PropertyValueFactory<Objet, String>("da"));
    }
}
