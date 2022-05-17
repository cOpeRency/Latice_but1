package latice.model;

import java.io.Serializable;

public class Player implements Serializable{
	private boolean myTurn;
	private Rack rack;
	private String name;
	private Integer points;
	private Stack stack;
	
	public Player(String name) {
		this.name = name;
		this.stack = new Stack();
		this.points = 0;
		this.myTurn = false;
	}
	
	public void addPoints(Integer points) {
		this.points = this.points + points;
		System.out.println(this.hashCode());
		System.out.println(this.points);
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean isMyTurn() {
		return myTurn;
	}
	
	public void setMyTurn(boolean myTurn) {
		this.myTurn = myTurn;
		this.rack.setLocked(!myTurn);
	}
	
	public Stack getStack() {
		return stack;
	}
	
	public Integer getStackSize() {
		return stack.stackLength();
	}
	
	public void createRack() {
		this.rack = new Rack(stack,this);
	}
	
	public Rack getRack() {
		return rack;
	}
}
