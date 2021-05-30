package com.seifabdelaziz.tetris.Scenes;

import com.seifabdelaziz.tetris.Engine.World;
import javafx.application.Platform;

public class Quit extends World {
    public Quit() {

    }

    @Override
    public void act(long now) {
        Platform.exit();
    }
}
