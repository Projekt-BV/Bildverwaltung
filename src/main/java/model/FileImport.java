package model;

import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import controller.MainControllerGalleryMode;
import database.SendSQLRequest;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileImport extends Application {
		 
    private MainControllerGalleryMode mainController;

	@Override
    public void start(final Stage stage) {
        stage.setTitle("File import");
 
        final FileChooser fileChooser = new FileChooser();  
        
        
        List<File> list = fileChooser.showOpenMultipleDialog(stage);
        
        // List of paths of all images in database, so that an existing image cannot be added again.
        // Storing the same picture twice would cause problems.
        List<String> allImagePaths = mainController.getDatabase()
        										   .getAlbums()
        										   .get(0)
        										   .getImages()
        										   .stream()
        										   .map(i -> i.getPath())
        										   .collect(Collectors.toList());
        
        if (list != null) {
        	for (File file : list) {
        		
        		String path = ("file:///" + file.getAbsolutePath()).replace("\\", "/");
        		
        		if (allImagePaths.isEmpty() || !allImagePaths.contains(path)) {
        			openFile(file);
        		}
            }
            mainController.reloadMainPage();
        }  
    }         
 
    private void openFile(File file) {
        try {
            ResultSet tmpRs;
            Date tmpDate = new Date();
            SimpleDateFormat df = new SimpleDateFormat( "dd.MM.yyyy");            
            String date = df.format(tmpDate);
        	String sqlrequest ="INSERT INTO fotos (Datum,Fotoname,Pfad) VALUES" + "('" + date + "','"+ file.getName() +"', 'file://" +file.toURI().getPath()+ "');";
        	tmpRs = SendSQLRequest.sendSQL(sqlrequest);
        	String foo= "select id from fotos where fotoname='"+file.getName()+"';";
        	tmpRs = SendSQLRequest.sendSQL(foo);
        	
        	
        	ResultSetMetaData rsmd = tmpRs.getMetaData();
    	    int cols = rsmd.getColumnCount();

    	    while(tmpRs.next())
    	    {
    	        // eine zeile ausgeben
    	        for(int i=1; i<=cols; i++)
    	            //System.out.print(tmpRs.getString(i)+"\t");
    	        	foo = "INSERT INTO albumfoto (AlbumID, FotoID) VALUES"+ "(1, '"+ tmpRs.getString(i)+ "');";
    	 
    	    }
    	    tmpRs = SendSQLRequest.sendSQL(foo);
        } catch (SQLException ex) {
            Logger.getLogger(
                FileImport.class.getName()).log(
                    Level.SEVERE, null, ex
                );
        }
    }

	public void injectMainController(MainControllerGalleryMode mainControllerGalleryMode) {
		this.mainController = mainControllerGalleryMode;		
	}
}
