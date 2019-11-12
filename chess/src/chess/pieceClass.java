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

import java.util.ArrayList;

/**
 *
 * @author Parker
 */
public class pieceClass {
    //set direcetly by constructor args
    int index = 0;//location in controller array
    String team = "black";//what team is the pawn on? (generally white or black)
    //set indirectly by constructor args
    int xCoord = 0;//x coord in coordinate plane
    int yCoord = 0;//y coord
    
    //set in constructors per type
    String icon = "/resources/";//used for icon construction, set dynamically later
    String name = "parent";//internal name of piece. mostly used for icon construction
    char[][] movements  = new char[8][];//see above documentation
    boolean jumpsOver = false;//does the piece ignore obstacles except for its destination?
    boolean manyMoves = true;//is the piece able to stop anywhere on its path? (like rooks)
    boolean specialKills = false;//is the pawn unable to kill normally (like pawns)?
    boolean abstractPiece = false;//used for blank tiles, en passant. true for this parent class but false by default.
    boolean canBePromoted = false;//can this pawn be promoted when it reaches the enemy back line?
    
    public pieceClass(int index, String team){
        //set by user
        this.index = index;
        this.team = team;
        
        //set by helpers
        this.xCoord = Helpers.getX(index);
        this.yCoord = Helpers.getY(index);
    }
    
    public void moved(int newIndex){
        this.index = newIndex;
    }
    
    public ArrayList<Integer> potentialMoves(pieceClass[] board){
        ArrayList<Integer> potentials = new ArrayList<>();//variable length array (list)
        for (char[] movement : this.movements) {
            if(movement == null) break;
            int tempIndex = this.index;
            int oldindex = tempIndex;
            for(int i = 0; i < movement.length; i++){
                char onemove = movement[i];
                boolean lastStep = (i == movement.length-1);
                oldindex = tempIndex;
                tempIndex = Helpers.movIndex(tempIndex, onemove, 1);
                if(!Helpers.isOOB(tempIndex, oldindex, movement[i])){
                    if(Helpers.isOccupied(tempIndex, board)){
                        if(!this.specialKills && Helpers.isEnemyOccupied(this.index, tempIndex, board)){
                            if(this.manyMoves || lastStep) potentials.add(tempIndex);
                        }
                        if(this.jumpsOver && !lastStep) continue;
                        break;
                    }
                    else if(this.manyMoves || lastStep) potentials.add(tempIndex);
                } else {
                    break;
                }
            }
        }
        return potentials;
    }
    
    public void onDeath(){//ran when a piece is killed by an enemy. blanks the tile the piece was on
        Chess.blankSpace(this.index);
    }
    
    public boolean isKing(){//useful later for checkmate calcs
        return false;
    }

}
