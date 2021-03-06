/**
 * Status class
 * 
 * enum class for the content/status of a square in the board
 *
 */
public enum Status {
	WATER,		//Default
	SHIPINTACT, //Section of a ship that hasn't been shot
	SHIPHIT,	//Section of a ship that has been shot but the ship is still floating
	SHIPSUNK,	//Section of a sunk ship
	MISS, 		//Missed shot (hit water)
	SELECTED_WATER,	//Selected by user
	SELECTED_SHIPINTACT	//Selected by user
}
