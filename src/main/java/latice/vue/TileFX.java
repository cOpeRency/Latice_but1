package latice.vue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import latice.application.GameMain;
import latice.model.BoardTile;
import latice.model.GameManager;
import latice.model.GameMode;
import latice.model.SpecialTile;
import latice.model.Tile;

public class TileFX extends ImageView implements Serializable {
	private static final long serialVersionUID = 1L;
	private Tile tileSource;
	private boolean isLastTilePlayed;
	private static String HOVER_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(200,200,0,0.8), 15, 0.6, 0, 0);";
	private static String NOT_FIXED_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(200,0,200,0.8), 15, 0.6, 0, 0);";
	private static String LAST_TILE_PLAYED_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(0,250,200,0.8), 15, 0.6, 0, 0);";
	public static String SHADOW_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0.4, 0, 0);";
	
	
	public TileFX(Tile tile) {
		this.tileSource = tile;
		this.isLastTilePlayed = true;
		if (tileSource.getClass().equals(BoardTile.class)) {
			initDragAndDrop((BoardTile) tileSource);
			setTileEffects((BoardTile) tileSource);
			
		} else {
			initDragAndDrop((SpecialTile) tileSource);
			setSpecialTileEffects();
		}
		setTileImage();
	}

	
	public boolean isLastTilePlayed() {
		return isLastTilePlayed;
	}

	public void setLastTilePlayed(boolean isLastTilePlayed) {
		this.isLastTilePlayed = isLastTilePlayed;
		if (isLastTilePlayed) {
			setStyle(LAST_TILE_PLAYED_EFFECT);		    			
		} else {
			setStyle(NOT_FIXED_EFFECT);
		}
	}

	
	public void hideTile() {
		String urlFichier;
		try {
			File fichier = new File("src/main/resources/themes/pokemon/back.png");
			urlFichier = fichier.toURI().toURL().toString();
			Image img = new Image(urlFichier, 62, 62, true, true);
			setImage(img);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}	
	
	public void setTileImage() {
		String urlFichier;
		try {
			File fichier = new File(this.tileSource.getImagePath());
			urlFichier = fichier.toURI().toURL().toString();
			Image img = new Image(urlFichier, 62, 62, true, true);
			setImage(img);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}	

	
	public Tile getTileSource() {
		return tileSource;
	}

	public void initDragAndDrop(BoardTile boardTile) {
		setOnDragDetected(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	if (GameManager.getGameMode().equals(GameMode.SINGLE_PUT_TILE) && !boardTile.isLocked() && isLastTilePlayed && !boardTile.getParentRack().isLocked() && boardTile.getParentRack().getOwner().isAbleToPutATile()) {
			    	Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
			        setStyle(SHADOW_EFFECT);
			        
			        ClipboardContent content = new ClipboardContent();
			        content.putImage(getImage());
			        content.putString(boardTile.getShape().toString()+"_"+boardTile.getColor().toString());
			        
			        if (boardTile.getParentBox()!=null) {
			        	content.putString("BoxToRack");
			        }
			        
			        content.put(GameMain.TILE_DATA, boardTile);
			        
			        
			        
			        
			        if (boardTile.getParentBox()!=null) {
			        	deletePlayerPointsOnExit(boardTile);
			    		
			        	//When we bought an extra move and then we move a tile from the gameboard before using it, we reset the extra move
			        	// Or when we take an extra tile which is already played, we reset the extra move too
			        	if (GameManager.getActivePlayer().isAbleToPutATile() || boardTile.getParentBox().getGameboard().getPlayingTiles().size()>1) {
			        		GameManager.getActivePlayer().addPoints(2);
			        		GameManager.getActivePlayer().getPlayerFX().setPointProperty();
			        	}
			        	
			        	boardTile.exitBox();
			        	boardTile.getParentBox().getBoxFX().getChildren().remove(boardTile.getTileFX());
			        	boardTile.getParentBox().getGameboard().removePlayingTile();
			    		
			    		GameManager.getActivePlayer().getPlayerFX().enableExchangeButton();
			    		
			    		if (boardTile.getParentBox().getGameboard().getPlayingTiles().size()==0) {
			    			GameManager.getActivePlayer().getPlayerFX().setExtraMoveButtonDisability(true);
			    		}
			    		
			    		GameManager.getActivePlayer().setAblilityToPutATile(true);
			    		GameManager.getActivePlayer().getRack().getRackFX().createCanPlayEffect(true);
			    	}
			        dragboard.setContent(content);
			        event.consume();
		    	
		    	} else if (GameManager.getGameMode().equals(GameMode.WIND_TILE) && boardTile.getParentBox()!=null) {
		        	
		    		Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
			        setStyle(SHADOW_EFFECT);
			        
			        ClipboardContent content = new ClipboardContent();
			        content.putImage(getImage());
			        content.putString(boardTile.getShape().toString()+"_"+boardTile.getColor().toString());
			        
			        content.put(GameMain.TILE_DATA, boardTile);
			        

			        dragboard.setContent(content);
			        
		        	boardTile.exitBox();
		        	boardTile.getParentBox().getBoxFX().getChildren().remove(boardTile.getTileFX());
		        	
			        event.consume();
		    	}
		    }


		});
		
		setOnDragDone(new EventHandler<DragEvent>() {
		    @Override
		    public void handle(DragEvent event) {
		    	if (event.getTransferMode() == TransferMode.MOVE) {
			    	if (boardTile.getParentRack()!=null) {
			    		boardTile.exitRack();
			    		boardTile.getParentRack().getRackFX().getChildren().remove(boardTile.getTileFX());
			    		if (GameManager.getGameMode().equals(GameMode.SINGLE_PUT_TILE)) {
			    			setStyle(NOT_FIXED_EFFECT);
			    		} else {
			    			GameManager.setGameMode(GameMode.SINGLE_PUT_TILE);
					    	GameManager.getActivePlayer().getPlayerFX().getExtraMoveButton().setDisable(true);
					    	GameManager.getActivePlayer().getPlayerFX().getBtnExchange().setDisable(false);
					    	GameManager.getActivePlayer().getPlayerFX().getBtnValidate().setDisable(false);
			    		}
			    	}
		    	} else  if (boardTile.getParentBox()!=null) {
		    		boardTile.resetPosition();
		    		boardTile.getParentBox().getBoxFX().getChildren().add(boardTile.getTileFX());
		    		if (GameManager.getGameMode().equals(GameMode.SINGLE_PUT_TILE)) {
		    			GameManager.getActivePlayer().setAblilityToPutATile(false);
		    			GameManager.getActivePlayer().getRack().getRackFX().createCanPlayEffect(false);
		    			if (GameManager.getActivePlayer().getPoints()>=2) {
		    				GameManager.getActivePlayer().getPlayerFX().setExtraMoveButtonDisability(false);
		    			}
		    			GameManager.getActivePlayer().getPlayerFX().setPointProperty();
		    			
		    			boardTile.getParentBox().getGameboard().addPlayingTile(boardTile);
		    			GameManager.getActivePlayer().getPlayerFX().disableExchangeButton();
		    			
		    			// If there is more than 1 playing tile on the gamebord, it mean that the extra move is reused, so we lose 2 points
		    			if (boardTile.getParentBox().getGameboard().getPlayingTiles().size()>1) {
		    				GameManager.getActivePlayer().addPoints(-2);
		    				GameManager.getActivePlayer().getPlayerFX().setPointProperty();
		    				GameManager.getActivePlayer().getPlayerFX().setExtraMoveButtonDisability(true);
		    			}
		    		}
		    	}
		        event.consume();
		    }
		});
	}
	

	private void deletePlayerPointsOnExit(BoardTile boardTile) {
		Integer points = boardTile.getParentBox().getTileMatchType(boardTile.getParentBox().getAdjacentBoxes()).value()   +   boardTile.getParentBox().gainPointBySunBox();
		GameManager.getActivePlayer().addPoints(-points);
		GameManager.getActivePlayer().getPlayerFX().setPointProperty();
	}
	
	private void setTileEffects(BoardTile boardTile) {
		this.setStyle(SHADOW_EFFECT);
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	if (boardTile.getParentRack() != null) {
			        setStyle(HOVER_EFFECT);
		    	}
		    }
		});
		this.setOnMouseExited(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	if (boardTile.isLocked() || boardTile.getParentBox()==null) {
		    		setStyle(SHADOW_EFFECT);
		    	} else {
		    		if (isLastTilePlayed) {
		    			setStyle(LAST_TILE_PLAYED_EFFECT);		    			
		    		} else {
		    			setStyle(NOT_FIXED_EFFECT);
		    		}
		    	}
		    }
		});
	}
	
	
	private void setSpecialTileEffects() {
		this.setStyle(SHADOW_EFFECT);
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	setStyle(HOVER_EFFECT);
		    }
		});
		this.setOnMouseExited(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	setStyle(SHADOW_EFFECT);
		    }
		});
	}
	
	
	
	public void initDragAndDrop(SpecialTile SpecialTile) {
		setOnDragDetected(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	System.out.println(SpecialTile.getParentRack());
		    	if (GameManager.getGameMode().equals(GameMode.SINGLE_PUT_TILE) && !SpecialTile.getParentRack().isLocked() && SpecialTile.getParentRack().getOwner().isAbleToPutATile()) {
			    	Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
			        setStyle(SHADOW_EFFECT);
			        
			        ClipboardContent content = new ClipboardContent();
			        content.putImage(getImage());
			        content.putString(SpecialTile.getType().toString());
			        
			        dragboard.setContent(content);
			        event.consume();
		    	}
		    }


		});
		
		setOnDragDone(new EventHandler<DragEvent>() {
		    @Override
		    public void handle(DragEvent event) {
		    	if (event.getTransferMode() == TransferMode.MOVE) {
		    		SpecialTile.exitRack();
		    		SpecialTile.getParentRack().getRackFX().getChildren().remove(SpecialTile.getTileFX());
			    	GameManager.setGameMode(GameMode.WIND_TILE);
			    	GameManager.getActivePlayer().getPlayerFX().getExtraMoveButton().setDisable(true);
			    	GameManager.getActivePlayer().getPlayerFX().getBtnExchange().setDisable(true);
			    	GameManager.getActivePlayer().getPlayerFX().getBtnValidate().setDisable(true);
		    	}
		        event.consume();
		    }
		});
	}

}
