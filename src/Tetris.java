import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Tetris extends Application {
    MediaPlayer soundtrackPlayer;

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

        String soundtrackAudioFilePath = getClass().getClassLoader().getResource("./resources/tetris.mp3").toString();
        Media soundtrack = new Media(soundtrackAudioFilePath);
        soundtrackPlayer = new MediaPlayer(soundtrack);
        soundtrackPlayer.setVolume(0.1);
        soundtrackPlayer.setAutoPlay(true);
        soundtrackPlayer.setOnEndOfMedia(() -> {
            soundtrackPlayer.seek(Duration.ZERO);
        });
        // TODO: Make looping sound better

        root.setCenter(tetrisWorld);
        stage.setScene(scene);
        stage.show();
        tetrisWorld.requestFocus();
        tetrisWorld.spawnTetrimino();
    }
}
