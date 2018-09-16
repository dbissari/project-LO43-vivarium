/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vivarium.view;

import java.util.Arrays;
import java.util.List;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.util.Pair;
import vivarium.app.VivariumApp;

/**
 *
 * @author bright
 */
public class MenuView extends View{
    
    private final List<Pair<String, Runnable>> _elements = Arrays.asList(
            new Pair<String, Runnable>("Nouveau", () -> this.nouveauAction()),
            new Pair<String, Runnable>("Charger", () -> this.chargerAction()),
            new Pair<String, Runnable>("Aide", () -> this.aideAction()),
            new Pair<String, Runnable>("Quitter", () -> this.quitterAction())
    );
    
    private Title _title;
    private Line _line;
    private final VBox _box = new VBox(-5);
    
    private final double _menuPosX = VivariumApp.primaryScreenBounds.getWidth() / 2 - 100;
    private final double _menuPosY = VivariumApp.primaryScreenBounds.getHeight() / 3 + 50;
    
    public MenuView() {
        this.initView();
    }
    
    private void nouveauAction() {
        VivariumApp.showNewVivarium();
    }
    
    private void chargerAction() {
        VivariumApp.loadVivarium();
    }
    
    private void aideAction() {
        VivariumApp.showAide();
    }
    
    private void quitterAction() {
        Platform.exit();
    }
    
    private void initView() {
        this.initBackgroundFromUrl("assets/menu/backgrounds/Cute_Green.jpg");
        this.initTitle();
        this.initLine();
        this.initBox();
        this.startAnimation();
        
        this.getChildren().addAll(this._background, this._title, this._line, this._box);
    }

    private void initTitle() {
        this._title = new Title("Vivarium", 48);
        this._title.setTranslateX(VivariumApp.primaryScreenBounds.getWidth() / 2 - this._title.getTitleWidth() / 2);
        this._title.setTranslateY(VivariumApp.primaryScreenBounds.getHeight() / 3);
    }

    private void initLine() {
        
        this._line = new Line(this._menuPosX, this._menuPosY, this._menuPosX, this._menuPosY + 150);
        this._line.setStrokeWidth(3);
        this._line.setStroke(Color.color(1, 1, 1, 0.75));
        this._line.setEffect(new DropShadow(5, Color.BLACK));
        this._line.setScaleY(0);
    }

    private void initBox() {
        this._box.setTranslateX(this._menuPosX + 5);
        this._box.setTranslateY(this._menuPosY + 5);
        this._elements.forEach(element -> {
            MenuButton item = new MenuButton(element.getKey(), new Polygon(
                        0, 0,
                        200, 0,
                        215, 15,
                        200, 30,
                        0, 30
            ));
            item.setOnAction(element.getValue());
            item.setTranslateX(-300);

            Rectangle clip = new Rectangle(300, 30);
            clip.translateXProperty().bind(item.translateXProperty().negate());

            item.setClip(clip);

            this._box.getChildren().add(item);
        });
    }

    private void startAnimation() {
        ScaleTransition st = new ScaleTransition(Duration.seconds(1), this._line);
        st.setToY(1);
        st.setOnFinished(e -> {

            for (int i = 0; i < this._box.getChildren().size(); i++) {
                Node n = this._box.getChildren().get(i);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(1 + i * 0.15), n);
                tt.setToX(0);
                tt.setOnFinished(e2 -> n.setClip(null));
                tt.play();
            }
        });
        st.play();
    }
    
}
