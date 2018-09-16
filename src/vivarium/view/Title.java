/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vivarium.view;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import vivarium.app.VivariumApp;

/**
 *
 * @author bright
 */
public class Title extends Pane {
    private final Text _text;

    public Title(String name, int size) {
        String spread = "";
        for (char c : name.toCharArray()) {
            spread += c + " ";
        }

        this._text = new Text(spread);
        this._text.setFont(Font.loadFont(VivariumApp.class.getResource(VivariumApp.fontUrl).toExternalForm(), size));
        this._text.setFill(Color.WHITE);
        this._text.setEffect(new DropShadow(30, Color.BLACK));

        this.getChildren().addAll(this._text);
    }

    public double getTitleWidth() {
        return this._text.getLayoutBounds().getWidth();
    }

    public double getTitleHeight() {
        return this._text.getLayoutBounds().getHeight();
    }
}
