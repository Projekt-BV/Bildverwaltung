package testDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectTest {
	public static void main(String[] args) {
		String url = "jdbc:mariadb://localhost:3306/prog3_db";
		String user = "root";
		String password = "Password1!";
		
		try (Connection con = DriverManager.getConnection(url, user, password)) {
			System.out.println("Verbindung zu " + url + " wurde hergestellt.");
		} catch (SQLException e) {
			System.err.println("Verbindung konnte nicht hergestellt werden.");
			System.err.println(e);
		}
	}
}
