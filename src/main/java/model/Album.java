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
 * 
 * @author philpsc
 */
public class Album {

	private StringProperty name = new SimpleStringProperty();
	private int id;
	private ArrayList<ImageContainer> images;

	public Album(String name, int id, ArrayList<ImageContainer> images) {
		this.name = new SimpleStringProperty(name);
		;
		this.id = id;
		this.images = images;
	}

	public Album(String name, int id) {
		this.name = new SimpleStringProperty(name);
		;
		this.id = id;
		this.images = new ArrayList<ImageContainer>();
	}
	
	void sortByDate() {
		images = images.stream()
				       .sorted((i1, i2) -> i2.getDate().compareTo(i1.getDate()))
				       .collect(Collectors.toCollection(ArrayList::new));
	}

	public String getName() {
		return this.name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public final StringProperty nameProperty() {
		return this.name;
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
	
	public String toString() {
		return this.getName();
	}

}
