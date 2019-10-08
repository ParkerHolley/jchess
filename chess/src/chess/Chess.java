/* Code
 * - 8*8 array grid
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Chesspiece[] board  = new Chesspiece[8*8];
            //need an object type for a chess tile before this array will work
    }
}
