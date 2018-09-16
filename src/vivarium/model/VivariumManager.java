/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vivarium.model;

import java.util.ArrayList;

/**
 *
 * @author bright
 */
public class VivariumManager {
    
    private final Vivarium _vivarium;
    
    public VivariumManager(Vivarium vivarium) {
        this._vivarium = vivarium;
    }
    
    public ArrayList<VivariumObject> tourner(double gameZoneWidth, double gameZoneHeight) {
        ArrayList<VivariumObject> aDetruire = new ArrayList<>();
        this._vivarium.getAnimaux().stream().forEach((Animal animal) -> {
            if (animal.estMort()) {
                aDetruire.add(animal);
            }
            else {
                Obstacle tmp = animal.seDeplacerEtInteragir(gameZoneWidth, gameZoneHeight); 
                if (tmp != null)
                    aDetruire.add(tmp);
            }
        });
        
        aDetruire.stream().forEach((VivariumObject vivariumObject) -> {
            vivariumObject.remove();
        });
        return aDetruire;
    }
    
    public void passerTemps() {
        DateHeure ancienneDate = new DateHeure(this._vivarium.getTemps());
        this._vivarium.getTemps().ajouter(this._vivarium.getVitessePassageTemps(), 0 ,0);
        this._vivarium.getAnimaux().stream().forEach((Animal animal) -> {
            if (!animal.estMort()) {
                animal.depenserEnergie(DateHeure.diffHeures(ancienneDate, this._vivarium.getTemps()));
                if (this._vivarium.getTemps().getJour() > ancienneDate.getJour())
                    animal.vieillir(this._vivarium.getTemps().getJour() - ancienneDate.getJour());
            }
        });
        
        if ((this._vivarium.getTemps().getJour() % this._vivarium.getDureeSaison()) == 0 && (this._vivarium.getTemps().getJour() / this._vivarium.getDureeSaison()) != this._vivarium.getNumSaison()) {
            this._vivarium.setSaison(this._vivarium.getSaison().getNext());
            this._vivarium.nextNumSaison();
        }
    }
    
    public void ajouterAnimal(Animal animal) {
        if (animal != null)
            this._vivarium.getAnimaux().add(animal);
    }
    
    public void ajouterObstacle(Obstacle obstacle) {
        if (obstacle != null)
            this._vivarium.getObstacles().add(obstacle);
    }
    
    public void tuerAnimal(Animal animal) {
        if (animal != null)
            animal.mourir();
    }
    
    public void detruireObstacle(Obstacle obstacle) {
        if (obstacle != null)
            obstacle.remove();
    }
    
    public void NourrirAnimal(Animal animal, int valeurNutritionnelle) {
        if (animal != null)
           animal.manger(valeurNutritionnelle);
    }

    public Vivarium getVivarium() {
        return this._vivarium;
    }
    
}
