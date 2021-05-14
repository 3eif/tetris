public class ZTetrminio extends Tetrimino {
    private static final int[][] shape = {
            {1, 1, 0},
            {0, 1, 1},
            {0, 0, 0},
    };

    public ZTetrminio(int tileSize) {
        super(tileSize, shape, "./resources/red-tile.png");
    }
}
