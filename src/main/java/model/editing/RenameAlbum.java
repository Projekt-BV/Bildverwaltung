package model.editing;

import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RenameAlbum extends Application {

	private Optional<String> result;

	private void showInputTextDialog() {

		TextInputDialog dialog = new TextInputDialog();

		dialog.setTitle("Rename image");
		dialog.setHeaderText("Enter the new name:");
		dialog.setContentText("Name:");

		result = dialog.showAndWait();
	}

	@Override
	public void start(Stage stage) {

		VBox root = new VBox();
		root.setPadding(new Insets(10));
		root.setSpacing(10);
		showInputTextDialog();

	}

	public Optional<String> getResult() {
		return this.result;
	}
}