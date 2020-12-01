package lab.aisd.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lab.aisd.Main;
import lab.aisd.util.FxmlView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Label mainLabel;

    @FXML
    private Button addBt;

    @FXML
    void openAddingWin(ActionEvent event) {
        Main.stageManager.openNewScene(FxmlView.ADD);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
