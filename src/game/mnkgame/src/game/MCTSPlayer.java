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
	private int row, cols, K;
    private Node mcts;
    private Boolean[] win_table_agent;
	private Boolean[] win_table_opponent;
	private GameStatus agentGameState;
	private GameStatus opponentGameState;
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
        this.K = K;
        TIMEOUT = timeout_lim;
        rand = new Random(System.currentTimeMillis());
        board = new CellState[row][col];
        this.mcts=new Node(row,col,k,board,null,null,agentCellState);
        this.win_table_agent = new Boolean[k+1];
        this.win_table_opponent = new Boolean[k+1];
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
        if(emptyCells.length == 1)
        {
            return emptyCells[0];
        }
        if(markedCells.length >0)
        {
            board[markedCells[markedCells.length-1].i][markedCells[markedCells.length-1].j] = opponentCellState;
        }
        mcts=new Node(row,cols,K,board,emptyCells,markedCells,agentCellState);
        Cell cell = mcts.bestMove();
        board[cell.i][cell.j] = agentCellState;
        return cell;

    }
    private boolean updateWinTable(int i,int j,CellState player){
        CellState currentState=player;
        int count;
        count = 1;
		for (int k = 1; j - k >= 0 && board[i][j - k] == currentState; k++)
			count++; // backward check
		for (int k = 1; j + k < cols && board[i][j + k] == currentState; k++)
			count++; // forward check
		if (count >= 2) {
			if (player == agentCellState) {
				for (int k = 2; k <= K; k++) {
					if (count >= k)
						win_table_agent[k] = true;
					else
						win_table_agent[k] = false;
				}
			} else {
				for (int k = 2; k <= K; k++) {
					if (count >= k)
						win_table_opponent[k] = true;
					else
						win_table_opponent[k] = false;
				}
			}
			if (count >= K)
				return true;
		}

		// Vertical check
		count = 1;
		for (int k = 1; i - k >= 0 && board[i - k][j] == currentState; k++)
			count++; // backward check
		for (int k = 1; i + k < row && board[i + k][j] == currentState; k++)
			count++; // forward check
		if (count >= 2) {
			if (player == agentCellState) {
				for (int k = 2; k <= K; k++) {
					if (count >= k)
						win_table_agent[k] = true;
					else
						win_table_agent[k] = false;
				}
			} else {
				for (int k = 2; k <= K; k++) {
					if (count >= k)
						win_table_opponent[k] = true;
					else
						win_table_opponent[k] = false;
				}
			}
			if (count >= K)
				return true;
		}

		// Diagonal check
		count = 1;
		for (int k = 1; i - k >= 0 && j - k >= 0 && board[i - k][j - k] == currentState; k++)
			count++; // backward check
		for (int k = 1; i + k < row && j + k < cols && board[i + k][j + k] == currentState; k++)
			count++; // forward check
		if (count >= 2) {
			if (player == agentCellState) {
				for (int k = 2; k <= K; k++) {
					if (count >= k)
						win_table_agent[k] = true;
					else
						win_table_agent[k] = false;
				}
			} else {
				for (int k = 2; k <= K; k++) {
					if (count >= k)
						win_table_opponent[k] = true;
					else
						win_table_opponent[k] = false;
				}
			}
			if (count >= K)
				return true;
		}

		// Anti-diagonal check
		count = 1;
		for (int k = 1; i - k >= 0 && j + k < cols && board[i - k][j + k] == currentState; k++)
			count++; // backward check
		for (int k = 1; i + k < cols && j - k >= 0 && board[i + k][j - k] == currentState; k++)
			count++; // backward check
		if (count >= 2) {
			if (player == agentCellState) {
				for (int k = 2; k <= K; k++) {
					if (count >= k)
						win_table_agent[k] = true;
					else
						win_table_agent[k] = false;
				}
			} else {
				for (int k = 2; k <= K; k++) {
					if (count >= k)
						win_table_opponent[k] = true;
					else
						win_table_opponent[k] = false;
				}
			}
			if (count >= K)
				return true;
		}

		win_table_agent[K] = false;
		win_table_opponent[K] = false;
		return false;
    }

    @Override
    public String playerName() {
      
        return "MCTS";
    }

}
