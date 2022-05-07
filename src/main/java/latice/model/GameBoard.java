package latice.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.layout.GridPane;

public class GameBoard {
	
	private Map<Position, Box> gameboardTiles;
	
	public GameBoard() {
		this.gameboardTiles = new HashMap<>();
    }

    public Map<Position, Box> tiles() {
        return this.gameboardTiles;
    }
    
    public GridPane generateGameBoard() {
    	GridPane board = new GridPane();
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
		    	board.add(this.gameboardTiles.get(position), j, i);
    		}
		}
    	board.setStyle("-fx-grid-lines-visible: true");
    	return board;
    }
    
    public Box getBox(Position position) {
    	for (Map.Entry<Position, Box> entry : gameboardTiles.entrySet()) {
			if (entry.getKey().row()==position.row() && entry.getKey().column()==position.column()) {
				return entry.getValue();
			}
		}
    	
    	return null;
    }
    
}

