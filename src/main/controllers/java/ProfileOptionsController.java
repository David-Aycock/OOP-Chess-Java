// Packages and Imports
package main.controllers.java;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


// This begins the ProfileOptionsController class that is able to change to the
// Statistics screen, the Delete Profile screen, or back to the 
// Profile Selection screen
public class ProfileOptionsController{

    
   // These variables are used to load up the stage,
   // (Stage being the actual window that opens)
   // the scene, (Scene being all the nodes of the fxml file or 
   // visually the contents of the window/"screen") and
   // the root (the root node of the fxml file).
    private Stage stage;
    private Scene scene;
    private Parent root;
    

    // This is a method that changes to the next scene (screen).
    // After the user picks to edit or view their profile, 
    // the user can view all game statistics for all profiles,
    // consisting of wins, loses, statemates, etc
    // With a click of the button, the screen will change to
    // the user's statistics
    public void changeToStatistics(ActionEvent event) throws IOException{
        // Set the root as the root node from the Statistics fxml file.
        root = FXMLLoader.load(getClass().getResource("/main/resources/fxml/Statistics.fxml"));
        // Set the stage by getting the window and the scene (content of the window)
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        // Set the title of the stage to "Statistics"
        stage.setTitle("Statistics");
        // Set the scene by using the root node from the Statistics fxml file
        scene = new Scene(root);
        // Connect the scene with the stage
        stage.setScene(scene);
        // Show it to the user
        stage.show();
        
    }

     // This is a method that changes to the next scene (screen).
     // After the user picks to edit or view their profile, 
     // the user can choose to delete their profile.
     // With a click of the button, the screen will change to
     // now asking the user if they are sure they want to delete their profile.
    public void changeToDeleteProfile(ActionEvent event) throws IOException{
        // Set the root as the root node from the Delete Profile fxml file.
        root = FXMLLoader.load(getClass().getResource("/main/resources/fxml/DeleteProfile.fxml"));
        // Set the stage by getting the window and the scene (content of the window)
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        // Set the title of the stage to "Delete Profile"
        stage.setTitle("Delete Profile");
        // Set the scene by using the root node from the Delete Profile fxml file
        scene = new Scene(root);
        // Connect the scene with the stage
        stage.setScene(scene);
        // Show it to the user
        stage.show();
    
}


    // This is a method that goes back to the previous scene (screen).
    // If the user changes their mind
    // after looking at their profile,
    // they can always go back to the Profile Selection screen using the
    // back arrow button.
    // With a click of the button, the screen will change 
    // back to the Profile Selection screen. 
   public void backToProfileSelection(ActionEvent event) throws IOException{
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
}

