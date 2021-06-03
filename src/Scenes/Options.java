package com.seifabdelaziz.tetris.Scenes;

import com.seifabdelaziz.tetris.Engine.GameManager;
import com.seifabdelaziz.tetris.Engine.MyButton;
import com.seifabdelaziz.tetris.Engine.World;
import com.seifabdelaziz.tetris.Tiles.Matrix;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Options extends World {
    public Options() {
        GameManager gameManager = GameManager.getInstance();
        Scene scene = gameManager.getGame();

        StackPane stackPane = new StackPane();
        BorderPane menuBorderPane = new BorderPane();
        menuBorderPane.setMinWidth(scene.getWidth());
        menuBorderPane.setMinHeight(scene.getHeight());

        new Matrix(this, (int) (scene.getHeight() / TILE_SIZE), (int) (scene.getWidth() / TILE_SIZE),
                TILE_SIZE, 0, 0, MATRIX_TILE_IMAGE);

        BorderPane titleBorderPane = new BorderPane();
        Text titleText = new Text("Options");
        titleText.setFont(Font.loadFont("file:resources/fonts/Kenney Mini Square.ttf", 70));
        Reflection ref = new Reflection();
        ref.setTopOpacity(0.4);
        ref.setTopOffset(0.5);
        ref.setBottomOpacity(0.05);
        titleText.setEffect(ref);
        titleText.setFill(Color.web("#FFCC00"));
        titleText.setX(50);
        titleText.setY(100);
        titleBorderPane.setCenter(titleText);
        titleBorderPane.setPadding(new Insets(TILE_SIZE, 0, 0, 0));

        BorderPane bottomBorderPane = new BorderPane();
        BorderPane backButtonPane = new BorderPane();
        Image backButtonImage = new Image("resources/images/yellow_sliderLeft.png");
        MyButton backButton = new MyButton("", backButtonImage, Menu.class);
        backButton.setPadding(new Insets(0, 0, TILE_SIZE, TILE_SIZE));
        backButtonPane.setLeft(backButton);

        VBox optionsVBox = new VBox();
        optionsVBox.setAlignment(Pos.CENTER);
        optionsVBox.setSpacing(20);

        HBox soundEffectsHBox = new HBox();
        soundEffectsHBox.setSpacing(25);
        soundEffectsHBox.setAlignment(Pos.CENTER);

        Slider soundEffectsSlider = new Slider();
        soundEffectsSlider.setMin(0);
        soundEffectsSlider.setMax(1);
        soundEffectsSlider.setValue(gameManager.getSoundEffectsVolume());
        soundEffectsSlider.setMinWidth(300);

        Image muteSoundEffectsButtonImage = new Image("resources/images/audioOn.png");
        Image unMuteSoundEffectsButtonImage = new Image("resources/images/audioOff.png");
        MyButton soundEffectsButton = new MyButton("", gameManager.getSoundEffectsVolume() == 0 ? unMuteSoundEffectsButtonImage : muteSoundEffectsButtonImage,
                gameManager.getSoundEffectsVolume() == 0 ? muteSoundEffectsButtonImage : unMuteSoundEffectsButtonImage);

        soundEffectsButton.setOnMouseClicked(keyEvent -> {
            double newVolume = gameManager.getSoundEffectsVolume() > 0 ? 0 : 1;
            gameManager.setSoundEffectsVolume(newVolume, true);
            soundEffectsSlider.setValue(newVolume);
        });
        soundEffectsSlider.valueProperty().addListener((observableValue, oldVal, newVal) -> {
            gameManager.setSoundEffectsVolume((Double) newVal, true);
            soundEffectsButton.setGraphic(new ImageView((Double) newVal > 0 ? muteSoundEffectsButtonImage : unMuteSoundEffectsButtonImage));
        });

        HBox musicHBox = new HBox();
        musicHBox.setSpacing(25);
        musicHBox.setAlignment(Pos.CENTER);

        Slider musicSlider = new Slider();
        musicSlider.setMin(0);
        musicSlider.setMax(1);
        musicSlider.setValue(gameManager.getMusicVolume());
        musicSlider.setMinWidth(300);

        Image muteButtonImage = new Image("resources/images/musicOn.png");
        Image unMuteButtonImage = new Image("resources/images/musicOff.png");
        MyButton musicButton = new MyButton("", gameManager.getMusicVolume() == 0 ? unMuteButtonImage : muteButtonImage,
                gameManager.getMusicVolume() == 0 ? muteButtonImage : unMuteButtonImage);

        musicButton.setOnMouseClicked(keyEvent -> {
            double newVolume = gameManager.getMusicVolume() > 0 ? 0 : 1;
            gameManager.setMusicVolume(newVolume, true);
            musicSlider.setValue(newVolume);
        });
        musicSlider.valueProperty().addListener((observableValue, oldVal, newVal) -> {
            gameManager.setMusicVolume((Double) newVal, true);
            musicButton.setGraphic(new ImageView((Double) newVal > 0 ? muteButtonImage : unMuteButtonImage));
        });

        musicHBox.getChildren().addAll(musicButton, musicSlider);
        soundEffectsHBox.getChildren().addAll(soundEffectsButton, soundEffectsSlider);
        optionsVBox.getChildren().addAll(soundEffectsHBox, musicHBox);
        bottomBorderPane.setLeft(backButtonPane);
        menuBorderPane.setBottom(bottomBorderPane);
        menuBorderPane.setCenter(optionsVBox);
        menuBorderPane.setTop(titleBorderPane);

        stackPane.getChildren().addAll(menuBorderPane);
        getChildren().add(stackPane);

        setOnKeyPressed(keyEvent -> addKeyCode(keyEvent.getCode()));
        setOnKeyReleased(keyEvent -> removeKeyCode(keyEvent.getCode()));
    }

    @Override
    public void act(long now) { }
}
