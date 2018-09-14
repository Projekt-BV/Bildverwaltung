package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GridPaneController implements Initializable {


	private MainController mainController;

	
	@FXML private GridPane gridPane;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {		
	}
	
	@FXML
	private void gridPaneImagePressed(MouseEvent e) throws IOException {
		ImageView imageView = (ImageView)e.getPickResult().getIntersectedNode();	
		
		MainControllerEditMode.image = imageView.getImage();
				
		int index = mainController.imagesInSelectedAlbum.indexOf(imageView.getImage());
		MainControllerEditMode.imageContainer = mainController.selectedAlbum.getImages().get(index);
		
		Parent pane = FXMLLoader.load(getClass().getResource("/design/Main_page_edit_mode.fxml"));
		Scene changePane = new Scene(pane);

		//Show stage information
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		window.setScene(changePane);
		window.show();
		
	}
	
	public void injectMainController(MainController fxmlDocumentController) {
		this.mainController = fxmlDocumentController;
	}

}
