package org.greg.drexel.b1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

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
	 * MAIN METHOD FOR PROGRAM 1B
	 */
	public static void main(String[] args) 
	{
	    // Setup a Swing Frame
		MainFrame f = new MainFrame();
		
		// Ask the user for a file location
		String fileLocation   = f.getFileLocation("Create or Select File", ".txt files", "txt");
		
		// Ask the user for READ or WRITE mode for that file
		FileModeType mode     = f.getReadWriteModifyMode();
		 
		
		// Handle the READ case
		if( mode == FileModeType.READ )
		{
		    try
            {
		        // Start up a file reader and get the contents as an ArrayList<String>
		        // ArrayList<String> was chosen for versatility later, there is no need to require a Real number in this file.
		        MyFileReader reader = new MyFileReader( fileLocation );
		        ArrayList<String> fileContents = new ArrayList<String>();
		        fileContents = reader.getFileContents();
		        
		        // Don't add to the JList if there is nothing in the file
		        if( fileContents.size() > 0 )
		        {
		            f.displayArrayList( fileContents );
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
		}
		
		// Handle WRITE mode
		else if( mode == FileModeType.WRITE )
		{
		    // Ask the user for how many Real numbers they plan on inputting
		    Integer quantityOfNumbersToInput = f.getQuantityOfNumbers();
		    
		    // Ask the user for the quantity of numbers they specified above
		    ArrayList<String> inputNumbers = f.getNumbersInput( quantityOfNumbersToInput );
		    
		    try
            {
		        // Write the contents of their input (all Doubles/Real numbers) to the file they specified
                MyFileWriter writer = new MyFileWriter( fileLocation );
                writer.setFileContents( inputNumbers );
                writer.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            
            if( inputNumbers.size() > 0 )
	        {
	            f.displayArrayList( inputNumbers );
	        }
            
            // Show the window with the file contents
            f.initializeAndDisplay();
            
		}
		else if( mode == FileModeType.MODIFY )
		{
			try
            {
		        // Start up a file reader and get the contents as an ArrayList<String>
		        // ArrayList<String> was chosen for versatility later, there is no need to require a Real number in this file.
		        MyFileReader reader = new MyFileReader( fileLocation );
		        ArrayList<String> fileContents = new ArrayList<String>();
		        fileContents = reader.getFileContents();
		        f.initializeAndDisplayWithPrompt( fileContents );
		        
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
            
			
			
		}
		else
		{
		    System.err.println("Unsupported mode! Exiting!");
		    System.exit(-1);
		}

	} // end main

} // end class
