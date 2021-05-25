package com.seifabdelaziz.tetris.Tetriminoes;

public class ZTetrminio extends Tetrimino {
    private static final int width = 3;
    private static final int height = 2;
    private static final int[][] shape = {
            {1, 1, 0},
            {0, 1, 1},
            {0, 0, 0},
    };

    public ZTetrminio(int tileSize) {
        super(width, height, tileSize, shape, "./resources/red-tile.png");
    }
}
