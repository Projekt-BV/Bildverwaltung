package controller;

import javafx.scene.layout.GridPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class GridController {
	GridPane grid = new GridPane();
	
	public void test() {
		Image image = new Image("C:\\Users\\Tobi\\Pictures\\198906.jpg");
		grid.getChildren().add(new ImageView(image));
	}
	
	public void sizeChange(int columns, String[] images) {
		for (int i = 0; i < images.length; i++) {
			Image image = new Image(images[i]);
			grid.getChildren().add(new ImageView(image));
		}
	}
	
	public static void main (String[] args) {
        new GridController().test();
	}
	
	//DATA
//	 GridPane gridpane = new GridPane();
//     gridpane.setPadding(new Insets(5));
//     gridpane.setHgap(10);
//     gridpane.setVgap(10);
//	pictureRegion.getChildren().add(imv);
//    gridpane.add(pictureRegion, 1, 1);
}
