package controller;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

import database.SendSQLRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Album;
import model.Database;

/**
 * Class to add albums to the database.
 * @author Mario Anklam
 */
public class AlbumController extends MainController implements Initializable {

	@FXML ListView<Album> listView;
	@FXML TextField albumName;
	@FXML Button exitId;

	Database database = new Database();
	private MainController mainController;
	
	/**
	 * This method closes the window and lets the user return to the main screen.
	 * @throws ParseException
	 */
	@FXML
	private void exitButtonpressed() throws ParseException {
		Stage stage = (Stage) exitId.getScene().getWindow();
		stage.close();
		
		// depending on the type of mainController, different initializations have to be made
		mainController.initializeListView();
		if (mainController instanceof MainControllerGalleryMode) {
			MainControllerGalleryMode mc = (MainControllerGalleryMode) mainController;
			mc.initializeTilePane();
		}
	}
	
	/**
	 * This method adds an album to the database.
	 * @throws SQLException
	 */
	@FXML
	private void newAlbumpressed() throws SQLException {

		// don't allow empty inputs or the reserved album name "All Images"
		if (albumName.getText().equals("") || albumName.getText().equals("All Images")) {
			return;
		}
		
		String tmp = albumName.getText();
		String query = "INSERT INTO ALBEN (Name) VALUES ('" + albumName.getText() + "');";
		SendSQLRequest.sendSQL(query);
		System.out.println(tmp);
		albumName.clear();
		listView.getItems().clear();
		database.reloadDatabaseContents();
		database.getAlbums().stream().forEach(album -> listView.getItems().add(album));
	}

	/**
	 * This method initializes the controller.
	 * @param arg0 inhereted from superclass
	 * @param arg1 inhereted from superclass
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		database.reloadDatabaseContents();
		database.getAlbums().stream().forEach(album -> listView.getItems().add(album));
		controllerCheck("AddAlbum");
	}

	/**
	 * This method gives the albumcontroller an instance of MainController.
	 * @param mainController the instance of MainController that wants to add albums
	 */
	public void injectMainController(MainController mainController) {
		this.mainController = mainController;
	}
}
