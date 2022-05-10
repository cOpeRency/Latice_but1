package latice.application;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
		
		private HBox playersRacks;
		private HBox rackP1vbox;
		private HBox rackP2vbox;
		
		private VBox player1Infos;
		
		private Button p1ValidButton;
		

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		gameStart();
		
		player1.getRack().showRack();
		player2.getRack().showRack();
		
		
		Random quiCommence = new Random();
		if ((quiCommence.nextInt(3-1) + 1)==1) {
			startGameplay(player1,player2);
		} else {
			startGameplay(player2,player1);
		}

		//rackP1vbox.getChildren().addAll(null);

		
		BorderPane borderPane = new BorderPane();
		GameBoard gameBoard = new GameBoard();
		borderPane.setCenter(gameBoard.generateGameBoard());
		borderPane.setBottom(playersRacks);
		borderPane.setLeft(player1Infos);
		//root.setRight(rackP2vbox);

		
		//----------------A mettre dans un test case fx : ajout d'une tuile manuellemet-------------------
		Position position = new Position(0, 4);
		//System.out.println("avant : "+gameBoard.getBox(position).getTile());
		gameBoard.getBox(position).setTile(new Tile(Shape.SHAPE1,Color.COLOR1));
		//System.out.println(gameBoard.getBox(position).getTile());
		
		StackPane root = new StackPane();
		String urlFichier;
		try {
			File fichier = new File("src/main/resources/themes/pokemon/bg_game.png");
			urlFichier = fichier.toURI().toURL().toString();
			ImageView background = new ImageView(new Image(urlFichier, 1300, 731, true, true));
			root.getChildren().add(background);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		root.getChildren().add(borderPane);
		
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
	
	public void startGameplay(Player firstPlayer, Player secondPlayer) {
		System.out.println(firstPlayer.getName()+" commence ! Que la partie débute !");
		
		this.rackP1vbox = new HBox();
		this.rackP1vbox.setPadding(new Insets(10,10,10,10));
		this.rackP1vbox.setSpacing(20);
		player1.getRack().setHbox(rackP1vbox);
		this.rackP1vbox.getChildren().addAll(player1.getRack().getTiles());
		this.rackP1vbox.setPrefWidth(410);
		this.rackP2vbox = new HBox();
		this.rackP2vbox.setPadding(new Insets(10,10,10,10));
		this.rackP2vbox.setSpacing(20);
		this.rackP2vbox.getChildren().addAll(player2.getRack().getTiles());
		player2.getRack().setHbox(rackP2vbox);
		this.rackP2vbox.setPrefWidth(410);

		this.playersRacks = new HBox();
		this.playersRacks.getChildren().addAll(this.rackP1vbox,this.rackP2vbox);
		this.playersRacks.setAlignment(Pos.CENTER);
		this.playersRacks.setSpacing(150);
		this.playersRacks.setPadding(new Insets(0,0,23,0));
		
		this.p1ValidButton = new Button("Valider");
		this.p1ValidButton.setDisable(true);
		this.player1Infos = new VBox();
		this.player1Infos.setPrefWidth(300);
		this.player1Infos.getChildren().addAll(new Label("P1"),this.p1ValidButton);
	}
}
