import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

/**
 * This tests whether the "Rapid Fire" mode,
 * where the user selects multiple squares to hit on each turn,
 * the number of squares corresponding to the number of enemy ships left,
 * is working
 * 
 */

class BattleShipMode3Test {
	
	/**
	 * Helper method for tests
	 * Returns a set of hit coordinates for a complete user turn
	 * From first avaiable spaces in the gameboard
	 * @return
	 */
	public ArrayList<Coordinate> getHitCoordinatesAfterTurn(Board b, BattleShipMode3 game){
		ArrayList<Coordinate> avaiableCoordinates = new ArrayList<Coordinate>();
		ArrayList<Coordinate> hitCoordinateSet = new ArrayList<Coordinate>();
		
		// find unhit coordinates
		for (int i = 0; i < b.size(); i++) {
			for (int j = 0; j < b.size(); j++) {
				Coordinate c = new Coordinate(i, j);
				if (b.getCoordinateStatus(c) == Status.WATER 
						|| b.getCoordinateStatus(c) == Status.SHIPINTACT) {
					avaiableCoordinates.add(c);
				}
			}
		}
		// take turn and hit squares
		while (game.isSelecting() == true) {
			int x = avaiableCoordinates.get(0).getX();
			int y = avaiableCoordinates.get(0).getY();
			Coordinate c = new Coordinate(x, y);
			avaiableCoordinates.remove(0);
			game.fireTarget(c);
			hitCoordinateSet.add(c);
		}
		return hitCoordinateSet;
	}	
	
	//testing to see that all of the squares selected are hit
	@Test
	void allSquaresTest() {
		// start mode 3 game
		BattleShipMode3 game = new BattleShipMode3();
		Board b = game.playBoard;

		// have user take turn
		// get set of hit ships from turn
		ArrayList<Coordinate> hitCoordinateSet = getHitCoordinatesAfterTurn(b, game);
		
		// make sure all squares are "MISS" or "SHIPHIT"
		Status[][] statusArr = b.getBoardStatus();
		for (Coordinate c : hitCoordinateSet) {
			boolean outcome = false;
			if (statusArr[c.x][c.y] == Status.MISS 
					|| statusArr[c.x][c.y] == Status.SHIPHIT
					|| statusArr[c.x][c.y] == Status.SHIPSUNK) {
				outcome = true;
			}
			assertEquals(outcome, true);
		}
	}

	//testing to see that all of the squares not selected are not hit
	@Test
	void notSelectedTest() {
		// start mode 3 game
		BattleShipMode3 game = new BattleShipMode3();
		Board b = game.playBoard;

		// have user take turn
		// get set of hit ships from turn
		ArrayList<Coordinate> hitCoordinateSet = getHitCoordinatesAfterTurn(b, game);

		// make sure all other squares in board are not hit
		// find unhit coordinates
		ArrayList<Coordinate> unHitCoordinates = new ArrayList<Coordinate>();
		for (int i = 0; i < b.size(); i++) {
			for (int j = 0; j < b.size(); j++) {
				Coordinate c = new Coordinate(i, j);
				if (b.getCoordinateStatus(c) == Status.WATER 
						|| b.getCoordinateStatus(c) == Status.SHIPINTACT) {
					unHitCoordinates.add(c);
				}
			}
		}
		// check that hit square (fire method used) are not still intact
		for (int i = 0; i < unHitCoordinates.size(); i++) {
			assertEquals(hitCoordinateSet.contains(unHitCoordinates.get(i)), false);
		}
	}
	
	// testing to see that number of hits equals enemy ships
	@Test
	void correctNumSelections() {
		// start mode 3 game
		BattleShipMode3 game = new BattleShipMode3();
		Board b = game.playBoard;
		
		// have user take turn
		// get set of hit ships from turn
		int activeships = game.playBoard.getActiveShips().size();
		ArrayList<Coordinate> hitCoordinateSet = getHitCoordinatesAfterTurn(b, game);
		
		//print coordinates
		for (int i = 0; i<hitCoordinateSet.size(); i++) {
			int x = hitCoordinateSet.get(i).getX();
			int y = hitCoordinateSet.get(i).getY();		
			System.out.print(x + ", " + y + "\t");
		}
		//check number of items in set
		assertEquals(hitCoordinateSet.size(), activeships);
	}
}

