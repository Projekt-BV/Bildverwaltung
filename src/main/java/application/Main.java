package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("Main_page_2.4.fxml"));
		Scene scene = new Scene(root);
		
		String css = Main.class.getResource("application.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage.setTitle("Genuine Coder");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
