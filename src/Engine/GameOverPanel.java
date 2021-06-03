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

public class GameOverPanel extends PopupPanel {
    private Score score;

    public GameOverPanel() {
        super(600, 500);

        GameManager gameManager = GameManager.getInstance();
        BorderPane content = new BorderPane();
        getPanel().setFill(Color.web("#e74c3c", 0.4));

        Text title = new Text("GAME OVER!");
        title.setFill(Color.WHITE);
        title.setFont(Font.loadFont("file:resources/fonts/Kenney Mini Square.ttf", 75));

        VBox pauseVBox = new VBox();
        pauseVBox.setSpacing(30);
        pauseVBox.setAlignment(Pos.CENTER);
        HBox pauseButtonIcons = new HBox();
        pauseButtonIcons.setSpacing(10);
        pauseButtonIcons.setAlignment(Pos.CENTER);

        VBox scoresVBox = new VBox();
        scoresVBox.setAlignment(Pos.CENTER);
        scoresVBox.setSpacing(5);
        Score highscore = new Score("Highscore: ");
        highscore.setScoreVal(gameManager.getHighScore());
        score = new Score("Score: ");
        scoresVBox.getChildren().addAll(score, highscore);

        Image homeButtonImage = new Image("resources/images/home.png", 50, 50, true, true);
        MyButton homeButton = new MyButton("", homeButtonImage, Menu.class);
        Image quitButtonImage = new Image("resources/images/power.png", 50, 50, true, true);
        MyButton quitButton = new MyButton("", quitButtonImage, Quit.class);
        pauseButtonIcons.getChildren().addAll(homeButton, quitButton);

        Image retryButtonImage = new Image("resources/images/red_button00.png");
        MyButton resumeButton = new MyButton("RETRY", retryButtonImage, TetrisWorld.class);
        resumeButton.setFont(Font.loadFont("file:resources/fonts/Kenney Mini Square.ttf", 30));
        resumeButton.setTextFill(Color.WHITE);

        pauseVBox.getChildren().addAll(title, scoresVBox, resumeButton, pauseButtonIcons);
        content.setCenter(pauseVBox);
        setContent(content);
    }

    public void setScore(int s) {
        score.setScoreVal(s);
    }
}
