package latice.vue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import latice.application.GameMain;
import latice.model.boxes.Box;
import latice.model.boxes.BoxType;
import latice.model.boxes.Position;
import latice.model.system.GameManager;
import latice.model.system.GameMode;
import latice.model.tiles.BoardTile;
import latice.model.tiles.Color;
import latice.model.tiles.Shape;

public class BoxFX extends StackPane implements Serializable{
	private Box box;
	public static String VALID_HOVER_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(255,255,0,0.8), 15, 0.8, 0, 0);";
	public static String NOT_VALID_HOVER_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(150,0,0,0.8), 15, 0.8, 0, 0);";
	public static String NO_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0), 0, 0, 0, 0);";
	private String imgURL;
	
	public BoxFX(Box box) {
		this.box = box;
		initBoxImage(this.box.getBoxType());
		initDragSystem();
	}
	
	public void initBoxImage(BoxType boxType) {
		if (boxType == BoxType.SUN) {
			this.imgURL = "src/main/resources/themes/pokemon/bg_sun.png";
		} else if (boxType == BoxType.MOON) {
			this.imgURL = "src/main/resources/themes/pokemon/bg_moon.png";
		} else {
			this.imgURL = "src/main/resources/themes/pokemon/bg_sea.png";
		}
		String urlFichier;
		try {
			File fichier = new File(imgURL);
			urlFichier = fichier.toURI().toURL().toString();
			Image img = new Image(urlFichier, 62, 62, true, true);
			ImageView imgView = new ImageView(img);
			this.getChildren().add(imgView);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initDragSystem() {
		setOnDragEntered(new EventHandler<DragEvent>() {
		    @Override
		    public void handle(DragEvent event) {
		    	if (event.getDragboard().getString()=="WIND" || event.getDragboard().getString()=="THUNDER" || (!GameManager.getGameMode().equals(GameMode.SINGLE_PUT_TILE) && canDragedTileBePutHere(event.getDragboard()))) {
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
		    	if (event.getDragboard().getString()=="WIND" || event.getDragboard().getString()=="THUNDER") {
	    			event.acceptTransferModes(TransferMode.MOVE);
		    	} else if (GameManager.getGameMode().equals(GameMode.WIND_TILE) && canDragedTileBePutHere(dragboard)) {
		    		event.acceptTransferModes(TransferMode.MOVE);
		    	} else if (GameManager.getGameMode().equals(GameMode.SINGLE_PUT_TILE) && checkValidity((BoardTile)dragboard.getContent(GameMain.TILE_DATA))) {
		    		if (dragboard.hasImage()) {
		    			event.acceptTransferModes(TransferMode.MOVE);
		    		}
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
		    		if (dragboard.getString()=="WIND" || dragboard.getString()=="THUNDER") {
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
		if (box.getTile()==null && tile.getParentBox().getAdjacentBoxes().contains(box)) {
			return true;
		}
		return false;
	}
	
	public void setTile(BoardTile tile) {
		//If there is at least one tile on gameboard and tile is from a box, we reuse the extra move 
		if (GameManager.getGameMode().equals(GameMode.SINGLE_PUT_TILE) && this.box.getGameboard().getPlayingTiles().size()>0 && tile.getParentBox()!=null) {
			GameManager.getActivePlayer().addPoints(-2);
			GameManager.getActivePlayer().getPlayerFX().setPointProperty();
			GameManager.getActivePlayer().getPlayerFX().setExtraMoveButtonDisability(true);
		}
		this.box.setTile(tile);
		this.box.getTile().setTileImage();
		this.getChildren().add(this.box.getTile().getTileFX());
		this.box.getGameboard().addPlayingTile(tile);
		GameManager.getActivePlayer().getPlayerFX().disableExchangeButton();
	}
	
	public boolean checkValidity(BoardTile tile) {
		return this.box.checkValidity(tile.getShape(),tile.getColor());
	}
	
}
