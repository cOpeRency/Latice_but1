package latice.vue;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import latice.application.GameMain;
import latice.model.boxes.Box;
import latice.model.boxes.BoxType;
import latice.model.system.GameManager;
import latice.model.system.GameMode;
import latice.model.tiles.BoardTile;

public class BoxImageData extends StackPane implements Serializable{
	private static final long serialVersionUID = 1L;
	private Box box;
	public static final String VALID_HOVER_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(255,255,0,0.8), 15, 0.8, 0, 0);";
	public static final String NOT_VALID_HOVER_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(150,0,0,0.8), 15, 0.8, 0, 0);";
	public static final String NO_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0), 0, 0, 0, 0);";
	private String imgURL;
	
	public BoxImageData(Box box) {
		this.box = box;
		initBoxImage(this.box.getBoxType());
		initDragSystem();
	}
	
	public void initBoxImage(BoxType boxType) {
		if (boxType == BoxType.SUN) {
			this.imgURL = "src/main/resources/themes/"+GameVisual.getTheme()+"/bg_sun.png";
		} else if (boxType == BoxType.MOON) {
			this.imgURL = "src/main/resources/themes/"+GameVisual.getTheme()+"/bg_moon.png";
		} else {
			this.imgURL = "src/main/resources/themes/"+GameVisual.getTheme()+"/bg_sea.png";
		}
		String urlFichier;
		try {
			File fichier = new File(imgURL);
			urlFichier = fichier.toURI().toURL().toString();
			Image img = new Image(urlFichier, 62, 62, true, true);
			ImageView imgView = new ImageView(img);
			this.getChildren().add(imgView);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void callThunder(){

		String urlFichier;
		try {
			File fichier = new File("src/main/resources/effects/thunderEffect.gif");
			urlFichier = fichier.toURI().toURL().toString();
			Image img = new Image(urlFichier,1600,900,true,true);
			ImageView imgView = new ImageView(img);
			ColorAdjust colorAdjust = new ColorAdjust();
			
			Blend blend = new Blend();    
			blend.setMode(BlendMode.ADD);
			
			colorAdjust.setInput(blend);
			
			imgView.setEffect(colorAdjust);

			System.out.println(this.getLayoutY());
			// 502 = posX de la 8e colonne de tuile (la ou la foudre tombe si setX(0))
			imgView.setX(this.getLayoutX()-502);
			// 578 = posY de la 10e ligne de tuile (en dehors du plateau du coup) (la ou la foudre tombe si setY(0))
			imgView.setY(this.getLayoutY()-578);
			
			Timeline timeline = new Timeline(
				    new KeyFrame(Duration.ZERO, e ->  
				    	GameVisual.getRoot().getChildren().add(imgView)
				    	),
				    new KeyFrame(Duration.seconds(0.4), e -> {
				    	box.getTile().getParentBox().getBoxFX().getChildren().remove(box.getTile().getTileFX());
			    		box.getTile().exitBox();
				    }),
				    new KeyFrame(Duration.seconds(1.2), e ->  
				    	GameVisual.getRoot().getChildren().remove(imgView)
				    	)
				);
				timeline.play();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void initDragSystem() {
		setOnDragEntered(new EventHandler<DragEvent>() {
		    @Override
		    public void handle(DragEvent event) {
		    	if (event.getDragboard().getString().equals("WIND") || event.getDragboard().getString().equals("THUNDER")|| (!GameManager.getGameMode().equals(GameMode.SINGLE_PUT_TILE) && canDragedTileBePutHere(event.getDragboard()))) {
			        setStyle(VALID_HOVER_EFFECT);
			        toFront();
		    	} else if (GameManager.getGameMode().equals(GameMode.SINGLE_PUT_TILE) && checkValidity((BoardTile)event.getDragboard().getContent(GameMain.TILE_DATA))) {
		    		if (event.getDragboard().hasImage()) {
				        setStyle(VALID_HOVER_EFFECT);
				        toFront();
			    	}
		    	} else {
			        setStyle(NOT_VALID_HOVER_EFFECT);
			        toFront();
		    	}
		        event.consume();
		    }
		});
		setOnDragExited(new EventHandler<DragEvent>() {
		    @Override
		    public void handle(DragEvent event) {
		        setStyle(NO_EFFECT);
		        event.consume();
		    }
		});
		
		
		setOnDragOver(new EventHandler<DragEvent>() {
		    @Override
		    public void handle(DragEvent event) {
		    	Dragboard dragboard = event.getDragboard();
		    	if (("WIND".equals(event.getDragboard().getString()) || "THUNDER".equals(event.getDragboard().getString())) 
		    			|| (GameManager.getGameMode().equals(GameMode.WIND_TILE) && canDragedTileBePutHere(dragboard))
		    			|| (GameManager.getGameMode().equals(GameMode.SINGLE_PUT_TILE) && checkValidity((BoardTile)dragboard.getContent(GameMain.TILE_DATA)) && dragboard.hasImage())) {
	    			event.acceptTransferModes(TransferMode.MOVE);
		    	}
		        event.consume();
		    }
		});
		setOnDragDropped(new EventHandler<DragEvent>() {
		    @Override
		    public void handle(DragEvent event) {
		    	Dragboard dragboard = event.getDragboard();
		    	boolean success = false;
		    	if (dragboard.hasImage()) {
		    		success = true;
		    	}
		    	if (dragboard.hasString()) {
		    		if (dragboard.getString().equals("WIND") || dragboard.getString().equals("THUNDER")) {
		    			System.out.println("vent detecté");
		    		} else if (GameManager.getGameMode().equals(GameMode.WIND_TILE)) {
			    		setTile((BoardTile)dragboard.getContent(GameMain.TILE_DATA));
			    		GameManager.getActivePlayer().setAblilityToPutATile(true);
			    		GameManager.getActivePlayer().getRack().getRackFX().createCanPlayEffect(true);
		    		} else {
			    		setTile((BoardTile)dragboard.getContent(GameMain.TILE_DATA));
			    		if (!GameManager.canUseSpecialTiles()) {
			    			GameManager.setCanUseSpecialTiles();
			    		}
			    		GameManager.getActivePlayer().getPlayerFX().setPointProperty();
			    		GameManager.getActivePlayer().setAblilityToPutATile(false);
			    		GameManager.getActivePlayer().getRack().getRackFX().createCanPlayEffect(false);
			    		if (!GameManager.getActivePlayer().isAbleToPutATile() && GameManager.getActivePlayer().getPoints()>=2) {
			    			GameManager.getActivePlayer().getPlayerFX().setExtraMoveButtonDisability(false);
			    		}
		    		}
		    	}
		    	
		    	event.setDropCompleted(success);
		        event.consume();
		    }
		});
	}
	
	
	public boolean canDragedTileBePutHere(Dragboard dragboard) {
		BoardTile tile = (BoardTile)dragboard.getContent(GameMain.TILE_DATA);
		if (box.getTile()==BoardTile.NO && tile.getParentBox().getAdjacentBoxes().contains(box)) {
			return true;
		}
		return false;
	}
	
	public void setTile(BoardTile tile) {
		//If there is at least one tile on gameboard and tile is from a box, we reuse the extra move 
		if (GameManager.getGameMode().equals(GameMode.SINGLE_PUT_TILE) && !GameVisual.getPlayingTiles().isEmpty() && tile.getParentBox()!=null) {
			GameManager.getActivePlayer().addPoints(-2);
			GameManager.getActivePlayer().getPlayerFX().setPointProperty();
			GameManager.getActivePlayer().getPlayerFX().setExtraMoveButtonDisability(true);
		}
		this.box.setTile(tile);
		this.box.getTile().setTileImage();
		this.getChildren().add(this.box.getTile().getTileFX());
		if (GameManager.getGameMode().equals(GameMode.SINGLE_PUT_TILE)) {
			GameVisual.addPlayingTile(tile);
		}
		GameManager.getActivePlayer().getPlayerFX().disableExchangeButton();
	}
	
	public boolean checkValidity(BoardTile tile) {
		return this.box.checkValidity(tile.getShape(),tile.getColor());
	}
	
}
