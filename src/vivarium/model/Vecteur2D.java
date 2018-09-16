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
public class Vecteur2D implements Serializable {
    private double _x;
    private double _y;

    public Vecteur2D() {
        this._x = 0;
        this._y = 0;
    }
    
    public Vecteur2D(double x, double y) {
        this._x = x;
        this._y = y;
    }
    /*
    Vecteur2D(Vecteur2D param) {
        this._x = param._x;
        this._y = param._y;
    }
    */
    public void ajouter(double x, double y) {
        this._x += x;
        this._y += y;
    }
    
    public void retrancher(double x, double y) {
        this._x -= x;
        this._y -= y;
    }
    
    public void multiplier(double param) {
        this._x *= param;
        this._y *= param;
    }
    
    public void diviser(double param) {
        this._x /= param;
        this._y /= param;
    }
    
    public double distance(Vecteur2D param) {
        return this.distance(param._x, param._y);
    }
    
    public double distance(double x, double y) {
        return Math.hypot(this._x - x, this._y - y);
    }

    public double getX() {
        return this._x;
    }

    public void setX(double _x) {
        this._x = _x;
    }

    public double getY() {
        return this._y;
    }

    public void setY(double _y) {
        this._y = _y;
    }
}
