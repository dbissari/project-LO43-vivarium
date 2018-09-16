/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vivarium.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author bright
 */
public class Vivarium implements Serializable {
    
    private final String _nom;
    private Saison _saison;
    private final int _dureeSaison;
    private int _numSaison = 0;
    private final DateHeure _temps = new DateHeure();
    private final int _vitessePassageTemps;
    
    private final ArrayList<Animal> _animaux = new ArrayList<>();
    private final ArrayList<Obstacle> _obstacles = new ArrayList<>();
    
    public Vivarium(String nom, Saison saison, int vitessePassageTemps, int dureeSaison) {
        this._nom = nom;
        this._saison = saison;
        this._dureeSaison = dureeSaison;
        this._vitessePassageTemps = vitessePassageTemps;
    }
    
    public String getNom() {
        return this._nom;
    }

    public ArrayList<Animal> getAnimaux() {
        return this._animaux;
    }

    public ArrayList<Obstacle> getObstacles() {
        return this._obstacles;
    }

    public DateHeure getTemps() {
        return this._temps;
    }

    public Saison getSaison() {
        return this._saison;
    }

    public void setSaison(Saison saison) {
        this._saison = saison;
    }

    public int getDureeSaison() {
        return this._dureeSaison;
    }

    public int getVitessePassageTemps() {
        return this._vitessePassageTemps;
    }

    public int getNumSaison() {
        return this._numSaison;
    }

    public void nextNumSaison() {
        this._numSaison += 1;
    }
    
}
