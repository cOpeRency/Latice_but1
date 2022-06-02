package latice.model.tiles;

import java.io.Serializable;

import latice.model.players.Rack;
import latice.vue.TileImageData;

public class SpecialTile extends Tile implements Serializable{
	private static final long serialVersionUID = 1L;
	private TypeOfSpecialTile type;
	
	public SpecialTile(TypeOfSpecialTile type) {
		this.type = type;
		this.imagePath = this.type+".gif";
	}
	
	
	public TypeOfSpecialTile getType() {
		return this.type;
	}


}
