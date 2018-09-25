package testDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.SendSQLRequest;
import model.Album;
import model.ImageContainer;

public class ConnectTest {
	public static void main(String[] args) throws SQLException {
	
			Album tmpAlbum = new Album("TestAlbum", 2);
			
			
			SendSQLRequest.deleteAlbum(tmpAlbum);
			//SendSQLRequest.checkTabelles();
		
	}
}
