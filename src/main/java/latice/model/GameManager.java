package latice.model;

public class GameManager {
	private static Player activePlayer;

	public static Player getActivePlayer() {
		return activePlayer;
	}

	public static void setActivePlayer(Player activePlayer) {
		GameManager.activePlayer = activePlayer;
	}
}
