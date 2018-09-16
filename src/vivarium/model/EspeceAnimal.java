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
public enum EspeceAnimal {
    
    GIRAFE("Girafe", "girafe_1.png", 50, 1.6, false, 20, false, false, true, 55, 40),
    CHAMEAU("Chameau", "animated_camel_1.gif", 45, 2.0, true, 30, false, false, true, 60, 40),
    CHEVAL("Cheval", "horse_1.png", 40, 3.0, true, 25, false, false, true, 50, 40),
    VACHE("Vache", "animated_cow_1.gif", 45, 2.0, true, 20, false, false, true, 50, 50),
    MOUTON("Mouton", "sheep_1.png", 30, 1.5, true, 15, false, false, true, 40, 30),
    CROCODILE("Crocodile", "crocodile_1.png", 25, 1.8, false, 25, true, false, false, 40, 50),
    LION("Lion", "lion_1.png", 30, 2.2, true, 28, true, false, false, 60, 60),
    TIGRE("Tigre", "tiger_1.png", 30, 2.2, false, 26, true, false, false, 60, 60),
    HYENE("Hyène", "hyene_1.png", 30, 2.0, true, 22, true, false, false, 40, 30),
    LOUP("Loup", "wolf_1.png", 30, 2.2, true, 25, true, false, false, 50, 50);
    
    private final String _nom;
    private final String _image;
    private final double _taille;
    private final double _vitesse;
    private final boolean _seDeplaceEnGroupe;
    private final int _esperanceDeVie;
    private final boolean _estCarnivore;
    private final boolean _estCanibale;
    private final boolean _estHerbivore;
    private final int _capaciteEstomac;
    private final int _valeurNutritionnelle;

    private EspeceAnimal(String nom, String image, double taille, double vitesse, boolean seDeplaceEnGroupe, int esperanceDeVie, boolean estCarnivore, boolean estCanibale, boolean estHerbivore, int capaciteEstomac, int valeurNutritionnelle) {
        this._nom = nom;
        this._image = image;
        this._taille = taille;
        this._vitesse = vitesse;
        this._seDeplaceEnGroupe = seDeplaceEnGroupe;
        this._esperanceDeVie = esperanceDeVie;
        this._estCarnivore = estCarnivore;
        this._estCanibale = estCanibale;
        this._estHerbivore = estHerbivore;
        this._capaciteEstomac = capaciteEstomac;
        this._valeurNutritionnelle = valeurNutritionnelle;
    }

    public String getNom() {
        return this._nom;
    }

    public double getVitesse() {
        return this._vitesse;
    }

    public boolean seDeplaceEnGroupe() {
        return this._seDeplaceEnGroupe;
    }

    public int getEsperanceDeVie() {
        return this._esperanceDeVie;
    }

    public boolean estCarnivore() {
        return this._estCarnivore;
    }

    public boolean estCanibale() {
        return this._estCanibale;
    }

    public boolean estHerbivore() {
        return this._estHerbivore;
    }

    public int getCapaciteEstomac() {
        return this._capaciteEstomac;
    }

    public int getValeurNutritionnelle() {
        return this._valeurNutritionnelle;
    }

    public double getTaille() {
        return this._taille;
    }

    public String getImage() {
        return this._image;
    }

    @Override
    public String toString() {
        return this._nom;
    }
    
}
