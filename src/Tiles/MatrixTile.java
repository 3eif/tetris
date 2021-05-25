package com.seifabdelaziz.tetris.Tiles;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;

public class MatrixTile extends Tile {
    public MatrixTile(Image image) {
        super(image);
    }

    @Override
    public void act(long now) {

    }

    public TetriminoTile getTetrminoTileAbove() {
        TetriminoTile aboveTetrimino = null;
        ObservableList<Node> actors = getParent().getChildrenUnmodifiable();
        for (Node actor : actors) {
            if (actor instanceof TetriminoTile && (actor.contains(getX() + (getWidth() / 2), getY()) &&
                    actor.contains(getX() + (getWidth() / 2), getY() + getHeight() / 2))) {
                aboveTetrimino = (TetriminoTile) actor;
            }
        }
        return aboveTetrimino;
    }
}
