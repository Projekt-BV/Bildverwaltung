package model.editing;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;

/**
 * Class for In and Out-Zooming of an image
 * @author Julian Einspenner
 */
public class Zoom{
	
	public static void zoom(ImageView imageView) {
		
		final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);
		
		zoomProperty.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                imageView.setFitWidth(zoomProperty.get() * 4);
                imageView.setFitHeight(zoomProperty.get() * 3);
            }
        });
	}
}
