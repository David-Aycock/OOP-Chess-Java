// Packages and Imports
package main.controllers.java;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


// This begins the DeleteProfileController class that is able to allow the user
// to delete their profile by clicking a confirmation button or to go back to the
// Profile Options screen. 
public class DeleteProfileController{


   // These variables are used to load up the stage,
   // (Stage being the actual window that opens)
   // the scene, (Scene being all the nodes of the fxml file or 
   // visually the contents of the window/"screen") and
   // the root (the root node of the fxml file).
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    
    // This is a method that deletes the profile completely using the name attached to the profile
    // button the user selected.
    // With a click of the button, the profile will be deleted and the user
    // will be back to the Profile Selection screen.    
    public void confirm(ActionEvent event) throws IOException{
        
        // From the profile created in the CreateProfileController,
        // call the deleteprofile method using the name from the button selected
        // in the ProfileSelectionController.
        CreateProfileController.test.deleteProfile(ProfileSelectionController.profileName);
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
    
   
    // This is a method that goes back to the previous scene (screen).
    // If the user changes their mind
    // about deleting their profile,
    // they can always go back to the Profile Options screen using the
    // back arrow button or the cancel button.
    // With a click of the button, the screen will change 
    // back to the Profile Options screen. 
    public void backToProfileOptions(ActionEvent event) throws IOException{
        // Set the root as the root node from the Profile Options fxml file.
        root = FXMLLoader.load(getClass().getResource("/main/resources/fxml/ProfileOptions.fxml"));
        // Set the stage by getting the window and the scene (content of the window)
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        // Set the title of the stage to "Profile Options"
        stage.setTitle("Profile Options");
        // Set the scene by using the root node from the Profile Options fxml file
        scene = new Scene(root);
        // Connect the scene with the stage
        stage.setScene(scene);
        // Show it to the user
        stage.show();
        
    }
}
    

