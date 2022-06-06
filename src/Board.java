import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Board class
 * 
 * This class will be used to create the board
 * for both the player and computer
 * This class is called by the BoardGenerator class
 * 
 *
 * Constructor creates a board with parameters:
 *
 *		- int size: the number of squares on each side of board
 *  	- Set<Ship> ships: the set of ships the board will contain
 *		- boolean auto_arrange: true if board will arrange ship automatically
 *			true for computer generated board
 *			false of user placing ships on board
 *
 * Methods:
 *
 * 		- searchIndex(Coordinate coordinate)
 * 		- getDamagedShips()
 * 		- getActiveShips()
 * 		- Getters (for private instance variables)
 */
public class Board {
	
	//this tells us what is in each square of the board
	private final Status[][] board; 
	//shipIndex: map of all (active) ships with their coordinates
	private final Map<Ship, Set<Coordinate>> shipIndex;
	private final int size;

	/**
	 * Constructor 
	 * @param size: board will have size * size grids
	 * @param ships: board will contain given ships
	 * @param auto_arrange: true if board will arrange ship automatically
	 */
	public Board(int size, Set<Ship> ships, boolean auto_arrange) {
		this.size = size;
		board = new Status[size][size];
		shipIndex = new HashMap<>();   //initialize shipIndex

		//if we want an auto generated board
		if (auto_arrange == true) {
			// Create a local ai to set up board
			AI ai = new AI();
			ai.setUpBoard(this, ships);
		} else {
			//otherwise add ship to index with blank coordinates
			for(Ship ship : ships) {
				shipIndex.put(ship, new HashSet<>());
			}
		}

		// If a square does not have a ship in it, set it as Water
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j] == null) {
					board[i][j] = Status.WATER;
				}
			}
		}
	}

	/**
	 * This method returns a the "damaged" ships.
	 * Damaged ships have been hit, but have not been sunk.
	 * 
	 * @return Set<Ship>
	 */
	public Set<Ship> getDamagedShips() {
		Set<Ship> result = new HashSet<>();
		
		//for all squares in the board
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				//identify SHIPHIT spots
				if (board[i][j] == Status.SHIPHIT) {
					//get the ship from that coordinate (use helper method searchIndex)
					Ship ship = searchIndex(new Coordinate(i, j));
					if (ship != null) {
						result.add(ship);
					}
				}
			}
		}
		return result;
	}

	/**
	 * Method to find if given coordinate is occupied by an active ship
	 * (this does not find the sunken ships). 
	 * Returns the ship that is in the coordinate spot if a ship is in that space,
	 * returns null if there is no ship in that space.
	 * 
	 * @param coordinate, the coordinate we want to look at
	 * @returns the ship that is in the coordinate spot if a ship is in that space,
	 * returns null if there is no ship in that space.
	 */
	public Ship searchIndex(Coordinate coordinate) {
		//go through the entries(coordinates) in the ship index HashMap
		for (Map.Entry<Ship, Set<Coordinate>> entry : shipIndex.entrySet()) {
			for (Coordinate c : entry.getValue()) {
				//if we find the right coordinate
				if (c.equals(coordinate)) {
					//return the ship
					return entry.getKey();
				}
			}
		}
		return null;
	}

	/**
	 * This method returns all the ships still floating 
	 * (all ships that have not been sunk)
	 * 
	 * @return Set<Ship>
	 */
	public Set<Ship> getActiveShips() {
		Set<Ship> result = new HashSet<>();
		//go through the entries(coordinates) in the ship index HashMap
		for (Map.Entry<Ship, Set<Coordinate>> entry : shipIndex.entrySet()) {
			//add if ship is found in the index
			if (entry.getValue().size() != 0) {
				result.add(entry.getKey());
			}
		}
		return result;
	}

	/**
	 * Method to put a ship on the board.
	 * returns true if target coordinate(x,y) is empty, return false if it's occupied
	 * or out of bound
	 * 
	 * @param Ship ship
	 * @param Coordinate c
	 * @return
	 */
	public boolean putShip(Ship ship, Coordinate c) {
		int shipSize = ship.getSize();
		Direction shipDirection = ship.getDirection();
		int vertical_offset = 0;
		int horizental_offset = 0;
		// Define offset depends on direction
		switch (shipDirection) {
		case NORTH:
			vertical_offset = -1;
			horizental_offset = 0;
			break;
		case SOUTH:
			vertical_offset = 1;
			horizental_offset = 0;
			break;
		case EAST:
			vertical_offset = 0;
			horizental_offset = 1;
			break;
		case WEST:
			vertical_offset = 0;
			horizental_offset = -1;
			break;
		}
		// check valid
		for (int i = 0; i < shipSize; i++) {
			// check out of bound
			if (c.x + vertical_offset * i >= size || c.y + horizental_offset * i >= size
					|| c.x + vertical_offset * i < 0 || c.y + horizental_offset * i < 0) {
				return false;
			}
			// check not occupied
			if (board[c.x + vertical_offset * i][c.y + horizental_offset * i] != Status.WATER
					&& board[c.x + vertical_offset * i][c.y + horizental_offset * i] != null) {
				return false;
			}
		}

		// put ship on board
		shipIndex.put(ship, new HashSet<>()); //add ship to index with null coordinates
		Set<Coordinate> coordinateSet = shipIndex.get(ship);  
		for (int i = 0; i < shipSize; i++) {
			//add new coordinates to coordinate set
			coordinateSet.add(new Coordinate(c.x + vertical_offset * i, c.y + horizental_offset * i));

			//change spaces in board to SI: Section of a ship that hasn't been shot.
			board[c.x + vertical_offset * i][c.y + horizental_offset * i] = Status.SHIPINTACT;
		}
		return true;
	}
	
	/**
	 * return status of square of target coordinate(x,y)
	 * 
	 * @param c
	 * @return
	 */
	public Status getCoordinateStatus(Coordinate c) {
		return board[c.x][c.y];
	}

	/**
	 * Fire on target coordinate, assuming target always valid
	 * 
	 * @param target
	 * @return true if hit successfully
	 */
	public boolean fireTarget(Coordinate target) {
		boolean hit = false;
		Ship ship = null;

		// check if hit a ship
		if (board[target.x][target.y] == Status.SHIPINTACT) {
			hit = true;
		}

		// if not hit, set status as miss
		if (!hit) {
			board[target.x][target.y] = Status.MISS;
			return hit;
		} else {
		// if hit, get the ship
			board[target.x][target.y] = Status.SHIPHIT;
			ship = searchIndex(target);
		}

		// check if sunk
		if (checkShipSunk(ship)) {
			// clean the board and shipIndex
			Set<Coordinate> shipCoordinates = shipIndex.get(ship);
			for (Coordinate c : shipCoordinates) {
				board[c.x][c.y] = Status.SHIPSUNK;
			}
			shipCoordinates.clear();
		}

		return hit;
	}

	/**
	 * Helper method to check if target ship is sunk
	 * 
	 * @param ship
	 * @return true if sunk
	 */
	boolean checkShipSunk(Ship ship) {
		for (Coordinate c : shipIndex.get(ship)) {
			if (board[c.x][c.y] != Status.SHIPHIT) {
				return false;
			}
		}
		return true;
	}

	/**
	 * return the current board of status for display use
	 * 
	 * @return
	 */
	public Status[][] getBoardStatus() {
		return board;
	}
	
	public int size() {
		return size;
	}
	

	public Map<Ship, Set<Coordinate>> getShipIndex() {
		return shipIndex;
	}

	public void removeShip(Ship ship) {
		for (Coordinate c : shipIndex.get(ship)) {
			if (board[c.x][c.y] == Status.SHIPINTACT) {
				board[c.x][c.y] = Status.WATER;
			}
			if (board[c.x][c.y] == Status.SHIPHIT) {
				board[c.x][c.y] = Status.MISS; 
			}
		}
		shipIndex.remove(ship);
	}
}
