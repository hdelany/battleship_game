import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * GUIGrids class
 * 
 * This GUI is used to display the game board.
 */
public class GUIGrids extends JPanel{
	private static final long serialVersionUID = 1L;

	public final int CELL_SIZE;
	public final int N_ROWS = 10;
	public final int N_COLS = 10;
	private final int BOARD_WIDTH;
	private final int BOARD_HEIGHT;

	private Status[][] board;
	private boolean showShips;
	
	public GUIGrids(int size, Status[][] board) {
		BOARD_HEIGHT = size;
		BOARD_WIDTH = size;
		CELL_SIZE = size / 10 - 4;
		setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
		this.board = board;
		showShips = false;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage((new ImageIcon(getClass().getResource("frame/gamePageBackground.png"))).getImage(), 0, 0, CELL_SIZE * 10, CELL_SIZE * 10, this);
		
		for (int i = 0; i < N_ROWS; i++) {

			for (int j = 0; j < N_COLS; j++) {

				Status cell = board[i][j];

				Image img = null;
				switch (cell) {
				case SHIPSUNK:
					img = (new ImageIcon(getClass().getResource("grid/flag.png"))).getImage();
					break;
				case SHIPHIT:
					img = (new ImageIcon(getClass().getResource("grid/hit.png"))).getImage();
					break;
				case WATER:
					img = (new ImageIcon(getClass().getResource("grid/default.png"))).getImage();
					break;
				case MISS:
					img = (new ImageIcon(getClass().getResource("grid/empty.png"))).getImage();
					break;
				case SHIPINTACT:
					if (showShips) {
						img = (new ImageIcon(getClass().getResource("grid/ship.png"))).getImage();
					} else {
						img = (new ImageIcon(getClass().getResource("grid/default.png"))).getImage();
					}
					break;
				case SELECTED_WATER:
					img = (new ImageIcon(getClass().getResource("grid/selected.png"))).getImage();
					break;
				case SELECTED_SHIPINTACT:
					img = (new ImageIcon(getClass().getResource("grid/selected.png"))).getImage();
					break;
				}

				g.drawImage(img, (j * CELL_SIZE), (i * CELL_SIZE), CELL_SIZE, CELL_SIZE, this);
			}

		}
	}

	public void showShips() {
		showShips = true;
	}
}
