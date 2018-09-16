/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vivarium.view;

import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import vivarium.app.VivariumApp;

/**
 *
 * @author bright
 */
public abstract class NavigationView extends View {
    
    protected Title _title;
    
    protected final double _contentBoxX = VivariumApp.primaryScreenBounds.getWidth() / 4;
    protected final double _contentBoxY = VivariumApp.primaryScreenBounds.getHeight() / 6 + 50;
    protected final VBox _box = new VBox(15);
    
    public NavigationView() {
        this._box.setTranslateX(this._contentBoxX);
        this._box.setTranslateY(this._contentBoxY);
        this._box.setMaxWidth(this._contentBoxX * 2);
    }
    
    protected final MenuButton _backMenuButton = new MenuButton("Retourner au menu", new Polygon(
                15, 0,
                215, 0,
                215, 30,
                15, 30,
                0, 15
    ));
    
    protected void initView() {
        this.initBackgroundFromUrl("assets/menu/backgrounds/Cute_Green.jpg");
        this.initTitle();
        this.initBox();
        this.initBackMenuButton();
        
        this.getChildren().addAll(this._background, this._title, this._box);
    }

    protected void initTitle() {
        this._title.setTranslateX(VivariumApp.primaryScreenBounds.getWidth() / 2 - this._title.getTitleWidth() / 2);
        this._title.setTranslateY(VivariumApp.primaryScreenBounds.getHeight() / 6);
    }
    
    protected void initBox() {
        this._box.getChildren().add(this._backMenuButton);
    }
    
    protected void initBackMenuButton() {
        this._backMenuButton.setTranslateX((this._contentBoxX * 2) - 300);
        Rectangle clip = new Rectangle(300, 30);
        this._backMenuButton.setClip(clip);
        
        this._backMenuButton.setOnAction(this::goToMenu);
    }
    
    protected void goToMenu() {
        VivariumApp.showMenu();
    }
    
}
