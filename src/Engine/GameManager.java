package com.seifabdelaziz.tetris.Engine;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.io.FileWriter;

public class GameManager {
    private static final GameManager instance = new GameManager();

    public static double defaultMusicVolume = 0.5;
    public static double defaultSoundEffectsVolume = 0.7;

    private int highScore;
    private double soundEffectsVolume;

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

    public void setHighScore(int highScore, boolean shouldWriteToDb) {
        this.highScore = highScore;
        if(shouldWriteToDb) writeToDb();
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

    public double getMusicVolume() {
        return soundtrack.getVolume();
    }

    public void setMusicVolume(double volume, boolean shouldWriteToDb) {
        soundtrack.setVolume(volume);
        if(shouldWriteToDb) writeToDb();
    }

    public double getSoundEffectsVolume() {
        return soundEffectsVolume;
    }

    public void setSoundEffectsVolume(double volume, boolean shouldWriteToDb) {
        soundEffectsVolume = volume;
        if(shouldWriteToDb) writeToDb();
    }

    private void writeToDb() {
        StringBuilder data = new StringBuilder("Highscore: " + getHighScore() + "\nMusic: " + getMusicVolume() +
                "\nSound Effects: " + soundEffectsVolume);
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
