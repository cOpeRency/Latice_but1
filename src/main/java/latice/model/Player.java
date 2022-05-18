package latice.model;

import java.io.Serializable;
import java.util.Objects;

import latice.vue.PlayerFX;

public class Player implements Serializable{
	private boolean myTurn;
	private Rack rack;
	private String name;
	private Integer points;
	private Stack stack;
	private boolean ableToPutATile;
	private PlayerFX playerFX;
	
	public Player(String name) {
		this.name = name;
		this.stack = new Stack();
		this.points = 0;
		this.myTurn = false;
		this.ableToPutATile = false;

	}
	
	public PlayerFX getPlayerFX() {
		return this.playerFX;
	}
	
	public void initPlayerFX() {
		this.playerFX = new PlayerFX(this);
	}
	
	public boolean isAbleToPutATile() {
		return this.ableToPutATile;
	}
	
	
	public void setAblilityToPutATile(boolean ability) {
		this.ableToPutATile = ability;
	}
	
	public void addPoints(Integer points) {
		this.points = this.points + points;
	}
	
	public Integer getPoints() {
		return this.points;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean isMyTurn() {
		return myTurn;
	}
	
	public void setMyTurn(boolean myTurn) {
		this.myTurn = myTurn;
		this.ableToPutATile = myTurn;
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

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return Objects.equals(name, other.name);
	}


	
	
	


	
	
}
