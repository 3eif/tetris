package com.seifabdelaziz.tetris.Tetriminoes;

public class OTetrimino extends Tetrimino {
    private static final int width = 2;
    private static final int height = 2;
    private static final int[][] shape = {
            {1, 1},
            {1, 1},
    };

    public OTetrimino(int tileSize) {
        super(width, height, tileSize, shape, "resources/images/yellow-tile.png");
    }
}
