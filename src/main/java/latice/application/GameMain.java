package latice.application;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
import latice.vue.RackFX;

public class GameMain extends Application {
		public static DataFormat TILE_DATA = new DataFormat("Tile");
		private GameBoard gameBoard;
	
		private Stack stack;
		private Player player1;
		private Player player2;
		
		private HBox playersRacks;
		private RackFX rackP1vbox;
		private RackFX rackP2vbox;
		
		private VBox player1Infos;
		private VBox player2Infos;
		
		private IntegerProperty p1Points;
		
		private Button p1ValidButton;
		private Button p2ValidButton;
		

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
		this.gameBoard = new GameBoard();
		this.gameBoard.generateBox();
		borderPane.setCenter(gameBoard.generateGameBoard());
		borderPane.setBottom(playersRacks);
		borderPane.setLeft(player1Infos);
		borderPane.setRight(player2Infos);
		//root.setRight(rackP2vbox);

		
		//----------------A mettre dans un test case fx : ajout d'une tuile manuellemet-------------------
		//Position position = new Position(0, 4);
		//System.out.println("avant : "+gameBoard.getBox(position).getTile());
		//gameBoard.getBox(position).setTile(new Tile(Shape.SHAPE1,Color.COLOR1));
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
		
		initializePlayersRack(firstPlayer,secondPlayer);
		startTurn(firstPlayer,secondPlayer);

		//this.p1Points = new SimpleIntegerProperty();
		Label p1StackSize = new Label();
		//p1Pts.textProperty().bind(p1Points.asString());
		p1StackSize.setTextFill(javafx.scene.paint.Color.WHITE);
		p1StackSize.setFont(Font.font(null, FontWeight.NORMAL, 35));
		p1StackSize.setMinHeight(50);
		p1StackSize.setText("Tiles left : "+firstPlayer.getStackSize().toString());
		this.p1ValidButton = new Button("Valider");
		this.p1ValidButton.setPrefSize(170, 70);
		this.p1ValidButton.setFont(Font.font(null, FontWeight.NORMAL, 35));
		this.p1ValidButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	gameBoard.resetPlayingTileEffect();
		    	gameBoard.lockPlayingTiles();
		    	firstPlayer.getRack().fillRack(firstPlayer.getStack());
				rackP1vbox.setRack(firstPlayer.getRack().getTiles());
				p1StackSize.setText("Tiles left : "+firstPlayer.getStackSize().toString());
		    	startTurn(secondPlayer,firstPlayer);
		    	p2ValidButton.setDisable(false);
		    	p1ValidButton.setDisable(true);
		    }
		});
		this.player1Infos = new VBox();
		this.player1Infos.setPrefWidth(300);
		Label p1name = new Label(firstPlayer.getName());
		p1name.setTextFill(javafx.scene.paint.Color.WHITE);
		p1name.setFont(Font.font(null, FontWeight.NORMAL, 35));
		p1name.setMinHeight(50);
		
		ImageView p1Picture = new ImageView();
		try {
			File fichier = new File("src/main/resources/profilePictures/face.png");
			p1Picture.setImage(new Image(fichier.toURI().toURL().toString(), 150, 150, true, true));
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		this.player1Infos.getChildren().addAll(p1name,p1Picture,p1StackSize,this.p1ValidButton);
		this.player1Infos.setAlignment(Pos.CENTER);
		
		
		this.p2ValidButton = new Button("Valider");
    	p2ValidButton.setDisable(true);
		this.p2ValidButton.setPrefSize(170, 70);
		this.p2ValidButton.setFont(Font.font(null, FontWeight.NORMAL, 35));
		this.p2ValidButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	gameBoard.resetPlayingTileEffect();
		    	gameBoard.lockPlayingTiles();
		    	secondPlayer.getRack().fillRack(secondPlayer.getStack());
				rackP2vbox.setRack(secondPlayer.getRack().getTiles());
		    	startTurn(firstPlayer,secondPlayer);
		    	p1ValidButton.setDisable(false);
		    	p2ValidButton.setDisable(true);
		    }
		});
		this.player2Infos = new VBox();
		this.player2Infos.setPrefWidth(300);
		Label p2name = new Label(secondPlayer.getName());
		p2name.setTextFill(javafx.scene.paint.Color.WHITE);
		p2name.setFont(Font.font(null, FontWeight.NORMAL, 35));
		p2name.setMinHeight(50);
		
		ImageView p2Picture = new ImageView();
		try {
			File fichier = new File("src/main/resources/profilePictures/face.png");
			p2Picture.setImage(new Image(fichier.toURI().toURL().toString(), 150, 150, true, true));
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		this.player2Infos.getChildren().addAll(p2name,p2Picture,this.p2ValidButton);
		this.player2Infos.setAlignment(Pos.CENTER);
	}

	private void startTurn(Player activePlayer, Player inactivePlayer) {
		activePlayer.setMyTurn(true);
		inactivePlayer.setMyTurn(false);
	}
	
	private void initializePlayersRack(Player firstPlayer, Player secondPlayer) {
		firstPlayer.getRack().createRackFX();
		this.rackP1vbox = firstPlayer.getRack().getRackFX();
		//player1.getRack().setHbox(rackP1vbox);
		this.rackP1vbox.setRack(firstPlayer.getRack().getTiles());
		//this.rackP1vbox.getChildren().addAll(player1.getRack().getTiles());
		
		secondPlayer.getRack().createRackFX();
		this.rackP2vbox = secondPlayer.getRack().getRackFX();
		this.rackP2vbox.setRack(secondPlayer.getRack().getTiles());
		//this.rackP2vbox.getChildren().addAll(player2.getRack().getTiles());
		//player2.getRack().setHbox(rackP2vbox);

		this.playersRacks = new HBox();
		this.playersRacks.getChildren().addAll(this.rackP1vbox,this.rackP2vbox);
		this.playersRacks.setAlignment(Pos.CENTER);
		this.playersRacks.setSpacing(150);
		this.playersRacks.setPadding(new Insets(0,0,23,0));
	}
}
