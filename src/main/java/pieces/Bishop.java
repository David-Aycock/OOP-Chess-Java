package main.java.pieces;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import main.java.Board;
import main.java.Piece;
import main.java.Position;
import main.java.Tile;

/**
 * Represents a Bishop.
 * Can move diagonally any number of squares.
 */
public class Bishop extends Piece {
    private static final int SIZE = 8;
    
    /**
     * Constructor.  Gives a specific color and position to the Bishop.
     * 
     * @param color The color of the bishop (WHITE/BLACK)
     * @param position The position of the Bishop.
     */
    public Bishop(Player color, Position position) {
        super(color, position);
    }
    
    /**
     * Checks if a move is valid for the Bishop.
     * 
     * @param board The current board state.
     * @param startTile The starting tile of the move.
     * @param endTile The ending tile of the move.
     * @return true if the move is valid.
     */
    @Override
    public boolean isValidMove(Board board, Tile startTile, Tile endTile) {
        if (isOccupiedBySameColor(endTile)) {
            // System.out.println("End tile is occupied by same color"); // Debugging statement
            return false;
        }
        
        int xStart = startTile.getX();
        int yStart = startTile.getY();
        int xEnd = endTile.getX();
        int yEnd = endTile.getY();
        
        // Check if the move is a valid diagonal move
        if (Math.abs(xStart - xEnd) == Math.abs(yStart - yEnd)) {
            // Checks if path is clear
            if (isPathClear(board, xStart, yStart, xEnd, yEnd)) {
                // System.out.println("Bishop move check passed."); // Debugging statement
                return true;
            }
        }
        return false;
    }
    
    /**
     * Generates a list of potential moves.
     * 
     * @return A list of potential moves that the Bishop may move to.
     */
    @Override
    public List<Position> getPotentialMoves() {
        List<Position> moves = new ArrayList<>();
        int currentRow = this.getPosition().getRow();
        int currentColumn = this.getPosition().getColumn();
        
        int[][] directions = {
            {-1, -1}, {-1, 1},
            {1, -1}, {1, 1}
        };
        
        for (int[] direction : directions) {
            int nextRow = currentRow;
            int nextColumn = currentColumn;
            
            while (true) {
                nextRow += direction[0];
                nextColumn += direction[1];
                
                // Check if position is within board limits
                if (nextRow >= 0 && nextRow < SIZE && nextColumn >= 0 && nextColumn < SIZE) {
                    moves.add(new Position(nextRow, nextColumn));
                } else {
                    break;
                }
            }
        }
        return moves;
    }
    
    /**
     * Checks if the path between the start and end positions is clear and available for movement.
     * Goes one-by-one, checking if each tile is clear.
     * 
     * @param board The current board state.
     * @param xStart The x-coordinate of the start position.
     * @param yStart The y-coordinate of the start position.
     * @param xEnd The x-coordinate of the end position.
     * @param yEnd The y-coordinate of the end position.
     * @return true if the path is clear.
     */
    private boolean isPathClear(Board board, int xStart, int yStart, int xEnd, int yEnd) {
        int xDirection = Integer.compare(xEnd, xStart);
        int yDirection = Integer.compare(yEnd, yStart);
        
        int x = xStart + xDirection;
        int y = yStart + yDirection;
        
        while (x != xEnd && y != yEnd) {
            if (board.getTile(x, y).isOccupied()) {
                return false;
            }
            x += xDirection;
            y += yDirection;
        }
        return true;
    }
    
    /**
     * Gets the image representing the Bishop.
     * 
     * @return An Image representing the Bishop.
     */
    @Override
    public Image getImage() {
        String imageName = "/main/resources/images/" + (getColor() == Player.WHITE ? "White" : "Black") + "Bishop.png";
        return new Image(imageName);
    }
}