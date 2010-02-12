package org.greg.drexel.b1.types;

/**
 * @author Greg Vannoni
 * @class INFO 636
 *
 * Purpose:
 * Simple enum class to hold the type of File I/O mode a user can select
 * 
 * @version 1.0
 * 
 * Notes:
 * Planned for expansion later
 */
public enum FileModeType
{
    READ("Read"),
    WRITE("Write"),
    MODIFY("Modify");
    
    private String name = null;
    
    private FileModeType(String name)
    {
        this.name = name;
    }
    
    public String toString()
    {
        return name;
    }
    
} // end class
