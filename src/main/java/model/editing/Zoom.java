package model.editing;

import java.awt.Event;
import java.awt.Graphics2D;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.beans.EventHandler;

import javafx.embed.swing.SwingFXUtils;


/**
 * In and Out-Zooming of an image
 * @author Julian Einspenner
 *
 */
public class Zoom{
	
	public static BufferedImage zoomIn(BufferedImage img) {
		
		int w = (int)img.getWidth(), h = (int)img.getHeight();
		
		if(w < 180 || h < 180) {
			return img;
		}	

		int zLev = 10;    // ZoomLevel: Angabe in Prozent
		int x1 = w / zLev;
		int x2 = h / zLev;
		
		int drawWidth = w - 2 * w / zLev;
		int drawHeight = h - 2 * h / zLev;
		
		img = img.getSubimage(x1, x2, drawWidth, drawHeight);

		return img;
		
	}

}
