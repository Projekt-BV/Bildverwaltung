package model.editing;

import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.paint.Color;


public class Filters {
	
	
	public static Image filters(String color, ImageView displayImageEditMode) {
		
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(-1.0);
        
		switch(color) {
		
			case("RED"):
				Blend red = new Blend(
					BlendMode.MULTIPLY,
					monochrome,
					new ColorInput(
							0,
							0,
							displayImageEditMode.getFitWidth(),
							displayImageEditMode.getFitHeight(),
							Color.RED
							)
					);
				displayImageEditMode.setEffect(red);
			break;
			
			
			case("BLUE"):
				Blend blue = new Blend(
					BlendMode.MULTIPLY,
					monochrome,
					new ColorInput(
							0,
							0,
							displayImageEditMode.getImage().getWidth(),
							displayImageEditMode.getImage().getHeight(),
							Color.BLUE
							)
					);
				displayImageEditMode.setEffect(blue);
			break;
			
			
			case("GREEN"):
				Blend green = new Blend(
					BlendMode.MULTIPLY,
					monochrome,
					new ColorInput(
							0,
							0,
							displayImageEditMode.getImage().getWidth(),
							displayImageEditMode.getImage().getHeight(),
							Color.GREEN
							)
					);
				displayImageEditMode.setEffect(green);
			break;
			
			
			case("YELLOW"):
				Blend yellow = new Blend(
					BlendMode.MULTIPLY,
					monochrome,
					new ColorInput(
							0,
							0,
							displayImageEditMode.getImage().getWidth(),
							displayImageEditMode.getImage().getHeight(),
							Color.YELLOW
							)
					);
				displayImageEditMode.setEffect(yellow);
			break;
				
			
			case("VIOLET"):
				Blend violet = new Blend(
					BlendMode.MULTIPLY,
					monochrome,
					new ColorInput(
							0,
							0,
							displayImageEditMode.getImage().getWidth(),
							displayImageEditMode.getImage().getHeight(),
							Color.VIOLET
							)
						);
			
				displayImageEditMode.setEffect(violet);
			break;		
			
			
			case("AQUA"):
				Blend aqua = new Blend(
					BlendMode.MULTIPLY,
					monochrome,
					new ColorInput(
							0,
							0,
							displayImageEditMode.getImage().getWidth(),
							displayImageEditMode.getImage().getHeight(),
							Color.AQUA
							)
					);
				displayImageEditMode.setEffect(aqua);
				
				//hoverfunction
//			displayImageEditMode.effectProperty().bind(
//					Bindings
//			        	.when(displayImageEditMode.hoverProperty())
//				        .then((Effect) aqua)
//				        .otherwise((Effect) null)
//				);
		}

	displayImageEditMode.setCache(true);
	displayImageEditMode.setCacheHint(CacheHint.SPEED);
	
	return displayImageEditMode.getImage();
	}
	
	
	/**
	 * 
	 * @param img
	 * @return
	 */
	public static Image filterCoulours(Image img, String colorSelection) {
		BufferedImage bimg = SwingFXUtils.fromFXImage(img, null);
		
		int w = bimg.getWidth();
		int h = bimg.getHeight();
		
		java.awt.Color[] colors = new java.awt.Color[w * h];
		
		int z = 0;
		for(int i = 0; i < h; i++) {
			for(int j = 0; j < w; j++) {
				colors[z++] = new java.awt.Color(bimg.getRGB(j, i));
			}
		}
		
		colorSelection = colorSelection.toLowerCase();
		
		switch(colorSelection) {
		
			case("red"): 
				colors = makeItRed(colors);
				break;
			case("blue"): 
				makeItBlue(colors);
				break;
			case("green"): 
				makeItGreen(colors);
				break;
			case("yellow"): 
				makeItYellow(colors);
				break;
			case("violet"): 
				makeItViolet(colors);
				break;
			case("aqua"): 
				makeItAqua(colors);
				break;
			default:
				System.err.println("Bad colour selection");
				return img;
		}
		
		int[] rgbArray = new int[colors.length];
		
		for(int i = 0; i < colors.length; i++) {
			rgbArray[i] = colors[i].getRGB();
			System.out.println(i);
		}
		
		bimg.setRGB(0, 0, w, h, rgbArray, 0, w);
		
		return SwingFXUtils.toFXImage(bimg, null);
	}
		
		
	private static java.awt.Color[] makeItRed(java.awt.Color[] colors) {
		for(int i = 0; i < colors.length; i++) {
			int red = colors[i].getRed();
			colors[i] = new java.awt.Color(red, 0, 0);
		}
		return colors;
	}
	
	private static java.awt.Color[] makeItGreen(java.awt.Color[] colors) {
		for(int i = 0; i < colors.length; i++) {
			int green = colors[i].getGreen();
			colors[i] = new java.awt.Color(0, green, 0);
		}
		return colors;
	}
	
	private static java.awt.Color[] makeItBlue(java.awt.Color[] colors) {
		for(int i = 0; i < colors.length; i++) {
			int blue = colors[i].getBlue();
			colors[i] = new java.awt.Color(0, 0, blue);
		}
		return colors;
	}
	
	private static java.awt.Color[] makeItYellow(java.awt.Color[] colors) {
		for(int i = 0; i < colors.length; i++) {
			int red = colors[i].getRed();
			int green = colors[i].getGreen();
			colors[i] = new java.awt.Color(red, green, 0);
		}
		return colors;
	}
	
	private static java.awt.Color[] makeItViolet(java.awt.Color[] colors) {
		for(int i = 0; i < colors.length; i++) {
			int red = colors[i].getRed();
			int blue = colors[i].getBlue();
			colors[i] = new java.awt.Color(red, 0, blue);
		}
		return colors;
	}
	
	private static java.awt.Color[] makeItAqua(java.awt.Color[] colors) {
		for(int i = 0; i < colors.length; i++) {
			int green = colors[i].getGreen();
			int blue = colors[i].getBlue();
			colors[i] = new java.awt.Color(0, green, blue);
		}
		return colors;
	}
}