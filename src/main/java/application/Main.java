package application;

import design.FXMLDocumentController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage stage) throws Exception{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/design/Main_page_2.4.fxml"));
		Parent root = (Parent) loader.load();
		//Parent root = FXMLLoader.load(getClass().getResource("/design/Main_page_2.4.fxml"));	
		Scene scene = new Scene(root);		
		String css = Main.class.getResource("/design/application.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage.setTitle("Bildverwaltung");
		stage.setScene(scene);
		stage.setMinHeight(768);
		stage.setMinWidth(1280);
		FXMLDocumentController controller = (FXMLDocumentController)loader.getController();
				controller.stageSizeChangeListener(stage);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
