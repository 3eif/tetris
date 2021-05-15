import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class TetrisWorld extends World {
    private int tileSize;

    private boolean shouldRotate;
    private boolean shouldMoveToNextTile;

    private MatrixTile[][] matrix;

    public TetrisWorld(int tileSize, Image matrixTileImage) {
        this.tileSize = tileSize;

        matrix = new MatrixTile[21][10];
        for(int r = 0; r < matrix.length; r++) {
            for(int c = 0; c < matrix[r].length; c++) {
                MatrixTile tile;
                if(r == matrix.length - 1) tile = new BottomTile(matrixTileImage);
                else tile = new MatrixTile(matrixTileImage);
                tile.setFitHeight(getTileSize());
                tile.setFitWidth(getTileSize());
                tile.setX(c * tile.getWidth());
                tile.setY(r * tile.getHeight());
                add(tile);
                matrix[r][c] = tile;
            }
        }
    }

    @Override
    public void act() {

    }

    public void delayedAct() {
        ArrayList<Double> colYs = new ArrayList<Double>();
        for(int r = 0; r < matrix.length; r++) {
            // First check if a column is filled with tetrimino tiles
            boolean isColFilled = true;
            for(int c = 0; c < matrix[r].length; c++) {
                MatrixTile tile = matrix[r][c];
                if(tile.getTetrminoTileAbove() == null || tile.getTetrminoTileAbove().getParentTetrimino().isMovable()) {
                    isColFilled = false;
                    break;
                }
            }

            // If it is filled then remove the column
            if(isColFilled) {
                for(int c = 0; c < matrix[r].length; c++) {
                    MatrixTile tile = matrix[r][c];
                    getChildren().remove(tile.getTetrminoTileAbove());
                }
                colYs.add(matrix[r][0].getY());
            }
        }

        // Drop the columns down if any column has been removed from the bottom
        if(colYs.size() > 0) {
            ObservableList<Node> actors = getChildrenUnmodifiable();
            for(Node actor : actors) {
                if(actor instanceof TetriminoTile) {
                    TetriminoTile tetriminoTile = (TetriminoTile) actor;
                    Tetrimino tetrimino = tetriminoTile.getParentTetrimino();
                    if (!tetrimino.isMovable() && tetriminoTile.getY() < colYs.get(colYs.size() - 1) - (int)(tileSize / 2)) {
                        tetriminoTile.setY(tetriminoTile.getY() + tileSize * colYs.size());
                    }
                }
            }
        }
    }

    public void spawnTetrimino() {
        Class[] tetriminoShapes = {ITetrimino.class, JTetrimino.class, LTetrimino.class, OTetrimino.class, STetrimino.class,
                TTetrimino.class, ZTetrminio.class};
        Class tetriminoShape = tetriminoShapes[(int)(Math.random() * tetriminoShapes.length)];

        Actor actor = null;

        try {
            actor = (Actor) tetriminoShape.getDeclaredConstructor(int.class).newInstance(tileSize);
            Tetrimino tetrimino = (Tetrimino) actor;
            actor.setY(0);
            int x = (int)(getWidth() / 2) - (tetrimino.getMaxWidth() / 2);
            if((x / 2) % 10 != 0) x -= tileSize / 2;
            actor.setX(x);
            add(actor);
            tetrimino.addTiles();
        } catch(NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.out.println(e);
        }
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public boolean getShouldRotate() {
        return shouldRotate;
    }

    public void setShouldRotate(boolean shouldRotate) {
        this.shouldRotate = shouldRotate;
    }

    public boolean getShouldMoveToNextTile() {
        return shouldMoveToNextTile;
    }

    public void setShouldMoveToNextTile(boolean shouldMove) {
        this.shouldMoveToNextTile = shouldMove;
    }

    public MatrixTile[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(MatrixTile[][] matrix) {
        this.matrix = matrix;
    }
}
