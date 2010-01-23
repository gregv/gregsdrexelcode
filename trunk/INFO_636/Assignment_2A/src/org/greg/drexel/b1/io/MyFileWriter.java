package org.greg.drexel.b1.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;


/**
 * @author Greg Vannoni
 * @class INFO 636
 *
 * Purpose:
 * Provide an easy library for file access
 *  - Extend BufferedWriter to easily write an ArrayList to a file
 * 
 * @version 2.0
 * Notes:
 */
public class MyFileWriter extends BufferedWriter
{

    
    /**
     * Method: MyFileWriter
     * @param writer - The java.io.Writer object to write to
     */
    public MyFileWriter(Writer writer)
    {
        super(writer);
    }
    
   
    /**
     * Method: MyFileWriter
     * @param filename - The full path of the file to write to
     * @throws IOException - When the system cannot write to the file
     */
    public MyFileWriter( String filename ) throws IOException
    {
        // Always create a new file (assumed from assignment specification)
        super( new java.io.FileWriter(filename,false) );
    }
    
    
    /**
     * Method: setFileContents<br/>
     * After specifying the storage location in the constructor, write the contents of
     * a String ArrayList to the location.
     *
     * @param inputArrayList - An ArrayList of Strings to write to disk.
     * @throws IOException - When the system cannot write to the file
     */
    public void setFileContents( ArrayList<String> inputArrayList ) throws IOException
    {
        for( String d : inputArrayList )
        {
            String s = d + "\n";
            write( s );
            flush();
        }
    }

} // end class
