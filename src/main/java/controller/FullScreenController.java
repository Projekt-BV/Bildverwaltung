package controller;

import java.net.*;
import java.util.*;

import javafx.fxml.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 * This class manages the fullscreen scene while displaying one large image.
 * @author Phillip Persch
 */
public class FullScreenController implements Initializable {

	@FXML
	ImageView imageView;
	@FXML
	StackPane stackPane;

	private Image image;
	private MainControllerEditMode mainController;

	/**
	 * This method initializes the controller.
	 * @author Phillip Persch
	 * @param arg0 inhereted from superclass
	 * @param arg1 inhereted from superclass
	 */
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
