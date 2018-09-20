package editingJUnit;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;

import org.junit.Before;
import org.junit.Test;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import model.editing.Cutter;
import model.editing.UtilsForImageHandling;

public class CutterJUnitTests {
	
	static int[] pixelInput = {0xff000001, 0xff000002, 0xff000003, 0xff000004, 0xff000005,
					     	   0xff000006, 0xff000007, 0xff000008, 0xff000009, 0xff000001,
							   0xff000002, 0xff000003, 0xff000004, 0xff000005, 0xff000006,
							   0xff000007, 0xff000008, 0xff000009, 0xff000001, 0xff000002,
							   0xff000003, 0xff000004, 0xff000005, 0xff000006, 0xff000007};
	
	static int[] pixelOutput = {0xff000007, 0xff000008, 0xff000009,
								0xff000003, 0xff000004, 0xff000005,
								0xff000008, 0xff000009, 0xff000001};
	
	static BufferedImage bimg;
								
	@Before
	public void setUpBefore() {
		bimg = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
		bimg.setRGB(0, 0, 5, 5, pixelInput, 0, 5);
	}
	
	@Test
	public void cutImageTest() {
		Image outImg = SwingFXUtils.toFXImage(bimg, null);
		outImg = Cutter.cutImage(1, 1, 4, 4, outImg);
		
		bimg = SwingFXUtils.fromFXImage(outImg, null);
		int[] actualArray = UtilsForImageHandling.getPixelArray(bimg);
		
		assertArrayEquals(pixelOutput, actualArray);
	}
	
	@Test
	public void cutImageWithXisSmallerThanYtest() {
		Image outImg = SwingFXUtils.toFXImage(bimg, null);
		outImg = Cutter.cutImage(4, 4, 1, 1, outImg);
		
		bimg = SwingFXUtils.fromFXImage(outImg, null);
		int[] actualArray = UtilsForImageHandling.getPixelArray(bimg);
		
		assertArrayEquals(pixelOutput, actualArray);
	}
	
	@Test
	public void cutImageWholeArrayTest() {
		Image outImg = SwingFXUtils.toFXImage(bimg, null);
		outImg = Cutter.cutImage(0, 0, 5, 5, outImg);
		
		bimg = SwingFXUtils.fromFXImage(outImg, null);
		int[] actualArray = UtilsForImageHandling.getPixelArray(bimg);
		
		assertArrayEquals(pixelInput, actualArray);
	}

}
