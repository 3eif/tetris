package com.seifabdelaziz.tetris.Engine;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

import java.lang.reflect.InvocationTargetException;

public class MyButton extends Label {
    private final ImageView imageView;
    String clickSoundPath = getClass().getClassLoader().getResource("resources/audio/click2.mp3").toString();
    AudioClip clickSound = new AudioClip(clickSoundPath);

    public MyButton(String text, Image image) {
        super(text);
        this.imageView = new ImageView(image);
        setGraphic(imageView);
        setContentDisplay(ContentDisplay.CENTER);

        clickSound.setVolume(GameManager.getInstance().getSoundEffectsVolume());
        setOnMousePressed(mouseEvent -> {
            clickSound.setVolume(GameManager.getInstance().getSoundEffectsVolume());
            clickSound.play();
        });
    }

    public MyButton(String text, Image originalImage, Image clickImage) {
        super(text);
        this.imageView = new ImageView(originalImage);
        setGraphic(imageView);
        setContentDisplay(ContentDisplay.CENTER);

        setOnMousePressed(mouseEvent -> {
            clickSound.setVolume(GameManager.getInstance().getSoundEffectsVolume());
            clickSound.play();
            if(imageView.getImage() == originalImage) {
                imageView.setImage(clickImage);
                imageView.setY(this.getLayoutY() + 4);
            } else {
                imageView.setImage(originalImage);
                imageView.setY(this.getLayoutY() - 4);
            }
        });
    }

    public MyButton(String text, Image image, Class sceneToOpen) {
        super(text);
        this.imageView = new ImageView(image);
        setGraphic(imageView);
        setContentDisplay(ContentDisplay.CENTER);

        setOnMousePressed(mouseEvent -> {
            clickSound.setVolume(GameManager.getInstance().getSoundEffectsVolume());
            clickSound.play();
        });

        setOnMouseReleased(mouseEvent -> {
            GameManager gameManager = GameManager.getInstance();
            try {
                gameManager.setCurrentScene((World) sceneToOpen.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }

    public MyButton(String text, Image originalImage, Image clickImage, Class sceneToOpen) {
        super(text);
        this.imageView = new ImageView(originalImage);
        setGraphic(imageView);
        setContentDisplay(ContentDisplay.CENTER);

        setOnMousePressed(mouseEvent -> {
            clickSound.setVolume(GameManager.getInstance().getSoundEffectsVolume());
            clickSound.play();
            if(imageView.getImage() == originalImage) {
                imageView.setImage(clickImage);
                imageView.setY(this.getLayoutY() + 4);
            } else {
                imageView.setImage(originalImage);
                imageView.setY(this.getLayoutY() - 4);
            }
        });

        setOnMouseReleased(mouseEvent -> {
            GameManager gameManager = GameManager.getInstance();
            try {
                gameManager.setCurrentScene((World) sceneToOpen.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }
}
