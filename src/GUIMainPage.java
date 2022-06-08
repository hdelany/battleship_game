import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * GUIMainPage class
 * 
 * This GUI class creates the main (intro) page. 
 * This class creates the buttons on the main page.
 */

public class GUIMainPage extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private JButton standard;
	private JButton mode1;
	private JButton mode2;
	private JButton mode3;
	private JButton mode4;
	private JLabel title;
	
	public GUIMainPage(GUIPanel panel) {
		setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//adding BATTLESHIP title/logo
		title = new JLabel();
		title.setIcon(new ImageIcon(getClass().getResource("frame/startPageTitle.png")));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(title);
		
		//adding buttons and Tool Tips
		standard = addButton("STANDARD GAME", Color.BLACK);
		standard.setToolTipText("Start a standard game");
		mode1 = addButton("SHIPS ON THE RUN", new Color(255, 30, 0));
		mode1.setToolTipText("If you don't sink a ship in time, it will regenerate and move!");
		mode2 = addButton("BOMBS AWAY!", new Color(255, 30, 0));
		mode2.setToolTipText("Use bombs to sink an entire ship with one hit!");
		mode3 = addButton("RAPID FIRE", new Color(255, 30, 0));
		mode3.setToolTipText("The number of hits per turn corresponds to the number of enemy ships in play");
		mode4 = addButton("LUCKY SHOT", new Color(255, 30, 0));
		mode4.setToolTipText("Computer assist will help you if you miss multiple times in a row!");
		
		standard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				BattleShip game = new BattleShip();
				panel.standardGameStart(game);
			}
		});
		
		mode1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				BattleShipMode1 game = new BattleShipMode1();
				panel.mode1GameStart(game);
			}
		});
		
		mode2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				BattleShipMode2 game = new BattleShipMode2();
				panel.mode2GameStart(game);
			}
		});
		
		mode3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				BattleShipMode3 game = new BattleShipMode3();
				panel.mode3GameStart(game);
			}
		});
		
		mode4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				BattleShipMode4 game = new BattleShipMode4();
				panel.mode4GameStart(game);
			}
		});
	}
	
	//icon for button
	ImageIcon icon = new ImageIcon(getClass().getResource("frame/startPageButton.png"));
	
	//characteristics for the buttons
	private JButton addButton(String name, Color c) {
		JButton button = new JButton(icon);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setFont(new Font("IMPACT", Font.ITALIC, 25));
		button.setForeground(c);	
		button.setBorderPainted(false);
		button.setText(name);
		button.setHorizontalTextPosition(JButton.CENTER);
		button.setVerticalTextPosition(JButton.CENTER);
		button.setContentAreaFilled(false);
		button.setOpaque(false);
		addSpace();
		add(button);
		return button;
	}
	
	private void addSpace() {
		add(Box.createRigidArea(new Dimension(5, 20)));
	}
}
