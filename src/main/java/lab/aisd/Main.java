package lab.aisd;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import lab.aisd.util.FxmlView;
import lab.aisd.util.MusicPlayer;
import lab.aisd.util.StageManager;

import java.net.URL;
import java.nio.file.Paths;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StageManager.initInstance(primaryStage);
        displayInitialScene();
        playBackgroundMusic();
    }

    protected void displayInitialScene() {
        StageManager.getInstance().switchScene(FxmlView.MENU);
        StageManager.getInstance().setIcon("/asset/icon.png");
        StageManager.getInstance().setResizable(false);
    }

    protected void playBackgroundMusic() {
        new Thread(() -> MusicPlayer.getInstance().play("/music/Ambulance_bg.wav", 0.2)).start();
    }
}
