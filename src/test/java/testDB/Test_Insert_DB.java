package testDB;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import database.*;

import org.junit.Test;



public class Test_Insert_DB {

	/**
	 * Beispielimplementierung f�r Abfragen in der Datenbank
	 * 
	 */
	@Test
	public void testInsert_Album() {
		ResultSet tmpRs;
		try {
			tmpRs = SendSQLRequest.sendSQL("INSERT INTO alben (Name) VALUES ('Urlaub1');");
			tmpRs = SendSQLRequest.sendSQL("INSERT INTO fotos (Schluesselwoerter,Datum,Fotoname,Pfad,Ort) VALUES ('1','15.05.18','test.jpg','C:/tmp','Buxdehude');");
			tmpRs = SendSQLRequest.sendSQL("INSERT INTO tags (Schluesselwort, Foto_ID) VALUES ('Baum', '1');");
			tmpRs = SendSQLRequest.sendSQL("INSERT INTO albumfoto (AlbumID, FotoID) VALUES ('1','1');");
			
			tmpRs = SendSQLRequest.sendSQL("SELECT * FROM alben;");
			printResultSet(tmpRs);
			
			tmpRs = SendSQLRequest.sendSQL("SELECT * FROM albumfoto;");
			printResultSet(tmpRs);
			 
			tmpRs = SendSQLRequest.sendSQL("SELECT * FROM fotos;");
			printResultSet(tmpRs);
			
			tmpRs = SendSQLRequest.sendSQL("SELECT * FROM tags;");
			printResultSet(tmpRs);
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	/**
	 * Ausgabe eines ResultSet Objects
	 * @param rs
	 * @throws SQLException
	 */
	public static void printResultSet(ResultSet rs) throws SQLException
	{
	    ResultSetMetaData rsmd = rs.getMetaData();
	    int cols = rsmd.getColumnCount();

	    for(int i=1; i<=cols; i++)
	        System.out.print(rsmd.getColumnLabel(i)+"\t");

	    System.out.println("\n-------------------------------");

	    while(rs.next())
	    {
	        // eine zeile ausgeben
	        for(int i=1; i<=cols; i++)
	            System.out.print(rs.getString(i)+"\t");

	        System.out.println();
	    }
	}


}
