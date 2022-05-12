package latice.model;

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
import latice.vue.TileFX;

public class Tile implements Serializable{
	private static final long serialVersionUID = 1L;
	private final Shape shape;
	private final Color color;
	public static Tile NO = null;
	private Rack parentRack;
	private Box parentBox;
	private boolean locked = false;
	private TileFX tileFX;
	private String imagePath = "src/main/resources/themes/classic/";
	
	public Tile(Shape shape, Color color) {
		super();
		this.shape = shape;
		this.color = color;
		this.imagePath = this.imagePath+this.shape.code()+"_"+this.color.code()+".png";
	}
	
	public TileFX getTileFX() {
		return this.tileFX;
	}
	
	public void setTileImage() {
		this.tileFX = new TileFX(this);
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
	
	public void resetPositionFX() {
		parentBox.getBoxFX().getChildren().add(tileFX);
	}
	
	public void exitRack() {
    	parentRack.removeTile(this);
	}
	
	public void exitBox() {
    	parentBox.removeTile(this);
	}
	
	public void exitRackFX() {
    	parentRack.removeTileFX(this);
	}
	
	public void exitBoxFX() {
    	parentBox.removeTileFX(this);
	}
	
	
	public void setParentRack(Rack parent) {
		this.parentRack = parent;
	}
	
	public void setParentBox(Box parent) {
		this.parentBox = parent;
	}
	
	public String toString() {
		return this.shape.code()+" "+this.color.code();
	}

	
	
	public Rack getParentRack() {
		return parentRack;
	}

	public Box getParentBox() {
		return parentBox;
	}

	public String getImagePath() {
		return imagePath;
	}
}
