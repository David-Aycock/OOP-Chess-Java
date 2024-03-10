package main.java;

import javafx.scene.paint.Color;

/**
 * Represents a single tile on the board.
 */
public class Tile {
    private final int x;
    private final int y;
    private Piece piece;
    private final Color color;
    
    /**
     * Constructor.  Creates a tile with a specific coordinate and color.
     * 
     * @param x The x-coordinate of the tile.
     * @param y The y-coordinate of the tile.
     * @param color The color of the tile.
     */
    public Tile(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
    
    /**
     * Checks if the tile is occupied by a piece.
     * 
     * @return true if the tile is occupied.
     */
    public boolean isOccupied() {
        return this.piece != null;
    }
    
    /**
     * Returns the piece occupying the tile if it exists.
     * 
     * @return The piece occupying the tile, otherwise null.
     */
    public Piece getPiece() {
        return this.piece;
    }
    
    /**
     * Places a piece on the tile.
     * 
     * @param piece The piece to place on the tile.
     */
    public void occupyTile(Piece piece) {
        this.piece = piece;
    }
    
    /**
     * Removes piece from the tile, making it unoccupied.
     */
    public void releaseTile() {
        this.piece = null;
    }
    
    /**
     * Gets the x-coordinate of the tile.
     * 
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }
    
    /**
     * Gets the y-coordinate of the tile.
     * 
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }
    
    /**
     * Gets the color of the tile.
     * 
     * @return The color of the tile.
     */
    public Color getColor() {
        return this.color;
    }
}