
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
