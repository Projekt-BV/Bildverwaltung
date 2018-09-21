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

public class AlbumController implements Initializable{

	@FXML ListView<Album> listView;
	@FXML TextField albumName;
	@FXML Button exitId;
	

	
	Database database = new Database();
	private MainControllerGalleryMode mainController;
	
	@FXML
	private void exitButtonpressed() throws ParseException {
		// get a handle to the stage
	    Stage stage = (Stage) exitId.getScene().getWindow();
	    // do what you have to do
	    stage.close();	    
	    mainController.reloadMainPage();
	}
	
	@FXML
	private void newAlbumpressed() throws SQLException {
		
		String tmp = albumName.getText();
		String query = "INSERT INTO ALBEN (Name) VALUES ('" + albumName.getText() + "');";
		SendSQLRequest.sendSQL(query);
		System.out.println(tmp);
		albumName.clear();
		listView.getItems().clear();
		database.reloadDatabaseContents();
		database.getAlbums().stream()
							.forEach(album -> listView.getItems().add(album));	
	}
	
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		database.reloadDatabaseContents();
		database.getAlbums().stream()
							.forEach(album -> listView.getItems().add(album));
		
	}

	public void injectMainController(MainControllerGalleryMode mainController) {
		this.mainController = mainController;
	}
}
