/*
 Types of pieces
    - blank: No piece on tile
    - pawn
    - queen
    - king
    - rook
    - bishop
    - knight

 Movements
    - a 2d array that contains 8 sub-arrays of potential movement directions
    - arrays are in the following order: n, s, e, w, ne, se, sw, nw
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
    String type = "blank";//type of piece. see types above
    int index = 0;//location in controller array
    int xCoord = 0;//x coord in coordinate plane
    int yCoord = 0;//y coord
    char[][] movements  = new char[8][];//see above documentation 
    
    public pieceClass(String type, int index){
        this.type = type;
        this.index = index;
    }
    
    public char[][] getClassMovement(){
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
                return movements;
            case "queen"://7 tiles in any dir, 1 less than board width
                movements[0] = new char[]{'n','n','n','n','n','n','n','n'};
                movements[1] = new char[]{'N','N','N','N','N','N','N','N'};
                movements[2] = new char[]{'e','e','e','e','e','e','e','e'};
                movements[3] = new char[]{'E','E','E','E','E','E','E','E'};
                movements[4] = new char[]{'s','s','s','s','s','s','s','s'};
                movements[5] = new char[]{'S','S','S','S','S','S','S','S'};
                movements[6] = new char[]{'w','w','w','w','w','w','w','w'};
                movements[7] = new char[]{'W','W','W','W','W','W','W','W'};
                return movements;
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
                return movements;
                //needs special handling to not move into check(mate)
            case "whitepawn":
                movements[0] = new char[]{'e'};
                movements[0] = new char[]{'e','e'};
                return movements;
            case "blackpawn":
                movements[0] = new char[]{'w'};
                movements[0] = new char[]{'w','w'};
                return movements;
                //needs special handling for diagonal kills
            case "rook":
                movements[0] = new char[]{'n','n','n','n','n','n','n','n'};
                movements[1] = new char[]{'e','e','e','e','e','e','e','e'};
                movements[2] = new char[]{'s','s','s','s','s','s','s','s'};
                movements[3] = new char[]{'w','w','w','w','w','w','w','w'};
                return movements;
            case "bishop":
                movements[0] = new char[]{'N','N','N','N','N','N','N','N'};
                movements[1] = new char[]{'E','E','E','E','E','E','E','E'};
                movements[2] = new char[]{'S','S','S','S','S','S','S','S'};
                movements[3] = new char[]{'W','W','W','W','W','W','W','W'};
            default:
                return new char[0][0];//blanks are dummies that cant move
        }
    }
}
