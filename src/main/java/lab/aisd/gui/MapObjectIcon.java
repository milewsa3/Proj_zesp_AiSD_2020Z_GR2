package lab.aisd.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


public class MapObjectIcon extends StackPane {
    public static final int ICON_WIDTH = 70;
    protected ImageView icon;

    public MapObjectIcon(int x, int y) {
        setTranslateX(x);
        setTranslateY(y);
    }

    public MapObjectIcon(int x, int y, String relativePathToImage) {
        this(x, y);
        icon = new ImageView(new Image(getClass().getResource(relativePathToImage).toString()));
        icon.setPreserveRatio(true);
        icon.setFitWidth(ICON_WIDTH);
        getChildren().add(icon);
    }
}
