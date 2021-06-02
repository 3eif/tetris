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
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Options extends World {
    public Options() {
        Scene scene = GameManager.getInstance().getGame();

        StackPane stackPane = new StackPane();
        BorderPane menuBorderPane = new BorderPane();
        menuBorderPane.setMinWidth(scene.getWidth());
        menuBorderPane.setMinHeight(scene.getHeight());

        Matrix matrix = new Matrix(this, (int) (scene.getHeight() / TILE_SIZE),
                (int) (scene.getWidth() / TILE_SIZE), TILE_SIZE, 0, 0, MATRIX_TILE_IMAGE);

        BorderPane titleBorderPane = new BorderPane();
        Text titleText = new Text("Options");
        titleText.setFont(Font.loadFont("file:resources/fonts/Kenney Mini Square.ttf", 70));
        Reflection ref = new Reflection();
        ref.setTopOpacity(0.4);
        ref.setTopOffset(0.5);
        ref.setBottomOpacity(0.05);
        titleText.setEffect(ref);
        titleText.setFill(Color.web("#FFCC00"));
        titleText.setX(50);
        titleText.setY(100);
        titleBorderPane.setCenter(titleText);
        titleBorderPane.setPadding(new Insets(TILE_SIZE, 0, 0, 0));

        BorderPane bottomBorderPane = new BorderPane();
        BorderPane backButtonPane = new BorderPane();
        Image backButtonImage = new Image("resources/images/yellow_sliderLeft.png");
        MyButton backButton = new MyButton("", backButtonImage, Menu.class);
        backButton.setPadding(new Insets(0, 0, TILE_SIZE, TILE_SIZE));
        backButtonPane.setLeft(backButton);

        bottomBorderPane.setLeft(backButtonPane);
        menuBorderPane.setBottom(bottomBorderPane);
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
