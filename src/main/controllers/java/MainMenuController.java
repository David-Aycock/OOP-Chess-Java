// Packages and Imports
package main.controllers.java;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


// This begins the MainMenuController class that is able to change to the
// Player Selection screen, the Profile Selection screen, or to exit out of the application.
public class MainMenuController{
    
   // These variables are used to load up the stage,
   // (Stage being the actual window that opens)
   // the scene, (Scene being all the nodes of the fxml file or 
   // visually the contents of the window/"screen") and
   // the root (the root node of the fxml file).
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    
    // This is a method that changes to the next scene (screen).
    // If the user wants to start a game of Chess,
    // they can click on the "New Game" button.
    // With a click of the button, the screen will change to
    // now asking the user to select a profile them, the player, 
    // wants to play as
    public void changeToPlayerSelection(ActionEvent event) throws IOException{
        // Set the root as the root node from the Player Selection fxml file.
        root = FXMLLoader.load(getClass().getResource("/main/resources/fxml/PlayerSelection.fxml"));
        // Set the stage by getting the window and the scene (content of the window)
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        // Set the title of the stage to "Player Selection"
        stage.setTitle("Player Selection");
        // Set the scene by using the root node from the Player Selection fxml file
        scene = new Scene(root);
        // Connect the scene with the stage
        stage.setScene(scene);
        // Show it to the user
        stage.show();
        
    }
   
    // This is a method that changes to the next scene (screen).
    // If the user wants to create a profile or edit their profile,
    // they can click on the "Profile" button.
    // With a click of the button, the screen will change to
    // now asking the user if they want to create a profile or 
    // select a preexisting profile. 
    public void changeToProfileSelection(ActionEvent event) throws IOException{
        // Set the root as the root node from the Profile Selection fxml file.
        root = FXMLLoader.load(getClass().getResource("/main/resources/fxml/ProfileSelection.fxml"));
        // Set the stage by getting the window and the scene (content of the window)
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        // Set the title of the stage to "Profile Selection"
        stage.setTitle("Profile Selection");
        // Set the scene by using the root node from the Profile Selection fxml file
        scene = new Scene(root);
        // Connect the scene with the stage
        stage.setScene(scene);
        // Show it to the user
        stage.show();
        
    }
        
    // This is a method that exits out of the application.
    // After the user is done playing Chess.
    // With a click of the button, the user can close the application.
    public void exit(ActionEvent event) throws IOException{
        // Set the stage by getting the window and the scene (content of the window)
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        // Close it
        stage.close();
        
    } 
        
}
