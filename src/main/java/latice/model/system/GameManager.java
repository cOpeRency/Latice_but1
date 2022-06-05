package latice.model.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import latice.model.boxes.Box;
import latice.model.boxes.Position;
import latice.model.game.GameBoard;
import latice.model.players.Player;
import latice.model.tiles.BoardTile;
import latice.model.tiles.SpecialTile;
import latice.model.tiles.Tile;

public class GameManager {
	private static Player activePlayer;
	private static Integer nbCycleMax = 50;
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
	public static final Integer MINIMUM_OF_PUT_TILES_FOR_USE_SPECIAL_ONE = 2;
	
	public static void playBoardTileAt(BoardTile tile,Position position) {
		if (gameboard.getBox(position).checkValidity(tile.getShape(), tile.getColor())) {
			putBoardTile(tile, position);
		}
	}
	
	public static boolean hasWon(Player player) {
		if (player.getAllBoardTilesLeft().isEmpty()) {
			return true;
		}
		return false;
	}
	
	public static boolean canPlayerPlay(Player player) {
    	List<BoardTile> boardtiles = player.getAllBoardTilesLeft();
		if (boardtiles.size()>15) {
			System.out.println("trop de tuiles");
			return true;
		} else {
			if (gameboard.howManyTileHaveBeenPlayed()>40) {
		    	List<Box> emptyBoxes = gameboard.getEmptyBoxes();
		    	for (Box box : emptyBoxes) {
					for (BoardTile tile : boardtiles) {
						if (box.checkValidity(tile.getShape(), tile.getColor())) {
							System.out.println("peut jouer "+tile.toString()+" a "+box.getPosition().toString());
							return true;
						}
					}
				}
		    	return false;
			}
			System.out.println("pas assez de tuiles sur le plateau");
			return true;
		}
	}
	
	
	public static void putBoardTile(BoardTile tile, Position position) {
		if (activePlayer.isAbleToPutATile()) {
			gameboard.getBox(position).setTile(tile);
			activePlayer.setAblilityToPutATile(false);
		} else if (activePlayer.getPoints()>=Player.EXTRA_MOVE_COST) {
			activePlayer.addPoints(-Player.EXTRA_MOVE_COST);
			gameboard.getBox(position).setTile(tile);
			activePlayer.setAblilityToPutATile(false);
		}
	}
	
	
	public static boolean canUseSpecialTiles() {
		return canUseSpecialTiles;
	}

	

	/*public static void playSpecialTile(SpecialTile tile, Position position) {
		if (canUseSpecialTiles) {
			switch (tile.getType()) {
			case
			}
		}
	} */

	
	public static void addCycles(Integer value) {
		GameManager.currentNbOfCycles += value;
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

	public static GameBoard getGameboard() {
		return gameboard;
	}

	public static Integer getNbCycleMax() {
		return nbCycleMax;
	}

	public static GameMode getGameMode() {
		return gameMode;
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

	/*public static void playSpecialTile(SpecialTile tile, Position position) {
		if (canUseSpecialTiles) {
			switch (tile.getType()) {
			case
			}
		}
	} */
	
	
	public static void setActivePlayer(Player activePlayer) {
		GameManager.activePlayer = activePlayer;
	}

	/*public static void playSpecialTile(SpecialTile tile, Position position) {
		if (canUseSpecialTiles) {
			switch (tile.getType()) {
			case
			}
		}
	} */
	
	
	public static void setGameMode(GameMode gameMode) {
		GameManager.gameMode = gameMode;
	}

	/*public static void playSpecialTile(SpecialTile tile, Position position) {
		if (canUseSpecialTiles) {
			switch (tile.getType()) {
			case
			}
		}
	} */
	
	
	public static void setGameboard(GameBoard gameboard) {
		GameManager.gameboard = gameboard;
	}

	public static Integer getCurrentNbOfCycles() {
		return currentNbOfCycles;
	}

	public static void setCanUseSpecialTiles() {
		if (gameboard.howManyTileHaveBeenPlayed()>=MINIMUM_OF_PUT_TILES_FOR_USE_SPECIAL_ONE) {
			GameManager.canUseSpecialTiles = true;
		} else {
			GameManager.canUseSpecialTiles = false;
		}
	}
}
