import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
/**
 * Test class for Board.java
 *
 */
class BoardTest {
	Board board;

	//this tests if board is generating properly
	@Test
	void testConstructorAutoGenerate() {
		for (int round = 0; round < 100; round++) {
			int size = 10;
			Ship ship1 = new Ship(1, "1");
			Ship ship2 = new Ship(2, "2");
			Ship ship3 = new Ship(3, "3");
			Ship ship4 = new Ship(4, "4");
			Ship ship5 = new Ship(5, "5");
			Set<Ship> ships = new HashSet<>();
			ships.add(ship1);
			ships.add(ship2);
			ships.add(ship3);
			ships.add(ship4);
			ships.add(ship5);
			board = new Board(size, ships, true);
			Status[][] status = board.getBoardStatus();
			int countWA = 0;
			int countSI = 0;
			for (int i = 0; i < board.size(); i++) {
				for (int j = 0; j < board.size(); j++) {
					if (status[i][j] == Status.SHIPINTACT) {
						countSI++;
					}
					if (status[i][j] == Status.WATER) {
						countWA++;
					}
				}
				System.out.println();
			}

			assertEquals(5, board.getActiveShips().size());
			assertEquals(countWA + countSI, size * size);
			assertEquals(15, countSI);
		}
	}
}
