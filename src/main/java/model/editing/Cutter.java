package model.editing;

import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;


/**
 * Class for cutting an image to select a preferred area of it
 * @author Julian Einspenner
 */
public class Cutter {
	
	public static int x1, y1;

	private static double fitWidth;
	private static double fitHeight;
	private static double imageWidth;
	private static double imageHeight;
	
	/**
	 * Initializes data to calculate from coordinates by clicking on the image to the correct position of the pixelArray
	 * @param fitWidth width of the drawable are of the imageView
	 * @param fitHeight height of the drawable are of the imageView
	 * @param imageWidth width of the image
	 * @param imageHeight height of the image
	 */
	public static void initValues(double fitWidth, double fitHeight, double imageWidth, double imageHeight) {
		Cutter.fitWidth = fitWidth;
		Cutter.fitHeight = fitHeight;
		Cutter.imageWidth = imageWidth;
		Cutter.imageHeight = imageHeight;
	}
	
	/**
	 * Used to cut an image with a compressed width
	 * @param e is the mouse event for getting the coordinates of the click at the imageView
	 */
	public static void cutCaseOne(MouseEvent e) {
		double compressFactor = imageHeight / fitHeight;
		double rightestCoord = imageWidth / compressFactor;
		double coordWidthRatio = e.getX() / rightestCoord;
		
		x1 = (int)(coordWidthRatio * imageWidth);
		y1 = (int) (e.getY() / fitHeight * imageHeight);
	}
	
	/**
	 * Used to cut an image with a compressed height
	 * @param e is the mouse event for getting the coordinates of the click at the imageView
	 */
	public static void cutCaseTwo(MouseEvent e) {
		double compressFactor = imageWidth / fitWidth;
		double lowestCoord = imageHeight / compressFactor;
		double coordHeightRatio = e.getY() / lowestCoord;
		y1 = (int) (coordHeightRatio * imageHeight);
		x1 = (int) (e.getX() / fitWidth * imageWidth);
	}
	
	/**
	 * Used to cut an image with a compressed width AND height
	 * @param e is the mouse event for getting the coordinates of the click at the imageView
	 */
	public static void cutCaseThree(MouseEvent e) {
		x1 = (int)(e.getX() / fitWidth * imageWidth);   
		y1 = (int)(e.getY() / fitHeight * imageHeight); 
	}
	
	/**
	 * Small images with coordinate == position in pixel Array. No conversion is needed
	 * @param e is the mouse event for getting the coordinates of the click at the imageView
	 */
	public static void cutCaseFour(MouseEvent e) {
		if(imageHeight >= imageWidth) {
			Cutter.cutCaseOne(e);
		}else {
			Cutter.cutCaseTwo(e);
		}
	}
	
	
	
	
	/**
	 * Cuts an image
	 * @param x1 builds with y1 the first point of an rectangle
	 * @param y1 builds with x1 the first point of an rectangle
	 * @param x2 builds with y2 the second point of an rectangle
	 * @param y2 builds with x2 the second point of an rectangle
	 * @param img is the Image
	 * @author Julian Einspenner
	 */
	public static Image cutImage(int x1, int y1, int x2, int y2, Image img) {
		BufferedImage bimg = SwingFXUtils.fromFXImage(img, null);
		
		int x0, w, h;
		
		if(x1 < x2) {
			x0 = x1;
			w  = x2 - x1;
		}else if(x1 > x2){
			x0 = x2;
			w  = x1 - x2;
		}else {
			return img;
		}
		
		int y0;
		if(y1 < y2) {
			y0 = y1;
			h  = y2 - y1;
		}else if(y1 > y2){
			y0 = y2;
			h  = y1 - y2;
		}else {
			return img;
		}
		
		BufferedImage bimg2 = bimg.getSubimage(x0, y0, w, h);
		
		return SwingFXUtils.toFXImage(bimg2, null);
	}
}
