package game;

import java.util.Scanner	;

public class Main {

	public static void main(String[] args) {
	
		Scanner in = new Scanner(System.in);
		
		char[][] gameBoard = new char[8][8];
		
		int moveDir = 0;
		int[] temp = new int[4];
		
		boolean playingChess = true;
		
		int xPos = 0,
				yPos = 0,
				prevX = 0,
				prevY = 0;
		
		//initial piece position
		gameBoard[0][0] = 'x';
		
		//place 'o' on open spots on board
		for(int x = 0; x<gameBoard.length; x++) {
			
			for(int y =0; y<gameBoard.length; y++) {
				
				if(gameBoard[x][y] != 'x') {
					gameBoard[x][y] = 'o';
				}
				
			}
			
		}
		
		
		//display board
		for(int x = 0; x<gameBoard.length; x++) {
			System.out.println();
			for(int y = 0; y<gameBoard.length; y++) {
				
				System.out.print(" " + gameBoard[x][y] + " ");
				
			}
			
		}
		
		
		System.out.println("\n1 = North \n2 = South \n3 = West \n4 = East \n-1 = End Game");
		
		//gameLoop
		
		do {
			System.out.println("Please enter a movement direction or -1 to end the game.");
			moveDir = in.nextInt();
			
			if(moveDir == 1) {
				temp = moveNorth(gameBoard);
				gameBoard[temp[0]][temp[1]] = 'x';
				gameBoard[temp[2]][temp[3]] = 'o';
			}
			else if(moveDir == 2) {
				temp = moveSouth(gameBoard);
				gameBoard[temp[0]][temp[1]] = 'x';
				gameBoard[temp[2]][temp[3]] = 'o';
			}
			else if(moveDir == 3) {
				temp = moveWest(gameBoard);
				gameBoard[temp[0]][temp[1]] = 'x';
				gameBoard[temp[2]][temp[3]] = 'o';
			}
			else if(moveDir == 4) {
				temp = moveEast(gameBoard);
				gameBoard[temp[0]][temp[1]] = 'x';
				gameBoard[temp[2]][temp[3]] = 'o';
			}
			else if(moveDir == -1) {
				playingChess = false;
			}
			else {
				System.out.println("No command found");
			}
			
			for(int x = 0; x<gameBoard.length; x++) {
				System.out.println();
				for(int y = 0; y<gameBoard.length; y++) {
					
					System.out.print(" " + gameBoard[x][y] + " ");
					
				}
				
			}
			
		}while(playingChess);
		
	}

	public static int[] moveNorth (char[][] board) {
		
		int[] piecePos = new int[4];
		
		boolean foundPiece = false;
		
		for(int x = 0; x<board.length; x++) {
			
			if(foundPiece) {
				break;
			}
			
			for(int y = 0; y < board.length; y++) {
				
				if(board[x][y] == 'x') {
					
					piecePos[0] = x;
					piecePos[2] = x;
					piecePos[3] = y;
										
					if(!((y-1)<0)) {
						piecePos[1] = y-1;
						foundPiece = true;
						break;
					}
				}
				
			}
			
		}
			
		return piecePos;
		
	}
	
	public static int[] moveSouth(char[][] board) {
		
		int[] piecePos = new int[4];
		
		boolean foundPiece = false;
		
		for(int x = 0; x<board.length; x++) {
			
			if(foundPiece) {
				break;
			}
			
			for(int y = 0; y < board.length; y++) {
				
				if(board[x][y] == 'x') {
					
					piecePos[0] = x;
					piecePos[2] = x;
					piecePos[3] = y;
										
					if(!((y+1)>8)) {
						piecePos[1] = y+1;
						foundPiece = true;
						break;
					}
				}
				
			}
			
		}
		
		return piecePos;
		
	}
	
	public static int[] moveWest(char[][] board) {
		
		int[] piecePos = new int[4];
		
		boolean foundPiece = false;
		
		for(int x = 0; x<board.length; x++) {
			
			if(foundPiece) {
				break;
			}
			
			for(int y = 0; y < board.length; y++) {
				
				if(board[x][y] == 'x') {
					
					piecePos[1] = y;
					piecePos[2] = x;
					piecePos[3] = y;
										
					if(!((x-1)<0)) {
						piecePos[0] = x-1;
						foundPiece = true;
						break;
					}
				}
				
			}
			
		}
		
		return piecePos;
		
	}
	
	public static int[] moveEast(char[][] board) {
		
		int[] piecePos = new int[4];
		
		boolean foundPiece = false;
		
		for(int x = 0; x<board.length; x++) {
			
			if(foundPiece) {
				break;
			}
			
			for(int y = 0; y < board.length; y++) {
				
				if(board[x][y] == 'x') {
					
					piecePos[1] = y;
					piecePos[2] = x;
					piecePos[3] = y;
					
					if(!((x+1)>8)){
						piecePos[0] = x+1;
						foundPiece = true;
						break;
					}
					
				}
				
			}
			
		}
		
		return piecePos;
		
	}
	
}
