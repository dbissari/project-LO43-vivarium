/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vivarium.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import vivarium.app.VivariumApp;

/**
 *
 * @author bright
 */
public abstract class View extends Pane {
    
    protected ImageView _background;
    
    protected void initBackgroundFromUrl(String imageUrl) {
        this._background = new ImageView(
                new Image(
                        VivariumApp.class.getResource(imageUrl).toExternalForm(),
                        VivariumApp.primaryScreenBounds.getWidth(),
                        VivariumApp.primaryScreenBounds.getHeight(),
                        false,
                        false
                )
        );
    }
    
    protected void goBackToMenu() {
        VivariumApp.showMenu();
    }
    
}
