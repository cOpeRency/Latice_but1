package latice.model;

import java.util.ArrayList;
import java.util.List;

public class Rack {
	private List<Tile> tiles;
	
	public Rack(Stack stack) {
		this.tiles = new ArrayList();
		this.fillRack(stack);
	}
	
	public void fillRack(Stack stack) {
		while (tiles.size() < 5) {
			tiles.add(stack.getTile());
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
}
