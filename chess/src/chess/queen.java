package chess;

/**
 *
 * @author Parker
 */
public class queen extends pieceClass{
    
    
    public queen(int index, String team){
        //parent constructor
        super(index, team);
        
        //by type
        this.movements[0] = new char[]{'n','n','n','n','n','n','n','n'};
        this.movements[1] = new char[]{'N','N','N','N','N','N','N','N'};
        this.movements[2] = new char[]{'e','e','e','e','e','e','e','e'};
        this.movements[3] = new char[]{'E','E','E','E','E','E','E','E'};
        this.movements[4] = new char[]{'s','s','s','s','s','s','s','s'};
        this.movements[5] = new char[]{'S','S','S','S','S','S','S','S'};
        this.movements[6] = new char[]{'w','w','w','w','w','w','w','w'};
        this.movements[7] = new char[]{'W','W','W','W','W','W','W','W'};
        
        this.name = "queen";
    }
}
