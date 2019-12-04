package chess;

/**
 *
 * @author Parker
 */
public class bishop extends pieceClass{
    
    
    public bishop(int index, String team){
        //parent constructor
        super(index, team);
        
        //by type
        this.name = "bishop";
        this.movements[0] = new char[]{'N','N','N','N','N','N','N','N'};
        this.movements[1] = new char[]{'E','E','E','E','E','E','E','E'};
        this.movements[2] = new char[]{'S','S','S','S','S','S','S','S'};
        this.movements[3] = new char[]{'W','W','W','W','W','W','W','W'};
    }
}
