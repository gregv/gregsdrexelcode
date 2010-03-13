package org.greg.drexel.b1.types;

/**
 * @author Greg Vannoni
 * @class INFO 636
 *
 * Purpose:
 * Simple enum class to hold the type of modification the user wants to perform
 * 
 * @version 1.0
 * 
 * Notes:
 * Planned for expansion later
 */
public enum FileModifyType
{
    ACCEPT("Accept"),
    ACCEPT_ALL("Accept All"),
    INSERT("Insert"),
    REPLACE("Replace"),
    DELETE("Delete");
    
    private String name = null;
    
    private FileModifyType(String name)
    {
        this.name = name;
    }
    
    public String toString()
    {
        return name;
    }
    
} // end class
