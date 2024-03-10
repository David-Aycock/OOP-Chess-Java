package main.java;

/**
 * Represents a position on the board with a row and column.
 */
public class Position {
    private int row;
    private int column;
    
    /**
     * Constructor.
     * 
     * @param row The row of the desired position location.
     * @param column The column of the desired position location.
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }
    
    /**
     * Gets the row of the position.
     * 
     * @return The row number.
     */
    public int getRow() {
        return row;
    }
    
    /**
     * Sets the row of position.
     * 
     * @param row The row number to set.
     */
    public void setRow(int row) {
        this.row = row;
    }
    
    /**
     * Gets the column of the position.
     * 
     * @return The column number.
     */
    public int getColumn() {
        return column;
    }
    
    /**
     * Sets the column of the position.
     * 
     * @param column The column number to set.
     */
    public void setColumn(int column) {
        this.column = column;
    }
    
    /**
     * Standard to-string method.
     * 
     * @return A string representation of the position.
     */
    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}