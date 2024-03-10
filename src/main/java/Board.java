package main.java;

import java.util.ArrayList;
import java.util.List;
import main.java.pieces.*;
import javafx.scene.paint.Color;

/**
 * Represents the chess board, including the tiles, pieces, and game logic. Keeps track of the board state throughout the game.
 */
public class Board {
    
    
    private final Tile[][] board; // 2D array representing the tiles of the chess board.
    private Piece.Player currentPlayer = Piece.Player.WHITE;
    private final List<Piece> capturedPieces = new ArrayList<>();
    
    private OnPieceCapturedListener pieceCapturedListener;
    
    /**
     * Constructor for the board.  Initializes the board with tiles and places the pieces.
     */
    public Board() {
        this.board = new Tile[8][8];
        initializeBoard();
        placePieces();
    }
    
    /**
     * Initializes the tiles of the chess board
     */
    private void initializeBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Color tileColor = (i + j) % 2 == 0 ? Color.WHITE : Color.GRAY;
                board[i][j] = new Tile(i, j, tileColor);
            }
        }
    }
    
    /**
     * Returns the tile at a given coordinate.
     * @param x The x-coordinate of the tile.
     * @param y The y-coordinate of the tile.
     * @return The tile at the specified coordinates, or null if the coordinates are out of bounds.
     */
    public Tile getTile(int x, int y) {
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            return board[x][y];
        } else {
            return null;
        }
    }
    
    /**
     * Places Chess pieces on their initial positions on the board.
     */
    private void placePieces() {
        // Places pawns
        for(int i = 0; i < 8; i++) {
            board[1][i].occupyTile(new Pawn(Piece.Player.BLACK, new Position(1,i)));
            board[6][i].occupyTile(new Pawn(Piece.Player.WHITE, new Position(6,i)));
        }
        // Places other pieces
        // Rooks
        board[0][0].occupyTile(new Rook(Piece.Player.BLACK, new Position(0,0)));
        board[0][7].occupyTile(new Rook(Piece.Player.BLACK, new Position(0,7)));
        board[7][0].occupyTile(new Rook(Piece.Player.WHITE, new Position(7,0)));
        board[7][7].occupyTile(new Rook(Piece.Player.WHITE, new Position(7,7)));
        
        // Knights
        board[0][1].occupyTile(new Knight(Piece.Player.BLACK, new Position(0,1)));
        board[0][6].occupyTile(new Knight(Piece.Player.BLACK, new Position(0, 6)));
        board[7][1].occupyTile(new Knight(Piece.Player.WHITE, new Position(7,1)));
        board[7][6].occupyTile(new Knight(Piece.Player.WHITE, new Position(7,6)));
        
        // Bishops
        board[0][2].occupyTile(new Bishop(Piece.Player.BLACK, new Position(0,2)));
        board[0][5].occupyTile(new Bishop(Piece.Player.BLACK, new Position(0,5)));
        board[7][2].occupyTile(new Bishop(Piece.Player.WHITE, new Position(7,2)));
        board[7][5].occupyTile(new Bishop(Piece.Player.WHITE, new Position(7,5)));
        
        // Queens
        board[0][3].occupyTile(new Queen(Piece.Player.BLACK, new Position(0,3)));
        board[7][3].occupyTile(new Queen(Piece.Player.WHITE, new Position(7,3)));
        
        // Kings
        board[0][4].occupyTile(new King(Piece.Player.BLACK, new Position(0,4)));
        board[7][4].occupyTile(new King(Piece.Player.WHITE, new Position(7,4)));
        
    }
    
    /**
     * Executes a move from a start position to an end position.
     * @param start The starting position of the pieces.
     * @param end The ending position of the piece.
     * @return true if the move is made successfully.
     */
    public boolean makeMove(Position start, Position end) {
        Tile startTile = board[start.getRow()][start.getColumn()];
        Tile endTile = board[end.getRow()][end.getColumn()];
        Piece piece = startTile.getPiece();
        
        if (endTile.isOccupied()) {
            capturedPieces.add(endTile.getPiece());
            if (pieceCapturedListener != null) {
                pieceCapturedListener.onPieceCaptured(endTile.getPiece());
            }
        }
        /**
         * Releases the start tile tile so that it is no longer occupied by a piece, then occupies the end tile with the same piece.
         * Then updates the position in code.
         */
        startTile.releaseTile();
        endTile.occupyTile(piece);
        piece.setPosition(end);
        
        /**
         * If the piece that moved is a King or Pawn, sets hasMoved to true so that the King can no longer castle
         * and the Pawn can no longer move two spaces forward.
         */
        if (piece instanceof King) {
            ((King) piece).setHasMoved(true);
        }
        if (piece instanceof Pawn) {
            ((Pawn) piece).setHasMoved(true);
        }
        return true;
    }
    
    /**
     * Sets a listener for piece capture events.
     * 
     * @param listener The The listener is notified when a piece is captured.
     */
    public void setOnPieceCapturedListener(OnPieceCapturedListener listener) {
        this.pieceCapturedListener = listener;
    }
    
    /**
     * Switches the current player.
     */
    public void switchPlayer() {
        currentPlayer = (currentPlayer == Piece.Player.WHITE) ? Piece.Player.BLACK : Piece.Player.WHITE;
    }
    
    /**
     * Gets the current player.
     * @return The current player.
     */
    public Piece.Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    /**
     * Gets the list of captured pieces.
     * 
     * @return A list of captured pieces.
     */
    public List<Piece> getCapturedPieces() {
        return capturedPieces;
    }
    
    /**
     * Checks if the King of a specified color is in check.
     * 
     * @param kingColor The color of the King to check.
     * @return true if the King is in check.
     */
    public boolean isKingInCheck(Piece.Player kingColor) {
        Position kingPosition = findKingPosition(kingColor);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Tile tile = board[row][col];
                if (tile.isOccupied() && tile.getPiece().getColor() != kingColor) {
                    Piece piece = tile.getPiece();
                    if (piece.isValidMove(this, tile, board[kingPosition.getRow()][kingPosition.getColumn()])) {
                        System.out.println("Check detected by piece: " + piece.getClass().getSimpleName());
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Checks if there is a double check on the king of a specified color.
     * 
     * @param kingColor The color of the King to check.
     * @return true if the King is in double check.
     */
    public boolean isDoubleCheck(Piece.Player kingColor) {
        int checkCount = 0;
        Position kingPosition = findKingPosition(kingColor);
        Tile kingTile = getTile(kingPosition.getRow(), kingPosition.getColumn());
        
        // L:oops through all of the pieces and checks if they can attack the King
        for (Piece piece : getAllPiecesOfColor(oppositeColor(kingColor))) {
            Tile pieceTile = getTile(piece.getPosition().getRow(), piece.getPosition().getColumn());
            if (piece.isValidMove(this, pieceTile, kingTile)) {
                checkCount++;
                if (checkCount > 1) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Gets all pieces of a specified color.
     * 
     * @param color The color of the pieces to retrieve.
     * @return A list of pieces of the specified color.
     */
    private List<Piece> getAllPiecesOfColor(Piece.Player color) {
        List<Piece> pieces = new ArrayList<>();
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Tile currentTile = getTile(i, j);
                if (currentTile.isOccupied()) {
                    Piece piece = currentTile.getPiece();
                    if (piece.getColor() == color) {
                        pieces.add(piece);
                    }
                }
            }
        }
        return pieces;
    }
    
    /**
     * Processes a move, including validation and execution.
     * 
     * @param start The starting position of the piece
     * @param end The ending position of the piece
     * @return true if the move is valid and executed
     */
    public boolean processMove(Position start, Position end) {
        Tile startTile = getTile(start.getRow(), start.getColumn());
        Tile endTile = getTile(end.getRow(), end.getColumn());
        Piece piece = startTile.getPiece();
        
        // Problem method - King cannot move into check, but is not required to move out of check.
        // Possibly create a boolean?  While boolean is true, King cannot move unless it sets boolean to false?
        if (piece != null && piece.isValidMove(this, startTile, endTile)) {
            // Simulates the move
            Piece capturedPiece = endTile.getPiece();
            endTile.occupyTile(piece);
            startTile.releaseTile();
            piece.setPosition(end);
            
            boolean wasInCheck = isKingInCheck(currentPlayer);
            boolean isInCheckAfterMove = isKingInCheck(currentPlayer);
            
            /// Undoes the move
            startTile.occupyTile(piece);
            endTile.occupyTile(capturedPiece);
            piece.setPosition(start);
            
            if (wasInCheck && isInCheckAfterMove) {
                // Move does not resolve check
                return false;
            } else if (!wasInCheck && isInCheckAfterMove) {
                // Move puts King in check
                return false;
            }
            
            // If not in check, makes the move
            makeMove(start, end);
            return true;
        }
        return false;
    }
    
    /**
     * Determines the opposing color of the given player color.
     * 
     * @param color The color of the player.
     * @return The opposing side's color.
     */
    private Piece.Player oppositeColor(Piece.Player color) {
        return color == Piece.Player.WHITE ? Piece.Player.BLACK : Piece.Player.WHITE;
    }
    
    /**
     * Checks if the king is still in check after a simulated move.
     * @param start The starting position of the move.
     * @param end The ending position of the move.
     * @return true if the King is still in check after the move.
     */
    // Tried using this to force King to move out of check.  Still struggling.
    private boolean stillInCheckAfterMove(Position start, Position end) {
        Tile startTile = getTile(start.getRow(), start.getColumn());
        Tile endTile = getTile(end.getRow(), end.getColumn());
        Piece movingPiece = startTile.getPiece();
        Piece capturedPiece = endTile.getPiece();
        
        if (movingPiece == null) {
            return false;
        }
        
        // Simulates the move
        endTile.occupyTile(movingPiece);
        startTile.releaseTile();
        movingPiece.setPosition(end);
        
        // Check if the Kind is still in check after this move
        boolean stillInCheck = isKingInCheck(movingPiece.getColor());
        
        // Undo the move
        startTile.occupyTile(movingPiece);
        endTile.occupyTile(capturedPiece);
        movingPiece.setPosition(start);
        
        return stillInCheck;
    }
    
    /**
     * Finds the position of the King of a specified color.
     * 
     * @param kingColor The color of the King to find.
     * @return The position of the King, or null if not found.
     */
    private Position findKingPosition(Piece.Player kingColor) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Tile tile = board[row][col];
                if (tile.isOccupied() && tile.getPiece() instanceof King && tile.getPiece().getColor() == kingColor) {
                    return new Position(row, col);
                }
            }
        }
        return null;
    }
    
    /**
     * Checks if a position is under attack by pieces of a specified color.
     * 
     * @param position The position to check.
     * @param kingColor The color of the King, opposite of the attacking pieces.
     * @return true if the position is under attack.
     */
    public boolean isPositionUnderAttack(Position position, Piece.Player kingColor) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Tile tile = board[row][col];
                if (tile.isOccupied() && tile.getPiece().getColor() != kingColor) {
                    Piece piece = tile.getPiece();
                    // Avoid calling isValidMove on the King to prevent recursion
                    if (!(piece instanceof King) && piece.isValidMove(this, tile, board[position.getRow()][position.getColumn()])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}