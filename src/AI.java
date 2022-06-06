import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * AI class
 * 
 * This class is used to have the computer play a move or set up a board
 *
 */
public class AI {
	// Constructor
	public AI() {
	}

	// Public Method
	/**
	 * Method to return AI's decision of where to fire of current round.
	 * 
	 * @param ammo: Input how many targets AI can choose for this round
	 * @param currentBoard: given board on which we want to find a target
	 * @return Set of target coordinates
	 */
	public Set<Coordinate> getFireCoordinates(Board currentBoard, int ammo) {
		// First create an abstract board for AI
		ArrayList<Integer>[][] possibleShips = getPossibilities(currentBoard);

		// Fields
		// targets: which going to be returned later
		// remaining: list of Coordinate of current undiscovered squares
		// damagedShips: list of Coordinate of current damagedShips
		Set<Coordinate> targets = new HashSet<>();
		List<Coordinate> remaining = new ArrayList<>();
		List<Coordinate> damagedShips = new ArrayList<>();

		// Scan current board
		for (int i = 0; i < currentBoard.size(); i++) {
			for (int j = 0; j < currentBoard.size(); j++) {
				Status status = currentBoard.getCoordinateStatus(new Coordinate(i, j));
				if (status != Status.SHIPHIT && status != Status.SHIPSUNK && status != Status.MISS) {
					remaining.add(new Coordinate(i, j));
				} else if (status == Status.SHIPHIT) {
					damagedShips.add(new Coordinate(i, j));
				}
			}
		}

		// If there are damaged ships, go find target around those
		if (!damagedShips.isEmpty()) {
			findTargetAroundDamagedShips(damagedShips, ammo, targets, possibleShips, currentBoard);
		}

		// If we still have ammo left, go find most likely targets
		if (targets.size() < ammo) {
			findTarget(remaining, ammo, targets, possibleShips, currentBoard);
		}

		return targets;
	}

	/**
	 * This method set up a board with given ship set randomly
	 * 
	 * @param board
	 * @param ships
	 */
	public void setUpBoard(Board board, Set<Ship> ships) {
		//for each ship, set a random direction 
		//then try to put it into random coordinate until success
		for (Ship ship : ships) {
			Random rand = new Random();
			int x;
			int y;
			do {
				ship.setDirection(Direction.values()[rand.nextInt(Direction.values().length)]);
				x = rand.nextInt(board.size());
				y = rand.nextInt(board.size());
			} while (!board.putShip(ship, new Coordinate(x, y)));
		}
	}

	/**
	 * helper method to find targets
	 * 
	 * @param remaining
	 * @param ammo
	 * @param targets
	 * @param possibleShips
	 * @param currentBoard
	 */
	private void findTarget(List<Coordinate> remaining, int ammo, Set<Coordinate> targets,
			ArrayList<Integer>[][] possibleShips, Board currentBoard) {
		// First we sort all possible squares by how many possible ships can fit that
		// square descending
		Collections.sort(remaining, new Comparator<Coordinate>() {
			@Override
			public int compare(Coordinate a, Coordinate b) {
				if (possibleShips[a.x][a.y].size() == possibleShips[b.x][b.y].size()) {
					return 0;
				}
				return (possibleShips[a.x][a.y].size() > possibleShips[b.x][b.y].size()) ? -1 : 1;
			}
		});

		// Then we randomly select from most possible ships' square as targets until
		// ammo used up
		while (targets.size() < ammo) {
			// from index of 0 to end are all have equally number of ships can fit
			int end = 0;
			while (end < remaining.size() && (end == 0 || possibleShips[remaining.get(end).x][remaining.get(end).y]
					.size() == possibleShips[remaining.get(end - 1).x][remaining.get(end - 1).y].size())) {
				end++;
			}

			// random select, add to targets, and remove from remaining
			Random rand = new Random();
			int index = rand.nextInt(end);
			boolean contains = false;
			for (Coordinate c : targets) {
				if (c.x == remaining.get(index).x && c.y == remaining.get(index).y) {
					contains = true;
				}
			}
			if (!contains) {
				targets.add(new Coordinate(remaining.get(index).x, remaining.get(index).y));
			}
			remaining.remove(index);
		}
	}

	/**
	 * helper method to find targets around damaged ships
	 * 
	 * @param damagedShips
	 * @param ammo
	 * @param targets
	 * @param possibleShips
	 * @param currentBoard
	 */
	private void findTargetAroundDamagedShips(List<Coordinate> damagedShips, int ammo, Set<Coordinate> targets,
			ArrayList<Integer>[][] possibleShips, Board currentBoard) {
		// A priority queue to sort highest score of coordinates around damaged ships
		PriorityQueue<Cell> pq = new PriorityQueue<>(16, new Comparator<Cell>() {
			@Override
			public int compare(Cell a, Cell b) {
				if (a.point == b.point) {
					return 0;
				}
				return (a.point > b.point) ? -1 : 1;
			}
		});

		// for each damaged Ships' square, find the highest likely target
		for (Coordinate coordinate : damagedShips) {
			int x = 0;
			int y = 0;
			int point = 0;
			for (Direction direction : Direction.values()) {
				int curPoint = check(direction, coordinate.x, coordinate.y, possibleShips, currentBoard);
				if (curPoint > point) {
					switch (direction) {
					case NORTH:
						x = -1;
						y = 0;
						break;
					case SOUTH:
						x = 1;
						y = 0;
						break;
					case WEST:
						x = 0;
						y = -1;
						break;
					case EAST:
						x = 0;
						y = 1;
						break;
					}
					point = curPoint;
				}
			}

			if (point > 0) {
				pq.offer(new Cell(coordinate.x + x, coordinate.y + y, point));
			}
		}

		while (pq.size() != 0 && targets.size() < ammo) {
			int x = pq.peek().x;
			int y = pq.poll().y;
			boolean contains = false;
			for (Coordinate c : targets) {
				if (c.x == x && c.y == y) {
					contains = true;
				}
			}
			if (!contains) {
				targets.add(new Coordinate(x, y));
			}
		}
	}

	/**
	 * helper method for findTargetAroundDamagedShips
	 * 
	 * @param direction
	 * @param i
	 * @param j
	 * @param possibleShips
	 * @param currentBoard
	 * @return
	 */
	private int check(Direction direction, int i, int j, ArrayList<Integer>[][] possibleShips, Board currentBoard) {
		int x = 0;
		int y = 0;
		switch (direction) {
		case NORTH:
			x = -1;
			y = 0;
			break;
		case SOUTH:
			x = 1;
			y = 0;
			break;
		case WEST:
			x = 0;
			y = -1;
			break;
		case EAST:
			x = 0;
			y = 1;
			break;
		}
		Set<Integer> damagedShipSize = new TreeSet<>();
		for (Ship ship : currentBoard.getDamagedShips()) {
			damagedShipSize.add(ship.getSize());
		}
		if (i + x >= 0 && i + x < possibleShips.length && j + y >= 0 && j + y < possibleShips.length
				&& possibleShips[i + x][j + y].size() != 0) {
			int point = 1;
			int move = 1;
			while (i - x * move >= 0 && i - x * move < possibleShips.length && j - y * move >= 0
					&& j - y * move < possibleShips.length) {
				if (currentBoard.getCoordinateStatus(new Coordinate(i - move * x, j - move * y)) != Status.SHIPHIT) {
					break;
				}
				point++;
				move++;
			}
			return point;
		}
		return 0;
	}

	// helper class
	private class Cell {
		int x;
		int y;
		int point;

		Cell(int x, int y, int point) {
			this.x = x;
			this.y = y;
			this.point = point;
		}
	}

	/**
	 * helper method to update the abstract board
	 * 
	 * @param currentBoard
	 * @return
	 */
	private ArrayList<Integer>[][] getPossibilities(Board currentBoard) {
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[][] possibleShips = (ArrayList<Integer>[][]) new ArrayList[currentBoard.size()][currentBoard
				.size()];

		int[][] left = new int[currentBoard.size()][currentBoard.size()];
		int[][] right = new int[currentBoard.size()][currentBoard.size()];
		int[][] up = new int[currentBoard.size()][currentBoard.size()];
		int[][] down = new int[currentBoard.size()][currentBoard.size()];

		for (int i = 0; i < currentBoard.size(); i++) {
			for (int j = 0; j < currentBoard.size(); j++) {
				up[i][j] = findConsecutive(currentBoard, i, j, -1, 0);
				down[i][j] = findConsecutive(currentBoard, i, j, 1, 0);
				left[i][j] = findConsecutive(currentBoard, i, j, 0, -1);
				right[i][j] = findConsecutive(currentBoard, i, j, 0, 1);
			}
		}

		Set<Ship> ships = currentBoard.getActiveShips();
		for (int i = 0; i < currentBoard.size(); i++) {
			for (int j = 0; j < currentBoard.size(); j++) {
				Status status = currentBoard.getCoordinateStatus(new Coordinate(i, j));
				possibleShips[i][j] = new ArrayList<>();
				if (status != Status.SHIPHIT && status != Status.SHIPSUNK && status != Status.MISS) {
					for (Ship ship : ships) {
						int size = ship.getSize();
						int curLeft = Math.min(size, left[i][j]);
						int curRight = Math.min(size, right[i][j]);
						int curUp = Math.min(size, up[i][j]);
						int curDown = Math.min(size, down[i][j]);
						int score = Math.max(0, curLeft + curRight - size) + Math.max(0, curUp + curDown - size);
						if (score > 0) {
							for (int counter = 0; counter < score; counter++) {
								possibleShips[i][j].add(size);
							}
						}
					}
				}
			}
		}
		return possibleShips;
	}

	private int findConsecutive(Board currentBoard, int row, int col, int x, int y) {
		int result = 0;
		while (row >= 0 && row < currentBoard.size() && col >= 0 && col < currentBoard.size()) {
			if (currentBoard.getCoordinateStatus(new Coordinate(row, col)) == Status.MISS
					|| currentBoard.getCoordinateStatus(new Coordinate(row, col)) == Status.SHIPSUNK) {
				break;
			}
			result++;
			row += x;
			col += y;
		}
		return result;
	}

}
