package org.greg.drexel.b1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.greg.drexel.b1.gui.MainFrame;
import org.greg.drexel.b1.io.MyFileReader;
import org.greg.drexel.b1.io.MyFileWriter;
import org.greg.drexel.b1.types.FileModeType;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainFrame f = new MainFrame();
		String fileLocation   = f.getFileLocation();
		FileModeType mode     = f.getReadWriteMode();
		
		 
		 
		if( mode == FileModeType.READ )
		{
		    try
            {
		        MyFileReader reader = new MyFileReader( fileLocation );
		        ArrayList<String> fileContents = new ArrayList<String>();
		        fileContents = reader.getFileContents();
		        
		        System.out.println( fileContents );
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
            
            f.initializeAndDisplay();
		}
		else if( mode == FileModeType.WRITE )
		{
		    Integer quantityOfNumbersToInput = f.getQuantityOfNumbers();
		    ArrayList<Double> inputNumbers = f.getNumbersInput( quantityOfNumbersToInput );
		    try
            {
                MyFileWriter writer = new MyFileWriter( fileLocation );
                writer.setFileContents( inputNumbers );
                writer.close();
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
		
		

	}

}
