package org.greg.drexel.b1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.greg.drexel.b1.gui.MainFrame;
import org.greg.drexel.b1.io.MyFileReader;
import org.greg.drexel.b1.io.MyFileWriter;
import org.greg.drexel.b1.types.FileModeType;
import org.greg.drexel.b6.regression.RegressionCalc;

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
 *  - Added filename checking (validity and per read/write/modify mode) per Program 4B spec
 *  - Added 2-D array support per Program 5B spec
 *  - Added support for regression estimation per program 6B and 7B spec
 *  
 * @version 7.0
 * 
 * Notes:
 * - MAKE SURE to update ENTRIES_PER_ROW below for the number of columns to
 *  want to support
 * 
 */
public class Main {

    // This is the K value alluded to in assignment 5B
    //  Users will enter in ENTRIES_PER_ROW for each row
    public static final int ENTRIES_PER_ROW = 5;
    
    
    
    /**
     * Method: getFileLocation<br/>
     * Displays the file chooser for a user to select or name their own file
     * 
     * @param f - The MainFrame object
     * @return the full file path of the file the user has selected to create
     */
    public static String getFileLocation( MainFrame f )
    {
        String fileLocation = null;
        
        // Ask the user for a file location
        fileLocation   = f.getFileLocation("Create or Select File", ".txt files", "txt");
        
        return fileLocation;
    }
    
    
	/***************************************************************************************************
	 * MAIN METHOD
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
		        ArrayList<ArrayList<String>> fileContents = new ArrayList<ArrayList<String>>();
		        fileContents = reader.getFileContents();
		        
		        // Don't add to the JList if there is nothing in the file
		        if( fileContents.size() > 0 )
		        {
		            for( ArrayList<String> arr : fileContents )
		            {
		                if( arr.size() != ENTRIES_PER_ROW )
		                {
		                    f.displayError("The K in the application does not match the number of columns in the data!\nK = " + ENTRIES_PER_ROW + ", Number of columns = " + arr.size(), "Column number mismatch!");
		                    System.exit(-20);
		                }
		            }
		            
		            f.displayArrayList( fileContents );
		            
		            
		            double estimatedValue = f.getSingleNumberInput(0, 0, "Estimated Object LOC (E)");
		            // Calculate and display regression numbers
		            RegressionCalc rc = new RegressionCalc( fileContents );
		            rc.calculateSizeEstimateRegression(estimatedValue);
		            
		            f.displaySingleRow("---");
		            f.displaySingleRow("Size Regression (columns 2 and 3)");
		            f.displaySingleRow( "Beta 1 = " + rc.getSupportingRegressionValues().get(RegressionCalc.BETA1) );
		            f.displaySingleRow( "Beta 0 = " + rc.getSupportingRegressionValues().get(RegressionCalc.BETA0) );
		            f.displaySingleRow( "RSquared = " + rc.getSupportingRegressionValues().get(RegressionCalc.RSQUARED) );
		            f.displaySingleRow( "Estimated size = " + rc.getSupportingRegressionValues().get(RegressionCalc.ESTIMATED_VALUE) + " LOC");
	                f.displaySingleRow( "Predicted size = " + rc.getSupportingRegressionValues().get(RegressionCalc.PREDICTED_VALUE) + " LOC");
	                
		            
		            
		            rc.calculateTimeEstimateRegression(estimatedValue);
		            f.displaySingleRow("Time Regression (columns 2 and 5)");
		            f.displaySingleRow( "Beta 1 = " + rc.getSupportingRegressionValues().get(RegressionCalc.BETA1) );
                    f.displaySingleRow( "Beta 0 = " + rc.getSupportingRegressionValues().get(RegressionCalc.BETA0) );
                    f.displaySingleRow( "RSquared = " + rc.getSupportingRegressionValues().get(RegressionCalc.RSQUARED) );
                    f.displaySingleRow( "Estimated size = " + rc.getSupportingRegressionValues().get(RegressionCalc.ESTIMATED_VALUE) + " LOC");
                    f.displaySingleRow( "Predicted time = " + rc.getSupportingRegressionValues().get(RegressionCalc.PREDICTED_VALUE) + " minutes" );
		            
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
            
		    // Ask the user for how many rows of Real numbers they plan on inputting
		    Integer quantityOfNumbersToInput = f.getQuantityOfNumbers();
		    
		    ArrayList<ArrayList<String>> inputNumbers = new ArrayList<ArrayList<String>>();
		    
		    // For as many ENTRIES_PER_ROW ask for input for these number of columns of data
		    for( int i=0; i<quantityOfNumbersToInput; i++ )
		    {
		        ArrayList<String> input = f.getNumbersInput( i+1, ENTRIES_PER_ROW );
		        inputNumbers.add( input );
		    }
		    
		    
		    try
            {
		        // Write the contents of their input (all Doubles/Real numbers) to the file they specified
                MyFileWriter writer = new MyFileWriter( fileLocation );
                writer.setFileContentsWithArray( inputNumbers );
                writer.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            
            if( inputNumbers.size() > 0 )
	        {
	            f.displayArrayList( inputNumbers );
	            double estimatedValue = f.getSingleNumberInput(0, 0, "Estimated Object LOC (E)");
	            
	            // Calculate and display regression numbers
	            RegressionCalc rc = new RegressionCalc( inputNumbers );
                rc.calculateSizeEstimateRegression( estimatedValue );
                
                f.displaySingleRow("---");
                f.displaySingleRow("Size Regression (columns 2 and 3)");
                f.displaySingleRow( "Beta 1 = " + rc.getSupportingRegressionValues().get(RegressionCalc.BETA1) );
                f.displaySingleRow( "Beta 0 = " + rc.getSupportingRegressionValues().get(RegressionCalc.BETA0) );
                f.displaySingleRow( "RSquared = " + rc.getSupportingRegressionValues().get(RegressionCalc.RSQUARED) );
                f.displaySingleRow( "Estimated size = " + rc.getSupportingRegressionValues().get(RegressionCalc.ESTIMATED_VALUE) + " LOC");
                f.displaySingleRow( "Predicted size = " + rc.getSupportingRegressionValues().get(RegressionCalc.PREDICTED_VALUE) + " LOC");
                
                
                
                rc.calculateTimeEstimateRegression( estimatedValue );
                f.displaySingleRow("Time Regression (columns 2 and 5)");
                f.displaySingleRow( "Beta 1 = " + rc.getSupportingRegressionValues().get(RegressionCalc.BETA1) );
                f.displaySingleRow( "Beta 0 = " + rc.getSupportingRegressionValues().get(RegressionCalc.BETA0) );
                f.displaySingleRow( "RSquared = " + rc.getSupportingRegressionValues().get(RegressionCalc.RSQUARED) );
                f.displaySingleRow( "Estimated size = " + rc.getSupportingRegressionValues().get(RegressionCalc.ESTIMATED_VALUE) + " LOC");
                f.displaySingleRow( "Predicted time = " + rc.getSupportingRegressionValues().get(RegressionCalc.PREDICTED_VALUE) + " minutes" );
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
		        ArrayList<ArrayList<String>> fileContents = new ArrayList<ArrayList<String>>();
		        fileContents = reader.getFileContents();
		        
		        for( ArrayList<String> arr : fileContents )
                {
                    if( arr.size() != ENTRIES_PER_ROW )
                    {
                        f.displayError("The K in the application does not match the number of columns in the data!\nK = " + ENTRIES_PER_ROW + ", Number of columns = " + arr.size(), "Column number mismatch!");
                        System.exit(-20);
                    }
                }
		        
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
