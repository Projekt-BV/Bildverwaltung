package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.ImageContainer;
import model.editing.Resizer;

public class MainControllerGalleryMode extends MainController implements Initializable {

	@FXML
	private AnchorPane rootPane;
	@FXML
	private GridPane gridPane;
	@FXML
	private ProgressIndicator progressIndicator;
	@FXML
	private ImageView displayImage;
	private Image imageToDownScale;
	private int col = 0;
	private int line = 0;
	private boolean refreshing = false;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		didSwitchBack = true;
		initializeListView();
		initializeGridPane();
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
		ImageView imageView = (ImageView) e.getPickResult().getIntersectedNode();

		int indexOfImageView = gridPane.getChildren().indexOf(imageView);
		ImageContainer completeImage = selectedAlbum.getImages().get(indexOfImageView);

		MainControllerEditMode.imageContainer = completeImage;

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

	// Bar above gridPane
	@FXML
	private void renameAllButtonPressed() {
		System.out.println("I am the renameAllButtonPressed function");
	}
}