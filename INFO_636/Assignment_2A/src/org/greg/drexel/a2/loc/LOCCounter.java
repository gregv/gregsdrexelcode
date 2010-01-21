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
 * @version
 * Notes:
 */
public class LOCCounter
{
    
    private ArrayList<String> logicalLOCexclusion_contains = null;
    private ArrayList<String> logicalLOCexclusion_startsWith = null;
    private ArrayList<String> logicalLOCexclusion_endsWith = null;
    private Boolean trimEachLine = null;
    
    public LOCCounter( ArrayList<String> containsExclusionFilter, ArrayList<String> startsWithExclusionFilter, ArrayList<String> endsWithExclusionFilter, Boolean trimEachLine )
    {
        this.logicalLOCexclusion_contains = containsExclusionFilter;
        this.logicalLOCexclusion_startsWith = startsWithExclusionFilter;
        this.logicalLOCexclusion_endsWith = endsWithExclusionFilter;
        this.trimEachLine = trimEachLine;
    }

    private boolean isLogicalLineOfCode( String line )
    {
        boolean result = true;
        if( trimEachLine )
        {
            line = line.trim();
        }
        
        if( line.length() == 0 )
        {
            return false;
        }
        
        for( String s : logicalLOCexclusion_contains )
        {
            if( line.contains(s) )
            {
                return false;
            }
        }
        
        for( String s : logicalLOCexclusion_startsWith )
        {
            if( line.startsWith(s) )
            {
                return false;
            }
        }
        
        for( String s : logicalLOCexclusion_endsWith )
        {
            if( line.endsWith(s) )
            {
                return false;
            }
        }
        
        return result;
    }
    
    public int countLogicalLOC( ArrayList<String> fileContents )
    {
        int counter = 0;
        for( String s : fileContents )
        {
            if( isLogicalLineOfCode( s ) )
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
