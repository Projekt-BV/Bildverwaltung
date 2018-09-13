package model;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.SendSQLRequest;
import javafx.application.Application;
import javafx.stage.FileChooser;

import javafx.stage.Stage;

public class FileImport extends Application {
		 
    @Override
    public void start(final Stage stage) {
        stage.setTitle("File import");
 
        final FileChooser fileChooser = new FileChooser();
        
        List<File> list = fileChooser.showOpenMultipleDialog(stage);
        if (list != null) {
        	for (File file : list) {
        		openFile(file);
            }
        }  
    }         
 
    private void openFile(File file) {
        try {
            ResultSet tmpRs;
        	String sqlrequest ="INSERT INTO fotos (Fotoname,Pfad) VALUES" + "('"+ file.getName() +"', 'file://" +file.toURI().getPath()+ "');";
            tmpRs = SendSQLRequest.sendSQL(sqlrequest);
        } catch (SQLException ex) {
            Logger.getLogger(
                FileImport.class.getName()).log(
                    Level.SEVERE, null, ex
                );
        }
    }
}
