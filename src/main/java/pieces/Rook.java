package main.java.pieces;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import main.java.Board;
import main.java.Piece;
import main.java.Position;
import main.java.Tile;

/**
 * Represents a Rook.
 * Can move horizontally or vertically any number of squares.
 */
public class Rook extends Piece {
    private static final int SIZE = 8;
    
    /**
     * Constructor.  Gives a specific color and position to the Rook.
     * 
     * @param color The color of the Rook (WHITE/BLACK)
     * @param position The position of the Rook.
     */
    public Rook(Piece.Player color, Position position) {
        super(color, position);
    }
    
    /**
     * Checks if a move is valid for the Rook.
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
        if (xStart == xEnd || yStart == yEnd) {
            // Check if the path is clear
            if (isPathClear(board, xStart, yStart, xEnd, yEnd)) {
                // System.out.println("Rook move check pass."); // Debugging statement
                return true;
            }
        }
        
        // System.out.println("Move invalid: Rook can only move horizontally or vertically."); // Debugging statement
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
        
        for (int i = 0; i < SIZE; i++) {
            if (i != currentRow) {
                moves.add(new Position(i, currentColumn));
            }
            if (i != currentColumn) {
                moves.add(new Position(currentRow, i));
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
     * Gets the image representing the Rook.
     * 
     * @return An Image representing the Rook.
     */
    @Override
    public Image getImage() {
        String imageName = "/main/resources/images/" + (getColor() == Piece.Player.WHITE ? "White" : "Black") + "Rook.png";
        return new Image(imageName);
    }
}