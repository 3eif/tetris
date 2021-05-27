package com.seifabdelaziz.tetris.Tiles;

import com.seifabdelaziz.tetris.Engine.Actor;
import com.seifabdelaziz.tetris.Scenes.TetrisWorld;
import com.seifabdelaziz.tetris.Tetriminoes.Tetrimino;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;

public class TetriminoTile extends Tile {
    private Tetrimino parentTetrimino;

    public TetriminoTile(Tetrimino tetrimino, Image image) {
        super(image);
        parentTetrimino = tetrimino;
    }

    @Override
    public <A extends Actor> A getOneIntersectingObject(java.lang.Class<A> cls) {
        A intersectingObject = null;
        ObservableList<Node> actors = getParent().getChildrenUnmodifiable();
        for(int i = 0; i < actors.size(); i++) {
            if(actors.get(i) != this && cls.isInstance(actors.get(i)) && (actors.get(i).contains(getX() + (getWidth() / 2), getY()) ||
                    actors.get(i).contains(getX() + (getWidth() / 2), getY() + getHeight()))) {
                intersectingObject = (A) actors.get(i);
                break;
            }
        }
        return intersectingObject;
    }

    public <A extends Actor> A getObjectContaining(java.lang.Class<A> cls) {
        A intersectingObject = null;
        ObservableList<Node> actors = getParent().getChildrenUnmodifiable();
        for (Node actor : actors) {
            if (actor != this && cls.isInstance(actor) && (actor.contains(getX() + (getWidth() / 2), getY()) &&
                    actor.contains(getX() + (getWidth() / 2), getY() + getHeight() / 2))) {
                intersectingObject = (A) actor;
                break;
            }
        }
        return intersectingObject;
    }

    @Override
    public void act(long now) {
        TetrisWorld tetrisWorld = (TetrisWorld) getWorld();

        MatrixTile matrixTile = getObjectContaining(MatrixTile.class);
        TetriminoTile tetriminoTileContaining = getObjectContaining(TetriminoTile.class);
//        if(!parentTetrimino.isBeingHeld() && !parentTetrimino.isNext() &&
//                tetriminoTileContaining != null && !parentTetrimino.isMovable())
//            parentTetrimino.moveVertical(-tetrisWorld.getTileSize());

        TetriminoTile tetriminoTile = getOneIntersectingObject(TetriminoTile.class);
        if(tetriminoTile != null && tetriminoTile.getParentTetrimino() != parentTetrimino && parentTetrimino.isMovable()) {
            parentTetrimino.setIsMovable(false);
            tetrisWorld.spawnTetrimino();
        }

        if(!parentTetrimino.isBeingHeld() && !parentTetrimino.isNext() && matrixTile == null && !parentTetrimino.isMovable())
            parentTetrimino.moveVertical(-tetrisWorld.getTileSize());
    }

    public Tetrimino getParentTetrimino() {
        return parentTetrimino;
    }

    public void setParentTetrimino(Tetrimino parentTetrimino) {
        this.parentTetrimino = parentTetrimino;
    }
}
