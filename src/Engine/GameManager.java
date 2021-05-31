package com.seifabdelaziz.tetris.Engine;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

import java.io.FileWriter;
import java.io.IOException;

public class GameManager {
    private static final GameManager instance = new GameManager();
    private int highScore;

    private World currentScene;
    private BorderPane root;
    private Scene game;

    private Soundtrack soundtrack;

    private GameManager() {}

    public static GameManager getInstance() {
        return instance;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;

        try {
            FileWriter writer = new FileWriter("highscore.txt");
            writer.write(String.valueOf(highScore), 0, String.valueOf(highScore).length());
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public World getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(World currentScene) {
        if(this.currentScene != null) this.currentScene.stop();
        this.currentScene = currentScene;
        root.setCenter(currentScene);
        currentScene.start();
        currentScene.requestFocus();
    }

    public void setRoot(BorderPane root) {
        this.root = root;
    }

    public Scene getGame() {
        return game;
    }

    public void setGame(Scene game) {
        this.game = game;
    }

    public Soundtrack getSoundtrack() {
        return soundtrack;
    }

    public void setSoundtrack(Soundtrack soundtrack) {
        if(this.soundtrack != null) this.soundtrack.stop();
        this.soundtrack = soundtrack;
        soundtrack.play();
        soundtrack.loop(true);
        soundtrack.setVolume(0.1);
    }
}