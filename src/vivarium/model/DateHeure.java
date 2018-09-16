/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vivarium.model;

import java.io.Serializable;

/**
 *
 * @author bright
 */
public class DateHeure implements Serializable {
    
    private int _jour;
    private int _heure;
    private int _minute;

    public DateHeure() {
        this._minute = 0;
        this._heure = 0;
        this._jour = 0;
    }

    public DateHeure(DateHeure dateHeure) {
        this._minute = dateHeure._minute;
        this._heure = dateHeure._heure;
        this._jour = dateHeure._jour;
    }

    public DateHeure(int minute, int heure, int jour) {
        this._minute = minute;
        this._heure = heure;
        this._jour = jour;
    }
    
    public void ajouter(int minute, int heure, int jour) {
        this._minute += (minute >= 0 ? minute : -minute);
        if (this._minute > 59) {
            this._heure += (this._minute / 60);
            this._minute = (this._minute % 60); 
        }
        
        this._heure += (heure >= 0 ? heure : -heure);
        if (this._heure > 23) {
            this._jour += (this._heure / 24);
            this._heure = (this._heure % 24); 
        }
        
        this._jour += jour;
    }
    
    public int getJour() {
        return this._jour;
    }

    public int getHeure() {
        return this._heure;
    }

    public int getMinute() {
        return this._minute;
    }

    @Override
    public String toString() {
        return "Jour " + this._jour + " | " + String.format("%02d", this._heure) + ":" + String.format("%02d", this._minute);
    }
    
    public static int diffHeures(DateHeure ancienne, DateHeure nouvelle) {
        int diffJours = nouvelle.getJour() - ancienne.getJour();
        int diffHeures = 0;
        if (diffJours > 0) {
            if (nouvelle.getHeure() > ancienne.getHeure()) {
                diffHeures = (nouvelle.getHeure() - ancienne.getHeure()) + (diffJours * 24);
            }
            else {
                diffHeures = (24 - ancienne.getHeure() + nouvelle.getHeure()) + ((diffJours - 1) * 24);
            }
        }
        else {
            diffHeures = nouvelle.getHeure() - ancienne.getHeure();
        }
        
        return diffHeures;
    }
    
}
