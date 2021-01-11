package lab.aisd;

import javafx.application.Application;
import javafx.stage.Stage;
import lab.aisd.util.FxmlView;
import lab.aisd.util.MusicPlayer;
import lab.aisd.util.StageManager;

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
        new Thread(() -> MusicPlayer.getInstance().play("/music/Ambulance_bg.wav", 0.1)).start();
    }
}
