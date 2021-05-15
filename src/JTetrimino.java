public class JTetrimino extends Tetrimino {
    private static final int[][] shape = {
            {1, 0, 0, 0},
            {1, 1, 1, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    };

    public JTetrimino(int tileSize) {
        super(tileSize, shape, "./resources/blue-tile.png");
    }
}
