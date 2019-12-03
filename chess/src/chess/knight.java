package chess;

/**
 *
 * @author Parker
 */
public class knight extends pieceClass{
    
    
    public knight(int index, String team){
        //parent constructor
        super(index, team);
        
        //by type
        this.movements[0] = new char[]{'n','n','e'};
        this.movements[1] = new char[]{'n','n','w'};
        this.movements[2] = new char[]{'e','e','n'};
        this.movements[3] = new char[]{'e','e','s'};
        this.movements[4] = new char[]{'s','s','e'};
        this.movements[5] = new char[]{'s','s','w'};
        this.movements[6] = new char[]{'w','w','s'};
        this.movements[7] = new char[]{'w','w','n'};
        
        this.jumpsOver = true;
        this.manyMoves = false;
        
        this.name = "knight";
        this.icon = Helpers.assembleIcon(this.icon, this.team, this.name);
    }
}
