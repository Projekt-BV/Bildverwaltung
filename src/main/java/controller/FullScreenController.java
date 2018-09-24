package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class FullScreenController implements Initializable {

	@FXML
	ImageView imageView;
	@FXML
	StackPane stackPane;

	private Image image;
	private MainControllerEditMode mainController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.image = MainControllerEditMode.getImage();
		this.imageView.setImage(image);

		imageView.fitHeightProperty().bind(stackPane.heightProperty());
		imageView.fitWidthProperty().bind(stackPane.widthProperty());

	}

	public void injectMainController(MainControllerEditMode mainControllerEditMode) {
		this.mainController = mainControllerEditMode;

	}

	public void setKeyListener() {
		imageView.imageProperty().bind(mainController.getDisplayImageEditMode().imageProperty());
		Stage stage = (Stage) imageView.getScene().getWindow();
		stage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				stage.close();
				return;
			} else if (event.getCode() == KeyCode.LEFT) {
				mainController.swipeBackwards();
			} else if (event.getCode() == KeyCode.RIGHT) {
				mainController.swipeForwards();
			}
		});
	}

}
