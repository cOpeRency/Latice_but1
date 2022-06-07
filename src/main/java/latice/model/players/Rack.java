package latice.model.players;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import latice.model.tiles.Tile;
import latice.vue.RackVisualData;

public class Rack implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<Tile> tiles;
	private RackVisualData rackVisualData;
	private boolean locked;
	private Player owner;
	private Integer currentMaxRackSize = 5;
	
	private static final Integer RACK_CAPACITY = 5;
	
	public Rack(Stack stack, Player owner) {
		this.tiles = new ArrayList<>();
		this.fillRack(stack);
		this.locked = false;
		this.owner = owner;
	}
	
	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public void addTile(Tile tile) {
		tiles.add(tile);
		tiles.get(tiles.size()-1).setParentRack(this);
	}
	
	public void fillRack(Stack stack) {
		if (stack.stackLength()+tiles.size() < RACK_CAPACITY) {
			this.currentMaxRackSize = stack.stackLength()+tiles.size();
		}
		while (tiles.size() < this.currentMaxRackSize) {
			tiles.add(stack.getFirstTile());
			tiles.get(tiles.size()-1).setParentRack(this);
			stack.removeTile();
		}
	}
	
	public void removeTile(Tile tile) {
		this.tiles.remove(tile);
	}
	
	public void exchange(Stack stack) {
		Integer currentTileNumber = tiles.size();
		while (!tiles.isEmpty()) {
			stack.addTile(tiles.remove(0));
		}
		Collections.shuffle(stack.content());
		
		while (tiles.size() < currentTileNumber) {
			tiles.add(stack.getFirstTile());
			tiles.get(tiles.size()-1).setParentRack(this);
			stack.removeTile();
		}
	}
	
	public void showRack() {
		String allTiles = "";
		for (Tile tile : tiles) {
			allTiles = allTiles + tile.toString() + " | ";
		}
		System.out.println("| "+allTiles);
	}
	
	public void initVisualData() {
		this.rackVisualData = new RackVisualData(this);
	}
	
	public Integer rackLength() {
		return tiles.size();
	}

	public boolean isLocked() {
		return locked;
	}

	public List<Tile> content() {
		return tiles;
	}

	public Player getOwner() {
		return this.owner;
	}

	public RackVisualData getVisualData() {
		return this.rackVisualData;
	}



	
	
}
