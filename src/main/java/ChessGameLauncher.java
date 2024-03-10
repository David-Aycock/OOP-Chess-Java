// Packages and Imports
package main.java;

import java.util.List;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.controllers.java.BoardController;

/**
 * Main class for launching the Chess game application.  This is generated dynamically rather than through a .fxml file.
 * This class sets up the user interface and handles the game's main functionalities.
 */
public class ChessGameLauncher extends Application {
    
    // Timers and labels for each player
    private ChessTimer whiteTimer;
    private ChessTimer blackTimer;
    private Label timerTop;
    private Label timerBottom;
    private Timeline timeline;
    
    // GUI elements to display captured pieces
    private FlowPane capturedPiecesWhite = new FlowPane(5,5);
    private FlowPane capturedPiecesBlack = new FlowPane(5,5);
    
    // Parameters for calling the time control.
    private int timeInSeconds;
    private int incrementInSeconds;
    private String whitePlayerName;
    private String blackPlayerName;
    
    private static final int TILE_SIZE = 75;
    
    public ChessGameLauncher() {
        // Default values
        this.timeInSeconds = 20 * 60;
        this.incrementInSeconds = 30;
        this.whitePlayerName = "White Player";
        this.blackPlayerName = "Black Player";
    }
    
    /**
     * Sets the time control for the game.
     * 
     * @param timeInSeconds The main time for each player in seconds.
     * @param incrementInSeconds The increment per move in seconds.  When a move is made, the player gets this amount of time back.
     */
    public void setTimeControl(int timeInSeconds, int incrementInSeconds) {
        this.timeInSeconds = timeInSeconds;
        this.incrementInSeconds = incrementInSeconds;
    }
    
    /**
     * Sets the names of the players.
     * @param whitePlayerName The name of the white player.
     * @param blackPlayerName The name of the black player.
     */
    public void setPlayerNames(String whitePlayerName, String blackPlayerName) {
        this.whitePlayerName = whitePlayerName;
        this.blackPlayerName = blackPlayerName;
    }
    
    /**
     * Initializes the user interface for the Chess game.
     * 
     * @param primaryStage The primary stage for this application.
     */
    public void initializeUI(Stage primaryStage) {
        BoardController boardController = new BoardController();
        boardController.initialize();
        
        // Create a borderPane as the root for the scene
        BorderPane root = new BorderPane();
        
        // Set the Chess board in the center
        GridPane chessBoardView = boardController.getChessBoardView();
        HBox chessBoardContainer = new HBox(chessBoardView);
        chessBoardContainer.setAlignment(Pos.CENTER);
        root.setCenter(chessBoardContainer);
        
        boardController.getChessBoard().setOnPieceCapturedListener(boardController);
        
        // Initializes timers.  These will be variable when we combine everything, but for now it starts at 20 minutes.
        whiteTimer = new ChessTimer(20 * 60, 30);
        blackTimer = new ChessTimer(20 * 60, 30);
        boardController.setTimers(whiteTimer, blackTimer);
       
        // Initializes timer labels
        timerTop = new Label(blackTimer.getFormattedTime());
        timerBottom = new Label(whiteTimer.getFormattedTime());
        
        timerTop.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        timerBottom.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        
        // Captured pieces areas
        capturedPiecesWhite.setAlignment(Pos.CENTER);
        capturedPiecesBlack.setAlignment(Pos.CENTER);
        
        // Adds a light-grey background for captured pieces containers, easier to see pieces
        BackgroundFill backgroundCapFill = new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY);
        Background backgroundCap = new Background(backgroundCapFill);
        capturedPiecesWhite.setBackground(backgroundCap);
        capturedPiecesBlack.setBackground(backgroundCap);
        
        // Sets padding and spacing for the VBox containers
        capturedPiecesWhite.setPadding(new Insets(10));
        capturedPiecesBlack.setPadding(new Insets(10));
//        capturedPiecesWhite.setSpacing(10);
//        capturedPiecesBlack.setSpacing(10);
        
        int maxCapturedPiecesPerRow = 6;
        double capturedPieceSize = TILE_SIZE * 0.5;
        double containerWidth = capturedPieceSize * maxCapturedPiecesPerRow + 5 * (maxCapturedPiecesPerRow);
        double containerHeight = capturedPieceSize * 3;
        
        
        // Sets fixed size for captured pieces containers
        capturedPiecesWhite.setPrefSize(containerWidth, containerHeight);
        capturedPiecesBlack.setPrefSize(containerWidth, containerHeight);
        
        // Ensures the containers do not grow past preferred size
        capturedPiecesWhite.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        capturedPiecesBlack.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        
        // Passes the VBoxes to BoardController
        boardController.setCapturedPiecesContainers(capturedPiecesWhite, capturedPiecesBlack);
        
        // Adds captured pieces containers to the layout
        root.setLeft(capturedPiecesBlack);
        root.setRight(capturedPiecesWhite);
        
        
        
        // Settings button
        Button settingsButton = new Button("Settings");
        HBox topRightContainer = new HBox(settingsButton);
        topRightContainer.setAlignment(Pos.TOP_RIGHT);
        
        // Layout for top elements (timers and settings)
        HBox topLayout = new HBox(timerTop, topRightContainer);
        HBox.setHgrow(topRightContainer, Priority.ALWAYS); // Ensures settings button stays to the right
        
        // Layout for bottom timer
        HBox bottomLayout = new HBox(timerBottom);
        bottomLayout.setAlignment(Pos.BOTTOM_RIGHT);
        
        root.setTop(topLayout);
        root.setBottom(bottomLayout);
        
        // Setup and start the timeline for updating the timers
        setupTimeline();
        timeline.play();
        
        // Create the scene with the root pane
        Scene scene = new Scene(root, 1280, 720);
        
        // Set background image
        try {
            Image backgroundImage = new Image(getClass().getResourceAsStream("board_wood_texture_720.jpeg"));
            BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            root.setBackground(new Background(background));
        } catch (Exception e) {
            System.out.println("Background image loading error: " + e.getMessage());
        }
        
        whiteTimer.activate();
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Better Chess");
        primaryStage.show();
    }
    
    /**
     * Initialization method called before the application starts.
     * Processes command line arguments in order to pass in time controls.
     */
    // TO DO: Time control screen needs to pass in player profile strings from previous fxml file.
    @Override
    public void init() {
        List<String> args = getParameters().getRaw();
        
        if (args.size() >= 2) {
            setTimeControl(Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1)));
        }
        
        if (args.size() >= 4) {
            setPlayerNames(args.get(2), args.get(3));
        }
    }
    
    /**
     * Starts the JavaFX app.
     * This method is called after the init() method has returned.
     * 
     * @param primaryStage The primary stage for this application.
     * @throws Exception if an error occurs during application start.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        initializeUI(primaryStage);
    }
    
    /**
     * Sets up the timeline for updating the timers.
     * Controls the countdown of Chess timers.
     */
    private void setupTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimers()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }
    
    /**
     * Updates the timers every second.
     * Called periodically by the timeline.
     */
    private void updateTimers() {
        // Decrements time and update labels
        blackTimer.decrementTime();
        whiteTimer.decrementTime();
        timerTop.setText(blackTimer.getFormattedTime());
        timerBottom.setText(whiteTimer.getFormattedTime());
        
//        System.out.println("Updating timers: Top - " + blackTimer.getFormattedTime() + ", Bottom: " + whiteTimer.getFormattedTime());
    }
    
    /**
     * Main entry point for all JavaFX applications.
     * 
     * @param args Takes command line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}