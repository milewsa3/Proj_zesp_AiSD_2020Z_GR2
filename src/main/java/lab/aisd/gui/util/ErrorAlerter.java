package lab.aisd.gui.util;

import javafx.scene.control.Alert;

public class ErrorAlerter {
    public static void showIntersectionsError() {
        showErrorAlertScene(
                "Error",
                "Intersection Finder Issue",
                "Intersection finder failed. Coudn't calculate intersection points!"
        );
    }

    public static void showDataNotValidError() {
        showErrorAlertScene("Data Error", "Data is not loaded");
    }

    public static void showMapNotLoadedError() {
        showErrorAlertScene("Data Error", "Map is not loaded. " +
                "To load patient, you have to load map first");
    }

    public static void showPatientNotWithinBorderError() {
        showErrorAlertScene("Adding error", "Patient must be located within the border!");
    }

    public static void showFileHandlingError(String message) {
        showErrorAlertScene("Problem with file handling", message);
    }

    public static void showReadingMapFileError(String message) {
        showErrorAlertScene("Reading map file error", message);
    }

    public static void showReadingPatientFileError(String message) {
        showErrorAlertScene("Reading patient file error", message);
    }

    private static void showErrorAlertScene(String content) {
        showErrorAlertScene("Error", "Error", content);
    }

    private static void showErrorAlertScene(String header,String content) {
        showErrorAlertScene("Error", header, content);
    }

    private static void showErrorAlertScene(String title, String header,String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
}
