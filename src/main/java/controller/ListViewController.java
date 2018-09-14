package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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

public class ListViewController implements Initializable {

	
	@FXML private ListView<Album> listView;
	
	private GridPane gridPane;
	private FXMLDocumentController mainController;
	private ArrayList<Image> imagesInSelectedAlbum;
	private Album selectedAlbum;
	private Database database;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	/** 
	 * Method to initialize the listView containing the album names.
	 */
	public void initializeListView() {
		
		database.reloadDatabaseContents();			
		database.getAlbums().stream()
							.forEach(album -> listView.getItems().add(album));

		initializeGridPane();
	}
	
	/** 
	 * Method to initialize the listView containing the album names.
	 */	
	@FXML void initializeGridPane() {		
		
		if (gridPane.getChildren().isEmpty()) {
			// get album "All Images"
			selectedAlbum = database.getAlbums().stream()
												.filter(album -> album.getName().equals("All Images"))
												.findFirst()
												.get();
		} else {
			// get album that has been clicked on
			gridPane.getChildren().clear(); // clear gridPane
			selectedAlbum = listView.getSelectionModel().getSelectedItem(); 
		}
		
		// add album's images to collection
		imagesInSelectedAlbum = new ArrayList<Image>();		
		selectedAlbum.getImages().stream().forEach(i -> imagesInSelectedAlbum.add(new Image(i.getPath())));
		
		// add collection to grid
		int row = 0;
		int line = 0;
		for (Image image : imagesInSelectedAlbum) {			
			ImageView imageView = new ImageView(image);
			imageView.setFitHeight(130);
			imageView.setFitWidth(135);
			
			gridPane.getChildren().add(imageView);
			
			GridPane.setConstraints(imageView, row, line, 1, 1);
			if (row > 5) {
				row = 0;
				line++;
			} else 
				row++;	 
		}
	}
	
	
	public void injectMainController(FXMLDocumentController fxmlDocumentController) {
		this.mainController = fxmlDocumentController;
		this.gridPane = mainController.gridPane;
		this.database = mainController.database;
		this.selectedAlbum = mainController.selectedAlbum;
		this.imagesInSelectedAlbum = mainController.imagesInSelectedAlbum;		
	}

}
