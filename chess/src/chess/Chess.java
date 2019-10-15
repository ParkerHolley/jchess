/* Code
 * - 8*8 array grid (defacto 7*7 that starts at 0)
 * - s = +8, n = -8, e = +1, w = -1, diagonals will be macros of cardinals
 * - if we use coords, % for row, intdiv for column
 * - Class for each piece (one for blank space? or just nulls)
 * - Function in each class:
 * - - to determine potential output indexes (account for blocking)
 * - - - very complicated. check each step for a block. keep an array of possible
         (unblocked) moves.
 * - - - knights in particular are going to be especially weird
 * - - - pawns killing diagonally will be a special case
 * - - setter for index (used internally in replace func)
 * - - "replace" function (for when the piece is killed)
 * - - move to dead piece bank
 * - A function that returns if a space is occupied. if occupied by the same team,
     block movement. by opposite color, allow kill.
 * - Two players, alternate between white & black
 * - Function that runs every pieces potential move functions at the start of each turn

 * Interface
 * - 8*8 tile grid (like real chess)
 * - Graphics for each piece (black and white sets)
 * - Highlight graphics (if its a valid move, blue. if its a kill, red)
 * - String under the board. Declares whose turn, if there is check, winner
 * - dead piece bank (container obj, 1 on each side)
 * - - will need code to pull graphics of killed pieces. not sure how this
       will work quite yet. need to look at how graphics are handled

 */
package chess;

/**
 *
 * @author Parker
 */

public class Chess {
    //define helping arrays and vars
    public static final String[] STARTINGPOSITIONS = {"rook", "knight", "bishop", "queen", "king", "bishop", "knight", "rook", "bpawn", "bpawn",
    "bpawn","bpawn","bpawn","bpawn","bpawn","bpawn","b","b","b","b","b","b","b","b","b","b","b","b","b","b","b","b","b","b",
    "b","b","b","b","b","b","b","b","b","b","b","b","b","b","wpawn","wpawn","wpawn","wpawn","wpawn","wpawn","wpawn","wpawn",
    "rook", "knight", "bishop", "queen", "king", "bishop", "knight", "rook"};
    
    //declare the board
    public static pieceClass[] board  = new pieceClass[8*8];
    
    
    
    public static void setUpBoard(){//initiailize the board
        boolean isWhite = false;
        for(int i = 0; i < board.length; i++){
            if(!isWhite && i > 15) isWhite = true;//all black pieces start in positions 0-15
            board[i] = new pieceClass(STARTINGPOSITIONS[i], i, isWhite, true);
        }
    }
    
    public static void moveSpace(int fromIndx, int toIndx){//move from an index to another
        board[toIndx] = board[fromIndx];//piece clones to new index
        board[toIndx].moved(toIndx);//update its internal index, its unmoved bool, etc.
        board[fromIndx] = new pieceClass("b", fromIndx, false, false);//replace old space with blank piece
    }
    
    public static void main(String[] args) {//main function (will likely be moved to UI)
        setUpBoard();
        
        //testing code
        
        /* Read what pieces are at 5,7 and 5,5. Move 5,7 to 5,5. Read again.
        System.out.println(board[Helpers.coord2index(5, 7)].type);
        System.out.println(board[Helpers.coord2index(5, 5)].type);
        moveSpace(Helpers.coord2index(5, 7), Helpers.coord2index(5, 5));
        System.out.println(board[Helpers.coord2index(5, 7)].type);
        System.out.println(board[Helpers.coord2index(5, 5)].type);
        */
        
        /* Read if the pieces at 0,6 and 0,1 are white. See what pieces 0,6 can kill.
        System.out.println("0,6 is white? "+board[Helpers.coord2index(0, 6)].isWhite);//expecting true
        System.out.println("0,1 is white? "+board[Helpers.coord2index(0, 1)].isWhite);//expecting false
        System.out.println("Can 0,6 kill 0,1? "+Helpers.isEnemyOccupied(Helpers.coord2index(0,6), Helpers.coord2index(0,1), board));//expecting true
        System.out.println("Can 0,6 kill 0,7? "+Helpers.isEnemyOccupied(Helpers.coord2index(0,6), Helpers.coord2index(0,7), board));//expecting false
        */

        /* Make 0,6 into a black piece. See that it is killable by the rook. Kill it with the rook.
        board[Helpers.coord2index(0,6)].isWhite = false;
        System.out.println(Helpers.potentialMoves(board, Helpers.coord2index(0,6)));
        System.out.println(Helpers.potentialMoves(board, Helpers.coord2index(0,7)));
        moveSpace(Helpers.coord2index(0, 7), Helpers.coord2index(0, 6));
        System.out.println(Helpers.potentialMoves(board, Helpers.coord2index(0,6)));
        */
        
        /* Knight movement check for the bottom-left-most knight.
        System.out.println("Knight at 1,7 moves: "+Helpers.potentialMoves(board,Helpers.coord2index(1,7)));
        System.out.println(Helpers.getCoords(42));
        System.out.println(Helpers.getCoords(40));
        */
        
        /* Spawn a rook at 1,4 and check its movement.
        System.out.println(Helpers.coord2index(1,4));
        board[Helpers.coord2index(1, 4)] = new pieceClass("rook", Helpers.coord2index(1, 4), false, false);
        System.out.println(Helpers.potentialMoves(board, Helpers.coord2index(1,4)));
        */
        
        /* Spawn a black unit at 2,5 and 4,5. See how the pawns below it are able to kill them.
        board[Helpers.coord2index(2, 5)] = new pieceClass("queen", Helpers.coord2index(2, 5), false, false);//indx 42
        board[Helpers.coord2index(4, 5)] = new pieceClass("queen", Helpers.coord2index(4, 5), false, false);//indx 44
        System.out.println(Helpers.potentialMoves(board, Helpers.coord2index(1,6)));//49
        System.out.println(Helpers.potentialMoves(board, Helpers.coord2index(2,6)));//50
        System.out.println(Helpers.potentialMoves(board, Helpers.coord2index(3,6)));//51
        System.out.println(Helpers.potentialMoves(board, Helpers.coord2index(4,6)));//52
        board[53].moved(53);//no longer unmoved; after first move, pawns can only move forward 1 tile
        System.out.println(Helpers.potentialMoves(board, Helpers.coord2index(5,6)));//53
        */
    }
}
