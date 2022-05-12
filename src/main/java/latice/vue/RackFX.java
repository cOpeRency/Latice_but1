package latice.vue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import latice.model.Tile;

public class RackFX extends HBox implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<Tile> tiles;
	
	
	public RackFX() {
		this.tiles = new ArrayList<Tile>();
		setPadding(new Insets(10,10,10,10));
		setSpacing(20);
		setPrefWidth(410);
	}
	
	public void setRack(List<Tile> tiles) {
		for (Tile tile : tiles) {
			tile.setTileImage();
			this.getChildren().add(tile.getTileFX());
		}
	}
	

}
