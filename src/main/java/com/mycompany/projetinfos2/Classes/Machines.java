/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetinfos2.Classes;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 *
 * @author gbrlbllx
 */
 /**
 * Classe Machine représentant une machine dans un atelier
 * Hérite de la classe Equipement et ajoute des attributs et méthodes spécifiques aux machines
 * Permet de gérer les caractéristiques et l'état des machines dans l'atelier
 */


public class Machine extends Equipement {
    // ========================== Attributs ================================

    private float posX; // Coordonnée x de la machine
    private float posY; // Coordonnée y de la machine
    private float coutHoraire; // Coût horaire de la machine
    private float dureeUtil; // Durée d'utilisation de la machine
    private EtatMachine etatMachine; // Etat de la machine

    // ========================== Constructeurs ============================

    /**
     * Constructeur par défaut
     * Initialise une machine avec des valeurs par défaut
     */
    public Machine() {
        super();
        this.posX = 0;
        this.posY = 0;
        this.coutHoraire = 0;
        this.dureeUtil = 0;
        this.etatMachine = EtatMachine.OCCUPE;
    }

    public Machine(String refMachine, String dMachine, float posX, float posY, float coutHoraire, float dureeUtil,
            EtatMachine etat) {
        super(refMachine, dMachine); // super appelle les constructeurs de la classe équipement pour ne pas avoir à recréer le code
        this.posX = posX;
        this.posY = posY;
        this.coutHoraire = coutHoraire;
        this.dureeUtil = dureeUtil;
        this.etatMachine = etat;
    }

    // ========================== Méthodes =================================

    /**
     * Affiche les informations de la machine dans la console
     * Utilisé principalement pour le débogage
     */
    public void afficherMachine() {
        System.out.println("refMachine = " + super.refEquipement + ", dMachine = " + super.dEquipement + ", posX = "
                + this.posX + ", posY = "
                + this.posY + ", coutHoraire = " + this.coutHoraire + ", dureeUtil = " + this.dureeUtil + ", etat = "
                + this.etatMachine);
    }

    /**
     * Supprime les données d'une machine en réinitialisant tous ses attributs
     * Met la référence et la description à null
     * Remet les valeurs numériques à 0 et l'état à OCCUPE
     */
    public void supprimerMachine() {
        super.refEquipement = null;
        super.dEquipement = null;
        this.posX = 0;
        this.posY = 0;
        this.coutHoraire = 0;
        this.dureeUtil = 0;
        this.etatMachine = EtatMachine.OCCUPE;
    }

    public static ArrayList<Machine> creerMachine(ArrayList<Machine> machines, Pane planAtelier, TextField dMach,
            TextField coutHMach, TextField dureeMach, ComboBox<EtatMachine> etatMach, TextField refMach) {
        // Création de la fenêtre
        Stage creerMachineStage = new Stage();
        creerMachineStage.setTitle("Nouvelle machine");

        // Création des champs de texte
        TextField refMachField = new TextField();
        refMachField.setPromptText("refMachine");
        TextField dMachField = new TextField();
        dMachField.setPromptText("dMachine");
        TextField posXField = new TextField();
        posXField.setPromptText("posX");
        TextField posYField = new TextField();
        posYField.setPromptText("posY");
        TextField coutHoraireField = new TextField();
        coutHoraireField.setPromptText("coutHoraire");
        TextField dureeUtilField = new TextField();
        dureeUtilField.setPromptText("dureeUtil");

        // Création des labels
        Label refMachLabel = new Label("Référence : "); // affiche du texte pour expliquer ce qu'il faut saisir
        Label dMachLabel = new Label("Désignation : ");
        Label posXLabel = new Label("Abscisse : ");
        Label posYLabel = new Label("Ordonnée : ");
        Label coutHoraireLabel = new Label("Coût horaire : ");
        Label dureeUtilLabel = new Label("Durée : ");

        // Création des boutons
        Button creerButton = new Button("Créer");
        Button terminerButton = new Button("Terminer");

        // Action du bouton Creer
        creerButton.setOnAction(e -> {
            try {
                // Vérification que tous les champs sont remplis
                if (refMachField.getText().isEmpty() || dMachField.getText().isEmpty() || posXField.getText().isEmpty()
                        || posYField.getText().isEmpty() || coutHoraireField.getText().isEmpty()
                        || dureeUtilField.getText().isEmpty()) {
                    throw new Exception("Veuillez remplir tous les champs");
                }

                // Vérification que la référence n'existe pas déjà
                if (machines.stream().anyMatch(m -> m.getRefEquipement().equals(refMachField.getText()))) {
                    throw new Exception("La machine existe déjà");
                }

                // Récupération des valeurs des champs
                String refMachine = refMachField.getText();
                String dMachine = dMachField.getText();
                float posX = Float.parseFloat(posXField.getText());
                float posY = Float.parseFloat(posYField.getText());
                float coutHoraire = Float.parseFloat(coutHoraireField.getText());
                float dureeUtil = Float.parseFloat(dureeUtilField.getText());

                // Création de la nouvelle machine et ajout à la liste
                Machine machine = new Machine(refMachine, dMachine, posX, posY, coutHoraire, dureeUtil,
                        EtatMachine.DISPONIBLE);
                machines.add(machine);

                // Réinitialisation des champs de saisie
                refMachField.clear();
                dMachField.clear();
                posXField.clear();
                posYField.clear();
                coutHoraireField.clear();
                dureeUtilField.clear();

                // Mise à jour de l'affichage de l'atelier
                Atelier.dessinerAtelier(planAtelier, machines, dMach, coutHMach, dureeMach, etatMach, refMach);
            } catch (Exception ex) {
                // Gestion des erreurs avec des alertes appropriées
                if (ex.getMessage().equals("La machine existe déjà")) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Machine déjà existante");
                    alert.setContentText("La machine existe déjà.");
                    alert.showAndWait();
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

        // Action du bouton Terminer : ferme la fenêtre
        terminerButton.setOnAction(e -> {
            creerMachineStage.close();
        });

        // Création de la disposition de l'interface
        VBox layout = new VBox(20);
        HBox layoutBoutons = new HBox(20);
        GridPane layoutChamps = new GridPane();

        // Configuration de la grille pour les champs de saisie
        layoutChamps.setHgap(10); //espacement horizontal
        layoutChamps.setVgap(10);
        layoutChamps.add(refMachLabel, 0, 0); // ajoute chaque composant à la position voulue
        layoutChamps.add(refMachField, 1, 0);
        layoutChamps.add(dMachLabel, 0, 1);
        layoutChamps.add(dMachField, 1, 1);
        layoutChamps.add(posXLabel, 0, 2);
        layoutChamps.add(posXField, 1, 2);
        layoutChamps.add(posYLabel, 0, 3);
        layoutChamps.add(posYField, 1, 3);
        layoutChamps.add(coutHoraireLabel, 0, 4);
        layoutChamps.add(coutHoraireField, 1, 4);
        layoutChamps.add(dureeUtilLabel, 0, 5);
        layoutChamps.add(dureeUtilField, 1, 5);

        // Organisation des boutons et des champs dans le layout
        layoutBoutons.getChildren().addAll(terminerButton, creerButton);
        layout.getChildren().addAll(layoutChamps, layoutBoutons);
        layout.setAlignment(Pos.CENTER); //centre le contenu
        layoutBoutons.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(10)); // ajoute une marge intérieure de 10px sur tous les côtés

        // Création et configuration de la scène
        Scene scene = new Scene(layout);

        // Configuration et affichage de la fenêtre
        creerMachineStage.getIcons().add(new Image("file:src\\main\\ressources\\icon.png"));
        creerMachineStage.setScene(scene);
        creerMachineStage.show();

        return machines;
    }

    // ========================== Getters/Setters ==========================

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getCoutHoraire() {
        return coutHoraire;
    }

    public void setCoutHoraire(float coutHoraire) {
        this.coutHoraire = coutHoraire;
    }

    public float getDureeUtil() {
        return dureeUtil;
    }

    public void setDureeUtil(float dureeUtil) {
        this.dureeUtil = dureeUtil;
    }

    public EtatMachine getEtat() {
        return etatMachine;
    }

    public void setEtat(EtatMachine etat) {
        this.etatMachine = etat;
    }
}


