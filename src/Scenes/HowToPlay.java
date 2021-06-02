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
        titleText.setFill(Color.web("#73CC4A"));
        titleText.setX(50);
        titleText.setY(100);
        titleBorderPane.setCenter(titleText);
        titleBorderPane.setPadding(new Insets(TILE_SIZE, 0, 0, 0));

        VBox howToPlayVBox = new VBox();
        howToPlayVBox.setPadding(new Insets(90, 0, 0, 0));
        howToPlayVBox.setSpacing(10);
        BorderPane howToPlayTextBorderPane = new BorderPane();
        Text howToPlayText = new Text("Tetrimino blocks drop down from the top of the screen one by one. " +
                "You must prevent the tetriminos from stacking up to the top otherwise you will lose. To prevent the" +
                " tetriminos from stacking, you must strategically place them so that they fill up each row. When one" +
                " row is filled up, all the tiles in that row are deleted and the blocks above that row fall one block" +
                " down.\n\nThe goal of the game is to prevent the tetriminos from stacking up for as long as possible and" +
                " to get a new high score.");
        howToPlayText.setFill(Color.WHITE);
        howToPlayText.setWrappingWidth(900);
        howToPlayText.setFont(new Font(22));
        howToPlayTextBorderPane.setCenter(howToPlayText);
        howToPlayVBox.getChildren().addAll(howToPlayTextBorderPane);

        BorderPane bottomBorderPane = new BorderPane();

        BorderPane backButtonPane = new BorderPane();
        Image backButtonImage = new Image("resources/images/green_sliderLeft.png");
        MyButton backButton = new MyButton("", backButtonImage, Menu.class);
        backButton.setPadding(new Insets(0, 0, TILE_SIZE, TILE_SIZE));
        backButtonPane.setLeft(backButton);

        BorderPane controlsBorderPane = new BorderPane();
        controlsBorderPane.setMinHeight(300);
        HBox controlsHBox = new HBox();
        controlsHBox.setAlignment(Pos.CENTER);
        controlsHBox.setSpacing(50);
        controlsBorderPane.setCenter(controlsHBox);

        VBox moveLeftControl = new VBox();
        moveLeftControl.setAlignment(Pos.CENTER);
        moveLeftControl.setSpacing(20);
        Text moveLeftText = new Text("Move\nLeft");
        moveLeftText.setTextAlignment(TextAlignment.CENTER);
        moveLeftText.setFont(Font.loadFont("file:resources/fonts/Kenney Future Narrow.ttf", 27));
        moveLeftText.setFill(Color.WHITE);
        ImageView moveLeftImage = new ImageView(new Image("./resources/images/shadedLight24.png"));
        moveLeftControl.getChildren().addAll(moveLeftText, moveLeftImage);

        VBox moveRightControl = new VBox();
        moveRightControl.setAlignment(Pos.CENTER);
        moveRightControl.setSpacing(20);
        Text moveRightText = new Text("Move\nRight");
        moveRightText.setTextAlignment(TextAlignment.CENTER);
        moveRightText.setFont(Font.loadFont("file:resources/fonts/Kenney Future Narrow.ttf", 27));
        moveRightText.setFill(Color.WHITE);
        ImageView moveRightImage = new ImageView(new Image("./resources/images/shadedLight25.png"));
        moveRightControl.getChildren().addAll(moveRightText, moveRightImage);

        VBox moveDownControl = new VBox();
        moveDownControl.setAlignment(Pos.CENTER);
        moveDownControl.setSpacing(20);
        Text moveDownText = new Text("Move\nDown");
        moveDownText.setTextAlignment(TextAlignment.CENTER);
        moveDownText.setFont(Font.loadFont("file:resources/fonts/Kenney Future Narrow.ttf", 27));
        moveDownText.setFill(Color.WHITE);
        ImageView moveDownImage = new ImageView(new Image("./resources/images/shadedLight27.png"));
        moveDownControl.getChildren().addAll(moveDownText, moveDownImage);

        VBox rotateControl = new VBox();
        rotateControl.setAlignment(Pos.CENTER);
        rotateControl.setSpacing(20);
        Text rotateText = new Text("Rotate\nTetrimino");
        rotateText.setTextAlignment(TextAlignment.CENTER);
        rotateText.setFont(Font.loadFont("file:resources/fonts/Kenney Future Narrow.ttf", 27));
        rotateText.setFill(Color.WHITE);
        ImageView rotateImage = new ImageView(new Image("./resources/images/shadedLight26.png"));
        rotateControl.getChildren().addAll(rotateText, rotateImage);

        VBox holdControl = new VBox();
        holdControl.setAlignment(Pos.CENTER);
        holdControl.setSpacing(20);
        Text holdText = new Text("Hold\nTetrimino");
        holdText.setTextAlignment(TextAlignment.CENTER);
        holdText.setFont(Font.loadFont("file:resources/fonts/Kenney Future Narrow.ttf", 27));
        holdText.setFill(Color.WHITE);
        ImageView holdImage = new ImageView(new Image("./resources/images/shadedLight38.png"));
        holdControl.getChildren().addAll(holdText, holdImage);

        controlsHBox.getChildren().addAll(moveLeftControl, moveRightControl, moveDownControl,
                rotateControl, holdControl);

        bottomBorderPane.setLeft(backButtonPane);
        bottomBorderPane.setTop(controlsBorderPane);

        menuBorderPane.setBottom(bottomBorderPane);
        menuBorderPane.setTop(titleBorderPane);
        menuBorderPane.setCenter(howToPlayVBox);

        stackPane.getChildren().addAll(menuBorderPane);
        getChildren().add(stackPane);

        setOnKeyPressed(keyEvent -> addKeyCode(keyEvent.getCode()));
        setOnKeyReleased(keyEvent -> removeKeyCode(keyEvent.getCode()));
    }

    @Override
    public void act(long now) {

    }
}
