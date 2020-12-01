package lab.aisd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lab.aisd.util.FxmlView;
import lab.aisd.util.StageManager;

import java.io.IOException;

public class Main extends Application {

    public static StageManager stageManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stageManager = new StageManager(primaryStage);
        displayInitialScene();
    }

    protected void displayInitialScene() {
        stageManager.switchScene(FxmlView.MAIN);
    }
}
