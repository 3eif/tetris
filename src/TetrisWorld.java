public class TetrisWorld extends World {
    private int tileSize;
    private boolean shouldRotate;

    public TetrisWorld(int tileSize) {
        this.tileSize = tileSize;
    }

    @Override
    public void act() {

    }

    public void spawnNextTetrimino() {
//        Class[] tetriminoShapes = {ITetrimino.class, JTetrimino.class, LTetrimino.class, OTetrimino.class,
//                TTetrimino.class, ZTetrminio.class};
//        Class tetriminoShape = tetriminoShapes[(int)(Math.random() * tetriminoShapes.length)];
        Tetrimino tetrimino = new LTetrimino(tileSize);
        tetrimino.setY(0);
        tetrimino.setX(0);
        add(tetrimino);
        tetrimino.addTiles();
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public boolean getShouldRotate() {
        return shouldRotate;
    }

    public void setShouldRotate(boolean shouldRotate) {
        this.shouldRotate = shouldRotate;
    }
}
