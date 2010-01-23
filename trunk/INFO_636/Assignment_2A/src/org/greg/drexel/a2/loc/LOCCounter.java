/**
 * 
 */
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
    
    
    public LOCCounter( CountingStandard standard )
    {
        countingStandard = standard;
    }
    
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
    
    
    public int countPhysicalLOC( ArrayList<String> fileContents )
    {
        return fileContents.size();
    }
}
