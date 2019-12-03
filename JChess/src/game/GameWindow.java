package game;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;


/**
 * 
 * @author Oskar
 *
 */

public class GameWindow{

	private JFrame frame;
	
	//Board Variables and Objects
	
	Image blackRookIcon = null,
			blackKnightIcon = null,
			blackBishopIcon = null,
			blackKingIcon = null,
			blackQueenIcon = null,
			blackPawnIcon = null,
			blackTileIcon = null,
			whiteRookIcon = null,
			whiteKnightIcon = null,
			whiteBishopIcon = null,
			whiteKingIcon = null,
			whiteQueenIcon = null,
			whitePawnIcon = null,
			whiteTileIcon = null;
	
	JButton[] boardTile = new JButton[64]; 
		
	//End board variables and objects

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameWindow window = new GameWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	 // Create the application.
	 
	public GameWindow() {
		initialize();
	}

	 //Initialize the contents of the frame.
	 
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 550);
		frame.setTitle("Chess in Java!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		try {
			blackRookIcon = ImageIO.read(getClass().getResource("/image/blackRook.png"));
			blackKnightIcon = ImageIO.read(getClass().getResource("/image/blackKnight.png"));
			blackBishopIcon = ImageIO.read(getClass().getResource("/image/blackBishop.png"));
			blackQueenIcon = ImageIO.read(getClass().getResource("/image/blackQueen.png"));
			blackKingIcon = ImageIO.read(getClass().getResource("/image/blackKing.png"));
			blackPawnIcon = ImageIO.read(getClass().getResource("/image/blackPawn.png"));
			blackTileIcon = ImageIO.read(getClass().getResource("/image/blackTile.png"));
			
			whiteRookIcon = ImageIO.read(getClass().getResource( "/image/whiteRook.png" ));
			whiteKnightIcon = ImageIO.read(getClass().getResource("/image/whiteKnight.png"));
			whiteBishopIcon = ImageIO.read(getClass().getResource("/image/whiteBishop.png"));
			whiteQueenIcon = ImageIO.read(getClass().getResource("/image/whiteQueen.png"));
			whiteKingIcon = ImageIO.read(getClass().getResource("/image/whiteKing.png"));
			whitePawnIcon = ImageIO.read(getClass().getResource("/image/whitePawn.png"));
			whiteTileIcon = ImageIO.read(getClass().getResource("/image/whiteTile.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ImageIcon bRookIcon = new ImageIcon(blackRookIcon);
		ImageIcon bKnightIcon = new ImageIcon(blackKnightIcon);
		ImageIcon bBishopIcon = new ImageIcon(blackBishopIcon);
		ImageIcon bQueenIcon = new ImageIcon(blackQueenIcon);
		ImageIcon bKingIcon = new ImageIcon(blackKingIcon);
		ImageIcon bPawnIcon = new ImageIcon(blackPawnIcon);
		ImageIcon bTileIcon = new ImageIcon(blackTileIcon);
		
		ImageIcon wRookIcon = new ImageIcon(whiteRookIcon);
		ImageIcon wKnightIcon = new ImageIcon(whiteKnightIcon);
		ImageIcon wBishopIcon = new ImageIcon(whiteBishopIcon);
		ImageIcon wQueenIcon = new ImageIcon(whiteQueenIcon);
		ImageIcon wKingIcon = new ImageIcon(whiteKingIcon);
		ImageIcon wPawnIcon	= new ImageIcon (whitePawnIcon);
		ImageIcon wTileIcon = new ImageIcon(whiteTileIcon);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 8, 3, 3));
		
		boardTile[0] = new JButton("");
		boardTile[0].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[0]);
		boardTile[0].setIcon(bRookIcon);
		
		boardTile[1] = new JButton("");
		boardTile[1].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[1]);
		boardTile[1].setIcon(bKnightIcon);
		
		boardTile[2] = new JButton("");
		boardTile[2].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[2]);
		boardTile[2].setIcon(bBishopIcon);
		
		boardTile[3] = new JButton("");
		boardTile[3].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[3]);
		boardTile[3].setIcon(bKingIcon);
		
		boardTile[4] = new JButton("");
		boardTile[4].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[4]);
		boardTile[4].setIcon(bQueenIcon);
		
		boardTile[5] = new JButton("");
		boardTile[5].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[5]);
		boardTile[5].setIcon(bBishopIcon);
		
		boardTile[6] = new JButton("");
		boardTile[6].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[6]);
		boardTile[6].setIcon(bKnightIcon);
		
		boardTile[7] = new JButton("");
		boardTile[7].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[7]);
		boardTile[7].setIcon(bRookIcon);
		
		boardTile[8] = new JButton("");
		boardTile[8].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[8]);
		boardTile[8].setIcon(bPawnIcon);
		
		boardTile[9] = new JButton("");
		boardTile[9].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[9]);
		boardTile[9].setIcon(bPawnIcon);
		
		boardTile[10] = new JButton("");
		boardTile[10].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[10]);
		boardTile[10].setIcon(bPawnIcon);
		
		boardTile[11] = new JButton("");
		boardTile[11].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[11]);
		boardTile[11].setIcon(bPawnIcon);
		
		boardTile[12] = new JButton("");
		boardTile[12].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[12]);
		boardTile[12].setIcon(bPawnIcon);
		
		boardTile[13] = new JButton("");
		boardTile[13].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[13]);
		boardTile[13].setIcon(bPawnIcon);
		
		boardTile[14] = new JButton("");
		boardTile[14].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[14]);
		boardTile[14].setIcon(bPawnIcon);
		
		boardTile[15] = new JButton("");
		boardTile[15].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[15]);
		boardTile[15].setIcon(bPawnIcon);
		
		boardTile[16] = new JButton("");
		panel.add(boardTile[16]);
		boardTile[16].setIcon(bTileIcon);
		
		boardTile[17] = new JButton("");
		panel.add(boardTile[17]);
		boardTile[17].setIcon(wTileIcon);
		
		boardTile[18] = new JButton("");
		panel.add(boardTile[18]);
		boardTile[18].setIcon(bTileIcon);
		
		boardTile[19] = new JButton("");
		panel.add(boardTile[19]);
		boardTile[19].setIcon(wTileIcon);
		
		boardTile[20] = new JButton("");
		panel.add(boardTile[20]);
		boardTile[20].setIcon(bTileIcon);
		
		boardTile[21] = new JButton("");
		panel.add(boardTile[21]);
		boardTile[21].setIcon(wTileIcon);
		
		boardTile[22] = new JButton("");
		panel.add(boardTile[22]);
		boardTile[22].setIcon(bTileIcon);
		
		boardTile[23] = new JButton("");
		panel.add(boardTile[23]);
		boardTile[23].setIcon(wTileIcon);
		
		boardTile[24] = new JButton("");
		panel.add(boardTile[24]);
		boardTile[24].setIcon(wTileIcon);
		
		boardTile[25] = new JButton("");
		panel.add(boardTile[25]);
		boardTile[25].setIcon(bTileIcon);
		
		boardTile[26] = new JButton("");
		panel.add(boardTile[26]);
		boardTile[26].setIcon(wTileIcon);
		
		boardTile[27] = new JButton("");
		panel.add(boardTile[27]);
		boardTile[27].setIcon(bTileIcon);
		
		boardTile[28] = new JButton("");
		panel.add(boardTile[28]);
		boardTile[28].setIcon(wTileIcon);
		
		boardTile[29] = new JButton("");
		panel.add(boardTile[29]);
		boardTile[29].setIcon(bTileIcon);
		
		boardTile[30] = new JButton("");
		panel.add(boardTile[30]);
		boardTile[30].setIcon(wTileIcon);
		
		boardTile[31] = new JButton("");
		panel.add(boardTile[31]);
		boardTile[31].setIcon(bTileIcon);
		
		boardTile[32] = new JButton("");
		panel.add(boardTile[32]);
		boardTile[32].setIcon(bTileIcon);
		
		boardTile[33] = new JButton("");
		panel.add(boardTile[33]);
		boardTile[33].setIcon(wTileIcon);
		
		boardTile[34] = new JButton("");
		panel.add(boardTile[34]);
		boardTile[34].setIcon(bTileIcon);
		
		boardTile[35] = new JButton("");
		panel.add(boardTile[35]);
		boardTile[35].setIcon(wTileIcon);
		
		boardTile[36] = new JButton("");
		panel.add(boardTile[36]);
		boardTile[36].setIcon(bTileIcon);
		
		boardTile[37] = new JButton("");
		panel.add(boardTile[37]);
		boardTile[37].setIcon(wTileIcon);
		
		boardTile[38] = new JButton("");
		panel.add(boardTile[38]);
		boardTile[38].setIcon(bTileIcon);
		
		boardTile[39] = new JButton("");
		panel.add(boardTile[39]);
		boardTile[39].setIcon(wTileIcon);
		
		boardTile[40] = new JButton("");
		panel.add(boardTile[40]);
		boardTile[40].setIcon(wTileIcon);
		
		boardTile[41] = new JButton("");
		panel.add(boardTile[41]);
		boardTile[41].setIcon(bTileIcon);
		
		boardTile[42] = new JButton("");
		panel.add(boardTile[42]);
		boardTile[42].setIcon(wTileIcon);
		
		boardTile[43] = new JButton("");
		panel.add(boardTile[43]);
		boardTile[43].setIcon(bTileIcon);
		
		boardTile[44] = new JButton("");
		panel.add(boardTile[44]);
		boardTile[44].setIcon(wTileIcon);
		
		boardTile[45] = new JButton("");
		panel.add(boardTile[45]);
		boardTile[45].setIcon(bTileIcon);
		
		boardTile[46] = new JButton("");
		panel.add(boardTile[46]);
		boardTile[46].setIcon(wTileIcon);
		
		boardTile[47] = new JButton("");
		panel.add(boardTile[47]);
		boardTile[47].setIcon(bTileIcon);
		
		boardTile[48] = new JButton("");
		boardTile[48].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[48]);
		boardTile[48].setIcon(wPawnIcon);
		
		boardTile[49] = new JButton("");
		boardTile[49].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[49]);
		boardTile[49].setIcon(wPawnIcon);
		
		boardTile[50] = new JButton("");
		boardTile[50].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[50]);
		boardTile[50].setIcon(wPawnIcon);
		
		boardTile[51] = new JButton("");
		boardTile[51].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[51]);
		boardTile[51].setIcon(wPawnIcon);
		
		boardTile[52] = new JButton("");
		boardTile[52].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[52]);
		boardTile[52].setIcon(wPawnIcon);
		
		boardTile[53] = new JButton("");
		boardTile[53].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[53]);
		boardTile[53].setIcon(wPawnIcon);
		
		boardTile[54] = new JButton("");
		boardTile[54].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[54]);
		boardTile[54].setIcon(wPawnIcon);
		
		boardTile[55] = new JButton("");
		boardTile[55].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[55]);
		boardTile[55].setIcon(wPawnIcon);
		
		boardTile[56] = new JButton("");
		boardTile[56].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[56]);
		boardTile[56].setIcon(wRookIcon);
		
		boardTile[57] = new JButton("");
		boardTile[57].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[57]);
		boardTile[57].setIcon(wKnightIcon);
		
		boardTile[58] = new JButton("");
		boardTile[58].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[58]);
		boardTile[58].setIcon(wBishopIcon);
		
		boardTile[59] = new JButton("");
		boardTile[59].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[59]);
		boardTile[59].setIcon(wKingIcon);
		
		boardTile[60] = new JButton("");
		boardTile[60].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[60]);
		boardTile[60].setIcon(wQueenIcon);
		
		boardTile[61] = new JButton("");
		boardTile[61].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[61]);
		boardTile[61].setIcon(wBishopIcon);
		
		boardTile[62] = new JButton("");
		boardTile[62].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[62]);
		boardTile[62].setIcon(wKnightIcon);
		
		boardTile[63] = new JButton("");
		boardTile[63].setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(boardTile[63]);
		boardTile[63].setIcon(wRookIcon);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblPleaseClickA = new JLabel("Please Click a tile to Select It");
		panel_1.add(lblPleaseClickA);
		
		JLabel lblItIss = new JLabel("It is: 's Turn");
		panel_1.add(lblItIss);
		
		JButton btnGiveUp = new JButton("Give Up");
		panel_1.add(btnGiveUp);
		
		JButton btnResetGame = new JButton("Reset Game");
		panel_1.add(btnResetGame);
		
		for(int i = 0; i < boardTile.length; i ++) {
			boardTile[i].addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	JButton src = (JButton)e.getSource();
			    	int index = 0;
			    	for(int i = 0; i < boardTile.length; i++){
			    		if(src == boardTile[i]) {
			    			index = i;
			    		}
			    	}
			        System.out.println("You just clicked the button at index: " + index);
			    }
			});
		}
		
	}
}
