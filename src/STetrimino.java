public class STetrimino extends Tetrimino {
    private static final int[][] shape = {
            {0, 1, 1, 0},
            {1, 1, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    };

    public STetrimino(int tileSize) {
        super(tileSize, shape, "./resources/green-tile.png");
    }
}
