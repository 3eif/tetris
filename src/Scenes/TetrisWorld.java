package com.seifabdelaziz.tetris.Scenes;

import com.seifabdelaziz.tetris.Engine.Actor;
import com.seifabdelaziz.tetris.Engine.GameManager;
import com.seifabdelaziz.tetris.Engine.Score;
import com.seifabdelaziz.tetris.Engine.World;
import com.seifabdelaziz.tetris.Tetriminoes.*;
import com.seifabdelaziz.tetris.Tiles.Matrix;
import com.seifabdelaziz.tetris.Tiles.MatrixTile;
import com.seifabdelaziz.tetris.Tiles.TetriminoTile;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class TetrisWorld extends World {
    private boolean shouldRotate;
    private boolean shouldMoveToNextTile;
    private boolean shouldMoveDown;
    private boolean shouldMoveToBottom;
    private boolean shouldHold;
    private boolean canHold;

    private Matrix matrix;
    private Score score;
    private Score highscore;
    private Tetrimino holdTetrimino;
    private ArrayList<Tetrimino> nextTetriminos = new ArrayList<Tetrimino>(3);

    private final int NEXT_TETRIMINO_COUNT = 4;

    public TetrisWorld() {
        setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.UP && !isKeyDown(KeyCode.UP))
                setShouldRotate(true);
            if (keyEvent.getCode() == KeyCode.X && !isKeyDown(KeyCode.X))
                setShouldHold(true);
            if (keyEvent.getCode() == KeyCode.DOWN && !isKeyDown(KeyCode.DOWN))
                setShouldMoveDown(true);
            if (keyEvent.getCode() == KeyCode.SPACE && !isKeyDown(KeyCode.SPACE))
                setShouldMoveToBottom(true);
            if ((keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.RIGHT) &&
                    (!isKeyDown(KeyCode.RIGHT) || !isKeyDown(KeyCode.LEFT)))
                setShouldMoveToNextTile(true);
            addKeyCode(keyEvent.getCode());
        });
        setOnKeyReleased(keyEvent -> removeKeyCode(keyEvent.getCode()));

        GameManager gameManager = GameManager.getInstance();
        Scene scene = gameManager.getGame();

        holdTetrimino = null;

        Rectangle background = new Rectangle();
        background.setWidth(scene.getWidth());
        background.setHeight(scene.getHeight());

        Text hold = new Text("Hold");
        hold.setFont(new Font(30));
        hold.setX(25);
        hold.setY(150);
        getChildren().addAll(background, hold);

        matrix = new Matrix(this, 20, 10, TILE_SIZE, (int)(scene.getWidth() / 2) -
                (int)((getTileSize() * 10) / 2), (scene.getHeight() - getTileSize() * 20) / 2, MATRIX_TILE_IMAGE);

        Rectangle holdRectangle = new Rectangle();
        holdRectangle.setStyle("-fx-stroke: white;");
        holdRectangle.setStrokeWidth(1);
        holdRectangle.setWidth(TILE_SIZE * 5);
        holdRectangle.setHeight(TILE_SIZE * 4);
        holdRectangle.setX(matrix.getX() - TILE_SIZE * 5);
        holdRectangle.setY(matrix.getY());
        Rectangle holdTextRectangle = new Rectangle();
        holdTextRectangle.setFill(Color.WHITE);
        holdTextRectangle.setWidth(TILE_SIZE * 5);
        holdTextRectangle.setHeight(TILE_SIZE);
        holdTextRectangle.setX(matrix.getX() - TILE_SIZE * 5);
        holdTextRectangle.setY(matrix.getY());
        Text holdText = new Text("HOLD");
        holdText.setX(matrix.getX() - TILE_SIZE * 5 + 5);
        holdText.setY(matrix.getY() + 23);
        holdText.setFont(Font.loadFont("file:resources/fonts/Kenney Mini Square.ttf", 25));

        Rectangle nextRectangle = new Rectangle();
        nextRectangle.setStyle("-fx-stroke: white;");
        nextRectangle.setStrokeWidth(1);
        nextRectangle.setWidth(TILE_SIZE * 5);
        nextRectangle.setHeight(TILE_SIZE * 15);
        nextRectangle.setX(matrix.getX() + matrix.getWidth());
        nextRectangle.setY(matrix.getY());
        Rectangle nextTextRectangle = new Rectangle();
        nextTextRectangle.setFill(Color.WHITE);
        nextTextRectangle.setWidth(TILE_SIZE * 5);
        nextTextRectangle.setHeight(TILE_SIZE);
        nextTextRectangle.setX(matrix.getX() + matrix.getWidth());
        nextTextRectangle.setY(matrix.getY());
        Text nextText = new Text("NEXT");
        nextText.setX(matrix.getX() + matrix.getWidth() + 5);
        nextText.setY(matrix.getY() + 23);
        nextText.setFont(Font.loadFont("file:resources/fonts/Kenney Mini Square.ttf", 25));
        getChildren().addAll(holdRectangle, holdTextRectangle, holdText, nextRectangle, nextTextRectangle, nextText);

        for(int i = 0; i < NEXT_TETRIMINO_COUNT; i++) {
            Tetrimino tetrimino = (Tetrimino) getRandomTetriminoActor();
            tetrimino.setIsMovable(false);
            tetrimino.setNext(true);
            add(tetrimino);
            tetrimino.addTiles();
            nextTetriminos.add(i, tetrimino);
        }
        spawnTetrimino();

        highscore = new Score("Highscore: ");
        highscore.setX(matrix.getX() + matrix.getWidth() + TILE_SIZE / 2);
        highscore.setY(matrix.getY() + matrix.getHeight() - 50);
        highscore.setScoreVal(gameManager.getHighScore());
        score = new Score("Score: ");
        score.setX(matrix.getX() + matrix.getWidth() + TILE_SIZE / 2);
        score.setY(matrix.getY() + matrix.getHeight() - 10);
        getChildren().addAll(score, highscore);
    }

    @Override
    public void act(long now) {
        GameManager gameManager = GameManager.getInstance();

        if (isKeyDown(KeyCode.X) && shouldHold && canHold) {
            Tetrimino tetriminoToSpawn = holdTetrimino;

            ObservableList<Node> actors = getChildrenUnmodifiable();
            for(Node actor : actors) {
                if(actor instanceof Tetrimino) {
                    Tetrimino tetrimino = (Tetrimino) actor;
                    if (tetrimino.isMovable()) {
                        tetrimino.resetRotation();
                        tetrimino.setIsMovable(false);
                        tetrimino.setXPos(matrix.getX() - TILE_SIZE * 2.5 - tetrimino.getMaxWidth() / 2);
                        tetrimino.setYPos(matrix.getY() + TILE_SIZE * 0.5 + tetrimino.getMaxHeight() / 2);
                        holdTetrimino = tetrimino;
                        holdTetrimino.setBeingHeld(true);
                        break;
                    }
                }
            }

            if(tetriminoToSpawn != null) {
                int x = (int) (matrix.getX() + (matrix.getWidth() / 2) - (tetriminoToSpawn.getMaxWidth() / 2));
                x -= x % 30 - TILE_SIZE / 2;
                tetriminoToSpawn.setXPos(x);
                tetriminoToSpawn.setYPos(matrix.getY());
                tetriminoToSpawn.setIsMovable(true);
                tetriminoToSpawn.setBeingHeld(false);
            } else spawnTetrimino();
            shouldHold = false;
            canHold = false;
        }

        if (now - getPrev() > (1e6 * 500)) {
            MatrixTile[][] matrixTiles = matrix.getMatrixTiles();
            ArrayList<Double> rowYs = new ArrayList<Double>();
            for (int r = 0; r < matrixTiles.length; r++) {
                // First check if a row is filled with tetrimino tiles
                boolean isColFilled = true;
                for (int c = 0; c < matrixTiles[r].length; c++) {
                    MatrixTile tile = matrixTiles[r][c];
                    if (tile.getTetrminoTileAbove() == null || tile.getTetrminoTileAbove().getParentTetrimino().isMovable()) {
                        isColFilled = false;
                        break;
                    }
                }

                // If it is filled then remove the row
                if (isColFilled) {
                    for (int c = 0; c < matrixTiles[r].length; c++) {
                        MatrixTile tile = matrixTiles[r][c];
                        getChildren().remove(tile.getTetrminoTileAbove());
                    }
                    rowYs.add(matrixTiles[r][0].getY());
                }
            }

            // Drop the rows down if any row has been removed from the bottom
            if (rowYs.size() > 0) {
                ObservableList<Node> actors = getChildrenUnmodifiable();
                for (Node actor : actors) {
                    if (actor instanceof TetriminoTile) {
                        TetriminoTile tetriminoTile = (TetriminoTile) actor;
                        Tetrimino tetrimino = tetriminoTile.getParentTetrimino();
                        if (!tetrimino.isMovable() && tetriminoTile.getY() < rowYs.get(rowYs.size() - 1) - (int) (TILE_SIZE / 2)
                                && !tetrimino.isBeingHeld() && !tetrimino.isNext()) {
                            tetriminoTile.setY(tetriminoTile.getY() + TILE_SIZE * rowYs.size());
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
            if (scoreToAdd > 0) score.setScoreVal(score.getScoreVal() + scoreToAdd);
            if(score.getScoreVal() > gameManager.getHighScore()) {
                gameManager.setHighScore(score.getScoreVal());
                highscore.setScoreVal(gameManager.getHighScore());
            }
        }
    }

    public Tetrimino spawnTetrimino() {
        removeKeyCode(KeyCode.DOWN);
        if(getMovableTetrimino() != null) return null;
        Tetrimino tetriminoToSpawn = nextTetriminos.remove(0);
        canHold = true;

        int x = (int) (matrix.getX() + (matrix.getWidth() / 2) - (tetriminoToSpawn.getMaxWidth() / 2));
        x -= x % 30 - TILE_SIZE / 2;

        tetriminoToSpawn.setIsMovable(true);
        tetriminoToSpawn.setYPos(matrix.getY() - tetriminoToSpawn.getMaxHeight());
        tetriminoToSpawn.setNext(false);
        tetriminoToSpawn.setXPos(x);

        Tetrimino tetriminoToAdd = (Tetrimino) getRandomTetriminoActor();
        tetriminoToAdd.setIsMovable(false);
        add(tetriminoToAdd);
        tetriminoToAdd.addTiles();
        nextTetriminos.add(tetriminoToAdd);

        for(int i = 0; i < nextTetriminos.size(); i++) {
            nextTetriminos.get(i).setNext(true);
            nextTetriminos.get(i).setXPos(matrix.getX() + matrix.getWidth() + TILE_SIZE * 2.5
                    - nextTetriminos.get(i).getMaxWidth() / 2);
            nextTetriminos.get(i).setYPos(matrix.getY() + TILE_SIZE * 1.5 + TILE_SIZE * (3.5 * i));
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
            actor = (Actor) tetriminoShape.getDeclaredConstructor(int.class).newInstance(TILE_SIZE);
            return actor;
        } catch(NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.out.println(e);
            return null;
        }
    }

    public int getTileSize() {
        return TILE_SIZE;
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

    public boolean getShouldMoveDown() {
        return shouldMoveDown;
    }

    public void setShouldMoveDown(boolean shouldMoveDown) {
        this.shouldMoveDown = shouldMoveDown;
    }

    public boolean getShouldMoveToBottom() {
        return shouldMoveToBottom;
    }

    public void setShouldMoveToBottom(boolean shouldMoveToBottom) {
        this.shouldMoveToBottom = shouldMoveToBottom;
    }
}
