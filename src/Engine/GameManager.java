package com.seifabdelaziz.tetris.Engine;

import javafx.application.Application;
import javafx.scene.Scene;
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

    private Application application;

    private GameManager() {}

    public static GameManager getInstance() {
        return instance;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
        boolean s = soundtrack == null || soundtrack.isMute();

        StringBuilder db = new StringBuilder("Highscore: " + highScore + "\nMusic: " + (!s ? "ON" : "OFF"));
        writeToDb(db);
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

    public boolean music() {
        return soundtrack.isMute();
    }

    public void setMusic(boolean music) {
        soundtrack.mute(!music);

        StringBuilder db = new StringBuilder("Highscore: " + getHighScore() + "\nMusic: " + (music ? "ON" : "OFF"));
        writeToDb(db);
    }

    private void writeToDb(StringBuilder data) {
        try{
            FileWriter writer = new FileWriter("db.txt");
            writer.write(String.valueOf(data), 0, data.length());
            writer.close();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
