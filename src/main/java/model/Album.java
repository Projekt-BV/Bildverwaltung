/**
 * 
 */
package model;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
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
