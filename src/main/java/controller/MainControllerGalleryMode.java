package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

import database.SendSQLRequest;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.ImageContainer;
import model.editing.EditMetaData;
import model.editing.Resizer;

public class MainControllerGalleryMode extends MainController implements Initializable {

	public static DataFormat imageContainerFormat = new DataFormat("model.ImageContainer");;
	@FXML
	private AnchorPane rootPane;
	@FXML
	private GridPane gridPane;
	@FXML
	private ProgressIndicator progressIndicator;
	@FXML
	private ImageView displayImage;
	@FXML
	TextField renameAllTextField;

	private Image imageToDownScale;
	private ContextMenu contextMenu;
	private int col = 0;
	private int line = 0;
	private boolean refreshing = false;
	boolean actionWasDragAndNoClick = false;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		didSwitchBack = true;
		initializeListView();
		initializeGridPane();
		initializeContextMenu();
		controllerCheck("GalleryMode");
	}

	/**
	 * Method to initialize the gridPane containing each album's images.
	 */
	@FXML
	void initializeGridPane() {

		if (refreshing == true) {
			return;
		}

		refreshing = true;
		gridPane.getChildren().clear(); // clear gridPane

		if (didSwitchBack && selectedAlbum != null) {
			// if we come from Edit mode, leave selectedAlbum as it is and highlight it in
			// listView

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

		// downscale all images for performance, load images into grid
		// Separate thread to keep UI responsive
		col = 0;
		line = 0;

		new Thread(() -> {

			progressIndicator.toFront();
			progressIndicator.setProgress(0.0);
			progressIndicator.setVisible(true);
			gridPane.setOpacity(0.5);

			for (ImageContainer ic : selectedAlbum.getImages()) {

				// Semaphore, so that col and line are not incremented before UI is updated
				final CountDownLatch latch = new CountDownLatch(1);

				// Call to garbageCollector, because Image takes up a lot of memory
				this.imageToDownScale = new Image(ic.getPath());
				System.gc();

				int newWidth = 300;
				int newHeight = (int) ((newWidth / imageToDownScale.getWidth()) * imageToDownScale.getHeight());
				imageToDownScale = Resizer.resizeImage(newWidth, newHeight, imageToDownScale);

				Platform.runLater(() -> {
					ImageView imageView = new ImageView(imageToDownScale);
					imageView.setFitHeight(130);
					imageView.setFitWidth(135);

					gridPane.getChildren().add(imageView);
					double currentProgress = progressIndicator.getProgress();
					int numberOfImages = selectedAlbum.getImages().size();
					progressIndicator.setProgress(currentProgress += (1.0 / numberOfImages));

					GridPane.setConstraints(imageView, col, line, 1, 1);
					latch.countDown();
				});
				try {
					latch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (col > 5) {
					col = 0;
					line++;
				} else
					col++;
			}
			progressIndicator.setVisible(false);
			gridPane.setOpacity(1.0);
			refreshing = false;
		}).start();

	}

	@FXML
	private void gridPaneImagePressed(MouseEvent e) throws IOException {

		System.out.println("I was called");
		// this check is to make sure, that the scene is not switched after a drag and
		// drop ends on an image. Boolean actionWasDragAndNoClick is set to true when
		// drag is detected
		if (actionWasDragAndNoClick) {
			actionWasDragAndNoClick = false;
			return;
		}

		ImageView imageView = (ImageView) e.getPickResult().getIntersectedNode();
		int indexOfImageView = gridPane.getChildren().indexOf(imageView);
		clickedOnImage = selectedAlbum.getImages().get(indexOfImageView);

		// if right mouse button was clicked, don't open detail view, but show context
		// menu
		if (e.getButton() == MouseButton.SECONDARY) {
			return;
		}

		MainControllerEditMode.imageContainer = clickedOnImage;

		Parent pane = FXMLLoader.load(getClass().getResource("/design/Main_page_edit_mode.fxml"));
		Scene changePane = new Scene(pane);

		// Show stage information
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.setScene(changePane);
		window.show();

	}

	@FXML
	public void reloadMainPage() {
		initializeListView();
		initializeGridPane();
	}

	// Drag and drop

	@FXML
	private void imageDragStarted(MouseEvent e) {
		actionWasDragAndNoClick = true;
		Dragboard dragBoard = gridPane.startDragAndDrop(TransferMode.ANY);
		ClipboardContent clipBoard = new ClipboardContent();

		// if drag started on grid, but not an image, drag a box to select images
		if (!(e.getPickResult().getIntersectedNode() instanceof ImageView)) {
			selectionDragStarted(e);
		} else {
			ImageView imageView = (ImageView) e.getPickResult().getIntersectedNode();
			int indexOfSelectedImage = gridPane.getChildren().indexOf(imageView);
			System.out.println(indexOfSelectedImage);
			ImageContainer image = selectedAlbum.getImages().get(indexOfSelectedImage);

			clipBoard.put(imageContainerFormat, image);
		}

		dragBoard.setContent(clipBoard);
		e.consume();
	}

	@FXML
	private void selectionDragStarted(MouseEvent e) {
		System.out.println("selectiondrag started");
	}

	@FXML
	private void imageDragOver(DragEvent e) {
		System.out.println("dragover");
		if (e.getDragboard().hasContent(imageContainerFormat)) {
			e.acceptTransferModes(TransferMode.ANY);
		}
		e.consume();
	}

	// Bar above gridPane
	@FXML
	private void renameAllButtonPressed() {
		if (renameAllTextField.getText().isEmpty()) {
			return;
		}
		selectedAlbum.getImages().stream().forEach(i -> EditMetaData.renameImage(i, renameAllTextField.getText()));
		reloadMainPage();
	}

	// ContextMenu
	private void initializeContextMenu() {
		contextMenu = new ContextMenu();
		MenuItem delete = new MenuItem("delete from album");
		MenuItem deleteFromAll = new MenuItem("delete from all albums");
		MenuItem rename = new MenuItem("rename");

		rename.setOnAction(e -> initializeRenameDialog());

		delete.setOnAction(e -> {
			try {
				SendSQLRequest.deleteImageFromAlbum(selectedAlbum, clickedOnImage);
				reloadMainPage();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		});
		contextMenu.getItems().addAll(rename, delete, deleteFromAll);
		contextMenu.setOpacity(1);
	}

	@FXML
	private void contextMenuRequested(ContextMenuEvent e) {
		ImageView imageView = (ImageView) e.getPickResult().getIntersectedNode();
		int indexOfImageView = gridPane.getChildren().indexOf(imageView);
		clickedOnImage = selectedAlbum.getImages().get(indexOfImageView);

		contextMenu.show(gridPane, e.getScreenX(), e.getScreenY());
	}

}