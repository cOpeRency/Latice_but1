package latice.application;

import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import latice.model.Box;
import latice.model.Color;
import latice.model.GameBoard;
import latice.model.Player;
import latice.model.Position;
import latice.model.Rack;
import latice.model.Shape;
import latice.model.Stack;
import latice.model.Tile;

public class GameMain extends Application {
		
		private Stack stack;
		private Player player1;
		private Player player2;
		
		

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		gameStart();
		
		player1.getRack().showRack();
		player2.getRack().showRack();
		
		BorderPane root = new BorderPane();
		GameBoard gameBoard = new GameBoard();
		root.setCenter(gameBoard.generateGameBoard());
		
		
		System.out.println("----------------A mettre dans un test case fx : ajout d'une tuile-------------------");
		Position position = new Position(0, 1);
		System.out.println("avant : "+gameBoard.getBox(position).getTile());
		gameBoard.getBox(position).setTile(new Tile(Shape.DBZ,Color.BLUE));
		System.out.println(gameBoard.getBox(position).getTile());
		
		
		
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
	
	
	
	public void gameStart() {
		this.stack = new Stack();
		this.player1 = new Player("Albert");
		this.player2 = new Player("Bernard");
		
		stack.initialize(player1, player2);
		player1.createRack();
		player2.createRack();
	}
}
