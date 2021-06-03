package com.seifabdelaziz.tetris;

import com.seifabdelaziz.tetris.Engine.GameManager;
import com.seifabdelaziz.tetris.Engine.Soundtrack;
import com.seifabdelaziz.tetris.Scenes.Menu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class Tetris extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Tetris");
        stage.setResizable(false);
        BorderPane root = new BorderPane();

        GameManager gameManager = GameManager.getInstance();
        gameManager.setHighScore(Integer.parseInt(readKey("Highscore", "0")), false);
        gameManager.setRoot(root);
        gameManager.setApplication(this);
        Scene scene = new Scene(root, 1050, 810);
        gameManager.setGame(scene);
        gameManager.setSoundtrack(new Soundtrack("./resources/audio/tetris.mp3")); // TODO: Make looping sound better
        gameManager.setMusicVolume(Double.parseDouble(readKey("Music",
                String.valueOf(GameManager.defaultMusicVolume))), false);
        gameManager.setSoundEffectsVolume(Double.parseDouble(readKey("Sound Effects",
                String.valueOf(GameManager.defaultSoundEffectsVolume))), false);

        stage.setScene(scene);
        stage.show();
        Menu menu = new Menu();
        gameManager.setCurrentScene(menu);
        root.setCenter(menu);
        root.getCenter().requestFocus();
    }

    public void setUpDb() {
        StringBuilder db = new StringBuilder("Highscore: 0\nMusic: " + GameManager.defaultMusicVolume + "\nSound" +
                " Effects: " + GameManager.defaultSoundEffectsVolume);
        try{
            FileWriter writer = new FileWriter("db.txt");
            writer.write(String.valueOf(db), 0, db.length());
            writer.close();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public String readKey(String key, String def) {
        try {
            try {
                File file = new File("db.txt");
                Scanner in = new Scanner(file);

                while(in.hasNext()) {
                    String nextLine = in.nextLine();
                    if(nextLine.contains(key)) {
                        System.out.println(Double.parseDouble(nextLine.replaceAll(key + ": ", "")));
                        return nextLine.replaceAll(key + ": ", "");
                    }
                }

                setUpDb();
                return def;
            } catch(FileNotFoundException e) {
                setUpDb();
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        return def;
    }
}
