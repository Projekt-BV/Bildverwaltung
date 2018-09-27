package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Properties;

import database.SendSQLRequest;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Album;
import model.Database;
import model.FileImport;
import model.ImageContainer;
import model.editing.EditMetaData;
import model.editing.RenameImage;

/**
 * This abstract class gives the basis for both main scenes of the app (gallery scene and edit scene).
 * It offers all the functionalities, that both controllers have in common, to reduce redundant code.
 * 
 * @author Phillip Persch, Tobias Reinert, Mario Anklam, Julian Einspenner
 */
public abstract class MainController {

	// GUI Elements, hierarchy like the hierarchy in Scenebuilder
	// North
	@FXML Menu MenuFile;
	@FXML MenuItem MenuImport;
	@FXML MenuItem MenuCopy;
	@FXML MenuItem MenuRename;
	@FXML MenuItem MenuDelete;
	@FXML MenuItem MenuSave;
	@FXML MenuItem MenuSaveAs;
	@FXML MenuItem MenuExit;
	@FXML Menu MenuHelp;
	@FXML MenuItem MenuAbout;
	@FXML Menu MenuChangeLanguage;
	@FXML MenuItem MenuEnglish;
	@FXML MenuItem MenuGerman;
	@FXML Button ButtonSearch;
	@FXML Button ButtonFScreen;
	@FXML Button ButtonFilter;
	@FXML TextField TextFieldKeyword;
	@FXML DatePicker DatePickerFrom;
	@FXML DatePicker DatePickerTo;
	// West
	@FXML Button ButtonAddAlbum;
	@FXML Button ButtonDeleteAlbum;
	// Center
	@FXML Button ButtonRenameAll;
	@FXML Button ButtonAddImage;
	
	// EditMode
	// North
	@FXML Button ButtonAlbumView;	
	// Center
	@FXML Button ButtonDeleteImage;	
	// East
	@FXML Tab TabEditing;
	@FXML Button TabEditButtonCClock;
	@FXML Button TabEditButtonClock;
	@FXML Button TabEditButtonResize;
	@FXML Text TextWidth;
	@FXML Button TabEditButtonSetRatio;
	@FXML Text TextHeight;
	@FXML Text TextRotate;
	@FXML Button cutModeButton;
	@FXML Text TextCutImage;
	@FXML Text TextColorFilter;
	@FXML Text TextResizeImage;
	@FXML Button TabEditButtonUndo;
	@FXML Button TabEditButtonSave;
	@FXML Tab TabMetadata;
	@FXML Button TabMetaButtonSave;
	@FXML Text TextTitle;
	@FXML Text TextLocation;
	@FXML Text TextTags;
	@FXML Text TextDate;
	@FXML Label pathLabel;	
	// AlbumController GUI
	@FXML Button exitId;
	@FXML Label NewAlbumLabelAlbum;
	@FXML Button NewAlbumButtonAdd;
	@FXML HBox sliderBox;
	@FXML ChoiceBox<String> colorChoiceBox;
	@FXML ListView<Album> listView;
	@FXML AnchorPane rootPane;

	
	// Fields
	Database database = new Database();
	static Album selectedAlbum;
	static boolean didSwitchBack = true;
	protected ImageContainer clickedOnImage;
	protected static String currentLanguage = "en";
	private String controller = "GalleryMode";

	/**
	 * Method to initialize the listView containing the album names.
	 * 
	 * @throws ParseException
	 */
	public void initializeListView() {
		
		// clear current list and reload albums from database
		listView.getItems().clear();
		database.reloadDatabaseContents();
		database.getAlbums().stream().forEach(album -> listView.getItems().add(album));

		// custom listView cells are required to handle drag and drop + context menu
		listView.setCellFactory(lv -> new AlbumListCell());

		// highlight the selected album
		Album referenceEqualAlbum;
		if (selectedAlbum != null) {
			referenceEqualAlbum = listView.getItems().stream()
					.filter(album -> album.getName().equals(selectedAlbum.getName())).findFirst().get();
		} else {
			// if no album is selected, highlight "All Images"
			referenceEqualAlbum = listView.getItems().stream().filter(album -> album.getName().equals("All Images"))
					.findFirst().get();
		}
		
		listView.getSelectionModel().select(referenceEqualAlbum);

	}
	
	/**
	 * Getter for field database.
	 * 
	 * @author Phillip Persch
	 * @return the database this controller works with
	 */
	public Database getDatabase() {
		return this.database;
	}

	
	
	// MenuBar

	// File
	@FXML
	private void importImage() {
		FileImport tmpImp = new FileImport();
		Stage window = new Stage();
		tmpImp.injectMainController((MainControllerGalleryMode) this);
		tmpImp.start(window);
	}

	
	@FXML
	private void deleteImage(Event e) throws SQLException, IOException {
		if (selectedAlbum.getName().equals("All Images")) {
			SendSQLRequest.deleteImageFromDB(MainControllerEditMode.imageContainer);			
		} else {
			SendSQLRequest.deleteImageFromAlbum(selectedAlbum, MainControllerEditMode.imageContainer);
		}	
	}
	
	/**
	 * This method exits the program.
	 * @author Phillip Persch
	 */
	@FXML
	private void exit() {
		System.exit(0);
	}

	// Help
	@FXML
	private void showCommands() throws IOException {
		Stage stage;
		Parent root;
		stage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/design/About.fxml"));
		root = loader.load();
		stage.setScene(new Scene(root));
		if (currentLanguage == "en") {
			stage.setTitle("Help");
		} else {
			stage.setTitle("Hilfe");
		}
		stage.show();
	}

	/**
	 * This method opens a new stage for adding albums to the database.
	 * The new stage is managed by an instance of AlbumController.
	 * 
	 * @author Mario Anklam, Phillip Persch, Tobias Reinert
	 * @throws IOException
	 */
	@FXML
	private void addAlbumButtonPressed() throws IOException {
		Stage stage;
		Parent root;
		stage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/design/NewAlbum.fxml"));
		root = loader.load();

		AlbumController albumController = loader.getController();
		albumController.injectMainController(this);

		stage.setScene(new Scene(root));
		stage.setAlwaysOnTop(true);
		stage.setOnCloseRequest(e -> {
			initializeListView();
			if (this instanceof MainControllerGalleryMode) {
				MainControllerGalleryMode mc = (MainControllerGalleryMode) this;
				mc.initializeTilePane();
			}
		});
		if (currentLanguage == "en") {
			stage.setTitle("New Album");
		} else {
			stage.setTitle("Neues Album");
		}
		stage.getIcons().add(new Image("/design/Nerd_Icon.jpg"));
		stage.show();

	}

	/**
	 * This method deletes the selected album from the database.
	 * 
	 * @param e the event that triggers the deletion of an album
	 * @throws SQLException
	 * @throws IOException
	 */
	@FXML
	private void deleteAlbumButtonPressed(Event e) throws SQLException, IOException {
		
		// never allow the user to delete album "All Images"		
		if (selectedAlbum.getName().equals("All Images")) {
			//TODO: tell the user that he is not allowed.
			return;
		}
		
		SendSQLRequest.deleteAlbum(selectedAlbum);
		selectedAlbum = null;
		
		// depending on the scene, do a different initialization
		initializeListView();
		if (this instanceof MainControllerGalleryMode) {
			((MainControllerGalleryMode) this).initializeTilePane();
		} 
	}

	
	// Bar below Menubar

	/**
	 * This method puts the window to fullscreen or maximized, depending on the class of the caller
	 * 
	 * @author Phillip Persch
	 * @throws IOException
	 */
	@FXML
	private void fullScreenButtonPressed() throws IOException {
		// if edit mode, call FullScreenController
		if (this instanceof MainControllerEditMode) {
			Stage stage;
			Parent root;
			stage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/design/FullScreen.fxml"));
			root = loader.load();

			stage.setScene(new Scene(root));
			stage.setFullScreen(true);
			stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

			FullScreenController fullScreenController = loader.getController();
			fullScreenController.injectMainController((MainControllerEditMode) this);
			fullScreenController.setKeyListener();

			stage.show();
		//if gallery mode, just maximize
		} else {
			
			Stage stage = (Stage) rootPane.getScene().getWindow();
			stage.setMaximized(true);
		}
	}

	// Rename

	/**
	 * This method asks for the new name, calls the renameImage function in EditMetaData and refreshes the model from the database
	 * 
	 * @author Phillip Persch
	 */
	protected void initializeRenameDialog() {
		RenameImage ri = new RenameImage();
		ri.start(new Stage());


		// check if user has put in text before confirming. Empty file paths are not fun
		if (ri.getResult().isPresent() && ri.getResult() != null && !ri.getResult().get().trim().equals("")) {
			EditMetaData.renameImage(clickedOnImage, ri.getResult().get());
			initializeListView();
			if (MainController.this instanceof MainControllerGalleryMode) {
				((MainControllerGalleryMode) MainController.this).initializeTilePane();
			}
		}
	}

	@FXML
	public void controllerCheck(String controller) {
		this.controller = controller;

		if (currentLanguage == "de") {
			changeToGerman();
		} else {
			changeToEnglish();
		}
		changeLanguage();
	}

	@FXML
	public void changeToGerman() {
		currentLanguage = "de";
		changeLanguage();

		switch (controller) {
		case ("GalleryMode"):
			ButtonAddImage.setPrefWidth(115);
			ButtonRenameAll.setLayoutX(510);
			break;

		case ("EditMode"):
			ButtonDeleteImage.setLayoutX(701);
			colorChoiceBox.setPrefWidth(105);
			break;
		}
	}

	@FXML
	public void changeToEnglish() {
		currentLanguage = "en";
		changeLanguage();

		switch (controller) {
		case ("GalleryMode"):
			ButtonAddImage.setPrefWidth(98);
			ButtonRenameAll.setLayoutX(545);
			break;

		case ("EditMode"):
		    colorChoiceBox.setPrefWidth(99);
			break;
		}
	}

	/**
	 * Allows to switch between languages
	 * 
	 * @author Tobias Reinert
	 * @throws IOException
	 */
	@FXML
	public void changeLanguage() {
		Properties config;
		config = new Properties();
		FileInputStream fis;

		try {
			fis = new FileInputStream("src/main/java/LangBundle_" + currentLanguage + ".properties");
			config.load(fis);

		} catch (IOException io) {
			io.printStackTrace();
		}

		// ChangeGui
		switch (controller) {
		case ("EditMode"):
			// BorderPane East
			MenuSave.setText(config.getProperty("MenuBar-File-Save"));
			MenuSaveAs.setText(config.getProperty("MenuBar-File-SaveAs"));
			MenuExit.setText(config.getProperty("MenuBar-File-Exit"));
			TabEditing.setText(config.getProperty("Tab-Editing"));
			TabEditButtonCClock.setText(config.getProperty("Tab-Editing-Button-CClock"));
			TabEditButtonClock.setText(config.getProperty("Tab-Editing-Button-Clockwise"));
			TabEditButtonResize.setText(config.getProperty("Tab-Editing-Button-Resize"));
			TextWidth.setText(config.getProperty("Tab-Editing-Text-Width"));
			TabEditButtonSetRatio.setText(config.getProperty("Tab-Editing-Button-SetRatio"));
			TextHeight.setText(config.getProperty("Tab-Editing-Text-Height"));
			TextRotate.setText(config.getProperty("Tab-Editing-Text-Rotate"));
			cutModeButton.setText(config.getProperty("Tab-Editing-Button-CutMode"));
			TextCutImage.setText(config.getProperty("Tab-Editing-Text-CutImage"));
			TextColorFilter.setText(config.getProperty("Tab-Editing-Text-ColorFilter"));
			TextResizeImage.setText(config.getProperty("Tab-Editing-Text-ResizeImage"));
			TabEditButtonUndo.setText(config.getProperty("Tab-Editing-Button-Undo"));
			TabEditButtonSave.setText(config.getProperty("Tab-Editing-Button-Save"));
			TabMetadata.setText(config.getProperty("Tab-Metadata"));
			TabMetaButtonSave.setText(config.getProperty("Tab-Metadata-Button-Save"));
			TextTitle.setText(config.getProperty("Tab-Metadata-Text-Title"));
			TextLocation.setText(config.getProperty("Tab-Metadata-Text-Location"));
			TextTags.setText(config.getProperty("Tab-Metadata-Text-Tags"));
			TextDate.setText(config.getProperty("Tab-Metadata-Text-Date"));
			pathLabel.setText(config.getProperty("Tab-Metadata-Label-Path"));

			ButtonAlbumView.setText(config.getProperty("EditMode-Button-AlbumView"));

		case ("GalleryMode"):
			// BorderPane North
			// MenuBar
			MenuFile.setText(config.getProperty("MenuBar-File"));
			// MenuImport Move to if cause below;
			MenuHelp.setText(config.getProperty("MenuBar-Help"));
			MenuAbout.setText(config.getProperty("MenuBar-Help-About"));
			MenuChangeLanguage.setText(config.getProperty("MenuBar-Help-Language"));
			MenuEnglish.setText(config.getProperty("MenuBar-Help-Language-English"));
			MenuGerman.setText(config.getProperty("MenuBar-Help-Language-German"));
			// North
			ButtonFScreen.setText(config.getProperty("Button-FScreen"));
			// BorderPane West
			ButtonAddAlbum.setText(config.getProperty("Button-AddAlbum"));
			if (currentLanguage == "en") {
				ButtonAddAlbum.setPrefWidth(106);
			} else {
				ButtonAddAlbum.setPrefWidth(120);
			}
			ButtonDeleteAlbum.setText(config.getProperty("Button-DeleteAlbum"));

			// BorderPane Center
			if (controller == "GalleryMode") {
				ButtonRenameAll.setText(config.getProperty("Button-RenameAll"));
				ButtonAddImage.setText(config.getProperty("Button-AddImage"));
				// North
				MenuImport.setText(config.getProperty("MenuBar-File-Import"));
			}
			break;

		case ("AddAlbum"):
			exitId.setText(config.getProperty("NewAlbum-Button-Exit"));
			NewAlbumLabelAlbum.setText(config.getProperty("NewAlbum-Label-Album"));
			NewAlbumButtonAdd.setText(config.getProperty("NewAlbum-Button-Add"));
			if (currentLanguage == "de") {
				NewAlbumButtonAdd.setLayoutX(172);
			} else {
				NewAlbumButtonAdd.setLayoutX(211);
			}
		}
	}

	/**
	 * This class defines custom cells for the listView.
	 *  
	 * @author Phillip Persch
	 */
	class AlbumListCell extends ListCell<Album> {

		private ContextMenu contextMenu;

		/**
		 * Constructor for AlbumListCell objects.
		 * 
		 * @author Phillip Persch
		 */
		public AlbumListCell() {

			contextMenu = new ContextMenu();

			
			// this gets called when an ImageContainer is dropped on the cell.
			// It is added to the album.
			this.setOnDragDropped(e -> {
				ImageContainer image = (ImageContainer) e.getDragboard()
						.getContent(MainControllerGalleryMode.imageContainerFormat);

				String sqlRequest = "INSERT INTO albumfoto (AlbumID, FotoID) VALUES" + "(" + this.getItem().getId()
						+ ", '" + image.getId() + "');";

				Album referenceEqualAlbum = database.getAlbums().stream().filter(a -> a.getId() == getItem().getId())
						.findAny().get();

				selectedAlbum = referenceEqualAlbum;

				try {
					// No that's not an SQL request in controller code :|
					ResultSet tmpRs = SendSQLRequest.sendSQL(sqlRequest);
				} catch (SQLException ex) {
					System.out.println("Bild schon in Album.");
					return;
				}
				e.setDropCompleted(true);

				initializeListView();
				if (MainController.this instanceof MainControllerGalleryMode) {
					((MainControllerGalleryMode) MainController.this).initializeTilePane();
				}
			});
		}

		/**
		 * This method sets visuals and functionality for each cell.
		 * 
		 * @author Phillip Persch
		 * @param item the album the cell belongs to
		 * @param empty tells if the cell is empty
		 */
		@Override
		protected void updateItem(Album item, boolean empty) {
			super.updateItem(item, empty);
			if (empty) {
				return;
			}
			
			contextMenu = new ContextMenu();

			// don't allow user to rename or delete album "All Images"
			if (!getItem().getName().equals("All Images")) {
				MenuItem delete = new MenuItem("Delete");

				
				// action for delete menu item
				delete.setOnAction(event -> {
					try {
						SendSQLRequest.deleteAlbum(this.getItem());
						selectedAlbum = database.getAlbums().get(0);
						initializeListView();
						if (MainController.this instanceof MainControllerGalleryMode) {
							MainControllerGalleryMode mc = (MainControllerGalleryMode) MainController.this;
							mc.initializeTilePane();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				});
				
				contextMenu.getItems().addAll(delete);
			}
			this.textProperty().bind(this.itemProperty().get().nameProperty());
			setContextMenu(contextMenu);
			}
		

	}
}
