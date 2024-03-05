package game;

import java.util.Random;

public class MiniMaxPlayer implements Player{
private int MAX_DEPTH=5;
/**
     * Play moves on the board alternating between playing as X and O analysing 
     * the board each time to return the value of the highest value move for the
     * X player. Return the highest value move when a terminal node or the 
     * maximum search depth is reached.
     * @param board Board to play on and evaluate
     * @param depth The maximum depth of the game tree to search to
     * @param isMax Maximising or minimising player 
     * @return Value of the board state
     */
    public static int MiniMaxPlayer(BigToeBoard board, int depth, boolean isMax,int alpha,int beta){
        int boardVal = evaluateBoard(board);
    }
    private static int evaluateBoard(BigToeBoard board){
        int score=0;
        
    }
        
}