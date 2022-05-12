package latice;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import latice.model.Box;
import latice.model.BoxType;
import latice.model.Color;
import latice.model.GameBoard;
import latice.model.Player;
import latice.model.Position;
import latice.model.Shape;
import latice.model.Stack;
import latice.model.Tile;

class LaticeMainTest {
	
	
	@Test
	void testStackJoueurDebiteApresCreationRack() {
		Stack stack = new Stack();
		Player player1 = new Player("Albert");
		Player player2 = new Player("Bernard");
		
		stack.initialize(player1, player2);
		player1.createRack();
		player2.createRack();
		assertEquals(5, player1.getRack().rackLength());
		assertEquals(36-5, player1.getStack().stackLength());
		assertEquals(5, player2.getRack().rackLength());
		assertEquals(36-5, player2.getStack().stackLength());
	}
	
	
	@Test
	void testCheckBoxType() {
		GameBoard gameBoard = new GameBoard();
		
		gameBoard.generateBox();
		
		assertEquals(gameBoard.getBox(new Position(0, 0)).getBoxType(),BoxType.SUN);
		assertEquals(gameBoard.getBox(new Position(0, 1)).getBoxType(),BoxType.NORMAL);
		assertEquals(gameBoard.getBox(new Position(1, 1)).getBoxType(),BoxType.SUN);
		assertEquals(gameBoard.getBox(new Position(2, 2)).getBoxType(),BoxType.SUN);
		assertEquals(gameBoard.getBox(new Position(7, 7)).getBoxType(),BoxType.SUN);
		assertEquals(gameBoard.getBox(new Position(8, 8)).getBoxType(),BoxType.SUN);
		assertEquals(gameBoard.getBox(new Position(4, 3)).getBoxType(),BoxType.NORMAL);
		assertEquals(gameBoard.getBox(new Position(5, 5)).getBoxType(),BoxType.NORMAL);
		assertEquals(gameBoard.getBox(new Position(4, 4)).getBoxType(),BoxType.MOON);
		
	}
	
	@Test
	void testTileToString() {
		
		assertEquals(new Tile(Shape.SHAPE1,Color.COLOR1).toString(),Shape.SHAPE1.code()+" "+Color.COLOR1.code());
		assertEquals(new Tile(Shape.SHAPE2,Color.COLOR1).toString(),Shape.SHAPE2.code()+" "+Color.COLOR1.code());
		assertEquals(new Tile(Shape.SHAPE3,Color.COLOR1).toString(),Shape.SHAPE3.code()+" "+Color.COLOR1.code());
		assertEquals(new Tile(Shape.SHAPE4,Color.COLOR2).toString(),Shape.SHAPE4.code()+" "+Color.COLOR2.code());
		assertEquals(new Tile(Shape.SHAPE5,Color.COLOR4).toString(),Shape.SHAPE5.code()+" "+Color.COLOR4.code());
		assertEquals(new Tile(Shape.SHAPE6,Color.COLOR3).toString(),Shape.SHAPE6.code()+" "+Color.COLOR3.code());
		
	}

	@Test
	void testTileImagePath() {
		
		assertEquals(new Tile(Shape.SHAPE1,Color.COLOR1).getImagePath(),"src/main/resources/themes/classic/"+Shape.SHAPE1.code()+"_"+Color.COLOR1.code()+".png");
		
	}
	
	@Test
	void testPutTileAtPosition() {
		GameBoard gameBoard = new GameBoard();
		Position position = new Position(0, 4);
		
		gameBoard.generateBox();
		
		assertNull(gameBoard.getBox(position).getTile());
		
		gameBoard.getBox(position).setTile(new Tile(Shape.SHAPE1,Color.COLOR1));
		
		assertNotNull(gameBoard.getBox(position).getTile());
		assertEquals(gameBoard.getBox(position).getTile().getShape(),Shape.SHAPE1);
		assertEquals(gameBoard.getBox(position).getTile().getColor(),Color.COLOR1);

		assertEquals(gameBoard.getBox(position).getTile().getParentBox(),gameBoard.getBox(position));
		
	}
	
	@Test
	void testRemoveTileAtPosition() {
		GameBoard gameBoard = new GameBoard();
		Position position = new Position(0, 4);
		Tile tile = new Tile(Shape.SHAPE1,Color.COLOR1);
		
		gameBoard.generateBox();
		gameBoard.getBox(position).setTile(tile);
		
		assertNotNull(gameBoard.getBox(position).getTile());
		gameBoard.getBox(position).removeTile(tile);
		assertNull(gameBoard.getBox(position).getTile());
		
		
	}
	
	@Test
	void testGetAdjacentBoxes() {
		GameBoard gameBoard = new GameBoard();
		Position position = new Position(4, 4);
		List<Box> boxes = new ArrayList();
		
		gameBoard.generateBox();
		gameBoard.getBox(position).setTile(new Tile(Shape.SHAPE1,Color.COLOR1));
		boxes = gameBoard.getBox(position).getAdjacentBoxes();
		
		assertTrue(boxes.contains(gameBoard.getBox(new Position(4,5))));
		assertTrue(boxes.contains(gameBoard.getBox(new Position(4,3))));
		assertTrue(boxes.contains(gameBoard.getBox(new Position(3,4))));
		assertTrue(boxes.contains(gameBoard.getBox(new Position(5,4))));
		

		assertFalse(boxes.contains(gameBoard.getBox(new Position(4,4))));
		assertFalse(boxes.contains(gameBoard.getBox(new Position(4,6))));
		assertFalse(boxes.contains(gameBoard.getBox(new Position(1,1))));
	}
	
	
	@Test
	void testCanPlayAtPosition() {
		GameBoard gameBoard = new GameBoard();
		Position position = new Position(4, 4);
		List<Box> boxes = new ArrayList();
		
		gameBoard.generateBox();
		gameBoard.getBox(position).setTile(new Tile(Shape.SHAPE1,Color.COLOR1));
		boxes = gameBoard.getBox(position).getAdjacentBoxes();
		
		assertTrue(boxes.get(0).checkValidity(Shape.SHAPE1, Color.COLOR1));
		assertTrue(boxes.get(1).checkValidity(Shape.SHAPE1, Color.COLOR2));
		assertTrue(boxes.get(2).checkValidity(Shape.SHAPE1, Color.COLOR6));
		assertTrue(boxes.get(0).checkValidity(Shape.SHAPE4, Color.COLOR1));
		assertTrue(boxes.get(1).checkValidity(Shape.SHAPE3, Color.COLOR1));
		assertTrue(boxes.get(3).checkValidity(Shape.SHAPE5, Color.COLOR1));
		

		assertFalse(boxes.get(3).checkValidity(Shape.SHAPE5, Color.COLOR2));
		assertFalse(boxes.get(1).checkValidity(Shape.SHAPE2, Color.COLOR2));
		assertFalse(boxes.get(2).checkValidity(Shape.SHAPE3, Color.COLOR6));
		assertFalse(boxes.get(0).checkValidity(Shape.SHAPE6, Color.COLOR4));
		
	}
	
	
	@Test
	void testRackDebitéWhenPutTileToBox() {
		Stack stack = new Stack();
		Player player1 = new Player("Albert");
		Player player2 = new Player("Bernard");
		GameBoard gameBoard = new GameBoard();
		Position position = new Position(0, 4);
		
		
		stack.initialize(player1, player2);
		player1.createRack();
		player2.createRack();
		
		Tile tile = player1.getRack().getTiles().get(0);
		
		assertEquals(tile.getParentRack(),player1.getRack());
		
		gameBoard.generateBox();
		gameBoard.getBox(position).setTile(tile);
		tile.exitRack();
		
		assertEquals(player1.getRack().rackLength(),4);
		
		tile = player1.getRack().getTiles().get(0);
		gameBoard.generateBox();
		gameBoard.getBox(position).setTile(tile);
		tile.exitRack();
		
		assertEquals(player1.getRack().rackLength(),3);
		
		
		
	}
}
