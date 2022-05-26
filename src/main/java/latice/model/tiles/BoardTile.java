package latice.model.tiles;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import latice.model.boxes.Box;
import latice.model.players.Rack;
import latice.vue.TileFX;

public class BoardTile extends Tile implements Serializable{
	private static final long serialVersionUID = 1L;
	private final Shape shape;
	private final Color color;
	public static BoardTile NO = null;
	private Box parentBox;
	private boolean locked = false;
	private TileFX tileFX;
	private String imagePath;
	private Rack parentRack;
	
	public BoardTile(Shape shape, Color color) {
		this.shape = shape;
		this.color = color;
		this.imagePath = this.themePath+this.shape.code()+"_"+this.color.code()+".png";

	}
	
	@Override
	public TileFX getTileFX() {
		return this.tileFX;
	}
	
	public Rack getParentRack() {
		return parentRack;
	}	
	
	public void exitRack() {
    	parentRack.removeTile(this);
	}
	
	public void setParentRack(Rack parent) {
		this.parentRack = parent;
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
    	parentBox.removeTile(this);
	}

	
	public void setParentBox(Box parent) {
		this.parentBox = parent;
	}
	
	public String toString() {
		return this.shape.code()+" "+this.color.code();
	}

	@Override
	public void setTileImage() {
		this.tileFX = new TileFX(this);
	}
	

	public Box getParentBox() {
		return parentBox;
	}
	
	public String getImagePath() {
		return imagePath;
	}	

}
