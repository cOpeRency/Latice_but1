package latice.vue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import latice.application.GameMain;
import latice.model.Color;
import latice.model.Rack;
import latice.model.Shape;
import latice.model.Tile;

public class RackFX extends HBox implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<Tile> tiles;
	private Rack rackSource;
	
	
	public RackFX(Rack rack) {
		this.tiles = new ArrayList<Tile>();
		this.rackSource = rack;
		setPadding(new Insets(10,10,10,10));
		setSpacing(20);
		setPrefWidth(410);
		initDragSystem();
	}
	
	public void setRack(List<Tile> tiles) {
		this.getChildren().clear();
		for (Tile tile : tiles) {
			tile.setTileImage();
			this.getChildren().add(tile.getTileFX());
		}
	}
	
	public void initDragSystem() {
		
		setOnDragOver(new EventHandler<DragEvent>() {
		    @Override
		    public void handle(DragEvent event) {
		    	Dragboard dragboard = event.getDragboard();
		    	if (dragboard.hasString() && !rackSource.isLocked()) {
		    		if (dragboard.getString()=="BoxToRack") {
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
		    		Tile tile = ((Tile)dragboard.getContent(GameMain.TILE_DATA));
		    		tile.setParentRack(rackSource);
		    		
		    		// If gameboard has at least one not locked tile, we delete the ability to play until he buy an extra move.
		    		if (tile.getParentBox().getGameboard().getPlayingTiles().size() >= 1) {
		    			tile.getParentRack().getOwner().setAblilityToPutATile(false);
		    		}
		    		
		    		
		    		if (tile.getParentBox().getGameboard().getActivePlayer().getPoints()>=2 && tile.getParentBox().getGameboard().getPlayingTiles().size()>0) {
		    			rackSource.getOwner().getPlayerFX().setExtraMoveButtonDisability(false);
		    			//tile.getParentBox().getGameboard().getActivePlayer().getPlayerFX().setExtraMoveButtonDisability(false);
		    		}
		    		tile.exitBox();
		    		tile.getParentBox().getBoxFX().getChildren().remove(tile.getTileFX());
	    			System.out.println(tile.getParentBox().getGameboard().getActivePlayer().isAbleToPutATile());
		    		tile.setParentBox(null);
					rackSource.addTile(tile);
					tile.setTileImage();
					getChildren().add(tile.getTileFX());
		    	}
		    	
		    	event.setDropCompleted(success);
		        event.consume();
		    }
		});
	}
	
	
	
}
