package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("/design/imageDummy.fxml"));
		Scene scene = new Scene(root);
		
		String css = Main.class.getResource("/design/application.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage.setTitle("Bildverwaltung");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
