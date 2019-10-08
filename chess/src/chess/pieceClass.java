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
            default:
                return new char[0][0];//blanks are dummies that cant move
        }
    }
}
