import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class TetrisWorld extends World {
    private int tileSize;

    private boolean shouldRotate;
    private boolean shouldMoveToNextTile;
    private boolean shouldHold;
    private boolean canHold;

    private Matrix matrix;
    private Score score;
    private Tetrimino holdTetrimino;
    private ArrayList<Tetrimino> nextTetriminos = new ArrayList<Tetrimino>(3);

    public TetrisWorld(int tileSize, Image matrixTileImage, double sceneWidth, double sceneHeight) {
        this.tileSize = tileSize;
        holdTetrimino = null;

        Text hold = new Text("Hold");
        hold.setFont(new Font(30));
        hold.setX(25);
        hold.setY(150);

        matrix = new Matrix(20, 10, tileSize, (int)(sceneWidth / 2) - (int)((getTileSize() * 10) / 2), 0, matrixTileImage);
        matrix.addMatrixToWorld(this);

        score = new Score();
        score.setX(25);
        score.setY(25);
        getChildren().addAll(score, hold);

        for(int i = 0; i < 3; i++) {
            Tetrimino tetrimino = (Tetrimino) getRandomTetriminoActor();
            tetrimino.setX(sceneWidth - 100);
            tetrimino.setY(i * 200 + 10);
            tetrimino.setIsMovable(false);
            add(tetrimino);
            tetrimino.addTiles();
            nextTetriminos.add(i, tetrimino);
        }
        spawnTetrimino();
    }

    @Override
    public void act() {
        if (isKeyDown(KeyCode.C) && shouldHold && canHold) {
            Tetrimino tetriminoToSpawn = holdTetrimino;

            ObservableList<Node> actors = getChildrenUnmodifiable();
            for(Node actor : actors) {
                if(actor instanceof Tetrimino) {
                    Tetrimino tetrimino = (Tetrimino) actor;
                    if (tetrimino.isMovable()) {
                        tetrimino.setIsMovable(false);
                        tetrimino.setYPos(50);
                        tetrimino.setXPos(20);
                        holdTetrimino = tetrimino;
                        break;
                    }
                }
            }

            if(tetriminoToSpawn != null) {
                int x = (int)(matrix.getWidth() / 2) - (tetriminoToSpawn.getMaxWidth() / 2);
                if((x / 2) % 10 != 0) x -= tileSize / 2;
                tetriminoToSpawn.setXPos(x + matrix.getX());
                tetriminoToSpawn.setYPos(matrix.getY());
                tetriminoToSpawn.setIsMovable(true);
            } else spawnTetrimino();
            shouldHold = false;
            canHold = false;
        }
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

    public Tetrimino spawnTetrimino() {
        if(getMovableTetrimino() != null) return null;
        Tetrimino tetriminoToSpawn = nextTetriminos.remove(0);
        canHold = true;

        int x = (int)(matrix.getWidth() / 2) - (tetriminoToSpawn.getMaxWidth() / 2);
        if((x / 2) % 10 != 0) x -= tileSize / 2;

        tetriminoToSpawn.setIsMovable(true);
        tetriminoToSpawn.setYPos(matrix.getY());
        tetriminoToSpawn.setXPos(x + matrix.getX());

        Tetrimino tetriminoToAdd = (Tetrimino) getRandomTetriminoActor();
        tetriminoToAdd.setIsMovable(false);
        add(tetriminoToAdd);
        tetriminoToAdd.addTiles();
        nextTetriminos.add(tetriminoToAdd);

        for(int i = 0; i < nextTetriminos.size(); i++) {
            nextTetriminos.get(i).setXPos(700);
            nextTetriminos.get(i).setYPos(i * 200 + 20);
        }
        return tetriminoToSpawn;
    }

    public Tetrimino getMovableTetrimino() {
        ObservableList<Node> actors = getChildrenUnmodifiable();
        for(Node actor : actors) {
            if(actor instanceof Tetrimino) {
                Tetrimino tetrimino = (Tetrimino) actor;
                if (tetrimino.isMovable()) {
                    return tetrimino;
                }
            }
        }
        return null;
    }

    public Actor getRandomTetriminoActor() {
        Class[] tetriminoShapes = {ITetrimino.class, JTetrimino.class, LTetrimino.class, OTetrimino.class, STetrimino.class,
                TTetrimino.class, ZTetrminio.class};
        Class tetriminoShape = tetriminoShapes[(int)(Math.random() * tetriminoShapes.length)];

        Actor actor = null;
        try {
            actor = (Actor) tetriminoShape.getDeclaredConstructor(int.class).newInstance(tileSize);
            return actor;
        } catch(NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.out.println(e);
            return null;
        }
    }

    public int getTileSize() {
        return tileSize;
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

    public void setShouldHold(boolean shouldHold) {
        this.shouldHold = shouldHold;
    }
}
