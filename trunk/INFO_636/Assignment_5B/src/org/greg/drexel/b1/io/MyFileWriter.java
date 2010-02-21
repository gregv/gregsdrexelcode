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
 *  - Updated to support Arrays of ArrayLists (2D array) for Program 5B
 * 
 * @version 4.0
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
     * Method: setFileContentsWithArray<br/>
     * After specifying the storage location in the constructor, write the contents of
     * an array of arraylists (2D) to disk
     *
     * @param input - An ArrayList of ArrayList<String> to write to disk
     * @throws IOException - When the system cannot write to the file
     */
    public void setFileContentsWithArray( ArrayList<ArrayList<String>> input ) throws IOException
    {
        for( ArrayList<String> a : input )
        {
            for( String s : a )
            {
                write( s + "  " );
                flush();
            }
            write( "\n" );
            flush();
        }
    }
    
    /**
     * Method: setFileContentsWithList<br/>
     * After specifying the storage location in the constructor, write the contents of
     * a Double ArrayList to the location.
     *
     * @param inputNumbers - An ArrayList of Doubles (Strings) to write to disk.
     * @throws IOException - When the system cannot write to the file
     */
    public void setFileContentsWithList( ArrayList<String> input ) throws IOException
    {
        for( String s : input )
        {
            write( s + "\n" );
            flush();
        }
    }
    

} // end class
