package com.seifabdelaziz.tetris.Tetriminoes;

public class TTetrimino extends Tetrimino {
    private static final int width = 3;
    private static final int height = 2;
    private static final int[][] shape = {
            {0, 1, 0},
            {1, 1, 1},
            {0, 0, 0},
    };

    public TTetrimino(int tileSize) {
        super(width, height, tileSize, shape, "resources/images/purple-tile.png");
    }
}
