package latice.model.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import latice.model.boxes.Box;
import latice.model.boxes.BoxType;
import latice.model.boxes.Position;

public class GameBoard implements Serializable{
	
	private Map<Position, Box> gameboardTiles;
	private List<Position> sunBoxesPosition;
	
	public GameBoard() {
		this.gameboardTiles = new HashMap<>();
		this.sunBoxesPosition = new ArrayList<>();
		this.sunBoxesPosition.add(new Position(0,0));
		this.sunBoxesPosition.add(new Position(1,1));
		this.sunBoxesPosition.add(new Position(2,2));
		this.sunBoxesPosition.add(new Position(6,2));
		this.sunBoxesPosition.add(new Position(7,1));
		this.sunBoxesPosition.add(new Position(8,0));
		this.sunBoxesPosition.add(new Position(0,8));
		this.sunBoxesPosition.add(new Position(1,7));
		this.sunBoxesPosition.add(new Position(2,6));
		this.sunBoxesPosition.add(new Position(6,6));
		this.sunBoxesPosition.add(new Position(7,7));
		this.sunBoxesPosition.add(new Position(8,8));
		this.sunBoxesPosition.add(new Position(0,4));
		this.sunBoxesPosition.add(new Position(4,0));
		this.sunBoxesPosition.add(new Position(4,8));
		this.sunBoxesPosition.add(new Position(8,4));
    }
	
    public Map<Position, Box> getGameboardTiles() {
		return gameboardTiles;
	}


	public Box getBox(Position position) {
		return gameboardTiles.get(position);
	}
    
    public Integer howManyTileHaveBeenPlayed() {
    	Integer numberOfTile = 0;
    	for (Map.Entry<Position, Box> entry : gameboardTiles.entrySet()) {
    		if (entry.getValue().getTile()!=null) {
    			numberOfTile += 1;
    		}
		}
    	return numberOfTile;
    }

	public Map<Position, Box> tiles() {
        return this.gameboardTiles;
    }
    
    public void generateBox() {
    	for (int i = 0; i < 9; i++) {
    		for (int j = 0; j < 9; j++) {
	    		Position position = new Position(i, j);
	    		if (this.sunBoxesPosition.contains(position)) {
	    			this.gameboardTiles.put(position, new Box(BoxType.SUN,this,position));
	    		} else if ((i==4 && j==4)) {
	    			this.gameboardTiles.put(position, new Box(BoxType.MOON,this,position));
	    		} else {
	    			this.gameboardTiles.put(position, new Box(BoxType.NORMAL,this,position));
	    		}
    		}
    	}
    }

}

