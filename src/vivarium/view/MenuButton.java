/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vivarium.view;

import javafx.beans.binding.Bindings;
import javafx.scene.Cursor;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import vivarium.app.VivariumApp;

/**
 *
 * @author bright
 */
public class MenuButton extends Pane {
    
    private final Text _text;

    private final Effect _shadow = new DropShadow(5, Color.BLACK);
    private final Effect _blur = new BoxBlur(1, 1, 3);

    public MenuButton(String name, Polygon form) {
        form.setStroke(Color.color(1, 1, 1, 0.75));
        form.setEffect(new GaussianBlur());

        form.fillProperty().bind(
                Bindings.when(pressedProperty())
                        .then(Color.color(0, 0, 0, 0.75))
                        .otherwise(Color.color(0, 0, 0, 0.25))
        );

        this._text = new Text(name);
        this._text.setTranslateX(30);
        this._text.setTranslateY(20);
        this._text.setFont(Font.loadFont(VivariumApp.class.getResource(VivariumApp.fontUrl).toExternalForm(), 14));
        this._text.setFill(Color.WHITE);
        this.setCursor(Cursor.HAND);

        this._text.effectProperty().bind(
                Bindings.when(hoverProperty())
                        .then(this._shadow)
                        .otherwise(this._blur)
        );

        this.getChildren().addAll(form, this._text);
    }

    public void setOnAction(Runnable action) {
        setOnMouseClicked(e -> action.run());
    }
    
}
