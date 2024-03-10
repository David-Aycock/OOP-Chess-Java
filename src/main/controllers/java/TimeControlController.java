// Packages and Imports
package main.controllers.java;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.ChessGameLauncher;


// This begins the TimeControlController class that is able to change to the
// chess board screen or back to the Color Selection screen
public class TimeControlController{

   // These variables are used to load up the stage,
   // (Stage being the actual window that opens)
   // the scene, (Scene being all the nodes of the fxml file or 
   // visually the contents of the window/"screen") and
   // the root (the root node of the fxml file).
    private Stage stage;
    private Scene scene;
    private Parent root;
    

    // This is a method that goes back to the previous scene (screen).
    // If the user changes their mind
    // after picking a color they want to play as in Chess,
    // they can always go back to the Color Selection screen using the
    // back arrow button.
    // With a click of the button, the screen will change 
    // back to the Color Selection screen. 
    public void backToColorSelection(ActionEvent event) throws IOException{
        // Set the root as the root node from the Color Selection fxml file.
        root = FXMLLoader.load(getClass().getResource("/main/resources/fxml/ColorSelection.fxml"));
        // Set the stage by getting the window and the scene (content of the window)
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        // Set the title of the stage to "Color Selection"
        stage.setTitle("Color Selection");
        // Set the scene by using the root node from the Color Selection fxml file
        scene = new Scene(root);
        // Connect the scene with the stage
        stage.setScene(scene);
        // Show it to the user
        stage.show();
    
    }
    
    // This method starts the chess game by
    // starting the ChessGameLauncher that contains
    // the dynamically created chess board.
    public void startGame(ActionEvent event) {
        // Variables relating to the time
        int timeInSeconds = 20 * 60;
        int incrementInSeconds = 30;
        // Variables relating to what color is each person
        // (Player and Opponent)
        String whitePlayerName = "Player1";
        String blackPlayerName = "Player2";
        
        // Launch ChessGameLauncher with parameters
        new Thread(() -> {
            Application.launch(ChessGameLauncher.class,
                    String.valueOf(timeInSeconds),
                    String.valueOf(incrementInSeconds),
                    whitePlayerName,
                    blackPlayerName);
        }).start();
    }
    
    // This method starts the chess game but now with the time the user selected
    // as parameters
    private void startGameWithTimeControl(ActionEvent event, int timeInSeconds, int incrementInSeconds) {
        // Create a new instance of the ChessGameLauncher
        ChessGameLauncher gameLauncher = new ChessGameLauncher();
        // Set the time parameters with the time the user selected
        gameLauncher.setTimeControl(timeInSeconds, incrementInSeconds);
        // create a new instance of the Stage
        Stage gameStage = new Stage();
        // Set the stage with the launcher
        gameLauncher.initializeUI(gameStage);
    }
    
    // This is triggered when the 1 | 0 button is clicked
    // The chess game will now be 1 minute with a 0 increment
    public void handleButton1Action(ActionEvent event) {
        System.out.println("Button 1 clicked - Starting game with 1 minute timer and 0 increment.");
        startGameWithTimeControl(event, 1 * 60, 0);
    }
    
    // This is triggered when the 3 | 0 button is clicked
    // The chess game will now be 3 minutes with a 0 increment
    public void handleButton2Action(ActionEvent event) {
        System.out.println("Button 1 clicked - Starting game with 3 minute timer and 0 increment.");
        startGameWithTimeControl(event, 3 * 60, 0);
    }
    
    // This is triggered when the 5 | 0 button is clicked
    // The chess game will now be 5 minutes with a 0 increment
    public void handleButton3Action(ActionEvent event) {
        System.out.println("Button 1 clicked - Starting game with 5 minute timer and 0 increment.");
        startGameWithTimeControl(event, 5 * 60, 0);
    }
    
    // This is triggered when the 10 | 0 button is clicked
    // The chess game will now be 10 minutes with a 0 increment
    public void handleButton4Action(ActionEvent event) {
        System.out.println("Button 1 clicked - Starting game with 10 minute timer and 0 increment.");
        startGameWithTimeControl(event, 10 * 60, 0);
    }
    
    // This is triggered when the 1 | 1 button is clicked
    // The chess game will now be 1 minute with a 1 increment
    public void handleButton5Action(ActionEvent event) {
        System.out.println("Button 1 clicked - Starting game with 1 minute timer and 1 increment.");
        startGameWithTimeControl(event, 1 * 60, 1);
    }
    
    // This is triggered when the 3 | 2 button is clicked
    // The chess game will now be 3 minutes with a 2 increment
    public void handleButton6Action(ActionEvent event) {
        System.out.println("Button 1 clicked - Starting game with 3 minute timer and 2 increment.");
        startGameWithTimeControl(event, 3 * 60, 2);
    }
    
    // This is triggered when the 5 | 2 button is clicked
    // The chess game will now be 5 minutes with a 2 increment
    public void handleButton7Action(ActionEvent event) {
        System.out.println("Button 1 clicked - Starting game with 5 minute timer and 2 increment.");
        startGameWithTimeControl(event, 5 * 60, 2);
    }
    
    // This is triggered when the 10 | 5 button is clicked
    // The chess game will now be 10 minutes with a 5 increment
    public void handleButton8Action(ActionEvent event) {
        System.out.println("Button 1 clicked - Starting game with 10 minute timer and 5 increment.");
        startGameWithTimeControl(event, 10 * 60, 5);
    }
}
    
