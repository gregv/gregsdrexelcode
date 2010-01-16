/**
 * 
 */
package org.greg.drexel.b1.io;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

/**
 * @author Greg Vannoni
 * @class INFO 636
 *
 */
public class MyFileWriter extends BufferedWriter
{

    /**
     * @param arg0
     */
    public MyFileWriter(Writer arg0)
    {
        super(arg0);
    }
    
    public MyFileWriter( String filename ) throws IOException
    {
        // Always append to file (assumed from assignment specification
        super( new java.io.FileWriter(filename,true) );
    }
    
    
    public void setFileContents( ArrayList<Double> inputNumbers ) throws IOException
    {
        for( Double d : inputNumbers )
        {
            String s = d.doubleValue() + "\n";
            write( s );
            flush();
        }
    }

}
