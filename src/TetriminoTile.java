import javafx.scene.image.Image;

public class TetriminoTile extends Tile {
    private Tetrimino parentTetrimino;

    public TetriminoTile(Tetrimino tetrimino, Image image) {
        super(image);
        parentTetrimino = tetrimino;
    }

    @Override
    public void act() {
        BottomTile bottomTile = getOneIntersectingObject(BottomTile.class);
        TetriminoTile tetriminoTile = getOneIntersectingObject(TetriminoTile.class);
        if((bottomTile != null || (tetriminoTile != null && tetriminoTile.getParentTetrimino() != parentTetrimino))
                && parentTetrimino.isMovable()) {
            TetrisWorld tetrisWorld = (TetrisWorld) getWorld();
            parentTetrimino.setIsMovable(false);
            tetrisWorld.spawnNextTetrimino();
        }
    }

    public Tetrimino getParentTetrimino() {
        return parentTetrimino;
    }

    public void setParentTetrimino(Tetrimino parentTetrimino) {
        this.parentTetrimino = parentTetrimino;
    }
}
