package editingJUnit;

import static org.junit.Assert.assertArrayEquals;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.junit.Before;
import org.junit.Test;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import model.editing.ColorFilter;
import model.editing.UtilsForImageHandling;

/**
 * JUnit tests for colorfiltering
 * @author Julian Einspenner
 *
 */
public class ColorFilterTests {

	static int[] colorFilterImage;
	static int[] pixelArrayAqua;
	
	static BufferedImage bimg;
	
	@Before
	public void setUpBefore() {
		colorFilterImage = new int[] {16246392, 131371, 16777215, 0, 2897730, 851968, 270368, 3637376, 65793, 7895160};
		pixelArrayAqua = new int[]{59110, 299, 65535, 0, 14146, 0, 8224, 32896, 257, 30840};
		
		bimg = new BufferedImage(5, 2, BufferedImage.TYPE_INT_RGB);
		bimg.setRGB(0, 0, 5, 2, colorFilterImage, 0, 5);
		
		int [] filteredPixelArray = UtilsForImageHandling.getPixelArray(bimg);
		System.out.println("Dieses Array soll gefiltert werden");
		Utils.printArray(filteredPixelArray, 5);
	}
	
	@Test
	public void aquaFilterTest() {
		Image img = SwingFXUtils.toFXImage(bimg, null);
		img = ColorFilter.filterCoulours(img, "aqua");
		
		bimg = SwingFXUtils.fromFXImage(img, null);
		
		int [] filteredPixelArray = UtilsForImageHandling.getPixelArray(bimg);
		filteredPixelArray = bimg.getRGB(0, 0, 5, 2, filteredPixelArray, 0, 5);
		
		Utils.printArray(colorFilterImage, 5);
		Utils.printArray(filteredPixelArray, 5);
		
		assertArrayEquals(pixelArrayAqua, filteredPixelArray);
	}
	
}
