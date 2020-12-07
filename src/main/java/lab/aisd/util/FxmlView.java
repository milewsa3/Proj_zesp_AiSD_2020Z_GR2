package lab.aisd.util;

import java.util.ResourceBundle;

public enum FxmlView {

    MENU {
        @Override
        public String getTitle() {
            return "Menu";
        }

        @Override
        public String getFxmlFile() {
            return "menu.fxml";
        }
    },
    MAP {
        @Override
        public String getTitle() {
            return "Map";
        }

        @Override
        public String getFxmlFile() {
            return "map.fxml";
        }
    },
    ABOUT {
        @Override
        public String getTitle() {
            return "About";
        }

        @Override
        public String getFxmlFile() {
            return "about.fxml";
        }
    };


    public abstract String getTitle();
    public abstract String getFxmlFile();

}