import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * GUIFrame class
 * 
 * This GUI class creates the window and menu bar.
 */

public class GUIFrame{
	private final JFrame frame;
	private final JMenuBar menuBar;
	private final JMenu menu;
	private final JMenuItem show_ship;
	private final JMenuItem restart;
	private final JMenuItem quit;
	private GUIPanel panel;
	
	public GUIFrame() {
		frame = new JFrame();
		menuBar = new JMenuBar();
		menu = new JMenu("Menu");
		show_ship = new JMenuItem("Unhide Ships");
		restart = new JMenuItem("Restart");
		quit = new JMenuItem("Quit");
		initUI();
	}
	
	private void initUI() {
		menu.add(show_ship);
		show_ship.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				panel.method1();
			}
		});
		menu.add(restart);
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				panel.restart();
			}
		});
		menu.add(quit);
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);
		
		panel = new GUIPanel(new Dimension(800, 600));
		frame.add(panel, BorderLayout.CENTER);
		frame.setSize(800, 600);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void run() {
		frame.setVisible(true);
	}
}
