package latice.model;

public class Tile {
	private final Shape shape;
	private final Color color;
	public static Tile NO = null;
	
	public Tile(Shape shape, Color color) {
		this.shape = shape;
		this.color = color;
	}
	
	public String toString() {
		return this.shape+" "+this.color;
	}
}
