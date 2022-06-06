import java.util.Random;

/**
 * This class incorporoates the "Lucky Shot" mode,
 * where if the player misses several shots in a row,
 * the computer will pick a target that becomes 
 * gradually more likely to hit a target
 * 
 * Once a shot is missed, game will try another shot in background, 
 * if that backgorund shot hits a ship, it will replace your shot.
 * The more misses in a row, the more shots the game will try in the backgrou
 */

public class BattleShipMode4 extends BattleShip{
	private int playerMissed;
	private int aiMissed;
	
	public BattleShipMode4() {
		super();
		playerMissed = 0;
		aiMissed = 0;
	}
	
	@Override
	public void fireTarget(Coordinate coordinate) {
		Coordinate originalCoordinate = coordinate;
		//replacing coordinate with lucky shot if applicable
		if (playBoard.getCoordinateStatus(coordinate) == Status.WATER && playerMissed > 0) {
			for (int i = 0; i < playerMissed; i++) {
				coordinate = findNextAvailable(playBoard);
				if (playBoard.getCoordinateStatus(coordinate) != Status.WATER) {
					break;
				}
			}
		}
		
		if (playBoard.getCoordinateStatus(coordinate) == Status.WATER) {
			lastHits = "";
			lastSunk = "";
			playerMissed++;
			coordinate = originalCoordinate;
		} else {
			lastHits = playBoard.searchIndex(coordinate).getName();
			playerMissed = 0;
		}
		playBoard.fireTarget(coordinate);
		
		//updating text prompt
		if (playBoard.getCoordinateStatus(coordinate) == Status.SHIPSUNK) {
			lastSunk = lastHits;
		}
		
		//computer/AI's turn
		for (Coordinate c :ai.getFireCoordinates(aiBoard, 1)) {
			if (aiBoard.getCoordinateStatus(c) == Status.WATER && aiMissed > 0) {
				for (int i = 0; i < aiMissed; i++) {
					c = findNextAvailable(aiBoard);
					if (aiBoard.getCoordinateStatus(c) != Status.WATER) {
						break;
					}
				}
			}
			
			if (aiBoard.getCoordinateStatus(c) == Status.WATER) {
				lastHitsByAi = "";
				lastSunkByAi = "";
				aiMissed++;
			} else {
				lastHitsByAi = aiBoard.searchIndex(c).getName();
				aiMissed = 0;
			}
			aiBoard.fireTarget(c);
			if (aiBoard.getCoordinateStatus(c) == Status.SHIPSUNK) {
				lastSunkByAi = lastHitsByAi;
			}
		}
	}
	
	/**
	 * Method to find a random, available (not yet hit) coordinate in the board.
	 * @param board
	 * @return Coordinate
	 */
	private Coordinate findNextAvailable(Board board) {
		Random rand = new Random();
		Status status = null;
		int i;
		do {
			i = rand.nextInt(board.size() * board.size());
			status = board.getCoordinateStatus(new Coordinate(i / board.size(), i % board.size()));
		} while (status != Status.WATER && status != Status.SHIPINTACT);
		return new Coordinate(i / board.size(), i % board.size());
	}
}
