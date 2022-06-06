import javax.swing.JCheckBox;

/**
 * This class incorporoates the "Bombs away!" mode,
 * where the ships can be sunk with one hit if a bomb is used
 *
 */

public class BattleShipMode2 extends BattleShip{
	
	public static final int BOMBNUMBER = 3;
	
	private int bomb;
	private boolean useBomb;
	private JCheckBox checkBox;
	
	public BattleShipMode2() {
		super();
		bomb = BOMBNUMBER;
		useBomb = false;
		checkBox = null;
	}
	
	@Override
	public void fireTarget(Coordinate coordinate) {
		if (useBomb && bomb > 0) {
			bomb--;
		} else {
			useBomb = false;
		}
		if (playBoard.getCoordinateStatus(coordinate) == Status.WATER) {
			lastHits = "";
			lastSunk = "";
		} else {
			lastHits = playBoard.searchIndex(coordinate).getName();
		}
		playBoard.fireTarget(coordinate);
		if (playBoard.getCoordinateStatus(coordinate) == Status.SHIPHIT) {
			if (useBomb) {
				sinkTarget(playBoard.searchIndex(coordinate));
			}
		}
		if (checkBox != null) {
			checkBox.setSelected(false);
			if (bomb == 0) {
				checkBox.setEnabled(false);
			}
		}
		if (playBoard.getCoordinateStatus(coordinate) == Status.SHIPSUNK) {
			lastSunk = lastHits;
		}
		
		
		for (Coordinate c :ai.getFireCoordinates(aiBoard, 1)) {
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


	private void sinkTarget(Ship ship) {
		Status[][] board = playBoard.getBoardStatus();
		for (Coordinate c : playBoard.getShipIndex().get(ship)) {
			board[c.x][c.y] = Status.SHIPSUNK;
		}
		playBoard.getShipIndex().get(ship).clear();
	}

	public void useBomb(boolean useBomb, JCheckBox checkBox) {
		this.useBomb = useBomb;	
		this.checkBox = checkBox;
	}

	//getters and setters

	public void setUseBomb(boolean useBomb) {
		this.useBomb = useBomb;
	}
	
	public int getBomb() {
		return bomb;
	}
}
