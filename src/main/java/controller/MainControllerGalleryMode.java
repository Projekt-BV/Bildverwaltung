package controller;

import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import javafx.stage.Stage;
import model.Album;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class MainControllerGalleryMode extends MainController implements Initializable {
	
	@FXML private AnchorPane rootPane;	
	@FXML private GridPane gridPane;	
	@FXML private ImageView displayImage;
		 
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			initializeListView();
			initializeGridPane();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	
	/** 
	 * Method to initialize the gridPane containing each album's images.
	 */	
	@FXML void initializeGridPane() {			
		
		if (didSwitchBack && selectedAlbum != null){			
			// if we come from Edit mode, leave selectedAlbum as it is and highlight it in listView
			
			Album referenceEqualSelectedAlbum = database.getAlbums().stream()
					   												.filter(album -> album.getName().equals(selectedAlbum.getName()))
					   												.findFirst()
					   												.get();
			
			listView.getSelectionModel().select(database.getAlbums().indexOf(referenceEqualSelectedAlbum));
		} else if (selectedAlbum == null) {
			// else if no album has been clicked on, get album "All Images"
			selectedAlbum = database.getAlbums().stream()
												.filter(album -> album.getName().equals("All Images"))
												.findFirst()
												.get();
		} else {
			// else get album that has been clicked on
			gridPane.getChildren().clear(); // clear gridPane
			selectedAlbum = listView.getSelectionModel().getSelectedItem(); 
		}
		
		didSwitchBack = false;

		
		// add album's images to collection
		imagesInSelectedAlbum = new ArrayList<Image>();		
		selectedAlbum.getImages().stream()
								 .forEach(i -> imagesInSelectedAlbum.add(new Image(i.getPath())));
		
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
	
	@FXML
	private void gridPaneImagePressed(MouseEvent e) throws IOException {
		ImageView imageView = (ImageView)e.getPickResult().getIntersectedNode();	
		
		MainControllerEditMode.image = imageView.getImage();
				
		int index = imagesInSelectedAlbum.indexOf(imageView.getImage());
		MainControllerEditMode.imageContainer = selectedAlbum.getImages().get(index);
		
		Parent pane = FXMLLoader.load(getClass().getResource("/design/Main_page_edit_mode.fxml"));
		Scene changePane = new Scene(pane);

		//Show stage information
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		window.setScene(changePane);
		window.show();	
		
	}
	
	// Bar above gridPane
	@FXML
	private void renameAllButtonPressed() {
		System.out.println("I am the renameAllButtonPressed function");
	}
}