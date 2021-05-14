public class TTetrimino extends Tetrimino {
    private static final int[][] shape = {
            {0, 1, 0},
            {1, 1, 1},
            {0, 0, 0},
    };

    public TTetrimino(int tileSize) {
        super(tileSize, shape, "./resources/orange-tile.png");
    }
}
