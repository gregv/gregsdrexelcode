package org.greg.drexel.b1;

import java.io.File;
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
 *  - Ask for user input for READ. WRITE  or MODIFY mode
 *    - If write mode is selected, it will NOT append to a file, if it exists
 *  - If READ mode: Display the contents of the file in a JList
 *  - If WRITE mode: 
 *    - Ask the user for how many Real numbers they plan to input
 *    - Get the input from the user
 *    - Write this input to a file
 *  - If MODIFY mode:
 *    - Ask the user if they wish to Replace, Insert, Delete, Accept All, or Accept
 *    
 *  - Added feature of File > Save As... capability per Program 2B spec.
 *    
 * @version 3.0
 * 
 * Notes:
 */
public class Main {


    public static String getFileLocation( MainFrame f )
    {
        String fileLocation = null;
        
        // Ask the user for a file location
        fileLocation   = f.getFileLocation("Create or Select File", ".txt files", "txt");
        
        return fileLocation;
    }
	/***************************************************************************************************
	 * MAIN METHOD FOR PROGRAM 2B
	 */
	public static void main(String[] args) 
	{
	    // Setup a Swing Frame
		MainFrame f = new MainFrame();
		
		String fileLocation = getFileLocation( f );
		
		// Ask the user for READ or WRITE mode for that file
		FileModeType mode     = f.getReadWriteModifyMode();
		 
		
		// Handle the READ case
		if( mode == FileModeType.READ )
		{
		    try
            {
		        // Verify that the file about to be read from exists
		        boolean exit = false;
		        while( !exit )
		        {
		           File fileToRead = new File( fileLocation );
		           if( !fileToRead.exists() )
		           {
		               f.displayWarning("The file you selected does not exist for reading!", "File does not exist!");
		               fileLocation = getFileLocation( f );
		           }
		           else
		           {
		               exit = true;
		           }
		        }
		        
		        
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
		    // Verify that the file about to be written to exists
            boolean exit = false;
            while( !exit )
            {
               File fileToRead = new File( fileLocation );
               if( fileToRead.exists() )
               {
                   f.displayWarning("The file you selected already exists, will not write to existing file!", "File already exists!");
                   fileLocation = getFileLocation( f );
               }
               else
               {
                   exit = true;
               }
            }
            
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
			    // Verify that the file about to be modified from exists
                boolean exit = false;
                while( !exit )
                {
                   File fileToRead = new File( fileLocation );
                   if( !fileToRead.exists() )
                   {
                       f.displayWarning("The file you selected does not exist for modification!", "File does not exist!");
                       fileLocation = getFileLocation( f );
                   }
                   else
                   {
                       exit = true;
                   }
                }
			    
		        // Start up a file reader and get the contents as an ArrayList<String>
		        // ArrayList<String> was chosen for versatility later, there is no need to require a Real number in this file.
		        MyFileReader reader = new MyFileReader( fileLocation );
		        ArrayList<String> fileContents = new ArrayList<String>();
		        fileContents = reader.getFileContents();
		        
		        // Because this is a modify operation, prompt the user for action
		        f.initializeAndDisplayWithPrompt( fileContents );
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
