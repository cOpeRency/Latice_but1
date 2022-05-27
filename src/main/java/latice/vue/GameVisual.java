package latice.vue;

import java.util.Map;

import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import latice.model.boxes.Box;
import latice.model.boxes.Position;
import latice.model.game.GameBoard;

public class GameVisual {
	private static GameBoard gameboard;
	private static AnchorPane root;

	
	
	
	
	public static void setGameboard(GameBoard gameboard) {
		GameVisual.gameboard = gameboard;
	}

	public static AnchorPane getRoot() {
		return root;
	}

	public static void setRoot(AnchorPane root) {
		GameVisual.root = root;
	}
	
    public static GridPane generateGameBoard() {
    	GridPane board = new GridPane();

    	for (Map.Entry<Position, Box> entry : gameboard.getGameboardTiles().entrySet()) {
    		entry.getValue().setBoxImage();
    		board.add(entry.getValue().getBoxFX(), entry.getKey().column(), entry.getKey().row());
		}
		    	
    	
    	board.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(255,255,153,0.8), 15, 0.7, 0, 0);");
    	board.setPadding(new Insets(20,68,0,68));
    	return board;
    }
	
	
}
