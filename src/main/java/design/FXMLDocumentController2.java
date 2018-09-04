package design;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.DragEvent;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class FXMLDocumentController2 implements Initializable{
	@FXML
	private ImageView images;
	private AnchorPane rootPane;
	private Pane paneView;
	
/*	
	@FXML public ResourceBundle rb;
	@FXML public URL url;
*/
	
//	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

	@FXML
	private void upButtonPressed() {
		System.out.println("This is the 'Up' button");
	}
	
	@FXML
	private void browseButtonPressed() {
		System.out.println("I am the browseButtonPressed function");
	}
	
	@FXML
	private void fullScreenButtonPressed() {
		System.out.println("I am the fullScreenButtonPressed function");
	}
	
	@FXML
	private void gridButtonPressed() {
		System.out.println("I am the gridButtonPressed function");
	}
	
	@FXML
	private void newFolderButtonPressed() {
		System.out.println("I am the newFolderButtonPressed function");
	}

	@FXML
	private void filterButtonPressed() {
		System.out.println("I am the filterButtonPressed function");
	}
	
	@FXML
	private void renameAllButtonPressed() {
		System.out.println("I am the renameAllButtonPressed function");
	}
	
	@FXML
	private void synchronizeButtonPressed() {
		System.out.println("I am the synchronizeButtonPressed function");
	}
	
	@FXML
	private void autoSynchroButtonSet() {
		System.out.println("I am the autoSynchroButtonSet function");
	}
	
	@FXML
	private void dropdownButtonChoiceSelected() {
		System.out.println("I am the dropdownButtonChoiceSelected function");
	}
//______________________________________________________________

	@FXML
	private void sliderMove() {
		System.out.println("I am the sliderMove function");
	}
	
//______________________________________________________________

// Menue-Bar----------------------------------------------------
	
	//File
	@FXML
	private void importImage() {
		System.out.println("I am the importImage function");
	}
	
//	@FXML
//	private void cutImage() {
//
//	} Already exist -> Vermeide doppelte Klassen
	
	@FXML
	private void copyImage() {
		System.out.println("I am the copyImage function");
	}
	
	@FXML
	private void renameImage() {
		System.out.println("I am the renameImage function");
	}
	
	@FXML
	private void deleteImage() {
		System.out.println("I am the deleteImage function");
	}
	
	@FXML
	private void saveImage() {
		System.out.println("I am the saveImage function");
	}
	
	@FXML
	private void saveImageAs() {
		System.out.println("I am the saveImageAs function");
	}
	
	@FXML
	private void exit() {
		System.out.println("I am the Exit function");
	}
	//-----------------------
	
	//Edit
	@FXML
	private void blackWhite() {
		System.out.println("I am the blackWhite function");
	}	
	
	@FXML
	private void cutImage() {
		System.out.println("I am the cutImage function");
	}	
	
	@FXML
	private void resizeImage() {
		System.out.println("I am the resizeImage function");
	}
	
	//---------------------------
	
	//Organize

	@FXML
	private void searchImage() {
		System.out.println("I am the searchImage function");
	}
	
	@FXML
	private void filterImages() {
		System.out.println("I am the filterImages function");
	}	
	
	@FXML
	private void sortImages() {
		System.out.println("I am the sortImages function");
	}	
	
	//---------------------------
	
	//View
	
	@FXML
	private void showDetail() {
		System.out.println("I am the showDetail function");
	}	
	
//	@FXML
//	private void showPreview() {
//
//	}	Fullscreen?
	
	@FXML
	private void navigator() {
		System.out.println("I am the navigator function");
	}	
	
	@FXML
	private void information() {
		System.out.println("I am the information function");
	}
	
	//----------------------------
	
	//Help
	@FXML
	private void showCommands() {
		System.out.println("I am the showCommands function");
	}	
	
	@FXML
	private void changeLanguage() {
		System.out.println("I am the changeLanguage function");
	}	
	
	//--------------------------------------------------------
	
	//Edit Mode
	@FXML
	private void rotateClockwise() {
		BufferedImage img = null;
		//get image to img
		model.Rotater.rotateClockwise(img);
		//return img to ImagePane
	}
	
	@FXML
	private void rotateCounterClockwise() {
		BufferedImage img = null;
		//get image to img
		model.Rotater.rotateAntiClockwise(img);
		//return img to ImagePane
	}
	
	@FXML
	private void undo() {
		System.out.println("I am the undo function");
	}
	
	@FXML
	private void saveMetadata() {
		System.out.println("I am the saveMetadata function");
	}	
	
	@FXML
	private void filterColor() {
		System.out.println("I am the filterColor function");
	}	
	
	@FXML
	private void monochroneButtonPressed() {
		BufferedImage img = null;
		//get image to img
		model.GrayScaler.grayScaleImage(img);
		//return img to ImagePane
	}
	
	@FXML
	private void removeFilterButtonPressed() {
		System.out.println("I am the removeFilter function");
	}
	
	// TEST
	@FXML
	private void loadImage() {
		System.out.println("What");
		paneView.getChildren().clear();
		Image image = new Image ("file///C:/Users/Tobi/Pictures/FFDP_Logo.jpg");
		System.out.println("What");
		javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
		paneView.getChildren().add(imageView);
		
	}
}