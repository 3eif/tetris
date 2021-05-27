package com.seifabdelaziz.tetris.Scenes;

import com.seifabdelaziz.tetris.Engine.GameManager;
import com.seifabdelaziz.tetris.Engine.World;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Menu extends World {
    public Menu() {
        Scene scene = GameManager.getInstance().getGame();

        Rectangle background = new Rectangle();
        background.setWidth(scene.getWidth());
        background.setHeight(scene.getHeight());
        getChildren().add(background);

        Text titleText = new Text("Tetris");
        titleText.setFont(Font.loadFont("file:resources/fonts/Kenney Mini Square.ttf", 50));
        titleText.setFill(Color.WHITE);
        titleText.setX(50);
        titleText.setY(100);

        Text wipText = new Text("This UI is a WIP");
        wipText.setFont(Font.loadFont("file:resources/fonts/Kenney Mini Square.ttf", 30));
        wipText.setFill(Color.WHITE);
        wipText.setX(50);
        wipText.setY(145);

        Text playText = new Text("Press P to play");
        playText.setFont(Font.loadFont("file:resources/fonts/Kenney Mini Square.ttf", 40));
        playText.setFill(Color.WHITE);
        playText.setX(50);
        playText.setY(195);

        ImageView button = new ImageView(new Image("resources/images/blue_button00.png"));
        button.setX(50);
        button.setY(250);

        String hoverSoundPath = getClass().getClassLoader().getResource("resources/audio/rollover2.mp3").toString();
        AudioClip hoverSound = new AudioClip(hoverSoundPath);
        button.hoverProperty().addListener((observableValue, oldVal, newVal) -> {
            if(newVal) {
                button.setImage(new Image("resources/images/blue_button01.png"));
                button.setY(254);
                hoverSound.play();
            } else {
                button.setImage(new Image("resources/images/blue_button00.png"));
                button.setY(250);
            }
        });

        getChildren().addAll(titleText, wipText, playText, button);

        setOnKeyPressed(keyEvent -> addKeyCode(keyEvent.getCode()));
        setOnKeyReleased(keyEvent -> removeKeyCode(keyEvent.getCode()));
    }

    @Override
    public void act(long now) {
        if(isKeyDown(KeyCode.P)) {
            GameManager gameManager = GameManager.getInstance();
            gameManager.setCurrentScene(new TetrisWorld());
        }
    }
}
