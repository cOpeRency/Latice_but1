package latice.model.tiles;

import java.io.Serializable;

import latice.model.players.Rack;
import latice.vue.GameVisual;
import latice.vue.TileImageData;

public abstract class Tile implements Serializable {
	private static final long serialVersionUID = 1L;
	protected TileImageData image;
	protected Rack parentRack;
	protected String imagePath;
	
	public void setTileImage() {
		this.image = new TileImageData(this);
	}

	public TileImageData getTileFX() {
		return this.image;
	}

	public void setParentRack(Rack rack) {
		this.parentRack = rack;
		
	}
	
	public String getImagePath() {
		return imagePath;
	}	
	
	public void exitRack() {
    	parentRack.removeTile(this);
	}
	
	public Rack getParentRack() {
		return parentRack;
	}	
}
