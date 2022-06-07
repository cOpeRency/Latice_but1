package latice.model.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import latice.model.boxes.Box;
import latice.model.boxes.BoxType;
import latice.model.boxes.Position;
import latice.model.system.GameManager;

public class GameBoard implements Serializable{
	
	private Map<Position, Box> boxes;
	private List<Position> sunBoxesPosition;
	
	public GameBoard() {
		this.boxes = new HashMap<>();
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
	
    public Map<Position, Box> getBoxes() {
		return boxes;
	}


	public Box getBox(Position position) {
		return boxes.get(position);
	}
    
    public Integer howManyTileHaveBeenPlayed() {
    	Integer numberOfTile = 0;
    	for (Map.Entry<Position, Box> entry : boxes.entrySet()) {
    		if (entry.getValue().getTile()!=null) {
    			numberOfTile += 1;
    		}
		}
    	System.out.println(numberOfTile);
    	return numberOfTile;
    }
    
    public List<Box> getEmptyBoxes() {
    	List<Box> emptyBoxes = new ArrayList<>();
    	for (Map.Entry<Position, Box> entry : boxes.entrySet()) {
    		if (entry.getValue().getTile()==null) {
    			emptyBoxes.add(entry.getValue());
    		}
		}
    	return emptyBoxes;
    }
    
    public void generateBox() {
    	for (int i = 0; i < GameManager.BOARD_SIZE; i++) {
    		for (int j = 0; j < GameManager.BOARD_SIZE; j++) {
	    		Position position = new Position(i, j);
	    		if (this.sunBoxesPosition.contains(position)) {
	    			this.boxes.put(position, new Box(BoxType.SUN,this,position));
	    		} else if (position.equals(Box.MOON_POSITION)) {
	    			this.boxes.put(position, new Box(BoxType.MOON,this,position));
	    		} else {
	    			this.boxes.put(position, new Box(BoxType.NORMAL,this,position));
	    		}
    		}
    	}
    }

}

