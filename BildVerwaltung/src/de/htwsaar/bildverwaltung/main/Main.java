package de.htwsaar.bildverwaltung.main;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/de/htwsaar/bildverwaltung/view/Main_page_2.4.fxml"));
			primaryStage.setTitle("Bildverwaltungssytem");
			
			primaryStage.setScene(new Scene(root, 1920, 1080));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//alle eventlistener hier unter implementieren
	
	
}
