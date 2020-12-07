package lab.aisd.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AboutController {

    @FXML
    private Button closeBt;

    @FXML
    void exit(ActionEvent event) {
        Stage stage = (Stage) closeBt.getScene().getWindow();
        stage.close();
    }

}
