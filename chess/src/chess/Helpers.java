//Helper procs (static procs that return usable values elsewhere)

/*Documentation
    - Coords: Very top left cell (index 0) has coords (0,0).
      Very bottom right cell (index 63) has coords (8,8).

*/

package chess;

import java.util.Arrays; //not sure if this will be needed
import java.util.ArrayList;
import java.util.function.Predicate;

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
    
    public static String getCoords(int index){
        String output = "";
        output += Integer.toString(getX(index));
        output += ", ";
        output += Integer.toString(getY(index));
        return output;
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

    //moved into pieceClass
    /*public static ArrayList potentialMoves(pieceClass[] board, int pieceIndx){ //returns an array of potential movements
        ArrayList<Integer> potentials = new ArrayList<>();//variable length array (list)
        pieceClass piece = board[pieceIndx];
        for (char[] movement : piece.movements) {
            if(movement == null) break;
            int tempIndex = piece.index;
            int oldindex = tempIndex;
            for(int i = 0; i < movement.length; i++){
                char onemove = movement[i];
                oldindex = tempIndex;
                tempIndex = movIndex(tempIndex, onemove, 1);
                if(!isOOB(tempIndex, oldindex, movement[i])){
                    if(isOccupied(tempIndex, board)){
                        if(!board[pieceIndx].isPawn && isEnemyOccupied(pieceIndx, tempIndex, board)){
                            if(board[pieceIndx].manySteps || i == movement.length-1) potentials.add(tempIndex);
                        }
                        if("knight".equals(board[pieceIndx].type) && i != movement.length-1) continue;//knights jump over blocks
                        break;
                    }
                    else if(piece.manySteps || i == movement.length-1) potentials.add(tempIndex);
                } else {
                    break;
                }
            }
        }

        if(board[pieceIndx].isPawn){//handle pawnkills
            for (char killMovement : piece.pawnKills){
                int tempIndex = movIndex(pieceIndx, killMovement, 1);
                if(!isOOB(tempIndex, pieceIndx, killMovement) && isOccupied(tempIndex, board) && isEnemyOccupied(pieceIndx, tempIndex, board)){
                    potentials.add(tempIndex);
                }
            }
        }
        return potentials;
    }*/
    
    public static int[] intList2prim(ArrayList<Integer> toConvert){//returns Integer ArrayList as primitive int[]
        Integer[] convertStep = toConvert.toArray(new Integer[toConvert.size()]);
        int[] returnable = new int[convertStep.length];
        for(int i = 0; i < convertStep.length; i++){
            returnable[i] = convertStep[i];
        }
        return returnable;
    }
    
    public static ArrayList purgeListDupes(ArrayList<Integer> toPurge){//removes duplicate entries from Integer ArrayLists
        ArrayList<Integer> tempList = new ArrayList<>();
        toPurge.stream().filter(new PredicateImpl(tempList)).forEachOrdered((eachItem) -> {
            tempList.add(eachItem);
        });//i wrote this with a for loop, netbeans corrected it to use a stream. not sure how that works,
        return tempList;//but it seems that it does
    }
    
    public static boolean isIntInList(int[] theList, int theInt){
        return Arrays.stream(theList).anyMatch(i -> i == theInt);
    }
    
    public static int[] getAllOfColor(pieceClass[] board, String team){//returns an array of indexes of all pieces of a specified color
        int[] validIndexes = new int[BOARDSIZE*BOARDSIZE];//future proofed to allow for every tile to have a piece
        int addToIndex = 0;
        Arrays.fill(validIndexes, -1);//fill with a dummy value
        for (pieceClass piece:board) {
            if(piece.team.equals(team) && !piece.abstractPiece){
                validIndexes[addToIndex] = piece.index;
                addToIndex++;
            }
        }
        return validIndexes;
    }
    
    public static ArrayList getAllPotentialMoves(pieceClass[] board, String team){//gets all potential moves of a color
        ArrayList<Integer> allPotentials = new ArrayList<>();
        int[] allColors = getAllOfColor(board, team);
        for(int pieceIndex:allColors){
            if(pieceIndex != -1){//filter out dummy value
                ArrayList<Integer> movesOfOne = board[pieceIndex].potentialMoves(board);
                allPotentials.addAll(movesOfOne);
            }
        }
        allPotentials = purgeListDupes(allPotentials);
        return allPotentials;
    }
    
    public static int getKing(pieceClass[] board, String team){//returns the index of the white/black king
        int indexToReturn = -1;
        for(pieceClass piece:board){
            if(piece.isKing() && piece.team.equals(team)){
                indexToReturn = piece.index;
            }
        }
        if(indexToReturn == -1){
            System.out.print("King not found. Was searching for team " + team +"\n");
            System.exit(1);//kill the program
        }
        return indexToReturn;
    }
    
    public static boolean isOOB(int newindex, int oldindex, char dir){//returns true if an index is Out Of Bounds of the chessboard
        if(dir == 'S' || dir == 'w' || dir == 'W'){//this prevents going off the sides
            if(getX(oldindex) == 0 && getX(newindex) == 7){
                return true;
            }
        } else if(dir == 'N' || dir == 'e' || dir == 'E'){
            if(getX(oldindex) == 7 && getX(newindex) == 0){
                return true;
            }
        }
        
        return (newindex < 0 || newindex > (BOARDSIZE*BOARDSIZE)-1);//this prevents going off the top/bottom
    }
    
    public static boolean isOccupied(int index, pieceClass[] board){//checks if a board tile isn't blank
        return !(board[index].abstractPiece);
    }
    
    //overloaded to check for passants
    public static boolean isOccupied(int index, pieceClass[] board, char passantCheck){
        return !(board[index] instanceof blankPiece);
    }

    public static boolean isEnemyOccupied(int moverIndex, int index, pieceClass[] board){//checks if a board tile is occupied by an enemy
        String moverTeam = board[moverIndex].team;//should only be ran when we know we're moving to an occupied space
        return (!board[index].team.equals(moverTeam));
    }
    
    public static char[] getDiagsFromCardinal(char cardinal){
        char[] returnMe = new char[2];
        switch(cardinal){
            case 'n':
                returnMe[0] = 'W';
                returnMe[1] = 'N';
                return returnMe;
            case 'e':
                returnMe[0] = 'N';
                returnMe[1] = 'E';
                return returnMe;
            case 's':
                returnMe[0] = 'E';
                returnMe[1] = 'S';
                return returnMe;
            case 'w':
                returnMe[0] = 'S';
                returnMe[1] = 'W';
                return returnMe;
            default:
                returnMe[0] = 'o';
                returnMe[1] = 'o';
                System.out.println("INVALID CARDINAL IN Helpers.getDiagsFromCardinal");
                return returnMe;
        }
    }
    
    public static String assembleIcon(String initialIcon, String team, String name){
        return initialIcon + team + "set/" + name + ".png";
    }
    
    /*public static void main(String args[]){
        System.out.print( );//test functions here
    }*/

    private static class PredicateImpl implements Predicate<Integer> {

        private final ArrayList<Integer> tempList;

        public PredicateImpl(ArrayList<Integer> tempList) {
            this.tempList = tempList;
        }

        @Override
        public boolean test(Integer eachItem) {
            return !tempList.contains(eachItem);
        }
    }
}
