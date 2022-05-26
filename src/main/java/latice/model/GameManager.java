package latice.model;

import javafx.stage.Stage;

public class GameManager {
	private static Player activePlayer;
	private static Integer nbCycleMax = 10;
	private static Integer currentNbOfCycles = 1;
	private static GameBoard gameboard;
	private static GameMode gameMode;
	
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
		if (currentNbOfCycles == nbCycleMax) {
			return true;	
		}
		return false;
	}
}
