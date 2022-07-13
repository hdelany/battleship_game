# Classes - An Overview

#### Main
- The main method invokes the <tt><b>GUIFrame</b></tt> class, which starts the game.

#### GUIFrame
- The <tt><b>GUIFrame</b></tt> class creates the window and menu bar. 
- The menu bar has the options: 
    - Restart
    - Unhide ships
    - Quit. 
- This class instantiates a <tt><b>GUIPanel</b></tt>.

#### GUIPanel
- The <tt><b>GUIPanel</b></tt> class paints the panel for current page (main page or game page). 
- It also instantiates a <tt><b>GUIMainPage</b></tt> and defines methods to create, navigate between, and repaint pages.

#### GUIMainPage
- The <tt><b>GUIMainPage</b></tt> class draws the main (intro) page components. 
- This includes creating the buttons on the main page which allow the user to select the game mode.
- This class has action listener methods to capture the user game mode selection and call the method to instatiate the corresponding <tt><b>BattleShip/BattleshipMode#</b></tt> and <tt><b>GUIGamePage</b></tt> classes.

#### GUIGamePage
- The <tt><b>GUIGamePage</b></tt> class draws the game page (second page) components. 
-  This includes the user board, computer board, fleet status panels, and action result panel.
- This class uses the <tt><b>GUIGrids</b></tt> class to instantiate the boards.
- The <tt><b>GUIGamePage</b></tt> class has methods to update the text of the panels after each move based on the status of the game.
- The <tt><b>GUIGamePage</b></tt> class also has a method to receive mouse events/ capture the selection when a user clicks on a board square, and then call the <tt><b>Battleship's</b> public void fireTarget(Coordinate coordinate) </tt> method on the square.

#### GUIGrids
- The <tt><b>GUIGrids</b></tt> class is used to display the game board. Each square displays an ImageIcon based on status of the square. 
- Status options are defined in the <tt><b>Status</b></tt> class (see below).

#### Battleship
- The <tt><b>Battleship</b></tt> class defines the methods for a standard game. 
- This includes methods to create a set of <tt><b>Ships</b></tt>, create the user <tt><b>Board</b></tt> and computer <tt><b>Board</b></tt>, play a move/ fire at a target, and get the game status (to be used by the GUI).
- The <tt><b>Battleship</b> fireTarget</tt> method plays the user's move, and then immediately calls <tt><b>AI</b>'s fireTarget</tt> method to play the computer opponent's move.
- This class instantiates objects from the following classes: <tt><b> Board, Ship, AI</b></tt>.
- All <tt><b>BattleshipMode#</b></tt> classes extend the <tt><b>Battleship</b></tt> class.

#### BattleshipMode1
- This class extends the <tt><b>Battleship</b></tt> class to incorporate <i>"SHIPS ON THE RUN"</i> gameplay.
- In the <i>"SHIPS ON THE RUN"</i> mode, the ships move & regenerate if they are not sunk in time.
-  This class defines a <tt>shipFlee</tt> method, and overrides the <tt><b>Battleship</b> fireTarget</tt> method to incorporate the <tt>shipFlee</tt> method.

#### BattleshipMode2
- This class extends the <tt><b>Battleship</b></tt> class to incorporate <i>"BOMBS AWAY!"</i> gameplay.
-  In the <i>"BOMBS AWAY!"</i> mode, the ships can be sunk with one hit if a bomb is used. This class defines bomb methods, and overrides the <tt><b>Battleship</b> fireTarget</tt> method to incorporate them.

#### BattleshipMode3
- This class extends the <tt><b>Battleship</b></tt> class to incorporate <i>"RAPID FIRE"</i> gameplay.
- In the <i>"RAPID FIRE"</i> mode, the user selects multiple squares to hit on each turn, where the number of squares the user selects corresponds to the number of enemy ships left.
- This class defines methods for tracking multiple user selected squares, and overrides the <tt><b>Battleship</b> fireTarget</tt> and <tt>getRoundInfo</tt> methods.

#### BattleshipMode4
- This class extends the <tt><b>Battleship</b></tt> class to incorporate <i>"LUCKY SHOT"</i> gameplay.
- In the <i>"LUCKY SHOT"</i> mode,  if the player misses several shots in a row, the game will try another shot in background that becomes gradually more likely to hit a target. If that background shot hits a ship, it replaces the player's shot.
- This class overrides the <tt><b>Battleship</b> fireTarget</tt> method.
          
#### AI
- The <tt><b>AI</b></tt> class defines the methods required for the computer opponent to play a move/ fire at a target.
- This includes the <tt>fireTarget</tt> method as well as helper methods that allow the computer to find possible targets (priority is given by how many possible ships could be located in a particular square) and find targets around damaged ships.
     
#### Board
- The <tt><b>Board</b></tt> class is used to create the board for both the player and computer.
- <tt><b>Ship</b></tt>s are autoarranged on the board.
- This class also contains methods to get information about the status of the board (the state of the squares and <tt><b>Ship</b></tt>s on the board)

#### Coordinate
- This class defines a global usable coordinate. 
- The coordinate is immutable it is after created.
- This class is called by the <tt><b>Board</b></tt> class.
   
#### Status
- <tt>enum</tt> class defining the possible status of a square in the board.
- Possibilities include:
    - <tt>WATER</tt> 
    Square that not been hit, contains water
    - <tt>SHIPINTACT</tt>
    Square that not been hit, contains ship
    - <tt>SHIPHIT</tt>
    Section of a ship that has been hit, but the ship is still floating
    - <tt>SHIPSUNK</tt>
    Section of a sunk ship
    - <tt>MISS</tt>
    Square that has been hit, contains water
    - <tt>SELECTED_WATER</tt>
    User selected square, rapid fire game mode only)
    - <tt>SELECTED_SHIPINTACT</tt>
    User selected square, rapid fire game mode only) 

#### Ship
- The <tt><b>Ship</b></tt> class is used to create the ships.
- Each <tt><b>Ship</b></tt> has a size(int), name(string), and direction(Direction) where it points to.

#### Direction
- <tt>enum</tt> class defining the cardinal directions.

#### Test Classes
- The companion test classes are used to test for method functionality, state changes, and boundary & invalid input handling.