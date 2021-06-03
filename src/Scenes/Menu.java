package com.seifabdelaziz.tetris.Scenes;

import com.seifabdelaziz.tetris.Engine.*;
import com.seifabdelaziz.tetris.Tetriminoes.*;
import com.seifabdelaziz.tetris.Tiles.Matrix;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Menu extends World {
    private long prev = 0;
    private ArrayList<Tetrimino> tetriminos = new ArrayList<Tetrimino>();

    public Menu() {
        GameManager gameManager = GameManager.getInstance();
        Scene scene = gameManager.getGame();

        StackPane stackPane = new StackPane();
        BorderPane menuBorderPane = new BorderPane();
        menuBorderPane.setMinWidth(scene.getWidth());
        menuBorderPane.setMinHeight(scene.getHeight());

        new Matrix(this, (int) (scene.getHeight() / TILE_SIZE), (int) (scene.getWidth() / TILE_SIZE),
                TILE_SIZE, 0, 0, MATRIX_TILE_IMAGE);

        BorderPane titleBorderPane = new BorderPane();
        VBox titleVBox = new VBox();
        titleVBox.setAlignment(Pos.CENTER);

        Text titleText = new Text("Tetris");
        titleText.setFont(Font.loadFont("file:resources/fonts/Kenney Mini Square.ttf", 80));
        Reflection ref = new Reflection();
        ref.setTopOpacity(0.5);
        ref.setBottomOpacity(0);
        titleText.setEffect(ref);
        titleText.setFill(Color.WHITE);
        titleText.setX(50);
        titleText.setY(100);

        BorderPane highscoreBorderPane = new BorderPane();
        Score highscore = new Score("Highscore: ");
        highscore.setEffect(ref);
        highscore.setScoreVal(gameManager.getHighScore());
        highscoreBorderPane.setCenter(highscore);
        highscoreBorderPane.setPadding(new Insets(100, 0, 0, 0));

        titleVBox.getChildren().addAll(titleText, highscoreBorderPane);
        titleBorderPane.setCenter(titleVBox);
        titleBorderPane.setPadding(new Insets(TILE_SIZE, 0, 0, 0));
        menuBorderPane.setTop(titleBorderPane);

        VBox buttonsHBox = new VBox();
        buttonsHBox.setSpacing(15);

        Image playButtonImage = new Image("resources/images/blue_button04.png");
        Image playButtonImageClick = new Image("resources/images/blue_button05.png");
        MyButton playButton = new MyButton("Play", playButtonImage, playButtonImageClick, TetrisWorld.class);
        playButton.setFont(Font.loadFont("file:resources/fonts/Kenney Mini Square.ttf", 30));
        playButton.setTextFill(Color.WHITE);

        Image optionsButtonImage = new Image("resources/images/yellow_button04.png");
        Image optionsButtonImageClick = new Image("resources/images/yellow_button05.png");
        MyButton optionsButton = new MyButton("Options", optionsButtonImage, optionsButtonImageClick, Options.class);
        optionsButton.setFont(Font.loadFont("file:resources/fonts/Kenney Mini Square.ttf", 30));
        optionsButton.setTextFill(Color.WHITE);

        Image howToPlayButtonImage = new Image("resources/images/green_button04.png");
        Image howToPlayButtonImageClick = new Image("resources/images/green_button05.png");
        MyButton howToPlayButton = new MyButton("How to Play", howToPlayButtonImage,
                howToPlayButtonImageClick, HowToPlay.class);
        howToPlayButton.setFont(Font.loadFont("file:resources/fonts/Kenney Mini Square.ttf", 25));
        howToPlayButton.setTextFill(Color.WHITE);

        Image creditsButtonImage = new Image("resources/images/grey_button03.png");
        Image creditsButtonImageClick = new Image("resources/images/grey_button04.png");
        MyButton creditsButton = new MyButton("Credits", creditsButtonImage,
                creditsButtonImageClick, Credits.class);
        creditsButton.setFont(Font.loadFont("file:resources/fonts/Kenney Mini Square.ttf", 25));
        creditsButton.setTextFill(Color.GREY);

        Image quitButtonImage = new Image("resources/images/red_button01.png");
        Image quitButtonImageClick = new Image("resources/images/red_button02.png");
        MyButton quitButton = new MyButton("Quit", quitButtonImage,
                quitButtonImageClick, Quit.class);
        quitButton.setFont(Font.loadFont("file:resources/fonts/Kenney Mini Square.ttf", 25));
        quitButton.setTextFill(Color.WHITE);

        buttonsHBox.getChildren().addAll(playButton, optionsButton, howToPlayButton, creditsButton, quitButton);
        buttonsHBox.setAlignment(Pos.CENTER);
        menuBorderPane.setCenter(buttonsHBox);

        stackPane.getChildren().addAll(menuBorderPane);
        getChildren().add(stackPane);
    }

    @Override
    public void act(long now) {
        Scene scene = GameManager.getInstance().getGame();

        if (now - prev > (1e6 * 2500)) {
            prev = now;

            Class[] tetriminoShapes = {ITetrimino.class, JTetrimino.class, LTetrimino.class, OTetrimino.class, STetrimino.class,
                    TTetrimino.class, ZTetrminio.class};
            Class tetriminoShape = tetriminoShapes[(int)(Math.random() * tetriminoShapes.length)];

            Actor actor = null;
            try {
                actor = (Actor) tetriminoShape.getDeclaredConstructor(int.class).newInstance(30);

                Tetrimino tetriminoToAdd = (Tetrimino) actor;
                tetriminoToAdd.setIsMovable(false);

                double firstSection = Math.random() * (scene.getWidth() / 5);
                firstSection -= firstSection % 30;
                double secondSection = scene.getWidth() - firstSection - tetriminoToAdd.getMaxWidth();
                secondSection -= secondSection % 30;
                boolean spawnInFirstSection = (int) (Math.random() * 2) == 1;
                tetriminoToAdd.setXPos(spawnInFirstSection ? firstSection : secondSection);
                tetriminoToAdd.setYPos(-tetriminoToAdd.getMaxHeight());

                add(tetriminoToAdd);
                tetriminos.add(tetriminoToAdd);
                tetriminoToAdd.addTiles();
            } catch(NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                System.out.println(e);
            }
        }

        if (now - getPrev() > (1e6 * 500)) {
            ArrayList<Tetrimino> tetriminosToRemove = new ArrayList<>();
            for (Tetrimino tetrimino : tetriminos) {
                tetrimino.moveVertical(30);
                if(tetrimino.getY() > scene.getHeight() + tetrimino.getMaxHeight()) {
                    tetriminosToRemove.add(tetrimino);
                }
            }
            getChildren().removeAll(tetriminosToRemove);
        }
    }
}
