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
public abstract class VivariumObject implements Removable, Displayable, Serializable {
    
    protected Vecteur2D _pos;
    protected Vivarium _vivarium;
    
    public VivariumObject(Vivarium vivarium, double x, double y) {
        this._vivarium = vivarium;
        this._pos = new Vecteur2D(x, y);
    }

    @Override
    public final Vecteur2D getPos() {
        return this._pos;
    }
    
}
