package com.seifabdelaziz.tetris.Tiles;

import com.seifabdelaziz.tetris.Engine.World;
import javafx.scene.image.Image;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class Matrix {
    private int rows;
    private int cols;
    private double x;
    private double y;
    private int tileSize;

    private MatrixTile[][] matrixTiles;
    private Line leftLine;
    private Line rightLine;
    private Line bottomLine;

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

        ArrayList<MatrixTile> matrixTilesToAdd = new ArrayList<>();
        for (MatrixTile[] matrixTile : matrixTiles) {
            for (MatrixTile tile : matrixTile) {
                matrixTilesToAdd.add(tile);
            }
        }
        world.getChildren().addAll(matrixTilesToAdd);

        leftLine = new Line();
        leftLine.setStartX(x);
        leftLine.setEndX(x);
        leftLine.setStartY(y);
        leftLine.setEndY(y + rows * tileSize);
        leftLine.setStyle("-fx-stroke: white;");
        rightLine = new Line();
        rightLine.setStartX(x + cols * tileSize);
        rightLine.setEndX(x + cols * tileSize);
        rightLine.setStartY(y);
        rightLine.setEndY(y + rows * tileSize);
        rightLine.setStyle("-fx-stroke: white;");
        bottomLine = new Line();
        bottomLine.setStartX(x);
        bottomLine.setEndX(x + cols * tileSize);
        bottomLine.setStartY(y + rows * tileSize);
        bottomLine.setEndY(y + rows * tileSize);
        bottomLine.setStyle("-fx-stroke: white;");
        world.getChildren().addAll(leftLine, rightLine, bottomLine);
    }

    public MatrixTile[][] getMatrixTiles() {
        return matrixTiles;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
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
