package com.seifabdelaziz.tetris.Engine;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PopupPanel {
    private Rectangle background = new Rectangle();
    private Rectangle panel = new Rectangle();
    private Pane content = new Pane();

    public PopupPanel(double panelWidth, double panelHeight) {
        Scene scene = GameManager.getInstance().getGame();

        background.setWidth(scene.getWidth());
        background.setHeight(scene.getHeight());
        background.setFill(Color.web("#000000", 0.8));

        panel.setFill(Color.web("#34495e", 0.4));
        panel.setWidth(panelWidth);
        panel.setHeight(panelHeight);
        panel.setArcWidth(20);
        panel.setArcHeight(20);

        panel.setX(scene.getWidth() / 2 - panel.getWidth() / 2);
        panel.setY(scene.getHeight() / 2 - panel.getHeight() / 2);
    }

    public Rectangle getPanel() {
        return panel;
    }

    public Rectangle getBackground() {
        return background;
    }

    public Pane getContent() {
        return content;
    }

    public void setContent(Pane content) {
        Scene scene = GameManager.getInstance().getGame();
        this.content = content;
        content.setMinWidth(scene.getWidth());
        content.setMinHeight(scene.getHeight());
    }
}
