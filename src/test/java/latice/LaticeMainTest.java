package latice;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import latice.model.Box;
import latice.model.GameBoard;
import latice.model.Player;
import latice.model.Position;
import latice.model.Stack;

class LaticeMainTest {

	@Test
	void stackJoueurDebiteApresCreationRack() {
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
}
