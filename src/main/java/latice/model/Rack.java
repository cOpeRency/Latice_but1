package latice.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import latice.vue.RackFX;
import latice.vue.TileFX;

public class Rack implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<Tile> tiles;
	private RackFX rackFX;
	private boolean locked;
	private Player owner;
	
	public Rack(Stack stack, Player owner) {
		this.tiles = new ArrayList();
		this.fillRack(stack);
		this.locked = false;
		this.owner = owner;
	}
	
	public Player getOwner() {
		return this.owner;
	}
	
	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public List<Tile> getTiles() {
		return tiles;
	}
	
	public void addTile(Tile tile) {
		tiles.add(tile);
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
	
	public void showRack() {
		String allTiles = "";
		for (Tile tile : tiles) {
			allTiles = allTiles + tile.toString() + " | ";
		}
		System.out.println("| "+allTiles);
	}
	
	public void createRackFX() {
		this.rackFX = new RackFX(this);
	}
	
	public RackFX getRackFX() {
		return this.rackFX;
	}
	
	public Integer rackLength() {
		return tiles.size();
	}



	
	
}
