package latice.model;

import java.util.ArrayList;
import java.util.List;


public class Stack {
	private List<Tile> tiles;
	
	public Stack() {
		this.tiles = new ArrayList();
	}
	
	public void addTile(Tile tile) {
		this.tiles.add(tile);
	}
	
	public void showTiles() {
		for (Tile tile : tiles) {
			System.out.println(tile.toString());
		}
	}
	
}
