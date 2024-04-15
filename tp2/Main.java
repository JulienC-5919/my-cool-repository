TextArea taNom = new TextArea();
    TextArea taPrix = new TextArea();
    Spinner<Integer> spQuantite = new Spinner<>();
    DatePicker dpAchat = new DatePicker();
    File facture;

    private void creerSectionGenerale(GridPane gridPane) {
        Label entete = new Label("Secton générale");
        entete.setFont(new Font(20));

        gridPane.add(entete, 0, 2, 2, 1);

        gridPane.add(new Label("Nom:"), 0, 3);
        gridPane.add(new Label("Prix:"), 0, 4);
        gridPane.add(new Label("Quantité:"), 0, 5);
        gridPane.add(new Label("Date d'achat:"), 0, 6);
        gridPane.add(new Label("Image facture:"), 0, 7);
        gridPane.add(new Label("État:"), 0, 8);
        gridPane.add(new Label("Emplacement:"), 0, 9);

        gridPane.add(taNom, 1, 3);
        gridPane.add(taPrix, 1, 4);
        spQuantite.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE) {
        });
        gridPane.add(spQuantite, 1, 5);
        gridPane.add(dpAchat, 1, 6);
        gridPane.add(selecteurFacture(),1,7);
        gridPane.add(comboBoxEtat(), 1, 8);
    }

    private HBox selecteurFacture() {
        HBox selecteur = new HBox();
        TextField adresseFichier = new TextField();
        Button btnChoisirFichier = new Button("\uD83D\uDCC4");
        btnChoisirFichier.setOnAction(e -> {
            {
                FileChooser selecteurFichier = new FileChooser();
                facture = selecteurFichier.showOpenDialog(stage);
                if (facture != null) {
                adresseFichier.setText(facture.getPath());
                }
            }
        });
        selecteur.getChildren().add(adresseFichier);
        selecteur.getChildren().add(btnChoisirFichier);
        return selecteur;
    }

    private static ComboBox<String> comboBoxEtat() {

        ComboBox<String> etat = new ComboBox<String>();
        return etat;
    }