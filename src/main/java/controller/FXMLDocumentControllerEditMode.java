package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database.SendSQLRequest;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javafx.stage.Stage;
import model.ImageContainer;
import model.editing.ColorFilter;
import model.editing.GrayScaler;
import model.editing.Resizer;
import model.editing.Rotater;
import model.editing.Zoom;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ChoiceBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;


public class FXMLDocumentControllerEditMode implements Initializable{
	

	public static Image image;	// Das Bild, das angezeigt wird
	public static Image imagePlain;   //Das Bild ohne Editing wird hier festgehalten
	public static ImageContainer imageContainer; //Wrapper für das Bild mit allen Informationen (wird veraendert nach dem Editieren!)
	
	private int initFitWidth, initFitHeight;
	

	@FXML
	private AnchorPane rootPane;
	
	@FXML
	private ImageView displayImageEditMode;
	
	@FXML
	private ListView<String> listView;
	
	@FXML
	private GridPane gridPane;
	
	@FXML
	private Button applyFilterButton;

	@FXML
	private ChoiceBox colorChoiceBox;
	ObservableList<String> colorChoiceList = FXCollections
				.observableArrayList("Red", "Green", "Blue", 
								     "Yellow", "Violet", "Aqua");
	
	@FXML
	private TextField widthTextField, heightTextField;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		image = new Image(imageContainer.getPath());
		imagePlain = new Image(imageContainer.getPath());
		displayImageEditMode.setImage(image);	
		
		initFitWidth = (int)displayImageEditMode.getFitWidth();
		initFitHeight = (int)displayImageEditMode.getFitHeight();
		
		colorChoiceBox.setItems(colorChoiceList);
		colorChoiceBox.setValue(colorChoiceList.get(0));
		
		initializeListView();	
		
		setFitDimensions();
		setScrollingToImageView();
		
		setResizeTextFields();
	}
		
	
	/** 
	 * Method to initialize the listView containing the album names.
	 * @author Phillip Persch
	 */
	private void initializeListView() {		
		try {
			ResultSet albums = SendSQLRequest.sendSQL("SELECT * FROM alben");
			
			while (albums.next()) {
				listView.getItems().add(albums.getString("Name"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Images which are smaller as the imageView will be drawn in their original size
	 * @author Julian Einspenner
	 */
	private void setFitDimensions() {
		if(image.getWidth() < displayImageEditMode.getFitWidth()) {
			displayImageEditMode.setFitWidth(image.getWidth());
		}
		if(image.getHeight() < displayImageEditMode.getFitHeight()) {
			displayImageEditMode.setFitHeight(image.getHeight());
		}
	}
	
	/**
	 * Sets the text of the resize textfields width and height
	 * @author Julian Einspenner
	 */
	private void setResizeTextFields() {
		int width = (int) displayImageEditMode.getImage().getWidth();
		int height = (int) displayImageEditMode.getImage().getHeight();
		
		widthTextField.setText(String.valueOf(width));
		heightTextField.setText(String.valueOf(height));
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
	private void browseButtonPressed() {
		System.out.println("I am the browseButtonPressed function");
	}
	
	@FXML
	private void fullScreenButtonPressed() {
		System.out.println("I am the fullScreenButtonPressed function");
	}
	
	
	@FXML
	private void newFolderButtonPressed() {
		System.out.println("I am the newFolderButtonPressed function");
	}

	@FXML
	private void filterButtonPressed() {
		String color = (String)colorChoiceBox.getValue();
		Image img = ColorFilter.filterCoulours(displayImageEditMode.getImage(), color);
		displayImageEditMode.setImage(img);
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
	
	@FXML
	private void cutImage() {
		System.out.println("I am the cutImage function");
	}	
	
	@FXML
	private void resizeImage() {
		int width, height;
		
		try {
			width = Integer.parseInt(widthTextField.getText());
			height = Integer.parseInt(heightTextField.getText());
		}catch(NumberFormatException e){
			return;
		}
		
		setFitDimensionsIfSmallerThanImageViewsMaxSize(width, height);
		
		displayImageEditMode.setImage(Resizer.resizeImage(width, height, displayImageEditMode.getImage()));
		
		setResizeTextFields();
		
	}
	
	
	private void setFitDimensionsIfSmallerThanImageViewsMaxSize(int width, int height) {
		if(initFitWidth >= width) {
			displayImageEditMode.setFitWidth(width);
		}
		if(initFitHeight >= height) {
			displayImageEditMode.setFitHeight(height);
		}
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
	
	//--------------------------------------------------------
	
	//Edit Mode
	@FXML
	private void rotateClockwise() {
		Image image = displayImageEditMode.getImage();
		BufferedImage bimage = SwingFXUtils.fromFXImage(image, null);
		
		bimage = Rotater.rotateClockwise(bimage);
		
		displayImageEditMode.setImage(
				SwingFXUtils.toFXImage(bimage, null));
		
		setResizeTextFields();
		swapFitDimensions();
	}
	
	@FXML
	private void rotateCounterClockwise() {
		Image image = displayImageEditMode.getImage();
		BufferedImage bimage = SwingFXUtils.fromFXImage(image, null);
		
		bimage = Rotater.rotateAntiClockwise(bimage);
		
		displayImageEditMode.setImage(
				SwingFXUtils.toFXImage(bimage, null));

		setResizeTextFields();
		swapFitDimensions();
	}
	
	private void swapFitDimensions() {
		if(displayImageEditMode.getImage().getWidth() < displayImageEditMode.getFitWidth() ||
		   displayImageEditMode.getImage().getHeight() < displayImageEditMode.getFitHeight() ) {	
			int right = (int)displayImageEditMode.getFitWidth();
			displayImageEditMode.setFitWidth(displayImageEditMode.getFitHeight());
			displayImageEditMode.setFitHeight(right);
		}
	}
	
	
	
	@FXML
	private void undo() {
		System.out.println("I am the undo function");
	}
	
	@FXML
	private void saveMetadata() {
		System.out.println("I am the saveMetadata function");
	}	
	
	@FXML
	private void filterColor() {
		System.out.println("I am the filterColor function");
	}	
	
	@FXML
	private void addKeyword() {
		System.out.println("I am the addKeyword function");
	}
	
	@FXML
	private void monochroneButtonPressed() {
		displayImageEditMode.setImage(GrayScaler.grayScaleImage(displayImageEditMode.getImage()));
	}
	
	@FXML
	private void deleteAlbum() {
		System.out.println("I am the deleteAlbum function");
	}
	

// IMAGE TEST
//-----------------------------------------------------------
	@FXML
	private void loadImage() throws IOException{
		displayImageEditMode.setImage(null);
		Image image = new Image("/design/dummyImages/2.jpeg");
		displayImageEditMode.setImage(image);
	}

    private void setScrollingToImageView(){
    	displayImageEditMode.setOnScroll(e -> {
    		if(e.getDeltaY() > 0) {
    			BufferedImage bimg = Zoom.zoomIn(SwingFXUtils.fromFXImage(displayImageEditMode.getImage(), null));
    			Image img = SwingFXUtils.toFXImage(bimg, null);
    			
    			displayImageEditMode.setImage(img);
    		}else if(e.getDeltaY() < 0) {
    			BufferedImage bimg = Zoom.zoomOut(SwingFXUtils.fromFXImage(displayImageEditMode.getImage(), null));
    			Image img = SwingFXUtils.toFXImage(bimg, null);
    			
    			displayImageEditMode.setImage(img);
    		}
		});
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