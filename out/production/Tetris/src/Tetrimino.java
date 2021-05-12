import javafx.scene.image.Image;

public class Tetrimino extends Actor {
    public Tetrimino(Image image) {
        setImage(image);
    }

    @Override
    public void act() {
        TetrisWorld world = (TetrisWorld) getWorld();
        setY(getY() + 1);
    }
}
