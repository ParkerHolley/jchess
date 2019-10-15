//Helper procs (static procs that return usable values elsewhere)

/*Documentation
    - Coords: Very top left cell (index 0) has coords (0,0).
      Very bottom right cell (index 63) has coords (8,8).

*/

package chess;

import java.util.ArrayList;

/**
 *
 * @author Parker
 */
public class Helpers {
    public static final int BOARDSIZE = 8;//chess board is always 8 tiles wide
    
    public static int getX(int index){//array index -> x coord
        return(index%BOARDSIZE);
    }
    
    public static int getY(int index){//array index -> y coord
        return(int)(Math.floor(index/BOARDSIZE));
    }
    
    public static int coord2index(int x, int y){//coords -> index
        return x + (y*BOARDSIZE);
    }
    
    public static int movIndex(int index, char dir, int steps){//old index + direction to move -> new index.
        if(steps < 1){//set steps to mov multiple times in the same dir. otherwise, always move at least once
            steps = 1;
        }
        int modifier = 0;//flat added to index
        switch(dir){
            case 'n':
                modifier = BOARDSIZE*-1;
                break;
            case 'e':
                modifier = 1;
                break;
            case 's':
                modifier = BOARDSIZE;
                break;
            case 'w':
                modifier = -1;
                break;
            case 'N'://capital dirs are diagonals, starting at NE and going clockwise. this is NE
                modifier = (BOARDSIZE*-1)+1;
                break;
            case 'E'://SE
                modifier = BOARDSIZE+1;
                break;
            case 'S'://SW
                modifier = BOARDSIZE-1;
                break;
            case 'W'://NW
                modifier = (BOARDSIZE*-1)-1;
                break;
            default:
                System.out.print("Bad dir in mov helper");
                System.exit(1);//kill the program
        }
        for(int i = 0; i < steps; i++){
            index += modifier;
        }
        return index;
    }
    
    public static int movCoords(int x, int y, char dir, int steps){
        return movIndex(coord2index(x,y), dir, steps);//macro to do mov code with coords
    }

    public static ArrayList potentialMoves(pieceClass piece){ //returns an array of potential movements
        ArrayList<Integer> potentials = new ArrayList<>();//variable length array (list)
        for (char[] movement : piece.movements) {
            int tempindex = piece.index;
            for(int i = 0; i < movement.length; i++){
                char onemove = movement[i];
                tempindex = movIndex(tempindex, onemove, 1);
                if(!isOOB(tempindex)){//TODO: && !blocked()
                    if(piece.manySteps || i == movement.length-1) potentials.add(tempindex);
                }
            }
        }
        return potentials;//may need refactor to also output list of potential kills
    }
    
    public static boolean isOOB(int index){//checks if an index is Out Of Bounds of the controller array
        return !(index < 0 || index > (BOARDSIZE*BOARDSIZE)-1);
    }
    
    public static boolean isOccupied(int index, pieceClass[] board){//checks if a board tile isn't blank
        return !(board[index].type.equals("b"));
    }
    
    /*public static void main(String args[]){
        System.out.print( );//test functions here
    }*/
}
