public class ITetrimino extends Tetrimino {
    private static final int[][] shape = {
            {1, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 0, 0, 0}
    };

    public ITetrimino(int tileSize) {
        super(tileSize, shape, "./resources/turquoise-tile.png");
    }
}
