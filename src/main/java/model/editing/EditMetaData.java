package model.editing;

import java.sql.SQLException;

import database.SendSQLRequest;

/**
 * Class to edit metadata of displayed images
 * @author Julian Einspenner
 */
public class EditMetaData {
	
	/**
	 * This function generates the SQL-Statements to save an images metadata to the database
	 * @author Julian Einspenner
	 * @param title is the name of the image
	 * @param location is the location where this photo was shot
	 * @param date is a time stamp of the image
	 * @param tags are additional information, e.g. "vacation16"
	 * @param id is the unique identifier of the image
	 */
	public static void saveMetaData(String title, String location, String date, String[] tags, int id) {
		title    = (title    == null)  ?  ""   :  title;
		location = (location == null)  ?  ""   :  location;
		date     = (date     == null)  ?  ""   :  date;
		tags[0]  = (tags[0]  == null)  ?  ""   :  tags[0];
		
		String sqlStatementTitleLocationDate = createStatementForTitleLocationDate(title, location, date, id);
		String sqlStatementDeleteTags = createStatementToDeleteOldTags(id);
		
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
	
	
	/**
	 * Generates the SQL-Statement for setting title, date and location
	 * @author Julian Einspenner
	 * @param title name of the image
	 * @param date time stamp of the image
	 * @param location location of the image
	 * @param id is the unique identifier of the image in database
	 * @return is the SQL-statement
	 */
	public static String createStatementForTitleLocationDate(String title, String date, String location, int id) {
		return "UPDATE prog3_db.fotos SET Fotoname='" + 
				title + "', Datum='" + 
				date + "', Ort='" + 
				location + 
				"' WHERE ID=" + id;
	}
	
	/**
	 * Generates the SQL-Statement for deleting an images tags
	 * @author Julian Einspenner
	 * @param id is the unique identifier of the image in database
	 * @return is the SQL-Statement
	 */
	public static String createStatementToDeleteOldTags(int id) {
		 return "DELETE FROM tags WHERE Foto_ID=" + id;
	}
	
}
