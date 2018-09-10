package model.editing;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * Class for undoing edits
 * @author Julian Einspenner
 *
 */
public class Undo {

	public static LinkedList<BufferedImage> fifo = new LinkedList<>();
	
	
	
	public static void addImage(BufferedImage img) {
		if(fifo.size() == 10) {
			fifo.removeFirst();
		}
		fifo.add(img);
	}
	
	
	public static void undoLastEdit() {
		if(fifo.size() <= 1) {
			return;
		}
		fifo.remove();
	}
	
	public static void resetListOfEditedImages() {
		fifo = new LinkedList<>();
	}
	
}

