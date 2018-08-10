package design;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import java.io.IOException;


public class FXMLDocumentController implements Initializable {
	
	@FXML
	private AnchorPane rootPane;
	
/*	
	@FXML public ResourceBundle rb;
	@FXML public URL url;
*/
	
//	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}
	
	@FXML
	private void switchScene(ActionEvent event) throws IOException{
		boolean pageCheck = false;
		
		if (pageCheck == false) {
			pageCheck = true;
			AnchorPane pane = FXMLLoader.load(getClass().getResource("/design/Main_page_edit_mode.fxml"));
			rootPane.getChildren().setAll(pane);
			
		} else {
			pageCheck = false;
			AnchorPane pane = FXMLLoader.load(getClass().getResource("/design/Main_page_2.4.fxml"));
			rootPane.getChildren().setAll(pane);
		}
		
	}
	
	@FXML
	private void upButtonPressed() {

	}
	
	@FXML
	private void browseButtonPressed() {

	}
	
	@FXML
	private void fullScreenButtonPressed() {

	}
	
	@FXML
	private void gridButtonPressed() {
		
	}
	
	@FXML
	private void newFolderButtonPressed() {

	}

	@FXML
	private void filterButtonPressed() {

	}
	
	@FXML
	private void renameAllButtonPressed() {

	}
	
	@FXML
	private void synchronizeButtonPressed() {

	}
	
	@FXML
	private void autoSynchroButtonSet() {

	}
	
	@FXML
	private void dropdownButtonPressed() {

	}
//______________________________________________________________

	@FXML
	private void sliderMove() {

	}
	
//______________________________________________________________

// Menue-Bar----------------------------------------------------
	
	//File
	@FXML
	private void importImage() {

	}
	
//	@FXML
//	private void cutImage() {
//
//	} Already exist -> Vermeide doppelte Klassen
	
	@FXML
	private void copyImage() {

	}
	
	@FXML
	private void renameImage() {

	}
	
	@FXML
	private void deleteImage() {

	}
	
	@FXML
	private void saveImage() {

	}
	
	@FXML
	private void saveImageAs() {

	}
	//-----------------------
	
	//Edit
	@FXML
	private void rotateLeft() {

	}
	
	@FXML
	private void rotateRight() {

	}
	
	@FXML
	private void blackWhite() {

	}	
	
	@FXML
	private void cutImage() {

	}	
	
	@FXML
	private void resizeImage() {

	}
	//---------------------------
	
	//Organize
//	@FXML
//	private void newAlbum() {
//
//	}	newFolderButtonPressed
	
	@FXML
	private void searchImage() {

	}
	
	@FXML
	private void filterImages() {

	}	
	
	@FXML
	private void sortImages() {

	}	
	
	//---------------------------
	
	//View
	
	@FXML
	private void showDetail() {

	}	
	
//	@FXML
//	private void showPreview() {
//
//	}	Fullscreen?
	
	@FXML
	private void navigator() {

	}	
	
	@FXML
	private void information() {

	}
	
	//----------------------------
	
	//Help
	@FXML
	private void showCommands() {

	}	
	
	@FXML
	private void changeLanguage() {

	}	
	
	//--------------------------------------------------------
	
	//Edit Mode
	@FXML
	private void undo() {

	}
	
	@FXML
	private void saveMetadata() {

	}	
}
