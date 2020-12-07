package lab.aisd.util;


import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URISyntaxException;
import java.net.URL;

public class MusicPlayer {
    private static MusicPlayer instance;

    private MediaPlayer mediaPlayer;

    private MusicPlayer() {
    }

    public static MusicPlayer getInstance() {
        if (instance == null) {
            instance = new MusicPlayer();
        }

        return instance;
    }

    public void play(String relativePath) {
        play(relativePath, 1.0);
    }

    public void play(String relativePath, double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }

        URL songPath = getClass().getResource(relativePath);
        Media media = null;

        try {
            media = new Media(songPath.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setVolume(volume);
            mediaPlayer.play();
        } catch (URISyntaxException e) {
            System.out.println("Wrong path: " + relativePath);
            e.printStackTrace();
            Platform.exit();
        }
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public boolean isPlaying () {
        return mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING);
    }

    public void setVolume(double value) {
        mediaPlayer.setVolume(value);
    }
}
