package main.controllers.java;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import main.java.Board;
import main.java.ChessTimer;
import main.java.OnPieceCapturedListener;
import main.java.Piece;
import main.java.Position;
import main.java.Tile;

/**
 * Controls the chessboard's graphical user interface.
 * Also handles user interactions.
 */
public class BoardController implements OnPieceCapturedListener {
    // Constants for board size and tile size.
    private static final int SIZE = 8;
    private static final int TILE_SIZE = 75;
    
    // Logical and visual representations of the chess board.
    private final Board chessBoard = new Board();
    private final GridPane gridPane = new GridPane();
    
    // Game state variables.
    private Piece selectedPiece = null;
    private Piece.Player currentPlayer = Piece.Player.WHITE;
    
    // Timers for each player
    private ChessTimer whiteTimer;
    private ChessTimer blackTimer;
    
    // CREDIT: ChatGPT.  Java's version of a dictionary, where there's a key and value.
    // Stores original tile colors for highlighting purposes.
    private final Map<Position, Color> originalTileColors = new HashMap<>();
    
    // Containers for captured pieces.
    private FlowPane capturedPiecesWhite;
    private FlowPane capturedPiecesBlack;
    
    /**
     * Initializes the chess board GUI.
     */
    @FXML
    public void initialize() {
        createChessBoard();
    }
    
    /**
     * Handles captured pieces by adding them to the display.
     * 
     * @param piece The captured piece.
     */
    @Override
    public void onPieceCaptured(Piece piece) {
        addCapturedPiece(piece);
    }
    
    /**
     * Generates the chess board in memory.
     * Additionally sets the event handlers for when clicked on.
     * If there is a piece on the tile, it generates the image.
     */
    private void createChessBoard() {
        System.out.println("Creating Chess board"); // Debugging statement
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                // Create a visual tile and add it to the grid pane
                Tile tile = chessBoard.getTile(i, j);
                Rectangle square = new Rectangle(TILE_SIZE, TILE_SIZE, tile.getColor());
                
                // Set an event handler for mouse clicks on each tile
                square.setOnMouseClicked(this::handleTileClick);
                
                // Adds square to the grid pane
                gridPane.add(square, j, i);
                
                // If the tile has a piece, add its visual representation
                Piece piece = tile.getPiece();
                if (piece != null) {
                    addPieceView(i, j, piece);
                }
            }
        }
        System.out.println("Chess board created"); // Debugging statement
//        printGridPaneChildren(); // Debugging method.  Print the initial state of the GridPane.
    }
    
    /**
     * Designates the appropriate containers for the captured pieces.
     * 
     * @param white The container associated with the white pieces.
     * @param black The container associated with the black pieces.
     */
    public void setCapturedPiecesContainers(FlowPane white, FlowPane black) {
        this.capturedPiecesWhite = white;
        this.capturedPiecesBlack = black;
    }
    
    /**
     * Adds the image of the captured piece to the container at half size.
     * 
     * @param piece The captured piece to be added to the container.
     */
    public void addCapturedPiece(Piece piece) {
        if (piece == null) {
            System.out.println("Attempted to add a null piece to captured pieces.");
            return;
        }
        ImageView pieceView = new ImageView(piece.getImage());
        pieceView.setFitWidth(TILE_SIZE * 0.5);
        pieceView.setFitHeight(TILE_SIZE * 0.5);
        
        if (piece.getColor() == Piece.Player.WHITE) {
            capturedPiecesBlack.getChildren().add(pieceView);
        } else {
            capturedPiecesWhite.getChildren().add(pieceView);
        }
    }
    
    /**
     * Handles mouse clicks on tiles, potentially triggering piece movement.
     * 
     * @param event The mouse event.
     */
    @FXML
    private void handleTileClick(MouseEvent event) {
        System.out.print("Tile clicked"); // Debugging statement
        Node source = (Node) event.getSource();
        
        // Had some problems with pieces on the end returning null.  If a tile position returns null, changes it to 0.
        int col = GridPane.getColumnIndex(source) != null ? GridPane.getColumnIndex(source) : 0;
        int row = GridPane.getRowIndex(source) != null ? GridPane.getRowIndex(source) : 0;
        
        handleMove(row, col);
        
        System.out.println("Clicked on tile at row: " + row + ", column: " + col); // Debugging statement
    }
    
    /**
     * Moves a piece to a new position on the board, then switches to the other player's turn.
     * 
     * @param piece The piece to move.
     * @param newPosition The new position of the piece.
     */
    private void movePiece(Piece piece, Position newPosition) {
        System.out.println("Moving piece from " + piece.getPosition() + " to " + newPosition); // Debugging statement
        Position originalPosition = piece.getPosition();
        
        // Updates the backend board, then updates the view if the move is successful
        if (chessBoard.makeMove(originalPosition, newPosition)) {
            movePieceView(originalPosition, newPosition);
            piece.setPosition(newPosition);
            switchPlayer();
            addIncrementToTimer();
        }
        selectedPiece = null; // Deselects the piece after moving
        System.out.println("Piece moved"); // Debugging statement
//        printGridPaneChildren(); // Debugging method.  Print the initial state of the GridPane.
        
    }
    
    /**
     * Updates the visual representation of a moved piece.
     * 
     * @param start The starting position of the piece.
     * @param end The ending position of the piece.
     */
    private void movePieceView(Position start, Position end) {
        System.out.println("Updating piece view from " + start + " to " + end);
        
        // If tile occupied by a piece belonging to the other player, removes it before placing your piece there.
        // Visual representation of capturing a piece.
        ImageView pieceAtEnd = findPieceImageView(end);
        if (pieceAtEnd != null) {
            gridPane.getChildren().remove(pieceAtEnd);
        }
        
        // Finds the piece located on the start square.
        // If a piece exists, it removes the piece from the start square and adds it to the end square.
        ImageView pieceAtStart = findPieceImageView(start);
        if (pieceAtStart != null) {
            gridPane.getChildren().remove(pieceAtStart);
            gridPane.add(pieceAtStart, end.getColumn(), end.getRow());
        } else {
            System.out.println("Piece view update failed: Piece not found at start position");
        }
    }
    
    
    /**
     * Clears any highlighted tiles on the board.
     */
    private void clearHighlights() {
        System.out.println("Clearing highlights"); // Debugging statement
        
        // CREDIT: ChatGPT.  Reverts the node to the original color stored in the originalTileColors field.
        for (Map.Entry<Position, Color> entry : originalTileColors.entrySet()) {
            Node tileNode = findTileNode(entry.getKey());
            if (tileNode instanceof Rectangle) {
                ((Rectangle) tileNode).setFill(entry.getValue());
            }
        }
        originalTileColors.clear();
        System.out.println("Highlights cleared");
    }
    
    /**
     * Highlights potential moves for the selected piece.
     * 
     * @param moves  The list of potential moves.
     */
    private void highlightPotentialMoves(List<Position> moves) {
        System.out.println("Highlighting potential moves"); // Debugging statement
        
        // Debugging loop
        if (moves == null) {
            System.out.println("No potential moves (moves list is null)");
            return;
        }
        
        for (Position move : moves) {
            System.out.println("Checking move to " + move);
            
            // Check if each move is valid, then highlight it
            if (selectedPiece.isValidMove(chessBoard, chessBoard.getTile(selectedPiece.getPosition().getRow(), selectedPiece.getPosition().getColumn()), chessBoard.getTile(move.getRow(), move.getColumn()))) {

                // CREDIT: ChatGPT.  Part of highlighting available moves.  Stores the original color of the tile in the originalTileColors field.
                // Then sets the highlight color.
                Node tileNode = findTileNode(move);
                if (tileNode instanceof Rectangle) {
                    Rectangle tile = (Rectangle) tileNode;
                    originalTileColors.put(move, (Color) tile.getFill()); // Stores the original color
                    tile.setFill(new Color(1, 0, 0, 0.5));
                }
            }
        }
        System.out.println("Potential moves highlighted");
    }
    
    /**
     * Handles mouse clicks on pieces.  Selects them and shows potential moves by highlighting them.
     * 
     * @param event The mouse event.
     */
    @FXML
    private void handlePieceClick(MouseEvent event) {
        System.out.println("Piece clicked");
        Node source = (Node) event.getSource();
        
        // Ensures the source is an instance of ImageView
        if (!(source instanceof ImageView)) {
            System.out.println("Clicked source is not a piece");
            return;
        }
        // Gets the position location.
        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);
        
        // Handles null values for row and column indices
        if (colIndex == null || rowIndex == null) {
            System.out.println("Clicked on a piece with no position in the GridPane");
            return;
        }
        
        int col = colIndex;
        int row = rowIndex;
        
        System.out.println("Clicked on piece at row: " + row + ", column: " + col);
        
        // Calls a common method to handle the move
        handleMove(row, col);
        
        // Gets the tile and piece at clicked location
        Tile tile = chessBoard.getTile(row, col);
        Piece piece = tile.getPiece();
        
        
        if (piece !=null && piece.getPlayer() == chessBoard.getCurrentPlayer()) {
            selectedPiece = piece;
            clearHighlights();
            List<Position> potentialMoves = selectedPiece.getPotentialMoves();
            highlightPotentialMoves(potentialMoves);
        } else {
            System.out.println("No piece found at clicked tile");
        }
        
        // Consumes event to prevent from propagating to tile
        event.consume();
    }
    
    /**
     * Adds a visual representation of a piece to the grid pane.
     * 
     * @param x The x-coordinate on the grid.
     * @param y The y-coordinate on the grid.
     * @param piece The piece to be displayed.
     */
    public void addPieceView(int x, int y, Piece piece) {
        System.out.println("Adding piece view at row " + x + ", column: " + y);
        ImageView pieceView = new ImageView(piece.getImage());
        pieceView.setFitWidth(TILE_SIZE);
        pieceView.setFitHeight(TILE_SIZE);
        
        // Sets an event handler for mouse clicks on piece
        pieceView.setOnMouseClicked(this::handlePieceClick);
        
        // Add the piece's image to the grid pane
        gridPane.add(pieceView, y, x);
        System.out.println("Piece view added at row " + x + ", column: " + y);
    }
    
    /**
     * Gets the GridPane for the board.
     * 
     * @return The GridPane for the board.
     */
    public GridPane getChessBoardView() {
        System.out.println("Getting chess board view"); // Debugging statement
        return gridPane;
    }
    
    /**
     * Sets the timer for each player.
     * 
     * @param whiteTimer White's timer.
     * @param blackTimer Black's timer.
     */
    public void setTimers(ChessTimer whiteTimer, ChessTimer blackTimer) {
        this.whiteTimer = whiteTimer;
        this.blackTimer = blackTimer;
    }
    
    /**
     * Swaps the active player, activating and deactivating timers appropriately.
     */
    public void switchPlayer() {
        chessBoard.switchPlayer();
        currentPlayer = chessBoard.getCurrentPlayer();
        if (currentPlayer == Piece.Player.WHITE) {
            if (whiteTimer != null) whiteTimer.activate();
            if (blackTimer != null) blackTimer.deactivate();
        } else {
            if (whiteTimer != null) whiteTimer.deactivate();
            if (blackTimer != null) blackTimer.activate();
        }
    }
    
    /**
     * Finds and returns the ImageView of a piece at a given position.
     * 
     * @param position The position to search for a piece.
     * @return The ImageView of the piece.  Null if not found.
     */
    private ImageView findPieceImageView(Position position) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == position.getRow() && GridPane.getColumnIndex(node) == position.getColumn() && node instanceof ImageView) {
                return (ImageView) node;
            }
        }
        return null;
    }
    
    /**
     * Adds time increment to the timer of the player who just made a move.
     */
    public void addIncrementToTimer() {
        if (currentPlayer == Piece.Player.WHITE) {
            if (blackTimer != null) blackTimer.addIncrement();
        } else {
            if (whiteTimer != null) whiteTimer.addIncrement();
        }
        System.out.println("Increment added to timer"); // Debugging statement
    }
    
    // CREDIT: ChatGPT.
    /**
     * Finds and returns the node representing a tile at a given position.
     * 
     * @param position The position to search for a tile.
     * @return The node of a tile.  Null if not found.
     */
    private Node findTileNode(Position position) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == position.getRow() && GridPane.getColumnIndex(node) == position.getColumn() && node instanceof Rectangle) {
                return node;
            }
        }
        return null;
    }
    
    /**
     * Handles the logic for moving a piece based on user interaction.
     * 
     * @param row The row index of the move.
     * @param col The column index of the move.
     */
    private  void handleMove(int row, int col) {
        
        // If a piece is selected and the move is valid, move the piece
        if (selectedPiece != null) {
            Position newPosition = new Position(row, col);
            if (selectedPiece.isValidMove(chessBoard, chessBoard.getTile(selectedPiece.getPosition().getRow(), selectedPiece.getPosition().getColumn()), chessBoard.getTile(row, col))) {
                movePiece(selectedPiece, newPosition);
                clearHighlights();
            }
        }
    }
    
    /**
     * Returns the logical representation of the board.
     * 
     * @return The board.
     */
    public Board getChessBoard() {
        return chessBoard;
    }
}