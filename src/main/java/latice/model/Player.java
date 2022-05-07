package latice.model;

public class Player {
	private Rack rack;
	private String name;
	private Integer points;
	private Stack stack;
	
	public Player(String name) {
		this.name = name;
		this.stack = new Stack();
		this.points = 0;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Stack getStack() {
		return stack;
	}
	
	public void createRack() {
		this.rack = new Rack(stack);
	}
	
	public Rack getRack() {
		return rack;
	}
}
