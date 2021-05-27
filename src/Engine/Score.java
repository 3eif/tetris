package com.seifabdelaziz.tetris.Engine;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Score extends Text{
    private int scoreVal;

    public Score() {
        scoreVal = 0;
        setFont(Font.loadFont("file:resources/fonts/Kenney Mini Square.ttf", 30));
        setFill(Color.WHITE);
        updateDisplay();
    }

    public void updateDisplay() {
        this.setText("Score: " + scoreVal);
    }

    public int getScoreVal() {
        return scoreVal;
    }

    public void setScoreVal(int val) {
        scoreVal = val;
        updateDisplay();
    }
}
