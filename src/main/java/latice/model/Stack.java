package latice.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Stack {
	private List<Tile> tiles;
	
	public Stack() {
		this.tiles = new ArrayList();
	}
	
	public void addTile(Tile tile) {
		this.tiles.add(tile);
	}
	
	public void removeTile() {
		tiles.remove(0);
	}
	
	public Tile getTile() {
		return tiles.get(0);
	}
	
	public void showTiles() {
		for (Tile tile : tiles) {
			System.out.println(tile.toString());
		}
	}
	
	public void initialize(Stack stackJ1, Stack stackJ2) {
		Collections.shuffle(tiles);
		for (int j = 0; j < 36; j++) {
			stackJ1.addTile(tiles.get(0));
			this.removeTile();
			stackJ2.addTile(tiles.get(0));
			this.removeTile();
		}
	}
	
}
