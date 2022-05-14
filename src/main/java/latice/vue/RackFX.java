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
		    		tile.exitBox();
		    		tile.exitBoxFX();
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
