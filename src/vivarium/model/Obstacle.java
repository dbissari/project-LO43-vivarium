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
public class Obstacle extends VivariumObject {
    
    private boolean _existe = true;
    private final TypeObstacle _type;
    
    public Obstacle(Vivarium vivarium, double x, double y, TypeObstacle type) {
        super(vivarium, x, y);
        this._type = type;
    }

    public boolean existe() {
        return this._existe;
    }

    public void setExiste(boolean existe) {
        this._existe = existe;
    }

    @Override
    public void remove() {
        this._vivarium.getObstacles().remove(this);
    }

    @Override
    public double getTaille() {
        return this._type.getTaille();
    }

    @Override
    public String getImage() {
        return this._type.getImage();
    }

    public boolean estMangeable() {
        return this._type.estMangeable();
    }

    public boolean estPourHerbivore() {
        return this._type.estPourHerbivore();
    }

    public boolean estPourCarnivore() {
        return this._type.estPourCarnivore();
    }

    public int getValeurNutritionnelle() {
        return this._type.getValeurNutritionnelle();
    }

    @Override
    public String toString() {
        return 
                "Type : " + this._type.getNom() + "\n" +
                (this._type.estMangeable() ? "Est mangeable \n" : "") +
                (this._type.estPourHerbivore() ? "Est pour herbivore \n" : "") +
                (this._type.estPourCarnivore()? "Est pour carnivore \n" : "") + "\n" +
                (this._type.estMangeable() ? "Valeur :" + this._type.getValeurNutritionnelle() + "\n" : "")
                ;
    }
    
}
