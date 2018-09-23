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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Album;
import model.Database;
import model.ImageContainer;
import model.editing.FileImport;

public abstract class MainController {

	// GUI Elements, hierarchy like the hierarchy in Scenebuilder
	// North
	@FXML
	Menu MenuFile;
	@FXML
	MenuItem MenuImport;
	@FXML
	MenuItem MenuCopy;
	@FXML
	MenuItem MenuRename;
	@FXML
	MenuItem MenuDelete;
	@FXML
	MenuItem MenuSave;
	@FXML
	MenuItem MenuSaveAs;
	@FXML
	MenuItem MenuExit;
	@FXML
	Menu MenuHelp;
	@FXML
	MenuItem MenuAbout;
	@FXML
	Menu MenuChangeLanguage;
	@FXML
	MenuItem MenuEnglish;
	@FXML
	MenuItem MenuGerman;
	@FXML
	Button ButtonSearch;
	@FXML
	Button ButtonFScreen;
	@FXML
	Button ButtonFilter;
	@FXML
	TextField TextFieldKeyword;
	@FXML
	DatePicker DatePickerFrom;
	@FXML
	DatePicker DatePickerTo;
	// West
	@FXML
	Button ButtonAddAlbum;
	@FXML
	Button ButtonDeleteAlbum;
	// Center
	@FXML
	Button ButtonRenameAll;
	@FXML
	Button ButtonAddImage;
	// EditMode
	// North
	@FXML
	Button ButtonAlbumView;
	// Center
	@FXML
	Button ButtonDeleteImage;
	// East
	@FXML
	Tab TabEditing;
	@FXML
	Button TabEditButtonCClock;
	@FXML
	Button TabEditButtonClock;
	@FXML
	Button TabEditButtonResize;
	@FXML
	Text TextWidth;
	@FXML
	Button TabEditButtonSetRatio;
	@FXML
	Text TextHeight;
	@FXML
	Text TextRotate;
	@FXML
	Button cutModeButton;
	@FXML
	Text TextCutImage;
	@FXML
	Text TextColorFilter;//
	@FXML
	Text TextResizeImage;
	@FXML
	Button TabEditButtonUndo;
	@FXML
	Button TabEditButtonSave;
	@FXML
	Tab TabMetadata;
	@FXML
	Button TabMetaButtonSave;
	@FXML
	Text TextTitle;
	@FXML
	Text TextLocation;
	@FXML
	Text TextTags;
	@FXML
	Text TextDate;
	@FXML
	Label pathLabel;
	// NewAlbum GUI
	@FXML
	Button exitId;
	@FXML
	Label NewAlbumLabelAlbum;
	@FXML
	Button NewAlbumButtonAdd;

	// Method to rewrite Choicebox
	// Tab-Editing-ChoiceBox1=kein Filter
	// Tab-Editing-ChoiceBox2=Schwarz-Wei�
	// Tab-Editing-ChoiceBox3=Rot
	// Tab-Editing-ChoiceBox4=Gr�n
	// Tab-Editing-ChoiceBox5=Blau
	// Tab-Editing-ChoiceBox6=Gelb
	// Tab-Editing-ChoiceBox7=Violet
	// Tab-Editing-ChoiceBox8=Aqua

	@FXML
	ListView<Album> listView;
	@FXML
	AnchorPane rootPane;

	Database database = new Database();

	static Album selectedAlbum;
	static boolean didSwitchBack = true;
	private static String currentLanguage = "en";
	private String controller = "GalleryMode";

	/**
	 * Method to initialize the listView containing the album names.
	 * 
	 * @throws ParseException
	 */
	public void initializeListView() {
		listView.getItems().clear();
		database.reloadDatabaseContents();
		database.getAlbums().stream().forEach(album -> listView.getItems().add(album));

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

	public Database getDatabase() {
		return this.database;
	}

	// Navigation between the main controllers
	@FXML
	private void switchScene(Event event) throws IOException {
		Parent pane = FXMLLoader.load(getClass().getResource("/design/Main_page_edit_mode.fxml"));
		Scene changePane = new Scene(pane);

		// Show stage information
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(changePane);
		window.show();
	}

	@FXML
	private void switchBack(Event event) throws IOException {
		selectedAlbum = listView.getSelectionModel().getSelectedItem();
		didSwitchBack = true;

		Parent pane = FXMLLoader.load(getClass().getResource("/design/Main_page_2.4.fxml"));
		Scene changePane = new Scene(pane);
		// Show stage information

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(changePane);
		window.show();
	}

	// Menue-Bar----------------------------------------------------

	// File
	@FXML
	private void importImage() {
		FileImport tmpImp = new FileImport();
		Stage window = new Stage();
		tmpImp.injectMainController((MainControllerGalleryMode) this);
		tmpImp.start(window);
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

	// Help
	@FXML
	private void showCommands() {
		System.out.println("I am the showCommands function");
	}

	// Buttons below listView
	@FXML
	private void addAlbumButtonPressed() throws IOException {
		Stage stage;
		Parent root;
		stage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/design/NewAlbum.fxml"));
		root = loader.load();

		AlbumController albumController = loader.getController();
		albumController.injectMainController((MainControllerGalleryMode) this);

		stage.setScene(new Scene(root));
		stage.setTitle("New Album");
		stage.show();

	}

	@FXML
	private void deleteAlbumButtonPressed() {
		System.out.println("I am the deleteAlbumButtonPressed function");
	}

	// Bar below Menubar

	@FXML
	private void browseButtonPressed() {
		System.out.println("I am the browseButtonPressed function");
	}

	@FXML
	private void fullScreenButtonPressed() throws IOException {
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
			fullScreenController.setExitListener();

			stage.show();
		} else {
			Stage stage = (Stage) rootPane.getScene().getWindow();
			stage.setFullScreen(true);
		}
	}

	// Bar above gridPane / ImageView
	@FXML
	private void dropdownButtonChoiceSelected() {
		System.out.println("I am the dropdownButtonChoiceSelected function");
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
		case("GalleryMode"):
			ButtonAddImage.setPrefWidth(115);
			ButtonRenameAll.setLayoutX(510);
		break;
		
		case("EditMode"):
			ButtonDeleteImage.setLayoutX(701);
		break;
		}
	}

	@FXML
	public void changeToEnglish() {
		currentLanguage = "en";
		changeLanguage();
		
		switch (controller) {
		case("GalleryMode"):
			ButtonAddImage.setPrefWidth(98);
			ButtonRenameAll.setLayoutX(545);
		break;
		
		case("EditMode"):
			ButtonDeleteImage.setLayoutX(694);
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
		  //BorderPane East
			TabEditing.setText(config.getProperty("Tab-Editing"));
			TabEditButtonCClock.setText(config.getProperty("Tab-Editing-Button-CClock"));
			TabEditButtonClock.setText(config.getProperty("Tab-Editing-Button-Clockwise"));
			TabEditButtonResize.setText(config.getProperty("Tab-Editing-Button-Resize"));
			TextWidth.setText(config.getProperty("Tab-Editing-Text-Width"));
			TabEditButtonSetRatio.setText(config.getProperty("Tab-Editing-Button-SetRatio"));
			TextHeight.setText(config.getProperty("Tab-Editing-TextHeight"));
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
			ButtonDeleteImage.setText(config.getProperty("EditMode-Button-DeleteImage"));

		case ("GalleryMode"):
		   //BorderPane North
		   //MenuBar
			 MenuFile.setText(config.getProperty("MenuBar-File"));
			 //MenuImport Move to if cause below;
			 MenuCopy.setText(config.getProperty("MenuBar-File-Copy"));
			 MenuRename.setText(config.getProperty("MenuBar-File-Rename"));
			 MenuDelete.setText(config.getProperty("MenuBar-File-Delete"));
			 MenuSave.setText(config.getProperty("MenuBar-File-Save"));
			 MenuSaveAs.setText(config.getProperty("MenuBar-File-SaveAs"));
			 MenuExit.setText(config.getProperty("MenuBar-File-Exit"));
			 MenuHelp.setText(config.getProperty("MenuBar-Help"));
			 MenuAbout.setText(config.getProperty("MenuBar-Help-About"));
			 MenuChangeLanguage.setText(config.getProperty("MenuBar-Help-Language"));
			 MenuEnglish.setText(config.getProperty("MenuBar-Help-Language-English"));
			 MenuGerman.setText(config.getProperty("MenuBar-Help-Language-German"));
		   //North
			 ButtonSearch.setText(config.getProperty("Button-Search"));
			 ButtonFScreen.setText(config.getProperty("Button-FScreen"));
			 ButtonFilter.setText(config.getProperty("Button-Filter"));
			 TextFieldKeyword.setText(config.getProperty("TextField-Keyword"));
			 DatePickerFrom.setAccessibleText(config.getProperty("DatePicker-From"));
			 DatePickerTo.setAccessibleText(config.getProperty("DatePicker-To"));
		   //BorderPane West
			// ButtonAddAlbum.setText(config.getProperty("AddAlbum"));
			// ButtonDeleteAlbum.setText(config.getProperty("DeleteAlbum"));
			
		   //BorderPane Center
			if (controller == "GalleryMode") {
				ButtonRenameAll.setText(config.getProperty("Button-RenameAll"));
				ButtonAddImage.setText(config.getProperty("Button-AddImage"));
		   //North
				MenuImport.setText(config.getProperty("MenuBar-File-Import"));
			}
			break;

		case ("AddAlbum"):
			exitId.setText(config.getProperty("NewAlbum-Button-Exit"));
			NewAlbumLabelAlbum.setText(config.getProperty("NewAlbum-Label-Album"));
			NewAlbumButtonAdd.setText(config.getProperty("NewAlbum-Button-Add"));
			if (currentLanguage == "de") {NewAlbumButtonAdd.setLayoutX(172);}
			else {NewAlbumButtonAdd.setLayoutX(211);}
		}
	}

	class AlbumListCell extends ListCell<Album> {

		private final ContextMenu contextMenu;

		public AlbumListCell() {
			contextMenu = new ContextMenu();
			MenuItem delete = new MenuItem("Delete");
			contextMenu.getItems().addAll(delete);

			// delete.setOnAction(e -> //TODO);

			this.setOnDragDropped(e -> {
				ImageContainer image = (ImageContainer) e.getDragboard()
						.getContent(MainControllerGalleryMode.imageContainerFormat);

				String sqlRequest = "INSERT INTO albumfoto (AlbumID, FotoID) VALUES" + "(" + this.getItem().getId()
						+ ", '" + image.getId() + "');";
				try {
					ResultSet tmpRs = SendSQLRequest.sendSQL(sqlRequest);
				} catch (SQLException ex) {
					System.out.println("Bild schon in Album.");
					return;
				}
				e.setDropCompleted(true);

				Album referenceEqualAlbum = listView.getItems().stream().filter(a -> a.getId() == getItem().getId())
						.findAny().get();

				selectedAlbum = referenceEqualAlbum;
				initializeListView();
				if (MainController.this instanceof MainControllerGalleryMode) {
					((MainControllerGalleryMode) MainController.this).initializeGridPane();
				}
			});
		}

		@Override
		protected void updateItem(Album item, boolean empty) {
			super.updateItem(item, empty);
			if (!empty) {
				this.textProperty().bind(this.itemProperty().get().nameProperty());
				setContextMenu(contextMenu);
			}
		}

	}
}
