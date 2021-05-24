import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Tetris extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Tetris");
        stage.setResizable(false);
        BorderPane root = new BorderPane();

        double sceneWidth = 900;
        double sceneHeight = 700;

        Scene scene = new Scene(root, sceneWidth, sceneHeight);

        String matrixTileImagePath = getClass().getClassLoader().getResource("black-tile.png").toString();
        Image matrixTileImage = new Image(matrixTileImagePath);

        TetrisWorld tetrisWorld = new TetrisWorld(30, matrixTileImage, sceneWidth, sceneHeight);
        tetrisWorld.setMinHeight(scene.getHeight());
        tetrisWorld.setMinWidth(scene.getWidth());
        tetrisWorld.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.UP && !tetrisWorld.isKeyDown(KeyCode.UP))
                tetrisWorld.setShouldRotate(true);
            if (keyEvent.getCode() == KeyCode.C && !tetrisWorld.isKeyDown(KeyCode.C))
                tetrisWorld.setShouldHold(true);
            if (keyEvent.getCode() == KeyCode.DOWN && !tetrisWorld.isKeyDown(KeyCode.DOWN))
                tetrisWorld.setShouldMoveDown(true);
            if ((keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.RIGHT) &&
                    (!tetrisWorld.isKeyDown(KeyCode.RIGHT) || !tetrisWorld.isKeyDown(KeyCode.LEFT)))
                tetrisWorld.setShouldMoveToNextTile(true);
            tetrisWorld.addKeyCode(keyEvent.getCode());
        });
        tetrisWorld.setOnKeyReleased(keyEvent -> tetrisWorld.removeKeyCode(keyEvent.getCode()));
        tetrisWorld.start();

        root.setCenter(tetrisWorld);
        stage.setScene(scene);
        stage.show();
        tetrisWorld.requestFocus();

        tetrisWorld.spawnTetrimino();
    }
}
