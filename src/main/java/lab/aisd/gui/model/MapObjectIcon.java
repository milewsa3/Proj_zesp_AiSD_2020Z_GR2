package lab.aisd.gui.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


public class MapObjectIcon extends StackPane {
    protected ImageView icon;

    public MapObjectIcon(int x, int y) {
        this.setPrefWidth(100);
        this.setPrefHeight(100);
        setLayoutX(x);
        setLayoutY(y);
    }

    public MapObjectIcon(int x, int y, String relativePathToImage) {
        this(x, y);
        initIcon(relativePathToImage);
    }

    private void initIcon(String relativePathToImage) {
        icon = new ImageView(new Image(getClass().getResource(relativePathToImage).toString()));
        icon.setPreserveRatio(true);
        icon.fitHeightProperty().bind(this.prefHeightProperty());

        /*this.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));*/
        getChildren().add(icon);
    }

    @Override
    public String toString() {
        return "MapObjectIcon{" +
                "Width: " + this.getPrefWidth() +
                ", Height: " + this.getPrefHeight() +
                ", x Layout: " + this.getLayoutX() +
                ", y Layout: " + this.getLayoutY() +
                ", x Translation: " + this.getTranslateX() +
                ", y Translation: " + this.getTranslateY();
    }

    public ImageView getIcon() {
        return icon;
    }
}
