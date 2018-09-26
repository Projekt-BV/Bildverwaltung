/**
 * 
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Collectors;

import database.SendSQLRequest;

/**
 * Database class 
 * @author philpsc 
 */
public class Database {
	
	private ArrayList<Album> albums;
	

	public void reloadDatabaseContents() {
		ResultSet albumsResultSet; 
		albums = new ArrayList<Album>();
		
		try {			
			
			// 1. get all albums 
			albumsResultSet = SendSQLRequest.sendSQL("SELECT * FROM alben");
			
			while (albumsResultSet.next()) {
				albums.add(new Album(albumsResultSet.getString("Name"), albumsResultSet.getInt("ID")));
			}
			
			
			// 2. search each album's images, write them into a collection, add them to the Album objects			
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
						
						// get tag list
						ArrayList<String> tags = new ArrayList<String>();
						ResultSet tagsResult = SendSQLRequest.sendSQL("SELECT Schluesselwort FROM tags WHERE Foto_ID=" + id);
						
						while (tagsResult.next()) {
							tags.add(tagsResult.getString("Schluesselwort"));
						}
						
						// build imageContainer					
						ImageContainer image = new ImageContainer(id, name, path, location, date, tags);					
						
						// add imageContainer to album
						album.getImages().add(image);
					}					
				}
				album.sortByDate();
			}
			sortByName();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void sortByName() {
		albums = albums.stream()
				       .sorted((i1, i2) -> i1.getName().compareTo(i2.getName()))
				       .collect(Collectors.toCollection(ArrayList::new));
		Deque<Album> albumQueue = new LinkedList<Album>(albums);
		
		Album allImages = albumQueue.stream()
									.filter(album -> album.getName()	
									.equals("All Images"))
									.findFirst()
									.get();
		
		albumQueue.remove(allImages);
		albumQueue.addFirst(allImages);
		albums = new ArrayList<Album>(albumQueue);
	}
	
	public ArrayList<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(ArrayList<Album> albums) {
		this.albums = albums;
	}

}
