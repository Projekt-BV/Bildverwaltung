package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import database.SendSQLRequest;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import model.ImageContainer;
import model.editing.EditMetaData;
import model.editing.Resizer;

/**
 * This class manages the gallery scene. (first main scene)
 * @author Phillip Persch, Julian Einspenner, Mario Anklam, Tobias Reinert
 */
public class MainControllerGalleryMode extends MainController implements Initializable {	
	
	// FXML Fields
	@FXML private AnchorPane rootPane;
	@FXML private TilePane tilePane;
	@FXML private ProgressIndicator progressIndicator;
	@FXML private ImageView displayImage;
	@FXML TextField renameAllTextField;
	
	
	// Fields
	public static DataFormat imageContainerFormat = new DataFormat("model.ImageContainer");
	private Image imageToDownScale;
	private ContextMenu contextMenu;
	private boolean refreshing = false;
	boolean actionWasDragAndNoClick = false;

	/**
	 * This method initializes the controller.
	 * @param arg0 inhereted from superclass
	 * @param arg1 inhereted from superclass
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		didSwitchBack = true;
		initializeListView();
		initializeTilePane();
		initializeContextMenu();
		controllerCheck("GalleryMode");		
	}

	/**
	 * Method to initialize the tilePane containing each album's images.
	 * This method does way too much.
	 */
	@FXML
	void initializeTilePane() {

		if (refreshing == true) {
			return;
		}

		// 1. Clear current tile pane
		
		refreshing = true;
		if (!tilePane.getChildren().isEmpty())
			tilePane.getChildren().clear();

		
		
		// 2. Check where this was called from and assign field selectedAlbum accordingly		
		
		if (didSwitchBack && selectedAlbum != null) {
			// if we come from Edit mode, leave selectedAlbum as it is and highlight it in listView

			selectedAlbum = database.getAlbums().stream()
					.filter(album -> album.getName().equals(selectedAlbum.getName())).findFirst().get();

			listView.getSelectionModel().select(database.getAlbums().indexOf(selectedAlbum));
		} else if (selectedAlbum == null) {
			// else if no album has been clicked on, get album "All Images"
			selectedAlbum = database.getAlbums().stream().filter(album -> album.getName().equals("All Images"))
					.findFirst().get();
		} else {
			// else get album that has been clicked on
			String selectedAlbumName = listView.getSelectionModel().getSelectedItem().getName();
			selectedAlbum = database.getAlbums().stream().filter(album -> album.getName().equals(selectedAlbumName))
					.findFirst().get();

		}

		didSwitchBack = false;

		
		// separate thread to keep UI responsive
		new Thread(() -> {

			// 3. setup progress indicator
			progressIndicator.toFront();
			progressIndicator.setProgress(0.0);
			progressIndicator.setVisible(true);
			tilePane.setOpacity(0.5);

			for (ImageContainer ic : selectedAlbum.getImages()) {


				// call to garbageCollector, because javafx.scene.Image objects take up a lot of memory
				this.imageToDownScale = new Image(ic.getPath());
				System.gc();

				// 4. images are scaled down for better performance
				int newWidth = 300;
				int newHeight = (int) ((newWidth / imageToDownScale.getWidth()) * imageToDownScale.getHeight());
				imageToDownScale = Resizer.resizeImage(newWidth, newHeight, imageToDownScale);

				// 5. switch back to javafx thread to change UI
				// semaphore to make this thread wait for UI to finish
				final CountDownLatch latch = new CountDownLatch(1);

				Platform.runLater(() -> {
					ImageView imageView = new ImageView(imageToDownScale);
					imageView.setFitHeight(130);
					imageView.setFitWidth(135);

					tilePane.getChildren().add(imageView);
					double currentProgress = progressIndicator.getProgress();
					int numberOfImages = selectedAlbum.getImages().size();
					progressIndicator.setProgress(currentProgress += (1.0 / numberOfImages));

					latch.countDown();
				});
				
				try {
					latch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			progressIndicator.setVisible(false);
			tilePane.setOpacity(1.0);
			refreshing = false;
		}).start();

	}

	/**
	 * This method handles the click on an image in the tile pane.
	 * The result is the transition to the edit scene.
	 * @param e the mouse click on an image that triggered this method call
	 * @throws IOException
	 */
	@FXML
	private void tilePaneImagePressed(MouseEvent e) throws IOException {

		/*
		 * this check is to make sure that the scene is not switched after a drag and
		 * drop ends on an image. Boolean actionWasDragAndNoClick is set to true when
		 * drag is detected
		 */		
		if (actionWasDragAndNoClick) {
			actionWasDragAndNoClick = false;
			return;
		}

		try {
			
		ImageView imageView = (ImageView) e.getPickResult().getIntersectedNode();
		int indexOfImageView = tilePane.getChildren().indexOf(imageView);
		clickedOnImage = selectedAlbum.getImages().get(indexOfImageView);
		
		} catch (ClassCastException e1) {
			// Click was on background of tilePane. Doing nothing is fine.
			return;
		}
		

		// if right mouse button was clicked, don't open detail view, but show context
		// menu
		if (e.getButton() == MouseButton.SECONDARY) {
			return;
		}

		MainControllerEditMode.imageContainer = clickedOnImage;
		// switch the scene to edit mode
		switchScene(e);
	}
	
	
	// navigation
	
	/**
	 * This method causes the scene to switch to the edit scene.
	 * @param e the mouse event that triggered the scene switch
	 * @throws IOException
	 */
	private void switchScene(MouseEvent e) throws IOException {
		Parent pane = FXMLLoader.load(getClass().getResource("/design/Main_page_edit_mode.fxml"));
		// Show stage information
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.getScene().setRoot(pane);		
		window.setMinWidth(1280);
		window.setMinHeight(767);
		window.show();
	}

	/**
	 * This method should get called whenever the gallery scene needs to be reloaded.
	 */	
	public void reloadMainPage() {
		initializeListView();
		initializeTilePane();
	}

	
	// Drag and drop

	/**
	 * This method handles the start of the drag and drop event.
	 * It determines which image has been selected an initiates the procedure.
	 * @param e the start of the drag and drop event
	 */
	@FXML
	private void imageDragStarted(MouseEvent e) {
		actionWasDragAndNoClick = true;
		Dragboard dragBoard = tilePane.startDragAndDrop(TransferMode.ANY);
		ClipboardContent clipBoard = new ClipboardContent();

		ImageView imageView = (ImageView) e.getPickResult().getIntersectedNode();
		int indexOfSelectedImage = tilePane.getChildren().indexOf(imageView);
		System.out.println(indexOfSelectedImage);
		ImageContainer image = selectedAlbum.getImages().get(indexOfSelectedImage);

		clipBoard.put(imageContainerFormat, image);

		dragBoard.setContent(clipBoard);
		e.consume();
	}

	/**
	 * This method gets called when something is dragged over the tile pane, but not yet dropped.
	 * @param e the event of the mouse being dragged over the tile pane
	 */
	@FXML
	private void imageDragOver(DragEvent e) {
		System.out.println("dragover");
		if (e.getDragboard().hasContent(imageContainerFormat)) {
			e.acceptTransferModes(TransferMode.ANY);
		}
		e.consume();
	}

	// Bar above tilePane
	
	/**
	 * This method renames all images in the selected album.
	 * If the corresponding text field is not empty, all images are renamed to:
	 * the textfield's text + an incrementing integer.
	 * 
	 * For example:
	 * text field's text: "Xmas"
	 * new image names: "Xmas", "Xmas1", "Xmas2"...
	 */
	@FXML
	private void renameAllButtonPressed() {
		if (renameAllTextField.getText().isEmpty()) {
			return;
		}
		selectedAlbum.getImages().stream().forEach(i -> EditMetaData.renameImage(i, renameAllTextField.getText()));
		reloadMainPage();
	}

	// ContextMenu
	/**
	 * This method initializes the context menu on the tile pane, including all menu items and action listeners.
	 * There are two delete operations:
	 * 1) delete from album: deletes the image only from the currently selected album
	 * 2) delete from all albums: deletes the image from the entire database
	 * 
	 * There is no operation to delete images from the file system.
	 */
	private void initializeContextMenu() {
		Properties config;
		config = new Properties();
		FileInputStream fis;

		try {
			fis = new FileInputStream("src/main/java/LangBundle_" + currentLanguage + ".properties");
			config.load(fis);

		} catch (IOException io) {
			io.printStackTrace();
		}
		
		contextMenu = new ContextMenu();
	
		// Menu item rename + action listener
		MenuItem rename = new MenuItem(config.getProperty("ContextRename"));
		rename.setOnAction(e -> initializeRenameDialog());
		
		
		// Menu item delete from album + action listener
		MenuItem deleteImageFromAlbum = new MenuItem(config.getProperty("Context-Delete-From-Album"));
		deleteImageFromAlbum.setOnAction(event -> {
			try {
				SendSQLRequest.deleteImageFromAlbum(selectedAlbum, clickedOnImage);
				reloadMainPage();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		});

		
		// Menu item delete from all albums + action listener
		MenuItem deleteImageFromAllAlbums = new MenuItem(config.getProperty("Context-Delete-From-All-Albums"));
		deleteImageFromAllAlbums.setOnAction(event -> {
			try {
				SendSQLRequest.deleteImageFromDB(clickedOnImage);
				reloadMainPage();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});

		contextMenu.getItems().addAll(rename, deleteImageFromAlbum, deleteImageFromAllAlbums);
		contextMenu.setOpacity(1);
	}

	/**
	 * This method shows the context menu over the selected image when right clicked.
	 * @param e right mouse click 
	 */
	@FXML
	private void contextMenuRequested(ContextMenuEvent e) {
		ImageView imageView = (ImageView) e.getPickResult().getIntersectedNode();
		int indexOfImageView = tilePane.getChildren().indexOf(imageView);
		clickedOnImage = selectedAlbum.getImages().get(indexOfImageView);

		contextMenu.show(tilePane, e.getScreenX(), e.getScreenY());
	}

	/**
	 * Filters the displayed images in gallery. Checks if the tags contain the
	 * keyword or the date is between two other dates
	 */
	@FXML
	private void filterButtonPressed() {
		String keyword = TextFieldKeyword.getText();
		
		Date min = convertToDateViaSqlDate(DatePickerFrom.getValue());
		Date max = convertToDateViaSqlDate(DatePickerTo.getValue());
		
		boolean keywordOnly = keyword != null && !keyword.equals("") &&
							  (!keyword.equals("Keyword") && !keyword.equals("Schlüsselwort")) && 
							  (min == null || max == null);
		
		boolean dateOnly    = (keyword.equals("Keyword") || keyword.equals("Schlüsselwort") || keyword.equals("")) &&
							  (min != null && max != null);
		
		boolean booth       = keyword != null && !keyword.equals("") &&
							  (!keyword.equals("Keyword") && !keyword.equals("Schlüsselwort")) &&
							  (min != null && max != null);
							
		TreeSet<Integer> idSet = new TreeSet<Integer>();
		
		if(keywordOnly) {
			filterGalleryImages(filterByKeyword(keyword, idSet));
			return;
		}else if(dateOnly) {
			filterGalleryImages(filterByDate(min, max, idSet));
			return;
		}else if(booth){
			filterGalleryImages(filterByKeywordAndDate(keyword, min, max, idSet));
		}
	}

	/**
	 * Converts from LocalDate() to Date()
	 * @param dateToConvert is the LocalDate object to convert
	 * @return is the new Date
	 */
	private Date convertToDateViaSqlDate(LocalDate dateToConvert) {
		if(dateToConvert == null ) {
			return null;
		}
		return java.sql.Date.valueOf(dateToConvert);
	}
	
	/**
	 * Filters images by keyword
	 * @param keyword is the keyword to filter
	 * @param idSet is a Set of image ID's. Will be stocked up, if filter matches
	 * @return the idSet
	 */
	private TreeSet<Integer> filterByKeyword(String keyword, TreeSet<Integer> idSet){
		for (ImageContainer ic : selectedAlbum.getImages()) {
			for (String tag : ic.getTags()) {
				if (tag.contains(keyword)) {
					idSet.add(ic.getId());
					break;
				}
			}
		}
		return idSet;
	}
	
	/**
	 * Filters images by date
	 * @param min the "from"-Date
	 * @param max the "to"-Date
	 * @param idSet is a Set of image ID's. Will be stocked up, if filter matches
	 * @return the idSet
	 */
	private TreeSet<Integer> filterByDate(Date min, Date max, TreeSet<Integer> idSet) {
		for (ImageContainer ic : selectedAlbum.getImages()) {
			Date date = ic.getDate();
			if (date.before(max) && date.after(min) || date.equals(max) || date.equals(min)) {
				idSet.add(ic.getId());
			}
		}
		return idSet;
	}
	
	/**
	 * Filters images by keyword AND date
	 * @param keyword is the keyword to filter
	 * @param min the "from"-Date
	 * @param max the "to"-Date
	 * @param idSet is a Set of image ID's. Will be stocked up, if filter matches
	 * @return the idSet
	 */
	private TreeSet<Integer> filterByKeywordAndDate(String keyword, Date min, Date max, TreeSet<Integer> idSet) {
		for (ImageContainer ic : selectedAlbum.getImages()) {
			Date date = ic.getDate();
			for (String tag : ic.getTags()) {
				if (tag.contains(keyword) && 
					(date.before(max) && date.after(min) || date.equals(max) || date.equals(min))) {
					idSet.add(ic.getId());
					break;
				}
			}
		}
		return idSet;
	}
	
	
	/**
	 * This method puts the images that match the filter criteria into the tile pane.
	 * @param idSet a set of image IDs, representing imageContainers who meet the filter criteria
	 */
	private void filterGalleryImages(TreeSet<Integer> idSet) {
		ArrayList<ImageContainer> filteredImages = selectedAlbum.getImages()
																.stream()
																.filter(i -> idSet.contains(i.getId()))
																.collect(Collectors.toCollection(ArrayList::new));
		
		filteredImages.stream().forEach(i -> System.out.println(i));
		selectedAlbum.setImages(filteredImages);
		initializeTilePane();
	}

}