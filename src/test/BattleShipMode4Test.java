import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * This class tests Mode4, the "Lucky Shot" mode,
 * where if the player misses several shots in a row,
 * the computer will pick a target that becomes 
 * gradually more likely to hit a target
 * 
 * Once a shot is missed, game will try another shot in background, 
 * if that backgorund shot hits a ship, it will replace your shot.
 * The more misses in a row, the more shots the game will try in the background
 */

class BattleShipMode4Test {

	
	// test that lucky shot is given/replaced with user shot only if the user misses
	@Test
	void luckyShotOnlyIfPlayerMisses() {
		// start mode 4 game
		BattleShipMode4 game = new BattleShipMode4();
		Board b = game.playBoard;
	
		// locate active ships (+coordinates)
		Map<Ship, Set<Coordinate>> shipsMap = b.getShipIndex();

		// hit the first ship in the set 
		Ship firstShip = shipsMap.keySet().iterator().next();
		Set<Coordinate> firstShipCoordinateSet = shipsMap.get(firstShip);
		Coordinate lastCoordinate = null;
		for (Coordinate c : firstShipCoordinateSet) {
			lastCoordinate = c;
		}
		game.fireTarget(lastCoordinate);
		boolean outcome = true;
		
		// iterate through board
		Status[][] statusArr = b.getBoardStatus();
		for (int i = 0; i < statusArr.length; i++) {
			for (int j = 0; j < statusArr[i].length; j++) {
				
				// check that no other coordinates were hit
				if (i != lastCoordinate.getX() && j != lastCoordinate.getY()) {
					if (statusArr[i][j] == Status.SHIPHIT || statusArr[i][j] == Status.MISS) {
						outcome = false;
					}
				}
			}
		}
		assertEquals(outcome, true);
	}
		
	// test that we only get one square per turn (even with lucky shot)
	@Test
	void onlyOneSquare() {
		// count how many turns to finish game
		int numberOfTurns = 0;
	
		// start mode 4 game
		BattleShipMode4 game = new BattleShipMode4();
		Board b = game.playBoard;
		AI ai = new AI();
		
		// keep having turns until no more active ships
		while (b.getActiveShips().size() != 0) {
			for (Coordinate c : ai.getFireCoordinates(b, 1)) {
				game.fireTarget(c);
			}
			numberOfTurns++;
		}	
		// count how many spots have been hit
		int hitSquares = 0;
		
		// iterate through board
		Status[][] statusArr = b.getBoardStatus();
		for (int i = 0; i < statusArr.length; i++) {
			for (int j = 0; j < statusArr[i].length; j++) {

				// count hit coordinates
				if (statusArr[i][j] == Status.SHIPHIT || statusArr[i][j] == Status.MISS
						|| statusArr[i][j] == Status.SHIPSUNK) {
					hitSquares++;
				}
			}
		}
		assertEquals(numberOfTurns, hitSquares);
	}

	// test that lucky shot gives a more favorable outcome than standard mode
	@Test
	void higherSuccessRate() {
		
		// play mode 4 for 1000 rounds
		int sum = 0;
		for (int i = 0; i < 1000; i++) {
			int numberOfTurns = 0;
			
			// start mode 4 game
			BattleShipMode4 game = new BattleShipMode4();
			Board b = game.playBoard;
			AI ai = new AI();
			
			// keep having turns until no more active ships
			while (b.getActiveShips().size() != 0) {
				for (Coordinate c : ai.getFireCoordinates(b, 1)) {
					game.fireTarget(c);
				}
				numberOfTurns++;
			}	
			sum += numberOfTurns;
		}
		double averageMode4 = (double)sum / 1000;
		System.out.println("Mode4 game average turns: " + averageMode4);
		
		// play stadard game for 1000 rounds
		int sum2 = 0;
		for (int i = 0; i < 1000; i++) {
			int numberOfTurns = 0;
			
			// start standard game
			BattleShip battleShip = new BattleShip();
			Board b = battleShip.playBoard;
			AI ai = battleShip.ai;
			
			//keep having turns until no more active ships
			while (b.getActiveShips().size() != 0) {
				for (Coordinate c : ai.getFireCoordinates(b, 1)) {
					b.fireTarget(c);
				}
				numberOfTurns++;
			}	
			sum2 += numberOfTurns;
		}
		double averageStandard = (double)sum2 / 1000;
		System.out.println("Standard game average turns: " + averageStandard);
			
		// check that mode 4 uses less turns to end on average
		boolean outcome = false;
		if (averageMode4 < averageStandard) {
			outcome = true;
		}
		assertEquals(outcome, true);	
	}
}
