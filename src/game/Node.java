package game;
import java.util.LinkedList;
import java.util.Random;
import java.util.*;

public class Node {
    Node parent;
    List<> children;
    private Random rand;
    protected CellState[][] boardState;
    Cell[] emptyCells;
    Queue<Cell> frontier;
    Cell[] markedCells;
    private int M, N, K;
    private CellState nodeState;

    int simulationCount;
    double uct;
    int value;
    public Node(int M, int N, int K, CellState[][] boardState, Cell[] emptyCells, Cell[] markedCells, CellState nodeState){
        rand = new Random(System.currentTimeMillis());
        children =  new ArrayList<Node>();
        this.boardState = new CellState[M][N];
        this.nodeState = nodeState;
        this.emptyCells = new Cell[M*N];
        this.markedCells = new Cell[M*N];
        this.simulationCount = 0;
        this.uct =Double.POSITIVE_INFINITY;
        this.value = 0;
        this.M = M;
        this.N = N;
        this.K = K;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                boardState[i][j] = CellState.EMPTY;
            }
        }
        //initialize queue for frontier
        frontier = new LinkedList<Cell>();
        for(int i = 0; i < emptyCells.length; i++){
            this.emptyCells[i] = emptyCells[i];
            frontier.add(emptyCells[i]);
        }
        print("frontier: " + frontier.size()+ " empty cells"+emptyCells.length);
        
    }
    //getters setters
    public List<Node> getChildren()
    {
        return children;
    }
    public void addChild(Node child)
    {
        children.add(child);
    }
    public void removeChild(Node child)
    {
        children.remove(child);
    }
    public void setParent(Node parent)
    {
        this.parent = parent;
    }
    public Node getParent()
    {
        return parent;
    }
    public double getUctValue(int parentSimulationCount){
        int t = parentSimulationCount;
        uct = Math.sqrt(2) * Math.sqrt(Math.log((double) t) / (double) simulationCount);
        uct += (double) ((double) (value) / (double) (simulationCount));
        return uct;

    }
    /* Method: Selection
     * Description: Searches for the node with the highest UCT value and continues until
     * reaching a node that has not yet reached its maximum number of children.
     * Once a complete node is reached, selection is made on the child with the maximum UCT value.
     */
    public void selection() {
        if (frontier.size() > 0) {
            expand();
        } else {
            Node bestNodeUCT = null;
            for (Node child : children) {
                if (bestNodeUCT == null || child.getUctValue(simulationCount) > bestNodeUCT.getUctValue(simulationCount)) {
                    bestNodeUCT = child;
                }
            }
            
            if (bestNodeUCT != null) {
                bestNodeUCT.selection();
            } else {
                simulate();
            }
        }
    }
    
    public void expand() {
        if (frontier.size() > 0) {
            Cell cell = frontier.poll();
            Node child = new Node(M, N, K, boardState, emptyCells, markedCells, nodeState);
            child.boardState[cell.i][cell.j] = nodeState;
            child.emptyCells = new Cell[emptyCells.length - 1];
            child.markedCells = new Cell[markedCells.length + 1];
            for (int i = 0, j = 0, k = 0; i < emptyCells.length; i++) {
                if (emptyCells[i].i != cell.i || emptyCells[i].j != cell.j) {
                    child.emptyCells[j++] = emptyCells[i];
                } else {
                    child.markedCells[k++] = emptyCells[i];
                }
            }
            child.setParent(this);
            addChild(child);
            child.simulate();
        }
    }
    public void simulate(){
        CellState currentState=nodeState;
        CellState[][]backupState=copyCells(boardState);
        List<Cell>listEC=new ArrayList<Cell>();
        int count=0;
        for(int i=0;i<emptyCells.length;i++){
            if (emptyCells[i] != null)
                    listEC.add(emptyCells[i]);
        }
        if(markedCells.length > 0){
            
        }
        
    }
    //Checks
    //helpers
    private CellState[][] deepCopy(CellState[][] state){
        CellState[][] copy = new CellState[state.length][];
        for (int i = 0; i < state.length; i++) {
            copy[i] = state[i].clone();
            for(int j=0;j<state[i].length;j++){
                copy[i][j]=state[i][j];
            }
        }
        return copy;

    }
    public Cell[] copyCells(Cell[] cells){
        Cell[] copy = new Cell[cells.length];
        for(int i=0;i<cells.length;i++){
            copy[i]=cells[i];
        }
        return copy;
    }
}
