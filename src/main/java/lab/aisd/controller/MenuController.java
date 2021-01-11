package lab.aisd.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lab.aisd.util.FxmlView;
import lab.aisd.util.StageManager;

public class MenuController {

    @FXML
    private Button startBt;

    @FXML
    private Button exitBt;

    @FXML
    void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void start(ActionEvent event) {
        StageManager.getInstance().switchScene(FxmlView.MAP);
    }

}