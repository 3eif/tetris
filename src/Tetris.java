package com.seifabdelaziz.tetris;

import com.seifabdelaziz.tetris.Engine.GameManager;
import com.seifabdelaziz.tetris.Engine.Soundtrack;
import com.seifabdelaziz.tetris.Scenes.Menu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
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
        gameManager.setHighScore(getHighScore()); // TODO: Change this when you implement storing high scores in txt file or local db
        gameManager.setRoot(root);
        Scene scene = new Scene(root, 1050, 810);
        gameManager.setGame(scene);
        gameManager.setSoundtrack(new Soundtrack("./resources/audio/tetris.mp3")); // TODO: Make looping sound better

        stage.setScene(scene);
        stage.show();
        Menu menu = new Menu();
        gameManager.setCurrentScene(menu);
        root.setCenter(menu);
        root.getCenter().requestFocus();
    }

    public int getHighScore() {
        try {
            try {
                File file = new File("highscore.txt");
                Scanner in = new Scanner(file);

                if(in.hasNext()) {
                    return in.nextInt();
                } else {
                    FileWriter writer = new FileWriter("highscore.txt");
                    writer.write("0", 0, 0);
                    writer.close();
                    return 0;
                }
            } catch(FileNotFoundException e) {
                FileWriter writer = new FileWriter("highscore.txt");
                writer.write("0", 0, 0);
                writer.close();
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        return 0;
    }
}
