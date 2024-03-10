package main.java;

import java.util.List;
import javafx.scene.image.Image;

/**
 * Abstract class representing a chess piece.  Provides the framework for all specific types
 * of chess pieces.
 * Includes common properties, i.e. color, position, and provides abstract methods to be 
 * overridden in subclasses.
 */
public abstract class Piece {
    /**
     * Enumeration for player color.
     * Went with enum for security - may only pass WHITE and BLACK as parameters.
     */
    public enum Player {
        WHITE,
        BLACK
    }
    private final Player color;
    private Position position;
    
    /**
     * Constructor.
     * 
     * @param color The color of the piece. (Must be WHITE or BLACK.)
     * @param position The initial position of the piece on the board.
     */
    public Piece(Player color, Position position) {
        this.color = color;
        this.position = position;
    }
    
    /**
     * Gets the color of the piece.
     * 
     * @return The color of the piece.
     */
    public Player getColor() {
        return color;
    }
    
    /**
     * Gets the position of the piece.
     * 
     * @return The current position of the piece.
     */
    public Position getPosition() {
        return position;
    }
    
    /**
     * Sets the position of the piece.
     * 
     * @param position The updated position of the piece.
     */
    public void setPosition(Position position) {
        this.position = position;
    }
    
    /**
     * Abstract method to generate all potential moves for the piece.
     * Subclasses take game state into account.
     * 
     * @return A list of potential moves.
     */
    public abstract List<Position> getPotentialMoves();
    
    /**
     * Abstract method to determine if a move is valid based on the current game state.
     * 
     * @param board The current game board state.
     * @param startTile The starting tile of the piece moving.
     * @param endTile The ending tile of the piece moving.
     * @return true if the move is valid.
     */
    public abstract boolean isValidMove(Board board, Tile startTile, Tile endTile);
    
    /**
     * Method to determine if the end tile is occupied by a piece of the same color.
     * Using this disallows the option for piece capture.
     * 
     * @param endTile The tile to be checked.
     * @return true if the end tile is occupied by a piece of the same color.
     */
    protected boolean isOccupiedBySameColor(Tile endTile) {
        Piece piece = endTile.getPiece();
        return piece != null && piece.getColor() == this.getColor();
    }
    
    /**
     * Gets the image representation of the piece.
     * The image is determined based on the piece's color and type.
     * 
     * @return The image of the piece.
     */
    public Image getImage() {
        String colorName = (this.color == Player.WHITE) ? "White" : "Black";
        String imageName = colorName + this.getClass().getSimpleName() + ".png";
        Image image = new Image(getClass().getResourceAsStream("/main/resources/images/" + imageName));
        return image;
    }
    
    /**
     * Gets the player color of the piece.
     * 
     * @return The player color.
     */
    public Player getPlayer() {
        return color;
    }
    
}