
package game;

import java.util.Scanner;

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
public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the number of rows: ");
    int rows = scanner.nextInt();
    System.out.println("Enter the number of columns: ");
    int cols = scanner.nextInt();
    System.out.println("Enter the value of k: ");
    int k = scanner.nextInt();
    System.out.println("Select game type (1: Human vs Human, 2: Computer vs Human, 3: Computer vs Computer): ");
    int gameTypeChoice = scanner.nextInt();
    GameType gameType;
    switch (gameTypeChoice) {
        case 1:
            gameType = GameType.HUMAN_VS_HUMAN;
            break;
        case 2:
            gameType = GameType.COMPUTER_VS_HUMAN;
            System.out.println("Enter the type of agent for player 1 (1: Minimax, 2: MCTS):");
            int agentTypeChoice = scanner.nextInt();
            switch (agentTypeChoice) {
                case 1:
                    ComPlayer[0] = new MiniMaxPlayer();
                    break;
                case 2:
                    ComPlayer[0] = new MCTSPlayer();
                    break;
                default:
                    System.out.println("Invalid agent type choice. Exiting...");
                    return;
            }
            break;
        case 3:
            gameType = GameType.COMPUTER_VS_COMPUTER;
            System.out.println("Enter the type of agent for player 1 (1: Minimax, 2: MCTS):");
            int agentTypeChoice1 = scanner.nextInt();
            switch (agentTypeChoice1) {
                case 1:
                    ComPlayer[0] = new MiniMaxPlayer();
                    
                case 2:
                    ComPlayer[0] = new MCTSPlayer();
                    
                    break;
                default:
                    System.out.println("Invalid agent type choice. Exiting...");
                    return;
            }
            break;
        default:
            System.out.println("Invalid game type choice. Exiting...");
            return;
    }
    GameEngine gameEngine = new GameEngine(rows, cols, k, gameType);
    gameEngine.selectPlayerTurn();
    // Start the game logic here
}

    
    
private void selectPlayerTurn() {
    if(Player[0] == null) { 
        if(gameType == GameType.HUMAN_VS_HUMAN) {
            Player[0] = PlayerType.HUMAN; 
            Player[1] = PlayerType.HUMAN; 
        } else if(gameType == GameType.COMPUTER_VS_HUMAN) {
            Player[0] = PlayerType.AI;
            Player[1] = PlayerType.HUMAN;
        } else if(gameType == GameType.COMPUTER_VS_COMPUTER) {
            Player[0] = PlayerType.AI;
            Player[1] = PlayerType.AI;
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
