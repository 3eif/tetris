package com.seifabdelaziz.tetris.Engine;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Soundtrack {
    MediaPlayer soundtrackPlayer;

    public Soundtrack(String audioFilePath) {
        String soundtrackAudioFilePath = getClass().getClassLoader().getResource(audioFilePath).toString();
        Media soundtrack = new Media(soundtrackAudioFilePath);
        soundtrackPlayer = new MediaPlayer(soundtrack);
    }

    public void play() {
        soundtrackPlayer.setAutoPlay(true);
    }

    public void stop() {
        soundtrackPlayer.stop();
    }

    public double getVolume() {
        return soundtrackPlayer.getVolume();
    }

    public void setVolume(double volume) {
        soundtrackPlayer.setVolume(volume * 0.10);
    }

    public void loop(boolean loop) {
        if(loop) {
            soundtrackPlayer.setOnEndOfMedia(() -> soundtrackPlayer.seek(Duration.ZERO));
        } else {
            soundtrackPlayer.setOnEndOfMedia(() -> {});
        }
    }
}
