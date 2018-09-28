package model.editing;

import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 * Class for grayscaling any image
 * @author Julian Einspenner
 */
public class GrayScaler {

	
	/**
	 * The typical black & and white algorithm. It gets a Buffered Image and makes it black and white
	 * @param img is the image getting black & white
	 * @return the black & white image
	 */
	public static Image grayScaleImage(Image img) {
		
		BufferedImage bimg = SwingFXUtils.fromFXImage(img, null);
		int[] pixels = UtilsForImageHandling.getPixelArray(bimg);
		
		for(int i = 0; i < pixels.length; i++) {
			int p = pixels[i];
			
			int a = (p>>24) & 0xff;
			int r = (p>>16) & 0xff;
			int g = (p>>8 ) & 0xff;
			int b =  p      & 0xff;
			
			int avg = (r+g+b) / 3;
			
			p = (a << 24 ) | (avg << 16) | (avg << 8) | avg;
			
			pixels[i] = p;
		}
		
		bimg.setRGB(0, 0, bimg.getWidth(), bimg.getHeight(), pixels, 0, bimg.getWidth());
		
		return SwingFXUtils.toFXImage(bimg, null);
	}
	
}
