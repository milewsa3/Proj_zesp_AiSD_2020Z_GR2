package lab.aisd.gui.util;

import javafx.scene.control.Alert;

public class InfoAlerter {
    public static void showDataLoadedSuccessfullyInfo() {
        showInfoAlertScene("Information",
                "Data loaded successfully",
                "Great. Your data is valid and it was loaded properly.");
    }

    private static void showInfoAlertScene(String content) {
        showInfoAlertScene("Information", "Information", content);
    }

    private static void showInfoAlertScene(String header, String content) {
        showInfoAlertScene("Information", header, content);
    }

    private static void showInfoAlertScene(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
}
