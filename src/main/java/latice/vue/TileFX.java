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
		initDragAndDrop();
		setTileEffects();
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

	public void initDragAndDrop() {
		setOnDragDetected(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	if (!tileSource.isLocked() && isLastTilePlayed && !tileSource.getParentRack().isLocked() && tileSource.getParentRack().getOwner().isAbleToPutATile()) {
			    	Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
			        setStyle(SHADOW_EFFECT);
			        
			        ClipboardContent content = new ClipboardContent();
			        content.putImage(getImage());
			        content.putString(tileSource.getShape().toString()+"_"+tileSource.getColor().toString());
			        
			        if (tileSource.getParentBox()!=null) {
			        	content.putString("BoxToRack");
			        }
			        
			        content.put(GameMain.TILE_DATA, tileSource);
			        
			        
			        
			        
			        if (tileSource.getParentBox()!=null) {
			        	deletePlayerPointsOnExit();
			    		
			        	//When we bought an extra move and then we move a tile from the gameboard before using it, we reset the extra move
			        	// Or when we take an extra tile which is already played, we reset the extra move too
			        	if (tileSource.getParentBox().getGameboard().getActivePlayer().isAbleToPutATile() || tileSource.getParentBox().getGameboard().getPlayingTiles().size()>1) {
			        		tileSource.getParentBox().getGameboard().getActivePlayer().addPoints(2);
			        		tileSource.getParentBox().getGameboard().getActivePlayer().getPlayerFX().setPointProperty();
			        	}
			        	
			        	tileSource.exitBox();
			    		tileSource.getParentBox().getBoxFX().getChildren().remove(tileSource.getTileFX());
			    		tileSource.getParentBox().getGameboard().removePlayingTile();
			    		
			    		if (tileSource.getParentBox().getGameboard().getPlayingTiles().size()==0) {
			    			tileSource.getParentBox().getGameboard().getActivePlayer().getPlayerFX().setExtraMoveButtonDisability(true);
			    		}
			    		
			    		tileSource.getParentBox().getGameboard().getActivePlayer().setAblilityToPutATile(true);
			    	}
			        dragboard.setContent(content);
			        event.consume();
		    	}
		    }


		});
		
		setOnDragDone(new EventHandler<DragEvent>() {
		    @Override
		    public void handle(DragEvent event) {
		    	if (event.getTransferMode() == TransferMode.MOVE) {
			    	if (tileSource.getParentRack()!=null) {
			    		tileSource.exitRack();
			    		tileSource.getParentRack().getRackFX().getChildren().remove(tileSource.getTileFX());
				        setStyle(NOT_FIXED_EFFECT);
			    	}
		    	} else  if (tileSource.getParentBox()!=null) {
		    		tileSource.resetPosition();
		    		tileSource.getParentBox().getGameboard().getActivePlayer().setAblilityToPutATile(false);
		    		if (tileSource.getParentBox().getGameboard().getActivePlayer().getPoints()>=2) {
		    			tileSource.getParentBox().getGameboard().getActivePlayer().getPlayerFX().setExtraMoveButtonDisability(false);
		    		}
		    		tileSource.getParentBox().getGameboard().getActivePlayer().getPlayerFX().setPointProperty();
		    		tileSource.getParentBox().getBoxFX().getChildren().add(tileSource.getTileFX());
		    		
		    		tileSource.getParentBox().getGameboard().addPlayingTile(tileSource);
		    		
		    		// If there is more than 1 playing tile on the gamebord, it mean that the extra move is reused, so we lose 2 points
		    		if (tileSource.getParentBox().getGameboard().getPlayingTiles().size()>1) {
		    			tileSource.getParentBox().getGameboard().getActivePlayer().addPoints(-2);
		    			tileSource.getParentBox().getGameboard().getActivePlayer().getPlayerFX().setPointProperty();
		    			tileSource.getParentBox().getGameboard().getActivePlayer().getPlayerFX().setExtraMoveButtonDisability(true);
		    		}
		    	}
		        event.consume();
		    }
		});
	}
	

	private void deletePlayerPointsOnExit() {
		Integer points = tileSource.getParentBox().getTileMatchType(tileSource.getParentBox().getAdjacentBoxes()).value()   +   tileSource.getParentBox().gainPointBySunBox();
		tileSource.getParentBox().getGameboard().getActivePlayer().addPoints(-points);
		tileSource.getParentBox().getGameboard().getActivePlayer().getPlayerFX().setPointProperty();
	}
	
	private void setTileEffects() {
		this.setStyle(SHADOW_EFFECT);
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	if (tileSource.getParentRack() != null) {
			        setStyle(HOVER_EFFECT);
		    	}
		    }
		});
		this.setOnMouseExited(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent t) {
		    	if (tileSource.isLocked()) {
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
	
}
