package com.seifabdelaziz.tetris.Scenes;

import com.seifabdelaziz.tetris.Engine.*;
import com.seifabdelaziz.tetris.Tetriminoes.*;
import com.seifabdelaziz.tetris.Tiles.Matrix;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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

        Matrix matrix = new Matrix(this, (int) (scene.getHeight() / TILE_SIZE),
                (int) (scene.getWidth() / TILE_SIZE), TILE_SIZE, 0, 0, MATRIX_TILE_IMAGE);

        BorderPane titleTextBorderPane = new BorderPane();
        Text titleText = new Text("Tetris");
        titleText.setFont(Font.loadFont("file:resources/fonts/Kenney Mini Square.ttf", 100));
        Reflection ref = new Reflection();
        ref.setTopOpacity(0.4);
        ref.setTopOffset(0.5);
        ref.setBottomOpacity(0.05);
        titleText.setEffect(ref);
        titleText.setFill(Color.WHITE);
        titleText.setX(50);
        titleText.setY(100);
        titleTextBorderPane.setCenter(titleText);
        titleTextBorderPane.setPadding(new Insets(TILE_SIZE, 0, 0, 0));
        menuBorderPane.setTop(titleTextBorderPane);

        VBox buttonsHBox = new VBox();
        buttonsHBox.setSpacing(20);

        Image playButtonImage = new Image("resources/images/blue_button00.png");
        Image playButtonImageClick = new Image("resources/images/blue_button01.png");
        MyButton playButton = new MyButton("Play", playButtonImage, playButtonImageClick, TetrisWorld.class);
        playButton.setFont(Font.loadFont("file:resources/fonts/Kenney Mini Square.ttf", 30));
        playButton.setTextFill(Color.WHITE);

        Image howToPlayButtonImage = new Image("resources/images/green_button00.png");
        Image howToPlayButtonImageClick = new Image("resources/images/green_button01.png");
        MyButton howToPlayButton = new MyButton("How to Play", howToPlayButtonImage,
                howToPlayButtonImageClick, HowToPlay.class);
        howToPlayButton.setFont(Font.loadFont("file:resources/fonts/Kenney Mini Square.ttf", 25));
        howToPlayButton.setTextFill(Color.WHITE);

        Image quitButtonImage = new Image("resources/images/red_button00.png");
        Image quitButtonImageClick = new Image("images/red_button00.png");
        MyButton quitButton = new MyButton("Quit", quitButtonImage,
                quitButtonImageClick, Quit.class);
        quitButton.setFont(Font.loadFont("file:resources/fonts/Kenney Mini Square.ttf", 25));
        quitButton.setTextFill(Color.WHITE);

        buttonsHBox.getChildren().addAll(playButton, howToPlayButton, quitButton);
        buttonsHBox.setAlignment(Pos.CENTER);
        menuBorderPane.setCenter(buttonsHBox);

        Soundtrack soundtrack = gameManager.getSoundtrack();
        BorderPane muteButtonPane = new BorderPane();
        Image muteButtonImage = new Image("resources/images/musicOn.png");
        Image unMuteButtonImage = new Image("resources/images/musicOff.png");
        MyButton muteButton = new MyButton("", soundtrack.isMute() ? unMuteButtonImage : muteButtonImage,
                soundtrack.isMute() ? muteButtonImage : unMuteButtonImage);
        muteButton.setPadding(new Insets(0, 0, TILE_SIZE, TILE_SIZE));
        muteButton.setOnMouseClicked(keyEvent -> {
            soundtrack.mute(!soundtrack.isMute());
        });
        muteButtonPane.setLeft(muteButton);
        menuBorderPane.setBottom(muteButtonPane);

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
            for (Tetrimino tetrimino : tetriminos) {
                tetrimino.moveVertical(30);
                if(tetrimino.getY() > scene.getHeight() + tetrimino.getMaxHeight()) {
                    remove(tetrimino);
                }
            }
        }
    }
}
