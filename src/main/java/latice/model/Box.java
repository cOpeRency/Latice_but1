package latice.model;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import latice.vue.BoxFX;

public class Box implements Serializable{
	private BoxFX boxFX;
	private BoxType boxType;
	private Tile tile;
	private Position position;
	private GameBoard gameboard;
	
	public Box(BoxType boxType, GameBoard gameboard,Position position) {
		this.boxType = boxType;
		this.tile = Tile.NO;
		this.gameboard = gameboard;
		this.position = position;
		
	}

	public GameBoard getGameboard() {
		return gameboard;
	}
	
	public void setBoxImage() {
		this.boxFX = new BoxFX(this);
	}
	
	public BoxType getBoxType() {
		return boxType;
	}

	public BoxFX getBoxFX() {
		return boxFX;
	}
	
	
	public void setTile(Tile tile) {
		tile.setParentBox(this);
		this.tile = tile;
		System.out.println(getTileMatchType(getAdjacentBoxes())+" for player "+gameboard.getActivePlayer().getName());
		gameboard.getActivePlayer().addPoints(getTileMatchType( getAdjacentBoxes()).value()+gainPointBySunBox() );
	}
	
	public Tile getTile() {
		return this.tile;
	}

	public void removeTile(Tile tile) {
		this.tile = null;
	}
	
	public Integer gainPointBySunBox() {
		if (this.boxType==BoxType.SUN) {
			return 2;
		}
		return 0;
	}
	
	public MatchType getTileMatchType(List<Box> boxAdjacent) {
		Integer numberOfTile = 0;
		for (Box box : boxAdjacent) {
			if (box.getTile()!=Tile.NO) {
					numberOfTile += 1;
			}
		}
		
		switch (numberOfTile){
		case 2:
			return MatchType.DOUBLE;
		case 3:
			return MatchType.TREFOIL;
		case 4:
			return MatchType.LATICE;
		}
		return MatchType.SIMPLE;
	}
	
	public List<Box> getAdjacentBoxes(){
		List<Box> listBoxes = new ArrayList<>();
		
		if (position.row()>0) {
			listBoxes.add(this.gameboard.getBox(new Position(position.row()-1,position.column())));
		}
		if (position.row()<8) {
			listBoxes.add(this.gameboard.getBox(new Position(position.row()+1,position.column())));
		}
		
		if (position.column()>0) {
			listBoxes.add(this.gameboard.getBox(new Position(position.row(),position.column()-1)));
		}
		
		if (position.column()<8) {
			listBoxes.add(this.gameboard.getBox(new Position(position.row(),position.column()+1)));
		}
		
		return listBoxes;
	}
	
	public boolean checkValidity(Shape shape, Color color) {
		if ((position.column()==4 && position.row()==4)&&tile==null) {
			return true;
		}
		
		if (gameboard.getBox(new Position(4, 4)).tile==null) {
			return false;
		}

		if (this.tile!=Tile.NO) {

			return false;
		}
		
		Integer numberOfNullTiles = 0;
		List<Box> listBoxes = getAdjacentBoxes();
		for (Box box : listBoxes) {
			//System.out.println(box.getTile().toString());
			//System.out.println(box.getTile().getShape()+shape.toString());
			if (box.getTile()!=null) {
				if (box.getTile().getShape()!=shape && box.getTile().getColor()!=color) {				
					return false;
				}
			} else {
				numberOfNullTiles = numberOfNullTiles + 1;
			}
		}
		
		if (numberOfNullTiles==listBoxes.size()) {
			return false;
		}
		return true;
	}
}
