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
 *  - Updated to support saving only double data to a file
 *  
 * @version 5.0
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
     * @param onlySaveDoubleData - If you want to save only doubles to the file - use this if you have a mixture of text and doubles
     * @throws IOException - When the system cannot write to the file
     */
    public void setFileContentsWithList( ArrayList<String> input, boolean onlySaveDoubleData ) throws IOException
    {
        for( String s : input )
        {
            if( onlySaveDoubleData )
            {
                try
                {
                    String[] toWrite = s.split("  ");
                    
                    // If anything other than a double is in this list, the exception will be caught
                    for( String str : toWrite )
                    {
                        Double.parseDouble( str );
                    }
                    
                    // If there was no exception, the string will be written to the file
                    write( s + "\n" );
                    flush();
                }catch( NumberFormatException nfe )
                {
                    // This was not a double, don't write it to the file
                }
            }
            else
            {
                write( s + "\n" );
                flush();
            }
        }
    }
    

} // end class
