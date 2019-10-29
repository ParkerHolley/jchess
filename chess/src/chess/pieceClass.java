/*
 Types of pieces
    - blank: No piece on tile
    - pawn (white and black may be different as they move oppositely)
    - queen
    - king
    - rook
    - bishop
    - knight

 Movements
    - a 2d array that contains 8 sub-arrays of potential movement directions
    - arrays are in the following order: n, e, s, w, ne, se, sw, nw
    - diagonals are represented as the capital chars N, E, S, W
    - the order is by clockwise cardinals starting from the top, then
      clockwise diagonals starting from the top right.
    - not all pieces will use all arrays. the queen/king would use all 8, as
      they can move in all directions. bishops would only use diagonals.
    - see getClassMovement below for examples of how this is set
*/
package chess;

/**
 *
 * @author Parker
 */
public class pieceClass {
    int index = 0;//location in controller array
    int xCoord = 0;//x coord in coordinate plane
    int yCoord = 0;//y coord
    String team = "black";//what team is the pawn on? (generally white or black)
    //needed for pawns: boolean unmoved = true;//has the pawn been moved before?
    char[][] movements  = new char[8][];//see above documentation
    boolean abstractPiece = false;//used for blank tiles, en passant
    //for pawns: char[] pawnKills = new char[2];//pawn diagonal kill moves
    
    public pieceClass(int index, String team){
        //set by user
        this.index = index;
        this.team = team;
        
        //set by helpers
        this.xCoord = Helpers.getX(index);
        this.yCoord = Helpers.getY(index);
        
        //set by type
        switch(this.type){
            case "knight":
                movements[0] = new char[]{'n','n','e'};
                movements[1] = new char[]{'n','n','w'};
                movements[2] = new char[]{'e','e','n'};
                movements[3] = new char[]{'e','e','s'};
                movements[4] = new char[]{'s','s','e'};
                movements[5] = new char[]{'s','s','w'};
                movements[6] = new char[]{'w','w','s'};
                movements[7] = new char[]{'w','w','n'};
                break;
            case "queen"://7 tiles in any dir, 1 less than board width
                movements[0] = new char[]{'n','n','n','n','n','n','n','n'};
                movements[1] = new char[]{'N','N','N','N','N','N','N','N'};
                movements[2] = new char[]{'e','e','e','e','e','e','e','e'};
                movements[3] = new char[]{'E','E','E','E','E','E','E','E'};
                movements[4] = new char[]{'s','s','s','s','s','s','s','s'};
                movements[5] = new char[]{'S','S','S','S','S','S','S','S'};
                movements[6] = new char[]{'w','w','w','w','w','w','w','w'};
                movements[7] = new char[]{'W','W','W','W','W','W','W','W'};
                break;
                //ensure movement checker doesnt let it move OOB. same for rook/bishop
            case "king":
                movements[0] = new char[]{'n'};
                movements[1] = new char[]{'N'};
                movements[2] = new char[]{'e'};
                movements[3] = new char[]{'E'};
                movements[4] = new char[]{'s'};
                movements[5] = new char[]{'S'};
                movements[6] = new char[]{'w'};
                movements[7] = new char[]{'W'};
                break;
                //needs special handling to not move into check(mate)
            case "wpawn"://white pawn
                //this.pawnKills = new char[]{'W','N'};
                
                movements[0] = new char[]{'n'};
                movements[1] = new char[]{'n','n'};
                break;
            case "bpawn"://black pawn
                //this.pawnKills = new char[]{'E','S'};
                
                movements[0] = new char[]{'s'};
                movements[1] = new char[]{'s','s'};
                break;
            case "rook":
                movements[0] = new char[]{'n','n','n','n','n','n','n','n'};
                movements[1] = new char[]{'e','e','e','e','e','e','e','e'};
                movements[2] = new char[]{'s','s','s','s','s','s','s','s'};
                movements[3] = new char[]{'w','w','w','w','w','w','w','w'};
                break;
            case "bishop":
                movements[0] = new char[]{'N','N','N','N','N','N','N','N'};
                movements[1] = new char[]{'E','E','E','E','E','E','E','E'};
                movements[2] = new char[]{'S','S','S','S','S','S','S','S'};
                movements[3] = new char[]{'W','W','W','W','W','W','W','W'};
                break;
            default:
                //blanks do not have movement
        }
    }
    
    public void moved(int newIndex){
        this.index = newIndex;
    }
    
    public abstract int[] potentialMoves();//basically move the current helper function into this
    
    public void onDeath(){//ran when a piece is killed by an enemy. blanks the tile the piece was on
        Chess.blankSpace(this.index);
    }
    
    public boolean isKing(){//useful later for checkmate calcs
        return false;
    }
    
}
