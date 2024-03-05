package game;

import java.util.Random;

public class MiniMaxPlayer implements Player{
    private  int MAX_DEPTH;
    private Random rand;
    private int TIMEOUT;

    private CellState board[][];

    private int M, N, K;
    private CellState opponentState, agentState, freeState;
    private Cell selectedCell;
    private boolean IS_TIMEOUT;
    private int WIN = 1000;
    private int LOSS = -1000;
    private int SECURE_WIN = 800;
    private int SECURE_LOSS = -800;
    private int ALMOST_WIN = 50;
    private int ALMOST_LOSS = -50;

    private int agentScore;
    private int OpponentScore;
    private int possible_cells;
    private int[] win_table_agent;
    private int[] win_table_opp;
    private Boolean secure_win_mia, secure_win_nemica;
    private int almost_secure_win_agent, almost_secure_win_opponent;
    private GameStatus winAgent;
    private GameStatus winOpponent;

    private Boolean first;


    public MiniMaxPlayer(){

    }


    @Override
    public void setPlayer(int row, int col, int k, boolean turn, int timeout_lim) {
        rand = new Random(System.currentTimeMillis());
        // Save the timeout for testing purposes
        TIMEOUT = timeout_lim;
        // Save the game parameters
        
        this.M = M;
        this.N = N;
        this.K = K;
        this.MAX_DEPTH = 5;
        freeState = CellState.EMPTY;
        if(turn){
            agentState = CellState.PLAYER_1;
            opponentState = CellState.PLAYER_2;
        }else{
            agentState = CellState.PLAYER_2;
            opponentState = CellState.PLAYER_1;
        }
        board = new CellState[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = freeState;
            }
        }

    }


    @Override
    //TODO add heuristics first for specific board conditions,implement minimax in the most basic fashion create evaluation func

    public Cell selectCell(Cell[] emptyCell, Cell[] selectedCell) {
        IS_TIMEOUT = false;
        //check if there is only one cell left if so select that cell 
        if(emptyCell.length == 1){
            return emptyCell[0];
        }
        //if there are moves made by the opponent then update the board
        if(selectedCell.length > 0){
            board[selectedCell[selectedCell.length-1].i][selectedCell[selectedCell.length-1].j] = opponentState;
        }
        //check if game is active and initial move is not true
        if(!first){
            //if game is active then update the board
            board[selectedCell[selectedCell.length-1].i][selectedCell[selectedCell.length-1].j] = opponentState;
        }
        
        /*checks if the game is active and the AI is the first to play MC.length == 2 && primoAgiocare && active*/
        if(selectedCell.length == 2 && first){
            //if the AI is the first to play then update the board
            board[selectedCell[selectedCell.length-1].i][selectedCell[selectedCell.length-1].j] = agentState;
            first = false;
        }
        //
        return null;
        


    }
    public double evaluation(Cell lastMove,boolean aiTurn){
        /*TODO implement eval in */
        /*initialize 2 arrays win_table_agent,win_table_opponent to track winning lines
         *  checks if the last cell played leads to a win for the AI
         * checks if the last cell played leads to a win for the opponent
         * if a player wins then return the win valuefor PLAYER_1 or PLAYER_2 for turn
         * define secureness of win(4 moves or less?)
         * return evalscore=(winning score or almost winning score) for the AI or the opponent
         * iterateover potential winning lines and check if the AI or the opponent is close to winning? perhaps
         * then return final evaluse this in Minimax for best move
        */
        if(isWinningCell(lastMove.i, lastMove.j, agentState)||isWinningCell(lastMove.i, lastMove.j, opponentState)){
            return WIN;
        }
        return ALMOST_LOSS;

    }


    private boolean isWinningCell(int i, int j, CellState agentState2) {
        // TODO Auto-generated method stub, implement the method similar toour board wincell checker
        throw new UnsupportedOperationException("Unimplemented method 'isWinningCell'");
    }


    @Override
    public String playerName() {
        return "MiniMaxPlayer";
    }
    
        
}