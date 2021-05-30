package com.seifabdelaziz.tetris.Scenes;

import com.seifabdelaziz.tetris.Engine.GameManager;
import com.seifabdelaziz.tetris.Engine.MyButton;
import com.seifabdelaziz.tetris.Engine.World;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HowToPlay extends World {
    public HowToPlay() {
        Scene scene = GameManager.getInstance().getGame();

        StackPane stackPane = new StackPane();
        BorderPane menuBorderPane = new BorderPane();
        menuBorderPane.setMinWidth(scene.getWidth());
        menuBorderPane.setMinHeight(scene.getHeight());

        Matrix matrix = new Matrix(this, (int) (scene.getHeight() / TILE_SIZE),
                (int) (scene.getWidth() / TILE_SIZE), TILE_SIZE, 0, 0, MATRIX_TILE_IMAGE);

        BorderPane titleBorderPane = new BorderPane();
        Text titleText = new Text("How To Play");
        titleText.setFont(Font.loadFont("file:resources/fonts/Kenney Mini Square.ttf", 70));
        Reflection ref = new Reflection();
        ref.setTopOpacity(0.4);
        ref.setTopOffset(0.5);
        ref.setBottomOpacity(0.05);
        titleText.setEffect(ref);
        titleText.setFill(Color.WHITE);
        titleText.setX(50);
        titleText.setY(100);
        titleBorderPane.setCenter(titleText);
        titleBorderPane.setPadding(new Insets(TILE_SIZE, 0, 0, 0));

        BorderPane backButtonPane = new BorderPane();
        Image backButtonImage = new Image("resources/images/green_sliderLeft.png");
        MyButton backButton = new MyButton("", backButtonImage, Menu.class);
        backButton.setPadding(new Insets(0, 0, TILE_SIZE, TILE_SIZE));
        backButtonPane.setLeft(backButton);
        menuBorderPane.setBottom(backButtonPane);

        menuBorderPane.setTop(titleBorderPane);

        stackPane.getChildren().addAll(menuBorderPane);
        getChildren().add(stackPane);

        setOnKeyPressed(keyEvent -> addKeyCode(keyEvent.getCode()));
        setOnKeyReleased(keyEvent -> removeKeyCode(keyEvent.getCode()));
    }

    @Override
    public void act(long now) {

    }
}
