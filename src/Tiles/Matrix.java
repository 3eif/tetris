package com.seifabdelaziz.tetris.Tiles;

import com.seifabdelaziz.tetris.Engine.World;
import com.seifabdelaziz.tetris.Tiles.BottomTile;
import com.seifabdelaziz.tetris.Tiles.MatrixTile;
import javafx.scene.image.Image;

public class Matrix {
    private MatrixTile[][] matrixTiles;
    private int rows;
    private int cols;
    private double x;
    private double y;
    private int tileSize;

    public Matrix(World world, int rows, int cols, int tileSize, double x, double y, Image matrixTileImage) {
        this.rows = rows;
        this.cols = cols;
        this.tileSize = tileSize;
        this.x = x;
        this.y = y;

        this.matrixTiles = new MatrixTile[rows][cols];
        for(int r = 0; r < matrixTiles.length; r++) {
            for(int c = 0; c < matrixTiles[r].length; c++) {
                MatrixTile tile;
                if(r == matrixTiles.length - 1) tile = new BottomTile(matrixTileImage);
                else tile = new MatrixTile(matrixTileImage);
                tile.setFitHeight(getTileSize());
                tile.setFitWidth(getTileSize());
                tile.setX(x + c * tile.getWidth());
                tile.setY(y + r * tile.getHeight());
                matrixTiles[r][c] = tile;
            }
        }

        for (MatrixTile[] matrixTile : matrixTiles) {
            for (MatrixTile tile : matrixTile) {
                world.add(tile);
            }
        }
    }

    public MatrixTile[][] getMatrixTiles() {
        return matrixTiles;
    }

    public void setMatrixTiles(MatrixTile[][] matrixTiles) {
        this.matrixTiles = matrixTiles;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getTileSize() {
        return tileSize;
    }

    public double getWidth() {
        return tileSize * cols;
    }

    public double getHeight() {
        return tileSize * rows;
    }
}
