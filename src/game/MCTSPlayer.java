package game;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
public class MCTSPlayer implements Player{

    private Random rand;
	private int TIMEOUT;
	protected CellState[][] board;
	private int row, cols, k;
    private Node mcts;
    private Boolean[] win_table_agent;
	private Boolean[] win_table_opponent;
	private GameState agentGameState;
	private GameState opponentGameState;
	private CellState agentCellState;
	private CellState opponentCellState;
    private int oppScore;
    private int agentScore;
    private boolean isInitialMove;
    public MCTSPlayer(){
        
    }

    public void setPlayer(int row,int col,int k,boolean turn,int timeout_lim){
        this.row = row;
        this.cols = col;
        this.k = k;
        TIMEOUT = timeout_lim;
        rand = new Random(System.currentTimeMillis());
        board = new CellState[row][col];
        this.mcts = new Node();
        this.win_table_agent = new Boolean[K+1];
        this.win_table_opponent = new Boolean[K+1];
        this.agentCellState = turn ? CellState.PLAYER_1 : CellState.PLAYER_2;
        this.opponentCellState = turn ? CellState.PLAYER_2 : CellState.PLAYER_1;
        this.oppScore = 0;
        this.agentScore = 0;
        this.isInitialMove = true;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = CellState.EMPTY;
            }
        }
        for(int i = 0; i <= k; i++){
            win_table_agent[i] = false;
            win_table_opponent[i] = false;
        }
    }
    //select random cell from free cells
    public Cell selectCell(Cell[] emptyCells,Cell[] markedCells)
    {
        //first check for only one available move(empty cell)
        if(emptyCells.length == 1){
            return emptyCells[0];
        }
        /*if there are no marked cells, it randomly selects a free cell, marks it as occupied by the AI player */
        if(markedCells.length == 0){
            int index = rand.nextInt(emptyCells.length);
            Cell c=emptyCells[index];
            board[c.i][c.j]=agentCellState;
        }
        if(markedCells.length>0){
            /*If there are marked cells, it marks the last marked cell as occupied by the opponent opponentCellState */
            Cell c=markedCells[markedCells.length-1];
            board[c.i][c.j]=opponentCellState;  
        }
        mcts =new Node(row,cols,k,agentCellState,emptyCells,markedCells,board);
        /*  runs the MCTS algorithm until it has run a certain number of simulations (15000 in this case) 
        or until the number of simulations stops increasing. 
        The Selection method is part of the MCTS algorithm and is used to select the next node to explore.*/
        
    }

}
