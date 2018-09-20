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
	static boolean didSwitchBack = true;
	
	/** 
	 * Method to initialize the listView containing the album names.
	 * @throws ParseException 
	 */
	public void initializeListView() throws ParseException {
		
		listView.getItems().clear();
		database.reloadDatabaseContents();			
		database.getAlbums().stream()
							.forEach(album -> listView.getItems().add(album));
		
		// highlight the selected album		
		Album referenceEqualAlbum;
		if (selectedAlbum != null) {
			referenceEqualAlbum = listView.getItems().stream()
											         .filter(album -> album.getName().equals(selectedAlbum.getName()))
												     .findFirst()
											         .get();
		} else {
			// if no album is selected, highlight "All Images"
			referenceEqualAlbum = listView.getItems().stream()
			         .filter(album -> album.getName().equals("All Images"))
				     .findFirst()
			         .get();
		}
		listView.getSelectionModel().select(referenceEqualAlbum);

		
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
		private void importImage() throws ParseException {
			FileImport tmpImp = new FileImport();
			Stage window = new Stage();
			tmpImp.injectMainController((MainControllerGalleryMode)this);
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
		private void addAlbumButtonPressed() throws IOException {
			Stage stage;
			Parent root;
			stage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/design/NewAlbum.fxml"));
			root = loader.load();
			
			AlbumController albumController = loader.getController();
			albumController.injectMainController((MainControllerGalleryMode)this);
			
			stage.setScene(new Scene(root));
			stage.setTitle("New Album");
			stage.show();
			
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
	

