// Packages and Imports
package main.resources.database;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;


/**
 * @author colby
 * Below begins the Profile class that is able to add, delete, or find a specific profile
 * This class also creates an array list to store all the profiles from the CSV file for GUI use
 */
public class Profile {

   // These variables are used to create the profile
   // by having a profile name, the game wins, losses, and draws
    private String name = "";
    private int wins = 0;
    private int losses = 0;
    private int draws = 0;

    /**
     * Sets up the CSV file and writes the header
     */
    public Profile() {
        if (findRow("Name") != 0) {
            // Specify the csv file 
            String filepath = "Profile.csv";
            try {
                // Initialize the writer object 
                BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
                // write the header to the CSV file
                writer.write("Name, Wins, Losses, Draws\n");
                // close the writer
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Getter for name 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * The setter for name
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the wins value
     * @return wins
     */
    public int getWins() {
        return wins;
    }

    /** 
     * Setter for the wins value
     * @param wins 
     */
    public void setWins(int wins) {
        this.wins = wins;
    }

    /**
     * Getter for the losses value
     * @return losses
     */
    public int getLosses() {
        return losses;
    }

    /** 
     * Setter for the losses value 
     * @param losses 
     */
    public void setLosses(int losses) {
        this.losses = losses;
    }

    /**
     * Getter for the draw value
     * @return draws
     */
    public int getDraws() {
        return draws;
    }
    
    /**
     * Getter for the draw value
     * @param draws
     */
    public void setDraws(int draws) {
        this.draws = draws;
    }

    /**
     * This method is used to load profiles from the CSV file 
     * @return profiles
     */
    public static List<Profile> loadProfilesFromCSV() {
        // create a new arraylist to store the profiles
        List<Profile> profiles = new ArrayList<>();

        // read from the CSV file
        try (BufferedReader reader = new BufferedReader(new FileReader("Profile.csv"))) {
            String line;
            // Skip the header line
            reader.readLine();
            
            // get the name, wins, losses, and draws for each profile
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0].trim();
                int wins = Integer.parseInt(parts[1].trim());
                int losses = Integer.parseInt(parts[2].trim());
                int draws = Integer.parseInt(parts[3].trim());

                // create the profile
                Profile profile = new Profile();
                // set the name, wins, losses, and draws
                profile.setName(name);
                profile.setWins(wins);
                profile.setLosses(losses);
                profile.setDraws(draws);

                // add to the list of profiles
                profiles.add(profile);
            }
        } catch (IOException | NumberFormatException e) {
            Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // return the list of profiles
        return profiles;
    }
    
    
    
    /**
     * This method is for creating a new profile in the CSV file 
     * @param name 
     */
    public void createNewProfile(String name) {
        
        // Specify the csv file 
        String filepath = "Profile.csv";
        try {
            
            // Initialize the writer object 
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, true));
            
            // The row that allocated for the profile gets filled with 0's initially for the rows 
            writer.write(name + "," + wins + "," + losses + "," + draws + "\n");
            
            // Close the object/file 
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is for finding the row number of a particular given name 
     * @param name
     * @return index
     */
    public int findRow(String name) {
        
        // Specify the csv file 
        String filePath = "Profile.csv";
        
        // Initialize a currentLine variable that will be used for reading each line of the file for finding the index of the name
        String currentLine;
        
        // Initialize the index of the given name at 0 to be returned later when the index has been found 
        int index = 0;
        try {
            
            // Initialize two readers that work together to find the index of the given name
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            BufferedReader buffer = new BufferedReader(reader);
            
            // While the currentLine isn't null/empty 
            while ((currentLine = buffer.readLine()) != null) {
                
                // Initialize a String columns array that will store each value of the csv row into an array that considers every value before/after a comma to be a value 
                String[] columns = currentLine.split(",");
                
                // If the name column, column[0], matches the name that was the method argument, then close the files and return the index 
                if (columns[0].equals(name))  {
                    buffer.close();
                    reader.close(); 
                    return index;
                }
                
                // Increment the index if the name doesn't match the name in the row
                index++;
            }
            
            // Closes the files
            buffer.close();
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return index;
    }

    
    /**
     * This method will delete a profile off of the CSV file 
     * @param name 
     */
    public void deleteProfile(String name) {
        
        // Initialize the profile and the temporary profile that will be used later
        String filePath = "Profile.csv";
        String tempFile = "tempProfile.csv";
        
        // Initialize the index that has the index of the given name in the csv file
        int rowIndex = findRow(name);
        
        // Initialize a string variable that will be used for reading the csv file 
        String currentLine;
        
        // The index is also used for comparisons and reading the csv file 
        int index = 0;
        try {
            
            // Initialize the reader and writer objects to be used with the tempFile and the filePath variables 
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            
            // While the row that's being read is not empty, the following code runs 
            while ((currentLine = reader.readLine()) != null) {
                
                // If the index that was initialized earlier doesn't equal to the row that needs to be deleted, then write the row to the temp file
                // If the index does match to the index of the deleted row, then it doesn't get added to the temp file
                if (index != rowIndex) {
                    writer.write(currentLine + System.lineSeparator());
                }
                
                // Increment the index variable to move to the next index 
                index++;
            }
            
            // Close both the reader and writer files
            reader.close();
            writer.close();
            
            // Initialize the path of the source and the destination for overwriting 
            Path sourcePath = Path.of(tempFile);
            Path destinationPath = Path.of(filePath);
            
            // Copy the source file (temp) onto the destination file (profile) for overwritting
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
