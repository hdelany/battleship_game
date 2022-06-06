import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * This class incorporoates the "Ships on the Run" mode,
 * where the ships move & regenerate if they are not sunk in time
 * 
 * @author Hdelany
 *
 */

public class BattleShipMode1 extends BattleShip{
	Map<Ship, Integer> damagedAIShip;
	Map<Ship, Integer> damagedPlayerShip;
	
	private static final int easyness = 2;
	
	public BattleShipMode1() {
		super();
		damagedAIShip = new HashMap<>();
		damagedPlayerShip = new HashMap<>();
	}
	
	@Override
	public void fireTarget(Coordinate coordinate) {
		Ship damagedShip = null;
		if (playBoard.getCoordinateStatus(coordinate) == Status.WATER) {
			lastHits = "";
			lastSunk = "";
		} else {
			//if hit, add to damaged ship
			damagedShip = playBoard.searchIndex(coordinate);
			lastHits = damagedShip.getName();
			if (!damagedAIShip.containsKey(damagedShip)) {
				damagedAIShip.put(damagedShip, damagedShip.getSize() + easyness);
			}
		}
		playBoard.fireTarget(coordinate);
		if (playBoard.getCoordinateStatus(coordinate) == Status.SHIPSUNK) {
			lastSunk = lastHits;
		}
		
		//all count minus 1
		Set<Map.Entry<Ship, Integer>> set = damagedAIShip.entrySet();
		Iterator<Map.Entry<Ship, Integer>> iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry<Ship, Integer> entry = iterator.next();
			int count = entry.getValue();
			//if count going to be 0, try flee
			if (count == 1) {
				if (shipFlee(playBoard, entry.getKey())) {
					if (damagedShip == entry.getKey()) {
						lastHits = "";
					}
				}
				//no matter success or not, remove it from damaged ship count
				iterator.remove();
			} else {
				entry.setValue(count - 1);
			}
		}
		
		
		for (Coordinate c :ai.getFireCoordinates(aiBoard, 1)) {
			if (aiBoard.getCoordinateStatus(c) == Status.WATER) {
				lastHitsByAi = "";
				lastSunkByAi = "";
			} else {
				damagedShip = aiBoard.searchIndex(c);
				lastHitsByAi = damagedShip.getName();
				if (!damagedPlayerShip.containsKey(damagedShip)) {
					damagedPlayerShip.put(damagedShip, damagedShip.getSize() + easyness);
				}
			}
			aiBoard.fireTarget(c);
			if (aiBoard.getCoordinateStatus(c) == Status.SHIPSUNK) {
				lastSunkByAi = lastHitsByAi;
			}
			
			set = damagedPlayerShip.entrySet();
			iterator = set.iterator();
			while (iterator.hasNext()) {
				Map.Entry<Ship, Integer> entry = iterator.next();
				int count = entry.getValue();
				if (count == 1) {
					if (shipFlee(aiBoard, entry.getKey())) {
						if (damagedShip == entry.getKey()) {
							lastHitsByAi = "";
						}
					}
					iterator.remove();
				} else {
					entry.setValue(count - 1);
				}
			}
		}
	}

	/**
	 * This method is to flee the ship 
	 * @param board
	 * @param ship
	 * @return boolean
	 */
	private boolean shipFlee(Board board, Ship ship) {
		Status[][] boardStatus = board.getBoardStatus();
		int size = ship.getSize();
		boolean hasSpace = false;
		//check if sunk
		if (!board.getDamagedShips().contains(ship)) {
			return false;
		}
		//check if has space
		for (int i = 0; i < boardStatus.length; i++) {
			for (int j = 0; j < boardStatus[0].length; j++) {
				if (boardStatus[i][j] == Status.WATER) {
					boolean cur = true;
					for (int x = 1; x < size; x++) {
						if (i + x == boardStatus.length || boardStatus[i+x][j] != Status.WATER) {
							cur = false;
							break;
						}
					}
					if (cur) {
						hasSpace = cur;
					}
					cur = true;
					for (int x = 1; x < size; x++) {
						if (j + x == boardStatus[0].length || boardStatus[i][j+x] != Status.WATER) {
							cur = false;
							break;
						}
					}
					if (cur) {
						hasSpace = cur;
					}
				}
			}
		}
		if (!hasSpace) {
			return false;
		}
		
		//remove ship
		board.removeShip(ship);
		
		//add ship
		Set<Ship> ships = new HashSet<>();
		ships.add(ship);
		ai.setUpBoard(board, ships);
		return true;
	}
}
