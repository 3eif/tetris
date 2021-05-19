import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class TetrisWorld extends World {
    private int tileSize;

    private boolean shouldRotate;
    private boolean shouldMoveToNextTile;

    private Matrix matrix;
    private Score score;

    public TetrisWorld(int tileSize, Image matrixTileImage, double sceneWidth, double sceneHeight) {
        this.tileSize = tileSize;

        matrix = new Matrix(21, 10, tileSize, (int)(sceneWidth / 2) - (int)((getTileSize() * 10) / 2), 0, matrixTileImage);
        matrix.addMatrixToWorld(this);

        score = new Score();
        score.setX(25);
        score.setY(25);
        getChildren().add(score);
    }

    @Override
    public void act() {

    }

    public void delayedAct() {
        MatrixTile[][] matrixTiles = matrix.getMatrixTiles();
        ArrayList<Double> rowYs = new ArrayList<Double>();
        for(int r = 0; r < matrixTiles.length; r++) {
            // First check if a row is filled with tetrimino tiles
            boolean isColFilled = true;
            for(int c = 0; c < matrixTiles[r].length; c++) {
                MatrixTile tile = matrixTiles[r][c];
                if(tile.getTetrminoTileAbove() == null || tile.getTetrminoTileAbove().getParentTetrimino().isMovable()) {
                    isColFilled = false;
                    break;
                }
            }

            // If it is filled then remove the row
            if(isColFilled) {
                for(int c = 0; c < matrixTiles[r].length; c++) {
                    MatrixTile tile = matrixTiles[r][c];
                    getChildren().remove(tile.getTetrminoTileAbove());
                }
                rowYs.add(matrixTiles[r][0].getY());
            }
        }

        // Drop the rows down if any row has been removed from the bottom
        if(rowYs.size() > 0) {
            ObservableList<Node> actors = getChildrenUnmodifiable();
            for(Node actor : actors) {
                if(actor instanceof TetriminoTile) {
                    TetriminoTile tetriminoTile = (TetriminoTile) actor;
                    Tetrimino tetrimino = tetriminoTile.getParentTetrimino();
                    if (!tetrimino.isMovable() && tetriminoTile.getY() < rowYs.get(rowYs.size() - 1) - (int)(tileSize / 2)) {
                        tetriminoTile.setY(tetriminoTile.getY() + tileSize * rowYs.size());
                    }
                }
            }
        }

        int scoreToAdd = 0;
        switch (rowYs.size()) {
            case 1:
                scoreToAdd = 100;
                break;
            case 2:
                scoreToAdd = 300;
                break;
            case 3:
                scoreToAdd = 500;
                break;
            case 4:
                scoreToAdd = 800;
        }
        if(scoreToAdd > 0) score.setScoreVal(score.getScoreVal() + scoreToAdd);
    }

    public void spawnTetrimino() {
        Class[] tetriminoShapes = {ITetrimino.class, JTetrimino.class, LTetrimino.class, OTetrimino.class, STetrimino.class,
                TTetrimino.class, ZTetrminio.class};
        Class tetriminoShape = tetriminoShapes[(int)(Math.random() * tetriminoShapes.length)];

        Actor actor = null;

        try {
            actor = (Actor) tetriminoShape.getDeclaredConstructor(int.class).newInstance(tileSize);
            Tetrimino tetrimino = (Tetrimino) actor;
            actor.setY(matrix.getY());
            int x = (int)(matrix.getWidth() / 2) - (tetrimino.getMaxWidth() / 2);
            if((x / 2) % 10 != 0) x -= tileSize / 2;
            actor.setX(x + matrix.getX());
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

    public Matrix getMatrix() {
        return matrix;
    }

    public Score getScore() {
        return score;
    }
}
