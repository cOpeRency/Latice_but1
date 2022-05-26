package latice.model.tiles;

import java.io.Serializable;

import latice.model.players.Rack;
import latice.vue.TileFX;

public abstract class Tile implements Serializable {
	private static final long serialVersionUID = 1L;

	protected String themePath = "src/main/resources/themes/pokemon/";
	
	public abstract void setTileImage();

	public abstract TileFX getTileFX();

	public abstract void setParentRack(Rack rack);

	public abstract String getImagePath();
}
