package editingJUnit;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import model.editing.ColorFilter;

/**
 * JUnit tests for colorfiltering
 * @author Julian Einspenner
 */
public class ColorFilterTests {

	static int[] colorArrayPlainInt;
	static int[] colorArrayAquaInt;
	static int[] colorArrayRedInt;
	static int[] colorArrayBlueInt;
	
	static Color[] colorArrayPlain;
	static Color[] colorArrayAqua;
	static Color[] colorArrayRed;
	static Color[] colorArrayBlue;
	
	static Color[] filteredColors; 
	
	/*
	 * Used rgb-Values for Plain-Array:
	 * 247 230 120, 2 1 43, 255 255 255, 0 0 0, 44 55 66
	 * 13 0 0, 4 32 32, 55 128 128, 1 1 1, 120 120 120 
	 */
	
	
	@Before
	public void setUpBefore() {
		//Input
		colorArrayPlainInt = new int[] {16246392, 131371, 16777215, 0, 2897730, 851968, 270368, 3637376, 65793, 7895160};
		
		//Expected output
		colorArrayAquaInt  = new int[] {59000, 299, 65535, 0, 14146, 0, 8224, 32896, 257, 30840};
		colorArrayRedInt   = new int[] {16187392, 131072, 16711680, 0, 2883584, 851968, 262144, 3604480, 65536, 7864320};
		colorArrayBlueInt  = new int[] {120, 43, 255, 0, 66, 0, 32, 128, 1, 120};
		
		colorArrayPlain = new Color[colorArrayPlainInt.length];
		for(int i = 0; i < colorArrayPlain.length; i++) {
			colorArrayPlain[i] = new Color(colorArrayPlainInt[i]);
		}
		
		colorArrayAqua = new Color[colorArrayPlainInt.length];
		for(int i = 0; i < colorArrayPlain.length; i++) {
			colorArrayAqua[i] = new Color(colorArrayAquaInt[i]);
		}
		
		colorArrayRed = new Color[colorArrayPlainInt.length];
		for(int i = 0; i < colorArrayPlain.length; i++) {
			colorArrayRed[i] = new Color(colorArrayRedInt[i]);
		}
		
		colorArrayBlue = new Color[colorArrayPlainInt.length];
		for(int i = 0; i < colorArrayPlain.length; i++) {
			colorArrayBlue[i] = new Color(colorArrayBlueInt[i]);
		}
	}
	
	@Test
	public void aquaFilterTest() {
		filteredColors = ColorFilter.makeItAqua(colorArrayPlain);
		
		boolean b = true;
		for(int i = 0; i < filteredColors.length; i++) {
			if(!filteredColors[i].equals(colorArrayAqua[i])) {
				b = false;
				break;
			}
		}
		
		assertEquals(true, b);
	}
	
	@Test
	public void redFilterTest() {
		filteredColors = ColorFilter.makeItRed(colorArrayPlain);
		
		boolean b = true;
		for(int i = 0; i < filteredColors.length; i++) {
			if(!filteredColors[i].equals(colorArrayRed[i])) {
				b = false;
				break;
			}
		}
		assertEquals(true, b);
	}
	
	@Test
	public void blueFilterTest() {
		filteredColors = ColorFilter.makeItBlue(colorArrayPlain);
		
		boolean b = true;
		for(int i = 0; i < filteredColors.length; i++) {
			if(!filteredColors[i].equals(colorArrayBlue[i])) {
				b = false;
				break;
			}
		}
		assertEquals(true, b);
	}
	
}
