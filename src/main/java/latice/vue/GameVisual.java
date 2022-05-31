package latice.vue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import latice.model.boxes.Box;
import latice.model.boxes.Position;
import latice.model.game.GameBoard;
import latice.model.tiles.BoardTile;

public class GameVisual {
	private static GameBoard gameboard;
	private static AnchorPane root;
	private static List<BoardTile> playingTiles = new ArrayList<BoardTile>();
	private static GameTheme theme;

	
	
	public static String getTheme() {
		return theme.code();
	}


	public static void setTheme(GameTheme theme) {
		GameVisual.theme = theme;
	}


	public static List<BoardTile> getPlayingTiles() {
		return playingTiles;
	}
	
	
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
	
    public static void addPlayingTile(BoardTile newTile) {

		for (BoardTile tile : playingTiles) {
			tile.getTileFX().setLastTilePlayed(false);
		}
		playingTiles.add(newTile);
	}
	
	public static void removePlayingTile() {
		playingTiles.remove(playingTiles.size()-1);
		if (playingTiles.size()>=1) {
			playingTiles.get(playingTiles.size()-1).getTileFX().setLastTilePlayed(true);
		}
	}
    
    public static void lockPlayingTiles() {
    	for (BoardTile tile : playingTiles) {
			tile.setLocked(true);
		}
    	playingTiles.clear();
    	
    }
    
    public static void resetPlayingTileEffect() {
    	for (BoardTile tile : playingTiles) {
			tile.getTileFX();
			tile.getTileFX().setStyle(TileFX.SHADOW_EFFECT);
		}
    	
    }
}
