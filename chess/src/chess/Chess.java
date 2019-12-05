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
    
    public static String gameState = "play";//valid gamestates: play (normal gameplay), check, gameover (checkmate or draw)
    
    //declare the board
    public static pieceClass[] board  = new pieceClass[Helpers.BOARDSIZE*Helpers.BOARDSIZE];
    public static pieceClass[] futureBoard = new pieceClass[Helpers.BOARDSIZE*Helpers.BOARDSIZE];//used later to look a move into the future
    
    public static void newPiece(String type, int index, String team){
        board[index] = makeNewPiece(type, index, team);
    }

    public static void newPiece(String type, int index, String team, pieceClass linkedPiece){
        board[index] = makeNewPiece(type, index, team, linkedPiece);
    }
    
    public static pieceClass makeNewPiece(String type, int index, String team){//spawn pieces by type
        if(type.length() == 5 && type.substring(0,4).equals("pawn")){//accepts pawnn, pawne, pawns, pawnw for each cardinal
            char dir = type.charAt(type.length() - 1);
            return new pawn(index, team, dir);
        }
        
        switch(type){
            case "b":
            case "blank":
                return new blankPiece(index, "");
            case "rook":
                return new rook(index, team);
            case "knight":
                return new knight(index, team);
            case "bishop":
                return new bishop(index, team);
            case "queen":
                return new queen(index, team);
            case "king":
                return new king(index, team);
            default:
                System.out.println("INVALID TYPE "+type+" SPAWNED IN Chess.newPiece");
                return new blankPiece(index, "");
        }
    }
    
    //overloaded proc for passant pieces
    public static pieceClass makeNewPiece(String type, int index, String team, pieceClass linkedPiece){
        return new enPassantPiece(index, team, linkedPiece);
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
        board[toIndx].onDeath(board);
        board[toIndx] = board[fromIndx];//piece clones to new index
        board[toIndx].moved(toIndx);//update its internal index, its unmoved bool, etc.
        blankSpace(fromIndx);//replace old space with blank piece
        checkForPromotion(toIndx);
    }

    public static void moveOtherSpace(int fromIndx, int toIndx, pieceClass[] otherBoard){
        otherBoard[toIndx].onDeath(otherBoard);
        otherBoard[toIndx] = otherBoard[fromIndx];//piece clones to new index
        otherBoard[toIndx].beforeMoved(toIndx);//update its internal index, its unmoved bool, etc.
        blankSpace(fromIndx, otherBoard);//replace old space with blank piece
        //no promotions needed here
    }
    
    
    
    
    public static void checkForPromotion(int toIndex){
        if(!board[toIndex].canBePromoted) return;
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
        //System.out.println("Your pawn has been promoted!");
        newPiece("queen", toIndex, whoseTurn);
    }
    
    public static void blankSpace(int index){//spawns a blank tile on the board
        board[index] = new blankPiece(index, "");
    }
    
    public static void blankSpace(int index, pieceClass[] otherBoard){//overloaded for futureBoard
        otherBoard[index] = new blankPiece(index, "");
    }
    
    public static void nextTurn(){//simply flips the turn string like a boolean
        turnTracker++;
        if(turnTracker > numberOfTeams-1) turnTracker = 0;
        whoseTurn = teams[turnTracker];
    }
    
    public static String getOtherTeam(String team){
        int tempTracker = 0;
        for(int i = 0; i < numberOfTeams; i++){
            if(teams[i].equals(team)) tempTracker = i;
        }
        if(tempTracker+1 > numberOfTeams-1) tempTracker -= numberOfTeams;
        return teams[tempTracker+1];
    }
    
    public static void clearPassants(){
        for(pieceClass lookAt:board){
            if(lookAt instanceof enPassantPiece && !lookAt.team.equals(whoseTurn)){
                lookAt.beforeDeath();
            }
        }
    }

    //1. after team X moves a piece, check if king Y is in team Xs allPotMoves
    //2. if it is, set game state to Check.
    //3. int[] uncheckMoves is an array of allowed moves that gets team Y out of check (with any piece, not just king)
    //4. 1 futureboard is needed. test ALL of team Ys possible moves one at a time, checking the futureboard for check each time
    //5. if the futureboard is not in check after a potential move, that move is put into uncheckMoves
    //6. while gamestate is in check, team Y can only use moves listed in uncheckMoves
    //7. if uncheckMoves is empty, checkmate and end the game
    
    public static boolean check4check(pieceClass[] useBoard, String team){
        int[] allEnemyMoves = Helpers.intList2prim(Helpers.getAllPotentialMoves(useBoard, getOtherTeam(team)));
        int ourKingsTile = Helpers.getKing(useBoard, team);
        return Helpers.isIntInList(allEnemyMoves, ourKingsTile);
    }
    
    public static pieceClass[] makeDupeBoard(){
        pieceClass[] dupeBoard = new pieceClass[Helpers.BOARDSIZE*Helpers.BOARDSIZE];
        enPassantPiece tempPassant = null;
        for(int i = 0; i < board.length; i++){
            pieceClass pieceAt = board[i];
            String nameToPass = pieceAt.name;
            if(pieceAt instanceof pawn){
                pawn pawnAt = (pawn)pieceAt;
                nameToPass = nameToPass + pawnAt.direction;
                //System.out.println("Pawn name: "+nameToPass);
            } else if(pieceAt instanceof enPassantPiece){
                tempPassant = (enPassantPiece)pieceAt;
                continue;
            }
            dupeBoard[i] = makeNewPiece(nameToPass, i, pieceAt.team);
        }
        if(tempPassant != null){
            dupeBoard[tempPassant.index] = makeNewPiece(tempPassant.name, tempPassant.index, tempPassant.team, dupeBoard[tempPassant.linkedPiece.index]);
        }
        for(int i = 0; i < board.length; i++){
            if(dupeBoard[i] == null){
                System.out.println(i + " index is null in dupeboard creation");
                System.exit(1);
            }
        }
        //System.out.println(Arrays.toString(dupeBoard));
        return dupeBoard;
    }
    
    public static boolean didILose(){//sees if the current player is in checkmate
        int[] allMyPieces = Helpers.getAllOfColor(board, whoseTurn);
        for(int piece:allMyPieces){
            if(piece == -1) continue;
            if(testMovesOfOne(piece).length != 0){
                return false;
            }
        }
        return true;
    }
    
    public static int[] testMovesOfOne(int pieceIndex){//filters potential moves for moves that put you into check
        int validMoves = 0;
        futureBoard = makeDupeBoard();
        int[] potentialMoves = Helpers.intList2prim(board[pieceIndex].potentialMoves(futureBoard));
        
//        System.out.println("Before filter moves: "+Arrays.toString(potentialMoves));
        
        for(int i = 0; i < potentialMoves.length; i++){
            int nextMove = potentialMoves[i];
            moveOtherSpace(pieceIndex, nextMove, futureBoard);
//            System.out.println("Potential future #"+i);
//            int[] targets = new int[Helpers.BOARDSIZE*Helpers.BOARDSIZE];
//            Arrays.fill(targets,-1);
//            displayBoard(targets, futureBoard);
            if(check4check(futureBoard, whoseTurn)){
//                System.out.println("That move checks us, remove it");
                potentialMoves[i] = -1;
//                System.out.println(Arrays.toString(potentialMoves));
            } else {
                validMoves++;
            }
            futureBoard = makeDupeBoard();
        }
        int[] validMovesArray = new int[validMoves];
        validMoves = 0;
        for(int i = 0; i < potentialMoves.length; i++){
            if(potentialMoves[i] != -1){
                validMovesArray[validMoves] = potentialMoves[i];
                validMoves++;
            }
        }
//        System.out.println("After filter moves: "+Arrays.toString(validMovesArray));
        return validMovesArray;
    }
    
//Obsoleted but functional ASCII-playable functions
/*    
    public static void displayBoard(int[] targets, pieceClass[] board){
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
            if(check4check(board, whoseTurn)) gameState = "check";
            else gameState = "play";
            //System.out.println("DEBUG: "+gameState);
            if(gameState.equals("check")){
                if(didILose()){
                    System.out.println("Game over! "+getOtherTeam(whoseTurn)+" wins! Starting a new game...");
                    setUpBoard();
                }
            }
            Arrays.fill(targets,-1);
            displayBoard(targets, board);
            System.out.printf("It is %s's turn.\nSelect a piece to move by entering an X coord, then a Y coord.\n",whoseTurn);
            int x = input.nextInt();
            int y = input.nextInt();
            int selected = Helpers.coord2index(x, y);
            if(!board[selected].team.equals(whoseTurn) || board[selected].abstractPiece){
                System.out.println("You selected an invalid piece. Try again.");
                continue;
            }
            int[] importedArray = testMovesOfOne(selected);
            if(importedArray.length == 0){
                System.out.println("The selected piece has no possible moves. Try again.");
                continue;
            }
            String validCoordsMessage = "Valid moves are at these coords:\n";
            for(int targetIndex:importedArray){
                targets[targetIndex] = targetIndex;
                validCoordsMessage += Helpers.getCoords(targetIndex)+"\n";
            }
            displayBoard(targets, board);
            System.out.print(validCoordsMessage);
            System.out.println("Select a tile to move to by entering an X and then a Y coord.");
            x = input.nextInt();
            y = input.nextInt();
            int targetSelected = Helpers.coord2index(x, y);
            if(!Helpers.isIntInList(importedArray, targetSelected)){
                System.out.println("You entered an invalid move. Try again.");
                continue;
            }
            moveSpace(selected, targetSelected);
            clearPassants();
            nextTurn();
        }
    }
*/
}
