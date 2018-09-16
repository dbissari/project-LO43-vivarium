/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vivarium.model;

/**
 *
 * @author bright
 */
public enum Saison {
    
    ETE("Eté", "grass.jpg"),
    AUTOMNE("Automne", "sand.jpg"),
    HIVER("Hiver", "snow.jpg"),
    PRINTEMPS("Printemps", "light_sand.jpg");
    
    private final String _nom;
    private final String _image;

    private Saison(String nom, String image) {
        this._nom = nom;
        this._image = image;
    }

    public String getNom() {
        return this._nom;
    }

    public String getImage() {
        return this._image;
    }

    @Override
    public String toString() {
        return this._nom;
    }
    
    public Saison getNext() {
        return Saison.values()[(this.ordinal()+1) % Saison.values().length];
    }
    
}
