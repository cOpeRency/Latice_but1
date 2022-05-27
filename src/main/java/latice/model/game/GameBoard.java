package latice.model.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import latice.model.boxes.Box;
import latice.model.boxes.BoxType;
import latice.model.boxes.Position;
import latice.model.tiles.BoardTile;

public class GameBoard implements Serializable{
	
	private Map<Position, Box> gameboardTiles;
	
	public GameBoard() {
		this.gameboardTiles = new HashMap<>();
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
	    		if ((i==0 && j==0)||(i==1 && j==1)||(i==2 && j==2)||(i==0 && j==8)||(i==1 && j==7)||(i==2 && j==6)   ||(i==8 && j==0)||(i==7 && j==1)||(i==6 && j==2)||(i==8 && j==8)||(i==7 && j==7)||(i==6 && j==6)   ||(i==0 && j==4)||(i==4 && j==0)||(i==4 && j==8)||(i==8 && j==4)) {
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

