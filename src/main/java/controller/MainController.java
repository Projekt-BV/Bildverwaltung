package controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Album;
import model.Database;
import model.editing.FileImport;

public abstract class MainController {

	@FXML ListView<Album> listView;
	@FXML AnchorPane rootPane;	
	
	Database database = new Database();
	ArrayList<Image> imagesInSelectedAlbum = new ArrayList<Image>();
	static Album selectedAlbum;
	static boolean didSwitchBack = false;
	
	/** 
	 * Method to initialize the listView containing the album names.
	 * @throws ParseException 
	 */
	public void initializeListView() throws ParseException {
		
		database.reloadDatabaseContents();			
		database.getAlbums().stream()
							.forEach(album -> listView.getItems().add(album));		
	}
	
	public Database getDatabase() {
		return this.database;
	}
	
	
	// Navigation between the main controllers
	@FXML
	private void switchScene(Event event) throws IOException{
			Parent pane = FXMLLoader.load(getClass().getResource("/design/Main_page_edit_mode.fxml"));
			Scene changePane = new Scene(pane);
	
			//Show stage information
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(changePane);
			window.show();
	}
			
	@FXML
	private void switchBack(Event event) throws IOException{
			selectedAlbum = listView.getSelectionModel().getSelectedItem(); 
			didSwitchBack = true;

			Parent pane = FXMLLoader.load(getClass().getResource("/design/Main_page_2.4.fxml"));
			Scene changePane = new Scene(pane);
			//Show stage information
			
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(changePane);
			window.show();
	}
	
	// Menue-Bar----------------------------------------------------
	
		//File
		@FXML
		private void importImage() {
			FileImport tmpImp = new FileImport();
			Stage window = new Stage();
			tmpImp.start(window);
		}
	
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
			
		//Help
		@FXML
		private void showCommands() {
			System.out.println("I am the showCommands function");
		}	
			
		@FXML
		private void changeLanguage() {
			System.out.println("I am the changeLanguage function");
		}	
		
		
		// Buttons below listView
		@FXML
		private void addAlbumButtonPressed() {
			System.out.println("I am the addAlbumButtonPressed function");
		}
		
		@FXML
		private void deleteAlbumButtonPressed() {
			System.out.println("I am the deleteAlbumButtonPressed function");
		}
			
		
		// Bar below Menubar
		
		@FXML
		private void browseButtonPressed() {
			System.out.println("I am the browseButtonPressed function");
		}
		
		@FXML
		private void fullScreenButtonPressed() {
			System.out.println("I am the fullScreenButtonPressed function");
		}
		
		
		// Bar above gridPane / ImageView
		@FXML
		private void dropdownButtonChoiceSelected() {
			System.out.println("I am the dropdownButtonChoiceSelected function");
		}

}
	

