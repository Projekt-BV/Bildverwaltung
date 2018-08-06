package design;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import java.io.IOException;


public class FXMLDocumentController implements Initializable {
	
	@FXML
	private AnchorPane rootPane;
	
/*	
	@FXML public ResourceBundle rb;
	@FXML public URL url;
*/
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}
	
	@FXML
	private void switchScene(ActionEvent event) throws IOException{
		boolean pageCheck = false;
		
		if (pageCheck == false) {
			pageCheck = true;
			AnchorPane pane = FXMLLoader.load(getClass().getResource("/design/Main_page_edit_mode.fxml"));
			rootPane.getChildren().setAll(pane);
			
		} else {
			pageCheck = false;
			AnchorPane pane = FXMLLoader.load(getClass().getResource("/design/Main_page_2.4.fxml"));
			rootPane.getChildren().setAll(pane);
		}
	}
}
