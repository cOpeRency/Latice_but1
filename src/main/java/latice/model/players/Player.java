package latice.model.players;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import latice.model.system.GameManager;
import latice.model.tiles.BoardTile;
import latice.model.tiles.Tile;
import latice.vue.PlayerVisualData;

public class Player implements Serializable{
	private static final long serialVersionUID = 1L;
	private boolean myTurn;
	private Rack rack;
	private String name;
	private Integer points;
	private Stack stack;
	private boolean ableToPutATile;
	private PlayerVisualData visualData;
	
	public static final Integer EXTRA_MOVE_COST = 2;
	
	public Player(String name) {
		this.name = name;
		this.stack = new Stack();
		this.points = GameManager.NO_POINT;
		this.myTurn = false;
		this.ableToPutATile = false;

	}
	
	public void initPlayerVisualData() {
		this.visualData = new PlayerVisualData(this);
	}
	
	public void createRack() {
		this.rack = new Rack(stack,this);
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
	
	public boolean isAbleToPutATile() {
		return this.ableToPutATile;
	}

	public boolean isMyTurn() {
		return myTurn;
	}
	
	public Rack getRack() {
		return rack;
	}
	
	public List<BoardTile> getAllBoardTilesLeft(){
		List<BoardTile> boardTiles = new ArrayList<>();

    	for (Tile tile : this.rack.content()) {
			if (tile.getClass().equals(BoardTile.class)) {
				boardTiles.add((BoardTile)tile);
			}
		}
    	boardTiles.addAll(stack.getBoardTiles());
		return boardTiles;
	}

	public PlayerVisualData getVisualData() {
		return this.visualData;
	}

	public Stack getStack() {
		return stack;
	}

	public void setMyTurn(boolean myTurn) {
		this.myTurn = myTurn;
		this.ableToPutATile = myTurn;
		this.rack.setLocked(!myTurn);
	}

	public void setAblilityToPutATile(boolean ability) {
		this.ableToPutATile = ability;
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
