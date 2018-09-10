/**
 * 
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.SendSQLRequest;

/**
 * @author philpsc 
 */
public class Album {

	private String name;
	private int id;
	private ArrayList<ImageContainer> images;
	
	public Album(String name, int id, ArrayList<ImageContainer> images) {
		this.name = name;
		this.id = id;
		this.images = images;
	}
	
	public Album(String name, int id) {
		this.name = name;
		this.id = id;
		this.images = new ArrayList<ImageContainer>();
	}
	
	public Album(ResultSet album) throws SQLException {
		this.name = album.getString("Name");
		this.id = album.getInt("ID");
		this.images = null; //TODO: Vervollständigen
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<ImageContainer> getImages() {
		return images;
	}

	public void setImages(ArrayList<ImageContainer> images) {
		this.images = images;
	}
	
	
}
