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
public enum TypeObstacle {
    
    ARBRE1("Arbre 1", "tree_1.png", 60, true, true, false, 40),
    ARBRE2("Arbre 2", "tree_2.png", 60, true, true, false, 45),
    ARBRE3("Arbre 3", "tree_3.png", 60, true, true, false, 30),
    ARBRE4("Arbre 4", "tree_4.png", 60, true, true, false, 35),
    ARBRE5("Arbre 5", "tree_5.png", 60, true, true, false, 35),
    ARBRE6("Arbre 6", "tree_6.png", 60, true, true, false, 50),
    ROCK1("Rock 1", "rock_1.png", 40, false, false, false, 0),
    ROCK2("Rock 2", "rock_2.png", 40, false, false, false, 0),
    ROCK3("Rock 3", "rock_3.png", 40, false, false, false, 0),
    ROCK4("Rock 4", "rock_4.png", 40, false, false, false, 0),
    ROCK5("Rock 5", "rock_5.png", 40, false, false, false, 0),
    ROCK6("Rock 6", "rock_6.png", 40, false, false, false, 0);
    
    private final String _nom;
    private final String _image;
    private final double _taille;
    private final boolean _estMangeable;
    private final boolean _estPourHerbivore;
    private final boolean _estPourCarnivore;
    private final int _valeurNutritionnelle;

    private TypeObstacle(String nom, String image, double taille, boolean estMangeable, boolean estPourHerbivore, boolean estPourCarnivore, int valeurNutritionnelle) {
        this._nom = nom;
        this._image = image;
        this._taille = taille;
        this._estMangeable = estMangeable;
        this._estPourHerbivore = estPourHerbivore;
        this._estPourCarnivore = estPourCarnivore;
        this._valeurNutritionnelle = valeurNutritionnelle;
    }

    public String getNom() {
        return this._nom;
    }

    public boolean estMangeable() {
        return this._estMangeable;
    }

    public boolean estPourHerbivore() {
        return this._estPourHerbivore;
    }

    public boolean estPourCarnivore() {
        return this._estPourCarnivore;
    }

    public int getValeurNutritionnelle() {
        return this._valeurNutritionnelle;
    }

    public String getImage() {
        return this._image;
    }

    public double getTaille() {
        return _taille;
    }

    @Override
    public String toString() {
        return this._nom;
    }
    
}
