package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class DB_Connector {
	private String username = "root";
	private String pwd 	= "Password1!";
	private Connection connectDB() {
		Connection connection;
		connection = null;
		try {
	  

	          connection = DriverManager.getConnection(  
	                  "jdbc:mariadb://localhost:3306/prog3_db", username, pwd);  
	         System.out.println("Datenbankverbindung erstellt");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return connection;
	}

}
