package main.java.com.ege.main;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.reflect.*;
import java.util.Random;

import java.util.concurrent.Future;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.Callable;
/*Game engine of big toe */
public class BigToeGame extends JFrame {
    private final BigToeBoard BOARD;
    private final int BOARD_WIDTH;
    private final int BOARD_HEIGHT;
    
    private final int CELL_SIZE;
    private final int GRID_WIDTH; // Grid-line's width
	private final int GRID_WIDTH_HALF;
    private final int CELL_CROSS_SIZE;
    public DrawBoard board;
    private JLabel statusBar;
    public enum GameMode {
        HUMAN_VS_HUMAN,
        HUMAN_VS_AI,
        AI_VS_AI
    }
    public enum playerType {
        HUMAN,
        AI
    }
    private playerType[] players=new playerType[2];
    private Player[] player=new Player[2];
    private Player[] aiPlayers=new Player[2];
    private GameMode gameMode;
    private static final int MOVE_TIME_LIMIT = 15;
    /*counters */
    private int p1_wins,p2_wins,draw_count, autoplay_times;
    private Random Rand = new Random(System.currentTimeMillis());

    public BigToe(int M, int N, int K, GameMode gameMode) {
        BOARD = new BigToeBoard(M, N, K);
        CELL_SIZE = 100;
        BOARD_WIDTH = N*CELL_SIZE;
        BOARD_HEIGHT = M*CELL_SIZE;
        
        GRID_WIDTH = CELL_SIZE / 10;
        GRID_WIDTH_HALF = GRID_WIDTH / 2;
        CELL_CROSS_SIZE = 32;
        autoplay_times = 1;
        
        board = new DrawBoard();
        board.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

		// Add MouseEvent upon mouse-click
		board.addMouseListener(new MouseClickHandler());

		// Setup the status bar (JLabel) to display status message
		statusBar = new JLabel("  ");
		statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
		statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));

		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(board, BorderLayout.CENTER);
		cp.add(statusBar, BorderLayout.PAGE_END);

		setResizable(false); // window not resizable
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack(); // pack all the components in this JFrame

		initGame(); // initialize board and variables
        
    }
    



    private class MouseClickHandler extends MouseAdapter{
        private class StopPlayer implements Callable<Cell>
        {
            private final Player P;
            private final BigToeBoard B;
            private StopPlayer(Player P, BigToeBoard B)
            {
                this.P = P;
                this.B = B;
            }
        }
        public Cell call() throws InterruptedException
        {
            return P.selectCell(B.getEmptyCells(), B.getSelectedCells());
        }
        @Override
        public void mouseClicked(MouseEvent e)
        {
            try{
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            for (int k=0;k<autoplay_times;k++){
              int X = e.getX();
              int Y = e.getY();
              int i=X/CELL_SIZE;
              int j=Y/CELL_SIZE;
                if(BOARD.gameState()==GameState.ONGOING)
                {
                    if(players[BOARD.currentPlayer()] == playerType.HUMAN)
                      if(BOARD.getCellState()==CellState.Empty)
                      {
                        BOARD.selectCell(i, j);
                      }
                      else{
                        int currentPlayer = BOARD.currentPlayer();
                        final ExecutorService executor = Executors.newSingleThreadExecutor();
                        final Future<Cell> future = executor.submit(new StopPlayer(player[aiPlayers[currentPlayer]], BOARD));
                        executor.shutdown();
                        try {

                            final Cell cell = future.get(MOVE_TIME_LIMIT, TimeUnit.SECONDS);
                            BOARD.selectCell(cell.i, cell.j);
                        } catch (TimeoutException ex) {
                            executor.shutdownNow();
                            System.err.println(aiPlayer[currentPlayer].plyrName() + " failed to play due to timeout");
                        } catch (InterruptedException ex) {
                            future.cancel(true);
                            Thread.currentThread().interrupt();
                        } catch (ExecutionException ex) {
                            future.cancel(true);
                            System.err.println("Execution exception");
                        } finally {
                            executor.shutdown();
                        }
                        if(!executor.isTerminated())
                        {
                            executor.shutdownNow();
                        }
                        if(BOARD.getCellState(cell.i, cell.j)==CellState.EMPTY)
                        {
                            BOARD.selectCell(cell.i, cell.j);
                        }
                        else{
                            System.err.println("Invalid move by " + aiPlayer[currentPlayer].plyrName());
                            System.exit(1);
                        }
                        checkStatus();
                        initGame();
                        repaint();
                        if(BOARD.getGameState()!=DRAW && BOARD.getGameState()!=GameState.ONGOING)
                        {
                            Cell[] finCells = BOARD.getSelectedCells();
                            System.out.println("Game over");
                            for(int m = 0; m < finCells.length; m++) {
                                System.out.println(finCells[m].i + " - " + finCells[m].j);
                            }
                        }
                      }
                }    
            }
        }
    }
     
    
}







