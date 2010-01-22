package org.greg.drexel.b1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.greg.drexel.a2.loc.LOCCounter;
import org.greg.drexel.b1.gui.MainFrame;
import org.greg.drexel.b1.io.MyFileReader;
import org.greg.drexel.b1.io.MyFileWriter;
import org.greg.drexel.b1.types.FileModeType;

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
		String fileLocation   = f.getFileLocation();
		
		// Always use FileModeType of READ to always read source code
		FileModeType mode     = FileModeType.READ;
		
		ArrayList<String> exclusion_contains = new ArrayList<String>();
		ArrayList<String> exclusion_startsWith = new ArrayList<String>();
		ArrayList<String> exclusion_endsWith = new ArrayList<String>();
		ArrayList<String> visualLogicalLOC = new ArrayList<String>();
		
		exclusion_startsWith.add("//");
		exclusion_startsWith.add("/*");
		exclusion_startsWith.add("*/");
		exclusion_startsWith.add("*");
		exclusion_startsWith.add("import ");
		
		
		    try
            {
		        // Start up a file reader and get the contents as an ArrayList<String>
		        MyFileReader reader = new MyFileReader( fileLocation );
		        ArrayList<String> fileContents = new ArrayList<String>();
		        fileContents = reader.getFileContents();
		        
		        // Don't add to the JList if there is nothing in the file
		        if( fileContents.size() > 0 )
		        {
		            LOCCounter logicalLOCCounter = new LOCCounter( exclusion_contains,exclusion_startsWith, exclusion_endsWith, true );
		            int logicalLOC = logicalLOCCounter.countLogicalLOC(fileContents);
		            int physicalLOC = logicalLOCCounter.countPhysicalLOC(fileContents);
		            visualLogicalLOC = logicalLOCCounter.getLogicalVisualLOC( fileContents );
		            
		           ArrayList<String> printToUser = new ArrayList<String>();
		            printToUser.add("Logical LOC: " + logicalLOC);
		            printToUser.add("Physical LOC: " + physicalLOC);
		            
		            
		            f.displayArrayList( printToUser );
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
		
		    try
            {
		        // Write the contents of their input (all Doubles/Real numbers) to the file they specified
                MyFileWriter writer = new MyFileWriter( fileLocation );
                //writer.setFileContents( visualLogicalLOC );
                //writer.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

	} // end main

} // end class
