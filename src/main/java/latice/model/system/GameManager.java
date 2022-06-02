package latice.model.system;

import java.util.Objects;

import latice.model.boxes.Box;
import latice.model.boxes.Position;
import latice.model.game.GameBoard;
import latice.model.players.Player;
import latice.model.tiles.BoardTile;
import latice.model.tiles.SpecialTile;

public class GameManager {
	private static Player activePlayer;
	private static Integer nbCycleMax = 10;
	private static Integer currentNbOfCycles = 1;
	private static GameBoard gameboard;
	private static GameMode gameMode;
	private static boolean canUseSpecialTiles = false;
	
	public static final Integer NO_POINT = 0;
	public static final Integer SUN_POINT = 2;
	public static final Integer DOUBLE_POINT = 1;
	public static final Integer TREFOIL_POINT = 2;
	public static final Integer LATICE_POINT = 4;
	
	public static final Integer BOARD_SIZE = 9;
	
	public static void playBoardTileAt(BoardTile tile,Position position) {
		if (gameboard.getBox(position).checkValidity(tile.getShape(), tile.getColor())) {
			putBoardTile(tile, position);
		}
	}
	
	public static void putBoardTile(BoardTile tile, Position position) {
		if (activePlayer.isAbleToPutATile()) {
			gameboard.getBox(position).setTile(tile);
			activePlayer.setAblilityToPutATile(false);
		} else if (activePlayer.getPoints()>=2) {
			activePlayer.addPoints(-2);
			gameboard.getBox(position).setTile(tile);
			activePlayer.setAblilityToPutATile(false);
		}
	}
	
	
	public static boolean canUseSpecialTiles() {
		return canUseSpecialTiles;
	}

	public static void setCanUseSpecialTiles() {
		if (gameboard.howManyTileHaveBeenPlayed()>=2) {
			GameManager.canUseSpecialTiles = true;
		} else {
			GameManager.canUseSpecialTiles = false;
		}
	}

	/*public static void playSpecialTile(SpecialTile tile, Position position) {
		if (canUseSpecialTiles) {
			switch (tile.getType()) {
			case
			}
		}
	} */

	
	public static Player getActivePlayer() {
		return activePlayer;
	}

	public static void setActivePlayer(Player activePlayer) {
		GameManager.activePlayer = activePlayer;
	}
	
	
	
	public static GameMode getGameMode() {
		return gameMode;
	}

	public static void setGameMode(GameMode gameMode) {
		GameManager.gameMode = gameMode;
	}

	public static GameBoard getGameboard() {
		return gameboard;
	}

	public static void setGameboard(GameBoard gameboard) {
		GameManager.gameboard = gameboard;
	}
	
	

	public static void addCycles(Integer value) {
		GameManager.currentNbOfCycles += value;
	}

	public static Integer getNbCycleMax() {
		return nbCycleMax;
	}

	public static Integer getCurrentNbOfCycles() {
		return currentNbOfCycles;
	}

	public static void startTurn(Player activePlayer, Player inactivePlayer) {
		activePlayer.setMyTurn(true);
		GameManager.setActivePlayer(activePlayer);
		inactivePlayer.setMyTurn(false);
	}
	
	public static boolean determineGameEnd() {
		if (Objects.equals(currentNbOfCycles, nbCycleMax)) {
			return true;	
		}
		return false;
	}
}
