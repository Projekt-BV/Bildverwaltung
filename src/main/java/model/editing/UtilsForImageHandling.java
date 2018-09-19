package model.editing;

import java.awt.image.BufferedImage;

public class UtilsForImageHandling {

	/**
	 * Generates a pixel array
	 * @author Julian Einspenner
	 * @param is your picture source
	 * @return the array 
	 */
	public static int[] getPixelArray(BufferedImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		
		int[] pixels = new int[width * height];
		int z = 0;
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				pixels[z] = img.getRGB(x, y);
				z++;
			}
		}
		return pixels;
	}
	
}
