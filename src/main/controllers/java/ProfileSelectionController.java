// Packages and Imports
package main.controllers.java;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


// This begins the ProfileSelectionController class that is able to change to the
// Create Profile screen, the Profile Options screen, or back to the Main Menu screen
public class ProfileSelectionController{
    
   // These variables are used to load up the stage,
   // (Stage being the actual window that opens)
   // the scene, (Scene being all the nodes of the fxml file or 
   // visually the contents of the window/"screen") and
   // the root (the root node of the fxml file).
    private Stage stage;
    private Scene scene;
    private Parent root;
    
   // This variable is used to store the profile name
  // from the profile button the user clicks. 
    public static String profileName;
    
  // This is a label and object within the fxml file
  // so @FXML must be here in order for the program to know
  // to look into the fxml. 
    @FXML
   // VBox from the ProfileSelection fxml with the 
   // label "profileButtonContainer"
    private VBox profileButtonContainer;

    // This method calls the loadProfilesFromCSV method
    @FXML
    private void initialize() {
        // Load profiles from CSV and populate the buttonContainer
        loadProfilesFromCSV();
    }

    
    // This is a method that reads from the CSV file and 
    // dynamically creates buttons into the ProfileSelection fxml file
    // based on the profile names found in the CSV file.
    // After the user picks a profile they want to delete or view stats for.
    // With a click of the button, the screen will change to
    // now asking the user if they want to see the stat or delete the profile.
    private void loadProfilesFromCSV() {
    // Read from the CSV file
    try (BufferedReader reader = new BufferedReader(new FileReader("Profile.csv"))) {
        String line;
        // Skip the header line
        reader.readLine();

        // Only get the profile name from the CSV file
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String name = parts[0].trim();

            // Create a button for each profile
            Button profileButton = new Button(name);
            // Set the action event handler using a lambda expression
            profileButton.setOnAction(e -> {
                try {
                    // Retrieve the name associated with the button
                    profileName = profileButton.getText();
                    // When a profile is selected, change to profile options screen
                    changeToProfileOptions(e);
                } catch (IOException ioException) {
                    ioException.printStackTrace();  // Handle the exception appropriately
                }
            });
            
            // Button customization
            // Button color
            profileButton.setStyle("-fx-background-color: #511111;");
            // Text color
            profileButton.setTextFill(javafx.scene.paint.Color.WHITE);
            // Button size
            profileButton.setPrefSize(300, 90);

            // Set the font to Century and size to 24
            Font centuryFont = Font.font("Century", FontWeight.NORMAL, FontPosture.REGULAR, 24);
            profileButton.setFont(centuryFont);

            // Add the button to the container
            profileButtonContainer.getChildren().add(profileButton);
        }
    } catch (IOException | NumberFormatException e) {
        Logger.getLogger(ProfileSelectionController.class.getName()).log(Level.SEVERE, null, e);
    }
}


    // This is a method that changes to the next scene (screen).
    // If the user would like to create a new profile after clicking
    // the profile button in the Main Menu.
    // With a click of the button, the screen will change to
    // now asking the user what they would like their profile name to be.
    public void changeToCreateProfile(ActionEvent event) throws IOException {
        // Set the root as the root node from the Create Profile fxml file.
        root = FXMLLoader.load(getClass().getResource("/main/resources/fxml/CreateProfile.fxml"));
        // Set the stage by getting the window and the scene (content of the window)
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        // Set the title of the stage to "Create Profile"
        stage.setTitle("Create Profile");
        // Set the scene by using the root node from the Create Profile fxml file
        scene = new Scene(root);
        // Connect the scene with the stage
        stage.setScene(scene);
        // Show it to the user
        stage.show();
}
   
    // This is a method that changes to the next scene (screen).
    // If the user would like to edit their profile after clicking
    // the profile button in the Main Menu.
    // With a click of the button, the screen will change to
    // now asking the user if they would like to see their game statisitics,
    // or delete their profile.
    public void changeToProfileOptions(ActionEvent event) throws IOException{
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
    
    // This is a method that goes back to the previous scene (screen).
    // If the user changes their mind about creating or editing a profile
    // they can always go back to the Main Menu screen using the
    // back arrow button.
    // With a click of the button, the screen will change 
    // back to the Main Menu screen. 
    public void backToMainMenu(ActionEvent event) throws IOException{
        // Set the root as the root node from the Main Menu Selection fxml file.
        root = FXMLLoader.load(getClass().getResource("/main/resources/fxml/MainMenu.fxml"));
        // Set the stage by getting the window and the scene (content of the window)
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        // Set the title of the stage to "Main Menu"
        stage.setTitle("Main Menu");
        // Set the scene by using the root node from the Main Menu fxml file
        scene = new Scene(root);
        // Connect the scene with the stage
        stage.setScene(scene);
        // Show it to the user
        stage.show();
    
}
}
