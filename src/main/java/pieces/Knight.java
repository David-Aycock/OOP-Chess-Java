package main.java.pieces;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import main.java.Tile;
import main.java.Piece;
import main.java.Board;
import main.java.Position;

/**
 * Represents a Knight.
 * Moves in an L-shape: two squares in one direction, then one square normal to that direction.
 */
public class Knight extends Piece {
    
    private static final int SIZE = 8;
    
    /**
     * Constructor.  Gives Knight color and position.
     * 
     * @param color The color of the Knight. (WHITE/BLACK)
     * @param position The position of the Knight.
     */
    public Knight(Player color, Position position){
        super(color, position);
    }
    
    /**
     * Checks if a move is valid.
     * Knight moves in an L-shape.
     * 
     * @param board The current board state.
     * @param startTile The starting tile for the move.
     * @param endTile The ending tile for the move.
     * @return true if the move is valid.
     */
    @Override
    public boolean isValidMove (Board board, Tile startTile, Tile endTile){
        if (isOccupiedBySameColor (endTile)){
            return false;
        }
        
        int xStart = startTile.getX();
        int yStart = startTile.getY();
        int xEnd = endTile.getX();
        int yEnd = endTile.getY();
        
        //Allows movement by 2 units in horizontal direction and 1 unit in vertical direction
        //Or movement by 1 unit in horizontal direction and 2 units in vertical direction
        if (Math.abs(xStart - xEnd) == 2 && Math.abs(yStart - yEnd) == 1){
            return true;
        } else if (Math.abs(xStart - xEnd) == 1 && Math.abs(yStart - yEnd) == 2){
            return true;
        }
        return false;
    }
    
    /**
     * Generates a list of potential moves.
     * 
     * @return List opf potential positions the Knight may move to.
     */
    @Override
    public List<Position> getPotentialMoves() {
        List<Position> moves = new ArrayList<>();
        int[][] directions = {
            {-2, 1}, {-2, -1}, {2, -1}, {2, 1},
            {1, -2}, {-1, -2}, {1, 2}, {-1, 2}
        };
        
        int currentRow = this.getPosition().getRow();
        int currentColumn = this.getPosition().getColumn();
        
        for (int[] direction : directions) {
            int nextRow = currentRow + direction[0];
            int nextColumn = currentColumn + direction[1];
            
            if (nextRow >= 0 && nextRow < SIZE && nextColumn >= 0 && nextColumn < SIZE) {
                moves.add(new Position(nextRow, nextColumn));
            }
        }
        return moves;
    }
    
    /**
     * Gets the image representing the Knight.
     * 
     * @return Image object representing the Knight.
     */
    @Override
    public Image getImage() {
        String imageName = "/main/resources/images/" + (getColor() == Player.WHITE ? "White" : "Black") + "Knight.png";
        return new Image(imageName);
    }
}