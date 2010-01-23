package org.greg.drexel.a2.loc;

import java.util.ArrayList;

/**
 * @author Greg Vannoni
 * @class INFO 636
 *
 * Purpose:
 *  - Based on a list of user-defined exclusion filters, determine
 *  if a String should be counted as a logical line of code
 *  
 * @version 1.0
 * 
 * Notes:
 */
public class CountingStandard
{
    private ArrayList<String> containsExclusionFilter = null;
    private ArrayList<String> startsWithExclusionFilter = null;
    private ArrayList<String> endsWithExclusionFilter = null;
    private boolean trimLine = true;
    
    /**
     * CountingStandard <br/>
     * If trimLine is true, the algorithm will call String.trim() on each
     * incoming string.  This is the recommended usage.
     * 
     * @param trimLine - trim() each String
     */
    public CountingStandard( boolean trimLine )
    {
        containsExclusionFilter     = new ArrayList<String>();
        startsWithExclusionFilter   = new ArrayList<String>();
        endsWithExclusionFilter     = new ArrayList<String>();
        this.trimLine = trimLine;
    }
    
    /**
     * CountingStandard <br/>
     * If trimLine is true, the algorithm will call String.trim() on each
     * incoming string.  This is the recommended usage.  
     * 
     * @param contains - A list of counting exclusions for a string.contains().
     * @param startsWith - A list of counting exclusions for the beginning string
     * @param endsWith - A list of counting exclusions for the ending strings
     * @param trimLine - trim() each String
     */
    public CountingStandard( ArrayList<String> contains, ArrayList<String> startsWith, ArrayList<String> endsWith, boolean trimLine )
    {
        containsExclusionFilter     = contains;
        startsWithExclusionFilter   = startsWith;
        endsWithExclusionFilter     = endsWith;
        this.trimLine = trimLine;
    }
    
    
    /**
     * Method: isLogicalLineOfCode<br/>
     * Return a simple true/false if this line of code should be counted
     *   as a logical line of code.
     *  If the input line is empty it is NOT counted as a logical line of code.
     *
     * @param line - a single line of code
     * @return true if input line is a logical line of code based on the exclusion list
     *         false if the input string is not a logical line of code
     */
    public boolean isLogicalLineOfCode( String line )
    {
        boolean result = true;
        
        if( trimLine )
        {
            line = line.trim();
        }
        
        if( line.length() == 0 )
        {
            return false;
        }
        
        
        // Check the exclusion filters for the input line
        for( String s : containsExclusionFilter )
        {
            if( line.contains(s) )
            {
                return false;
            }
        }
        for( String s : startsWithExclusionFilter )
        {
            if( line.startsWith(s) )
            {
                return false;
            }
        }
        for( String s : endsWithExclusionFilter )
        {
            if( line.endsWith(s) )
            {
                return false;
            }
        }
        
        return result;
    }

}
