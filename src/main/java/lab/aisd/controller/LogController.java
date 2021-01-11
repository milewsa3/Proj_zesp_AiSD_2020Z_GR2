package lab.aisd.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import lab.aisd.gui.util.ErrorAlerter;
import lab.aisd.log.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LogController implements Initializable {

    @FXML
    private TextArea logTextArea;

    @FXML
    private Button saveBt;

    public void setLogs(List<Log> logs) {
        StringBuilder result = new StringBuilder();

        for (Log l : logs) {
            result.append(l);
            result.append('\n');
        }

        Thread thread = new Thread(() -> {
            char[] chars = result.toString().toCharArray();

            for (char c : chars) {
                logTextArea.setText(logTextArea.getText() + c);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        thread.start();
    }

    @FXML
    void saveLogs(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text", "*.txt"));

        File file = fileChooser.showSaveDialog(saveBt.getScene().getWindow());
        if (file == null)
            return;

        try {
            if(file.createNewFile()) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));

                bw.write(logTextArea.getText());
                bw.close();
            }
        } catch (IOException e) {
            ErrorAlerter.showFileHandlingError(e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
