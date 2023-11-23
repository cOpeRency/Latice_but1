package latice.vue;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import latice.GameMain;
import latice.model.players.Rack;
import latice.model.system.GameManager;
import latice.model.tiles.BoardTile;
import latice.model.tiles.Tile;

public class RackVisualData extends HBox implements Serializable{
	private static final long serialVersionUID = 1L;
	private Rack rackSource;
	
	
	public RackVisualData(Rack rack) {
		this.rackSource = rack;
		setPadding(new Insets(10,10,10,10));
		setSpacing(20);
		setPrefWidth(410);
		initDragSystem();
		createCanPlayEffect(true);
	}

	public void createCanPlayEffect(boolean createEffect) {
		if (createEffect) {
			setOpacity(1);
		} else {
			setOpacity(0.35);
		}
	}
	
	public void setRack(List<Tile> list) {
		this.getChildren().clear();
		for (Tile tile : list) {
			tile.setTileImage();
			this.getChildren().add(tile.getTileFX());
		}
	}
	
	public void hideTiles(List<Tile> tiles) {
		for (Tile tile : tiles) {
			tile.getTileFX().hideTile();
		}
	}
	
	public void showTiles(List<Tile> list) {
		for (Tile tile : list) {
			tile.getTileFX().setTileImage();
		}
	}
	
	public void initDragSystem() {
		
		setOnDragOver(new EventHandler<DragEvent>() {
		    @Override
		    public void handle(DragEvent event) {
		    	Dragboard dragboard = event.getDragboard();
		    	if (dragboard.hasString() && !rackSource.isLocked()) {
		    		if (Objects.equals(dragboard.getString(), "BoxToRack")) {
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
		    		BoardTile tile = ((BoardTile)dragboard.getContent(GameMain.TILE_DATA));
		    		tile.setParentRack(rackSource);
		    		
		    		// If gameboard has at least one not locked tile, we delete the ability to play until he buy an extra move.
		    		if (GameVisual.getPlayingTiles().size() >= 1) {
		    			tile.getParentRack().getOwner().setAblilityToPutATile(false);
		    			createCanPlayEffect(false);
		    		} else {
		    			createCanPlayEffect(true);
		    		}
		    		
		    		
		    		if (GameManager.getActivePlayer().getPoints()>=2 && GameVisual.getPlayingTiles().size()>0) {
		    			rackSource.getOwner().getVisualData().setExtraMoveButtonDisability(false);
		    		}
		    		tile.exitBox();
		    		tile.getParentBox().getImageData().getChildren().remove(tile.getTileFX());
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
