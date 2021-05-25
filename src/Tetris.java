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
        stage.setResizable(false);
        BorderPane root = new BorderPane();

        GameManager gameManager = GameManager.getInstance();
        gameManager.setHighScore(100); // TODO: Change this when you implement storing high scores in txt file or local db
        gameManager.setRoot(root);
        Scene scene = new Scene(root, 900, 700);
        gameManager.setGame(scene);
        gameManager.setSoundtrack(new Soundtrack("./resources/tetris.mp3")); // TODO: Make looping sound better

        stage.setScene(scene);
        stage.show();
        Menu menu = new Menu();
        gameManager.setCurrentScene(menu);
        root.setCenter(menu);
        root.getCenter().requestFocus();
    }
}
