package chess;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;


/**
 * 
 * @author Oskar
 *
 */

public class GameWindow{

	private JFrame gameFrame;
	
	//Board Variables and Objects
	
	Image blackTileIcon = ImageIO.read(getClass().getResource("/resources/blackset/blackTile.png")), whiteTileIcon = ImageIO.read(getClass().getResource("/resources/whiteset/whiteTile.png"));

        ImageIcon bTileIcon = new ImageIcon(blackTileIcon);

        ImageIcon wTileIcon = new ImageIcon(whiteTileIcon);
        
	JButton[] boardTile = new JButton[64]; 
        
        boolean selecting = true;//if you're selecting a piece when you click. if not, youre choosing where to move a selected piece.
        
        boolean gameOver = false;//if true, the game is over and cannot be played until restarted
        
        int selected = -1;//index of selected piece. -1 = no selection
	
        
        JLabel lblItIss = new JLabel("It is white's turn");
        
	//End board variables and objects

	/**
	 * Launch the application.
     * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
                    try {
                        GameWindow window = new GameWindow();
                        window.gameFrame.setVisible(true);
                    } catch (IOException e) {
                    }
                });
	}

	 // Create the application.
	 
	public GameWindow() throws IOException {
		initialize();
	}

	 //Initialize the contents of the frame.
	 
        
        public void displayFromChess() throws IOException{
            pieceClass[] boardToUse = Chess.board;
            for(int i = 0; i < boardToUse.length; i++){
                pieceClass piece = boardToUse[i];
                if(piece.abstractPiece || piece.name == null || piece.team == null){//if its a blank space (no icon)
                    
                    if(Helpers.getX(i)%2 == 0){//checker pattern math
                        if(Helpers.getY(i)%2 == 0){
                            boardTile[i].setIcon(bTileIcon);
                        } else {
                            boardTile[i].setIcon(wTileIcon);
                        }
                    } else {
                        if(Helpers.getY(i)%2 == 0){
                            boardTile[i].setIcon(wTileIcon);
                        } else {
                            boardTile[i].setIcon(bTileIcon);
                        }
                    }
                    
                } else {
                    boardTile[i].setIcon(assembleIcon(piece.team, piece.name));
                }
                boardTile[i].setBackground(Color.gray);
            }
        }
        
        public void highlightValids(int[] validMoves){
            for(int validMove:validMoves){
                boardTile[validMove].setBackground(Color.red);
            }
        }
        
        public ImageIcon assembleIcon(String team, String name) throws IOException{
            String assembledImagePath = "/resources/" + team + "set/" + team + Helpers.capitalFirstLetter(name) + ".png";
            //System.out.println("Assembling an image at "+assembledImagePath);
            Image eventualIcon = ImageIO.read(getClass().getResource(assembledImagePath));
            return new ImageIcon(eventualIcon);
        }
        
        public void nextTurn() throws IOException{
            Chess.nextTurn();
            lblItIss.setText("It is "+Chess.whoseTurn+"'s turn");
            selected = -1;
            selecting = true;
            displayFromChess();
        }
        
	private void initialize() throws IOException {
		gameFrame = new JFrame();
		gameFrame.setVisible(true);
		gameFrame.setBounds(100, 100, 800, 550);
		gameFrame.setTitle("Chess in Java!");
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		

		
		JPanel panel = new JPanel();
		gameFrame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 8, 3, 3));
		
                for(int i = 0; i < Helpers.BOARDSIZE*Helpers.BOARDSIZE; i++){
                    boardTile[i] = new JButton("");
                    boardTile[i].setHorizontalAlignment(SwingConstants.CENTER);
                    panel.add(boardTile[i]);
                }
                
                Chess.setUpBoard();
                displayFromChess();
		
		JPanel panel_1 = new JPanel();
		gameFrame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblAnnouncer = new JLabel("Click a piece to select it.");
		panel_1.add(lblAnnouncer);
		
		panel_1.add(lblItIss);
		
		JButton btnGiveUp = new JButton("Give Up");
		panel_1.add(btnGiveUp);
		
                btnGiveUp.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(gameOver) return;
                        lblAnnouncer.setText(Helpers.capitalFirstLetter(Chess.whoseTurn)+" forefeits. Game over!");
                        gameOver = true;
                    }
                });
                
		JButton btnResetGame = new JButton("Reset Game");
		panel_1.add(btnResetGame);
		
                btnResetGame.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Chess.setUpBoard();
                        try {
                            displayFromChess();
                        } catch (IOException ex) {
                            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        lblAnnouncer.setText("Restarting... White plays first.");
                        lblItIss.setText("It is white's turn");
                        gameOver = false;
                    }
                });
                
		for(int i = 0; i < boardTile.length; i ++) {
			boardTile[i].addActionListener(new ActionListener() {
                            @Override
			    public void actionPerformed(ActionEvent e) {
			    	JButton src = (JButton)e.getSource();
			    	int index = 0;
			    	for(int i = 0; i < boardTile.length; i++){
			    		if(src == boardTile[i]) {
			    			index = i;
			    		}
			    	}
//			        System.out.println(index);
                                
                                if(gameOver){
                                    return;
                                }
                                try {
                                    displayFromChess();
                                } catch (IOException ex) {
                                    Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                
                                
                                if(selecting || selected == -1){

                                    pieceClass chosenPiece = Chess.board[index];
                                    if(!chosenPiece.team.equals(Chess.whoseTurn) || chosenPiece.abstractPiece){
                                        lblAnnouncer.setText("You don't own that piece.");
                                        return;
                                    }
                                    
                                    int[] importedArray = Chess.testMovesOfOne(index);
                                    if(importedArray.length == 0){
                                        lblAnnouncer.setText("He has no moves.");
                                        return;
                                    }
                                    lblAnnouncer.setText("Click a red space to move to.");
                                    highlightValids(importedArray);
                                    selected = index;
                                    selecting = false;

                                } else {

                                    int[] importedArray = Chess.testMovesOfOne(selected);
                                    if(!Helpers.isIntInList(importedArray, index)){
                                        lblAnnouncer.setText("He can't move there.");
                                        selected = -1;
                                        selecting = true;
                                        return;
                                    }
                                    Chess.moveSpace(selected, index);
                                    Chess.clearPassants();
                                        try {
                                            lblAnnouncer.setText("Click a piece to select it.");
                                            nextTurn();
                                            if(Chess.check4check(Chess.board, Chess.whoseTurn)){
                                                if(Chess.didILose()){
                                                    lblAnnouncer.setText("Game over! "+Helpers.capitalFirstLetter(Chess.getOtherTeam(Chess.whoseTurn))+" wins!");
                                                    gameOver = true;
                                                }
                                            }
                                        } catch (IOException ex) {
                                            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                }

			    }
			});
		}
	}
}
