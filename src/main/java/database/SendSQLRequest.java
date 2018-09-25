package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import model.Album;
import model.ImageContainer;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;


public class SendSQLRequest {
	
	private static final String url = "jdbc:mariadb://localhost:3306/prog3_db";
	private static final String user = "root";
	private static final String password = "Password1!";
	private static Statement stmt = null;
	
	private static Connection con;
	
		
	/**
	 * Testet ein uebergebenen SQL-Befehl
	 * 
	 * @param stmt
	 * @param sqlRequest
	 * @return
	 * @throws SQLException
	 */
	private static boolean testStatement(Statement stmt, String sqlRequest) throws SQLException {
		
		return stmt.execute(sqlRequest);
	}
	
	/**
	 * Erzeugt eine Verbindung zur Datenbank
	 * @throws SQLException
	 */
	private static void getDB_Connection() throws SQLException {
		 con = DriverManager.getConnection(url, user, password);
		
		
	}
	
	/**
	 * Schliesst eine bestehende Datenbankverbindung
	 * @throws SQLException
	 */
	private static void closeDB_Connection() throws SQLException {
		con.close();
		
	}
	
	public static ResultSet sendSQL(String sqlRequest) throws SQLException {
		
		try {
			getDB_Connection();
			stmt = con.createStatement();
			
			if(testStatement(stmt ,sqlRequest)== true) {
				
				//closeDB_Connection();
				return sendSQL_Query(sqlRequest);
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			stmt.close();
			closeDB_Connection();			
		}
		
		return null;
	}
	
	/**
	 * Fuehrt eine SELECT Sql-Anweissung durch
	 * @param sqlRequest SQL-Anweissung
	 * @return ResultSet
	 * 
	 */
	public static ResultSet sendSQL_Query (String sqlRequest) {
		ResultSet rs;
		rs = null;
		try {
            rs = stmt.executeQuery(sqlRequest);
			//rs = getStatement().executeQuery(sqlRequest);
            stmt.close();
            con.commit();     
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
			try {
				closeDB_Connection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return rs;
	}

	/**
	 * Ueberprueft Datenbank ob Pfade noch gueltig sind -> löscht fehlerhafte Einträge
	 * 	
	 * @throws SQLException
	 */
	public static void checkTabelles() throws SQLException {
		ResultSet tmpRs;
		File tmpFile;
		
		String query = "SELECT * from fotos;";
		String deleteFromAlbumfoto = "DELETE FROM albumfoto WHERE FotoID=?";
		String deleteFromFoto = "DELETE FROM fotos WHERE ID=?";
		String deleteFromTags = "DELETE FROM tags WHERE Foto_ID=?";
		
		getDB_Connection();
		stmt = con.createStatement();

		
        tmpRs = stmt.executeQuery(query);
        
        ResultSetMetaData rsmd = tmpRs.getMetaData();
	 
	    PreparedStatement preAlbumFoto = con.prepareStatement(deleteFromAlbumfoto);
	    PreparedStatement preFoto = con.prepareStatement(deleteFromFoto);
	    PreparedStatement preTag = con.prepareStatement(deleteFromTags);
	    String tmpString ="";
	
	    while(tmpRs.next())
	    {
	        	tmpString = tmpRs.getString(4);
	        	tmpString = tmpString.substring(8, tmpString.length());
	        	System.out.println(tmpString);
	        	tmpFile = new File (tmpString);
	        	boolean tmpbool = tmpFile.exists();
	        	if(tmpbool) {
	        		
	        	}else
	        	{
	     
	        		int tmpId = tmpRs.getInt(1);
	      
	        		preAlbumFoto.setInt(1, tmpId);
	        		preFoto.setInt(1, tmpId);
	        		preTag.setInt(1, tmpId);
	        		
	        		preAlbumFoto.executeUpdate();
	        		preTag.executeUpdate();
	        		preFoto.executeUpdate();
	        	}
	
	  
	    }
	    con.commit();
	    closeDB_Connection();
	}
	
	/**Entfernt uebergebens Album aus der DB
	 * 
	 * @param album Zulöschendes Album
	 * @throws SQLException
	 */
	public static void deleteAlbum (Album album) throws SQLException {
		String deleteFromAlbum = "DELETE FROM alben WHERE ID = "+ album.getId();
		String deleteFromAlbumfoto ="DELETE FROM albumfoto WHERE AlbumID =" +album.getId()+ "; \n";
	
		
		sendSQL(deleteFromAlbumfoto);
		sendSQL(deleteFromAlbum);
	}
	
	/**
	 * 
	 * @param album 
	 * @param ic
	 * @throws SQLException
	 */
	public static void deleteImageFromAlbum (Album album, ImageContainer ic) throws SQLException {
		
		String deleteFromAlbumfoto ="DELETE FROM albumfoto WHERE AlbumID = " +album.getId()+ " AND FotoID = " + ic.getId();
		
		sendSQL (deleteFromAlbumfoto);
	}
	
	public static void deleteImageFromDB (ImageContainer ic) throws SQLException {
		int id = ic.getId();
		
		String deleteFotoFormFotos = "DELETE FROM fotos WHERE ID = " + id + ";";
		String deleteFromAlbumFoto = "DELETE FROM albumdoto WHERE FotoID = "+ id + ";\n";
		String deleteFromTags = "DELETE FROM tags WHERE Foto_ID = "+ id + ";" ;
	
		
		sendSQL (deleteFromAlbumFoto);
		sendSQL (deleteFromTags);
		sendSQL (deleteFotoFormFotos);
	}
}

