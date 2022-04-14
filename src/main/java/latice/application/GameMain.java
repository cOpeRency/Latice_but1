package latice.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import latice.model.Color;
import latice.model.Shape;
import latice.model.Stack;
import latice.model.Tile;

public class GameMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Stack stack = new Stack();
		
		for (Color color : Color.values()) {
			for (Shape shape : Shape.values()) {
				for (int i = 0; i < 2; i++) {
					stack.addTile(new Tile(shape, color));
				}
			}
		}
		
		stack.showTiles();
		
		BorderPane root = new BorderPane();
		
		// Creation de la scene
		Scene scene = new Scene(root, 1280, 720);
		
		// affichage de la fenetre
		primaryStage.setScene(scene);
		primaryStage.setTitle("Fenetre");
		primaryStage.setResizable(false);
		primaryStage.show();

	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}

}
