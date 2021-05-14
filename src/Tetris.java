import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
        Scene scene = new Scene(root, 300, 600);

        TetrisWorld tetrisWorld = new TetrisWorld(30);
        tetrisWorld.setMinHeight(scene.getHeight());
        tetrisWorld.setMinWidth(scene.getWidth());

        String matrixTileImagePath = getClass().getClassLoader().getResource("black-tile.png").toString();
        Image matrixTileImage = new Image(matrixTileImagePath);

        Tile[][] matrix = new Tile[21][10];
        for(int r = 0; r < matrix.length; r++) {
            for(int c = 0; c < matrix[r].length; c++) {
                Tile tile;
                if(r == matrix.length - 1) tile = new BottomTile(matrixTileImage);
                else tile = new Tile(matrixTileImage);
                tile.setFitHeight(tetrisWorld.getTileSize());
                tile.setFitWidth(tetrisWorld.getTileSize());
                tile.setX(c * tile.getWidth());
                tile.setY(r * tile.getHeight());
                tetrisWorld.add(tile);
                matrix[r][c] = tile;
            }
        }

        tetrisWorld.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.UP && !tetrisWorld.isKeyDown(KeyCode.UP)) tetrisWorld.setShouldRotate(true);
                tetrisWorld.addKeyCode(keyEvent.getCode());
            }
        });

        tetrisWorld.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                tetrisWorld.removeKeyCode(keyEvent.getCode());
            }
        });

        tetrisWorld.start();

        root.setCenter(tetrisWorld);
        stage.setScene(scene);
        stage.show();
        tetrisWorld.requestFocus();

        Tetrimino tetrimino = new LTetrimino(tetrisWorld.getTileSize());
        tetrimino.setY(0);
        tetrimino.setX(0);
        tetrisWorld.add(tetrimino);
        tetrimino.addTiles();
    }
}
