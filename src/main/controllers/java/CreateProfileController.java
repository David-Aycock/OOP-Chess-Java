// Packages and Imports
package main.controllers.java;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.resources.database.Profile;


// This begins the CreateProfileController class that is able to allow the
// user to input a profile name of their liking in order
// to create a new profile or to go back to the 
// Profile Selection screen.
public class CreateProfileController{


   // These variables are used to load up the stage,
   // (Stage being the actual window that opens)
   // the scene, (Scene being all the nodes of the fxml file or 
   // visually the contents of the window/"screen") and
   // the root (the root node of the fxml file).
    private Stage stage;
    private Scene scene;
    private Parent root;
    
  // This is a label and object within the fxml file
  // so @FXML must be here in order for the program to know
  // to look into the fxml. 
    @FXML
  // TextField from the CreateProfile fxml with the 
  // label "newProfile"
    private TextField newProfile;

  // These are variables that are used to create the profile
  // and store the profile's name
    static String profileName;
    static Profile test = new Profile();

    
    // This is a method that goes back to the previous scene (screen).
    // If the user changes their mind
    // about creating a new profile in Chess,
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
    
    // This is a method creates the profile using the name the user
    // has entered into the textfield within the CreateProfile fxml file.
    // With a click of the button, the profile will be created and the user
    // will be back to the Profile Selection screen.
    public void confirm(ActionEvent event) throws IOException{
       
        // Get the user's profile name from the textfield
        profileName = newProfile.getText();
        // using that name, create a profile
        test.createNewProfile(profileName);
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