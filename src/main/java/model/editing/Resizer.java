package model.editing;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 * Class makes it possible to resize an image
 * @author Thomas Spanier, Julian Einspenner
 */
public class Resizer {

	/**
	 * Resizes an image to the preferred width and height
	 * @param w is the new width
	 * @param h is the new height
	 * @return is the resized image
	 */
	public static Image resizeImage(int width, int height, Image img) {
		BufferedImage bimg = SwingFXUtils.fromFXImage(img, null);
		
		java.awt.Image awtImage = bimg.getScaledInstance(width, height, 0);
		
		bimg = new BufferedImage(awtImage.getWidth(null), awtImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    Graphics2D bGr = bimg.createGraphics();
	    bGr.drawImage(awtImage, 0, 0, null);
	    bGr.dispose();
		
		return SwingFXUtils.toFXImage(bimg, null);
	}
	
}
