package application;

import java.sql.SQLException;

import database.SendSQLRequest;
import database.SqlRunner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("/design/Main_page_2.4.fxml"));
		Scene scene = new Scene(root);
		
		String css = Main.class.getResource("/design/application.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage.getIcons().add(new Image("/design/Nerd_Icon.jpg"));
		stage.setTitle("Bildverwaltung");
		stage.minHeightProperty().bind(stage.widthProperty().multiply(0.65));
		stage.maxHeightProperty().bind(stage.widthProperty().multiply(0.5));
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) throws SQLException {
		SqlRunner.main(null);
		SendSQLRequest.checkTabelles();
		launch(args);
	}
}
