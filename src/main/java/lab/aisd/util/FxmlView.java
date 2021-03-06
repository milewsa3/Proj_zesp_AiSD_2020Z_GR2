package lab.aisd.util;

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
    SETTINGS {
        @Override
        public String getTitle() {
            return "Settings";
        }

        @Override
        public String getFxmlFile() {
            return "settings.fxml";
        }
    },
    CONFIG {
        @Override
        public String getTitle() {
            return "Configuration";
        }

        @Override
        public String getFxmlFile() {
            return "configuration.fxml";
        }
    },
    LOG {
        @Override
        public String getTitle() {
            return "Log";
        }

        @Override
        public String getFxmlFile() {
            return "log.fxml";
        }
    },
    HOSPITAL_INFO {
        @Override
        public String getTitle() {
            return "Hospital Info";
        }

        @Override
        public String getFxmlFile() {
            return "hospitalInfo.fxml";
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