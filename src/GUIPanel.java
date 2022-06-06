import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * GUIPanel class
 * 
 * This GUI class responds to the menu actions.
 */

public class GUIPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private final int BOARD_WIDTH;
	private final int BOARD_HEIGHT;
	
	private GUIMainPage mainpage;
	private GUIGamePage gamepage;
	private String currentPage;
	
	private CardLayout card = new CardLayout(40, 30);

	public GUIPanel(Dimension d) {
		BOARD_WIDTH = d.width;
		BOARD_HEIGHT = d.height;
		
		setLayout(card);
		
		mainpage = new GUIMainPage(this);
		
		add(mainpage);
		currentPage = "MainPage";	
	}
	
	public void standardGameStart(BattleShip game) {
		currentPage = "GamePage";
		removeAll();
		gamepage = new GUIGamePage(game, "Standard");
		add(gamepage);
		revalidate();
		repaint();
	}
	
	public void restart() {
		currentPage = "MainPage";
		removeAll();
		mainpage = new GUIMainPage(this);
		add(mainpage);
		revalidate();
		repaint();
	}
	
	public void method1() {
		if (currentPage.equals("GamePage")) {
			gamepage.showShips();
		}
		revalidate();
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (currentPage.equals("MainPage")) {
			g.drawImage((new ImageIcon("src/resources/gui/startPageBackground.png")).getImage(), 
					0, 0, BOARD_WIDTH, BOARD_HEIGHT, this);
		} else {
			g.drawImage((new ImageIcon("src/resources/gui/gamePageBackground.png")).getImage(),
					0, 0, BOARD_WIDTH, BOARD_HEIGHT, this);
		}
	}

	public void mode1GameStart(BattleShipMode1 game) {
		currentPage = "GamePage";
		removeAll();
		gamepage = new GUIGamePage(game, "Mode1");
		add(gamepage);
		revalidate();
		repaint();
	}

	public void mode2GameStart(BattleShipMode2 game) {
		currentPage = "GamePage";
		removeAll();
		gamepage = new GUIGamePage(game, "Mode2");
		add(gamepage);
		revalidate();
		repaint();
	}

	public void mode3GameStart(BattleShipMode3 game) {
		currentPage = "GamePage";
		removeAll();
		gamepage = new GUIGamePage(game, "Mode3");
		add(gamepage);
		revalidate();
		repaint();
	}

	public void mode4GameStart(BattleShipMode4 game) {
		currentPage = "GamePage";
		removeAll();
		gamepage = new GUIGamePage(game, "Mode4");
		add(gamepage);
		revalidate();
		repaint();
	}
}
