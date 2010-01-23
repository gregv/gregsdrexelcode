package org.greg.drexel.a2.loc;

import java.util.ArrayList;

/**
 * @author Greg Vannoni
 * @class INFO 636
 *
 * Purpose:
 * - Provide an easy way to count Java source code based on a "Counting Standard"
 * 
 * @version 1.0
 * Notes:
 */
public class LOCCounter
{
    private CountingStandard countingStandard = null;
    
    
    /**
     * Public constructor
     * 
     * @param standard - The CountingStandard to use when counting lines of code
     */
    public LOCCounter( CountingStandard standard )
    {
        countingStandard = standard;
    }
    
    /**
     * Method: getLogicalVisualLOC<br/>
     * Return an ArrayList of the logical lines of code from fileContents.
     *
     * @param fileContents - the contents of a file
     * @return an ArrayList of the logical lines of code in the file
     */
    public ArrayList<String> getLogicalVisualLOC( ArrayList<String> fileContents )
    {
        ArrayList<String> visualLogicalLOC = new ArrayList<String>();
        
        for( String s : fileContents )
        {
            if( countingStandard.isLogicalLineOfCode( s ) )
            {
                visualLogicalLOC.add( s );
            }
        }

        return visualLogicalLOC;
    }
    
    /**
     * Method: countLogicalLOC<br/>
     * Use the countingStandard to determine if each line of fileContents
     * is a logical line of code.  Return the total number of logical lines
     * of code.
     *
     * @param fileContents - the contents of a file
     * @return number of logical lines of code
     */
    public int countLogicalLOC( ArrayList<String> fileContents )
    {
        int counter = 0;
        for( String s : fileContents )
        {
            if( countingStandard.isLogicalLineOfCode( s ) )
            {
                counter++;
            }
        }
        
        return counter;
    }
    
    
    /**
     * Method: countPhysicalLOC<br/>
     * Return the physical lines of code of the input file
     *
     * @param fileContents
     * @return physical lines of code of the input file
     */
    public int countPhysicalLOC( ArrayList<String> fileContents )
    {
        return fileContents.size();
    }
}
