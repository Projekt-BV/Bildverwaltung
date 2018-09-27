/**
 * 
 */
package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Wrapper class for handling images
 * 
 * @author philpsc
 */
public class ImageContainer implements Serializable {

	private static final long serialVersionUID = -1705421401516164743L;
	private int id;
	private Date date;
	private String name;
	private String path;
	private String location;
	private ArrayList<String> tags;

	/**
	 * Constructor for ImageContainer objects
	 * 
	 * @author Phillip Persch
	 * @throws ParseException
	 * @param id
	 * @param name 
	 * @param path the path where the image is stored on the machine
	 * @param location the geolocation of the image
	 * @param date today's date, but can be changed by the user in the application
	 * @param tags the keywords the user associates with his image
	 */
	public ImageContainer(int id, String name, String path, String location, String date, ArrayList<String> tags) {
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
	
	/**
	 * Convenience constructor for ImageContainer objects without a tag list.
	 * 
	 * @author Phillip Persch
	 * @throws ParseException
	 * @param id
	 * @param name 
	 * @param path the path where the image is stored on the machine
	 * @param location the geolocation of the image
	 * @param date today's date, but can be changed by the user in the application
	 */
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

	/**
	 * Getter for the field date.
	 * 
	 * @author Phillip Persch
	 * @return the imageContainers date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Setter for the field date using a Date.
	 * 
	 * @author Phillip Persch
	 * @param date the new date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	
	/**
	 * Setter for the field date using a String
	 * 
	 * @author Phillip Persch
	 * @param date a String representing the new date
	 */
	public void setDate(String date) {
		try {
			this.date = new SimpleDateFormat("dd.mm.yyyy").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Getter for the field name.
	 * 
	 * @author Phillip Persch
	 * @return the imageContainer's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for the field name.
	 * 
	 * @author Phillip Persch
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for the field location
	 * 
	 * @author Phillip Persch
	 * @return the imageContainer's geolocation
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * Setter for the field location
	 * 
	 * @author Phillip Persch
	 * @param location the new location
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
	 * Setter for the field path
	 * 
	 * @author Phillip Persch
	 * @param path the new path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Getter for the field tags
	 * 
	 * @author Phillip Persch
	 * @return the imageContainer's tag list
	 */
	public ArrayList<String> getTags() {
		return tags;
	}

	/**
	 * Setter for the field tags.
	 * 
	 * @author Phillip Persch
	 * @param tags the new tag list
	 */
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	
	/**
	 * Getter for the field Id.
	 * 
	 * @author Phillip Persch
	 * @return the imageContainer's ID
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Getter for the field Path.
	 * 
	 * @author Phillip Persch
	 * @return the path where the imageContainer is stored
	 */
	public String getPath() {
		return path;
	}

}
