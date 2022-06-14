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
		
		System.setProperty("sun.java2d.d3d", "False");
   		System.setProperty("sun.java2d.opengl", "True");
   		System.setProperty("sun.java2d.noddraw", "True");
 
   		//System.out.println("sun.java2d.noddraw: " + System.getProperty("sun.java2d.noddraw"));
        //System.out.println("sun.java2d.d3d: " + System.getProperty("sun.java2d.d3d"));
        //System.out.println("sun.java2d.opengl: " + System.getProperty("sun.java2d.opengl"));
  
        
		EventQueue.invokeLater(() -> {
			GUIFrame gui = new GUIFrame();
			gui.run();
		});
	}

}
