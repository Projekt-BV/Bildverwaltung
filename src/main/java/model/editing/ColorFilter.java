package model.editing;

import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.*;
import java.awt.Color;


/**
 * This class enables to filter colors of an image
 * @author Julian Einspenner
 */
public class ColorFilter {
	
	/**
	 * @author Julian Einspenner
	 * @param img is the image which is going to be colorfiltered
	 * @param colorSelection is the color the image should have after filtering (possible strings: red, green, blue, yellow, violet, aqua)
	 * @return
	 */
	public static Image filterCoulours(Image img, String colorSelection) {
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
		
		colorSelection = colorSelection.toLowerCase();
		
		switch(colorSelection) {
		
			case("red"): 
				colors = makeItRed(colors);
				break;
			case("blue"): 
				colors = makeItBlue(colors);
				break;
			case("green"): 
				colors = makeItGreen(colors);
				break;
			case("yellow"): 
				colors = makeItYellow(colors);
				break;
			case("violet"): 
				colors = makeItViolet(colors);
				break;
			case("aqua"): 
				colors = makeItAqua(colors);
				break;
			default:
				System.err.println("Bad colour selection");
				return img;
		}
		
		int[] rgbArray = new int[colors.length];
		
		for(int i = 0; i < colors.length; i++) {
			rgbArray[i] = colors[i].getRGB();
		}
		
		bimg.setRGB(0, 0, w, h, rgbArray, 0, w);
		
		return SwingFXUtils.toFXImage(bimg, null);
	}
		
		
	public static Color[] makeItRed(Color[] colors) {
		for(int i = 0; i < colors.length; i++) {
			int red = colors[i].getRed();
			colors[i] = new Color(red, 0, 0);
		}
		return colors;
	}
	
	private static Color[] makeItGreen(Color[] colors) {
		for(int i = 0; i < colors.length; i++) {
			int green = colors[i].getGreen();
			colors[i] = new Color(0, green, 0);
		}
		return colors;
	}
	
	public static Color[] makeItBlue(Color[] colors) {
		for(int i = 0; i < colors.length; i++) {
			int blue = colors[i].getBlue();
			colors[i] = new Color(0, 0, blue);
		}
		return colors;
	}
	
	private static Color[] makeItYellow(Color[] colors) {
		for(int i = 0; i < colors.length; i++) {
			int red = colors[i].getRed();
			int green = colors[i].getGreen();
			colors[i] = new Color(red, green, 0);
		}
		return colors;
	}
	
	private static Color[] makeItViolet(Color[] colors) {
		for(int i = 0; i < colors.length; i++) {
			int red = colors[i].getRed();
			int blue = colors[i].getBlue();
			colors[i] = new Color(red, 0, blue);
		}
		return colors;
	}
	
	public static Color[] makeItAqua(Color[] colors) {
		for(int i = 0; i < colors.length; i++) {
			int green = colors[i].getGreen();
			int blue = colors[i].getBlue();
			colors[i] = new Color(0, green, blue);
		}
		return colors;
	}
}