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
	public ImageContainer(int id, String name, String path, String location, String date, ArrayList<String> tags)  {
		this.id = id;
		this.name = name;
		this.path = path;
		this.location = location;
		try {
			this.date = new SimpleDateFormat("dd.MM.yyyy").parse(date);
		} catch (ParseException e) {
			this.date = null;
			e.printStackTrace();
		}				

		this.tags = tags;
	}
	
	public ImageContainer(int id, String name, String path, String location, String date) {
		this.id = id;
		this.name = name;
		this.path = path;
		this.location = location;
		try {
			this.date = new SimpleDateFormat("dd.MM.yyyy").parse(date);
		} catch (ParseException e) {
			this.date = null;
			e.printStackTrace();
		}
		this.tags = new ArrayList<String>();
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setDate(String date) {
		try {
			this.date = new SimpleDateFormat("dd.mm.yyyy").parse(date);
		} catch (ParseException e) {			
			e.printStackTrace();
		}
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
