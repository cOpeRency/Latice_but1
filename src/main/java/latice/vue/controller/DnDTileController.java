package latice.vue.controller;

import javafx.event.EventHandler;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import latice.GameMain;
import latice.model.boxes.Position;
import latice.model.players.Player;
import latice.model.system.GameManager;
import latice.model.system.GameMode;
import latice.model.tiles.BoardTile;
import latice.model.tiles.SpecialTile;
import latice.model.tiles.TypeOfSpecialTile;
import latice.vue.GameVisual;
import latice.vue.TileImageData;

public abstract class DnDTileController {

	
	public static void initBoardTileDnD(TileImageData tile, BoardTile boardTile) {
		tile.setOnDragDetected(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	if (GameManager.getGameMode().equals(GameMode.PUT_SINGLE_TILE) && !boardTile.isLocked() && tile.isLastTilePlayed() && !boardTile.getParentRack().isLocked() && boardTile.getParentRack().getOwner().isAbleToPutATile()) {
			    	Dragboard dragboard = tile.startDragAndDrop(TransferMode.MOVE);
			    	tile.setStyle(TileImageData.SHADOW_EFFECT);
			        
			        ClipboardContent content = new ClipboardContent();
			        content.putImage(tile.getImage());
			        content.putString(boardTile.getShape().toString()+"_"+boardTile.getColor().toString());
			        
			        if (boardTile.getParentBox()!=null) {
			        	content.putString("BoxToRack");
			        }
			        content.put(GameMain.TILE_DATA, boardTile);
			        
			        if (boardTile.getParentBox()!=null) {
			        	tile.deletePlayerPointsOnExit(boardTile);
			    		
			        	//When we bought an extra move and then we move a tile from the gameboard before using it, we reset the extra move
			        	// Or when we take an extra tile which is already played, we reset the extra move too
			        	if (GameVisual.getPlayingTiles().size()>1) {
			        		GameManager.getActivePlayer().addPoints(Player.EXTRA_MOVE_COST);
			        		GameManager.getActivePlayer().getVisualData().setPointProperty();
			        	}
			        	
			        	if (GameManager.getActivePlayer().isAbleToPutATile()) {
			        		GameManager.getActivePlayer().addPoints(Player.EXTRA_MOVE_COST);
			        		GameManager.getActivePlayer().getVisualData().setPointProperty();
			        	}
			        	
			        	boardTile.exitBox();
			        	boardTile.getParentBox().getImageData().getChildren().remove(boardTile.getTileFX());
			        	GameVisual.removePlayingTile();
			    		
			    		GameManager.getActivePlayer().getVisualData().enableExchangeButton();
			    		
			    		if (GameVisual.getPlayingTiles().isEmpty()) {
			    			GameManager.getActivePlayer().getVisualData().setExtraMoveButtonDisability(true);
			    		}
			    		
			    		GameManager.getActivePlayer().setAblilityToPutATile(true);
			    		GameManager.getActivePlayer().getRack().getVisualData().createCanPlayEffect(true);
			    	}
			        dragboard.setContent(content);
			        event.consume();
		    	
		    	} else if (GameManager.getGameMode().equals(GameMode.WIND_TILE) && boardTile.getParentBox()!=null && !boardTile.getParentBox().getPosition().equals(new Position(4,4))) {
		        	
		    		Dragboard dragboard = tile.startDragAndDrop(TransferMode.MOVE);
		    		tile.setStyle(TileImageData.SHADOW_EFFECT);
			        
			        ClipboardContent content = new ClipboardContent();
			        content.putImage(tile.getImage());
			        content.putString(boardTile.getShape().toString()+"_"+boardTile.getColor().toString());
			        
			        content.put(GameMain.TILE_DATA, boardTile);
			        

			        dragboard.setContent(content);
			        
		        	boardTile.exitBox();
		        	boardTile.getParentBox().getImageData().getChildren().remove(boardTile.getTileFX());
		        	
			        event.consume();
		    	}
		    }


		});
		
		tile.setOnDragDone(new EventHandler<DragEvent>() {
		    @Override
		    public void handle(DragEvent event) {
		    	if (event.getTransferMode() == TransferMode.MOVE) {
			    	if (boardTile.getParentRack()!=null) {
			    		boardTile.exitRack();
			    		boardTile.getParentRack().getVisualData().getChildren().remove(boardTile.getTileFX());
			    		if (GameManager.getGameMode().equals(GameMode.PUT_SINGLE_TILE)) {
			    			tile.setStyle(TileImageData.NOT_FIXED_EFFECT);
			    		} else {
			    			GameManager.setGameMode(GameMode.PUT_SINGLE_TILE);
					    	GameManager.getActivePlayer().getVisualData().getExtraMoveButton().setDisable(true);
					    	GameManager.getActivePlayer().getVisualData().getBtnExchange().setDisable(false);
					    	GameManager.getActivePlayer().getVisualData().getBtnValidate().setDisable(false);
			    		}
			    	}
		    	} else  if (boardTile.getParentBox()!=null) {
		    		boardTile.resetPosition();
		    		boardTile.getParentBox().getImageData().getChildren().add(boardTile.getTileFX());
		    		if (GameManager.getGameMode().equals(GameMode.PUT_SINGLE_TILE)) {
		    			GameManager.getActivePlayer().setAblilityToPutATile(false);
		    			GameManager.getActivePlayer().getRack().getVisualData().createCanPlayEffect(false);
		    			if (GameManager.getActivePlayer().getPoints()>=2) {
		    				GameManager.getActivePlayer().getVisualData().setExtraMoveButtonDisability(false);
		    			}
		    			GameManager.getActivePlayer().getVisualData().setPointProperty();
		    			
		    			GameVisual.addPlayingTile(boardTile);
		    			GameManager.getActivePlayer().getVisualData().disableExchangeButton();
		    			
		    			// If there is more than 1 playing tile on the gamebord, it mean that the extra move is reused, so we lose 2 points
		    			if (GameVisual.getPlayingTiles().size()>1) {
		    				GameManager.getActivePlayer().addPoints(-2);
		    				GameManager.getActivePlayer().getVisualData().setPointProperty();
		    				GameManager.getActivePlayer().getVisualData().setExtraMoveButtonDisability(true);
		    			}
		    		}
		    	}
		        event.consume();
		    }
		});
	}
	
	public static void initSpecialTileDnD(TileImageData tile, SpecialTile specialTile) {
		tile.setOnDragDetected(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	if (GameManager.getGameMode().equals(GameMode.PUT_SINGLE_TILE) && GameManager.canUseSpecialTiles() && !specialTile.getParentRack().isLocked() && specialTile.getParentRack().getOwner().isAbleToPutATile()) {
			    	Dragboard dragboard = tile.startDragAndDrop(TransferMode.MOVE);
			    	tile.setStyle(TileImageData.SHADOW_EFFECT);
			        
			        ClipboardContent content = new ClipboardContent();
			        content.putImage(tile.getImage());
			        content.putString(specialTile.getType().toString());
			        
			        dragboard.setContent(content);
			        event.consume();
		    	}
		    }


		});
		
		tile.setOnDragDone(new EventHandler<DragEvent>() {
		    @Override
		    public void handle(DragEvent event) {
		    	if (event.getTransferMode() == TransferMode.MOVE) {
		    		specialTile.exitRack();
		    		specialTile.getParentRack().getVisualData().getChildren().remove(specialTile.getTileFX());
		    		if (specialTile.getType().equals(TypeOfSpecialTile.WIND)) {
		    			GameManager.setGameMode(GameMode.WIND_TILE);
		    		} else {
		    			GameManager.setGameMode(GameMode.THUNDER_TILE);
		    		}
			    	
			    	GameManager.getActivePlayer().getVisualData().getExtraMoveButton().setDisable(true);
			    	GameManager.getActivePlayer().getVisualData().getBtnExchange().setDisable(true);
			    	GameManager.getActivePlayer().getVisualData().getBtnValidate().setDisable(true);
		    	}
		        event.consume();
		    }
		});
	}
}
