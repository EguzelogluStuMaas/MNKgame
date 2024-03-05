
package game;

public class GameEngine {
private int rows;
private int cols;
private int k;
private GameType gameType;
private BigToeBoard gameBoard;
private PlayerType[]    Player    = new PlayerType[2];
private static Player[] ComPlayer = new Player[2];
public GameEngine(int rows, int cols, int k, GameType type){
    this.rows = rows;
    this.cols = cols;
    this.k = k;
    gameType = type;
    gameBoard = new BigToeBoard(rows, cols, k);
}
void initialize()
{
    Scanner in = new Scanner(System.in);
   System.out.println("Enter M (number of rows): ");
    int M = in.nextInt();
    System.out.println("Enter N (number of columns): ");
    int N = in.nextInt();
    System.out.println("Enter K (number of cells in a row to win): ");
    int K = in.nextInt();
    System.out.println("Enter game type (0 for HUMAN_VS_HUMAN, 1 for COMPUTER_VS_HUMAN, 2 for COMPUTER_VS_COMPUTER): ");
    gameType = GameType.values()[in.nextInt()];
    BigToeBoard gameState = new BigToeBoard( M, N, K);
    Game game = new Game();
    while(gameState!=GameState.DRAW && gameState!=GameState.WIN_PLAYER_1 && gameState!=GameState.WIN_PLAYER_2)
    {
        System.out.println("Current player: "+gameState.getCurrentPlayer());
        if(gameState.getCurrentPlayer()==0)
        {
            System.out.println("Enter row and column: ");
            int i = in.nextInt();
            int j = in.nextInt();
            gameState.selectCell(i, j);
        }
        else
        {
            System.out.println("Computer is thinking...");
            Cell cell = game.getBestMove(gameState);
            gameState.selectCell(cell.i, cell.j);
        }
    }

    
    
}
private void selectPlayerTurn() {
    if(Player[0] == null) { 
        if(gameType == GameType.HUMANvsHUMAN) {
            Player[0] = PlayerType.HUMAN; 
            Player[1] = PlayerType.HUMAN; 
        } else if(gameType == GameType.HUMANvsCOMPUTER) {
            Player[0] = PlayerType.COMPUTER;
            Player[1] = PlayerType.HUMAN;
        } else if(gameType == GameType.COMPUTERvsCOMPUTER) {
            Player[0] = PlayerType.COMPUTER;
            Player[1] = PlayerType.COMPUTER;
        }
    } else {                // from second game, switch
        PlayerType tmp1 = Player[0];
        Player[0]          = Player[1];
        Player[1]          = tmp1;
        Player     tmp2 = ComPlayer[0];
        ComPlayer[0]	     = ComPlayer[1];
        ComPlayer[1]       = tmp2;
    }
}
}
