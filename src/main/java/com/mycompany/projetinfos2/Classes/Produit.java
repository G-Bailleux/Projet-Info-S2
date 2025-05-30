/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetinfos2.Classes;

/**
 *
 * @author gbrlbllx
 */
import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Produit {
    // ========================== Attributs ================================

    private String codeProduit;
    private String dProduit; // Désignation du produit

    // ========================== Constructeurs ============================

    public Produit() {
        this.codeProduit = null;
        this.dProduit = null;
    }

    public Produit(String codeProduit, String dProduit) {
        this.codeProduit = codeProduit;
        this.dProduit = dProduit;
    }
    
     // ---------------------------------------------------------------------
    // ========================== Méthodes =================================
    
    // ========================== Getters/Setters ==========================

    public String getCodeProduit() {
        return codeProduit;
    }

    public void setCodeProduit(String codeProduit) {
        this.codeProduit = codeProduit;
    }

    public String getdProduit() {
        return dProduit;
    }

    public void setdProduit(String dProduit) {
        this.dProduit = dProduit;
    }
     
   

    // Affiche les informations du produit
    public void afficherProduit() {
        System.out.println("codeProduit = " + codeProduit + ", dProduit = " + dProduit + "\n");
    }

    
    // Supprime le produit
    public void supprimerProduit() {
        this.codeProduit = null;
        this.dProduit = null;
    }

    // ---------------------------------------------------------------------
    // Créer un produit
    public static ArrayList<Produit> creerProduit(ArrayList<Produit> produits) {
        // Création de la fenêtre
        Stage creerProdStage = new Stage();
        creerProdStage.setTitle("Nouveau produit");

        // Création des champs de texte
        TextField codeProduitField = new TextField();
        codeProduitField.setPromptText("codeProduit"); //setPromptText(...) = texte d’exemple gris pour aider l’utilisateur
        TextField dProduitField = new TextField();
        dProduitField.setPromptText("dProduit");

        // Création des labels
        Label codeProduitLabel = new Label("Code du produit : ");
        Label dProduitLabel = new Label("Désignation du produit : ");

        // Création des boutons
        Button creerButton = new Button("Créer");
        Button terminerButton = new Button("Terminer");

        // Action du bouton Valider
        creerButton.setOnAction(e -> {  // e repésente un évenemment (clic) qu'on peut ignorer si on utilise pas
            try {
                if (codeProduitField.getText().isEmpty() || dProduitField.getText().isEmpty()) {
                    throw new Exception("Veuillez remplir tous les champs");
                }
                if (produits.stream().anyMatch(p -> p.getCodeProduit().equals(codeProduitField.getText()))) {
                    throw new Exception("Produit déjà existant");
                }
                String codeProduit = codeProduitField.getText();
                String dProduit = dProduitField.getText();
                Produit produit = new Produit(codeProduit, dProduit);
                produits.add(produit);
                codeProduitField.clear();
                dProduitField.clear();

            } catch (Exception ex) {
                if (ex.getMessage().equals("Produit déjà existant")) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Produit déjà existant");
                    alert.setContentText("Le produit existe déjà.");
                    alert.showAndWait(); // Empêcher que le reste de l’application continue avant que l’utilisateur ait lu le message
                    return;
                } else if (ex.getMessage().equals("Veuillez remplir tous les champs")) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Champs vides");
                    alert.setContentText("Veuillez remplir tous les champs.");
                    alert.showAndWait(); 
                    return;
                }
            }
        });

        // Action du bouton Annuler
        terminerButton.setOnAction(e -> creerProdStage.close());

        // Création de la disposition
        VBox layout = new VBox(20); //VBox pour empiler verticalement les blocs (champs + boutons).
        HBox layoutBoutons = new HBox(20); //HBox pour placer les boutons côte à côte.
        GridPane layoutChamps = new GridPane(); //GridPane pour aligner proprement les champs (labels + textes)

        layoutChamps.setHgap(10);
        layoutChamps.setVgap(10);
        layoutChamps.add(codeProduitLabel, 0, 0);
        layoutChamps.add(codeProduitField, 1, 0);
        layoutChamps.add(dProduitLabel, 0, 1);
        layoutChamps.add(dProduitField, 1, 1);

        layoutBoutons.getChildren().addAll(terminerButton, creerButton); // .getChildren() retourne la liste des éléments graphiques (boutons, ...)
        layout.getChildren().addAll(layoutChamps, layoutBoutons);
        layout.setAlignment(Pos.CENTER);
        layoutBoutons.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(10)); // évite que les éléments soient collés en ajoutant une marge

        // Création de la scène
        Scene scene = new Scene(layout);

        // Affichage de la fenêtre
        creerProdStage.getIcons().add(new Image("file:src\\main\\ressources\\icon.png"));
        creerProdStage.setScene(scene);
        creerProdStage.show();
        return produits;
    }
}
