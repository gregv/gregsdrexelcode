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
public class CountingStandard
{
    private ArrayList<String> containsExclusionFilter = null;
    private ArrayList<String> startsWithExclusionFilter = null;
    private ArrayList<String> endsWithExclusionFilter = null;
    private boolean trimLine = true;
    
    public CountingStandard( boolean trimLine )
    {
        containsExclusionFilter     = new ArrayList<String>();
        startsWithExclusionFilter   = new ArrayList<String>();
        endsWithExclusionFilter     = new ArrayList<String>();
        this.trimLine = trimLine;
    }
    
    public CountingStandard( ArrayList<String> contains, ArrayList<String> startsWith, ArrayList<String> endsWith, boolean trimLine )
    {
        containsExclusionFilter     = contains;
        startsWithExclusionFilter   = startsWith;
        endsWithExclusionFilter     = endsWith;
        this.trimLine = trimLine;
    }
    
    
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
