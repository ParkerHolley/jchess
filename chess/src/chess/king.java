package chess;

/**
 *
 * @author Parker
 */
public class king extends pieceClass{
    
    
    public king(int index, String team){
        //parent constructor
        super(index, team);
        
        //by type
        this.movements[0] = new char[]{'n'};
        this.movements[1] = new char[]{'N'};
        this.movements[2] = new char[]{'e'};
        this.movements[3] = new char[]{'E'};
        this.movements[4] = new char[]{'s'};
        this.movements[5] = new char[]{'S'};
        this.movements[6] = new char[]{'w'};
        this.movements[7] = new char[]{'W'};
        
        this.name = "king";
    }
    
    @Override
    public boolean isKing(){
        return true;
    }
    
    @Override
    public void onDeath(){
        System.out.print(this.team + " king was killed! This shouldn't happen...\n");
        //System.exit(1);//kill the program
    }
}
