//Abstract piece that is created by a pawn and can be killed en passant

package chess;

/**
 *
 * @author Parker
 */
public class enPassantPiece extends pieceClass{
    
    pieceClass linkedPawn;
    
    public enPassantPiece(int index, String team, pieceClass passantPawn){
        super(index, team);
        this.name = "passant";
        this.abstractPiece = true;
        this.linkedPawn = passantPawn;
        this.icon = Helpers.assembleIcon(this.icon, this.team, this.name);
    }
    
    @Override
    public void onDeath(){
        linkedPawn.onDeath();
        super.onDeath();
    }
}
