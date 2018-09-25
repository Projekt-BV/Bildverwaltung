package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
	 * Erzeugt ein Statement
	 * @return
	 * @throws SQLException
	 */
	private static Statement getStatement() throws SQLException {
		try {
			getDB_Connection();
			stmt = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			stmt.close();
			closeDB_Connection();
		} finally {

		}
		return stmt;
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
		Statement tmpStatement = getStatement();
		if(testStatement(tmpStatement ,sqlRequest)== true) {
			
			//closeDB_Connection();
			return sendSQL_Query(sqlRequest);
		}
		else {
			//sendSQL_Update(sqlRequest);
			// Bessere L�sung suchen als null zur�ckgeben wenn keine ResultSet-Objekt
			return null;
		}
	
	}
	
	public static ResultSet sendSQL_Query (String sqlRequest) {
		ResultSet rs;
		rs = null;
		try {
            rs = getStatement().executeQuery(sqlRequest);
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


	public static void checkTabelles() throws SQLException {
		ResultSet tmpRs;
		File tmpFile;
		
		String query = "SELECT * from fotos;";
		String delete1 = "DELETE FROM albumfoto WHERE FotoID=?";
		String delete2 = "DELETE FROM fotos WHERE ID=?";


		
        tmpRs = getStatement().executeQuery(query);
        ResultSetMetaData rsmd = tmpRs.getMetaData();
	    int cols = rsmd.getColumnCount();
		
	    // Statement vorbereiten
	    getDB_Connection();
	    //con.setAutoCommit(false);
	    
	    PreparedStatement preStatement = con.prepareStatement(delete1);
	    PreparedStatement preStatementDel2 = con.prepareStatement(delete2);
	    String tmpString ="";
	    boolean statementBool = false;
	    while(tmpRs.next())
	    {
	      
	        	
	        	
	        	tmpString = tmpRs.getString(4);
	        	System.out.println(tmpString);
	        	tmpString = tmpString.substring(8, tmpString.length());
	        	System.out.println(tmpString);
	        	tmpFile = new File (tmpString);
	        	boolean tmpbool = tmpFile.exists();
	        	if(tmpbool) {
	        		
	        	}else
	        	{
	        		statementBool = true;
	        		int tmpId = tmpRs.getInt(1);
	      
	        		preStatement.setInt(1, tmpId);
	        		preStatementDel2.setInt(1, tmpId);
	        	}
	
	  
	    }
	    
	    if(statementBool == true) {
	    	
		    System.out.println(preStatement.toString());
		    System.out.println(preStatementDel2.toString());
	        preStatement.executeUpdate();
	        preStatementDel2.executeUpdate();
	        con.commit();
	        
	    }
        
		
	    closeDB_Connection();
		 
		
	}
}

