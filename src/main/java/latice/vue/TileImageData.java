package latice.vue;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import latice.model.boxes.BoxType;
import latice.model.system.GameManager;
import latice.model.system.GameMode;
import latice.model.tiles.BoardTile;
import latice.model.tiles.SpecialTile;
import latice.model.tiles.Tile;
import latice.vue.controller.DnDTileController;

public class TileImageData extends ImageView implements Serializable {
	private static final long serialVersionUID = 1L;
	private Tile tileSource;
	private boolean isLastTilePlayed;
	public static final String HOVER_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(200,200,0,0.8), 15, 0.6, 0, 0);";
	public static final String NOT_FIXED_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(200,0,200,0.8), 15, 0.6, 0, 0);";
	public static final String LAST_TILE_PLAYED_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(0,250,200,0.8), 15, 0.6, 0, 0);";
	public static final String SHADOW_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0.4, 0, 0);";
	
	
	public TileImageData(Tile tile) {
		this.tileSource = tile;
		this.isLastTilePlayed = true;
		if (tileSource.getClass().equals(BoardTile.class)) {
			
			this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent event) {
			    	if (GameManager.getGameMode().equals(GameMode.THUNDER_TILE) && !((BoardTile) tileSource).getParentBox().getBoxType().equals(BoxType.MOON)) {
			    		((BoardTile) tileSource).getParentBox().getBoxFX().callThunder();
		    			GameManager.setGameMode(GameMode.SINGLE_PUT_TILE);
				    	GameManager.getActivePlayer().getPlayerFX().getExtraMoveButton().setDisable(true);
				    	GameManager.getActivePlayer().getPlayerFX().getBtnExchange().setDisable(false);
				    	GameManager.getActivePlayer().getPlayerFX().getBtnValidate().setDisable(false);
			    	}
			    }
			});
			
			initDragAndDrop((BoardTile) tileSource);
			setTileEffects((BoardTile) tileSource);
			
		} else {
			initDragAndDrop((SpecialTile) tileSource);
			setSpecialTileEffects();
		}
		setTileImage();
	}

	
	public void initDragAndDrop(SpecialTile specialTile) {
		DnDTileController.initSpecialTileDnD(this, specialTile);
	}


	public void initDragAndDrop(BoardTile boardTile) {
		DnDTileController.initBoardTileDnD(this, boardTile);
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
			File fichier = new File("src/main/resources/themes/"+GameVisual.getTheme()+"/back.png");
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
			File fichier = new File("src/main/resources/themes/"+GameVisual.getTheme()+"/"+this.tileSource.getImagePath());
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

	public void deletePlayerPointsOnExit(BoardTile boardTile) {
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

}
