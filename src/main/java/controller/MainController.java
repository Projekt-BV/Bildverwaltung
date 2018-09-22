package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Properties;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Album;
import model.Database;
import model.editing.FileImport;
import java.io.FileInputStream;
import java.util.Properties;
import java.io.File;

public abstract class MainController {

	@FXML
	ListView<Album> listView;
	@FXML
	AnchorPane rootPane;

	Database database = new Database();

	static Album selectedAlbum;
	static boolean didSwitchBack = true;
	String currentLanguage = "en";
	
	
	/**
	 * Method to initialize the listView containing the album names.
	 * 
	 * @throws ParseException
	 */
	public void initializeListView() {
		listView.getItems().clear();
		database.reloadDatabaseContents();
		database.getAlbums().stream().forEach(album -> listView.getItems().add(album));

		// highlight the selected album
		Album referenceEqualAlbum;
		if (selectedAlbum != null) {
			referenceEqualAlbum = listView.getItems().stream()
					.filter(album -> album.getName().equals(selectedAlbum.getName())).findFirst().get();
		} else {
			// if no album is selected, highlight "All Images"
			referenceEqualAlbum = listView.getItems().stream().filter(album -> album.getName().equals("All Images"))
					.findFirst().get();
		}
		listView.getSelectionModel().select(referenceEqualAlbum);

	}

	public Database getDatabase() {
		return this.database;
	}

	// Navigation between the main controllers
	@FXML
	private void switchScene(Event event) throws IOException {
		Parent pane = FXMLLoader.load(getClass().getResource("/design/Main_page_edit_mode.fxml"));
		Scene changePane = new Scene(pane);

		// Show stage information
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(changePane);
		window.show();
	}

	@FXML
	private void switchBack(Event event) throws IOException {
		selectedAlbum = listView.getSelectionModel().getSelectedItem();
		didSwitchBack = true;

		Parent pane = FXMLLoader.load(getClass().getResource("/design/Main_page_2.4.fxml"));
		Scene changePane = new Scene(pane);
		// Show stage information

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(changePane);
		window.show();
	}

	// Menue-Bar----------------------------------------------------

	// File
	@FXML
	private void importImage() {
		FileImport tmpImp = new FileImport();
		Stage window = new Stage();
		tmpImp.injectMainController((MainControllerGalleryMode) this);
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

	// Help
	@FXML
	private void showCommands() {
		System.out.println("I am the showCommands function");
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
		albumController.injectMainController((MainControllerGalleryMode) this);

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
	private void fullScreenButtonPressed() throws IOException {
		if (this instanceof MainControllerEditMode) {
			Stage stage;
			Parent root;
			stage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/design/FullScreen.fxml"));
			root = loader.load();

			stage.setScene(new Scene(root));
			stage.setFullScreen(true);
			stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

			FullScreenController fullScreenController = loader.getController();
			fullScreenController.injectMainController((MainControllerEditMode) this);
			fullScreenController.setExitListener();

			stage.show();
		} else {
			Stage stage = (Stage) rootPane.getScene().getWindow();
			stage.setFullScreen(true);
		}
	}

	// Bar above gridPane / ImageView
	@FXML
	private void dropdownButtonChoiceSelected() {
		System.out.println("I am the dropdownButtonChoiceSelected function");
	}

	  /**
		 * Allows to switch between languages
		 * @author Tobias Reinert
	     * @throws IOException 
		 */
		@FXML
		public void changeLanguage() throws IOException {
			Properties config;
			config = new Properties();
			FileInputStream fis;
		//	FileInputStream fis2;
			
			if (currentLanguage == "en") {
				currentLanguage = "de";
			} else {
				currentLanguage = "en";
			}
				
			try {
			fis = new FileInputStream("C:\\Users\\Tobi\\eclipse-workspace\\Bildverwaltung\\src\\main\\resources\\design\\LangBundle_"+currentLanguage+".properties");
		//	fis2 = new FileInputStream(System.getProperty("user.dir")+
		//			"\\eclipse-workspace\\Bildverwaltung\\src\\main\\resources\\design\\LangBundle_de.properties");
			config.load(fis);
			
			} catch (IOException io) {
				io.printStackTrace();
			}
			System.out.println(config.getProperty("MenuBar-Language"));
		}
}
