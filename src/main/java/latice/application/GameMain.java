package latice.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import latice.model.Color;
import latice.model.Rack;
import latice.model.Shape;
import latice.model.Stack;
import latice.model.Tile;

public class GameMain extends Application {
		
		private Stack stack;
		private Stack stackJ1;
		private Stack stackJ2;

	@Override
	public void start(Stage primaryStage) throws Exception {
			
		this.stack = new Stack();
		this.stackJ1 = new Stack();
		this.stackJ2 = new Stack();
		
		for (Color color : Color.values()) {
			for (Shape shape : Shape.values()) {
				for (int i = 0; i < 2; i++) {
					stack.addTile(new Tile(shape, color));
				}
			}
		}	
		stack.initialize(stackJ1, stackJ2);
		System.out.println("----------------PiocheJ1(avant fillRack())-----------------");
		stackJ1.showTiles();
		System.out.println("----------------------------------");
		stackJ2.showTiles();
		System.out.println("--------------RackJ1--------------------");
		
		
		Rack rack = new Rack(stackJ1);
		rack.showRack();
		System.out.println("----------------PiocheJ1(apres fillRack())-------------------");
		stackJ1.showTiles();
		
		BorderPane root = new BorderPane();
		
		// Creation de la scene
		Scene scene = new Scene(root, 1280, 720);
		// affichage de la fenetre
		primaryStage.setScene(scene);
		primaryStage.setTitle("Fenetre");
		primaryStage.setResizable(false);
		primaryStage.show();

	}
	
	//public GridPane createT
	
	public static void main(String[] args) {
		Application.launch(args);
	}

}
