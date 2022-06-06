/**
 * Coordinate class
 * 
 * This class defines a global usable coordinate, 
 * the coordinate is immutable it is after created
 * This class is called by the Board Class
 * 
 */
public class Coordinate {
	public final int x;
	public final int y;

	Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	// Override equals for compare
	@Override
	public boolean equals(Object another) {
		if (x == ((Coordinate)another).x && y == ((Coordinate)another).y) {
			return true;
		}
		return false;
	}

	//getters
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
