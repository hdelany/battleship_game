/**
 * Ship class
 * 
 * This class will be used to create the ships
 * This class is called by the BoardGenerator class
 * 
 * Constructor creates a ship with parameters :
 * 
 * 		- int size: length of the ship 
 * 		- String name: unique identifier for each ship 
 * 		- Direction direction: where the ship is pointing at
 * 		If direction is not specified, a default value of "EAST" will be used
 * 
 *  Methods:
 *  	
 *  	- Getters (for private instance variables)
 *
 */
public class Ship {

	private final int size;
	private final String name;
	private Direction direction;

	/**
	 * Constructor with direction specified
	 * 
	 * @param size
	 * @param name
	 * @param direction
	 */
	public Ship(int size, String name, Direction direction) {
		this.size = size;
		this.name = name;
		this.direction = direction;
	}

	/**
	 * Constructor with no direction specified, will be set as default
	 * 
	 * @param size
	 * @param name
	 */
	public Ship(int size, String name) {
		direction = Direction.EAST;
		this.size = size;
		this.name = name;
	}

	// Getters and setters
	public int getSize() {
		return size;
	}

	public String getName() {
		return name;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
}
