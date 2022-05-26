package latice.model.tiles;

import java.io.Serializable;

import latice.model.players.Rack;
import latice.vue.TileFX;

public class SpecialTile extends Tile implements Serializable{
	private static final long serialVersionUID = 1L;
	private TileFX image;
	private String imagePath;
	private Rack parentRack;
	private TypeOfSpecialTile type;
	
	public SpecialTile(TypeOfSpecialTile type) {
		this.type = type;
		this.imagePath = this.themePath+this.type+".gif";
	}

	@Override
	public void setTileImage() {
		this.image = new TileFX(this);
	}

	@Override
	public TileFX getTileFX() {
		return this.image;
	}

	@Override
	public void setParentRack(Rack rack) {
		this.parentRack = rack;
		
	}
	
	public Rack getParentRack() {
		return parentRack;
	}	
	
	
	public TypeOfSpecialTile getType() {
		return this.type;
	}
	
	public void exitRack() {
    	parentRack.removeTile(this);
	}
	
	public String getImagePath() {
		return imagePath;
	}	

}
