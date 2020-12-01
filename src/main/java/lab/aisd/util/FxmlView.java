package lab.aisd.util;

import java.util.ResourceBundle;

public enum FxmlView {

    MAIN {
        @Override
        public String getTitle() {
            return "Main";
        }

        @Override
        public String getFxmlFile() {
            return "main.fxml";
        }
    },
    ADD {
        @Override
        public String getTitle() {
            return "Add";
        }

        @Override
        public String getFxmlFile() {
            return "add.fxml";
        }
    };


    public abstract String getTitle();
    public abstract String getFxmlFile();

}