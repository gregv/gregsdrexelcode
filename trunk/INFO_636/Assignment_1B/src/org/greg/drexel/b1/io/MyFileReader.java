/**
 * 
 */
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
 */
public class MyFileReader extends BufferedReader
{

    /**
     * @param arg0
     */
    private MyFileReader( Reader arg0 )
    {
        super(arg0);
    }

    public MyFileReader( String filename ) throws FileNotFoundException
    {
        super( new java.io.FileReader(filename) );
    }
    
    public ArrayList<String> getFileContents() throws IOException
    {
        ArrayList<String> result = new ArrayList<String>();
        String  line = null;
        
        while( (line = readLine()) != null )
        {
            result.add( line );
        }
        
        return result;
    }
}
