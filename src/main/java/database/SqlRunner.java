package database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlRunner {
	public static void main(String[] args) {
		try {
			executeScriptUsingStatement();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	   }
	 
	   static void executeScriptUsingStatement() throws IOException, SQLException {
		String scriptFilePath = "../Bildverwaltung/pro3Sql.sql";
		BufferedReader reader = null;
		Connection con = null;
		Statement statement = null;
		String url = "jdbc:mariadb://localhost:3306/";
		String user = "root";
		String password = "Password1!";
		
		con = DriverManager.getConnection(url, user, password);
			
			System.out.println("Verbindung zu " + url + " wurde hergestellt.");
		
			// create statement object
			statement = con.createStatement();
			String line = null;
		
			// initialize file reader
			reader = new BufferedReader(new FileReader(scriptFilePath));
			
			// read script line by line
			while ((line = reader.readLine()) != null) {
				// execute query
				System.out.println(line);
				statement.execute(line);
			}

	   }
	}