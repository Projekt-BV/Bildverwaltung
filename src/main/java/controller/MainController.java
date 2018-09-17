package controller;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Album;
import model.Database;
import model.editing.FileImport;

public abstract class MainController {

@FXML ListView<Album> listView;
	
	
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
	
	
	

}
	

