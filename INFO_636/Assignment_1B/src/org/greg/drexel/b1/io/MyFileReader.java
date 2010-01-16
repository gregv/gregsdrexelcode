package org.greg.drexel.b1.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;


/**
 * @author Greg Vannoni
 * @class INFO 636
 *
 * Purpose: Provide an easy library for file access
 *  - Extend BufferedReader to allow for easy reading of
 * a file into an ArrayList which can be easily used to in the MainFrame
 * class with a JList.
 * 
 * @version 1.0
 * Notes:
 */
public class MyFileReader extends BufferedReader
{
  
    
    /**
     * Method: MyFileReader
     * @param reader - a java.io.Reader
     */
    private MyFileReader( Reader reader )
    {
        super(reader);
    }

    /**
     * Method: MyFileReader
     * @param filename - The full path of the file
     * @throws FileNotFoundException When a file does not exist
     */
    public MyFileReader( String filename ) throws FileNotFoundException
    {
        super( new java.io.FileReader(filename) );
    }
    
    /**
     * Method: getFileContents<br/>
     * Read the file and return an ArrayList
     *
     * @return ArrayList<String> to be easily read by another method
     * @throws IOException
     */
    public ArrayList<String> getFileContents() throws IOException
    {
        ArrayList<String> result = new ArrayList<String>();
        String  line = null;
        
        // Read the file, line-by-line and add to ArrayList
        while( (line = readLine()) != null )
        {
            result.add( line );
        }
        
        return result;
    }
} // end class
