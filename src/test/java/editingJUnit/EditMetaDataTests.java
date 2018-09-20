package editingJUnit;

import static org.junit.Assert.*;

import org.junit.Test;

import model.editing.EditMetaData;

public class EditMetaDataTests {
	
	static String titleDateLocationStatement = "UPDATE prog3_db.fotos SET Fotoname='" + 
											   "universe.jpg" + 
											   "', Datum='" + "22.11.2010" + 
											   "', Ort='" + "pluto" + 
											   "' WHERE ID=" + 42;
	
	static String tagDeletionStatement = "DELETE FROM tags WHERE Foto_ID=" + 12;

	@Test
	public void createSQLForTitleDateLocationTest() {
		String statement = EditMetaData.createStatementForTitleLocationDate("universe.jpg", "22.11.2010", "pluto", 42);
		assertEquals(titleDateLocationStatement, statement);
	}

	@Test
	public void createSQLToDeleteTagsTest() {
		String statement = EditMetaData.createStatementToDeleteOldTags(12);
		assertEquals(tagDeletionStatement, statement);
	}
}
