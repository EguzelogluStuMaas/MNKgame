package game;

public class Cell {
    /* Cell row index
   */
	public final int  i;
	/**
   * Cell column index
   */
	public final int  j;
	/**
   * Cell state
   */
	public final CellState state;
    public Cell(int i, int j, CellState state) {
        this.i = i;
        this.j = j;
        this.state = state;
    }
    /**Allocates a cell
     * @param i row index
     * @param j column index
     */
    public Cell(int i,int j) {
        this.i = i;
        this.j = j;
        this.state = CellState.EMPTY;
    }
    @Override
	public boolean equals(Object obj) {
		if(obj == null)               return false;
		if(obj == this)               return true; 	
		if(!(obj instanceof Cell)) return false;

		Cell c = (Cell) obj;

		return this.i == c.i && this.j == c.j && this.state == c.state;
	}
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
    @Override
    public String toString() {
        return "Cell(" + this.i + "," + this.j + "," + this.state + ")";
    }

}
