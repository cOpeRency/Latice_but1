package latice.model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Rack {
	private List<Tile> tiles;
	private HBox hbox;
	
	public Rack(Stack stack) {
		this.tiles = new ArrayList();
		this.fillRack(stack);
	}
	
	public List<Tile> getTiles() {
		return tiles;
	}
	
	public void fillRack(Stack stack) {
		while (tiles.size() < 5) {
			tiles.add(stack.getTile());
			tiles.get(tiles.size()-1).setParentRack(this);;
			stack.removeTile();
		}
	}
	
	public void removeTile(Tile tile) {
		this.tiles.remove(tile);
		this.hbox.getChildren().remove(tile);
	}
	
	public void showRack() {
		String allTiles = "";
		for (Tile tile : tiles) {
			allTiles = allTiles + tile.toString() + " | ";
		}
		System.out.println("| "+allTiles);
	}
	
	public Integer rackLength() {
		return tiles.size();
	}
	
	public void setHbox(HBox hbox) {
		this.hbox = hbox;
	}
}
