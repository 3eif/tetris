public class OTetrimino extends Tetrimino {
    private static final int[][] shape = {
            {1, 1, 0},
            {1, 1, 0},
            {0, 0, 0},
    };

    public OTetrimino(int tileSize) {
        super(tileSize, shape, "./resources/yellow-tile.png");
    }
}
