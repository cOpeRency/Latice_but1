package latice.model.boxes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import latice.model.game.GameBoard;
import latice.model.system.GameManager;
import latice.model.system.GameMode;
import latice.model.system.MatchType;
import latice.model.tiles.BoardTile;
import latice.model.tiles.Color;
import latice.model.tiles.Shape;
import latice.vue.BoxImageData;

public class Box implements Serializable{
	private static final long serialVersionUID = 1L;
	private BoxImageData boxFX;
	private BoxType boxType;
	private BoardTile tile;
	private Position position;
	private GameBoard gameboard;
	public static final Position MOON_POSITION = new Position(4,4);
	
	public Box(BoxType boxType, GameBoard gameboard,Position position) {
		this.boxType = boxType;
		this.tile = BoardTile.NO;
		this.gameboard = gameboard;
		this.position = position;
		
	}

	public Position getPosition() {
		return position;
	}
	
	public void initImage() {
		this.boxFX = new BoxImageData(this);
	}
	
	public BoxType getType() {
		return boxType;
	}

	public BoxImageData getImageData() {
		return boxFX;
	}
	
	
	public void setTile(BoardTile tile) {
		tile.setParentBox(this);
		this.tile = tile;
		if (GameManager.getGameMode().equals(GameMode.PUT_SINGLE_TILE)){
			System.out.println(getTileMatchType(getAdjacentBoxes())+" for player "+GameManager.getActivePlayer().getName());
			GameManager.getActivePlayer().addPoints(getTileMatchType( getAdjacentBoxes()).value()+getPointsGainnedByType() );
		}
		

	}
	
	public BoardTile getTile() {
		return this.tile;
	}

	public void removeTile() {
		this.tile = null;
	}
	
	public Integer getPointsGainnedByType() {
		if (this.boxType==BoxType.SUN) {
			return GameManager.SUN_POINT;
		}
		return GameManager.NO_POINT;
	}
	
	public MatchType getTileMatchType(List<Box> boxAdjacent) {
		Integer numberOfTile = 0;
		for (Box box : boxAdjacent) {
			if (box.getTile()!=BoardTile.NO) {
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
		default:
			return MatchType.SIMPLE;
		}
		
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
		if ((position.column()==MOON_POSITION.column() && position.row()==MOON_POSITION.column())&&tile==BoardTile.NO) {
			return true;
		}
		
		if (gameboard.getBox(MOON_POSITION).getTile()==BoardTile.NO) {
			return false;
		}

		if (this.tile!=BoardTile.NO) {
			return false;
		}
		
		Integer numberOfNullTiles = 0;
		List<Box> listBoxes = getAdjacentBoxes();
		for (Box box : listBoxes) {
			if (box.getTile()!=null) {
				if (box.getTile().getShape()!=shape && box.getTile().getColor()!=color) {				
					return false;
				}
			} else {
				numberOfNullTiles = numberOfNullTiles + 1;
			}
		}
		
		if (numberOfNullTiles==listBoxes.size())
			return false;
		
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(position);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Box other = (Box) obj;
		return Objects.equals(position, other.position);
	}
	
	
}
