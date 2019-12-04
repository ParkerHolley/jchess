package chess;

/**
 *
 * @author Parker
 */
public class rook extends pieceClass{
    
    
    public rook(int index, String team){
        //parent constructor
        super(index, team);
        
        //by type
        this.movements[0] = new char[]{'n','n','n','n','n','n','n','n'};
        this.movements[1] = new char[]{'e','e','e','e','e','e','e','e'};
        this.movements[2] = new char[]{'s','s','s','s','s','s','s','s'};
        this.movements[3] = new char[]{'w','w','w','w','w','w','w','w'};
        
        this.name = "rook";
    }
}
