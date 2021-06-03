package com.seifabdelaziz.tetris.Scenes;

import com.seifabdelaziz.tetris.Engine.GameManager;
import com.seifabdelaziz.tetris.Engine.MyButton;
import com.seifabdelaziz.tetris.Engine.World;
import com.seifabdelaziz.tetris.Tiles.Matrix;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
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

public class Credits extends World {
    public Credits() {
        GameManager gameManager = GameManager.getInstance();
        Application application = gameManager.getApplication();
        Scene scene = gameManager.getGame();

        StackPane stackPane = new StackPane();
        BorderPane menuBorderPane = new BorderPane();
        menuBorderPane.setMinWidth(scene.getWidth());
        menuBorderPane.setMinHeight(scene.getHeight());

        new Matrix(this, (int) (scene.getHeight() / TILE_SIZE), (int) (scene.getWidth() / TILE_SIZE),
                TILE_SIZE, 0, 0, MATRIX_TILE_IMAGE);

        BorderPane titleBorderPane = new BorderPane();
        Text titleText = new Text("Credits");
        titleText.setFont(Font.loadFont("file:resources/fonts/Kenney Mini Square.ttf", 70));
        Reflection ref = new Reflection();
        ref.setTopOpacity(0.4);
        ref.setTopOffset(0.5);
        ref.setBottomOpacity(0.05);
        titleText.setEffect(ref);
        titleText.setFill(Color.web("#F7F7F7"));
        titleText.setX(50);
        titleText.setY(100);
        titleBorderPane.setCenter(titleText);
        titleBorderPane.setPadding(new Insets(TILE_SIZE, 0, 0, 0));

        BorderPane creditsBorderPane = new BorderPane();
        VBox creditsVBox = new VBox();
        creditsVBox.setAlignment(Pos.CENTER);
        creditsVBox.setSpacing(40);
        VBox creditsTextVBox = new VBox();
        creditsTextVBox.setAlignment(Pos.CENTER);

        String kenneyCreditsString = "https://www.kenney.nl";
        String team2xhCreditsLink = "https://tetrio.team2xh.net/?t=skins";
        Hyperlink kenneyCreditsHyperlink = new Hyperlink("UI game assets (buttons, icons, fonts, and sounds): " +
                kenneyCreditsString);
        kenneyCreditsHyperlink.setFont(new Font(22));
        kenneyCreditsHyperlink.setOnAction(actionEvent ->
                application.getHostServices().showDocument(kenneyCreditsString));

        Hyperlink team2xhCreditsHyperlink= new Hyperlink("Tetris tile assets: " + team2xhCreditsLink);
        team2xhCreditsHyperlink.setFont(new Font(22));
        team2xhCreditsHyperlink.setOnAction(actionEvent ->
                application.getHostServices().showDocument(team2xhCreditsLink));

        creditsTextVBox.getChildren().addAll(kenneyCreditsHyperlink, team2xhCreditsHyperlink);
        creditsVBox.getChildren().add(creditsTextVBox);

        HBox websiteScreenshots = new HBox();
        websiteScreenshots.setAlignment(Pos.CENTER);
        websiteScreenshots.setSpacing(20);
        ImageView kenneyScreenshot = new ImageView(new Image("resources/images/Kenney.png",
                400, 400, true, true));
        ImageView tetrioStatisticsScreenshot = new ImageView(new Image("resources/images/TETRIO Statistics.png",
                400, 400, true, true));
        websiteScreenshots.getChildren().addAll(kenneyScreenshot, tetrioStatisticsScreenshot);
        creditsVBox.getChildren().addAll(websiteScreenshots);

        BorderPane bottomBorderPane = new BorderPane();
        BorderPane backButtonPane = new BorderPane();
        Image backButtonImage = new Image("resources/images/grey_sliderLeft.png");
        MyButton backButton = new MyButton("", backButtonImage, Menu.class);
        backButton.setPadding(new Insets(0, 0, TILE_SIZE, TILE_SIZE));
        backButtonPane.setLeft(backButton);

        creditsBorderPane.setCenter(creditsVBox);
        bottomBorderPane.setLeft(backButtonPane);
        menuBorderPane.setCenter(creditsBorderPane);
        menuBorderPane.setBottom(bottomBorderPane);
        menuBorderPane.setTop(titleBorderPane);

        stackPane.getChildren().addAll(menuBorderPane);
        getChildren().add(stackPane);

        setOnKeyPressed(keyEvent -> addKeyCode(keyEvent.getCode()));
        setOnKeyReleased(keyEvent -> removeKeyCode(keyEvent.getCode()));
    }

    @Override
    public void act(long now) { }
}
