package JoRaSue;
/**
 * GUI
 * Creates GUI for the game "JoRaSue"
 * @author HeeSue Kim, Tarannum Gupta
 * @version 20170205
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import JoRaSue.btnProperty;
import JoRaSue.EndGameProp;


enum btnTypeEnum{
	player1, player2, empty
}


public class GUI extends JFrame {

	private JPanel pnlBoard;			//button board
	private btnProperty[] btn = new btnProperty[9];	//9 buttons
	private JButton btnStart;

	public ImageIcon imgPlayer11 = new ImageIcon("angry.png");
	public ImageIcon imgPlayer22 = new ImageIcon("crying.png");
	public ImageIcon imghlPlayer11 = new ImageIcon("happyhl.png");
	public ImageIcon imghlPlayer22 = new ImageIcon("kisshl.png");
	
	
	Image angryImage = imgPlayer11.getImage();
	Image newAngryImage = angryImage.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
	ImageIcon imgPlayer1 = new ImageIcon(newAngryImage);
	
	Image cryingImage = imgPlayer22.getImage();
	Image newCryingImage = cryingImage.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
	ImageIcon imgPlayer2 = new ImageIcon(newCryingImage);
	
	Image happyhlImage = imghlPlayer11.getImage();
	Image newhappyhlImage = happyhlImage.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
	ImageIcon imgPlayer1hl = new ImageIcon(newhappyhlImage);

	Image kisshlImage = imghlPlayer22.getImage();
	Image newkisshlImage = kisshlImage.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
	ImageIcon imgPlayer2hl = new ImageIcon(newkisshlImage);
	
	ImageIcon imgWhite = new ImageIcon("white.png");
			
	
	public int intTurnCount = 0;	//counting the number of turns
	public String strPlayer1 = null; //initialize 'a' for player's name
	public String strPlayer2 = null;
	
	public EndGameProp propertyEnd = new EndGameProp();
	
	public GUI() {
		super();
		setSize(new Dimension(300,350));
		setTitle("JoRaSue");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		setContentPane(new JPanel(null));

		pnlBoard = new JPanel();
		pnlBoard.setLayout(new GridLayout(4,4));
		add(pnlBoard);

		
		//set buttons(array)
		btnListener bListener = new btnListener();
		for (int i = 0; i < 9; i++) {
			btn[i] = new btnProperty();	//initialize
			btn[i].btnObject = new JButton();
			btn[i].btnType = btnTypeEnum.empty;
			btn[i].btnObject.addActionListener(bListener);	//add to ActionListener
			btn[i].btnObject.setEnabled(false);
			pnlBoard.add(btn[i].btnObject);		//add in pnlBoard
		}

		
		btnStart = new JButton("Start"); // creates a button "Start"
		btnStart.setFont(new Font("Buxton Sketch", Font.PLAIN, 15));
		btnStart.setActionCommand("Start"); // set the action command to "Start"
		btnStart.addActionListener(new btnListener()); // add the button to the listener class
		add(btnStart);
		
		// set Location and Size
		pnlBoard.setSize(new Dimension(219, 288));
		pnlBoard.setLocation(38,38);
		
		btnStart.setSize(new Dimension(80, 30));
		btnStart.setLocation(175, 265);
		
		for (int i = 0  ; i < 9 ; i++){
			btn[i].btnObject.setPreferredSize(new Dimension(70,70));
		}

		setVisible(true);

	}

	
	private class btnListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			if (e.getActionCommand().equals("Start")) {
				intTurnCount = 0;
				intTurnCount += 1;											
				strPlayer1 = JOptionPane.showInputDialog("Player1 name?");	//asking player's name
				strPlayer2 = JOptionPane.showInputDialog("Player2 name?");	//asking player's name
				
				for (int i = 0; i < 9; i++) {	//distribute randomized numbers
					btn[i].btnObject.setActionCommand(String.valueOf(i));	//set Action commands
					btn[i].btnObject.setEnabled(true);
					btn[i].btnType = btnTypeEnum.empty;
					btn[i].btnObject.setIcon(imgWhite);
				}
				
				JOptionPane.showMessageDialog(null, "Game Start!");	//pop-up dialog to start
				btnStart.setEnabled(false);
				
				
			} else if (e.getActionCommand().equals("0")) {
				intTurnCount += 1;	
				boolean blnOccupied = mvcmodel.blnCheckOccupied(btn[0]);
				boolean blnPlayer = false;	// false:1 true:2
				blnPlayer = (intTurnCount%2 == 1)? true : false;
				if (blnOccupied){
					JOptionPane.showMessageDialog(null, "It's occupied!");
				} else {
					if (!blnPlayer){
						btn[0].btnObject.setIcon(imgPlayer1);
						btn[0].btnType = btnTypeEnum.player1;
					} else {
						btn[0].btnObject.setIcon(imgPlayer2);
						btn[0].btnType = btnTypeEnum.player2;
					}
				}		
				propertyEnd = mvcmodel.propCompLine(btn);
				if (propertyEnd.blnCompleteLine){
					 
					 	
					if(!blnPlayer){
						btn[propertyEnd.intCompLine1].btnObject.setIcon(imgPlayer1hl);
						btn[propertyEnd.intCompLine2].btnObject.setIcon(imgPlayer1hl);
						btn[propertyEnd.intCompLine3].btnObject.setIcon(imgPlayer1hl);
						int result = JOptionPane.showConfirmDialog(null, strPlayer1 + " is the winner!.", "end game", JOptionPane.DEFAULT_OPTION);
				        if (result == 0) System.exit(0);
					} else {
						btn[propertyEnd.intCompLine1].btnObject.setIcon(imgPlayer2hl);
						btn[propertyEnd.intCompLine2].btnObject.setIcon(imgPlayer2hl);
						btn[propertyEnd.intCompLine3].btnObject.setIcon(imgPlayer2hl);
						int result = JOptionPane.showConfirmDialog(null, strPlayer2 + " is the winner!.", "end game", JOptionPane.DEFAULT_OPTION);
				        if (result == 0) System.exit(0);
					}				
				} else if (!(propertyEnd.blnCompleteLine) && intTurnCount > 9) { //game is tied
					int result = JOptionPane.showConfirmDialog(null, "It was a tie game, " + strPlayer1 + ", " + strPlayer2 + "!", "end game", JOptionPane.DEFAULT_OPTION);
			        if (result == 0) System.exit(0);
				}

				
				
			} else if (e.getActionCommand().equals("1")) {
				intTurnCount += 1;	
				boolean blnOccupied = mvcmodel.blnCheckOccupied(btn[1]);
				boolean blnPlayer = false;	//true:1 false:2
				blnPlayer = (intTurnCount%2 == 1)? true : false;
				if (blnOccupied){
					JOptionPane.showMessageDialog(null, "It's occupied!");
				} else {
					if (!blnPlayer){
						btn[1].btnObject.setIcon(imgPlayer1);
						btn[1].btnType = btnTypeEnum.player1;
					} else {
						btn[1].btnObject.setIcon(imgPlayer2);
						btn[1].btnType = btnTypeEnum.player2;
					}
				}
							
				propertyEnd = mvcmodel.propCompLine(btn);
				if (propertyEnd.blnCompleteLine){
					 
					 	
					if(!blnPlayer){
						btn[propertyEnd.intCompLine1].btnObject.setIcon(imgPlayer1hl);
						btn[propertyEnd.intCompLine2].btnObject.setIcon(imgPlayer1hl);
						btn[propertyEnd.intCompLine3].btnObject.setIcon(imgPlayer1hl);
						int result = JOptionPane.showConfirmDialog(null, strPlayer1 + " is the winner!.", "end game", JOptionPane.DEFAULT_OPTION);
				        if (result == 0) System.exit(0);
					} else {
						btn[propertyEnd.intCompLine1].btnObject.setIcon(imgPlayer2hl);
						btn[propertyEnd.intCompLine2].btnObject.setIcon(imgPlayer2hl);
						btn[propertyEnd.intCompLine3].btnObject.setIcon(imgPlayer2hl);
						int result = JOptionPane.showConfirmDialog(null, strPlayer2 + " is the winner!.", "end game", JOptionPane.DEFAULT_OPTION);
				        if (result == 0) System.exit(0);
					}				
				} else if (!(propertyEnd.blnCompleteLine) && intTurnCount > 9) { //game is tied
					int result = JOptionPane.showConfirmDialog(null, "It was a tie game, " + strPlayer1 + ", " + strPlayer2 + "!", "end game", JOptionPane.DEFAULT_OPTION);
			        if (result == 0) System.exit(0);
				}

				
			
			} else if (e.getActionCommand().equals("2")) {
				intTurnCount += 1;	
				boolean blnOccupied = mvcmodel.blnCheckOccupied(btn[2]);
				boolean blnPlayer = false;	//true:1 false:2
				blnPlayer = (intTurnCount%2 == 1)? true : false;
				if (blnOccupied){
					JOptionPane.showMessageDialog(null, "It's occupied!");
				} else {
					if (!blnPlayer){
						btn[2].btnObject.setIcon(imgPlayer1);
						btn[2].btnType = btnTypeEnum.player1;
					} else {
						btn[2].btnObject.setIcon(imgPlayer2);
						btn[2].btnType = btnTypeEnum.player2;
					}
				}
							
				propertyEnd = mvcmodel.propCompLine(btn);
				if (propertyEnd.blnCompleteLine){
					 
					 	
					if(!blnPlayer){
						btn[propertyEnd.intCompLine1].btnObject.setIcon(imgPlayer1hl);
						btn[propertyEnd.intCompLine2].btnObject.setIcon(imgPlayer1hl);
						btn[propertyEnd.intCompLine3].btnObject.setIcon(imgPlayer1hl);
						int result = JOptionPane.showConfirmDialog(null, strPlayer1 + " is the winner!.", "end game", JOptionPane.DEFAULT_OPTION);
				        if (result == 0) System.exit(0);
					} else {
						btn[propertyEnd.intCompLine1].btnObject.setIcon(imgPlayer2hl);
						btn[propertyEnd.intCompLine2].btnObject.setIcon(imgPlayer2hl);
						btn[propertyEnd.intCompLine3].btnObject.setIcon(imgPlayer2hl);
						int result = JOptionPane.showConfirmDialog(null, strPlayer2 + " is the winner!.", "end game", JOptionPane.DEFAULT_OPTION);
				        if (result == 0) System.exit(0);
					}				
				} else if (!(propertyEnd.blnCompleteLine) && intTurnCount > 9) { //game is tied
					int result = JOptionPane.showConfirmDialog(null, "It was a tie game, " + strPlayer1 + ", " + strPlayer2 + "!", "end game", JOptionPane.DEFAULT_OPTION);
			        if (result == 0) System.exit(0);
				}

						
			} else if (e.getActionCommand().equals("3")) {
				intTurnCount += 1;	
				boolean blnOccupied = mvcmodel.blnCheckOccupied(btn[3]);
				boolean blnPlayer = false;	//true:1 false:2
				blnPlayer = (intTurnCount%2 == 1)? true : false;
				if (blnOccupied){
					JOptionPane.showMessageDialog(null, "It's occupied!");
				} else {
					if (!blnPlayer){
						btn[3].btnObject.setIcon(imgPlayer1);
						btn[3].btnType = btnTypeEnum.player1;
					} else {
						btn[3].btnObject.setIcon(imgPlayer2);
						btn[3].btnType = btnTypeEnum.player2;
					}
				}
							
				propertyEnd = mvcmodel.propCompLine(btn);
				if (propertyEnd.blnCompleteLine){
					 
					 	
					if(!blnPlayer){
						btn[propertyEnd.intCompLine1].btnObject.setIcon(imgPlayer1hl);
						btn[propertyEnd.intCompLine2].btnObject.setIcon(imgPlayer1hl);
						btn[propertyEnd.intCompLine3].btnObject.setIcon(imgPlayer1hl);
						int result = JOptionPane.showConfirmDialog(null, strPlayer1 + " is the winner!.", "end game", JOptionPane.DEFAULT_OPTION);
				        if (result == 0) System.exit(0);
					} else {
						btn[propertyEnd.intCompLine1].btnObject.setIcon(imgPlayer2hl);
						btn[propertyEnd.intCompLine2].btnObject.setIcon(imgPlayer2hl);
						btn[propertyEnd.intCompLine3].btnObject.setIcon(imgPlayer2hl);
						int result = JOptionPane.showConfirmDialog(null, strPlayer2 + " is the winner!.", "end game", JOptionPane.DEFAULT_OPTION);
				        if (result == 0) System.exit(0);
					}				
				} else if (!(propertyEnd.blnCompleteLine) && intTurnCount > 9) { //game is tied
					int result = JOptionPane.showConfirmDialog(null, "It was a tie game, " + strPlayer1 + ", " + strPlayer2 + "!", "end game", JOptionPane.DEFAULT_OPTION);
			        if (result == 0) System.exit(0);
				}
	
			} else if (e.getActionCommand().equals("4")) {
				intTurnCount += 1;
				boolean blnOccupied = mvcmodel.blnCheckOccupied(btn[4]);
				boolean blnPlayer = false;	//true:1 false:2
				blnPlayer = (intTurnCount%2 == 1)? true : false;
				if (blnOccupied){
					JOptionPane.showMessageDialog(null, "It's occupied!");
				} else {
					if (!blnPlayer){
						btn[4].btnObject.setIcon(imgPlayer1);
						btn[4].btnType = btnTypeEnum.player1;
					} else {
						btn[4].btnObject.setIcon(imgPlayer2);
						btn[4].btnType = btnTypeEnum.player2;
					}
				}
							
				propertyEnd = mvcmodel.propCompLine(btn);
				if (propertyEnd.blnCompleteLine){
					 
					 	
					if(!blnPlayer){
						btn[propertyEnd.intCompLine1].btnObject.setIcon(imgPlayer1hl);
						btn[propertyEnd.intCompLine2].btnObject.setIcon(imgPlayer1hl);
						btn[propertyEnd.intCompLine3].btnObject.setIcon(imgPlayer1hl);
						int result = JOptionPane.showConfirmDialog(null, strPlayer1 + " is the winner!.", "end game", JOptionPane.DEFAULT_OPTION);
				        if (result == 0) System.exit(0);
					} else {
						btn[propertyEnd.intCompLine1].btnObject.setIcon(imgPlayer2hl);
						btn[propertyEnd.intCompLine2].btnObject.setIcon(imgPlayer2hl);
						btn[propertyEnd.intCompLine3].btnObject.setIcon(imgPlayer2hl);
						int result = JOptionPane.showConfirmDialog(null, strPlayer2 + " is the winner!.", "end game", JOptionPane.DEFAULT_OPTION);
				        if (result == 0) System.exit(0);
					}				
				} else if (!(propertyEnd.blnCompleteLine) && intTurnCount > 9) { //game is tied
					int result = JOptionPane.showConfirmDialog(null, "It was a tie game, " + strPlayer1 + ", " + strPlayer2 + "!", "end game", JOptionPane.DEFAULT_OPTION);
			        if (result == 0) System.exit(0);
				}

					
			} else if (e.getActionCommand().equals("5")) {
				intTurnCount += 1;
				boolean blnOccupied = mvcmodel.blnCheckOccupied(btn[5]);
				boolean blnPlayer = false;	//true:1 false:2
				blnPlayer = (intTurnCount%2 == 1)? true : false;
				if (blnOccupied){
					JOptionPane.showMessageDialog(null, "It's occupied!");
				} else {
					if (!blnPlayer){
						btn[5].btnObject.setIcon(imgPlayer1);
						btn[5].btnType = btnTypeEnum.player1;
					} else {
						btn[5].btnObject.setIcon(imgPlayer2);
						btn[5].btnType = btnTypeEnum.player2;
					}
				}
							
				propertyEnd = mvcmodel.propCompLine(btn);
				if (propertyEnd.blnCompleteLine){
					 
					 	
					if(!blnPlayer){
						btn[propertyEnd.intCompLine1].btnObject.setIcon(imgPlayer1hl);
						btn[propertyEnd.intCompLine2].btnObject.setIcon(imgPlayer1hl);
						btn[propertyEnd.intCompLine3].btnObject.setIcon(imgPlayer1hl);
						int result = JOptionPane.showConfirmDialog(null, strPlayer1 + " is the winner!.", "end game", JOptionPane.DEFAULT_OPTION);
				        if (result == 0) System.exit(0);
					} else {
						btn[propertyEnd.intCompLine1].btnObject.setIcon(imgPlayer2hl);
						btn[propertyEnd.intCompLine2].btnObject.setIcon(imgPlayer2hl);
						btn[propertyEnd.intCompLine3].btnObject.setIcon(imgPlayer2hl);
						int result = JOptionPane.showConfirmDialog(null, strPlayer2 + " is the winner!.", "end game", JOptionPane.DEFAULT_OPTION);
				        if (result == 0) System.exit(0);
					}				
				} else if (!(propertyEnd.blnCompleteLine) && intTurnCount > 9) { //game is tied
					int result = JOptionPane.showConfirmDialog(null, "It was a tie game, " + strPlayer1 + ", " + strPlayer2 + "!", "end game", JOptionPane.DEFAULT_OPTION);
			        if (result == 0) System.exit(0);
				}

					
			} else if (e.getActionCommand().equals("6")) {
				intTurnCount += 1;
				boolean blnOccupied = mvcmodel.blnCheckOccupied(btn[6]);
				boolean blnPlayer = false;	//true:1 false:2
				blnPlayer = (intTurnCount%2 == 1)? true : false;
				if (blnOccupied){
					JOptionPane.showMessageDialog(null, "It's occupied!");
				} else {
					if (!blnPlayer){
						btn[6].btnObject.setIcon(imgPlayer1);
						btn[6].btnType = btnTypeEnum.player1;
					} else {
						btn[6].btnObject.setIcon(imgPlayer2);
						btn[6].btnType = btnTypeEnum.player2;
					}
				}
							
				propertyEnd = mvcmodel.propCompLine(btn);
				if (propertyEnd.blnCompleteLine){
					 
					 	
					if(!blnPlayer){
						btn[propertyEnd.intCompLine1].btnObject.setIcon(imgPlayer1hl);
						btn[propertyEnd.intCompLine2].btnObject.setIcon(imgPlayer1hl);
						btn[propertyEnd.intCompLine3].btnObject.setIcon(imgPlayer1hl);
						int result = JOptionPane.showConfirmDialog(null, strPlayer1 + " is the winner!.", "end game", JOptionPane.DEFAULT_OPTION);
				        if (result == 0) System.exit(0);
					} else {
						btn[propertyEnd.intCompLine1].btnObject.setIcon(imgPlayer2hl);
						btn[propertyEnd.intCompLine2].btnObject.setIcon(imgPlayer2hl);
						btn[propertyEnd.intCompLine3].btnObject.setIcon(imgPlayer2hl);
						int result = JOptionPane.showConfirmDialog(null, strPlayer2 + " is the winner!.", "end game", JOptionPane.DEFAULT_OPTION);
				        if (result == 0) System.exit(0);
					}				
				} else if (!(propertyEnd.blnCompleteLine) && intTurnCount > 9) { //game is tied
					int result = JOptionPane.showConfirmDialog(null, "It was a tie game, " + strPlayer1 + ", " + strPlayer2 + "!", "end game", JOptionPane.DEFAULT_OPTION);
			        if (result == 0) System.exit(0);
				}

				
			} else if (e.getActionCommand().equals("7")) {
				intTurnCount += 1;
				boolean blnOccupied = mvcmodel.blnCheckOccupied(btn[7]);
				boolean blnPlayer = false;	//true:1 false:2
				blnPlayer = (intTurnCount%2 == 1)? true : false;
				if (blnOccupied){
					JOptionPane.showMessageDialog(null, "It's occupied!");
				} else {
					if (!blnPlayer){
						btn[7].btnObject.setIcon(imgPlayer1);
						btn[7].btnType = btnTypeEnum.player1;
					} else {
						btn[7].btnObject.setIcon(imgPlayer2);
						btn[7].btnType = btnTypeEnum.player2;
					}
				}
							
				propertyEnd = mvcmodel.propCompLine(btn);
				if (propertyEnd.blnCompleteLine){
					 
					 	
					if(!blnPlayer){
						btn[propertyEnd.intCompLine1].btnObject.setIcon(imgPlayer1hl);
						btn[propertyEnd.intCompLine2].btnObject.setIcon(imgPlayer1hl);
						btn[propertyEnd.intCompLine3].btnObject.setIcon(imgPlayer1hl);
						int result = JOptionPane.showConfirmDialog(null, strPlayer1 + " is the winner!.", "end game", JOptionPane.DEFAULT_OPTION);
				        if (result == 0) System.exit(0);
					} else {
						btn[propertyEnd.intCompLine1].btnObject.setIcon(imgPlayer2hl);
						btn[propertyEnd.intCompLine2].btnObject.setIcon(imgPlayer2hl);
						btn[propertyEnd.intCompLine3].btnObject.setIcon(imgPlayer2hl);
						int result = JOptionPane.showConfirmDialog(null, strPlayer2 + " is the winner!.", "end game", JOptionPane.DEFAULT_OPTION);
				        if (result == 0) System.exit(0);
					}				
				} else if (!(propertyEnd.blnCompleteLine) && intTurnCount > 9) { //game is tied
					int result = JOptionPane.showConfirmDialog(null, "It was a tie game, " + strPlayer1 + ", " + strPlayer2 + "!", "end game", JOptionPane.DEFAULT_OPTION);
			        if (result == 0) System.exit(0);
				}

				
			} else if (e.getActionCommand().equals("8")) {
				intTurnCount += 1;	
				boolean blnOccupied = mvcmodel.blnCheckOccupied(btn[8]);
				boolean blnPlayer = false;	//true:1 false:2
				blnPlayer = (intTurnCount%2 == 1)? true : false;
				if (blnOccupied){
					JOptionPane.showMessageDialog(null, "It's occupied!");
				} else {
					if (!blnPlayer){
						btn[8].btnObject.setIcon(imgPlayer1);
						btn[8].btnType = btnTypeEnum.player1;
					} else {
						btn[8].btnObject.setIcon(imgPlayer2);
						btn[8].btnType = btnTypeEnum.player2;
					}
				}
							
				propertyEnd = mvcmodel.propCompLine(btn);
				if (propertyEnd.blnCompleteLine){
					 
					 	
					if(!blnPlayer){
						btn[propertyEnd.intCompLine1].btnObject.setIcon(imgPlayer1hl);
						btn[propertyEnd.intCompLine2].btnObject.setIcon(imgPlayer1hl);
						btn[propertyEnd.intCompLine3].btnObject.setIcon(imgPlayer1hl);
						int result = JOptionPane.showConfirmDialog(null, strPlayer1 + " is the winner!.", "end game", JOptionPane.DEFAULT_OPTION);
				        if (result == 0) System.exit(0);
					} else {
						btn[propertyEnd.intCompLine1].btnObject.setIcon(imgPlayer2hl);
						btn[propertyEnd.intCompLine2].btnObject.setIcon(imgPlayer2hl);
						btn[propertyEnd.intCompLine3].btnObject.setIcon(imgPlayer2hl);
						int result = JOptionPane.showConfirmDialog(null, strPlayer2 + " is the winner!.", "end game", JOptionPane.DEFAULT_OPTION);
				        if (result == 0) System.exit(0);
					}				
				} else if (!(propertyEnd.blnCompleteLine) && intTurnCount > 9) { //game is tied
					int result = JOptionPane.showConfirmDialog(null, "It was a tie game, " + strPlayer1 + ", " + strPlayer2 + "!", "end game", JOptionPane.DEFAULT_OPTION);
			        if (result == 0) System.exit(0);
				}

			
			}
						
			
		}//end: public void actionPerformed(ActionEvent e)
	
	}//end: private class btnListener implements ActionListener

/**
 * main
 * starts the game
 * @param args
 * @return void
 */
	public static void main(String[] args) {
		new GUI();
	}
}