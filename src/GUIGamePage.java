import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * GUIGamePage class
 * 
 * This GUI class creates the game page (second page), and all of its contents.
 */
public class GUIGamePage extends JPanel{
	private static final long serialVersionUID = 1L;
	
	GUIGrids userBoard;
	GUIGrids computerBoard;
	BattleShip game;
	JTextArea txtrFleetRemain;
	JTextArea txtrTargetRemain;
	JTextArea txtInformation;
	
	boolean inGame;
	String mode;
	
	public GUIGamePage(BattleShip game, String mode) {
		inGame = true;
		this.mode = mode;
		this.game = game;
		setOpaque(false);
		
		//adding computer board
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {720};
		gridBagLayout.rowHeights = new int[] {360, 180};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0};
		setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{360, 360};
		gbl_panel.rowHeights = new int[]{360};
		gbl_panel.columnWeights = new double[]{1.0, 1.0};
		gbl_panel.rowWeights = new double[]{1.0};
		panel.setLayout(gbl_panel);
		
		computerBoard = new GUIGrids(360, game.getBoard(true));
		computerBoard.addMouseListener(new BattleShipAdapter());
		GridBagConstraints gbc_computerBoard = new GridBagConstraints();
		gbc_computerBoard.fill = GridBagConstraints.BOTH;
		gbc_computerBoard.gridx = 0;
		gbc_computerBoard.gridy = 0;
		computerBoard.setOpaque(false);
		panel.add(computerBoard, gbc_computerBoard);
		
		JPanel panel_3 = new JPanel();
		panel_3.setOpaque(false);
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 0;
		panel.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{360};
		gbl_panel_3.rowHeights = new int[]{315, 45};
		gbl_panel_3.columnWeights = new double[]{1.0};
		gbl_panel_3.rowWeights = new double[]{1.0, 1.0};
		panel_3.setLayout(gbl_panel_3);
		
		JPanel panel_6 = new JPanel();
		panel_6.setOpaque(false);
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.fill = GridBagConstraints.BOTH;
		gbc_panel_6.gridx = 0;
		gbc_panel_6.gridy = 0;
		panel_3.add(panel_6, gbc_panel_6);
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.X_AXIS));
			
		//left text box with initial text
		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);
		panel_6.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		txtrTargetRemain = new JTextArea();
		txtrTargetRemain.setBackground(new Color(0, 20, 51, 50));
		txtrTargetRemain.getCaret().deinstall(txtrTargetRemain);
		txtrTargetRemain.setText("  Enemy Ships:\t       \r\n  8\r\n\r\n\r\n\r\n  Patrol:\t3\r\n  Submarine:\t1\r\n  Cruiser:\t2\r\n  Battleship:\t1\r\n  Carrier:\t1");
		txtrTargetRemain.setFont(new Font("Impact", Font.ITALIC, 20));
		txtrTargetRemain.setForeground(Color.BLACK);
		txtrTargetRemain.setRows(11);
		txtrTargetRemain.setEditable(false);
		panel_2.add(txtrTargetRemain);
			
		//right text box with initial text
		JPanel panel_5 = new JPanel();
		panel_5.setOpaque(false);
		panel_6.add(panel_5);
		panel_5.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		txtrFleetRemain = new JTextArea();
		txtrFleetRemain.setBackground(new Color(0, 20, 51, 50));
		txtrFleetRemain.getCaret().deinstall(txtrFleetRemain);
		txtrFleetRemain.setText("  Your Fleet:\t      \r\n  8\r\n\r\n\r\n\r\n  Patrol:\t3\r\n  Submarine:\t1\r\n  Cruiser:\t2\r\n  Battleship:\t1\r\n  Carrier:\t1");
		txtrFleetRemain.setRows(11);
		txtrFleetRemain.setFont(new Font("Impact", Font.ITALIC, 20));
		txtrFleetRemain.setForeground(Color.BLACK);
		txtrFleetRemain.setEditable(false);
		panel_5.add(txtrFleetRemain);
		
		JPanel panel_7 = new JPanel();
		panel_7.setOpaque(false);
		FlowLayout flowLayout = (FlowLayout) panel_7.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.fill = GridBagConstraints.BOTH;
		gbc_panel_7.gridx = 0;
		gbc_panel_7.gridy = 1;
		panel_3.add(panel_7, gbc_panel_7);
		
		//adding bomb checkbox
		JCheckBox chckbxNewCheckBox = new JCheckBox("Use BOMB");
		chckbxNewCheckBox.setFont(new Font("Courier Ner", Font.BOLD, 24));
		chckbxNewCheckBox.setForeground(Color.WHITE);
		chckbxNewCheckBox.setOpaque(false);
		if (mode.equals("Mode2")) {
			chckbxNewCheckBox.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent itemEvent) {
			        int state = itemEvent.getStateChange();
			        if (state == ItemEvent.SELECTED) {
			        	if (game instanceof BattleShipMode2) {
			        		((BattleShipMode2)game).useBomb(true, chckbxNewCheckBox);
			        	}
			        } else {
			        	if (game instanceof BattleShipMode2) {
			        		((BattleShipMode2)game).useBomb(false, chckbxNewCheckBox);
			        	}
			        }
				}
			});
			panel_7.add(chckbxNewCheckBox);
		}
		
		//adding user board
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{540, 180};
		gbl_panel_1.rowHeights = new int[]{180};
		gbl_panel_1.columnWeights = new double[]{1.0, 1.0};
		gbl_panel_1.rowWeights = new double[]{1.0};
		panel_1.setLayout(gbl_panel_1);
		
		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_4.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_4.setOpaque(false);
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 0;
		panel_1.add(panel_4, gbc_panel_4);
		
		txtInformation = new JTextArea();
		txtInformation.setBackground(new Color(255, 255, 255, 150));
		txtInformation.getCaret().deinstall(txtInformation);
		txtInformation.setFont(new Font("Courier New", Font.BOLD, 20));
		txtInformation.setForeground(Color.BLACK);
		txtInformation.setEditable(false);
		txtInformation.setRows(6);
		txtInformation.setText("\r\n\r\n\r\n\r\n                                         ");
		panel_4.add(txtInformation);
		
		userBoard = new GUIGrids(180, game.getBoard(false));
		userBoard.setOpaque(false);
		GridBagConstraints gbc_userBoard = new GridBagConstraints();
		gbc_userBoard.fill = GridBagConstraints.BOTH;
		gbc_userBoard.gridx = 1;
		gbc_userBoard.gridy = 0;
		panel_1.add(userBoard, gbc_userBoard);
	}

	private class BattleShipAdapter extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {

			int x = e.getX();
			int y = e.getY();

			int cCol = x / computerBoard.CELL_SIZE;
			int cRow = y / computerBoard.CELL_SIZE;

			if ((x < computerBoard.N_COLS * computerBoard.CELL_SIZE) && (y < computerBoard.N_ROWS * computerBoard.CELL_SIZE) && inGame) {

				if (game.getBoard(true)[cRow][cCol] != Status.WATER && game.getBoard(true)[cRow][cCol] != Status.SHIPINTACT && game.getBoard(true)[cRow][cCol] != Status.SELECTED_WATER && game.getBoard(true)[cRow][cCol] != Status.SELECTED_SHIPINTACT) {

					return;
				}

				game.fireTarget(new Coordinate(cRow, cCol));
				if (game.getTargetInfo().size() == 0 || game.getFleetInfo().size() == 0) {
					inGame = false;
				}
				updateTxt();
				
				repaint();
			}
		}
	}

	
	/**
	 * method for updating text after each move
	 */
	private void updateTxt() {
		Set<Ship> targetLeft = game.getTargetInfo();
		Set<Ship> fleetLeft = game.getFleetInfo();
		String[] roundInfo = game.getRoundInfo();
		
		StringBuilder sb = new StringBuilder();
		sb.append("  Enemy Ships:\t       \r\n  ");
		sb.append(targetLeft.size());
		sb.append("\r\n\r\n\r\n\r\n  Patrol:\t");
		sb.append(countShip(targetLeft, "Patrol"));
		sb.append("\r\n  Submarine:\t");
		sb.append(countShip(targetLeft, "Submarine"));
		sb.append("\r\n  Cruiser:\t");
		sb.append(countShip(targetLeft, "Cruiser"));
		sb.append("\r\n  Battleship:\t");
		sb.append(countShip(targetLeft, "Battleship"));
		sb.append("\r\n  Carrier:\t");
		sb.append(countShip(targetLeft, "Carrier"));
		txtrTargetRemain.setText(sb.toString());
		
		sb = new StringBuilder();
		sb.append("  Your Fleet:\t      \r\n  ");
		sb.append(fleetLeft.size());
		sb.append("\r\n\r\n\r\n\r\n  Patrol:\t");
		sb.append(countShip(fleetLeft, "Patrol"));
		sb.append("\r\n  Submarine:\t");
		sb.append(countShip(fleetLeft, "Submarine"));
		sb.append("\r\n  Cruiser:\t");
		sb.append(countShip(fleetLeft, "Cruiser"));
		sb.append("\r\n  Battleship:\t");
		sb.append(countShip(fleetLeft, "Battleship"));
		sb.append("\r\n  Carrier:\t");
		sb.append(countShip(fleetLeft, "Carrier"));
		txtrFleetRemain.setText(sb.toString());
		
		sb = new StringBuilder();
		sb.append(roundInfo[0]);
		sb.append("\r\n ");
		sb.append(roundInfo[1]);
		sb.append("\r\n ");
		sb.append(roundInfo[2]);
		sb.append("\r\n ");
		sb.append(roundInfo[3]);
		sb.append("\r\n ");
		sb.append("                                         ");
		if (inGame) {
			txtInformation.setText(" " +sb.toString());
		} else {
			if (targetLeft.size() == 0) {
				txtInformation.setText(" Congratulations! You Win!\r\n\r\n\r\n\r\n                                         ");
			} else {
				txtInformation.setText(" You Lose!\r\n\r\n\r\n\r\n                                         ");
			}
		}
	}
	
	/**
	 * Helper method for determining how many ships of @param type are left
	 * Used in updateTxt method
	 * @param ships, set representing the remaining fleet
	 * @param ship, name/type of ship
	 * @return int count
	 */
	private int countShip(Set<Ship> ships, String ship) {
		int count = 0;
		for (Ship s : ships) {
			if (s.getName().equals(ship)) {
				count++;
			}
		}
		return count;
	}

	public void showShips() {
		computerBoard.showShips();
	}
}