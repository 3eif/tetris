import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;

public class TetriminoTile extends Tile {
    private Tetrimino parentTetrimino;

    public TetriminoTile(Tetrimino tetrimino, Image image) {
        super(image);
        parentTetrimino = tetrimino;
    }

    @Override
    public <A extends Actor> A getOneIntersectingObject(java.lang.Class<A> cls) {
        A intersectingObject = null;
        ObservableList<Node> actors = getParent().getChildrenUnmodifiable();
        for(int i = 0; i < actors.size(); i++) {
            if(actors.get(i) != this && cls.isInstance(actors.get(i)) && (actors.get(i).contains(getX() + (getWidth() / 2), getY()) ||
                    actors.get(i).contains(getX() + (getWidth() / 2), getY() + getHeight()))) {
                intersectingObject = (A) actors.get(i);
                break;
            }
        }
        return intersectingObject;
    }

    @Override
    public void act() {
        BottomTile bottomTile = getOneIntersectingObject(BottomTile.class);
        TetriminoTile tetriminoTile = getOneIntersectingObject(TetriminoTile.class);
        if((bottomTile != null || (tetriminoTile != null && tetriminoTile.getParentTetrimino() != parentTetrimino))
                && parentTetrimino.isMovable()) {
            TetrisWorld tetrisWorld = (TetrisWorld) getWorld();
            parentTetrimino.setIsMovable(false);
            tetrisWorld.spawnTetrimino();
        }


    }

    public Tetrimino getParentTetrimino() {
        return parentTetrimino;
    }

    public void setParentTetrimino(Tetrimino parentTetrimino) {
        this.parentTetrimino = parentTetrimino;
    }
}
