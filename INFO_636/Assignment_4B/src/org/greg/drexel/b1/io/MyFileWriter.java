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
 * @version 3.0
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
        // Never append to file (assumed from assignment specification
        super( new java.io.FileWriter(filename,false) );
    }
    
    
    /**
     * Method: setFileContents<br/>
     * After specifying the storage location in the constructor, write the contents of
     * a Double ArrayList to the location.
     *
     * @param inputNumbers - An ArrayList of Doubles to write to disk.
     * @throws IOException - When the system cannot write to the file
     */
    public void setFileContents( ArrayList<String> input ) throws IOException
    {
        for( String s : input )
        {
            write( s + "\n" );
            flush();
        }
    }
    

} // end class
