import java.util.HashSet;
import java.util.Set;

/**
 * This class is the main algorithm for standard game, all other modes will
 * extends this class.
 */
public class BattleShip {

	protected Board aiBoard;
	protected Board playBoard;
	protected AI ai;

	protected String lastHits = "";
	protected String lastSunk = "";
	protected String lastHitsByAi = "";
	protected String lastSunkByAi = "";

	public BattleShip() {
		createTemplateBoard(false);
		createTemplateBoard(true);
		ai = new AI();
	}

	/**
	 * This method create a template board
	 * 
	 * @param isPlayBoard
	 */
	protected void createTemplateBoard(boolean isPlayBoard) {
		Ship boat1 = new Ship(2, "Patrol");
		Ship boat2 = new Ship(2, "Patrol");
		Ship boat3 = new Ship(2, "Patrol");
		Ship boat4 = new Ship(3, "Submarine");
		Ship boat5 = new Ship(3, "Cruiser");
		Ship boat6 = new Ship(3, "Cruiser");
		Ship boat7 = new Ship(4, "Battleship");
		Ship boat8 = new Ship(5, "Carrier");
		Set<Ship> ships = new HashSet<>();
		ships.add(boat1);
		ships.add(boat2);
		ships.add(boat3);
		ships.add(boat4);
		ships.add(boat5);
		ships.add(boat6);
		ships.add(boat7);
		ships.add(boat8);
		if (isPlayBoard) {
			playBoard = new Board(10, ships, true);
		} else {
			aiBoard = new Board(10, ships, true);
		}
	}

	/**
	 * This method return the board status for graphic use
	 * 
	 * @param isPlayBoard
	 * @return
	 */
	public Status[][] getBoard(boolean isPlayBoard) {
		if (isPlayBoard) {
			return playBoard.getBoardStatus();
		}
		return aiBoard.getBoardStatus();
	}

	/**
	 * This method is called every time when user select a coordinate and give back
	 * response
	 * 
	 * @param coordinate
	 */
	public void fireTarget(Coordinate coordinate) {
		if (playBoard.getCoordinateStatus(coordinate) == Status.WATER) {
			lastHits = "";
			lastSunk = "";
		} else {
			lastHits = playBoard.searchIndex(coordinate).getName();
		}
		playBoard.fireTarget(coordinate);
		if (playBoard.getCoordinateStatus(coordinate) == Status.SHIPSUNK) {
			lastSunk = lastHits;
		}

		for (Coordinate c : ai.getFireCoordinates(aiBoard, 1)) {
			if (aiBoard.getCoordinateStatus(c) == Status.WATER) {
				lastHitsByAi = "";
				lastSunkByAi = "";
			} else {
				lastHitsByAi = aiBoard.searchIndex(c).getName();
			}
			aiBoard.fireTarget(c);
			if (aiBoard.getCoordinateStatus(c) == Status.SHIPSUNK) {
				lastSunkByAi = lastHitsByAi;
			}
		}
	}

	// Methods for showing info of current game status

	public Set<Ship> getTargetInfo() {
		return playBoard.getActiveShips();
	}

	public Set<Ship> getFleetInfo() {
		return aiBoard.getActiveShips();
	}

	public String[] getRoundInfo() {
		String[] info = new String[4];
		int index = 0;
		if (lastHits.length() == 0) {
			info[index++] = "You Missed!";
		} else {
			info[index++] = "You hit a " + lastHits + "!";
		}
		if (lastSunk.length() != 0) {
			info[index++] = "You sink a " + lastSunk + "!";
		}
		if (lastHitsByAi.length() == 0) {
			info[index++] = "Your Opponent Missed!";
		} else {
			info[index++] = "Your " + lastHitsByAi + " was hit!";
		}
		if (lastSunkByAi.length() != 0) {
			info[index++] = "Your " + lastSunkByAi + " was sunk!";
		}
		for (int i = index; i < 4; i++) {
			info[i] = "";
		}
		return info;
	}
}
