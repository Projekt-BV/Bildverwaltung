package model.editing;

import java.awt.image.BufferedImage;

/**
 * This class is able to rotate a Buffered Image
 * @author Julian Einspenner
 */
public class Rotater {

	
	/**
	 * Rotates the image clockwise with 45 degree
	 * @author Julian Einspenner
	 * @param img is the Image to rotate
	 * @return the rotated Image
	 */
	public static BufferedImage rotateClockwise(BufferedImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		
		int pixels[];
		pixels = UtilsForImageHandling.getPixelArray(img);
		pixels = rotateArrayClockwise(pixels, width, height);
		
		int tmp = width;
		width = height;
		height = tmp;
		
		BufferedImage img2 = new BufferedImage(width, height, 1);
		img2.setRGB(0, 0, width, height, pixels, 0, img.getHeight());
		
		return img2;
	}
	
	/**
	 * Rotates the image anticlockwise with 45 degree
	 * @author Julian Einspenner
	 * @param img is the Image to rotate
	 * @return the rotated Image
	 */
	public static BufferedImage rotateAntiClockwise(BufferedImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		
		int pixels[];
		pixels = UtilsForImageHandling.getPixelArray(img);
		pixels = rotateArrayAntiClockwise(pixels, width, height);
		
		int tmp = width;
		width = height;
		height = tmp;
		
		BufferedImage img2 = new BufferedImage(width, height, 1);
		img2.setRGB(0, 0, width, height, pixels, 0, img.getHeight());
		
		return img2;
	}
	
	
	
	
	/**
	 * Rotates a pixel array anticlockwise
	 * @author Julian Einspenner
	 * @param pixels is the pixel array
	 * @param width the current width of the image
	 * @param height the current height of the image
	 * @return the anticlockwise rotated pixel array
	 */
	public static int[] rotateArrayAntiClockwise(int[] pixels, int width, int height) {
		int [] pixelRotatedAntiClockwise = new int[pixels.length];
		
		int z = 0;
		for(int i = 1; i <= width; i++) {
			for(int j = 1; j <= height; j++) {
				pixelRotatedAntiClockwise[z] = pixels[j*width - i];
				z++;
			}
		}
		return pixelRotatedAntiClockwise;
	}
	
	/**
	 * Rotates a pixel array clockwise
	 * @author Julian Einspenner
	 * @param pixels is the pixel array
	 * @param width the current width of the image
	 * @param height the current height of the image
	 * @return the clockwise rotated pixel array
	 */
	public static int[] rotateArrayClockwise(int[] pixels, int width, int height) {
		int size = pixels.length;
		int [] pixelRotatedClockwise = new int[pixels.length];
		
		int z = 0;
		for(int i = 0; i < width; i++) {
			for(int j = 1; j <= height; j++) {
				pixelRotatedClockwise[z] = pixels[size - j*width + i];
				z++;
			}
		}
		
		return pixelRotatedClockwise;
	}
	
}
