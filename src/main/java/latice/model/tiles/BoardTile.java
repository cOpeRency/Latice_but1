package latice.model.tiles;

import java.io.Serializable;

import latice.model.boxes.Box;
import latice.model.players.Rack;
import latice.vue.TileFX;

public class BoardTile extends Tile implements Serializable{
	private static final long serialVersionUID = 1L;
	private final Shape shape;
	private final Color color;
	public final static BoardTile NO = null;
	private Box parentBox;
	private boolean locked = false;
	
	public BoardTile(Shape shape, Color color) {
		this.shape = shape;
		this.color = color;
		this.imagePath = this.shape.code()+"_"+this.color.code()+".png";

	}
	
	public boolean isLocked() {
		return this.locked;
	}

	public void setLocked(boolean isLocked) {
		this.locked = isLocked;
	}

	public Shape getShape() {
		return shape;
	}

	public Color getColor() {
		return color;
	}

	public void resetPosition() {
		parentBox.setTile(this);
	}

	
	public void exitBox() {
    	parentBox.removeTile();
	}

	
	public void setParentBox(Box parent) {
		this.parentBox = parent;
	}
	
	public String toString() {
		return this.shape.code()+" "+this.color.code();
	}

	public Box getParentBox() {
		return parentBox;
	}

}
