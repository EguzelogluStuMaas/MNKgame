package game;

public interface Player {
    /*Interface for the big toe player, mcts and min max agents will be players implementing this interface */
    public void setPlayer(int row,int col,int k,boolean turn,int timeout_lim);

    public Cell selectCell(Cell[]emptyCell,Cell[]selectedCell);

    public String playerName();
   

}
