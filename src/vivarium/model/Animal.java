/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vivarium.model;

import java.util.Random;

/**
 *
 * @author bright
 */
public class Animal extends VivariumObject {
    
    protected Vecteur2D _dPos = new Vecteur2D();
    protected Random _r = new Random();
    protected EspeceAnimal _espece;
    
    protected boolean _estMort = false;
    
    protected int _age = 0;
    
    protected int _reserveAlimentaire;
    
    public Animal(Vivarium vivarium, double x, double y, EspeceAnimal espece) {
        super(vivarium, x, y);
        this._espece = espece;
        this._reserveAlimentaire = espece.getCapaciteEstomac();
    }
    
    public Obstacle seDeplacerEtInteragir(double gameZoneWidth, double gameZoneHeight) {
        
        int nbVoisins = 0;
        Animal premierVoisin = null;
        if (!this._vivarium.getAnimaux().isEmpty()) {
            premierVoisin = this._vivarium.getAnimaux().get(0);
            double dist = this._pos.distance(premierVoisin.getPos());
            for (Animal animal : this._vivarium.getAnimaux()) {
                if (animal != this && !animal._estMort) {
                    double tmp = this._pos.distance(animal._pos);
                    if (this._espece.seDeplaceEnGroupe() && this._espece == animal._espece && tmp <  this.getTaille() * 2) { // Récupère les animaux dans le voisinage
                        this._dPos.ajouter(animal._dPos.getX(), animal._dPos.getY()); 
                        nbVoisins += 1;
                    }
                    if (tmp < dist) {
                        premierVoisin = animal;
                        dist = tmp;
                    }
                } 
            }
            if ((dist >= ((this.getTaille() + premierVoisin.getTaille()) / 2)) || (premierVoisin == this) || (premierVoisin._estMort))
                premierVoisin = null;
        }
        this._dPos.diviser(nbVoisins + 1);
        if (premierVoisin != null){
            if (this._espece.estCarnivore() && (this._espece != premierVoisin._espece || this._espece.estCanibale())) {
                premierVoisin.mourir();
                this.manger(premierVoisin.getValeurNutritionnelle());
            }
            else {
                this.eviter(premierVoisin.getPos(), 5.0);
            }
        }
        
        this.deplacerAleatoirement();
        
        Obstacle premierObstacle = null;
        if (!this._vivarium.getObstacles().isEmpty()) {
            premierObstacle = this._vivarium.getObstacles().get(0);
            double dist = this._pos.distance(premierObstacle.getPos());
            for (Obstacle obstacle : this._vivarium.getObstacles()) {
                if (obstacle.existe()) {
                    double tmp  = this._pos.distance(obstacle.getPos());
                    if (tmp <= dist) {
                        premierObstacle = obstacle;
                        dist = tmp;
                    }
                }
            }
            if (dist >= ((this.getTaille() + premierObstacle.getTaille()) / 2) || !premierObstacle.existe())
                premierObstacle = null;
        }
        
        Obstacle aDetruire = null;
        if (premierObstacle != null) {
            if (premierObstacle.estMangeable() && ((premierObstacle.estPourHerbivore() && this._espece.estHerbivore()) || (premierObstacle.estPourCarnivore() && this._espece.estCarnivore()))) {
                premierObstacle.setExiste(false);
                this.manger(premierObstacle.getValeurNutritionnelle());
                aDetruire = premierObstacle;
            }
            else {
                this.eviter(premierObstacle.getPos(), 10.0);
            }
        }
        
        this.modererVitesse();
        this.resterDansZone(gameZoneWidth, gameZoneHeight);
        this._pos.ajouter(this._dPos.getX(), this._dPos.getY());
        
        return aDetruire;
    }
    
    public void vieillir(int jours) {
        this._age += jours;
        if (this._age >= (this._espece.getEsperanceDeVie() - 2 + this._r.nextInt(5)))
            this.mourir();
    }
    
    public void manger(int valeurNutritionnelle) {
        this._reserveAlimentaire += valeurNutritionnelle;
        if (this._reserveAlimentaire > this.getCapaciteEstomac())
            this._reserveAlimentaire = this.getCapaciteEstomac();
    }
    
    public void depenserEnergie(int heures) {
        this._reserveAlimentaire -= heures;
        if (this._reserveAlimentaire < -5)
            this.mourir();
    }
    
    public void mourir() {
        this._estMort = true;
    }
    
    protected void eviter(Vecteur2D pos, double vitesse) {
        double distX = this._pos.getX() - pos.getX();
        double distY = this._pos.getY() - pos.getY();
        if (distX < 0) { //to the left of neighbour
            this._dPos.retrancher(vitesse, 0);
        } else {
            this._dPos.ajouter(vitesse, 0);
        }

        if (distY < 0) { //on top of neighbour
            this._dPos.retrancher(0, vitesse);
        } else {
            this._dPos.ajouter(0, vitesse);
        }
    }
    
    protected void deplacerAleatoirement() {
        if (true){
            double noiseX = this.randomNegative(this._r.nextDouble() * 0.25);
            double noiseY = this.randomNegative(this._r.nextDouble() * 0.25);
            
            this._dPos.ajouter(noiseX, noiseY);
        }
    }
	
    protected void modererVitesse() {
        // Modère les changements des coordonnées de déplacement des sorte que tous les animaux aient une même vitesse en moyenne
        double vitesse = this._dPos.distance(0, 0);
        this._dPos.multiplier(this.getVitesse() / vitesse);
    }
    
    protected void resterDansZone(double width, double height) {
        if ((this._pos.getX() - (this.getTaille() / 2)) < 0) { //wrap in the horizontal direction
            this._pos.setX(width - (this.getTaille() / 2));
        } else if ((this._pos.getX() + (this.getTaille() / 2)) > width) {
            this._pos.setX(this.getTaille() / 2);
        }

        if ((this._pos.getY() - (this.getTaille() / 2)) < 0) { //wrap in the vertical direction
            this._pos.setY(height - (this.getTaille() / 2));
        } else if ((this._pos.getY() + (this.getTaille() / 2)) > height) {
            this._pos.setY(this.getTaille() / 2);
        }
    }
	
    protected double randomNegative(double number) {
        //randomly make a number its negative value
        switch(this._r.nextInt(2)) {
        case 1:
            number = -number;
            break;
        }

        return number;
    }

    public boolean estMort() {
        return this._estMort;
    }

    public int getValeurNutritionnelle() {
        return this._espece.getValeurNutritionnelle() + this._age + this._reserveAlimentaire;
    }
    
    public int getCapaciteEstomac() {
        return this._espece.getCapaciteEstomac() + this._age;
    }
    
    public double getVitesse() {
        return this._espece.getVitesse() + (this._age / 10);
    }

    @Override
    public void remove() {
        this._vivarium.getAnimaux().remove(this);
    }
    
    @Override
    public double getTaille() {
        return this._espece.getTaille();
    }

    @Override
    public String getImage() {
        return this._espece.getImage();
    }

    public EspeceAnimal getEspece() {
        return this._espece;
    }

    @Override
    public String toString() {
        return 
                "Espèce : " + this._espece.getNom() + "\n" +
                "Age : " + this._age + "\n" +
                "Espérance : " + this._espece.getEsperanceDeVie() + "\n" +
                "Réserve : " + this._reserveAlimentaire + "\n" +
                "Capacité : " + this.getCapaciteEstomac()+ "\n" +
                (this._espece.seDeplaceEnGroupe() ? "Se déplace en groupe \n" : "") +
                (this._espece.estHerbivore()? "Est herbivore \n" : "") +
                (this._espece.estCarnivore()? "Est carnivore \n" : "") +
                (this._espece.estCanibale()? "Est canibale \n" : "")
                ;
    }
    
}
