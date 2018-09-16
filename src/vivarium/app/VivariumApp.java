/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vivarium.app;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import vivarium.model.Vivarium;
import vivarium.util.FileSave;
import vivarium.util.Sound;
import vivarium.view.HelpView;
import vivarium.view.MenuView;
import vivarium.view.NewVivariumView;
import vivarium.view.VivariumView;

/**
 *
 * @author bright
 */
public class VivariumApp extends Application {
    
    private static final Pane _rootPane = new Pane();
    
    private static Stage _primaryStage;
    
    public static final Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
    
    public static final String fontUrl = "assets/fonts/Penumbra-HalfSerif-Std_35114.ttf";
    
    public final Sound _sound = new Sound(VivariumApp.class.getResource("assets/sounds/relax.wav"));
    
    @Override
    public void start(Stage primaryStage) {
        VivariumApp._primaryStage = primaryStage;
        
        VivariumApp.showMenu();
        
        Scene scene = new Scene(VivariumApp._rootPane);
        
        primaryStage.setTitle("Vivarium");
        
        primaryStage.setFullScreen(true);
        
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        this._sound.play();
        scene.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode().equals(KeyCode.P)) {
                if (this._sound.isPlaying())
                    this._sound.stop();
                else
                    this._sound.play();
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static void showMenu() {
        VivariumApp._rootPane.getChildren().clear();
        VivariumApp._rootPane.getChildren().add(new MenuView());
    }
    
    public static void showNewVivarium() {
        VivariumApp._rootPane.getChildren().clear();
        VivariumApp._rootPane.getChildren().add(new NewVivariumView());
    }
    
    public static void showVivarium(Vivarium vivarium) {
        VivariumApp._rootPane.getChildren().clear();
        VivariumApp._rootPane.getChildren().add(new VivariumView(vivarium));
    }
    
    public static void showAide() {
        VivariumApp._rootPane.getChildren().clear();
        VivariumApp._rootPane.getChildren().add(new HelpView());
    }
    
    public static void loadVivarium() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir le vivarium à charger");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers Vivarium", "*.vivarium"));
        File file = fileChooser.showOpenDialog(VivariumApp._primaryStage);
        
        if (file != null) {
            try {
                Vivarium vivarium = (Vivarium)FileSave.load(file.getAbsolutePath());
                VivariumApp.showVivarium(vivarium);
            } catch (IOException | ClassNotFoundException ex) {
            }
        }
    }    
}
