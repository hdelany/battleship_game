import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * This tests whether the "Ships on the Run" mode,
 * where the ships move & regenerate if they are not sunk in time,
 * is working
 * 
 * @author Hdelany
 *
 */

class BattleShipMode1Test {

	//testing to see that a ship will not move if you hit spots in succession
	@Test
	void stayInPlaceTest() {
		
		//start mode 1 game
		BattleShipMode1 game = new BattleShipMode1();
		Board b = game.playBoard;
		
		/** print the board
		//board in the form of Status enum 2D array
		Status[][] statusArr = b.getBoardStatus();
		for (int i = 0; i < statusArr.length; i++) {
			for (int j = 0; j < statusArr[i].length; j++) {
				System.out.printf("%1$13s", statusArr[i][j] + "\t");
			}
			System.out.println();
		}
		*/
		
		//locate active ships (+coordinates)		
		Map<Ship, Set<Coordinate>> shipsMap = b.getShipIndex();

		/**print active ship coordinates
		for (Ship key: shipsMap.keySet()) {
			System.out.printf("%1$13s", key.getName() + ":\t");
			Set<Coordinate> cSet = shipsMap.get(key);
			for (Coordinate c : cSet) {
				System.out.print(c.getX() + " " + c.getY() + ",\t");
			}
			System.out.println();
		}
		*/
			
		//hit the first ship in the set
		Ship firstShip = shipsMap.keySet().iterator().next();
		Set<Coordinate> firstShipCoordinateSet = shipsMap.get(firstShip);

		//hit all coordinates in a row
		for (Coordinate c : firstShipCoordinateSet) {
			game.fireTarget(c);
		}
		
		assertEquals(b.checkShipSunk(firstShip), true);
	}


	//testing to see that a ship will move if you take too long to hit a spot
	@Test
	void moveTest() {

		// start mode 1 game
		BattleShipMode1 game = new BattleShipMode1();
		Board b = game.playBoard;

		// locate active ships (+coordinates)
		Map<Ship, Set<Coordinate>> shipsMap = b.getShipIndex();

		// hit the first ship in the set
		Ship firstShip = shipsMap.keySet().iterator().next();
		Set<Coordinate> firstShipCoordinateSet = shipsMap.get(firstShip);

		// print first ship "before"coordinates
		System.out.print("Ship poistion before: ");
		for (Coordinate c : firstShipCoordinateSet) {
			System.out.printf(c.getX() + ", " + c.getY() + "\t");
		}

		// hit only one coordinate
		Coordinate lastCoordinate = null;
		for (Coordinate c : firstShipCoordinateSet) {
			lastCoordinate = c;
		}
		game.fireTarget(lastCoordinate);

		// hit water for a few turns
		for (int i = 0; i < b.size(); i++) {
			for (int j = 0; j < b.size(); j++) {
				Coordinate c = new Coordinate(i, j);
				if (b.getCoordinateStatus(c) == Status.WATER) {
					game.fireTarget(c);
				}
			}
		}

		// check that ship is not sunk
		assertEquals(b.checkShipSunk(firstShip), false);

		// print first ship "after"coordinates
		Map<Ship, Set<Coordinate>> updateShipsMap = b.getShipIndex();
		System.out.print("\nShip poistion after: ");
		for (Coordinate c : updateShipsMap.get(firstShip)) {
			System.out.printf(c.getX() + ", " + c.getY() + "\t");
		}

		// check that ship is moved
		assertNotEquals(updateShipsMap.get(firstShip), firstShipCoordinateSet);

	}
}
