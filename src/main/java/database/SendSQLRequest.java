package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.ResultSet;


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
			
			closeDB_Connection();
			return sendSQL_Query(sqlRequest);
		}
		else {
			//sendSQL_Update(sqlRequest);
			// Bessere L�sung suchen als null zur�ckgeben wenn keine ResultSet-Objekt
			return null;
		}
	
	}
	

	
	
//	private static void sendSQL_Update (String sqlRequest) {
//	
//		try {
//			getDB_Connection();
//			stmt = con.createStatement();
//			stmt.executeUpdate(sqlRequest);
//			stmt.close();
//		    con.commit();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		finally {
//			
//		try {
//			closeDB_Connection();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		}
//       
//	}
	public static ResultSet sendSQL_Query (String sqlRequest) {
		ResultSet rs;
		rs = null;
		try {
		
            // Query ausf�hren - einf�gen
            
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
}
