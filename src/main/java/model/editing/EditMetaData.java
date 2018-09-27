package model.editing;

import java.io.File;
import java.sql.SQLException;

import controller.MainControllerEditMode;
import database.SendSQLRequest;
import model.ImageContainer;

/**
 * Class to edit metadata of displayed images
 * 
 * @author Julian Einspenner, Phillip Persch
 */
public class EditMetaData {

	/**
	 * This function generates the SQL-Statements to save an images metadata to the
	 * database
	 * 
	 * @author Julian Einspenner
	 * @param title
	 *            is the name of the image
	 * @param location
	 *            is the location where this photo was shot
	 * @param date
	 *            is a time stamp of the image
	 * @param tags
	 *            are additional information, e.g. "vacation16"
	 * @param id
	 *            is the unique identifier of the image
	 */
	public static boolean saveMetaData(String title, String location, String date, String[] tags, int id) {
		renameImage(MainControllerEditMode.imageContainer, title);
		location = (location == null) ? "" : location;
		date = (date == null) ? "" : date;
		tags[0] = (tags[0] == null) ? "" : tags[0];

		String sqlStatementTitleLocationDate = createStatementForTitleLocationDate(location, date, id);
		String sqlStatementDeleteTags = createStatementToDeleteOldTags(id);

		try {
			SendSQLRequest.sendSQL(sqlStatementTitleLocationDate);
			SendSQLRequest.sendSQL(sqlStatementDeleteTags);

			for (int i = 0; i < tags.length; i++) {
				String sqlStatementTags = "INSERT INTO prog3_db.tags (Schluesselwort, Foto_ID) VALUES ('" + tags[i]
						+ "', " + id + ")";
				SendSQLRequest.sendSQL(sqlStatementTags);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Failed while savig metadata to database");
			return false;
		}
		return true;
	}

	/**
	 * Generates the SQL-Statement for setting title, date and location
	 * 
	 * @author Julian Einspenner
	 * @param title
	 *            name of the image
	 * @param date
	 *            time stamp of the image
	 * @param location
	 *            location of the image
	 * @param id
	 *            is the unique identifier of the image in database
	 * @return is the SQL-statement
	 */
	public static String createStatementForTitleLocationDate(String location, String date, int id) {
		return "UPDATE prog3_db.fotos SET " + "Datum='" + date + "', Ort='" + location + "' WHERE ID=" + id;
	}

	/**
	 * Generates the SQL-Statement for deleting an images tags
	 * 
	 * @author Julian Einspenner
	 * @param id
	 *            is the unique identifier of the image in database
	 * @return is the SQL-Statement
	 */
	public static String createStatementToDeleteOldTags(int id) {
		return "DELETE FROM tags WHERE Foto_ID=" + id;
	}
	
	
	/**
	 * This method renames an image.
	 * If the corresponding text field is not empty, it are renamed to:
	 * the textfield's text + an incrementing integer.
	 * 
	 * 
	 * @author Phillip Persch
	 * @param imageContainer the image to be renamed
	 * @param newName the new name
	 */
	public static boolean renameImage(ImageContainer imageContainer, String newName) {

		// String manipulation necessary to have correct paths
		String oldPath = imageContainer.getPath().substring(8);
		File file = new File(oldPath);
		int lastSlash = oldPath.lastIndexOf("/");
		int dot = oldPath.lastIndexOf(".");
		String fileType = oldPath.substring(dot, oldPath.length());

		String newPath = oldPath.substring(0, lastSlash + 1);
		File newFile = new File(newPath + newName + fileType);

		int i = 1;
		String originalNewName = newName;
		while (newFile.exists() && !newFile.equals(file)) {
			newName = originalNewName + i++;
			newFile = new File(newPath + newName + fileType);
		}

		// database calls
		if (file.renameTo(newFile)) {
			String updatePathRequest = "UPDATE fotos SET Pfad='file:///" + newPath + newName + fileType + "' WHERE ID="
					+ imageContainer.getId() + ";";
			String updateNameRequest = "UPDATE fotos SET Fotoname='" + newName + fileType + "' WHERE ID="
					+ imageContainer.getId() + ";";
			try {
				SendSQLRequest.sendSQL(updatePathRequest);
				SendSQLRequest.sendSQL(updateNameRequest);
				imageContainer.setPath("file:///" + newPath + newName + fileType);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

}
