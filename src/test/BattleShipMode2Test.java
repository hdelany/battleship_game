import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * This tests whether the "Bombs away!" mode,
 * where the can be sunk with one hit if a bomb is used,
 * is working
 *
 */

class BattleShipMode2Test {

	//testing to see that a ship will be not be sunk in one hit without bomb
	@Test
	void noBombTest() {
		// start mode 2 game
		BattleShipMode2 game = new BattleShipMode2();
		Board b = game.playBoard;

		// locate active ships (+coordinates)
		Map<Ship, Set<Coordinate>> shipsMap = b.getShipIndex();

		// hit the first ship in the set (without bomb)
		Ship firstShip = shipsMap.keySet().iterator().next();
		Set<Coordinate> firstShipCoordinateSet = shipsMap.get(firstShip);
		Coordinate lastCoordinate = null;
		for (Coordinate c : firstShipCoordinateSet) {
			lastCoordinate = c;
		}
		game.setUseBomb(false);
		game.fireTarget(lastCoordinate);

		// check that ship is sunk
		assertEquals(b.checkShipSunk(firstShip), false);
	}

	//testing to see that a ship will be sunk with one hit if you use a bomb
	@Test
	void sunkTest() {
		// start mode 2 game
		BattleShipMode2 game = new BattleShipMode2();
		Board b = game.playBoard;

		// locate active ships (+coordinates)
		Map<Ship, Set<Coordinate>> shipsMap = b.getShipIndex();

		// hit the first ship in the set (with bomb
		Ship firstShip = shipsMap.keySet().iterator().next();
		Set<Coordinate> firstShipCoordinateSet = shipsMap.get(firstShip);
		Coordinate lastCoordinate = null;
		for (Coordinate c : firstShipCoordinateSet) {
			lastCoordinate = c;
		}
		game.setUseBomb(true);
		game.fireTarget(lastCoordinate);
		
		// check that ship is  sunk
		assertEquals(b.checkShipSunk(firstShip), true);
	}
	
	//testing to see that no ships are sunk if bomb is used and shot missed
	@Test
	void missTest() {
		// start mode 2 game
		BattleShipMode2 game = new BattleShipMode2();
		Board b = game.playBoard;

		// hit water with a bomb
		game.setUseBomb(true);
		boolean done = false;
		while (!done) {
			for (int i = 0; i < b.size(); i++) {
				for (int j = 0; j < b.size(); j++) {
					Coordinate c = new Coordinate(i, j);
					if (b.getCoordinateStatus(c) == Status.WATER) {
						game.fireTarget(c);
						done = true;
					}
				}
			}
		}	
		// check that no ships are sunk
		//board in the form of Status enum 2D array
		Status[][] statusArr = b.getBoardStatus();
		for (int i = 0; i < statusArr.length; i++) {
			for (int j = 0; j < statusArr[i].length; j++) {
				assertNotEquals(statusArr[i][j], Status.SHIPSUNK);
			}
		}
	}
	
	//testing to check that bomb count reduces after each use
	@Test
	void bombCountTest() {
		// start mode 2 game
		BattleShipMode2 game = new BattleShipMode2();
		int count = game.getBomb();

		// hit square with a bomb
		game.setUseBomb(true);
		Coordinate c = new Coordinate(0, 0);
		game.fireTarget(c);

		// check that bomb count is reduced
		assertEquals(game.getBomb(), count - 1);	
	}	
	
	//testing to check that bomb does not work if bomb count is used
	@Test
	void bombEmpty() {
		// start mode 2 game
		BattleShipMode2 game = new BattleShipMode2();
		Board b = game.playBoard;
		int count = game.getBomb();

		// hit water with a bomb until bomb count is 0
		while (count > 0) {
			game.setUseBomb(true);
			Coordinate waterCoordinate = null;
			//identify first water coordinate
			boolean done = false;
			
			while(!done) {
				for (int i = 0; i < b.size(); i++) {
					for (int j = 0; j < b.size(); j++) {
						Coordinate c = new Coordinate(i, j);
						if (b.getCoordinateStatus(c) == Status.WATER) {	
							waterCoordinate = c;
							done = true;
						}
					}	
				}
			}
			//hit one water
			game.fireTarget(waterCoordinate);
			count = game.getBomb();
		}
		
		//hit ship with bomb
		// locate active ships (+coordinates)
		Map<Ship, Set<Coordinate>> shipsMap = b.getShipIndex();

		// hit the first ship in the set (with bomb
		Ship firstShip = shipsMap.keySet().iterator().next();
		Set<Coordinate> firstShipCoordinateSet = shipsMap.get(firstShip);
		Coordinate lastCoordinate = null;
		for (Coordinate c : firstShipCoordinateSet) {
			lastCoordinate = c;
		}
		game.setUseBomb(true);
		game.fireTarget(lastCoordinate);

		// check that ship not sunk
		assertEquals(b.checkShipSunk(firstShip), false);
	}
}
