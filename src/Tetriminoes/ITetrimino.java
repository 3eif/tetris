package com.seifabdelaziz.tetris.Tetriminoes;

public class ITetrimino extends Tetrimino {
    private static final int width = 4;
    private static final int height = 1;
    private static final int[][] shape = {
            {0, 0, 0, 0},
            {1, 1, 1, 1},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
    };

    public ITetrimino(int tileSize) {
        super(width, height, tileSize, shape, "resources/images/cyan-tile.png");
    }
}
