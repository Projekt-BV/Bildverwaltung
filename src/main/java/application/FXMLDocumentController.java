package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class FXMLDocumentController implements Initializable {
	
	@FXML
	private AnchorPane rootPane;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {	
	}
	
	@FXML
	private void switchScene(ActionEvent event) {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("Main_page_edit_mode.fxml"));
		rootPane.getChildren().setAll(pane);
	}
}
