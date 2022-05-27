package latice.vue;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class GameVisual {
	private static AnchorPane root;

	public static AnchorPane getRoot() {
		return root;
	}

	public static void setRoot(AnchorPane root) {
		GameVisual.root = root;
	}
	
	
}
