package latice.application;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Random;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import latice.audio.musicManager;
import latice.model.game.GameBoard;
import latice.model.players.Player;
import latice.model.players.Stack;
import latice.model.system.GameManager;
import latice.model.system.GameMode;
import latice.vue.GameVisual;
import latice.vue.RackVisualData;
import latice.vue.menu.GameMenu;
import latice.vue.menu.SelectMenu;
import latice.vue.system.PrimaryStage;

public class GameMain extends Application {
		public static DataFormat TILE_DATA = new DataFormat("BoardTile");
		private GameBoard gameBoard;
		
		private BorderPane borderPane;
	
		private Stack stack;
		private Player player1;
		private Player player2;
		
		private HBox playersRacks;
		private RackVisualData rackP1vbox;
		private RackVisualData rackP2vbox;
		
		private MediaPlayer audio;
		
		private Label lblCycles;

		

	@Override
	public void start(Stage primaryStage) throws Exception {
		PrimaryStage.setStage(primaryStage);
		primaryStage.setScene(GameMenu.getSceneMenu());
		Scene nextScene = SelectMenu.getSceneMenu();
		GameMenu.getBtnStart().setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	primaryStage.setScene(nextScene);
		    }
		});
		

		SelectMenu.getBtnStart().setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	if (!SelectMenu.getTfNameJ1().getText().isEmpty() && !SelectMenu.getTfNameJ2().getText().isEmpty() && !SelectMenu.getTfNameJ1().getText().equals(SelectMenu.getTfNameJ2().getText())) {		    		
		    		primaryStage.setScene(LoadingScreen.getScene());
		    		if(SelectMenu.getCbUseCycles().isSelected()) {
		    			GameManager.setNbCycleMax(SelectMenu.getSpCycles().getValue());
		    		}
		    		initGame(primaryStage);
		    	}
		    }
		});
		primaryStage.setTitle("Latice");
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}


	public void initGame(Stage primaryStage) {
		gameStart();
		this.gameBoard = new GameBoard();
		GameManager.setGameboard(gameBoard);
		GameVisual.setGameboard(gameBoard);
		this.borderPane = new BorderPane();
		
		player1.getRack().showRack();
		player2.getRack().showRack();
		
		Random quiCommence = new Random();
		if ((quiCommence.nextInt(3-1) + 1)==1) {
			startGameplay(player1,player2,primaryStage);
		} else {
			startGameplay(player2,player1,primaryStage);
		}
		this.gameBoard.generateBox();
		GridPane gp = GameVisual.generateGameBoard();
		this.borderPane.setCenter(gp);
		BorderPane.setAlignment(gp, Pos.CENTER);
		this.borderPane.setBottom(playersRacks);
		
		this.lblCycles = new Label();
    	setLabelCycleText();
		this.lblCycles.setTextFill(javafx.scene.paint.Color.WHITE);
		this.lblCycles.setFont(Font.font(null, FontWeight.BOLD, 15));
		this.lblCycles.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 6, 0.4, 0, 0);");
		this.borderPane.setTop(lblCycles);
		BorderPane.setAlignment(lblCycles, Pos.CENTER);
		
		AnchorPane root = new AnchorPane();
		String urlFichier;
		GameVisual.setRoot(root);
		try {
			File fichier = new File("src/main/resources/themes/"+GameVisual.getTheme()+"/bg_game.png");
			urlFichier = fichier.toURI().toURL().toString();
			ImageView background = new ImageView(new Image(urlFichier, 1300, 731, true, true));
			GameVisual.getRoot().getChildren().add(background);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		GameVisual.getRoot().getChildren().add(borderPane);
		
		
		
		try {
			File fichier = new File("src/main/resources/themes/"+GameVisual.getTheme()+"/intro.mp4");
			Media media = new Media(fichier.toURI().toURL().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(media);
	        mediaPlayer.setAutoPlay(true);
			MediaView mediaViewer = new MediaView(mediaPlayer);
			GameVisual.getRoot().getChildren().add(mediaViewer);
			mediaPlayer.setOnEndOfMedia(new Runnable() {
				@Override
				public void run() {
			    	GameVisual.getRoot().getChildren().remove(mediaViewer);
				}
			});
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.audio = musicManager.getBackgroundMusic();
		this.audio.play();
		Scene scene = new Scene(GameVisual.getRoot(), 1280, 720);
		System.out.println("start");
		primaryStage.setScene(scene);
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}


	private void setLabelCycleText() {
		if (SelectMenu.getCbUseCycles().isSelected()) {
			this.lblCycles.setText("Cycle : "+GameManager.getCurrentNbOfCycles()+"/"+GameManager.getNbCycleMax());
		} else {
			this.lblCycles.setText("Cycle : "+GameManager.getCurrentNbOfCycles());
		}
	}
	
	private void gameStart() {
		this.stack = new Stack();
		this.player1 = new Player(SelectMenu.getTfNameJ1().getText());
		this.player2 = new Player(SelectMenu.getTfNameJ2().getText());
		
		this.player1.initPlayerVisualData();
		this.player2.initPlayerVisualData();
		stack.initialize(player1, player2);
		player1.createRack();
		player2.createRack();
	}
	
	private void startGameplay(Player firstPlayer, Player secondPlayer,Stage primaryStage) {
		System.out.println(firstPlayer.getName()+" commence ! Que la partie débute !");
    	System.out.println("Cycle n°"+GameManager.getCurrentNbOfCycles());
    	
    	GameManager.setGameMode(GameMode.PUT_SINGLE_TILE);
    	
		this.borderPane.setLeft(firstPlayer.getVisualData().getVbInfos());
		this.borderPane.setRight(secondPlayer.getVisualData().getVbInfos());
		((VBox) this.borderPane.getLeft()).setPadding(new Insets(0, 0, 0, 40));
		((VBox) this.borderPane.getRight()).setPadding(new Insets(0, 40, 0, 0));
		
		initializePlayersRack(firstPlayer,secondPlayer);
		startTurn(firstPlayer,secondPlayer);
		rackP2vbox.hideTiles(secondPlayer.getRack().content());
		secondPlayer.getVisualData().getBtnValidate().setDisable(true);
		secondPlayer.getVisualData().getBtnExchange().setDisable(true);

		firstPlayer.getVisualData().getBtnValidate().setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	pressValidateButton(firstPlayer, secondPlayer, rackP1vbox, rackP2vbox);
		    }
		});
		
		secondPlayer.getVisualData().getBtnValidate().setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	pressValidateButton(secondPlayer, firstPlayer, rackP2vbox, rackP1vbox);
		    	determineGameEnd(primaryStage);
		    }
		});
		
	}
	
	
	private void initializePlayersRack(Player firstPlayer, Player secondPlayer) {
		firstPlayer.getRack().initVisualData();
		this.rackP1vbox = firstPlayer.getRack().getVisualData();
		this.rackP1vbox.setRack(firstPlayer.getRack().content());
		
		secondPlayer.getRack().initVisualData();
		this.rackP2vbox = secondPlayer.getRack().getVisualData();
		this.rackP2vbox.setRack(secondPlayer.getRack().content());
	
		this.playersRacks = new HBox();
		this.playersRacks.getChildren().addAll(this.rackP1vbox,this.rackP2vbox);
		this.playersRacks.setAlignment(Pos.CENTER);
		this.playersRacks.setSpacing(150);
		this.playersRacks.setPadding(new Insets(44,0,0,0));
	}


	private void startTurn(Player newActivePlayer, Player inactivePlayer) {
		GameManager.startTurn(newActivePlayer, inactivePlayer);
		newActivePlayer.getVisualData().updateBuyTileButtonDisability();
		inactivePlayer.getVisualData().getBuyTileButton().setDisable(true);
		newActivePlayer.getVisualData().setExtraMoveButtonDisability(true);
		inactivePlayer.getVisualData().setExtraMoveButtonDisability(true);
		newActivePlayer.getRack().getVisualData().createCanPlayEffect(true);
		inactivePlayer.getRack().getVisualData().createCanPlayEffect(false);
		
		if (!GameManager.canPlayerPlay(newActivePlayer)) {
			if (!GameManager.canPlayerPlay(inactivePlayer)) {
				System.out.println("Personne ne peut jouer !");
				gameEnd(PrimaryStage.getStage());
			} else {
				System.out.println(newActivePlayer.getName()+" ne peut pas jouer !");
			}
		}
		
	}


	private void pressValidateButton(Player playerWhoPressed, Player otherPlayer, RackVisualData hbRackPlayerWhoPressed, RackVisualData hbRackOtherPlayer) {
    	if (playerWhoPressed.isAbleToPutATile() && !GameVisual.getPlayingTiles().isEmpty()) {
    		playerWhoPressed.addPoints(2);
    		playerWhoPressed.getVisualData().setPointProperty();
    	}
    	if (GameManager.hasWon(playerWhoPressed)) {
    		gameEnd(PrimaryStage.getStage());
    	} else {
    		GameVisual.resetPlayingTileEffect();
    		GameVisual.lockPlayingTiles();
    		playerWhoPressed.getRack().fillRack(playerWhoPressed.getStack());
    		hbRackPlayerWhoPressed.setRack(playerWhoPressed.getRack().content());
    		hbRackPlayerWhoPressed.hideTiles(playerWhoPressed.getRack().content());
    		hbRackOtherPlayer.showTiles(otherPlayer.getRack().content());
    		playerWhoPressed.getVisualData().getStackSizeLabel().setText("Tiles left : "+playerWhoPressed.getStack().stackLength().toString());
    		startTurn(otherPlayer,playerWhoPressed);
    		otherPlayer.getVisualData().getBtnValidate().setDisable(false);
    		playerWhoPressed.getVisualData().getBtnValidate().setDisable(true);
    		playerWhoPressed.getVisualData().getBtnExchange().setDisable(true);
    		otherPlayer.getVisualData().getBtnExchange().setDisable(false);	
    	}
    	
	}
	
	private void determineGameEnd(Stage primaryStage) {
		if (GameManager.determineGameEnd()) {
			gameEnd(primaryStage);
		} else {
	    	GameManager.addCycles(1);
	    	setLabelCycleText();
		}
		
	}


	private void gameEnd(Stage primaryStage) {
		this.player1.setMyTurn(false);
		this.player2.setMyTurn(false);
		
		if (this.player1.getAllTilesLeft().size()<this.player2.getAllTilesLeft().size()) {
			System.out.println(this.player1.getName()+" a gagné !");
		} else if (this.player2.getAllTilesLeft().size()<this.player1.getAllTilesLeft().size()) {
			System.out.println(this.player2.getName()+" a gagné !");
		} else {
			System.out.println("Egalité !");
		}
		
		primaryStage.close();
	}
	
}
