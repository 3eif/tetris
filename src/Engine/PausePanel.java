package com.seifabdelaziz.tetris.Engine;

import com.seifabdelaziz.tetris.Scenes.Menu;
import com.seifabdelaziz.tetris.Scenes.Quit;
import com.seifabdelaziz.tetris.Scenes.TetrisWorld;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PausePanel extends PopupPanel {
    public PausePanel() {
        super(400, 400);

        GameManager gameManager = GameManager.getInstance();
        BorderPane content = new BorderPane();

        Text title = new Text("Paused");
        title.setFill(Color.WHITE);
        title.setFont(Font.loadFont("file:resources/fonts/Kenney Mini Square.ttf", 75));

        VBox pauseVBox = new VBox();
        pauseVBox.setSpacing(30);
        pauseVBox.setAlignment(Pos.CENTER);
        HBox pauseButtonIcons = new HBox();
        pauseButtonIcons.setSpacing(10);
        pauseButtonIcons.setAlignment(Pos.CENTER);

        Image retryButtonImage = new Image("resources/images/retry.png", 50, 50, true, true);
        MyButton retryButton = new MyButton("", retryButtonImage, TetrisWorld.class);
        Image homeButtonImage = new Image("resources/images/home.png", 50, 50, true, true);
        MyButton homeButton = new MyButton("", homeButtonImage, Menu.class);
        Image quitButtonImage = new Image("resources/images/power.png", 50, 50, true, true);
        MyButton quitButton = new MyButton("", quitButtonImage, Quit.class);
        pauseButtonIcons.getChildren().addAll(retryButton, homeButton, quitButton);

        Image resumeButtonImage = new Image("resources/images/blue_button04.png");
        MyButton resumeButton = new MyButton("RESUME", resumeButtonImage);
        resumeButton.setFont(Font.loadFont("file:resources/fonts/Kenney Mini Square.ttf", 30));
        resumeButton.setTextFill(Color.WHITE);
        resumeButton.setOnMousePressed(mouseEvent -> {
            TetrisWorld tetrisWorld = (TetrisWorld) gameManager.getCurrentScene();
            tetrisWorld.setPaused(!tetrisWorld.isPaused());
        });

        pauseVBox.getChildren().addAll(title, resumeButton, pauseButtonIcons);
        content.setCenter(pauseVBox);
        setContent(content);
    }
}
