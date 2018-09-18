package model.editing;

import java.awt.image.BufferedImage;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;


/**
 * In and Out-Zooming of an image
 * @author Julian Einspenner
 *
 */
public class Zoom{
	
	private static int zLev = 10;  //ZoomLevel, Angabe in %
	
	public static void zoom(ImageView imageView) {
		
		final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);
		
		zoomProperty.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                imageView.setFitWidth(zoomProperty.get() * 4);
                imageView.setFitHeight(zoomProperty.get() * 3);
            }
        });
		
		/*
		 
		scrollPane.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaY() > 0) {
                    zoomProperty.set(zoomProperty.get() * 1.1);
                } else if (event.getDeltaY() < 0) {
                    zoomProperty.set(zoomProperty.get() / 1.1);
                }
            }
        });
        
        */
	}
	
	
//	public static BufferedImage zoomIn(BufferedImage img) {
//		
//		int w = (int)img.getWidth(), h = (int)img.getHeight();
//
//		int zLev = 10;    // ZoomLevel: Angabe in Prozent
//		int x1 = w / zLev;
//		int x2 = h / zLev;
//		
//		int drawWidth = w - 2 * w / zLev;
//		int drawHeight = h - 2 * h / zLev;
//		
//		img = img.getSubimage(x1, x2, drawWidth, drawHeight);
//
//		return img;
//		
//	}
	
	
public static BufferedImage zoomOut(BufferedImage img) {
		
		// SOON
		return img;
	}

}
