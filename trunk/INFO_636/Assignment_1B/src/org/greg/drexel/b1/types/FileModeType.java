/**
 * 
 */
package org.greg.drexel.b1.types;

/**
 * @author Greg Vannoni
 * @class INFO 636
 *
 */
public enum FileModeType
{
    READ("Read"),
    WRITE("Write");
    
    private String name = null;
    
    private FileModeType(String name)
    {
        this.name = name;
    }
    
    public String toString()
    {
        return name;
    }
    
}
