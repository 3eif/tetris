package com.seifabdelaziz.tetris.Tetriminoes;

public class LTetrimino extends Tetrimino {
    private static final int width = 3;
    private static final int height = 2;
    private static final int[][] shape = {
            {1, 1, 1},
            {0, 0, 1},
            {0, 0, 0},
    };

    public LTetrimino(int tileSize) {
        super(width, height, tileSize, shape, "./resources/purple-tile.png");
    }
}
