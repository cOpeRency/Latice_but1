package latice.model;

import latice.vue.TileFX;

public abstract class Tile {

	protected String themePath = "src/main/resources/themes/pokemon/";
	
	public abstract void setTileImage();

	public abstract TileFX getTileFX();

	public abstract void setParentRack(Rack rack);
}
