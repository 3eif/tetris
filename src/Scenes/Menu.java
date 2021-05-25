package com.seifabdelaziz.tetris.Scenes;

import com.seifabdelaziz.tetris.Engine.GameManager;
import com.seifabdelaziz.tetris.Engine.World;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Menu extends World {
    public Menu() {
        Text titleText = new Text("Tetris");
        titleText.setFont(new Font(50));
        titleText.setX(100);
        titleText.setY(100);

        Text wipText = new Text("This UI is a WIP");
        wipText.setFont(new Font(30));
        wipText.setX(100);
        wipText.setY(145);

        Text playText = new Text("Press P to play");
        playText.setFont(new Font(40));
        playText.setX(100);
        playText.setY(195);
        getChildren().addAll(titleText, wipText, playText);

        setOnKeyPressed(keyEvent -> addKeyCode(keyEvent.getCode()));
        setOnKeyReleased(keyEvent -> removeKeyCode(keyEvent.getCode()));
    }

    @Override
    public void act(long now) {
        if(isKeyDown(KeyCode.P)) {
            GameManager gameManager = GameManager.getInstance();
            gameManager.setCurrentScene(new TetrisWorld());
        }
    }
}
