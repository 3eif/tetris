import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Tetris extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Tetris");
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 500, 700);

        TetrisWorld tetrisWorld = new TetrisWorld();
        tetrisWorld.setMinHeight(scene.getHeight());
        tetrisWorld.setMinWidth(scene.getWidth());
        root.setCenter(tetrisWorld);

        stage.setScene(scene);
        stage.show();
        tetrisWorld.requestFocus();
    }
}
