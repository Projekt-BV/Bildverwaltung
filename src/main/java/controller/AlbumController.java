package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

import database.SendSQLRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	
	@FXML
	private void exitButtonpressed() {
		// get a handle to the stage
	    Stage stage = (Stage) exitId.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	}
	
	@FXML
	private void newAlbumpressed() throws SQLException {
		
		String tmp = albumName.getText();
		String query = "INSERT INTO ALBEN (Name) VALUES ('" + albumName.getText() + "');";
		SendSQLRequest.sendSQL(query);
		System.out.println(tmp);
		albumName.clear();
		listView.getItems().clear();
		try {
			database.reloadDatabaseContents();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		database.getAlbums().stream()
							.forEach(album -> listView.getItems().add(album));
		
	
	}
	
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			database.reloadDatabaseContents();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		database.getAlbums().stream()
							.forEach(album -> listView.getItems().add(album));
		
	}
}
