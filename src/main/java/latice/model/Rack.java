package latice.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import latice.vue.RackFX;

public class Rack implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<Tile> tiles;
	private RackFX rackFX;
	
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
	}
	
	public void removeTileFX(Tile tile) {
		this.rackFX.getChildren().remove(tile.getTileFX());
	}
	
	public void showRack() {
		String allTiles = "";
		for (Tile tile : tiles) {
			allTiles = allTiles + tile.toString() + " | ";
		}
		System.out.println("| "+allTiles);
	}
	
	public void createRackFX() {
		this.rackFX = new RackFX();
	}
	
	public RackFX getRackFX() {
		return this.rackFX;
	}
	
	public Integer rackLength() {
		return tiles.size();
	}
	
}
