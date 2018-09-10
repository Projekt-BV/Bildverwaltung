/**
 * 
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeSet;

import database.SendSQLRequest;

/**
 * Database class 
 * @author philpsc 
 */
public class Database {
	
	private ArrayList<Album> albums;
	
	/**
	 * Loads all albums from database (including images)
	 * TODO: include tags
	 */
	public Database() {
		ResultSet albumsResultSet; 
		TreeSet<Integer>albumIDs = new TreeSet<Integer>(); 
		albums = new ArrayList<Album>();
		
		try {			
			
			// 1. get all album IDs for albums that have at least one image
			albumsResultSet = SendSQLRequest.sendSQL("SELECT * FROM albumfoto");
			
			while (albumsResultSet.next()) {
				albumIDs.add(albumsResultSet.getInt("AlbumID"));
			}
			
			
			// 2. get albums using their IDs and make Album objects
			for (int id : albumIDs) {
				ResultSet albumResultSet = SendSQLRequest.sendSQL("SELECT * FROM alben WHERE ID=" + id);
				if (albumResultSet.next()) {
					Album album = new Album(albumResultSet.getString("Name"), id);
					albums.add(album);
				}				
			}
			
			
			// 3. search each album's images, write them into a collection, add them to the Album objects
			for (Album album : albums) {
				ResultSet imageIDResultSet = SendSQLRequest.sendSQL("SELECT FotoID FROM albumfoto WHERE AlbumID=" + album.getId());
				while (imageIDResultSet.next()) {					
					ResultSet imageResultSet = SendSQLRequest.sendSQL("SELECT * FROM fotos WHERE ID=" + imageIDResultSet.getInt("FotoID"));
					if (imageResultSet.next()) {
						int id = imageResultSet.getInt("ID");
						String name = imageResultSet.getString("Fotoname");
						String path = imageResultSet.getString("Pfad");
						String location = imageResultSet.getString("Ort");
						String date = imageResultSet.getString("Datum");
						ImageContainer image = new ImageContainer(id, name, path, location, date);
						album.getImages().add(image);
					}					
				}
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(ArrayList<Album> albums) {
		this.albums = albums;
	}

}
