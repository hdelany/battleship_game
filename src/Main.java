import java.awt.EventQueue;

/**
 * Main class
 * 
 * This method invokes the GUIFrame class, which starts the game
 * 
 * @author Holland Delany, Hanzhao Yu, Jorge Corrales
 *
 */
public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			GUIFrame gui = new GUIFrame();
			gui.run();
		});
	}

}
