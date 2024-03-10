// Packages and Imports
package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// This begins the Main class that starts the application on 
// the Main Menu screen
public class Main extends Application {
    
    // Start by opening up the Main Menu fxml file
    @Override
    public void start(Stage primaryStage) {
        try {
        // Set the parent root as the root node from the Main Menu fxml file.
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/fxml/MainMenu.fxml"));
        // set the title of the stage to "Main Menu"
        primaryStage.setTitle("Main Menu");
        // connect the scene with the stage
        primaryStage.setScene(new Scene(root));
        // show it to the user
        primaryStage.show();
        // exception catching
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    // Launch
    public static void main(String[] args) {
        launch(args);
    }
   
}
