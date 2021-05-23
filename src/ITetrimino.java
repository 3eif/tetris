public class ITetrimino extends Tetrimino {
    private static final int width = 1;
    private static final int height = 4;
    private static final int[][] shape = {
            {0, 0, 1, 0},
            {0, 0, 1, 0},
            {0, 0, 1, 0},
            {0, 0, 1, 0},
    };

    public ITetrimino(int tileSize) {
        super(width, height, tileSize, shape, "./resources/turquoise-tile.png");
    }
}
