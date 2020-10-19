package seedu.duke.storage;

import seedu.duke.data.UserData;
import seedu.duke.event.Event;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Saves and loads the events list to and from an external txt file.
 */
public class Storage {
    public Path fileNamePath;
    public Path fileDirectoryPath;
    public Path filePersonalPath;
    public Path fileZoomPath;
    public Path fileTimeTablePath;
    public Path fileGoalPath;



    /**
     * Creates a new storage manager that can load and save files to and from a given filepath.
     *
     * @param initPath is the name of the filepath which files are saved to and loaded from
     */
    public Storage(String initPath) {

        //firstly, make string representation of storage files
        String personal = initPath + ",personal.txt";
        String zoom = initPath + ",zoom.txt";
        String goal = initPath + ",goal.txt";
        String timeTable = initPath + ",timetable.txt";

        //Directory words only contain info on making the folder
        //File words contain the info on how to make the file itself
        String[] pathDirectoryWords = initPath.split(",");
        String[] personalWords = personal.split(",");
        String[] zoomWords = zoom.split(",");
        String[] goalWords = goal.split(",");
        String[] timeTableWords = timeTable.split(",");

        fileDirectoryPath = createPath(pathDirectoryWords);
        filePersonalPath = createPath(personalWords);
        fileZoomPath = createPath(zoomWords);
        fileGoalPath = createPath(goalWords);
        fileTimeTablePath = createPath(timeTableWords);

    }

    /**
     * Creates a folder for the events list if it does not exist yet.
     */
    protected void initialiseFolder() {
        if (!Files.exists(fileNamePath)) {
            try {
                Files.createDirectory(fileDirectoryPath);
            } catch (IOException e) {
                System.out.println("IO exception encountered when creating data directory.");
            }
        }
    }

    /**
     * Saves events to an external txt file.
     *
     * @param fileName is the file to save events to
     * @param eventsList is the events list to be saved to an external txt file
     * @throws IOException if the named file does not exist and cannot be created, or cannot be opened
     */
    public void saveFile(String fileName, ArrayList<Event> eventsList) throws IOException {
        FileWriter fw = new FileWriter(fileName);
    }

    /**
     * Loads events from an external txt file.
     *
     * @param fileName is the file to load events from
     * @throws FileNotFoundException if no file with the given fileName is found
     */
    public void loadFile(Path fileName, UserData data) {

        try {

            //First, extract out all the file information
            List<String> fileLines = Files.readAllLines(fileName);

            //Next, line by line reform the event
            for (int i = 0; i < fileLines.size(); i++) {
                String line = fileLines.get(i);
            }

            //finally, store the information in the correct list
        } catch (IOException e) {
            //do nothing
        }
    }


    /**
     * Function accepts a string and creates a path object originating from the user directory.
     *
     * @param pathName is a string array which accepts in the path name words, each word represents a folder
     * @return Path object indicating the location of the pathName keyed in initially.
     */
    private Path createPath(String[] pathName) {

        String origin = System.getProperty("user.dir");
        Path newPath = Paths.get(origin, pathName);
        return newPath;
    }

    /**
     * Function gives a string containing the Directory location.
     *
     * @return String containing directory location
     */
    public String getDirectoryString() {
        return fileDirectoryPath.toString();
    }

    /**
     * Function gives a string containng the file full location path name.
     *
     * @return String containing the file's location
     */
    public String getFileLocationString() {
        return fileNamePath.toString();
    }
}
