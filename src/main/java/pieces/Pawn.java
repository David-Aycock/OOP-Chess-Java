package main.java.pieces;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import main.java.Tile;
import main.java.Piece;
import main.java.Board;
import main.java.Position;

/**
 * Represents a Pawn.
 * Pawns move forward one square, but capture diagonally.  They have an initial option to move forward two squares.
 * Additionally, they may capture via En Passant, but I didn't have enough time to implement this.
 */
public class Pawn extends Piece {
    private boolean hasMoved;
    
    /**
     * Constructor.  Creates Pawn with color and position.
     * 
     * @param color The color of the Pawn. (WHITE/BLACK)
     * @param position The position of the Pawn.
     */
    public Pawn(Player color, Position position){
        super(color, position);
        this.hasMoved = false;
    }
    
    /**
     * Checks if a move is valid.
     * Pawns move forward one square, capture diagonally.  May move forward two squares from their starting position.
     * 
     * @param board Current board state.
     * @param startTile Starting tile of the move.
     * @param endTile Ending tile of the move.
     * @return true if the move is valid.
     */
    @Override
    public boolean isValidMove (Board board, Tile startTile, Tile endTile){
        if (isOccupiedBySameColor (endTile)){
            return false;
        }
        
        int startRow = startTile.getX();
        int startColumn = startTile.getY();
        int endRow = endTile.getX();
        int endColumn = endTile.getY();
        
        // If White, returns a 1 to multiply against the values.  If black, changes the values to negative so they move in the opposite direction.
        int direction = this.getColor() == Player.WHITE ? -1 : 1;
        
        
        // Normal move
        if (startColumn == endColumn && board.getTile(endRow, endColumn).getPiece() == null) {
            if (startRow + direction == endRow) {
                return true;
            }
            if (!hasMoved && startRow + (2 * direction) == endRow) {
                // Check if the path is clear for a two-square move
                return board.getTile(startRow + direction, startColumn).getPiece() == null;
            }
        }
        
        // Capture move
        if (Math.abs(startColumn - endColumn) == 1 && startRow + direction == endRow) {
            return board.getTile(endRow, endColumn).getPiece() != null;
        }
        
        return false;
    }
    
    /**
     * Generates a list of potential moves.
     * 
     * @return A list of potential positions the Pawn may move to.
     */
    @Override
    public List<Position> getPotentialMoves() {
        List<Position> moves = new ArrayList<>();
        int row = this.getPosition().getRow();
        int column = this.getPosition().getColumn();
        int direction = this.getColor() == Player.WHITE ? -1 : 1;
        
        // Forward move
        if (isMoveWithinBoard(row + direction, column)) {
            moves.add(new Position(row + direction, column));
            if (!hasMoved && isMoveWithinBoard(row + (2 * direction), column)) {
                moves.add(new Position(row + (2 * direction), column));
            }
        }
        
        // Capture moves
        if (isMoveWithinBoard(row + direction, column + 1)) {
            moves.add(new Position(row + direction, column + 1));
        }
        if (isMoveWithinBoard(row + direction, column - 1)) {
            moves.add(new Position(row + direction, column - 1));
        }
        
        return moves;
    }
    
    
    /**
     * Checks if a move is within the boundaries of the board.
     * 
     * @param row The row index of the move.
     * @param column The column index of the move.
     * @return true if the move is within the board.
     */
    private boolean isMoveWithinBoard(int row, int column) {
        return row >= 0 && row < 8 && column >= 0 && column < 8;
    }
    
    /**
     * Sets the flag indicating whether the Pawn has moved.
     * Helps to determine if the Pawn may move two squares.
     * 
     * @param hasMoved true if the Pawn has moved.
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
    
    /**
     * Gets the image representing the Pawn.
     * 
     * @return The Image representing the Pawn.
     */
    @Override
    public Image getImage() {
        String imageName = "/main/resources/images/" + (getColor() == Player.WHITE ? "White" : "Black") + "Pawn.png";
        return new Image(imageName);
    }
}