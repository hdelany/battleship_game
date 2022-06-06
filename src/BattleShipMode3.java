import java.util.HashSet;
import java.util.Set;

/**
 * This class incorporoates the "Rapid Fire" mode,
 * where the user selects multiple squares to hit on each turn,
 * the number of squares corresponding to the number of enemy ships left.
 *
 */

public class BattleShipMode3 extends BattleShip{

	private boolean selecting;

	public BattleShipMode3() {
		super();
		selecting = true;
	}

	/**
	 * Method to count the number of user seleted squares on the board
	 * Used in fireTarget()
	 * @return int
	 */
	int countSelected() {
		int countSelected = 0;
		Status[][] playBoardStatus = playBoard.getBoardStatus();
		for (int i = 0; i < playBoardStatus.length; i++) {
			for (int j = 0; j < playBoardStatus[0].length; j++) {
				if (playBoardStatus[i][j] == Status.SELECTED_WATER
						|| playBoardStatus[i][j] == Status.SELECTED_SHIPINTACT) {
					countSelected++;
				}
			}
		}
		return countSelected;
	}

	@Override
	public void fireTarget(Coordinate coordinate) {
		int countSelected = countSelected();
		Status[][] playBoardStatus = playBoard.getBoardStatus();

		//changing square status after selecting or deselecting
		if (playBoardStatus[coordinate.x][coordinate.y] == Status.SELECTED_WATER) {
			playBoardStatus[coordinate.x][coordinate.y] = Status.WATER;
			countSelected--;
		} else if (playBoardStatus[coordinate.x][coordinate.y] == Status.SELECTED_SHIPINTACT){
			playBoardStatus[coordinate.x][coordinate.y] = Status.SHIPINTACT;
			countSelected--;
		} else if (playBoardStatus[coordinate.x][coordinate.y] == Status.SHIPINTACT) {
			playBoardStatus[coordinate.x][coordinate.y] = Status.SELECTED_SHIPINTACT;
			countSelected++;
		} else {
			playBoardStatus[coordinate.x][coordinate.y] = Status.SELECTED_WATER;
			countSelected++;
		}

		if (countSelected < playBoard.getActiveShips().size()) {
			selecting = true;
			return;
		}

		Set<Coordinate> coordinates = new HashSet<>();
		for (int i = 0; i < playBoardStatus.length; i++) {
			for (int j = 0; j < playBoardStatus[0].length; j++) {
				if (playBoardStatus[i][j] == Status.SELECTED_SHIPINTACT
						|| playBoardStatus[i][j] == Status.SELECTED_WATER) {
					coordinates.add(new Coordinate(i, j));
					if (playBoardStatus[i][j] == Status.SELECTED_WATER) {
						playBoardStatus[i][j] = Status.WATER;
					} else {
						playBoardStatus[i][j] = Status.SHIPINTACT;
					}
				}
			}
		}

		Set<Ship> damagedShips = new HashSet<>();
		int sunk = 0;
		for (Coordinate c : coordinates) {
			if (playBoardStatus[c.x][c.y] == Status.SHIPINTACT) {
				damagedShips.add(playBoard.searchIndex(c));
			}
			playBoard.fireTarget(c); 
		}
		for (Ship ship : damagedShips) {
			if (!playBoard.getActiveShips().contains(ship)) {
				sunk++;
			}
		}
		lastHits = Integer.toString(damagedShips.size());
		lastSunk = Integer.toString(sunk);

		damagedShips = new HashSet<>();
		sunk = 0;
		for (Coordinate c :ai.getFireCoordinates(aiBoard, aiBoard.getActiveShips().size())) {
			if (aiBoard.getBoardStatus()[c.x][c.y] == Status.SHIPINTACT) {
				damagedShips.add(aiBoard.searchIndex(c));
			}
			aiBoard.fireTarget(c);
		}

		for (Ship ship : damagedShips) {
			if (!aiBoard.getActiveShips().contains(ship)) {
				sunk++;
			}
		}
		lastHitsByAi = Integer.toString(damagedShips.size());
		lastSunkByAi = Integer.toString(sunk);

		selecting = false;
	}

	/**
	 * Method to retireve text information about  
	 * the outcome of the turn
	 * @return String[]
	 */
	@Override
	public String[] getRoundInfo() {
		String[] info = new String[4];
		int index = 0;

		int countSelected = countSelected();
		if (selecting == true) {
			info[index++] = "You still have " + (playBoard.getActiveShips().size() - countSelected)
					+ " more targets.";
		} else {
			if (lastHits.length() == 0) {
				info[index++] = "You Missed!";
			} else {
				if (Integer.parseInt(lastHits) == 1) {
					info[index++] = "You hit " + lastHits + " ship!";
				}
				else {
					info[index++] = "You hit " + lastHits + " ships!";	
				}

			}
			if (lastSunk.length() != 0) {
				info[index++] = "You sank " + lastSunk + " ships!";
			}
			if (lastHitsByAi.length() == 0) {
				info[index++] = "Your Opponent Missed!";
			} else {
				if (Integer.parseInt(lastHitsByAi) == 1) {
					info[index++] = lastHitsByAi + " ship from your fleet was hit!";
				}
				else {
					info[index++] = lastHitsByAi + " ships from your fleet were hit!";	
				}
			}
			if (lastSunkByAi.length() != 0) {
				if (Integer.parseInt(lastSunkByAi) == 1) {
					info[index++] = lastSunkByAi + " ship from your fleet was sunk!";	
				}
				else {
					info[index++] = lastSunkByAi + " ships from your fleet were sunk!";
				}

			}
		}
		for (int i = index; i < 4; i++) {
			info[i] = "";
		}
		return info;
	}

	//getter
	public boolean isSelecting() {
		return selecting;
	}
}
