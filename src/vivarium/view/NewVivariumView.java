/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vivarium.view;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import vivarium.app.VivariumApp;
import vivarium.model.Saison;
import vivarium.model.Vivarium;

/**
 *
 * @author bright
 */
public class NewVivariumView extends NavigationView {
    
    public NewVivariumView() {
        this._title = new Title("Nouveau Vivarium", 36);
        this.initView();
    }
    
    @Override
    protected void initBox() {
        Font font = Font.loadFont(VivariumApp.class.getResource(VivariumApp.fontUrl).toExternalForm(), 14);
        
        Label vivariumNameLabel = new Label("Nom du vivarium");
        vivariumNameLabel.setFont(font);
        vivariumNameLabel.setTextFill(Color.WHITE);
        
        TextField vivariumNameField = new TextField();
        vivariumNameField.setFont(font);
        
        
        Label vivariumSaisonLabel = new Label("Saison de départ");
        vivariumSaisonLabel.setFont(font);
        vivariumSaisonLabel.setTextFill(Color.WHITE);
        
        ComboBox<Saison> vivariumSaisonCbx = new ComboBox<>();
        vivariumSaisonCbx.getItems().setAll(Saison.values());
        vivariumSaisonCbx.getSelectionModel().selectFirst();
        
        
        Label vivariumDureeSaisonLabel = new Label("Nombre de jours de jeu par saison de jeu");
        vivariumDureeSaisonLabel.setFont(font);
        vivariumDureeSaisonLabel.setTextFill(Color.WHITE);
        
        TextField vivariumDureeSaisonField = new TextField();
        vivariumDureeSaisonField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                vivariumDureeSaisonField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        
        Label vivariumVitesseTempsLabel = new Label("Nombre de minutes de jeu par seconde réelle");
        vivariumVitesseTempsLabel.setFont(font);
        vivariumVitesseTempsLabel.setTextFill(Color.WHITE);
        
        TextField vivariumVitesseTempsField = new TextField();
        vivariumVitesseTempsField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                vivariumVitesseTempsField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        
        MenuButton nextButton = new MenuButton("Suivant", new Polygon(
                0, 0,
                200, 0,
                215, 15,
                200, 30,
                0, 30
        ));
        nextButton.setOnMouseClicked((MouseEvent event) -> {
            if (vivariumNameField.getText().length() == 0) {
            
            }
            else if (vivariumDureeSaisonField.getText().length() == 0) {
                
            }
            else if (Integer.parseInt(vivariumDureeSaisonField.getText()) == 0) {
                
            }
            else if (vivariumVitesseTempsField.getText().length() == 0) {
                
            }
            else if (Integer.parseInt(vivariumVitesseTempsField.getText()) == 0 || Integer.parseInt(vivariumVitesseTempsField.getText()) > 60) {
                
            }
            else {
                VivariumApp.showVivarium(new Vivarium(vivariumNameField.getText(), vivariumSaisonCbx.getValue(), Integer.parseInt(vivariumVitesseTempsField.getText()), Integer.parseInt(vivariumDureeSaisonField.getText())));
            }
        });
        
        
        this._box.getChildren().addAll(
                vivariumNameLabel, vivariumNameField, 
                vivariumSaisonLabel, vivariumSaisonCbx, 
                vivariumDureeSaisonLabel, vivariumDureeSaisonField,
                vivariumVitesseTempsLabel, vivariumVitesseTempsField,
                new Separator(), 
                nextButton
        );
        
        super.initBox();
    }
}
