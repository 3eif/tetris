public class LTetrimino extends Tetrimino {
    private static final int[][] shape = {
            {1, 1, 1},
            {0, 0, 1},
            {0, 0, 0},
    };

    public LTetrimino(int tileSize) {
        super(tileSize, shape, "./resources/purple-tile.png");
    }
}
