package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import database.SendSQLRequest;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.event.Event;

import java.io.IOException;
import java.awt.image.BufferedImage;
import javafx.stage.Stage;
import model.Album;
import model.ImageContainer;
import model.editing.ColorFilter;
import model.editing.Cutter;
import model.editing.GrayScaler;
import model.editing.Resizer;
import model.editing.Rotater;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;


public class MainControllerEditMode extends MainController implements Initializable{
	

	public static Image image;	// Das Bild, das angezeigt wird
	public static Image imagePlain;   //Das Bild ohne Editing wird hier festgehalten
	public static ImageContainer imageContainer; //Wrapper für das Bild mit allen Informationen (wird veraendert nach dem Editieren!)
	
	private int initFitWidth, initFitHeight;
	private boolean cutMode = false;

	@FXML
	private AnchorPane rootPane;
	
	@FXML
	private ScrollPane imageViewScrollPane;
	
	@FXML
	private ImageView displayImageEditMode;
		
	@FXML
	private Button applyFilterButton;
	
	@FXML
	private Button cutModeButton;

	@FXML
	private ChoiceBox<String> colorChoiceBox;
	ObservableList<String> colorChoiceList = FXCollections
				.observableArrayList("Red", "Green", "Blue", 
								     "Yellow", "Violet", "Aqua");
	
	@FXML
	private TextField widthTextField, heightTextField;
	
	@FXML
	private TextField titelTextField, locationTextField, 
			          dateTextField , tagsTextField; 
	
	@FXML
	private Label pathLabel;
	
	@FXML
	private Slider zoomSlider;
	
	@FXML
	private Label zoomSliderValueLabel;
	
	@FXML
	private StackPane imageStackPane;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		image = new Image(imageContainer.getPath());
		imagePlain = new Image(imageContainer.getPath());
		displayImageEditMode.setImage(image);	
		
		initFitWidth = (int)displayImageEditMode.getFitWidth();
		initFitHeight = (int)displayImageEditMode.getFitHeight();
		
		colorChoiceBox.setItems(colorChoiceList);
		colorChoiceBox.setValue(colorChoiceList.get(0));
		
		//Beladen der ChoiceBox passiert erst nach Umstrukturierung der Controller.
		//albumChoiceBox.getChildrenUnmodifiable().addAll(c);
		
		try {
			initializeListView();
		} catch (ParseException e) {
			database.getAlbums().stream().forEach(a -> System.out.println(a.getName()));
			e.printStackTrace();
		}
		initializeMetaData();

		imageViewScrollPane.setFitToWidth(true);
		imageViewScrollPane.setFitToHeight(true);
        imageStackPane.setStyle("-fx-background-color: rgb(80,80,80)");

		
		setScrollingToRootPane();
		setMouseClickToImageView();
		
		setFitDimensions();
		
		currentImageFitWidth = (int)displayImageEditMode.getFitWidth();
		currentImageFitHeight = (int)displayImageEditMode.getFitHeight();
		
		resetZooming();
		setResizeTextFields();
	}
	

	@FXML
	private void switchScene(MouseEvent event) throws IOException{
			Parent pane = FXMLLoader.load(getClass().getResource("/design/Main_page_edit_mode.fxml"));
			Scene changePane = new Scene(pane);
	
			//Show stage information
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(changePane);
			window.show();
	}
			
	@FXML
	private void switchBack(MouseEvent event) throws IOException{
			selectedAlbum = listView.getSelectionModel().getSelectedItem(); 
			didSwitchBack = true;

			Parent pane = FXMLLoader.load(getClass().getResource("/design/Main_page_2.4.fxml"));
			Scene changePane = new Scene(pane);
			//Show stage information
			
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(changePane);
			window.show();
	}
	
	
	
		
	//-----------------------------------------------------------------------------------------------------------//
	/**
	 * Initializes the metadata and the path of an image in editing mode
	 * @author Julian Einspenner
	 */
	private void initializeMetaData() {
		titelTextField.setText(imageContainer.getName());
		locationTextField.setText(imageContainer.getLocation());
		dateTextField.setText(new SimpleDateFormat("dd.MM.yyyy").format(imageContainer.getDate()));
		tagsTextField.setText("Datenbankaufruf folgt");
	    
	    String text = "Path:\n";
	    StringBuilder sb = new StringBuilder(imageContainer.getPath());
	    for(int i = 8; i < imageContainer.getPath().length(); i++) {
	    	text += sb.charAt(i);
	    	if(i % 38 == 0) {
	    		text += "\n";
	    	}
	    }
		pathLabel.setText(text);
	}
	
	/**
	 * Saves the metadata to the database
	 * @author Julian Einspenner
	 */
	@FXML
	private void saveMetaDataButtonPressed() {
		System.out.println("saveMetaData Button");
	}
	
	/**
	 * Images which are smaller as the imageView will be drawn in their original size
	 * @author Julian Einspenner
	 */
	private void setFitDimensions() {
		int width = (int) displayImageEditMode.getImage().getWidth();
		int height = (int) displayImageEditMode.getImage().getHeight();
		
		if(width < displayImageEditMode.getFitWidth()) {
			displayImageEditMode.setFitWidth(width);
		}
		if(height < displayImageEditMode.getFitHeight()) {
			displayImageEditMode.setFitHeight(height);
		}
	}
	
	
	//Fuer kleine Bilder gedacht
	private void setFitDimensionsIfSmallerThanImageViewsMaxSize(int width, int height) {
		if(width < initFitWidth) {
			displayImageEditMode.setFitWidth(width);
		}
		if(height < initFitHeight) {
			displayImageEditMode.setFitHeight(height);
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
	
	
	private int x1, x2, y1, y2;
	/**
	 * @author Julian Einspenner
	 * @param event
	 * @throws IOException
	 */
	private void setMouseClickToImageView() {
		displayImageEditMode.setOnMousePressed(e -> {
			setMouseEvents(e);
			x1 = Cutter.x1;
			y1 = Cutter.y1;
			System.out.println(e.getX());
		});
			
		
		displayImageEditMode.setOnMouseReleased(e -> {
			setMouseEvents(e);
			x2 = Cutter.x1;
			y2 = Cutter.y1;
			
			if(cutMode) {
				Image img = Cutter.cutImage(x1, y1, x2, y2, displayImageEditMode.getImage());
				displayImageEditMode.setFitWidth(initFitWidth);
				displayImageEditMode.setFitHeight(initFitHeight);
				displayImageEditMode.setImage(img);
				setFitDimensionsIfSmallerThanImageViewsMaxSize((int)img.getWidth(), (int)img.getHeight());
				setFitDimensions();
				setResizeTextFields();
				resetZooming();
			}
		});
	}
	
	/**
	 * Sets a mousePressed and a mouseReleased Event to the Image View
	 * @author Julian Einspenner
	 * @param e is the Mouse Event which will be added to the ImageView
	 */
	private void setMouseEvents(MouseEvent e){
		double fitWidth = displayImageEditMode.getFitWidth();
		double fitHeight = displayImageEditMode.getFitHeight();
		double imageWidth = displayImageEditMode.getImage().getWidth();
		double imageHeight = displayImageEditMode.getImage().getHeight();
		
		Cutter.initValues(fitWidth, fitHeight, imageWidth, imageHeight);
		
		//1. Fall
		if(imageWidth <= fitWidth && imageHeight > fitHeight) {
			Cutter.cutCaseOne(e); 
		}
		//2. Fall
		else if(imageHeight <= fitHeight && imageWidth > fitWidth){
			Cutter.cutCaseTwo(e);   
		}
		//3. Fall
		else if(imageWidth < initFitWidth && imageHeight < initFitHeight) {
			Cutter.cutCaseThree(e);
		}
		//4. Fall
		else {
			Cutter.cutCaseFour(e);
		}
	}
	

	@FXML
	private void browseButtonPressed() {
		System.out.println("I am the browseButtonPressed function");
	}
	
	@FXML
	private void fullScreenButtonPressed() {
		System.out.println("I am the fullScreenButtonPressed function");
	}
	
	
	/**
	 * Sets the ratio by the given width of the width textfield
	 * @author Julian Einspenner
	 */
	@FXML
	private void setRatio() {
		double ratio = displayImageEditMode.getImage().getWidth() / displayImageEditMode.getImage().getHeight();
		
		try{
			double width  = Double.valueOf(widthTextField.getText());
			double height = Double.valueOf(heightTextField.getText());
			double textFieldRatio = width / height;
			
			if(width < 1 ||height < 1) {
				return;
			}
			
			if(ratio != textFieldRatio) {
				heightTextField.setText(String.valueOf((int)(width / ratio)));
			}
			
		}catch(NumberFormatException e) {
			return;
		}
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
	

// Menue-Bar----------------------------------------------------
	
	//File
	@FXML
	private void importImage() {
		System.out.println("I am the importImage function");
	}
	
	@FXML
	private void cutModeButtonPressed() {
		if(cutMode) {
			cutModeButton.setStyle("-fx-background-color: #5a5a5a");
			cutMode = false;
			return;
		}
		cutModeButton.setStyle("-fx-background-color: #005f00");  //saftiges wiesengruen
		cutMode = true;
	} 
	
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
		displayImageEditMode.setFitWidth(initFitWidth);
		displayImageEditMode.setFitHeight(initFitHeight);
		
		displayImageEditMode.setImage(Resizer.resizeImage(width, height, imagePlain));
		
		setFitDimensionsIfSmallerThanImageViewsMaxSize(width, height);
		setResizeTextFields();
		resetZooming();
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
		
		displayImageEditMode.setImage(SwingFXUtils.toFXImage(bimage, null));

		setResizeTextFields();
		swapFitDimensions();
	}
	
	/**
	 * Swaps fit dimensions for rotated images
	 */
	private void swapFitDimensions() {
		if(displayImageEditMode.getImage().getWidth() < displayImageEditMode.getFitWidth() ||
		   displayImageEditMode.getImage().getHeight() < displayImageEditMode.getFitHeight() ) {	
			int width = (int)displayImageEditMode.getFitWidth();
			displayImageEditMode.setFitWidth(displayImageEditMode.getFitHeight());
			displayImageEditMode.setFitHeight(width);
		}
	}
	
	/**
	 * Current image will be appear in its plain state
	 * @author Julian Einspenner
	 */
	@FXML
	private void undoButtonPressed() {
		image = new Image(imageContainer.getPath());
		displayImageEditMode.setImage(image);	
		
		displayImageEditMode.setFitWidth(initFitWidth);
		displayImageEditMode.setFitHeight(initFitHeight);
	
		resetGUI();
		
	}
	
	private void resetGUI() {
		setFitDimensions();
		resetZooming();
		setResizeTextFields();
	}
	
	private void resetZooming() {
		zoomSlider.setValue(50);
		zoomSliderValueLabel.setText("50 %");
		zoomStage = 0;
	}
	
	/**
	 * Current image will be appear in its plain state
	 * @author Julian Einspenner
	 */
	@FXML
	private void saveButtonPressed() {
		System.out.println("Save Button");
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
	
	@FXML
	private int setAndGetSliderLabel() {
		int value = (int)zoomSlider.getValue();
		if(value % 5 <= 2) {
			value = value - (value % 5);
		}else {
			value = value + (5 - (value % 5));
		}
		zoomSlider.setValue(value);
		zoomSliderValueLabel.setText(String.valueOf(value) + " %");
		
		return value;
	}
	
	private int currentImageFitWidth;
	private int currentImageFitHeight;
	@FXML
	private void sliderMove() {
		
		int value = setAndGetSliderLabel();
		zoomStage = value / 5 - 10;
		
		double fitWidth    = (int) displayImageEditMode.getFitWidth();
		double fitHeight   = (int) displayImageEditMode.getFitHeight();
		int imageWidth     = (int) displayImageEditMode.getImage().getWidth();
		int imageHeight    = (int) displayImageEditMode.getImage().getHeight();
		
		if(zoomStage == 0) {
			displayImageEditMode.setFitWidth(initFitWidth);
			displayImageEditMode.setFitHeight(initFitHeight);
			setFitDimensionsIfSmallerThanImageViewsMaxSize(imageWidth, imageHeight);
			return;
		}else if(zoomStage > 0) {
			fitWidth = currentImageFitWidth *  Math.pow(1.1, zoomStage);
			fitHeight = currentImageFitHeight * Math.pow(1.1, zoomStage);
		}else {
			fitWidth = currentImageFitWidth /  Math.pow(1.1, Math.abs(zoomStage));
			fitHeight = currentImageFitHeight / Math.pow(1.1, Math.abs(zoomStage));
		}
		displayImageEditMode.setFitWidth(fitWidth);
		displayImageEditMode.setFitHeight(fitHeight);
	}

	private int zoomStage = 0;
	
    private void setScrollingToRootPane(){
    	
    	rootPane.setOnScroll(e -> {
    		int fitWidth    = (int) displayImageEditMode.getFitWidth();
    		int fitHeight   = (int) displayImageEditMode.getFitHeight();
    		int imageWidth  = (int) displayImageEditMode.getImage().getWidth();
    		int imageHeight = (int) displayImageEditMode.getImage().getHeight();
    		
    		double zoomSliderValue = zoomSlider.getValue();
    		
    		if(e.getDeltaY() > 0) {
    			if(zoomSliderValue < 100){
    				zoomStage--;
    				
    				if(zoomStage == 0) {
    					displayImageEditMode.setFitWidth(initFitWidth);
        				displayImageEditMode.setFitHeight(initFitHeight);
        				setFitDimensionsIfSmallerThanImageViewsMaxSize(imageWidth, imageHeight);
    				}else {
    					displayImageEditMode.setFitWidth(fitWidth * 1.1);
    					displayImageEditMode.setFitHeight(fitHeight * 1.1);
    				}
    				zoomSlider.setValue(zoomSliderValue + 5);
    			}
    		}
    		
    		if(e.getDeltaY() < 0) {
    			if(zoomSliderValue > 0) {
    				zoomStage++;
    				
    				if(zoomStage == 0) {
    					displayImageEditMode.setFitWidth(initFitWidth);
        				displayImageEditMode.setFitHeight(initFitHeight);
        				setFitDimensionsIfSmallerThanImageViewsMaxSize(imageWidth, imageHeight);
    				}else {
    					displayImageEditMode.setFitWidth(fitWidth / 1.1);
    					displayImageEditMode.setFitHeight(fitHeight / 1.1);
    				}
    				zoomSlider.setValue(zoomSliderValue - 5);
    			}
    		}
    		zoomSliderValueLabel.setText(Integer.toString((int) zoomSlider.getValue()) + " %");
		});
	}
}