//Abstract piece that is created by a pawn and can be killed en passant

package chess;

/**
 *
 * @author Parker
 */
public class enPassantPiece extends pieceClass{
    
    boolean safedeath = false;
    
    public enPassantPiece(int index, String team, pieceClass passantPawn){
        super(index, team);
        this.name = "passant";
        this.abstractPiece = true;
        this.linkedPiece = passantPawn;
    }
    
    @Override
    public void onDeath(){
        if(linkedPiece != null && !safedeath) linkedPiece.onDeath();
        super.onDeath();
    }
    
    @Override
    public void beforeDeath(){
        safedeath = true;
        onDeath();
    }
}
