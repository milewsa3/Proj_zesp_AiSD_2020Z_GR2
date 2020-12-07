package lab.aisd.util;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lab.aisd.Main;

import java.io.File;
import java.util.Objects;

/**
 * Manages switching Scenes on the Primary Stage
 */
public class StageManager {
    private static StageManager instance;

    private final Stage primaryStage;

    private StageManager(Stage stage) {
        this.primaryStage = stage;
    }

    public static void initInstance(Stage stage) {
        if (instance == null)
            instance = new StageManager(stage);
    }

    public static StageManager getInstance() {
        return instance;
    }

    public void showAlertScene(final FxmlView view, String title, String description) {
        //to implement
    }

    public void setResizable(boolean value) {
        primaryStage.setResizable(value);
    }

    public void openNewScene(final FxmlView view) {
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        Stage window = new Stage();
        Scene scene = new Scene(viewRootNodeHierarchy);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(view.getTitle());
        window.setScene(scene);
        window.showAndWait();
    }

    public void switchScene(final FxmlView view) {
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        show(viewRootNodeHierarchy, view.getTitle());
    }

    private void show(final Parent rootnode, String title) {
        Scene scene = prepareScene(rootnode);
        //scene.getStylesheets().add("/styles/Styles.css");

        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();

        try {
            primaryStage.show();
        } catch (Exception exception) {
            logAndExit ("Unable to show scene for title" + title,  exception);
        }
    }

    private Scene prepareScene(Parent rootnode){
        Scene scene = primaryStage.getScene();

        if (scene == null) {
            scene = new Scene(rootnode);
        }
        scene.setRoot(rootnode);
        return scene;
    }

    /**
     * Loads the object hierarchy from a FXML document and returns to root node
     * of that hierarchy.
     *
     * @return Parent root node of the FXML document hierarchy
     */
    private Parent loadViewNodeHierarchy(String fxmlFilePath) {
        Parent rootNode = null;
        try {
            rootNode = FXMLLoader.load(getClass().getResource("/fxml/" + fxmlFilePath));
            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
        } catch (Exception exception) {
            logAndExit("Unable to load FXML fxml/" + fxmlFilePath, exception);
        }
        return rootNode;
    }


    private void logAndExit(String errorMsg, Exception exception) {
        System.out.println(errorMsg);
        Platform.exit();
    }

    public void setIcon(String relativePath) {
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(relativePath)));
    }

    public Stage getStage() {
        return primaryStage;
    }
}
