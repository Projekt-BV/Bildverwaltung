package model.editing;

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
}