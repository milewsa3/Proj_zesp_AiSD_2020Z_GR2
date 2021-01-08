package lab.aisd.gui.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ConfirmationAlerter {
    public static boolean showUserWantsToStartCalcConfirmation() {
        return showConfirmationAlertScene("Are you sure you want to start calculations?")
                .get() == ButtonType.OK;
    }

    private static Optional<ButtonType> showConfirmationAlertScene(String content) {
        return showConfirmationAlertScene("Confirmation", "Confirmation", content);
    }

    private static Optional<ButtonType> showConfirmationAlertScene(String header, String content) {
        return showConfirmationAlertScene("Confirmation", header, content);
    }

    private static Optional<ButtonType> showConfirmationAlertScene(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        return alert.showAndWait();
    }
}
