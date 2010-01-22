package org.greg.drexel.b1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.greg.drexel.a2.loc.CountingStandard;
import org.greg.drexel.a2.loc.LOCCounter;
import org.greg.drexel.b1.gui.MainFrame;
import org.greg.drexel.b1.io.MyFileReader;
import org.greg.drexel.b1.io.MyFileWriter;

/**
 * @author Greg Vannoni
 * @class INFO 636
 *
 * Purpose: This is the main class that does all of the work.
 *  - Initialize the GUI
 *  - Ask user for new file name or existing file name
 *  - Ask for user input for READ or WRITE mode
 *    - If write mode is selected, it ALWAYS will append to a file, if it exists
 *  - If READ mode: Display the contents of the file in a JList
 *  - If WRITE mode: 
 *    - Ask the user for how many Real numbers they plan to input
 *    - Get the input from the user
 *    - Write this input to a file
 *    
 * @version 1.0
 * 
 * Notes:
 */
public class Main {

	/***************************************************************************************************
	 * MAIN METHOD FOR PROGRAM 2A
	 */
	public static void main(String[] args) 
	{
	    // Setup a Swing Frame
		MainFrame f = new MainFrame();
		
		// Ask the user for a file location
		File fileLocation   = f.getFileLocation();
		
		ArrayList<String> exclusion_contains = new ArrayList<String>();
		ArrayList<String> exclusion_startsWith = new ArrayList<String>();
		ArrayList<String> exclusion_endsWith = new ArrayList<String>();
		ArrayList<String> visualLogicalLOC = new ArrayList<String>();
		
		exclusion_startsWith.add("//");
		exclusion_startsWith.add("/*");
		exclusion_startsWith.add("*/");
		exclusion_startsWith.add("*");
		exclusion_startsWith.add("{");
		exclusion_startsWith.add("}");
		exclusion_startsWith.add("import ");
		
		CountingStandard countingStandard = new CountingStandard(exclusion_contains, exclusion_startsWith, exclusion_endsWith, true );
		
        try
        {
            // Start up a file reader and get the contents as an ArrayList<String>
            MyFileReader reader = new MyFileReader( fileLocation.getAbsolutePath() );
            ArrayList<String> fileContents = new ArrayList<String>();
            fileContents = reader.getFileContents();
            
            if( fileContents.size() > 0 )
            {
                // Put the code scanned from above into the LOC counter and put the output into a file
                LOCCounter logicalLOCCounter = new LOCCounter( countingStandard );
                visualLogicalLOC = logicalLOCCounter.getLogicalVisualLOC( fileContents );
                
                MyFileWriter writer = new MyFileWriter( "tmp.txt" );
                writer.setFileContents( visualLogicalLOC );

                // Display the converted file (based on counting standards) to the user along with the count of LOC.
                File tmpFile = new File("tmp.txt");
                MyFileReader readLogicalFileLOC = new MyFileReader( tmpFile.getAbsolutePath() );
                visualLogicalLOC = readLogicalFileLOC.getFileContents();
                tmpFile.delete();
                
                
                // Inform the user of the LOC and print the file contents
                visualLogicalLOC.add(0, "----------------" );
                visualLogicalLOC.add(0, "Logical Lines of Code: " + visualLogicalLOC.size() );
                visualLogicalLOC.add(0, "Physical Lines of Code: " + fileContents.size() );
                f.displayArrayList( visualLogicalLOC );
            }
        }
        catch (FileNotFoundException e)
        {
            // If the file isn't found, inform the user
            f.displayError("File does not exist!", "File not found" );
            System.exit(-3);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        // Show the window with the file contents
        f.initializeAndDisplay();
    
	} // end main

} // end class
