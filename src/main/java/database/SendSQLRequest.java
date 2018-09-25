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

//	/**
//	 * Erzeugt ein Statement
//	 * @return
//	 * @throws SQLException
//	 */
//	private static Statement getStatement() throws SQLException {
//		try {
//			getDB_Connection();
//			stmt = con.createStatement();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			stmt.close();
//			closeDB_Connection();
//		} finally {
//
//		}
//		return stmt;
//	}
	
	
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
		String delete1 = "DELETE FROM albumfoto WHERE FotoID=?";
		String delete2 = "DELETE FROM fotos WHERE ID=?";
		
		getDB_Connection();
		stmt = con.createStatement();

		
        tmpRs = stmt.executeQuery(query);
        ResultSetMetaData rsmd = tmpRs.getMetaData();
	    int cols = rsmd.getColumnCount();

	    
	    PreparedStatement preStatement = con.prepareStatement(delete1);
	    PreparedStatement preStatementDel2 = con.prepareStatement(delete2);
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
	      
	        		preStatement.setInt(1, tmpId);
	        		preStatementDel2.setInt(1, tmpId);
	    	        preStatement.executeUpdate();
	    	        preStatementDel2.executeUpdate();
	        	}
	
	  
	    }
	    con.commit();
	    closeDB_Connection();
	}
	
	public static void deleteAlbum (Album album) throws SQLException {
		String deleteFromAlbum = "DELETE FROM alben WHERE ID = "+ album.getId() + "; " ;
		String deleteFromAlbumfoto ="DELETE FROM albumfoto WHERE AlbumID =" +album.getId()+ ";";
		String sendSql= deleteFromAlbumfoto + deleteFromAlbum;
		
		sendSQL(sendSql);
	}
	
	public static void deleteImageFromAlbum (Album album, ImageContainer ic) throws SQLException {
		
		String deleteFromAlbumfoto ="DELETE FROM albumfoto WHERE AlbumID = " +album.getId()+ " AND FotoID = " + ic.getId();
		
		sendSQL (deleteFromAlbumfoto);
	}
	
	public static void deleteImageFromDB (ImageContainer ic) throws SQLException {
		int id = ic.getId();
		
		String deleteFotoFormFotos = "DELETE FROM fotos WHERE ID = " + id + ";";
		String deleteFromAlbumFoto = "DELETE FROM albumdoto WHERE FotoID = "+ id + ";";
		String sendSql = deleteFromAlbumFoto + deleteFotoFormFotos;
		
		sendSQL (sendSql);
	}
}

