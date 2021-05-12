import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;

public class Tetris extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Tetris");
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 500, 700);

        TetrisWorld tetrisWorld = new TetrisWorld(5.0);
        tetrisWorld.setMinHeight(scene.getHeight());
        tetrisWorld.setMinWidth(scene.getWidth());

        String tetriminoImagePath = getClass().getClassLoader().getResource("./resources/o-piece.png").toString();
        Image tetriminoImage = new Image(tetriminoImagePath);

        Tetrimino tetrimino = new Tetrimino(tetriminoImage);
        tetrimino.setY(10);
        tetrimino.setX(10);
        tetrisWorld.add(tetrimino);
        tetrisWorld.start();

        root.setCenter(tetrisWorld);
        stage.setScene(scene);
        stage.show();
        tetrisWorld.requestFocus();
    }
}
