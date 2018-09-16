/**
 * 
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Wrapper class for handling images
 * @author philpsc
 */
public class ImageContainer {

	private int id;
	private Date date;
	private String name;
	private String path;
	private String location;
	private ArrayList<String> tags;
	
	/**
	 * Constructor for ImageContainer objects
	 * @throws ParseException 
	 */
	public ImageContainer(int id, String name, String path, String location, String date, ArrayList<String> tags) throws ParseException {
		this.id = id;
		this.name = name;
		this.path = path;
		this.location = location;
		this.date = new SimpleDateFormat("dd.MM.yyyy").parse(date);				

		this.tags = tags;
	}
	
	public ImageContainer(int id, String name, String path, String location, String date) throws ParseException {
		this.id = id;
		this.name = name;
		this.path = path;
		this.location = location;
		this.date = new SimpleDateFormat("dd.MM.yyyy").parse(date);
		this.tags = new ArrayList<String>();
	}
	
	public ImageContainer(ResultSet imageResultSet) throws SQLException, ParseException {
		this.id = imageResultSet.getInt("ID");
		this.date = new SimpleDateFormat("dd.MM.yyyy").parse(imageResultSet.getString("Datum"));				
		this.name = imageResultSet.getString("Fotoname");
		this.path = imageResultSet.getString("Pfad");
		this.location = imageResultSet.getString("Ort");
		this.tags = null;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setDate(String date) throws ParseException {
		this.date = new SimpleDateFormat("dd.mm.yyyy").parse(date);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public int getId() {
		return id;
	}

	public String getPath() {
		return path;
	}
	
	

	

}
