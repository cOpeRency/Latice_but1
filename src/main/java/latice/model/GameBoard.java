package latice.model;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.layout.GridPane;

public class GameBoard {
	
	private Map<Position, Box> gameboardTiles;
	
	public GameBoard() {
		this.gameboardTiles = new HashMap<>();
    }

    //public Map<Position, Box> discs() {
    //    return this.gameboardTiles;
    //}
    
    public GridPane generateGameBoard() {
    	GridPane board = new GridPane();
    	for (int i = 0; i < 9; i++) {
    		for (int j = 0; j < 9; j++) {
	    		Position position = new Position(i, j);
		    	this.gameboardTiles.put(position, new Box(BoxType.SUN));
		    	board.add(this.gameboardTiles.get(position).InitializeStackPane(), j, i);
    		}
		}

    	
    	
    	return board;
    }
}

