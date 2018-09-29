/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class defines albums which have a name, an ID and a collection of images.
 * @author Phillip Persch, Aude Takam Nana
 */
public class Album {

	// StringProperty instead of String because custom list viel cell needs name binding to work
	private StringProperty name = new SimpleStringProperty();
	private int id;
	private ArrayList<ImageContainer> images;

	/**
	 * Constructor for album objects including a non-empty collection of images.
	 * @param name name of the album
	 * @param id ID of the album
	 * @param images ArrayList of ImageContainer objects
	 */
	public Album(String name, int id, ArrayList<ImageContainer> images) {
		this.name = new SimpleStringProperty(name);
		;
		this.id = id;
		this.images = images;
	}

	/**
	 * Convenience constructor with an empty collection of images.
	 * @param name
	 * @param id
	 */
	public Album(String name, int id) {
		this.name = new SimpleStringProperty(name);
		;
		this.id = id;
		this.images = new ArrayList<ImageContainer>();
	}
	
	/**
	 * This method sorts this album's images by date (new to old)
	 */
	void sortByDate() {
		images = images.stream()
				       .sorted((i1, i2) -> i2.getDate().compareTo(i1.getDate()))
				       .collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * Getter for field name.
	 * @return the album's name as a String.
	 */
	public String getName() {
		return this.name.get();
	}

	/**
	 * Setter for field name.
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name.set(name);
	}

	/**
	 * This method returns the nameProperty of this album.
	 * @return observable property of the name, used for binding to other properties
	 */
	public final StringProperty nameProperty() {
		return this.name;
	}

	/**
	 * Getter for field Id.
	 * @return the album's ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter for field Id.
	 * @param id the new ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Getter for field images.
	 * @return this album's images
	 */
	public ArrayList<ImageContainer> getImages() {
		return images;
	}

	/**
	 * Setter for field images.
	 * @param images the new images
	 */
	public void setImages(ArrayList<ImageContainer> images) {
		this.images = images;
	}
	
	/**
	 * This method defines how album objects are displayed as Strings.
	 */
	public String toString() {
		return this.getName();
	}

}
