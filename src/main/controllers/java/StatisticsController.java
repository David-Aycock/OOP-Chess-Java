// Packages and Imports
package main.controllers.java;

import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.resources.database.Profile;


// This begins the StatisticsController class that is able to show the user
// all game statistics for all profiles. This data will
// be gathered and shown to the screen using our CSV file. The user will be able
// to go back to the Profile Options screen from this screen as well. 
public class StatisticsController{

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
    // the table as a whole
    @FXML
    private TableView<Profile> tableView;
    // the name column
    @FXML
    private TableColumn<Profile, String> nameColumn;
    // the wins column
    @FXML
    private TableColumn<Profile, Integer> winsColumn;
    // the losses column
    @FXML
    private TableColumn<Profile, Integer> lossesColumn;
    // the draws column
    @FXML
    private TableColumn<Profile, Integer> drawsColumn;
    
    // This method creates the table in the Statistics fxml file
    public void initialize() {
        // Initialize columns
        // name
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        // wins
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));
        // losses
        lossesColumn.setCellValueFactory(new PropertyValueFactory<>("losses"));
        // draws
        drawsColumn.setCellValueFactory(new PropertyValueFactory<>("draws"));

        // Load profiles from CSV and populate TableView
        List<Profile> profiles = Profile.loadProfilesFromCSV();
        tableView.getItems().addAll(profiles);
    }

    // This is a method that goes back to the previous scene (screen).
    // If the user changes their mind
    // about viewing game statistics or is done viewing,
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

