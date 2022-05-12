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
	void testPutTileAtPosition() {
		GameBoard gameBoard = new GameBoard();
		Position position = new Position(0, 4);
		
		gameBoard.generateBox();
		
		assertNull(gameBoard.getBox(position).getTile());
		
		gameBoard.getBox(position).setTile(new Tile(Shape.SHAPE1,Color.COLOR1));
		
		assertNotNull(gameBoard.getBox(position).getTile());
		assertEquals(gameBoard.getBox(position).getTile().getShape(),Shape.SHAPE1);
		assertEquals(gameBoard.getBox(position).getTile().getColor(),Color.COLOR1);
		
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
}
