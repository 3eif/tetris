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
        gameManager.setHighScore(getHighscore()); // TODO: Change this when you implement storing high scores in txt file or local db
        gameManager.setRoot(root);
        gameManager.setApplication(this);
        Scene scene = new Scene(root, 1050, 810);
        gameManager.setGame(scene);
        gameManager.setSoundtrack(new Soundtrack("./resources/audio/tetris.mp3")); // TODO: Make looping sound better
        gameManager.setMusic(getMusic());

        stage.setScene(scene);
        stage.show();
        Menu menu = new Menu();
        gameManager.setCurrentScene(menu);
        root.setCenter(menu);
        root.getCenter().requestFocus();
    }

    public void setUpDb() {
        StringBuilder db = new StringBuilder("Highscore: 0\nMusic: ON");
        try{
            FileWriter writer = new FileWriter("db.txt");
            writer.write(String.valueOf(db), 0, db.length());
            writer.close();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public boolean getMusic() {
        try {
            try {
                File file = new File("db.txt");
                Scanner in = new Scanner(file);

                while(in.hasNext()) {
                    String nextLine = in.nextLine();
                    if(nextLine.contains("Music"))
                        return nextLine.replaceAll("Music: ", "").equals("ON");
                }

                setUpDb();
                return true;
            } catch(FileNotFoundException e) {
                setUpDb();
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        return true;
    }

    public int getHighscore() {
        try {
            try {
                File file = new File("db.txt");
                Scanner in = new Scanner(file);

                while(in.hasNextLine()) {
                    String nextLine = in.nextLine();
                    if(nextLine.contains("Highscore")) {
                        return Integer.parseInt(nextLine.replaceAll("Highscore: ", ""));
                    }
                }

                setUpDb();
                return 0;
            } catch(FileNotFoundException e) {
                setUpDb();
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        return 0;
    }
}
