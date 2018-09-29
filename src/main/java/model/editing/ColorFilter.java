package model.editing;

import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.*;
import java.awt.Color;


/**
 * This class enables to filter colors of an image
 * Color values:
 * 2 -> Red
 * 3 -> Green
 * 4 -> Blue
 * 5 -> Yellow
 * 6 -> Violet
 * 7 -> Aqua
 * @author Julian Einspenner, Aude Takam Nana
 */
public class ColorFilter {
	
	/**
	 * Colorfilters an image. See class documentation for the number and its referenced color
	 * @param img is the image which is going to be colorfiltered
	 * @param color is the color the image should have after filtering (possible strings: red, green, blue, yellow, violet, aqua)
	 * @return The filtered image
	 */
	public static Image filterCoulours(Image img, int color) {
		BufferedImage bimg = SwingFXUtils.fromFXImage(img, null);
		int w = bimg.getWidth();
		int h = bimg.getHeight();
		
		Color[] colors = new Color[w * h];
		
		int z = 0;
		for(int i = 0; i < h; i++) {
			for(int j = 0; j < w; j++) {
				colors[z++] = new Color(bimg.getRGB(j, i));
			}
		}
		
		switch(color) {
		
			case(2): 
				colors = makeItRed(colors);
				break;
			case(3): 
				colors = makeItGreen(colors);
				break;
			case(4): 
				colors = makeItBlue(colors);
				break;
			case(5): 
				colors = makeItYellow(colors);
				break;
			case(6): 
				colors = makeItViolet(colors);
				break;
			case(7): 
				colors = makeItAqua(colors);
				break;
			default:
				return img;
		}
		
		int[] rgbArray = new int[colors.length];
		
		for(int i = 0; i < colors.length; i++) {
			rgbArray[i] = colors[i].getRGB();
		}
		
		bimg.setRGB(0, 0, w, h, rgbArray, 0, w);
		
		return SwingFXUtils.toFXImage(bimg, null);
	}
		
	/**
	 * Filters an image red
	 * @param colors is the Color-Array if the images pixels
	 * @return the filtered colors
	 */
	public static Color[] makeItRed(Color[] colors) {
		for(int i = 0; i < colors.length; i++) {
			int red = colors[i].getRed();
			colors[i] = new Color(red, 0, 0);
		}
		return colors;
	}
	
	/**
	 * Filters an image green
	 * @param colors is the Color-Array if the images pixels
	 * @return the filtered colors
	 */
	private static Color[] makeItGreen(Color[] colors) {
		for(int i = 0; i < colors.length; i++) {
			int green = colors[i].getGreen();
			colors[i] = new Color(0, green, 0);
		}
		return colors;
	}
	
	/**
	 * Filters an image blue
	 * @param colors is the Color-Array if the images pixels
	 * @return the filtered colors
	 */
	public static Color[] makeItBlue(Color[] colors) {
		for(int i = 0; i < colors.length; i++) {
			int blue = colors[i].getBlue();
			colors[i] = new Color(0, 0, blue);
		}
		return colors;
	}
	
	/**
	 * Filters an image yellow
	 * @param colors is the Color-Array if the images pixels
	 * @return the filtered colors
	 */
	private static Color[] makeItYellow(Color[] colors) {
		for(int i = 0; i < colors.length; i++) {
			int red = colors[i].getRed();
			int green = colors[i].getGreen();
			colors[i] = new Color(red, green, 0);
		}
		return colors;
	}
	
	/**
	 * Filters an image violet
	 * @param colors is the Color-Array if the images pixels
	 * @return the filtered colors
	 */
	private static Color[] makeItViolet(Color[] colors) {
		for(int i = 0; i < colors.length; i++) {
			int red = colors[i].getRed();
			int blue = colors[i].getBlue();
			colors[i] = new Color(red, 0, blue);
		}
		return colors;
	}
	
	/**
	 * Filters an image aqua
	 * @param colors is the Color-Array if the images pixels
	 * @return the filtered colors
	 */
	public static Color[] makeItAqua(Color[] colors) {
		for(int i = 0; i < colors.length; i++) {
			int green = colors[i].getGreen();
			int blue = colors[i].getBlue();
			colors[i] = new Color(0, green, blue);
		}
		return colors;
	}
}