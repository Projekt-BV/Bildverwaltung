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
	
	private FXMLDocumentController mainController;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	/** 
	 * Method to initialize the listView containing the album names.
	 */
	public void initializeListView() {
		
		mainController.database.reloadDatabaseContents();			
		mainController.database.getAlbums().stream()
							.forEach(album -> listView.getItems().add(album));

		initializeGridPane();
	}
	
	/** 
	 * Method to initialize the listView containing the album names.
	 */	
	@FXML void initializeGridPane() {		
		
		if (mainController.gridPane.getChildren().isEmpty()) {
			// get album "All Images"
			mainController.selectedAlbum = mainController.database.getAlbums().stream()
												.filter(album -> album.getName().equals("All Images"))
												.findFirst()
												.get();
		} else {
			// get album that has been clicked on
			mainController.gridPane.getChildren().clear(); // clear gridPane
			mainController.selectedAlbum = listView.getSelectionModel().getSelectedItem(); 
		}
		
		// add album's images to collection
		mainController.imagesInSelectedAlbum = new ArrayList<Image>();		
		mainController.selectedAlbum.getImages().stream().forEach(i -> mainController.imagesInSelectedAlbum.add(new Image(i.getPath())));
		
		// add collection to grid
		int row = 0;
		int line = 0;
		for (Image image : mainController.imagesInSelectedAlbum) {			
			ImageView imageView = new ImageView(image);
			imageView.setFitHeight(130);
			imageView.setFitWidth(135);
			
			mainController.gridPane.getChildren().add(imageView);
			
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
	}

}
