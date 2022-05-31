package latice;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import latice.model.boxes.Box;
import latice.model.boxes.BoxType;
import latice.model.boxes.Position;
import latice.model.game.GameBoard;
import latice.model.players.Player;
import latice.model.players.Rack;
import latice.model.players.Stack;
import latice.model.system.GameManager;
import latice.model.system.GameMode;
import latice.model.system.MatchType;
import latice.model.tiles.BoardTile;
import latice.model.tiles.Color;
import latice.model.tiles.Shape;
import latice.model.tiles.Tile;

class LaticeMainTest {
	private Stack stack;
	private GameBoard gameBoard;
	private Player player1;
	private Player player2;
	
	private static final Integer doublePoint = 1;
	private static final Integer sunPoint = 2;
	private static final Integer trefoilPoint = 2;
	private static final Integer laticePoint = 4;
	
	
	@BeforeEach
	public void initEach(){
		GameManager.setGameMode(GameMode.SINGLE_PUT_TILE);
		this.stack = new Stack();
		this.player1 = new Player("Albert");
		this.player2 = new Player("Bernard");
		
		stack.initialize(player1, player2);
		player1.createRack();
		player2.createRack();
		
		this.gameBoard = new GameBoard();
		
		this.gameBoard.generateBox();

		GameManager.setActivePlayer(player1);
	}


	@Test
	void test_startTurn_invert_players_myTurn_attribute() {
		
		GameManager.startTurn(player2, player1);
		assertFalse(player1.isMyTurn());
		assertTrue(player2.isMyTurn());
	}
	
	
	@Test
	void testStackJoueurDebiteApresCreationRack() {
		assertEquals(5, this.player1.getRack().rackLength());
		assertEquals(42-5, this.player1.getStack().stackLength());
		assertEquals(5, player2.getRack().rackLength());
		assertEquals(42-5, player2.getStack().stackLength());
	}
	
	@Test
	void test_rack_joueur_filled_when_stack_less_than_5() {
		
		assertEquals(5, this.player1.getRack().rackLength());
		this.player1.getRack().removeTile(player1.getRack().getTiles().get(0));
		this.player1.getRack().removeTile(player1.getRack().getTiles().get(0));
		this.player1.getRack().removeTile(player1.getRack().getTiles().get(0));
		this.player1.getRack().removeTile(player1.getRack().getTiles().get(0));
		assertEquals(1, this.player1.getRack().rackLength());
		for (int i = 0; i < 36; i++) {
			this.player1.getStack().removeTile();
		}
		assertEquals(1, this.player1.getStack().stackLength());
		this.player1.getRack().fillRack(this.player1.getStack());
		assertEquals(0, this.player1.getStack().stackLength());
		assertEquals(2, this.player1.getRack().rackLength());
		
	}
	
	@Test
	void testCheckBoxType() {
		
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
		
		assertEquals(new BoardTile(Shape.SHAPE1,Color.COLOR1).toString(),Shape.SHAPE1.code()+" "+Color.COLOR1.code());
		assertEquals(new BoardTile(Shape.SHAPE2,Color.COLOR1).toString(),Shape.SHAPE2.code()+" "+Color.COLOR1.code());
		assertEquals(new BoardTile(Shape.SHAPE3,Color.COLOR1).toString(),Shape.SHAPE3.code()+" "+Color.COLOR1.code());
		assertEquals(new BoardTile(Shape.SHAPE4,Color.COLOR2).toString(),Shape.SHAPE4.code()+" "+Color.COLOR2.code());
		assertEquals(new BoardTile(Shape.SHAPE5,Color.COLOR4).toString(),Shape.SHAPE5.code()+" "+Color.COLOR4.code());
		assertEquals(new BoardTile(Shape.SHAPE6,Color.COLOR3).toString(),Shape.SHAPE6.code()+" "+Color.COLOR3.code());
		
	}

	@Test
	void testTileImagePath() {
		
		assertEquals(new BoardTile(Shape.SHAPE1,Color.COLOR1).getImagePath(),"src/main/resources/themes/pokemon/"+Shape.SHAPE1.code()+"_"+Color.COLOR1.code()+".png");
		
	}
	
	@Test
	void testPutTileAtPosition() {
		Position position = new Position(0, 4);
		
		assertNull(gameBoard.getBox(position).getTile());
		gameBoard.getBox(position).setTile(new BoardTile(Shape.SHAPE1,Color.COLOR1));
		
		assertNotNull(gameBoard.getBox(position).getTile());
		assertEquals(gameBoard.getBox(position).getTile().getShape(),Shape.SHAPE1);
		assertEquals(gameBoard.getBox(position).getTile().getColor(),Color.COLOR1);

		assertEquals(gameBoard.getBox(position).getTile().getParentBox(),gameBoard.getBox(position));
		
	}
	
	@Test
	void testRemoveTileAtPosition() {
		Position position = new Position(0, 4);
		BoardTile tile = new BoardTile(Shape.SHAPE1,Color.COLOR1);
		
		gameBoard.getBox(position).setTile(tile);
		
		assertNotNull(gameBoard.getBox(position).getTile());
		gameBoard.getBox(position).removeTile();
		assertNull(gameBoard.getBox(position).getTile());
		
		
	}
	
	@Test
	void testGetAdjacentBoxes() {
		Position position = new Position(4, 4);
		List<Box> boxes = new ArrayList();
		
		gameBoard.getBox(position).setTile(new BoardTile(Shape.SHAPE1,Color.COLOR1));
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
		Position position = new Position(4, 4);
		List<Box> boxes = new ArrayList();
		
		gameBoard.getBox(position).setTile(new BoardTile(Shape.SHAPE1,Color.COLOR1));
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
		Position position = new Position(0, 4);
		
		if (player1.getRack().getTiles().get(0).getClass().equals(BoardTile.class)) {
			BoardTile tile = (BoardTile) player1.getRack().getTiles().get(0);
			
			assertEquals(tile.getParentRack(),player1.getRack());
			
			gameBoard.getBox(position).setTile(tile);
			tile.exitRack();
			
			assertEquals(player1.getRack().rackLength(),4);
		}
		
	}
	
	@Test
	void testTileIsLockedWhenPutIt() {
		Position position = new Position(0, 4);

		if (player1.getRack().getTiles().get(0).getClass().equals(BoardTile.class)) {
			BoardTile tile = (BoardTile) player1.getRack().getTiles().get(0);
			
			gameBoard.getBox(position).setTile(tile);
			tile.exitRack();
			tile.setLocked(true);
			
			assertTrue(tile.isLocked());
		}
	}
	
	@Test
	void testPutTileConditions() {
		
		assertFalse(gameBoard.getBox(new Position(0, 4)).checkValidity(Shape.SHAPE1, Color.COLOR1));
		assertTrue(gameBoard.getBox(new Position(4, 4)).checkValidity(Shape.SHAPE1, Color.COLOR1));
		
		gameBoard.getBox(new Position(4, 4)).setTile(new BoardTile(Shape.SHAPE1,Color.COLOR1));
		
		assertFalse(gameBoard.getBox(new Position(0, 4)).checkValidity(Shape.SHAPE1, Color.COLOR1));
		assertTrue(gameBoard.getBox(new Position(3, 4)).checkValidity(Shape.SHAPE1, Color.COLOR1));
	}
	
	@Test
	void testCannotPutTileOnAnAnotherOne() {
		
		gameBoard.getBox(new Position(4, 4)).setTile(new BoardTile(Shape.SHAPE1,Color.COLOR1));
		gameBoard.getBox(new Position(4, 3)).setTile(new BoardTile(Shape.SHAPE1,Color.COLOR1));
		gameBoard.getBox(new Position(4, 3)).setTile(new BoardTile(Shape.SHAPE1,Color.COLOR1));
		
		assertFalse(gameBoard.getBox(new Position(4, 3)).checkValidity(Shape.SHAPE1, Color.COLOR1));
	}
	
	@Test
	void testChangeGameTurn() {
		player1.setMyTurn(true);
		player2.setMyTurn(false);
		
		assertTrue(player1.isMyTurn());
		assertFalse(player1.getRack().isLocked());
		assertFalse(player2.isMyTurn());
		assertTrue(player2.getRack().isLocked());
	}
	
	@Test
	void test_make_a_double_when_playing_near_to_two_tiles() {
		
		gameBoard.getBox(new Position(4, 4)).setTile(new BoardTile(Shape.SHAPE1,Color.COLOR1));
		gameBoard.getBox(new Position(4, 3)).setTile(new BoardTile(Shape.SHAPE2,Color.COLOR1));
		gameBoard.getBox(new Position(3, 3)).setTile(new BoardTile(Shape.SHAPE3,Color.COLOR1));
		

		assertTrue(gameBoard.getBox(new Position(3, 4)).checkValidity(Shape.SHAPE4, Color.COLOR1));
		
		List<Box> adjacentBoxes = gameBoard.getBox(new Position(3,4)).getAdjacentBoxes();
		MatchType matchType = gameBoard.getBox(new Position(3,4)).getTileMatchType(adjacentBoxes);
		
		assertEquals(matchType,MatchType.DOUBLE);
		
	}
	
	@Test
	void test_make_a_trefoil_when_playing_near_to_three_tiles() {
		
		gameBoard.getBox(new Position(4, 4)).setTile(new BoardTile(Shape.SHAPE1,Color.COLOR1));
		gameBoard.getBox(new Position(4, 3)).setTile(new BoardTile(Shape.SHAPE2,Color.COLOR1));
		gameBoard.getBox(new Position(3, 3)).setTile(new BoardTile(Shape.SHAPE3,Color.COLOR1));
		gameBoard.getBox(new Position(2, 3)).setTile(new BoardTile(Shape.SHAPE4,Color.COLOR1));
		gameBoard.getBox(new Position(2, 4)).setTile(new BoardTile(Shape.SHAPE5,Color.COLOR1));
		

		assertTrue(gameBoard.getBox(new Position(3, 4)).checkValidity(Shape.SHAPE6, Color.COLOR1));
		
		List<Box> adjacentBoxes = gameBoard.getBox(new Position(3,4)).getAdjacentBoxes();
		MatchType matchType = gameBoard.getBox(new Position(3,4)).getTileMatchType(adjacentBoxes);
		
		assertEquals(matchType,MatchType.TREFOIL);
		
	}
	
	@Test
	void test_make_a_latice_when_playing_near_to_four_tiles() {
		
		gameBoard.getBox(new Position(4, 4)).setTile(new BoardTile(Shape.SHAPE1,Color.COLOR1));
		gameBoard.getBox(new Position(4, 3)).setTile(new BoardTile(Shape.SHAPE2,Color.COLOR1));
		gameBoard.getBox(new Position(3, 3)).setTile(new BoardTile(Shape.SHAPE3,Color.COLOR1));
		gameBoard.getBox(new Position(2, 3)).setTile(new BoardTile(Shape.SHAPE4,Color.COLOR1));
		gameBoard.getBox(new Position(2, 4)).setTile(new BoardTile(Shape.SHAPE5,Color.COLOR1));
		gameBoard.getBox(new Position(4, 5)).setTile(new BoardTile(Shape.SHAPE2,Color.COLOR1));
		gameBoard.getBox(new Position(3, 5)).setTile(new BoardTile(Shape.SHAPE3,Color.COLOR1));
		gameBoard.getBox(new Position(2, 5)).setTile(new BoardTile(Shape.SHAPE4,Color.COLOR1));
		

		assertTrue(gameBoard.getBox(new Position(3, 4)).checkValidity(Shape.SHAPE6, Color.COLOR1));
		
		List<Box> adjacentBoxes = gameBoard.getBox(new Position(3,4)).getAdjacentBoxes();
		MatchType matchType = gameBoard.getBox(new Position(3,4)).getTileMatchType(adjacentBoxes);
		
		assertEquals(matchType,MatchType.LATICE);
		
	}
	
	@Test
	void test_gain_two_points_when_play_on_a_sun_box() {
		
		gameBoard.getBox(new Position(1, 1)).setTile(new BoardTile(Shape.SHAPE1,Color.COLOR1));
		
		assertEquals(gameBoard.getBox(new Position(1, 1)).getBoxType(),BoxType.SUN);
		assertEquals(GameManager.getActivePlayer().getPoints(),sunPoint);
		
	}
	
	@Test
	void test_gain_one_point_when_make_a_double() {
		
		gameBoard.getBox(new Position(4, 4)).setTile(new BoardTile(Shape.SHAPE1,Color.COLOR1));
		gameBoard.getBox(new Position(4, 3)).setTile(new BoardTile(Shape.SHAPE2,Color.COLOR1));
		gameBoard.getBox(new Position(3, 3)).setTile(new BoardTile(Shape.SHAPE3,Color.COLOR1));
		
		List<Box> adjacentBoxes = gameBoard.getBox(new Position(3,4)).getAdjacentBoxes();
		MatchType matchType = gameBoard.getBox(new Position(3,4)).getTileMatchType(adjacentBoxes);
		gameBoard.getBox(new Position(3, 4)).setTile(new BoardTile(Shape.SHAPE6,Color.COLOR1));
		
		
		assertEquals(matchType,MatchType.DOUBLE);
		assertEquals(GameManager.getActivePlayer().getPoints(),doublePoint);
		
	}
	
	@Test
	void test_gain_three_points_when_play_on_a_sun_box_while_doing_a_double() {
		
		gameBoard.getBox(new Position(0, 1)).setTile(new BoardTile(Shape.SHAPE1,Color.COLOR1));
		gameBoard.getBox(new Position(1, 0)).setTile(new BoardTile(Shape.SHAPE2,Color.COLOR1));
		List<Box> adjacentBoxes = gameBoard.getBox(new Position(0,0)).getAdjacentBoxes();
		MatchType matchType = gameBoard.getBox(new Position(0,0)).getTileMatchType(adjacentBoxes);
		gameBoard.getBox(new Position(0, 0)).setTile(new BoardTile(Shape.SHAPE2,Color.COLOR1));
		
		
		assertEquals(matchType,MatchType.DOUBLE);
		assertEquals(gameBoard.getBox(new Position(0, 0)).getBoxType(),BoxType.SUN);
		assertEquals(GameManager.getActivePlayer().getPoints(),sunPoint+doublePoint);
		
	}
	
	@Test
	void test_gain_two_point_when_make_a_trefoil() {
		
		gameBoard.getBox(new Position(4, 4)).setTile(new BoardTile(Shape.SHAPE1,Color.COLOR1));
		gameBoard.getBox(new Position(4, 3)).setTile(new BoardTile(Shape.SHAPE2,Color.COLOR1));
		gameBoard.getBox(new Position(3, 3)).setTile(new BoardTile(Shape.SHAPE3,Color.COLOR1));
		gameBoard.getBox(new Position(2, 3)).setTile(new BoardTile(Shape.SHAPE4,Color.COLOR1));
		gameBoard.getBox(new Position(2, 4)).setTile(new BoardTile(Shape.SHAPE5,Color.COLOR1));
		
		List<Box> adjacentBoxes = gameBoard.getBox(new Position(3,4)).getAdjacentBoxes();
		MatchType matchType = gameBoard.getBox(new Position(3,4)).getTileMatchType(adjacentBoxes);
		gameBoard.getBox(new Position(3, 4)).setTile(new BoardTile(Shape.SHAPE6,Color.COLOR1));
		
		
		assertEquals(matchType,MatchType.TREFOIL);
		assertEquals(GameManager.getActivePlayer().getPoints(),trefoilPoint);
		
	}
	
	@Test
	void test_gain_four_point_when_make_a_latice() {

		gameBoard.getBox(new Position(4, 4)).setTile(new BoardTile(Shape.SHAPE1,Color.COLOR1));
		gameBoard.getBox(new Position(4, 3)).setTile(new BoardTile(Shape.SHAPE2,Color.COLOR1));
		gameBoard.getBox(new Position(3, 3)).setTile(new BoardTile(Shape.SHAPE3,Color.COLOR1));
		gameBoard.getBox(new Position(2, 3)).setTile(new BoardTile(Shape.SHAPE4,Color.COLOR1));
		gameBoard.getBox(new Position(2, 4)).setTile(new BoardTile(Shape.SHAPE5,Color.COLOR1));
		gameBoard.getBox(new Position(4, 5)).setTile(new BoardTile(Shape.SHAPE2,Color.COLOR1));
		gameBoard.getBox(new Position(3, 5)).setTile(new BoardTile(Shape.SHAPE3,Color.COLOR1));
		
		List<Box> adjacentBoxes = gameBoard.getBox(new Position(3,4)).getAdjacentBoxes();
		MatchType matchType = gameBoard.getBox(new Position(3,4)).getTileMatchType(adjacentBoxes);

		gameBoard.getBox(new Position(3, 4)).setTile(new BoardTile(Shape.SHAPE6,Color.COLOR1));
		
		
		assertEquals(matchType,MatchType.LATICE);
		assertEquals(GameManager.getActivePlayer().getPoints(),laticePoint);
		
	}
	
	
	@Test
	void test_add_all_tiles_lefts_when_stack_cannot_fill_rack_completly() {
		
		assertEquals(5, this.player1.getRack().rackLength());
		this.player1.getRack().removeTile(player1.getRack().getTiles().get(0));
		this.player1.getRack().removeTile(player1.getRack().getTiles().get(0));
		this.player1.getRack().removeTile(player1.getRack().getTiles().get(0));
		this.player1.getRack().removeTile(player1.getRack().getTiles().get(0));
		assertEquals(1, this.player1.getRack().rackLength());
		for (int i = 0; i < 36; i++) {
			this.player1.getStack().removeTile();
		}
		assertEquals(1, this.player1.getStack().stackLength());
		this.player1.getRack().fillRack(this.player1.getStack());
		assertEquals(0, this.player1.getStack().stackLength());
		assertEquals(2, this.player1.getRack().rackLength());
		
	}
	
	
	@Test
	void test_rack_is_different_after_exchanging() {
		
		List<Tile> oldRack = new ArrayList<Tile>();
		oldRack.addAll(this.player1.getRack().getTiles());
		
		this.player1.getRack().exchange(player1.getStack());
		
		assertNotEquals(oldRack,this.player1.getRack().getTiles());
		
	}
}
