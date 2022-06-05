package latice.vue.system;

import javafx.stage.Stage;

public class PrimaryStage {
	private static Stage stage;

	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		PrimaryStage.stage = stage;
	}
	
	
}
