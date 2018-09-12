package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.stage.Stage;
import model.Album;
import model.Database;
import model.FileImport;
import model.ImageContainer;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class FXMLDocumentController implements Initializable {
	

	@FXML
	private AnchorPane rootPane;
	
	@FXML
	private ImageView displayImage;
	
	@FXML
	private ListView<Album> listView;
	
	@FXML
	private GridPane gridPane;
	
	Database database;
	ArrayList<Image> imagesInSelectedAlbum;
	Album selectedAlbum;

	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeListView();
	}
		
	
	/** 
	 * Method to initialize the listView containing the album names.
	 */
	private void initializeListView() {		
		database = new Database();
		
		for (Album album : database.getAlbums()) {
			listView.getItems().add(album);
		}
	}
	
	/** 
	 * Method to initialize the listView containing the album names.
	 */	
	@FXML
	private void initializeGridPane() {
		gridPane.getChildren().clear(); // clear gridPane
		selectedAlbum = listView.getSelectionModel().getSelectedItem(); // get album that has been clicked on
		
		// add album's images to collection
		imagesInSelectedAlbum = new ArrayList<Image>();		
		for (ImageContainer imageContainer : selectedAlbum.getImages()) {
			imagesInSelectedAlbum.add(new Image(imageContainer.getPath()));
		}		
		
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
	private void switchScene(ActionEvent event) throws IOException{
			Parent pane = FXMLLoader.load(getClass().getResource("/design/Main_page_edit_mode.fxml"));
			Scene changePane = new Scene(pane);
	
			//Show stage information
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(changePane);
			window.show();
	}
			
	@FXML
	private void switchBack(ActionEvent event) throws IOException{
			Parent pane = FXMLLoader.load(getClass().getResource("/design/Main_page_2.4.fxml"));
			Scene changePane = new Scene(pane);
			//Show stage information
			
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(changePane);
			window.show();
	}
	
	@FXML
	private void gridPaneImagePressed(MouseEvent e) throws IOException {
		ImageView imageView = (ImageView)e.getPickResult().getIntersectedNode();	
		
		FXMLDocumentControllerEditMode.image = imageView.getImage();
				
		int index = imagesInSelectedAlbum.indexOf(imageView.getImage());
		FXMLDocumentControllerEditMode.imageContainer = selectedAlbum.getImages().get(index);
		
		Parent pane = FXMLLoader.load(getClass().getResource("/design/Main_page_edit_mode.fxml"));
		Scene changePane = new Scene(pane);

		//Show stage information
		Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
		window.setScene(changePane);
		window.show();
		
	}
	
	@FXML
	private void browseButtonPressed() {
		System.out.println("I am the browseButtonPressed function");
	}
	
	@FXML
	private void fullScreenButtonPressed() {
		System.out.println("I am the fullScreenButtonPressed function");
	}
	

	@FXML
	private void newFolderButtonPressed() {
		FileImport tmpImp = new FileImport();
		Stage window = new Stage();
		tmpImp.start(window);
	}

	@FXML
	private void filterButtonPressed() {
		System.out.println("I am the filterButtonPressed function");
	}
	
	@FXML
	private void renameAllButtonPressed() {
		System.out.println("I am the renameAllButtonPressed function");
	}
	
	@FXML
	private void dropdownButtonChoiceSelected() {
		System.out.println("I am the dropdownButtonChoiceSelected function");
	}
//______________________________________________________________

	@FXML
	private void sliderMove() {
		System.out.println("I am the sliderMove function");
	}
	
//______________________________________________________________

// Menue-Bar----------------------------------------------------
	
	//File
	@FXML
	private void importImage() {
		System.out.println("I am the importImage function");
	}
	
//	@FXML
//	private void cutImage() {
//
//	} Already exist -> Vermeide doppelte Klassen
	
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
	//-----------------------
	
	//Edit
	@FXML
	private void blackWhite() {
		System.out.println("I am the blackWhite function");
	}	
	
	@FXML
	private void cutImage() {
		System.out.println("I am the cutImage function");
	}	
	
	@FXML
	private void resizeImage() {
		System.out.println("I am the resizeImage function");
	}
	
	//---------------------------
	
	//Organize

	@FXML
	private void searchImage() {
		System.out.println("I am the searchImage function");
	}
	
	@FXML
	private void filterImages() {
		System.out.println("I am the filterImages function");
	}	
	
	@FXML
	private void sortImages() {
		System.out.println("I am the sortImages function");
	}	
	
	//---------------------------
	
	//View
	
	@FXML
	private void showDetail() {
		System.out.println("I am the showDetail function");
	}	
	
//	@FXML
//	private void showPreview() {
//
//	}	Fullscreen?
	
	@FXML
	private void navigator() {
		System.out.println("I am the navigator function");
	}	
	
	@FXML
	private void information() {
		System.out.println("I am the information function");
	}
	
	//----------------------------
	
	//Help
	@FXML
	private void showCommands() {
		System.out.println("I am the showCommands function");
	}	
	
	@FXML
	private void changeLanguage() {
		System.out.println("I am the changeLanguage function");
	}	

// IMAGE TEST
//-----------------------------------------------------------
	@FXML
	private void loadImage(String picture) throws IOException{
		Image image = new Image(picture);
		displayImage.setImage(image);
	}
	
		
		//DATA
  
//		String url = "http://mariadb/image.png";
//		 
//		boolean backgroundLoading = true;
//		 
//		// The image is being loaded in the background
//		Image image = new Image(url, backgroundLoading);
//		
//		// An image file on the hard drive.
//		File file = new File("C:\\Users\\Tobi\\Pictures\\22550006_10210659715665085_3954056403038140095_n.jpg");
//		 
//		// --> file:/C:/MyImages/myphoto.jpg
//		String localUrl = file.toURI().toURL().toString();
//		 
//		Image image2 = new Image(localUrl);
//		ImageView imageView = new ImageView(image);
//		
//		FlowPane root = new FlowPane();
//        root.getChildren().add(imageView);

}