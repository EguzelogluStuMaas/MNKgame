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
    public int MiniMaxPlayer(BigToeBoard board, int depth, boolean isMax) {
        int score = evaluate(board);
        if (score == 10) {
            return score;
        }
        if (score == -10) {
            return score;
        }
        if (board.emptyCells.isEmpty()) {
            return 0;
        }
        if (depth == MAX_DEPTH) {
            return score;
        }
        if (isMax) {
            int best = -1000;
            for (Cell cell : board.emptyCells) {
                board.BOARD[cell.i][cell.j] = CellState.PLAYER_1;
                best = Math.max(best, MiniMaxPlayer(board, depth + 1, !isMax));
                board.BOARD[cell.i][cell.j] = CellState.EMPTY;
            }
            return best;
        } else {
            int best = 1000;
            for (Cell cell : board.emptyCells) {
                board.BOARD[cell.i][cell.j] = CellState.PLAYER_2;
                best = Math.min(best, MiniMaxPlayer(board, depth + 1, !isMax));
                board.BOARD[cell.i][cell.j] = CellState.EMPTY;
            }
            return best;
        }
    
    

}

private static int evaluateBoard(BigToeBoard board, int depth){
    
}
}