// Packages and Imports
package main.controllers.java;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


// This begins the ColorSelectionController class that is able to change to the
// Time Control screen or back to the Opponent Selection screen
public class ColorSelectionController{

   // These variables are used to load up the stage,
   // (Stage being the actual window that opens)
   // the scene, (Scene being all the nodes of the fxml file or 
   // visually the contents of the window/"screen") and
   // the root (the root node of the fxml file).
    private Stage stage;
    private Scene scene;
    private Parent root;
    

    // This is a method that changes to the next scene (screen).
    // After the user picks a color they want to play in Chess (white or black).
    // With a click of the button, the screen will change to
    // now asking the user what time increment they would like to play
    // on.
    public void changeToTimeControl(ActionEvent event) throws IOException{
        // Set the root as the root node from the Time Control fxml file.
        root = FXMLLoader.load(getClass().getResource("/main/resources/fxml/TimeControl.fxml"));
        // Set the stage by getting the window and the scene (content of the window)
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        // Set the title of the stage to "Time Control"
        stage.setTitle("Time Control");
        // Set the scene by using the root node from the Time Control fxml file
        scene = new Scene(root);
        // Connect the scene with the stage
        stage.setScene(scene);
        // Show it to the user
        stage.show();
    
}
    // This is a method that goes back to the previous scene (screen).
    // If the user changes their mind
    // after picking an opponent they want to play with in Chess,
    // they can always go back to the Opponent Selection screen using the
    // back arrow button.
    // With a click of the button, the screen will change 
    // back to the Opponent Selection screen. 
    public void backToOpponentSelection(ActionEvent event) throws IOException{
        // Set the root as the root node from the Opponent Selection fxml file.
        root = FXMLLoader.load(getClass().getResource("/main/resources/fxml/OpponentSelection.fxml"));
        // Set the stage by getting the window and the scene (content of the window)
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        // Set the title of the stage to "Opponent Selection"
        stage.setTitle("Opponent Selection");
        // Set the scene by using the root node from the Opponent Selection fxml file
        scene = new Scene(root);
        // Connect the scene with the stage
        stage.setScene(scene);
        // Show it to the user
        stage.show();
    
}
}
