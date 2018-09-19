package editingJUnit;

import static org.junit.Assert.*;
import model.editing.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RotaterTests {
	static BufferedImage image200x200;
	static BufferedImage image200x200_clockwise;
	static BufferedImage image200x200_anticlockwise;
	
	static int[] pixelArrayPlain;
	static int[] pixelArrayClockwise;
	static int[] pixelArrayAntiClockwise;
	static int[] pixelArrayHeadstand;
	
	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		image200x200 = ImageIO.read(new File("images/200x200.jpg"));
		image200x200_clockwise = ImageIO.read(new File("images/200x200_clockwise.jpg"));
		image200x200_anticlockwise = ImageIO.read(new File("images/200x200_anticlockwise.jpg"));
	}
	
	@Before
	public void setUpBefore() {
		pixelArrayPlain         = new int[]{5, 5, 5, 5, 5, 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1};
		pixelArrayClockwise     = new int[]{1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5};
		pixelArrayAntiClockwise = new int[]{5, 4, 3, 2, 1, 5, 4, 3, 2, 1, 5, 4, 3, 2, 1, 5, 4, 3, 2, 1, 5, 4, 3, 2, 1};
		pixelArrayHeadstand     = new int[]{1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5};
	}
	
	@Test
	public void rotatePixelArrayClockwiseTest() {
		pixelArrayPlain = Rotater.rotateArrayClockwise(pixelArrayPlain, 5, 5);
		assertArrayEquals(pixelArrayClockwise, pixelArrayPlain);
	}
	
	@Test
	public void rotatePixelArrayAntiClockwiseTest() {
		pixelArrayPlain = Rotater.rotateArrayAntiClockwise(pixelArrayPlain, 5, 5);
		assertArrayEquals(pixelArrayAntiClockwise, pixelArrayPlain);
	}
	
	@Test
	public void rotateToTimesClockwiseTest() {
		pixelArrayPlain = Rotater.rotateArrayClockwise(pixelArrayPlain, 5, 5);
		pixelArrayPlain = Rotater.rotateArrayClockwise(pixelArrayPlain, 5, 5);
		assertArrayEquals(pixelArrayHeadstand, pixelArrayPlain);
	}
	
	@Test
	public void rotateToTimesAntiClockwiseTest() {
		pixelArrayPlain = Rotater.rotateArrayAntiClockwise(pixelArrayPlain, 5, 5);
		pixelArrayPlain = Rotater.rotateArrayAntiClockwise(pixelArrayPlain, 5, 5);
		assertArrayEquals(pixelArrayHeadstand, pixelArrayPlain);
	}
	
//	@Test
//	public void rotateClockwiseTest() throws InterruptedException {
//		BufferedImage outputImage = model.editing.Rotater.rotateClockwise(image200x200);
//		
//		assertEquals(true, b);
//	}
	
	
	
	
	//Helps to print a pixel array for visual feedback
	private void printArray(int[] array, int width) {
		for(int i = 0; i < array.length; i++) {
			if(i % width == 0) {
				System.out.print("\n");
			}
			System.out.print(array[i] + " ");
			
		}
	}
}
