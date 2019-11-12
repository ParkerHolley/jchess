/* Code
 * - 8*8 array grid (defacto 7*7 that starts at 0)
 * - s = +8, n = -8, e = +1, w = -1, diagonals will be macros of cardinals
 * - if we use coords, % for row, intdiv for column
 * - Class for each piece (one for blank space? or just nulls)
 * - Function in each class:
 * - - to determine potential output indexes (account for blocking)
 * - - - very complicated. check each step for a block. keep an array of possible
         (unblocked) moves.
 * - - - knights in particular are going to be especially weird
 * - - - pawns killing diagonally will be a special case
 * - - setter for index (used internally in replace func)
 * - - "replace" function (for when the piece is killed)
 * - - move to dead piece bank
 * - A function that returns if a space is occupied. if occupied by the same team,
     block movement. by opposite color, allow kill.
 * - Two players, alternate between white & black
 * - Function that runs every pieces potential move functions at the start of each turn

 * Interface
 * - 8*8 tile grid (like real chess)
 * - Graphics for each piece (black and white sets)
 * - Highlight graphics (if its a valid move, blue. if its a kill, red)
 * - String under the board. Declares whose turn, if there is check, winner
 * - dead piece bank (container obj, 1 on each side)
 * - - will need code to pull graphics of killed pieces. not sure how this
       will work quite yet. need to look at how graphics are handled
 */

package chess;

import java.util.Scanner;
import java.util.Arrays;

/**
 *
 * @author Parker
 */

public class Chess {
    //define helping arrays and vars
    public static final String[] STARTINGPOSITIONS = {"rook", "knight", "bishop", "queen", "king", "bishop", "knight", "rook", "pawns", "pawns",
    "pawns","pawns","pawns","pawns","pawns","pawns","b","b","b","b","b","b","b","b","b","b","b","b","b","b","b","b","b","b",
    "b","b","b","b","b","b","b","b","b","b","b","b","b","b","pawnn","pawnn","pawnn","pawnn","pawnn","pawnn","pawnn","pawnn",
    "rook", "knight", "bishop", "queen", "king", "bishop", "knight", "rook"};
    
    public static final String[] STARTINGTEAMS = {"black", "black", "black", "black", "black", "black", "black", "black", 
    "black", "black", "black", "black", "black", "black", "black", "black", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 
    "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "white", "white", "white", "white", "white", "white", "white", "white", "white", 
    "white", "white", "white", "white", "white", "white", "white"};
    
    //team tracking vars. can have variable # of teams
    public static String teams[] = {"white", "black"};
    public static int backlines[] = {Helpers.BOARDSIZE-1, 0};
    public static char backlineXorY[] = {'y', 'y'};
    public static String whoseTurn;
    public static int numberOfTeams;
    public static int turnTracker = 0;
    
    public static boolean inCheck = false;
    public static boolean checkmate = false;
    
    //declare the board
    public static pieceClass[] board  = new pieceClass[8*8];
    
    public static void newPiece(String type, int index, String team){//spawn pieces by type
        if(type.length() == 5 && type.substring(0,4).equals("pawn")){//accepts pawnn, pawne, pawns, pawnw for each cardinal
            char dir = type.charAt(type.length() - 1);
            board[index] = new pawn(index, team, dir);
            return;
        }
        
        switch(type){
            case "b":
                board[index] = new blankPiece(index, "");
                break;
            case "rook":
                board[index] = new rook(index, team);
                break;
            case "knight":
                board[index] = new knight(index, team);
                break;
            case "bishop":
                board[index] = new bishop(index, team);
                break;
            case "queen":
                board[index] = new queen(index, team);
                break;
            case "king":
                board[index] = new king(index, team);
                break;
            default:
                System.out.println("INVALID TYPE "+type+" SPAWNED IN Chess.newPiece");
        }
    }
    
    //overloaded proc for passant pieces
    public static void newPiece(String type, int index, String team, pieceClass linkedPiece){
        switch(type){
            case "passant":
                board[index] = new enPassantPiece(index, team, linkedPiece);
                break;
            default:
                System.out.println("INVALID TYPE SPAWNED IN Chess.newPiece overloaded for passant");
        }
    }
    
    
    public static void setUpBoard(){//initiailize the board
        turnTracker = 0;
        whoseTurn = teams[0];
        numberOfTeams = teams.length;
        for(int i = 0; i < board.length; i++){
            newPiece(STARTINGPOSITIONS[i], i, STARTINGTEAMS[i]);
        }
    }
    
    public static void moveSpace(int fromIndx, int toIndx){//move from an index to another
        board[toIndx].onDeath();
        board[toIndx] = board[fromIndx];//piece clones to new index
        board[toIndx].moved(toIndx);//update its internal index, its unmoved bool, etc.
        blankSpace(fromIndx);//replace old space with blank piece
        checkForPromotion(toIndx);
    }
    
    public static void checkForPromotion(int toIndex){
        for(int i = 0; i < teams.length; i++){
            if(!board[toIndex].team.equals(teams[i])){
                int useCoord;
                if(backlineXorY[i] == 'y') useCoord = Helpers.getY(toIndex);
                else useCoord = Helpers.getX(toIndex);
                if(useCoord == backlines[i]){
                    promote(toIndex);
                }
            }
        }
    }
    
    public static void promote(int toIndex){
        System.out.println("Unfinished promotion code reached");
        //TODO replace piece with queen (or piece of users choice eventually)
    }
    
    public static void blankSpace(int index){
        board[index] = new blankPiece(index, "");
    }
    
    public static void newPassant(int index, String team, pieceClass spawner){//i would like to make this proc and the one above into a general spawnPiece()
        board[index] = new enPassantPiece(index, team, spawner);
    }
    
    public static void nextTurn(){//simply flips the turn string like a boolean
        turnTracker++;
        if(turnTracker > numberOfTeams-1) turnTracker = 0;
        whoseTurn = teams[turnTracker];
    }
    
    public static void clearPassants(){
        for(pieceClass lookAt:board){
            if(lookAt instanceof enPassantPiece && !lookAt.team.equals(whoseTurn)){
                lookAt.beforeDeath();
            }
        }
    }

//ASCII-playable functions
    public static void displayBoard(int[] targets){
        for(int i = 0; i < board.length; i++){
            char toPrint = '☐';
            if(board[i].team.equals("white")){
                switch(board[i].name){
                    case "rook":
                        toPrint = '♖';
                        break;
                    case "knight":
                        toPrint = '♘';
                        break;
                    case "bishop":
                        toPrint = '♗';
                        break;
                    case "queen":
                        toPrint = '♕';
                        break;
                    case "king":
                        toPrint = '♔';
                        break;
                    case "pawn":
                        toPrint = '♙';
                        break;
                    default:
                        break;
                }
            } else {//black set
                switch(board[i].name){
                    case "rook":
                        toPrint = '♜';
                        break;
                    case "knight":
                        toPrint = '♞';
                        break;
                    case "bishop":
                        toPrint = '♝';
                        break;
                    case "queen":
                        toPrint = '♛';
                        break;
                    case "king":
                        toPrint = '♚';
                        break;
                    case "pawn":
                        toPrint = '♟';
                        break;
                    default:
                        break;
                }
            }
            if(targets[i] == i) toPrint = '☒';
            System.out.print(toPrint);
            if(Helpers.getX(i) == 7) System.out.println("");
        }
    }
    
    public static void main(String[] args) {//main function (will likely be moved to UI)
        //initialize functions and variables
        setUpBoard();
        
        //ASCII playable code
        Scanner input = new Scanner(System.in);
        
        int[] targets = new int[Helpers.BOARDSIZE*Helpers.BOARDSIZE];//most potential moves in 1 move is 27, but this must match the len of the board
        
        
        System.out.println("Player, please note: Coords start at (0,0), at the top left of the board. The bottom right tile is at 7,7.");
        while(true){
            Arrays.fill(targets,-1);
            displayBoard(targets);
            System.out.printf("It is %s's turn.\nSelect a piece to move by entering an X coord, then a Y coord.\n",whoseTurn);
            int x = input.nextInt();
            int y = input.nextInt();
            int selected = Helpers.coord2index(x, y);
            if(!board[selected].team.equals(whoseTurn) || board[selected].abstractPiece){
                System.out.println("You selected an invalid piece. Try again.");
                continue;
            }
            int[] importedArray = Helpers.intList2prim(board[selected].potentialMoves(board));
            String validCoordsMessage = "Valid moves are at these coords:\n";
            for(int targetIndex:importedArray){
                targets[targetIndex] = targetIndex;
                validCoordsMessage += Helpers.getCoords(targetIndex)+"\n";
            }
            displayBoard(targets);
            System.out.print(validCoordsMessage);
            System.out.println("Select a tile to move to by entering an X and then a Y coord.");
            x = input.nextInt();
            y = input.nextInt();
            int targetSelected = Helpers.coord2index(x, y);
            boolean isValidMove = false;
            for(int targetIndex:importedArray){
                if(targetIndex == targetSelected) isValidMove = true;
            }
            if(!isValidMove){
                System.out.println("You entered an invalid move. Try again.");
                continue;
            }
            moveSpace(selected, targetSelected);
            clearPassants();
            nextTurn();
            
            //checking if we're now in check(mate)
            /*
            int kingIndex = Helpers.getKing(board, !turnFlag);//the king of the team that just moved
            int[] nextPotentials = Helpers.intList2prim(Helpers.getAllPotentialMoves(board, turnFlag));
            if(Helpers.isIntInList(nextPotentials, kingIndex)){//if true, youre in check at minimum
                boolean kingCanEscape = false;
                int[] kingEscapes = Helpers.intList2prim(Helpers.potentialMoves(board, kingIndex));
                for(int escapePlan:kingEscapes){
                    if(!Helpers.isIntInList(nextPotentials, escapePlan)) kingCanEscape = true;
                }//TODO: other pieces can move to block the check. need to handle that.
                if(kingCanEscape){
                    inCheck = true;
                } else {//TODO: actually use these bools to do something. checkmate ends game, check limits movement options.
                    checkmate = true;//also, maybe tie these bools to the king pieces.
                }
                
            }
            */
        }
        
        
        //testing code
        
        //print all potential moves
        //System.out.print(Arrays.toString(Helpers.intList2prim(Helpers.getAllPotentialMoves(board, isWhitesTurn))));
        
        //print the output of getAllOfColor
        //System.out.print(Arrays.toString(Helpers.getAllOfColor(board, true))+"\n");
        
        /* Read what pieces are at 5,7 and 5,5. Move 5,7 to 5,5. Read again.
        System.out.println(board[Helpers.coord2index(5, 7)].type);
        System.out.println(board[Helpers.coord2index(5, 5)].type);
        moveSpace(Helpers.coord2index(5, 7), Helpers.coord2index(5, 5));
        System.out.println(board[Helpers.coord2index(5, 7)].type);
        System.out.println(board[Helpers.coord2index(5, 5)].type);
        */
        
        /* Read if the pieces at 0,6 and 0,1 are white. See what pieces 0,6 can kill.
        System.out.println("0,6 is white? "+board[Helpers.coord2index(0, 6)].isWhite);//expecting true
        System.out.println("0,1 is white? "+board[Helpers.coord2index(0, 1)].isWhite);//expecting false
        System.out.println("Can 0,6 kill 0,1? "+Helpers.isEnemyOccupied(Helpers.coord2index(0,6), Helpers.coord2index(0,1), board));//expecting true
        System.out.println("Can 0,6 kill 0,7? "+Helpers.isEnemyOccupied(Helpers.coord2index(0,6), Helpers.coord2index(0,7), board));//expecting false
        */

        /* Make 0,6 into a black piece. See that it is killable by the rook. Kill it with the rook.
        board[Helpers.coord2index(0,6)].isWhite = false;
        System.out.println(Helpers.potentialMoves(board, Helpers.coord2index(0,6)));
        System.out.println(Helpers.potentialMoves(board, Helpers.coord2index(0,7)));
        moveSpace(Helpers.coord2index(0, 7), Helpers.coord2index(0, 6));
        System.out.println(Helpers.potentialMoves(board, Helpers.coord2index(0,6)));
        */
        
        /* Knight movement check for the bottom-left-most knight.
        System.out.println("Knight at 1,7 moves: "+Helpers.potentialMoves(board,Helpers.coord2index(1,7)));
        System.out.println(Helpers.getCoords(42));
        System.out.println(Helpers.getCoords(40));
        */
        
        /* Spawn a rook at 1,4 and check its movement.
        System.out.println(Helpers.coord2index(1,4));
        board[Helpers.coord2index(1, 4)] = new pieceClass("rook", Helpers.coord2index(1, 4), false, false);
        System.out.println(Helpers.potentialMoves(board, Helpers.coord2index(1,4)));
        */
        
        /* Spawn a black unit at 2,5 and 4,5. See how the pawns below it are able to kill them.
        board[Helpers.coord2index(2, 5)] = new pieceClass("queen", Helpers.coord2index(2, 5), false, false);//indx 42
        board[Helpers.coord2index(4, 5)] = new pieceClass("queen", Helpers.coord2index(4, 5), false, false);//indx 44
        System.out.println(Helpers.potentialMoves(board, Helpers.coord2index(1,6)));//49
        System.out.println(Helpers.potentialMoves(board, Helpers.coord2index(2,6)));//50
        System.out.println(Helpers.potentialMoves(board, Helpers.coord2index(3,6)));//51
        System.out.println(Helpers.potentialMoves(board, Helpers.coord2index(4,6)));//52
        board[53].moved(53);//no longer unmoved; after first move, pawns can only move forward 1 tile
        System.out.println(Helpers.potentialMoves(board, Helpers.coord2index(5,6)));//53
        */
    }
}
