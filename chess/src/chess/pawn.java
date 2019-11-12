

package chess;

import java.util.ArrayList;

/**
 *
 * @author Parker
 */
public class pawn extends pieceClass{
    pieceClass linkedPassant;
    boolean unmoved;
    char direction;
    char[] killMoves = new char[2];
    
    public pawn(int index, String team, char direction){
        //call parent constructor
        super(index, team);
        
        //initialize defaults
        this.unmoved = true;
        this.name = "pawn";
        this.icon = Helpers.assembleIcon(this.icon, this.team, this.name);
        
        //manual set var
        this.direction = direction;
        this.manyMoves = false;
        this.specialKills = true;
        this.canBePromoted = true;
        
        //extrapolate from givens
        this.movements[0] = new char[]{this.direction};
        this.movements[1] = new char[]{this.direction, this.direction};
        this.killMoves = Helpers.getDiagsFromCardinal(this.direction);
    }
    
    @Override
    public void moved(int newIndex){
        if(this.unmoved){
            this.unmoved = false;
            if(Math.abs(newIndex - this.index) == Helpers.BOARDSIZE*2){
                Chess.newPassant(this.index, this.team, this);//be wary of this obj ref
                linkedPassant = Chess.board[this.index];
            }
            this.movements[1] = null;
        } else if(linkedPassant != null){
            Chess.blankSpace(linkedPassant.index);
            linkedPassant = null;
        }
        super.moved(newIndex);
    }
    
    @Override//pawns should have their own movement code since they are so unique
    public ArrayList<Integer> potentialMoves(pieceClass[] board){
        ArrayList<Integer> potentials = new ArrayList<>();//variable length array (list)
        potentials = super.potentialMoves(board);
        
        //handle killmoves
        for(char killDir:killMoves){
            int tempIndex = Helpers.movIndex(this.index, killDir, 1);
            if(!Helpers.isOOB(tempIndex, this.index, killDir) && Helpers.isOccupied(tempIndex, board, 'y')){
                if(Helpers.isEnemyOccupied(this.index, tempIndex, board)){
                    potentials.add(tempIndex);
                }
            }
        }
        
        return potentials;
    }

}
