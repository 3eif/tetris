import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public abstract class Tetrimino extends Actor {
    private boolean isMovable = true;
    private TetriminoTile[][] tiles;
    private final int[][] shape;
    private final Image image;

    private int maxWidth;
    private int maxHeight;

    private final int tileSize;
    private double distanceToMove;
    private static final double MOVE_SENSITIVITY = 0.1;

    public Tetrimino(int tileSize, int[][] shape, String tileImage) {
        String tetriminoImagePath = getClass().getClassLoader().getResource(tileImage).toString();

        this.image = new Image(tetriminoImagePath);
        this.shape = shape;
        this.tileSize = tileSize;

        tiles = new TetriminoTile[shape.length][shape[0].length];
        setDimensions();
    }

    private void setDimensions() {
        for (int r = 0; r < shape.length; r++) {
            int width = 0;
            int height = 0;
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c] == 1) {
                    width += tileSize;
                } else tiles[r][c] = null;
                if (width > maxWidth) maxWidth = width;
                if (shape[c][r] == 1) height += tileSize;
            }
            if (height > maxHeight) maxHeight = height;
        }
    }

    public void addTiles() {
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c] == 1) {
                    TetriminoTile tile = new TetriminoTile(this, image);
                    tile.setFitHeight(tileSize);
                    tile.setFitWidth(tileSize);
                    tile.setX(getX() + c * tileSize);
                    tile.setY(getY() + r * tileSize);
                    tiles[r][c] = tile;
                    getWorld().add(tile);
                } else tiles[r][c] = null;
            }
        }
    }

    @Override
    public void act() {
        TetrisWorld tetrisWorld = (TetrisWorld) getWorld();
        Matrix matrix = tetrisWorld.getMatrix();

        // Controls how fast you can move your tetrimino.
        boolean shouldMoveToNextTile = tetrisWorld.getShouldMoveToNextTile();
        if (getWorld().isKeyDown(KeyCode.RIGHT) && getX() + maxWidth + tileSize <= matrix.getWidth() + matrix.getX()) {
            distanceToMove = shouldMoveToNextTile ? tileSize : distanceToMove + MOVE_SENSITIVITY;
            if (distanceToMove == tileSize) {
                moveHorizontal(distanceToMove);
                distanceToMove = 0;
            }
        }
        if (getWorld().isKeyDown(KeyCode.LEFT) && getX() - tileSize >= matrix.getX()) {
            distanceToMove = shouldMoveToNextTile ? -tileSize : distanceToMove - MOVE_SENSITIVITY;
            if (distanceToMove == -tileSize) {
                moveHorizontal(distanceToMove);
                distanceToMove = 0;
            }
        }
        if (!getWorld().isKeyDown(KeyCode.LEFT) && !getWorld().isKeyDown(KeyCode.RIGHT)) {
            distanceToMove = 0;
            tetrisWorld.setShouldMoveToNextTile(false);
        }
        if (shouldMoveToNextTile) tetrisWorld.setShouldMoveToNextTile(false);

        if (getWorld().isKeyDown(KeyCode.DOWN) && getY() + maxHeight < matrix.getHeight() + matrix.getY()) {
            moveVertical(tileSize);
        }

//        if (getWorld().isKeyDown(KeyCode.SPACE)) {
//            while(isMovable && getY() + maxHeight < getWorld().getHeight()) moveVertical(tileSize);
//        }

        if (tetrisWorld.getShouldRotate()) rotate();
    }

    public void rotate() {
        TetrisWorld tetrisWorld = (TetrisWorld) getWorld();
        
        TetriminoTile[][] newTiles = new TetriminoTile[tiles.length][tiles[0].length];
        for (int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles[r].length; c++) {
                if (tiles[tiles[r].length - c - 1][r] != null) {
                    TetriminoTile tile = tiles[tiles[r].length - c - 1][r];
                    tile.setX(getX() + r * tileSize);
                    tile.setY(getY() + (tiles[r].length - c - 1) * tileSize);
                    newTiles[r][c] = tile;
                }
            }
        }
        tiles = newTiles;
        tetrisWorld.setShouldRotate(false);

        int temp = maxWidth;
        maxWidth = maxHeight;
        maxHeight = temp;
    }

    public void delayedAct() {
        if (getY() + getFitHeight() < getWorld().getHeight()) {
            moveVertical(tileSize);
        }
    }

    public void setXPos(double v) {
        setX(v);
        for (int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles[r].length; c++) {
                TetriminoTile tile = tiles[r][c];
                if (tile != null) {
                    tile.setX(v + c * tile.getWidth());
                }
            }
        }
    }

    public void setYPos(double v) {
        setY(v);
        for (int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles[r].length; c++) {
                TetriminoTile tile = tiles[r][c];
                if (tile != null) {
                    tile.setY(v + r * tile.getHeight());
                }
            }
        }
    }

    public void moveVertical(double v) {
        setY(getY() + v);
        for (int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles[r].length; c++) {
                TetriminoTile tile = tiles[r][c];
                if (tile != null) {
                    tile.setY(tile.getY() + v);
                }
            }
        }
    }

    public void moveHorizontal(double v) {
        setX(getX() + v);
        for (int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles[r].length; c++) {
                Tile tile = tiles[r][c];
                if (tile != null) tile.setX(tile.getX() + v);
            }
        }
    }

    public boolean isMovable() {
        return isMovable;
    }

    public void setIsMovable(boolean isMovable) {
        this.isMovable = isMovable;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public int getMaxHeight() {
        return maxHeight;
    }
}
