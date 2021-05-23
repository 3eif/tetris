public class STetrimino extends Tetrimino {
    private static final int width = 3;
    private static final int height = 2;
    private static final int[][] shape = {
            {0, 1, 1},
            {1, 1, 0},
            {0, 0, 0},
    };

    public STetrimino(int tileSize) {
        super(width, height, tileSize, shape, "./resources/green-tile.png");
    }
}
