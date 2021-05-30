package com.seifabdelaziz.tetris.Engine;

import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class World extends Pane {
    AnimationTimer timer;
    private double mouseX = 0;
    private long prev = 0;
    HashSet<KeyCode> keyCodes = new HashSet<KeyCode>();

    public final String MATRIX_TILE_IMAGE_PATH = getClass().getClassLoader().getResource("images/black-tile.png").toString();
    public final Image MATRIX_TILE_IMAGE = new Image(MATRIX_TILE_IMAGE_PATH);

    public final int TILE_SIZE = 30;

    public World() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                act(now);

                ObservableList<Node> children = getChildren();
                for (int i = 0; i < children.size(); i++) {
                    if (children.get(i) instanceof Actor) {
                        Actor a = (Actor) (children.get(i));
                        a.act(now);
                    }
                }

                if (now - prev > (1e6 * 500)) {
                    prev = now;
                }
            }
        };
    }

    public void addKeyCode(KeyCode keyCode) {
        keyCodes.add(keyCode);
    }

    public void removeKeyCode(KeyCode keyCode) {
        keyCodes.remove(keyCode);
    }

    public boolean isKeyDown(KeyCode keyCode) {
        return keyCodes.contains(keyCode);
    }

    public abstract void act(long now);

    public void add(Actor actor) {
        getChildren().add(actor);
    }

    public void remove(Actor actor) {
        getChildren().remove(actor);
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public <A extends Actor> java.util.List<A> getObjects(java.lang.Class<A> cls) {
        ArrayList<A> objects = new ArrayList<>();
        for(int i = 0; i < getChildren().size(); i++) {
            if(getChildren().get(i).getClass().isInstance(cls)) {
                objects.add((A) Actor.class.cast(getChildren().get(i).getClass()));
            }
        }
        return objects;
    }

    public double getMouseX() {
        return mouseX;
    }

    public void setMouseX(double mouseX) {
        this.mouseX = mouseX;
    }

    public long getPrev() {
        return prev;
    }
}