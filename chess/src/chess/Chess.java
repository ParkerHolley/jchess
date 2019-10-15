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
        for(int i = 0; i < board.length; i++){
            board[i] = new pieceClass(STARTINGPOSITIONS[i], i);
        }
    }
    
    public static void moveSpace(int fromIndx, int toIndx){//move from an index to another
        board[toIndx] = board[fromIndx];//piece clones to new index
        board[toIndx].index = toIndx;//update its internal index
        board[fromIndx] = new pieceClass("b", fromIndx);//replace old space with blank piece
    }
    
    public static void main(String[] args) {//main function (will likely be moved to UI)
        setUpBoard();
        
        //testing code
        System.out.println(board[Helpers.coord2index(5, 7)].type);
        System.out.println(board[Helpers.coord2index(5, 5)].type);
        moveSpace(Helpers.coord2index(5, 7), Helpers.coord2index(5, 5));
        System.out.println(board[Helpers.coord2index(5, 7)].type);
        System.out.println(board[Helpers.coord2index(5, 5)].type);
    }
}
