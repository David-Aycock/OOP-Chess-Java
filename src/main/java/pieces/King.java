package main.java.pieces;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import main.java.Tile;
import main.java.Piece;
import main.java.Board;
import main.java.Position;

/**
 * Represents the King.
 * King may move exactly one square in any direction.
 * Additionally contains logic for if the King is in check or may go into check.
 */
public class King extends Piece {
    
    // Storing whether the King has moved or not as it relates to Castling. (If the King or the Rook has moved, then Castling is no longer available.)
    // This is set in Board.java when calculating a move.
    private boolean hasMoved;
    private static final int SIZE = 8;
    
    /**
     * Constructor.  Gives the King a color and position.
     * @param color The color of the king. (WHITE/BLACK)
     * @param position The position of the King.
     */
    public King(Player color, Position position) {
        super(color, position);
        this.hasMoved = false;
    }
    
    /**
     * Checks if a move is valid.
     * Additionally checks if the move would result in the King being put into check.
     * 
     * @param board The current board state.
     * @param startTile The starting tile of the move.
     * @param endTile The ending tile of the move.
     * @return true if the move is valid.
     */
    @Override
    public boolean isValidMove(Board board, Tile startTile, Tile endTile) {
        if (isOccupiedBySameColor(endTile)) {
            System.out.println("Move invalid: End tile is occupied by same color."); // Debugging statement
            return false;
        }
        
        int xStart = startTile.getX();
        int yStart = startTile.getY();
        int xEnd = endTile.getX();
        int yEnd = endTile.getY();
        
        // Allows for movement by one square in any direction.
        if (Math.abs(xStart - xEnd) <= 1 && Math.abs(yStart - yEnd) <= 1) {
            System.out.println("King basic move check passed."); // Debugging statement
            
            // Simulates piece movement to prevent moving into check
            Piece capturedPiece = endTile.getPiece();
            endTile.occupyTile(this);
            startTile.releaseTile();
            boolean isInCheck = board.isPositionUnderAttack(new Position(xEnd, yEnd), this.getColor());
            
            // Undoes the move
            startTile.occupyTile(this);
            endTile.occupyTile(capturedPiece);
            
            System.out.println("Is in check after in move? " + isInCheck);
            return !isInCheck;
        }
        
        if (board.isKingInCheck(this.getColor())) {
            // Simulates the move and checks if the King is still in check
            Piece tempPiece = endTile.getPiece();
            endTile.occupyTile(this);
            startTile.releaseTile();
            
            boolean stillInCheck = board.isKingInCheck(this.getColor());
            
            // Undoes the move
            startTile.occupyTile(this);
            endTile.occupyTile(tempPiece);
            
            return !stillInCheck;
        }
        System.out.println("Move invalid: King can only move one square in any direction.");  // Debugging statement.
        return false;
    }
    
    /**
     * Generates a list of potential moves for the King.
     * 
     * @return A list of potential positions the King may move to.
     */
    @Override
    public List<Position> getPotentialMoves() {
        List<Position> moves = new ArrayList<>();
        int[][] directions = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1}
        };
        
        int currentRow = this.getPosition().getRow();
        int currentColumn = this.getPosition().getColumn();
        
        for (int[] direction : directions) {
            int nextRow = currentRow + direction[0];
            int nextColumn = currentColumn + direction[1];
            
            // Checks to see if position is within board limits
            if (nextRow >= 0 && nextRow < SIZE && nextColumn >= 0 && nextColumn < SIZE) {
                moves.add(new Position(nextRow, nextColumn));
            }
        }
        return moves;
    }
    
    /**
     * Sets hasMoved flag for the King.
     * Important for castling.
     * 
     * @param hasMoved  The state of the hasMoved flag.
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
    
    /**
     * Gets the image representing the King.
     * 
     * @return An image representing the King.
     */
    @Override
    public Image getImage() {
        String imageName = "/main/resources/images/" + (getColor() == Player.WHITE ? "White" : "Black") + "King.png";
        return new Image(imageName);
    }
}