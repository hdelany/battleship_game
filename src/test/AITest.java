import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * This is test for AI functions, 
 * mainly test the setUpBoard function and fireTarget function works

 *
 */
class AITest {
	Board board;

	@Test
	void testFire() {
		AI ai = new AI();
		int sum = 0;

		for (int round = 0; round < 10000; round++) {

			int size = 10;
			Ship ship1 = new Ship(2, "1");
			Ship ship2 = new Ship(2, "2");
			Ship ship3 = new Ship(2, "3");
			Ship ship4 = new Ship(3, "4");
			Ship ship5 = new Ship(3, "5");
			Ship ship6 = new Ship(3, "6");
			Ship ship7 = new Ship(4, "7");
			Ship ship8 = new Ship(5, "8");
			Set<Ship> ships = new HashSet<>();
			ships.add(ship1);
			ships.add(ship2);
			ships.add(ship3);
			ships.add(ship4);
			ships.add(ship5);
			ships.add(ship6);
			ships.add(ship7);
			ships.add(ship8);
			board = new Board(size, ships, true);
			int counter = 0;

			// Allow computer to fire twice per round to fully test the AI
			while (board.getActiveShips().size() != 0) {
				for (Coordinate c : ai.getFireCoordinates(board, 2)) {
					board.fireTarget(c);
				}
				counter++;
			}

			sum += counter;
		}

		double average = 1.0 * sum / 10000;
		// for 10000 times game, the target finish turn should be around 1/3 * square
		// number, is 33 here, allow delta 30% * 33 = 9.9
		assertEquals(board.size() * board.size() / 3, average, board.size() * board.size() / 3 * 0.3);
	}

	@Test
	void testSetUpBoard() {
		int[][] sum = new int[10][10];

		for (int round = 0; round < 1000000; round++) {

			int size = 10;
			Ship ship1 = new Ship(2, "1");
			Ship ship2 = new Ship(2, "2");
			Ship ship3 = new Ship(2, "3");
			Ship ship4 = new Ship(3, "4");
			Ship ship5 = new Ship(3, "5");
			Ship ship6 = new Ship(3, "6");
			Ship ship7 = new Ship(4, "7");
			Ship ship8 = new Ship(5, "8");
			Set<Ship> ships = new HashSet<>();
			ships.add(ship1);
			ships.add(ship2);
			ships.add(ship3);
			ships.add(ship4);
			ships.add(ship5);
			ships.add(ship6);
			ships.add(ship7);
			ships.add(ship8);
			board = new Board(size, ships, true);

			for (int i = 0; i < board.size(); i++) {
				for (int j = 0; j < board.size(); j++) {
					if (board.getCoordinateStatus(new Coordinate(i, j)) == Status.SHIPINTACT) {
						sum[i][j]++;
					}
				}
			}
		}

		System.out
				.println("For 1 million times AI setup board, the rate of specific square been occupied is following:");
		for (int i = 0; i < board.size(); i++) {
			System.out.println(Arrays.toString(sum[i]));
		}
	}
}
