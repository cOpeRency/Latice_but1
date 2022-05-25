package latice.model;

import latice.vue.TileFX;

public class SpecialTile extends Tile{
	private TileFX tileFX;
	private String imagePath;
	private Rack parentRack;
	private TypeOfSpecialTile type;
	
	public SpecialTile(TypeOfSpecialTile type) {
		this.type = type;
		this.imagePath = this.themePath+this.type+".png";
	}

	@Override
	public void setTileImage() {
		this.tileFX = new TileFX(null);
	}

	@Override
	public TileFX getTileFX() {
		return this.tileFX;
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
