package model.editing;

import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * This class implements a dialog to choose a new image name.
 * @author Phillip Persch
 */
public class RenameImage extends Application {

	private Optional<String> result;

	/**
	 * This method sets up the instructions the user is given.
	 * It then waits for the user's inputs.
	 */
	private void showInputTextDialog() {

		TextInputDialog dialog = new TextInputDialog();

		dialog.setTitle("Rename image");
		dialog.setHeaderText("Enter the new name:");
		dialog.setContentText("Name:");

		result = dialog.showAndWait();
	}

	/**
	 * This method initializes the view.
	 */
	@Override
	public void start(Stage stage) {

		VBox root = new VBox();
		root.setPadding(new Insets(10));
		root.setSpacing(10);
		showInputTextDialog();

	}

	/**
	 * Getter for field result
	 * @return the result if there is one or null if there is none
	 */
	public Optional<String> getResult() {
		return this.result;
	}
}