package model.editing;

import java.sql.SQLException;

import database.SendSQLRequest;

/**
 * Class to edit metadata of displayed images
 * @author Julian Einspenner
 */
public class EditMetaData {
	
	public static void saveMetaData(String title, String location, String date, String[] tags, int id) {
		if(title == null || location == null || date == null) {
			title = "";
			location = "";
			date = "";
		}
		
		String sqlStatementTitleLocationDate = "UPDATE prog3_db.fotos SET Fotoname='" + 
												title + "', Datum='" + 
												date + "', Ort='" + 
												location + 
												"' WHERE ID=" + id;
		
		String sqlStatementDeleteTags = "DELETE FROM tags WHERE Foto_ID=" + id;
		
		try {
			SendSQLRequest.sendSQL(sqlStatementTitleLocationDate);
			SendSQLRequest.sendSQL(sqlStatementDeleteTags);
			
			for(int i = 0; i < tags.length; i++) {
				String sqlStatementTags = "INSERT INTO prog3_db.tags (Schluesselwort, Foto_ID) VALUES ('" + tags[i] + "', " + id + ")";
				SendSQLRequest.sendSQL(sqlStatementTags);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Failed while savig metadata to database");
		}
		

		
		
	}
	
}
