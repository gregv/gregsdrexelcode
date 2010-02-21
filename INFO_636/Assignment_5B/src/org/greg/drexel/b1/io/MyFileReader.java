package org.greg.drexel.b1.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * @author Greg Vannoni
 * @class INFO 636
 *
 * Purpose: Provide an easy library for file access
 *  - Extend BufferedReader to allow for easy reading of
 * a file into an ArrayList which can be easily used to in the MainFrame
 * class with a JList.
 * - Updated to support reading 2D arrays (arrays of arraylists)
 * 
 * @version 2.0
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
    public ArrayList<ArrayList<String>> getFileContents() throws IOException
    {
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        
        ArrayList<String> line = null;
        String  strline = null;
        
        // Read the file, line-by-line and add to ArrayList
        while( (strline = readLine()) != null )
        {
            line = new ArrayList<String>( Arrays.asList( strline.split("  ")) );
            result.add( line );
        }
        
        return result;
    }
} // end class
