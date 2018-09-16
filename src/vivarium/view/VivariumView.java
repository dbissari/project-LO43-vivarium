/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vivarium.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import vivarium.app.VivariumApp;
import vivarium.model.Animal;
import vivarium.model.EspeceAnimal;
import vivarium.model.Obstacle;
import vivarium.model.Saison;
import vivarium.model.TypeObstacle;
import vivarium.model.Vecteur2D;
import vivarium.model.Vivarium;
import vivarium.model.VivariumManager;
import vivarium.model.VivariumObject;
import vivarium.util.FileSave;

/**
 *
 * @author bright
 */
public class VivariumView extends View {
    
    private final VivariumManager _vivariumManager;
    
    private final double _controlsBoxX = 4 * VivariumApp.primaryScreenBounds.getWidth() / 5;
    private final double _controlsBoxY = 0;
    private final double _controlsBoxWidth = VivariumApp.primaryScreenBounds.getWidth() / 5;
    private final double _controlsBoxHeight = VivariumApp.primaryScreenBounds.getHeight();
    
    private final double _gameZoneWidth = VivariumApp.primaryScreenBounds.getWidth() - this._controlsBoxWidth;
    private final double _gameZoneHeight = VivariumApp.primaryScreenBounds.getHeight();
    
    private final Pane _timePane = new Pane();
    
    private final VBox _controlsBox = new VBox(this._controlsBoxHeight / 150);
    private final VBox _objectDetailsBox = new VBox(1);
    
    private final HashMap<VivariumObject, ImageView> _vivariumObjects = new HashMap<>();
    
    private final Vecteur2D _clickPos = new Vecteur2D(20, 20);
    private final Circle _clickMarker = new Circle(this._clickPos.getX(), this._clickPos.getY(), 15, Color.gray(0.6, 0.6));
    
    private final HashMap<Saison, Image> _saisonsImages = new HashMap<>();
    
    private final Timeline _refreshTimeline = new Timeline();
    private final Timeline _tempsTimeline = new Timeline();
    
    private final Text _timeText = new Text();
    
    private final Pane _gameZone = new Pane();
    
    private VivariumObject _selectedObject;
    
    private final TextArea _logTextArea = new TextArea();
    
    public VivariumView(Vivarium vivarium) {
        this._vivariumManager = new VivariumManager(vivarium);
        this._background = new ImageView();
        
        this.initView();
    }
    
    private void initView() {
        this.initSaisonsImages();
        this.initGameZone();
        this.initVivarium();
        this.initControlsBox();
        this.selectAnimal(null);
        
        this._refreshTimeline.play();
        this._tempsTimeline.play();
        
        this.getChildren().addAll(this._background, this._gameZone, this._controlsBox);
    }
    
    private void initSaisonsImages() {
        for(Saison saison : Saison.values()) {
            this._saisonsImages.put(
                saison, 
                new Image(
                        VivariumApp.class.getResource("assets/vivarium/backgrounds/" + saison.getImage()).toExternalForm(),
                        VivariumApp.primaryScreenBounds.getWidth(),
                        VivariumApp.primaryScreenBounds.getHeight(),
                        false,
                        false
                )
            );
        }
    }
    
    private void initGameZone() {
        this._gameZone.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                this._clickPos.setX(event.getX());
                this._clickPos.setY(event.getY());
                this._clickMarker.setCenterX(event.getX());
                this._clickMarker.setCenterY(event.getY());
            }
        });
        
        this._gameZone.setPrefSize(this._gameZoneWidth, this._gameZoneHeight);
        this._gameZone.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        
        this._timePane.setPrefSize(this._gameZoneWidth, this._gameZoneHeight);
        this._timePane.setStyle("-fx-background-color: #000");
        
        this._clickMarker.setStroke(Color.RED);
        this._clickMarker.setStrokeWidth(3);
        this._clickMarker.setStrokeType(StrokeType.OUTSIDE);
        
        this._gameZone.getChildren().addAll(this._timePane, this._clickMarker);
        
        
        this._refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        this._refreshTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(40), (ActionEvent) -> {
            ArrayList<VivariumObject> aDetruire = this._vivariumManager.tourner(this._gameZoneWidth, this._gameZoneHeight);
            aDetruire.stream().forEach((VivariumObject vivariumObject) -> {
                this._gameZone.getChildren().remove(this._vivariumObjects.remove(vivariumObject));
            });
            this._vivariumManager.getVivarium().getAnimaux().stream().forEach((Animal animal) -> {
                this.updateObjectViewPos(animal);
            });
        }));
    }

    private void initVivarium() {
        this.updateBackground();
        this.updateTimePaneOpacity();
        this.updateTimeText();
        this._vivariumManager.getVivarium().getAnimaux().stream().forEach((Animal animal) -> {
            this.addAnimalOnZone(animal);
        });
        this._vivariumManager.getVivarium().getObstacles().stream().forEach((Obstacle obstacle) -> {
            this.addObstacleOnZone(obstacle);
        });
    }
    
    private void initControlsBox() {
        this._controlsBox.setLayoutX(this._controlsBoxX);
        this._controlsBox.setLayoutY(this._controlsBoxY);
        this._controlsBox.setMaxSize(this._controlsBoxWidth, this._controlsBoxHeight);
        this._controlsBox.setPadding(new Insets(this._controlsBoxHeight / 150, this._controlsBoxWidth / 50, this._controlsBoxHeight / 150, this._controlsBoxWidth / 50));
        this._controlsBox.setStyle("-fx-background-color: #000");
        this._controlsBox.setOpacity(0.8);
        
        Text title = new Text("~ " + this._vivariumManager.getVivarium().getNom() + " ~");
        title.setFont(Font.loadFont(VivariumApp.class.getResource(VivariumApp.fontUrl).toExternalForm(), 16));
        title.setWrappingWidth(this._controlsBoxWidth - (this._controlsBoxWidth / 50));
        title.setFill(Color.WHITE);
        
        this._timeText.setFont(Font.loadFont(VivariumApp.class.getResource(VivariumApp.fontUrl).toExternalForm(), 16));
        this._timeText.setFill(Color.ORANGE);
        
        this._tempsTimeline.setCycleCount(Timeline.INDEFINITE);
        this._tempsTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), (ActionEvent) -> {
            this._vivariumManager.passerTemps();
            this.updateTimeText();
            if (this._vivariumManager.getVivarium().getTemps().getMinute() == 0) {
                this.updateTimePaneOpacity();
            }
            if (!this._background.getImage().equals(this._saisonsImages.get(this._vivariumManager.getVivarium().getSaison()))) {
                this.updateBackground();
            }
        }));
        
        Button leaveButton = new Button("Retourner au menu");
        leaveButton.setOnAction((ActionEvent) -> {
            try {
                FileSave.save(this._vivariumManager.getVivarium(), "vivariums/" + this._vivariumManager.getVivarium().getNom() + ".vivarium");
                VivariumApp.showMenu();
            } catch (IOException ex) {
            }
        });
        
        this._logTextArea.setEditable(false);
        this._logTextArea.textProperty().addListener((ObservableValue<?> observable, Object oldValue, Object newValue) -> {
            this._logTextArea.setScrollTop(Double.MIN_VALUE);
        });
        
        this._controlsBox.getChildren().addAll(
                title, 
                new Separator(), this._timeText, 
                new Separator(), this.initAddAnimalForm(),
                new Separator(), this.initAddObstacleForm(), 
                new Separator(), this._objectDetailsBox,
                new Separator(), leaveButton,
                new Separator(), this._logTextArea
        );
    }
    
    private void updateObjectViewPos(VivariumObject vivariumObject) {
        ImageView image = this._vivariumObjects.get(vivariumObject);
        image.setX(vivariumObject.getPos().getX() - (vivariumObject.getTaille() / 2));
        image.setY(vivariumObject.getPos().getY() - (vivariumObject.getTaille() / 2));
    }
    
    private void updateBackground() {
        this._background.setImage(this._saisonsImages.get(this._vivariumManager.getVivarium().getSaison()));
    }
    
    private void updateTimePaneOpacity() {
        double diff = (this._vivariumManager.getVivarium().getTemps().getHeure() * 0.05) - 0.6;
        this._timePane.setOpacity((diff >= 0) ? diff : -diff);
    }
    
    private void updateTimeText() {
        this._timeText.setText(this._vivariumManager.getVivarium().getTemps().toString());
    }
    
    private void addAnimalOnZone(Animal animal) {
        ImageView image = new ImageView(
                new Image(
                    VivariumApp.class.getResource("assets/vivarium/foregrounds/" + animal.getImage()).toExternalForm(),
                    animal.getTaille(),
                    animal.getTaille(),
                    false,
                    false
                )
        );
        image.setOnContextMenuRequested((ContextMenuEvent) -> {
            this.selectAnimal(animal);
        });
        this._vivariumObjects.put(animal, image);
        this.updateObjectViewPos(animal);
        this._gameZone.getChildren().add(image);
    }
    
    private void addObstacleOnZone(Obstacle obstacle) {
        ImageView image = new ImageView(
                new Image(
                    VivariumApp.class.getResource("assets/vivarium/foregrounds/" + obstacle.getImage()).toExternalForm(),
                    obstacle.getTaille(),
                    obstacle.getTaille(),
                    false,
                    false
                )
        );
        image.setOnContextMenuRequested((ContextMenuEvent) -> {
            this.selectObstacle(obstacle);
        });
        this._vivariumObjects.put(obstacle, image);
        this.updateObjectViewPos(obstacle);
        this._gameZone.getChildren().add(image);
    }
    
    private VBox initAddAnimalForm() {
        HBox form = new HBox(2);
        
        ComboBox<EspeceAnimal> especeCbx = new ComboBox<>();
        especeCbx.getItems().setAll(EspeceAnimal.values());
        especeCbx.getSelectionModel().selectFirst();
        
        Button addAnimalButton = new Button("Ajouter");
        addAnimalButton.setWrapText(true);
        addAnimalButton.setOnAction((ActionEvent) -> {
            Animal animal = new Animal(this._vivariumManager.getVivarium(), this._clickPos.getX(), this._clickPos.getY(), especeCbx.getValue());
            this.addAnimalOnZone(animal);
            this._vivariumManager.ajouterAnimal(animal);
        });
        
        form.getChildren().addAll(especeCbx, addAnimalButton);
        
        Text title = new Text("Animaux : ");
        title.setFont(Font.loadFont(VivariumApp.class.getResource(VivariumApp.fontUrl).toExternalForm(), 16));
        title.setFill(Color.WHITE);
        
        return new VBox(2, title, form);
    }
    
    private VBox initAddObstacleForm() {
        HBox form = new HBox(2);
        
        ComboBox<TypeObstacle> typeCbx = new ComboBox<>();
        typeCbx.getItems().setAll(TypeObstacle.values());
        typeCbx.getSelectionModel().selectFirst();
        
        Button addObstacleButton = new Button("Ajouter");
        addObstacleButton.setWrapText(true);
        addObstacleButton.setOnAction((ActionEvent) -> {
            Obstacle obstacle = new Obstacle(this._vivariumManager.getVivarium(), this._clickPos.getX(), this._clickPos.getY(), typeCbx.getValue());
            this.addObstacleOnZone(obstacle);
            this._vivariumManager.ajouterObstacle(obstacle);
        });
        
        form.getChildren().addAll(typeCbx, addObstacleButton);
        
        Text title = new Text("Obstacles : ");
        title.setFont(Font.loadFont(VivariumApp.class.getResource(VivariumApp.fontUrl).toExternalForm(), 16));
        title.setFill(Color.WHITE);
        
        return new VBox(2, title, form);
    }
    
    private void selectAnimal(Animal animal) {
        this.selectObject(animal);
        this._objectDetailsBox.getChildren().clear();
        if (animal == null || this._vivariumObjects.get(animal) == null) {
            this._objectDetailsBox.setVisible(false);
        }
        else {
            Text title = new Text("Animal");
            title.setFont(Font.loadFont(VivariumApp.class.getResource(VivariumApp.fontUrl).toExternalForm(), 16));
            title.setFill(Color.WHITE);
            
            Text detailsText = new Text(animal.toString());
            detailsText.setFill(Color.WHITE);
            
            Button killButton = new Button("Tuer");
            killButton.setOnAction((ActionEvent) -> {
                this._vivariumManager.tuerAnimal(animal);
                this.selectAnimal(null);
            });
            
            Button feedButton = new Button("Nourrir");
            feedButton.setOnAction((ActionEvent) -> {
                this._vivariumManager.NourrirAnimal(animal, 10);
                this.selectAnimal(animal);
            });
            
            Button refreshButton = new Button("Rafraîchir");
            refreshButton.setOnAction((ActionEvent) -> {
                this.selectAnimal(animal);
            });
            
            HBox buttons = new HBox(3, feedButton, killButton, refreshButton);
            
            this._objectDetailsBox.getChildren().addAll(title, detailsText, buttons);
            this._objectDetailsBox.setVisible(true);
        }
    }
    
    private void selectObstacle(Obstacle obstacle) {
        this.selectObject(obstacle);
        this._objectDetailsBox.getChildren().clear();
        if (obstacle == null) {
            this._objectDetailsBox.setVisible(false);
        }
        else {
            Text title = new Text("Obstacle");
            title.setFont(Font.loadFont(VivariumApp.class.getResource(VivariumApp.fontUrl).toExternalForm(), 16));
            title.setFill(Color.WHITE);
            
            Text detailsText = new Text(obstacle.toString());
            detailsText.setFill(Color.WHITE);
            
            Button destroyButton = new Button("Détruire");
            destroyButton.setOnAction((ActionEvent) -> {
                this._vivariumManager.detruireObstacle(obstacle);
                this._gameZone.getChildren().remove(this._vivariumObjects.remove(obstacle));
                this.selectObstacle(null);
            });
            
            Button refreshButton = new Button("Rafraîchir");
            refreshButton.setOnAction((ActionEvent) -> {
                this.selectObstacle(obstacle);
            });
            
            HBox buttons = new HBox(3, destroyButton, refreshButton);
            
            this._objectDetailsBox.getChildren().addAll(title, detailsText, buttons);
            this._objectDetailsBox.setVisible(true);
        }
    }
    
    private void selectObject(VivariumObject object) {
        if (this._selectedObject != null && this._vivariumObjects.get(this._selectedObject) != null) {
            this._vivariumObjects.get(this._selectedObject).setEffect(null);
        }
        this._selectedObject = object;
        if (object != null && this._vivariumObjects.get(object) != null) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setContrast(0.5);     
            colorAdjust.setHue(0.2);     
            colorAdjust.setBrightness(-0.2);  
            colorAdjust.setSaturation(0.8);   
            this._vivariumObjects.get(object).setEffect(colorAdjust);
        }
    }
}
