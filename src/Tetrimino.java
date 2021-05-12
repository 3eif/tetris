import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public abstract class Tetrimino extends Actor {
    private boolean isMovable = true;
    private TetriminoTile[][] tiles;
    private int[][] shape;
    private final Image image;

    private int maxWidth;
    private int maxHeight;

    private final int tileSize;

    public Tetrimino(int tileSize, int[][] shape, String tileImage) {
        String tetriminoImagePath = getClass().getClassLoader().getResource(tileImage).toString();
        Image tetriminoImage = new Image(tetriminoImagePath);

        this.image = tetriminoImage;
        this.shape = shape;
        this.tileSize = tileSize;

        tiles = new TetriminoTile[shape.length][shape[0].length];

        for(int r = 0; r < shape.length; r++) {
            int width = 0;
            int height = 0;
            for (int c = 0; c < shape[r].length; c++) {
                if(shape[r][c] == 1) {
                    width += tileSize;
                } else tiles[r][c] = null;
                if(width > maxWidth) maxWidth = width;
                if(shape[c][r] == 1) height += tileSize;
            }
            if(height > maxHeight) maxHeight = height;
        }
    }

    public void addTiles() {
        for(int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if(shape[r][c] == 1) {
                    TetriminoTile tile = new TetriminoTile(this, image);
                    tile.setFitHeight(tileSize);
                    tile.setFitWidth(tileSize);
                    tile.setX(c * tileSize);
                    tile.setY(r * tileSize);
                    tiles[r][c] = tile;
                    getWorld().add(tile);
                } else tiles[r][c] = null;
            }
        }
    }

    @Override
    public void act() {
        TetrisWorld tetrisWorld = (TetrisWorld) getWorld();
        if (getWorld().isKeyDown(KeyCode.RIGHT) &&  getX() + + maxWidth + tileSize <= tetrisWorld.getWidth()) {
            moveHorizontal(tileSize);
        }
        if (getWorld().isKeyDown(KeyCode.LEFT) && getX() - tileSize >= 0) {
            moveHorizontal(-tileSize);
        }

        if (getWorld().isKeyDown(KeyCode.DOWN) && getY() + maxHeight < getWorld().getHeight()) {
            moveVertical(tileSize);
        }

//        if (getWorld().isKeyDown(KeyCode.SPACE)) {
//            while(isMovable && getY() + maxHeight < getWorld().getHeight()) moveVertical(tileSize);
//        }

        if(tetrisWorld.getShouldRotate()) {
            TetriminoTile[][] newTiles = new TetriminoTile[tiles.length][tiles.length];
            for(int r = 0; r < tiles.length; r++) {
                for (int c = 0; c < tiles[r].length; c++) {
                    if(tiles[r][c] != null) {
                        TetriminoTile tile = tiles[r][c];
                        tile.setX(getX() + c * tileSize);
                        tile.setY(getY() + r * tileSize);
                        newTiles[c][r] = tile;
                    }
                }
            }
            tiles = newTiles;

            tetrisWorld.setShouldRotate(false);
        }
    }

    public void delayedAct() {
        TetrisWorld tetrisWorld = (TetrisWorld) getWorld();
        if (getY() + getFitHeight() < getWorld().getHeight()) {
            moveVertical(tileSize);
        }
    }

    public void moveVertical(double v) {
        TetrisWorld tetrisWorld = (TetrisWorld) getWorld();
        setY(getY() + v);
        for(int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles[r].length; c++) {
                TetriminoTile tile = tiles[r][c];
                if(tile != null) {
                    tile.setY(tile.getY() + v);
                }
            }
        }
    }

    public void moveHorizontal(double v) {
        TetrisWorld tetrisWorld = (TetrisWorld) getWorld();
        setX(getX() + v);
        for(int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles[r].length; c++) {
                Tile tile = tiles[r][c];
                if(tile != null) tile.setX(tile.getX() + v);
            }
        }
    }

    public boolean isMovable() {
        return isMovable;
    }

    public void setIsMovable(boolean isMovable) {
        this.isMovable = isMovable;
    }
}
