package game;


import java.util.LinkedList;
import java.util.HashSet;

public class BigToeBoard {
    public final int M;
    public final int N;
    public final int K;
    protected final CellState[][] BOARD;
    protected final LinkedList<Cell> selectedCells;
    protected final HashSet<Cell> emptyCells;
    protected GameState gameState;
    private final CellState[] Player={CellState.PLAYER_1, CellState.PLAYER_2};
    protected int currentPlayer;
    public BigToeBoard(int M, int N, int K) {
        this.M = M;
        this.N = N;
        this.K = K;
        BOARD = new CellState[M][N];
        selectedCells = new LinkedList<Cell>();
        emptyCells = new HashSet<Cell>((int) Math.ceil((M*N) / 0.75));
        reset();
    }

    public int getCurrentPlayer()
    {
        return currentPlayer;
    }

    public GameState selectCell(int i, int j) throws IndexOutOfBoundsException, IllegalStateException {
        if(gameState!=GameState.ONGOING)
        {
            throw new IllegalStateException("Game is over");
        }
        else if(i<0 || i>=M || j<0 || j>=N) {
            throw new IndexOutOfBoundsException("Invalid cell indexes at "+i+","+j);
        }
        else if(this.BOARD[i][j] != CellState.EMPTY) {
            throw new IllegalStateException("Cell is already selected");
        }
        Cell oldCell = new Cell(i,j,BOARD[i][j]);
        Cell newCell = new Cell(i,j,Player[currentPlayer]);
        BOARD[i][j] = Player[currentPlayer];
        emptyCells.remove(oldCell);
        selectedCells.add(newCell);
        currentPlayer = (currentPlayer + 1) % 2;
        if(isWinningCell(i, j))
        {
            gameState = (currentPlayer == 0) ? GameState.WIN_PLAYER_1 : GameState.WIN_PLAYER_2;
        }
        else if(emptyCells.isEmpty())
        {
            gameState = GameState.DRAW;
        }
        return gameState;

    }

    // Unselects last cell
    public void unselectCell(int i,int j)
    {
        if(selectedCells.isEmpty())
        {
            throw new IllegalStateException("No cell is selected");
        }
        else if(this.BOARD[i][j] == CellState.EMPTY) {
            throw new IllegalStateException("Cell is not selected");
        }
        else
        {
            Cell oldCell = selectedCells.removeLast();
            Cell newCell = new Cell(oldCell.i,oldCell.j,CellState.EMPTY);
            BOARD[oldCell.i][oldCell.j] = CellState.EMPTY;
            emptyCells.add(newCell);
            currentPlayer = (currentPlayer + 1) % 2;
            gameState = GameState.ONGOING;
        }
    }
    public boolean isWinningCell(int i,int j)
    {
        CellState currentState=BOARD[i][j];
        int count=1;
        if(currentState==CellState.EMPTY)
        {
            return false;
        }
    /*These 2 for loops check whether a filled cell is the winning cell by checking wheter cellstate is equal to prevvious cell state  */
    /*If it is then it increments the count by 1,they iterate on the row */
    for(int k=1;k>=0 && BOARD[i][j-k]==currentState;k++)
       {
           count++;
       }
    for(int k=1;k>=0 && BOARD[i][j+k]==currentState;k++)
         {
              count++;
         }
    if(count>=K) return true;

/*Same check but columnar check */
    count=1;
    for(int k=1;k>=0 && BOARD[i-k][j]==currentState;k++)
    {
        count++;
    }
    for(int k=1;k>=0 && BOARD[i+k][j]==currentState;k++)
    {
        count++;
    }
    if(count>=K) return true;
   //Diagonal check
    count=1;
    for(int k=1;k>=0 && BOARD[i-k][j-k]==currentState;k++)
    {
        count++;
    }
    for(int k=1;k>=0 && BOARD[i+k][j+k]==currentState;k++)
    {
        count++;
    }
    if(count>=K) return true;
    //Diagonal backwards checkEMPTY)
    count=1;
    for(int k=1;k>=0 && BOARD[i-k][j+k]==currentState;k++)
    {
        count++;
    }
    for(int k=1;k>=0 && BOARD[i+k][j-k]==currentState;k++)
    {
        count++;
    }
    if(count>=K) return true;
    return false;
}


    public GameState getGameState() {
        return gameState;
    }
    

    public void reset()
    {
        currentPlayer = 0;
        selectedCells.clear();
        gameState = GameState.ONGOING;
        resetBoard();
        resetEmptyCellList();
        resetSelectedCellList();

    }

    public Cell[] getSelectedCells() {
        return selectedCells.toArray(new Cell[selectedCells.size()]);
    }
    public Cell[] getEmptyCells() {
        return emptyCells.toArray(new Cell[emptyCells.size()]);
    }
    private void resetEmptyCellList() {
		this.emptyCells.clear();
		for(int i = 0; i < M; i++)
			for(int j = 0; j < N; j++)
				this.emptyCells.add(new Cell(i,j));
	}
    private void resetSelectedCellList()
    {
        this.selectedCells.clear();
    }
    private void resetBoard()
    {
        for(int i = 0; i < M; i++)
            for(int j = 0; j < N; j++)
                BOARD[i][j] = CellState.EMPTY;
    }
    public CellState getCellState(int i, int j) throws IndexOutOfBoundsException {
        if(i<0 || i>=M || j<0 || j>=N) {
            throw new IndexOutOfBoundsException("Invalid cell indexes at "+i+","+j);
        }
        else{
        return BOARD[i][j]; 
        }
        
    }
}
