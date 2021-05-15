import java.lang.reflect.InvocationTargetException;

public class TetrisWorld extends World {
    private int tileSize;
    private boolean shouldRotate;
    private boolean shouldMoveToNextTile;

    public TetrisWorld(int tileSize) {
        this.tileSize = tileSize;
    }

    @Override
    public void act() {

    }

    public void spawnTetrimino() {
        Class[] tetriminoShapes = {ITetrimino.class, JTetrimino.class, LTetrimino.class, OTetrimino.class,
                TTetrimino.class, ZTetrminio.class};
        Class tetriminoShape = tetriminoShapes[(int)(Math.random() * tetriminoShapes.length)];

        Actor actor = null;

        try {
            actor = (Actor) tetriminoShape.getDeclaredConstructor(int.class).newInstance(tileSize);
            actor.setY(0);
            actor.setX(0);
            add(actor);
            Tetrimino tetrimino = (Tetrimino) actor;
            tetrimino.addTiles();
        } catch(NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.out.println(e);
        }
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

    public boolean getShouldMoveToNextTile() {
        return shouldMoveToNextTile;
    }

    public void setShouldMoveToNextTile(boolean shouldMove) {
        this.shouldMoveToNextTile = shouldMove;
    }
}
