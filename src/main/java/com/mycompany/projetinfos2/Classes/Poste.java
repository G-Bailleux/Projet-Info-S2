/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetinfos2.Classes;
import java.util.ArrayList;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;
/**
 *
 * @author gbrlbllx
 */
public class Poste extends Equipement {
    // ========================== Attributs ================================

    private ArrayList<Machine> machines; // Liste des machines du poste

    // ========================== Constructeurs ============================

    public Poste() {
        super();
        this.machines = new ArrayList<Machine>();
    }

    public Poste(String refPoste, String dPoste, ArrayList<Machine> machines) {
        super(refPoste, dPoste);
        this.machines = machines;
    }

    // ========================== Méthodes =================================

    // ---------------------------------------------------------------------
    // Affiche les informations du poste
    public void afficherPoste() {
        ArrayList<String> listeM = new ArrayList<String>();
        for (Machine m : this.machines) {
            listeM.add(m.getRefEquipement());
        }
        System.out.println("refPoste = " + super.refEquipement + ", dPoste = " + super.dEquipement + ", machines = "
                + listeM + "\n");
    }

    // ---------------------------------------------------------------------
    // Crée un poste
    public static ArrayList<Poste> creerPoste(ArrayList<Poste> postes, ArrayList<Machine> machines) {
        Stage creerPosteStage = new Stage();
        creerPosteStage.setTitle("Nouveau Poste");

        // Création des champs de texte
        TextField refPosteField = new TextField(); // TextField permet à l'utilisateur de modifier ici la refPoste
        refPosteField.setPromptText("refPoste");
        TextField dPosteField = new TextField();
        dPosteField.setPromptText("dPoste");

        // Création des labels
        Label refPosteLabel = new Label("Référence du poste :"); // Label affiche un texte qu'on ne peut pas modifier
        Label dPosteLabel = new Label("Désignation du poste :");
        Label listeMachines = new Label("Liste des machines :");

        // Création des boutons
        Button creerButton = new Button("Créer");
        Button terminerButton = new Button("Terminer");
        MenuButton machinesButton = new MenuButton("Machines");

        // Création de la liste des machines
        for (Machine m : machines) {
            CheckMenuItem machineItem = new CheckMenuItem(m.getRefEquipement()); //Créer une option sélectionnable dans un menu déroulant, associée à une machine.
// Empêcher la fermeture du menu lors de la sélection d'un CheckMenuItem
            machinesButton.getItems().add(machineItem);
        }

        // Action du bouton Valider
        creerButton.setOnAction(e -> {
            try {
                if (refPosteField.getText().isEmpty() || dPosteField.getText().isEmpty() 
                        || machinesButton.getItems().stream().noneMatch(item -> ((CheckMenuItem) item).isSelected())) { //Si l’un des champs est vide OU si aucune machine n’est sélectionnée, alors on lance une erreur
                    throw new Exception("Veuillez remplir tous les champs");
                }
                if (postes.stream().anyMatch(p -> p.getRefEquipement().equals(refPosteField.getText()))) {
                    throw new Exception("Le poste existe déjà");
                }
                String refPoste = refPosteField.getText();
                String dPoste = dPosteField.getText();
                ArrayList<Machine> machinesPoste = new ArrayList<Machine>();
                for (MenuItem item : machinesButton.getItems()) { //parcours chaque élément du menu déroulant "machinesButton", qui contient une liste de machines sous forme de "CheckMenuItem"
                    if (item instanceof CheckMenuItem && ((CheckMenuItem) item).isSelected()) {
                        String refMachine = item.getText();
                        Machine machine = machines.stream() // .stream transforme la liste en un flux de donnée pour traiter les éléments de manière fonctionelle
                                .filter(m -> m.getRefEquipement().equals(refMachine)) // la ligne commencant par un point et une simplification d'écriture
                                .findFirst()
                                .orElse(null);
                        if (machine != null) {
                            machinesPoste.add(machine);
                        }
                    }
                }
                Poste poste = new Poste(refPoste, dPoste, machinesPoste);
                postes.add(poste);
                refPosteField.clear();
                dPosteField.clear();
                for (MenuItem item : machinesButton.getItems()) {
                    if (item instanceof CheckMenuItem) {
                        ((CheckMenuItem) item).setSelected(false);
                    }
                }
            } catch (Exception ex) {
                if (ex.getMessage().equals("Veuillez remplir tous les champs")) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Champs vides");
                    alert.setContentText("Veuillez remplir tous les champs.");
                    alert.showAndWait();
                    return;
                } else if (ex.getMessage().equals("Le poste existe déjà")) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Poste déjà existant");
                    alert.setContentText("Le poste existe déjà.");
                    alert.showAndWait();
                    return;
                }
            }
        });

        // Action du bouton Terminer
        terminerButton.setOnAction(e -> {
            creerPosteStage.close();
        });

        // Création de la mise en page
        VBox layout = new VBox(20); //layout indique qu'on organise des éléments graphique
        HBox layoutBoutons = new HBox(20);
        GridPane layoutChamps = new GridPane();

        layoutChamps.setHgap(10);
        layoutChamps.setVgap(10);
        layoutChamps.add(refPosteLabel, 0, 0);
        layoutChamps.add(refPosteField, 1, 0);
        layoutChamps.add(dPosteLabel, 0, 1);
        layoutChamps.add(dPosteField, 1, 1);
        layoutChamps.add(listeMachines, 0, 2);
        layoutChamps.add(machinesButton, 1, 2);

        layoutBoutons.getChildren().addAll(terminerButton, creerButton);
        layout.getChildren().addAll(layoutChamps, layoutBoutons);
        layout.setAlignment(Pos.CENTER);
        layoutBoutons.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(10));

        // Création de la scène
        Scene scene = new Scene(layout);

        // Affichage de la fenêtre
        creerPosteStage.getIcons().add(new Image("file:src\\main\\ressources\\icon.png"));
        creerPosteStage.setScene(scene);
        creerPosteStage.show();
        return postes;
    }

    // ---------------------------------------------------------------------
    // Supprime le poste
    public void supprimerPoste() {
        super.refEquipement = null;
        super.dEquipement = null;
        this.machines.clear();
    }

    // ========================== Getters/Setters ==========================

    public ArrayList<Machine> getMachines() {
        return machines;
    }

    public void setMachines(ArrayList<Machine> machines) {
        this.machines = machines;
    }

}

