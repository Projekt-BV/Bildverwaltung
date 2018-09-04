package testDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.SendSQLRequest;

public class ConnectTest {
	public static void main(String[] args) {
		String url = "jdbc:mariadb://localhost:3306/prog3_db";
		String user = "root";
		String password = "Password1!";
		
		try (Connection con = DriverManager.getConnection(url, user, password)) {
			System.out.println("Verbindung zu " + url + " wurde hergestellt.");
			ResultSet tmpRs = SendSQLRequest.sendSQL("INSERT INTO foto_metadaten (Titel,Beschreibung,Schluesselwoerter,Datum,Fotoname,Pfad,Ort) VALUES ('Foto1','Testbild1','1','15.05.18','test.jpg','C:/tmp','Buxdehude');");
			con.close();
		} catch (SQLException e) {
			System.err.println("Verbindung konnte nicht hergestellt werden.");
			System.err.println(e);
		}
	}
}
