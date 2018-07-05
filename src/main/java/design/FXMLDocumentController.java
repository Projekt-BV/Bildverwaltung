package design;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import java.awt.event.ActionEvent;

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
	private void switchScene(ActionEvent event) {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("Main_page_edit_mode.fxml"));
		rootPane.getChildren().setAll(pane);
	}
}
