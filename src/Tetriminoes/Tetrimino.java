package com.seifabdelaziz.tetris.Tetriminoes;

import com.seifabdelaziz.tetris.Engine.Actor;
import com.seifabdelaziz.tetris.Tiles.*;
import com.seifabdelaziz.tetris.Scenes.TetrisWorld;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public abstract class Tetrimino extends Actor {
    private boolean isMovable = true;
    private boolean isBeingHeld = false;
    private boolean isNext = false;

    private TetriminoTile[][] tiles;
    private final int[][] shape;
    private final Image image;

    private double maxWidth;
    private double maxHeight;
    private final int tileSize;

    private double distanceToMove;
    private double distanceToMoveDown;
    private static final double MOVE_SENSITIVITY = 0.1;

    public Tetrimino(double width, double height, int tileSize, int[][] shape, String tileImage) {
        String tetriminoImagePath = getClass().getClassLoader().getResource(tileImage).toString();

        this.maxWidth = width * tileSize;
        this.maxHeight = height * tileSize;
        this.image = new Image(tetriminoImagePath);
        this.shape = shape;
        this.tileSize = tileSize;

        tiles = new TetriminoTile[shape.length][shape[0].length];
    }

    public void addTiles() {
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c] == 1) {
                    TetriminoTile tile = new TetriminoTile(this, image);
                    tile.setFitHeight(tileSize);
                    tile.setFitWidth(tileSize);
                    tile.setX(getX() + c * tileSize);
                    tile.setY(getY() + r * tileSize);
                    tiles[r][c] = tile;
                    getWorld().add(tile);
                } else {
                    tiles[r][c] = null;
//                    String tetriminoImagePath = getClass().getClassLoader().getResource("./resources/white-tile.png").toString();
//                    TetriminoTile tile = new TetriminoTile(this, new Image(tetriminoImagePath));
//                    tile.setFitHeight(tileSize);
//                    tile.setFitWidth(tileSize);
//                    tile.setX(getX() + c * tileSize);
//                    tile.setY(getY() + r * tileSize);
//                    tiles[r][c] = tile;
//                    getWorld().add(tile);
                }
            }
        }
    }

    public void removeTiles() {
        for(int r = 0; r < tiles.length; r++) {
            for(int c = 0; c < tiles[r].length; c++) {
                if(tiles[r][c] != null) {
                    getWorld().remove(tiles[r][c]);
                }
            }
        }
    }

    @Override
    public void act(long now) {
        if(!isMovable) return;
        TetrisWorld tetrisWorld = (TetrisWorld) getWorld();
        Matrix matrix = tetrisWorld.getMatrix();

        double xOfFirstTile = Integer.MAX_VALUE;
        double yOfFirstTile = Integer.MAX_VALUE;
        for (TetriminoTile[] tetriminoTiles : tiles) {
            for (Tile tile : tetriminoTiles) {
                if (tile != null) {
                    if (tile.getX() < xOfFirstTile) xOfFirstTile = tile.getX();
                    if (tile.getY() < yOfFirstTile) yOfFirstTile = tile.getY();
                }
            }
        }

        // Controls how fast you can move your tetrimino.
        boolean shouldMoveToNextTile = tetrisWorld.getShouldMoveToNextTile();
        if (getWorld().isKeyDown(KeyCode.RIGHT) && xOfFirstTile + maxWidth < matrix.getWidth() + matrix.getX()) {
            distanceToMove = shouldMoveToNextTile ? tileSize : distanceToMove + MOVE_SENSITIVITY;
            if (distanceToMove == tileSize) {
                moveHorizontal(distanceToMove);
                distanceToMove = 0;
            }
        }
        if (getWorld().isKeyDown(KeyCode.LEFT) && xOfFirstTile - tileSize >= matrix.getX()) {
            distanceToMove = shouldMoveToNextTile ? -tileSize : distanceToMove - MOVE_SENSITIVITY;
            if (distanceToMove == -tileSize) {
                moveHorizontal(distanceToMove);
                distanceToMove = 0;
            }
        }

        boolean shouldMoveDown = tetrisWorld.getShouldMoveDown();
        if (getWorld().isKeyDown(KeyCode.DOWN) && getY() + maxHeight < matrix.getHeight() + matrix.getY()) {
            distanceToMoveDown = shouldMoveDown ? -tileSize : distanceToMoveDown - (int)(tileSize / 3);
            if(distanceToMoveDown <= -tileSize) {
                moveVertical(tileSize);
                distanceToMoveDown = 0;
            }
        }

        if (!getWorld().isKeyDown(KeyCode.LEFT) && !getWorld().isKeyDown(KeyCode.RIGHT)) {
            distanceToMove = 0;
            tetrisWorld.setShouldMoveToNextTile(false);
        }
        if(!getWorld().isKeyDown(KeyCode.DOWN)) {
            distanceToMoveDown = 0;
            tetrisWorld.setShouldMoveDown(false);
        }
        if (shouldMoveDown) tetrisWorld.setShouldMoveDown(false);
        if (shouldMoveToNextTile) tetrisWorld.setShouldMoveToNextTile(false);

//        if (tetrisWorld.getShouldMoveToBottom()) {
//            TetriminoTile tetriminoTile = getOneIntersectingObject(TetriminoTile.class);
//            while((tetriminoTile == null || tetriminoTile.getParentTetrimino() == this) && isMovable && getY() +
//                    maxHeight < matrix.getHeight() + matrix.getY()) {
//                if((getOneIntersectingObject(TetriminoTile.class) != null &&
//                        getOneIntersectingObject(TetriminoTile.class).getParentTetrimino() != this) ||
//                        getOneIntersectingObject(BottomTile.class) != null ||
//                        getOneIntersectingObject(MatrixTile.class) == null) {
//                    isMovable = false;
//                    tetrisWorld.spawnTetrimino();
//                    break;
//                }
//                moveVertical(tileSize);
//            }
//            tetrisWorld.setShouldMoveToBottom(false);
//        }

        if (tetrisWorld.getShouldRotate()) {
            rotate();
            tetrisWorld.setShouldRotate(false);
        }

        if (now - getWorld().getPrev() > (1e6 * 500)) {
            if (getY() + getFitHeight() < getWorld().getHeight()) {
                moveVertical(tileSize);
            }
        }
    }

    public void rotate() {
        TetrisWorld tetrisWorld = (TetrisWorld) getWorld();

        TetriminoTile[][] newTiles = new TetriminoTile[tiles[0].length][tiles.length];
        for (int r = 0; r < tiles.length; ++r) {
            for (int c = 0; c < tiles[r].length; ++c) {
                if (tiles[tiles[r].length - c - 1][r] != null) {
                    TetriminoTile tile = tiles[tiles[r].length - c - 1][r];
                    newTiles[r][c] = tile;
                }
            }
        }

        tiles = newTiles;
        for (int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles[r].length; c++) {
                TetriminoTile tile = tiles[r][c];
                if (tile != null) {
                    tile.setX(getX() + c * tileSize);
                    tile.setY(getY() + r * tileSize);
                }
            }
        }

        double temp = maxHeight;
        maxHeight = maxWidth;
        maxWidth = temp;

        // Prevents block from going out of matrix when rotating
        Matrix matrix = tetrisWorld.getMatrix();
        for (TetriminoTile[] tetriminoTiles : tiles) {
            for (Tile tile : tetriminoTiles) {
                if (tile != null) {
                    while (tile.getX() < matrix.getX()) moveHorizontal(tileSize);
                    while (tile.getX() + tileSize > matrix.getX() + matrix.getWidth()) moveHorizontal(-tileSize);
                }
            }
        }
    }

    public void resetRotation() {
        while(true) {
            boolean isSame = true;
            for (int r = 0; r < tiles.length; r++) {
                for (int c = 0; c < tiles[r].length; c++) {
                    if (!(shape[r][c] == 1 && tiles[r][c] != null) && !(shape[r][c] == 0 && tiles[r][c] == null)) {
                        isSame = false;
                        break;
                    }
                }
                if(!isSame) break;
            }
            if(isSame) break;
            else rotate();
        }
    }

    public void setXPos(double v) {
        setX(v);
        for (int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles[r].length; c++) {
                TetriminoTile tile = tiles[r][c];
                if (tile != null) {
                    tile.setX(v + c * tile.getWidth());
                }
            }
        }
    }

    public void setYPos(double v) {
        setY(v);
        for (int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles[r].length; c++) {
                TetriminoTile tile = tiles[r][c];
                if (tile != null) {
                    tile.setY(v + r * tile.getHeight());
                }
            }
        }
    }

    public void moveVertical(double v) {
        setY(getY() + v);
        for (int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles[r].length; c++) {
                TetriminoTile tile = tiles[r][c];
                if (tile != null) {
                    tile.setY(tile.getY() + v);
                }
            }
        }
    }

    public void moveHorizontal(double v) {
        setX(getX() + v);
        for (int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles[r].length; c++) {
                Tile tile = tiles[r][c];
                if (tile != null) tile.setX(tile.getX() + v);
            }
        }
    }

    public boolean isMovable() {
        return isMovable;
    }

    public void setIsMovable(boolean isMovable) {
        this.isMovable = isMovable;
    }

    public double getMaxWidth() {
        return maxWidth;
    }

    public double getMaxHeight() {
        return maxHeight;
    }

    public void printTetrimino(TetriminoTile[][] tiles) {
        for (TetriminoTile[] tetriminoTiles : tiles) {
            for (TetriminoTile tetriminoTile : tetriminoTiles) {
                if (tetriminoTile != null) System.out.print("▉");
                else System.out.print("░");
            }
            System.out.println();
        }
    }

    public boolean isBeingHeld() {
        return isBeingHeld;
    }

    public void setBeingHeld(boolean beingHeld) {
        isBeingHeld = beingHeld;
    }

    public boolean isNext() {
        return isNext;
    }

    public void setNext(boolean next) {
        isNext = next;
    }
}
