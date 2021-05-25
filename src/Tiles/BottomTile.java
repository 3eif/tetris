package com.seifabdelaziz.tetris.Tiles;

import com.seifabdelaziz.tetris.Engine.Actor;
import com.seifabdelaziz.tetris.Scenes.TetrisWorld;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;

public class BottomTile extends MatrixTile {
    public BottomTile(Image image) {
        super(image);
    }

    @Override
    public void act(long now) {
        TetriminoTile tetriminoTile = getOneIntersectingObject(TetriminoTile.class);
        if(tetriminoTile != null && tetriminoTile.getParentTetrimino().isMovable()) {
            TetrisWorld tetrisWorld = (TetrisWorld) getWorld();
            tetriminoTile.getParentTetrimino().setIsMovable(false);
            tetrisWorld.spawnTetrimino();
        }
    }

    @Override
    public <A extends Actor> A getOneIntersectingObject(java.lang.Class<A> cls) {
        A intersectingObject = null;
        ObservableList<Node> actors = getParent().getChildrenUnmodifiable();
        for(int i = 0; i < actors.size(); i++) {
            if(actors.get(i) != this && cls.isInstance(actors.get(i)) &&
                    (actors.get(i).contains(getX() + (getWidth() / 2), getY() + (getHeight() / 2)))) {
                intersectingObject = (A) actors.get(i);
                break;
            }
        }
        return intersectingObject;
    }
}
