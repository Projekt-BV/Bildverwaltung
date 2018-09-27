package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import database.SendSQLRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.ImageContainer;
import model.editing.ColorFilter;
import model.editing.Cutter;
import model.editing.EditMetaData;
import model.editing.GrayScaler;
import model.editing.Resizer;
import model.editing.Rotater;

/**
 * This class manages the edit image scene. (second main scene)
 * 
 * @author Julian Einspenner, Phillip Persch, Mario Anklam, Tobias Reinert *
 */
public class MainControllerEditMode extends MainController implements Initializable {

	public static Image image; // The image being displayed
	public static Image imagePlain; // The image before being edited
	public static ImageContainer imageContainer; // Wrapper for image and metadata
	
	// don't confuse Image, ImageView, ImageContainer!!!

	private int initFitWidth, initFitHeight;
	private boolean cutMode = false;
	private int currentImageFitWidth;
	private int currentImageFitHeight;
	private int zoomStage = 0;

	// FXML fields
	@FXML private ScrollPane imageViewScrollPane;
	@FXML private ImageView displayImageEditMode;
	@FXML private Button applyFilterButton;
	@FXML private Button cutModeButton;
	@FXML private ChoiceBox<String> colorChoiceBox;
	@FXML private TextField widthTextField, heightTextField;
	@FXML private TextField titelTextField, locationTextField, dateTextField, tagsTextField;
	@FXML private Label pathLabel;
	@FXML private Slider zoomSlider;
	@FXML private Label zoomSliderValueLabel;
	@FXML private StackPane imageStackPane;
	@FXML private Button forwardButton;
	@FXML private Button backwardButton;

	/**
	 * This method initializes the controller and all of its components.
	 * 
	 * @author Julian Einspenner, Phillip Persch
	 * @param arg0 inhereted from superclass
	 * @param arg1 inhereted from superclass
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		image = new Image(imageContainer.getPath());
		imagePlain = new Image(imageContainer.getPath());
		displayImageEditMode.setImage(image);

		initFitWidth = (int) displayImageEditMode.getFitWidth();
		initFitHeight = (int) displayImageEditMode.getFitHeight();
		
		initializeListView();

		imageViewScrollPane.setFitToWidth(true);
		imageViewScrollPane.setFitToHeight(true);
		imageStackPane.setStyle("-fx-background-color: rgb(80,80,80)");

		setScrollingToRootPane();
		setMouseClickToImageView();

		setFitDimensions();

		currentImageFitWidth = (int) displayImageEditMode.getFitWidth();
		currentImageFitHeight = (int) displayImageEditMode.getFitHeight();

		resetZooming();
		setResizeTextFields();

		controllerCheck("EditMode");
		initColorChoiceBox();
		initializeMetaData();
	}
	

	// -----------------------------------------------------------------------------------------------------------//
	// Initialization
	
	/**
	 * Initializes the metadata and the path of an image in editing mode
	 * 
	 * @author Julian Einspenner
	 */
	private void initializeMetaData() {

		String titleSplitted[] = imageContainer.getName().split("\\.");
		String title = "";
		if (titleSplitted[0] == null) {
			title = imageContainer.getName();
		} else {
			title = titleSplitted[0];
		}

		titelTextField.setText(title);
		locationTextField.setText(imageContainer.getLocation());
		dateTextField.setText(new SimpleDateFormat("dd.MM.yyyy").format(imageContainer.getDate()));

		String tags = "";
		for (int i = 0; i < imageContainer.getTags().size(); i++) {
			tags += imageContainer.getTags().get(i);
			if (i < imageContainer.getTags().size() - 1) {
				tags += ",";
			}
		}

		tagsTextField.setText(tags);

		setPath();
	}

	/**
	 * Sets the path label to current path of imageContainer
	 * 
	 * @author Julian Einspenner 
	 */
	private void setPath() {
		String text = "Path:\n";
		StringBuilder sb = new StringBuilder(imageContainer.getPath());
		for (int i = 8; i < imageContainer.getPath().length(); i++) {
			text += sb.charAt(i);
			if (i % 38 == 0) {
				text += "\n";
			}
		}
		pathLabel.setText(text);
	}

	/**
	 * Initializes the colorChoiceBox with values for filtering the color of the displayed image
	 * 
	 * @author Julian Einspenner
	 */
	private void initColorChoiceBox() {
		Properties config;
		config = new Properties();
		FileInputStream fis;

		try {
			fis = new FileInputStream("src/main/java/LangBundle_" + currentLanguage + ".properties");
			config.load(fis);

		} catch (IOException io) {
			io.printStackTrace();
		}

		ObservableList<String> colorChoiceList = FXCollections.observableArrayList(config.getProperty("ChoiceBox-None"),
				config.getProperty("ChoiceBox-Black-White"), config.getProperty("ChoiceBox-Red"),
				config.getProperty("ChoiceBox-Green"), config.getProperty("ChoiceBox-Blue"),
				config.getProperty("ChoiceBox-Yellow"), config.getProperty("ChoiceBox-Violet"),
				config.getProperty("ChoiceBox-Aqua"));

		colorChoiceBox.setItems(colorChoiceList);
		colorChoiceBox.setValue(colorChoiceList.get(0));
		colorChoiceBox.setOnAction(e -> {
			colorChoiceBoxUsed();
		});
	}
	
	/**
	 * This method switches back from this scene to the gallery scene.
	 * 
	 * @author Tobias Reinert, Phillip Persch
	 * @param event the event that caused the scene to switch
	 * @throws IOException
	 */
	@FXML
	private void switchBack(Event event) throws IOException {
		selectedAlbum = listView.getSelectionModel().getSelectedItem();
		didSwitchBack = true;

		Parent pane = FXMLLoader.load(getClass().getResource("/design/Main_page_2.4.fxml"));		
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		
		window.getScene().setRoot(pane);
		window.setMinWidth(1280);
		window.setMinHeight(767);
	}

	/**
	 * Images which are smaller as the imageView will be drawn in their original size 
	 * @author Julian Einspenner
	 */
	private void setFitDimensions() {
		int width = (int) displayImageEditMode.getImage().getWidth();
		int height = (int) displayImageEditMode.getImage().getHeight();

		if (width < displayImageEditMode.getFitWidth()) {
			displayImageEditMode.setFitWidth(width);
		}
		if (height < displayImageEditMode.getFitHeight()) {
			displayImageEditMode.setFitHeight(height);
		}
	}

	/**
	 * This method is written for small images which would be overstreched after
	 * loading them to the imageView
	 * 
	 * @author Julian Einspenner
	 * @param width
	 * @param height
	 */
	private void setFitDimensionsIfSmallerThanImageViewsMaxSize(int width, int height) {
		if (width < initFitWidth) {
			displayImageEditMode.setFitWidth(width);
		}
		if (height < initFitHeight) {
			displayImageEditMode.setFitHeight(height);
		}
	}

	/**
	 * Sets the text of the resize textfields width and height
	 * 
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

			if (cutMode) {
				Image img = Cutter.cutImage(x1, y1, x2, y2, displayImageEditMode.getImage());
				displayImageEditMode.setFitWidth(initFitWidth);
				displayImageEditMode.setFitHeight(initFitHeight);
				displayImageEditMode.setImage(img);
				setFitDimensionsIfSmallerThanImageViewsMaxSize((int) img.getWidth(), (int) img.getHeight());
				setFitDimensions();
				setResizeTextFields();
				resetZooming();
			}
		});
	}

	/**
	 * Sets a mousePressed and a mouseReleased Event to the Image View
	 * 
	 * @author Julian Einspenner
	 * @param e
	 *            is the Mouse Event which will be added to the ImageView
	 */
	private void setMouseEvents(MouseEvent e) {
		double fitWidth = displayImageEditMode.getFitWidth();
		double fitHeight = displayImageEditMode.getFitHeight();
		double imageWidth = displayImageEditMode.getImage().getWidth();
		double imageHeight = displayImageEditMode.getImage().getHeight();

		Cutter.initValues(fitWidth, fitHeight, imageWidth, imageHeight);

		// 1. Fall
		if (imageWidth <= fitWidth && imageHeight > fitHeight) {
			Cutter.cutCaseOne(e);
		}
		// 2. Fall
		else if (imageHeight <= fitHeight && imageWidth > fitWidth) {
			Cutter.cutCaseTwo(e);
		}
		// 3. Fall
		else if (imageWidth < initFitWidth && imageHeight < initFitHeight) {
			Cutter.cutCaseThree(e);
		}
		// 4. Fall
		else {
			Cutter.cutCaseFour(e);
		}
	}

	/**
	 * Sets the width-height ratio by the given width. The height textfield is going
	 * to be changed if the current width and height values from the textfields do
	 * not represent the images original ratio.
	 * 
	 * @author Julian Einspenner
	 */
	@FXML
	private void setRatio() {
		double ratio = displayImageEditMode.getImage().getWidth() / displayImageEditMode.getImage().getHeight();

		try {
			double width = Double.valueOf(widthTextField.getText());
			double height = Double.valueOf(heightTextField.getText());
			double textFieldRatio = width / height;

			if (width < 1 || height < 1) {
				return;
			}

			if (ratio != textFieldRatio) {
				heightTextField.setText(String.valueOf((int) (width / ratio)));
			}

		} catch (NumberFormatException e) {
			return;
		}
	}

	/**
	 * Activates or deactivates the cut mode
	 * 
	 * @author Julian Einspenner
	 */
	@FXML
	private void cutModeButtonPressed() {
		if (cutMode) {
			cutModeButton.setStyle("-fx-background-color: #5a5a5a");
			cutMode = false;
			return;
		}
		cutModeButton.setStyle("-fx-background-color: #005f00"); // saftiges wiesengruen
		cutMode = true;
	}

	/**
	 * Initializes the view to show the image with a new size. Calls the
	 * Resizer.java - Class to resize a new image
	 * 
	 * @author Julian Einspenner
	 */
	@FXML
	private void resizeImage() {
		int width, height;

		try {
			width = Integer.parseInt(widthTextField.getText());
			height = Integer.parseInt(heightTextField.getText());

		} catch (NumberFormatException e) {
			return;
		}
		displayImageEditMode.setFitWidth(initFitWidth);
		displayImageEditMode.setFitHeight(initFitHeight);

		displayImageEditMode.setImage(imagePlain);

		if (usedColorFilter) {
			colorChoiceBoxUsed();
		}

		displayImageEditMode.setImage(Resizer.resizeImage(width, height, displayImageEditMode.getImage()));

		setFitDimensionsIfSmallerThanImageViewsMaxSize(width, height);
		setResizeTextFields();
		resetZooming();
	}

	// --------------------------------------------------------

	/**
	 * Calls the Rotater-class from Editing-Package to rotate the image clockwise
	 * 
	 * @author Julian Einspenner
	 */
	@FXML
	private void rotateClockwise() {
		Image image = displayImageEditMode.getImage();
		BufferedImage bimage = SwingFXUtils.fromFXImage(image, null);

		bimage = Rotater.rotateClockwise(bimage);

		displayImageEditMode.setImage(SwingFXUtils.toFXImage(bimage, null));

		setResizeTextFields();
		swapFitDimensions();
	}

	/**
	 * Calls the Rotater-class from Editing-Package to rotate the image
	 * anticlockwise
	 * 
	 * @author Julian Einspenner
	 */
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
	 * 
	 * @author Julian Einspenner
	 */
	private void swapFitDimensions() {
		if (displayImageEditMode.getImage().getWidth() < displayImageEditMode.getFitWidth()
				|| displayImageEditMode.getImage().getHeight() < displayImageEditMode.getFitHeight()) {
			int width = (int) displayImageEditMode.getFitWidth();
			displayImageEditMode.setFitWidth(displayImageEditMode.getFitHeight());
			displayImageEditMode.setFitHeight(width);
		}
	}

	/**
	 * Current image will be appear in its plain state
	 * 
	 * @author Julian Einspenner
	 */
	@FXML
	private void undoButtonPressed() {
		image = new Image(imageContainer.getPath());
		displayImageEditMode.setImage(image);

		displayImageEditMode.setFitWidth(initFitWidth);
		displayImageEditMode.setFitHeight(initFitHeight);

		usedColorFilter = false;
		colorChoiceBox.getSelectionModel().selectFirst();

		resetGUI();
	}

	/**
	 * Resetting of the GUI to make a suitable state for a new image
	 * 
	 * @author Julian Einspenner
	 */
	private void resetGUI() {
		displayImageEditMode.setFitWidth(initFitWidth);
		displayImageEditMode.setFitHeight(initFitHeight);

		usedColorFilter = false;
		colorChoiceBox.getSelectionModel().selectFirst();

		setFitDimensions();
		resetZooming();
		setResizeTextFields();
		initializeMetaData();
	}

	/**
	 * Resets zoomSlider, its text label and the zoomstage
	 * 
	 * @author Julian Einspenner
	 */
	private void resetZooming() {
		zoomSlider.setValue(50);
		zoomSliderValueLabel.setText("50 %");
		zoomStage = 0;
	}

	/**
	 * Saves metadata to database
	 * 
	 * @author Julian Einspenner
	 */
	@FXML
	private void saveMetaDataButtonPressed() {
		String title = titelTextField.getText();
		String location = locationTextField.getText();
		String date = dateTextField.getText();

		Pattern p = Pattern.compile("[0-9]{1,2}\\.[0-9]{1,2}\\.[0-9]{4}");
		Matcher m = p.matcher(date);

		if (!m.matches()) {
			date = new SimpleDateFormat("dd.MM.yyyy").format(imageContainer.getDate());
		}

		String[] tags = tagsTextField.getText().split(",");

		if (EditMetaData.saveMetaData(title, location, date, tags, imageContainer.getId())) {
			imageContainer.setDate(date);
			imageContainer.setLocation(location);
			imageContainer.setName(title);

			ArrayList<String> tagList = new ArrayList<String>(Arrays.asList(tags));
			imageContainer.setTags(tagList);
			setPath();
		}
		saveImage();
	}

	@FXML
	private void saveImage() {
		String path = imageContainer.getPath().substring(8, imageContainer.getPath().length());
		BufferedImage img = SwingFXUtils.fromFXImage(displayImageEditMode.getImage(), null);
		File outFile = new File(path);
		try {
			ImageIO.write(img, "png", outFile);
		} catch (IOException e) {
			System.err.println("Failed while saving the image");
		}
	}

	@FXML
	private void saveImageAs() {
		FileChooser fc = new FileChooser();
		fc.setInitialFileName("Photo.jpg");
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image (*.jpg)", "*.jpg"));
		File selectedDirectory = fc.showSaveDialog(new Stage());

		if (selectedDirectory == null) {
			return;
		}

		String path = selectedDirectory.getAbsolutePath().replace("\\", "/");
		String filename = selectedDirectory.getName();
		
		File outFile = new File(path);
		
		Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");            
        String formattedDate = df.format(date);
		
		try {
			BufferedImage img = SwingFXUtils.fromFXImage(displayImageEditMode.getImage(), null);
			ImageIO.write(img, "png", outFile);
		} catch (IOException e) {
			System.err.println("Failed while saving the image");
		}
		
		String selectIdRequest  = "SELECT ID FROM fotos WHERE Pfad=" + "'file:///" + path + "'";
		String countRowsRequest = "SELECT COUNT(*) FROM fotos WHERE Pfad=" + "'file:///" + path + "'";
		
		ResultSet rs;
		try {
			rs = SendSQLRequest.sendSQL(countRowsRequest);
			if(rs.next() && rs.getInt(1) > 0) {
				//Bild wurde ueberschrieben, der Pfad existiert schon in der DB
				return;
			}
			
			//Speichere nach Tabelle fotos
			String saveAsRequestFotos = "INSERT INTO fotos (Datum, Fotoname, Pfad) VALUES ('" + formattedDate + "', '" + filename + "', 'file:///" + path + "')";
			SendSQLRequest.sendSQL(saveAsRequestFotos);
			
			//Welche ID hat das neue Bild?
			ResultSet set = SendSQLRequest.sendSQL(selectIdRequest);
			int id = -1; 
			while(set.next()) {
				id = set.getInt(1);
			}
			
		    //Speichere nach Tabelle albumfoto
			String saveAsRequestAlben = "INSERT INTO albumfoto (AlbumID, FotoID) VALUES ('1', '" + id + "')";
			SendSQLRequest.sendSQL(saveAsRequestAlben);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	boolean usedColorFilter = false;

	/**
	 * Calls the ColorFilter-Class to set a colorfilter to the image. A color
	 * filtered state is going to be stored in this class for not loosing it if the
	 * image will be resized
	 * 
	 * @author Julian Einspenner
	 */
	private void colorChoiceBoxUsed() {

		int color = getSelectedChoiceBoxIndex();

		if (color == 0) {
			return;
		}

		usedColorFilter = true;

		if (color == 1) {
			displayImageEditMode.setImage(GrayScaler.grayScaleImage(displayImageEditMode.getImage()));
			return;
		}

		Image img = ColorFilter.filterCoulours(displayImageEditMode.getImage(), color);
		displayImageEditMode.setImage(img);
	}

	/**
	 * Sets the zoomSlider-label with the value of the slider. After, this value
	 * will be returned
	 * 
	 * @author Julian Einspenner
	 * @return the new value of the zoomSlider
	 */
	@FXML
	private int setAndGetSliderLabel() {
		int value = (int) zoomSlider.getValue();
		if (value % 5 <= 2) {
			value = value - (value % 5);
		} else {
			value = value + (5 - (value % 5));
		}
		zoomSlider.setValue(value);
		zoomSliderValueLabel.setText(String.valueOf(value) + " %");

		return value;
	}

	/**
	 * If the slider is moved this function will calculate new fit dimensions for
	 * the imageView
	 * 
	 * @author Julian Einspenner
	 */
	@FXML
	private void sliderMove() {

		int value = setAndGetSliderLabel();
		zoomStage = value / 5 - 10;

		double fitWidth = (int) displayImageEditMode.getFitWidth();
		double fitHeight = (int) displayImageEditMode.getFitHeight();
		int imageWidth = (int) displayImageEditMode.getImage().getWidth();
		int imageHeight = (int) displayImageEditMode.getImage().getHeight();

		if (zoomStage == 0) {
			displayImageEditMode.setFitWidth(initFitWidth);
			displayImageEditMode.setFitHeight(initFitHeight);
			setFitDimensionsIfSmallerThanImageViewsMaxSize(imageWidth, imageHeight);
			return;
		} else if (zoomStage > 0) {
			fitWidth = currentImageFitWidth * Math.pow(1.1, zoomStage);
			fitHeight = currentImageFitHeight * Math.pow(1.1, zoomStage);
		} else {
			fitWidth = currentImageFitWidth / Math.pow(1.1, Math.abs(zoomStage));
			fitHeight = currentImageFitHeight / Math.pow(1.1, Math.abs(zoomStage));
		}
		displayImageEditMode.setFitWidth(fitWidth);
		displayImageEditMode.setFitHeight(fitHeight);
	}

	/**
	 * Enhances the GUI with a scrolling event to zoom in and zoom out the displayed
	 * image
	 * 
	 * @author Julian Einspenner
	 */
	private void setScrollingToRootPane() {

		rootPane.setOnScroll(e -> {
			int fitWidth = (int) displayImageEditMode.getFitWidth();
			int fitHeight = (int) displayImageEditMode.getFitHeight();
			int imageWidth = (int) displayImageEditMode.getImage().getWidth();
			int imageHeight = (int) displayImageEditMode.getImage().getHeight();

			double zoomSliderValue = zoomSlider.getValue();

			if (e.getDeltaY() > 0) {
				if (zoomSliderValue < 100) {
					zoomStage--;

					if (zoomStage == 0) {
						displayImageEditMode.setFitWidth(initFitWidth);
						displayImageEditMode.setFitHeight(initFitHeight);
						setFitDimensionsIfSmallerThanImageViewsMaxSize(imageWidth, imageHeight);
					} else {
						displayImageEditMode.setFitWidth(fitWidth * 1.1);
						displayImageEditMode.setFitHeight(fitHeight * 1.1);
					}
					zoomSlider.setValue(zoomSliderValue + 5);
				}
			}

			if (e.getDeltaY() < 0) {
				if (zoomSliderValue > 0) {
					zoomStage++;

					if (zoomStage == 0) {
						displayImageEditMode.setFitWidth(initFitWidth);
						displayImageEditMode.setFitHeight(initFitHeight);
						setFitDimensionsIfSmallerThanImageViewsMaxSize(imageWidth, imageHeight);
					} else {
						displayImageEditMode.setFitWidth(fitWidth / 1.1);
						displayImageEditMode.setFitHeight(fitHeight / 1.1);
					}
					zoomSlider.setValue(zoomSliderValue - 5);
				}
			}
			zoomSliderValueLabel.setText(Integer.toString((int) zoomSlider.getValue()) + " %");
		});
	}

	
	
	// Swipe through images

	/**
	 * This method selects the next ImageContainer in the selected album to be displayed.
	 * 
	 * @author: Phillip Persch
	 */
	@FXML
	void swipeForwards() {
		int newIndex = selectedAlbum.getImages().indexOf(imageContainer) + 1;
		if (newIndex > selectedAlbum.getImages().size() - 1) {
			return;
		}
		imageContainer = selectedAlbum.getImages().get(newIndex);
		image = new Image(imageContainer.getPath());
		imagePlain = new Image(imageContainer.getPath());

		displayImageEditMode.setImage(image);

		resetGUI();
	}

	/**
	 * This method selects the previous ImageContainer in the selected album to be displayed.
	 * 
	 * @author: Phillip Persch
	 */
	@FXML
	void swipeBackwards() {
		int newIndex = selectedAlbum.getImages().indexOf(imageContainer) - 1;
		if (newIndex < 0) {
			return;
		}
		imageContainer = selectedAlbum.getImages().get(newIndex);
		image = new Image(imageContainer.getPath());
		imagePlain = new Image(imageContainer.getPath());
		displayImageEditMode.setImage(image);

		resetGUI();
	}

	/**
	 * This method shows left and right arrow buttons to swipe through images.
	 * It is called when the mouse hovers over the stackPane that contains the imageView.
	 * 
	 * @author: Phillip Persch
	 */	
	@FXML
	private void showSwipeButtons() {
		forwardButton.setVisible(true);
		backwardButton.setVisible(true);
	}

	/**
	 * This method hides left and right arrow buttons to swipe through images.
	 * It is called when the mouse hovers over the stackPane that contains the imageView.
	 * 
	 * @author: Phillip Persch
	 */	
	@FXML
	private void hideSwipeButtons() {
		forwardButton.setVisible(false);
		backwardButton.setVisible(false);
	}
	
	@FXML
	private void deleteImage(Event e) throws SQLException, IOException {
		if (selectedAlbum.getName().equals("All Images")) {
			SendSQLRequest.deleteImageFromDB(MainControllerEditMode.imageContainer);			
		} else {
			SendSQLRequest.deleteImageFromAlbum(selectedAlbum, MainControllerEditMode.imageContainer);
		}	
		switchBack(e);
	}

	// Getters and setters

	/**
	 * Getter for static member image.
	 * 
	 * @author Phillip Persch
	 * @return the image being displayed. 
	 */
	public static Image getImage() {
		return image;
	}

	/**
	 * Getter for member imageView
	 * 
	 * @author: Phillip Persch
	 * @return the imageView holding the current image
	 */
	public ImageView getDisplayImageEditMode() {
		return displayImageEditMode;
	}

	
	private int getSelectedChoiceBoxIndex() {
		return colorChoiceBox.getSelectionModel().getSelectedIndex();
	}

}