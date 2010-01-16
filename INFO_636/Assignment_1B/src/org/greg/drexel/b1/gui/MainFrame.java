/**
 * 
 */
package org.greg.drexel.b1.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.greg.drexel.b1.types.FileModeType;

/**
 * @author Greg Vannoni
 * @class INFO 636
 * 
 */
public class MainFrame extends JFrame
{

    private static final long serialVersionUID = 1L;
    private DefaultListModel jListModel = null;
    
    public MainFrame()
    {
        jListModel = new DefaultListModel();
    }

    
    public void displayError( String message, String title )
    {
        JOptionPane.showMessageDialog(this, message,title, JOptionPane.ERROR_MESSAGE );
    }
    
    public void displayWarning( String message, String title )
    {
        JOptionPane.showMessageDialog(this, message,title, JOptionPane.WARNING_MESSAGE );
    }
    
    
    // Display an ArrayList of Strings in the JList
    public void displayArrayList( ArrayList<String> arrayList )
    {
        for( String s : arrayList )
        {
            jListModel.addElement( s );
        }
    }
    
    private JList getJListPane()
    {
        
        JList list = new JList( jListModel );
        return list;
    }
    
    public FileModeType getReadWriteMode()
    {
        FileModeType[] options = { FileModeType.READ, FileModeType.WRITE };
        int selection = JOptionPane.showOptionDialog(this, "Select mode:", "Mode Selection", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, FileModeType.READ );
        
        // User pressed X button instead of READ or WRITE
        if( selection < 0 )
        {
           System.exit(-5);
        }
        
        return options[selection];
    }
    
    
    private Double getSingleNumberInput( int identifier )
    {
       String input = JOptionPane.showInputDialog( "Input number " + identifier );
       Double result = null;
       
       if( input == null )
       {
           // User pressed cancel button, just exit for now
           System.exit(-6);
       }
       
       try
       {
           result = Double.parseDouble( input );
       }
       catch( NumberFormatException nfe )
       {
           // User didn't enter a double (Real number)
           displayWarning( "You didn't enter a Real number! Try again.", "Number input error" );
           return getSingleNumberInput( identifier );
       }
       
       return result;
    }
    
    
    public ArrayList<Double> getNumbersInput( Integer quantityOfNumbersToInput )
    {
        ArrayList<Double> numbersInputted = new ArrayList<Double>();
        
        for( int i=0; i<quantityOfNumbersToInput; i++ )
        {
            Double input = getSingleNumberInput( i+1 );
            numbersInputted.add( input );
        }
        
        return numbersInputted;
    }
    
    public Integer getQuantityOfNumbers()
    {
        Integer quantity = null;
        
        String input = JOptionPane.showInputDialog( "How many numbers are you going to record?" );
        
        if( input == null )
        {
            // User pressed cancel button, just exit for now
            System.exit(-6);
        }
        
        try
        {
            quantity = Integer.parseInt( input );
            
            
        }
        catch( NumberFormatException nfe )
        {
            // User didn't enter a number (jerk!), return them to the input screen.
            displayWarning( "You didn't enter in a number! Try again.", "Number input error" );
            return getQuantityOfNumbers();
        }
        
        
        return quantity;
    }
    
    public String getFileLocation()
    {
        String location = null;

        JFileChooser chooser = new JFileChooser();
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT files", "txt");
        chooser.setFileFilter(filter);
        // int returnVal = chooser.showOpenDialog(this);
        int returnVal = chooser.showDialog(this, "Create or Select File" );
        
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            System.out.println("You chose to open/create this file: " + chooser.getSelectedFile().getName());
        }
        else
        {
            System.exit(-2);
        }

        location = chooser.getSelectedFile().getAbsolutePath();
        return location;
    }

    /**
     * 
     */
    public void initializeAndDisplay()
    {
        this.setSize(500, 500);
        this.setTitle( "INFO 636 - Program 1B - Number Retreiver" );
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        JPanel panel = new JPanel( new BorderLayout(3, 0) );
        JPanel fillerPanelEast = new JPanel();
        JPanel fillerPanelWest = new JPanel();
        JPanel fillerPanelSouth = new JPanel();
        JPanel myLabelPanel = new JPanel();
        
        // Setup all of the panels around the JList to be DARK_GRAY for consistency (and good looks).
        fillerPanelEast.setBackground( Color.DARK_GRAY );
        fillerPanelWest.setBackground( Color.DARK_GRAY );
        fillerPanelSouth.setBackground( Color.DARK_GRAY );
        myLabelPanel.setBackground( Color.DARK_GRAY );
        
        
        // This label will be just above the JList that holds the file data
        JLabel label = new JLabel("File Contents");
        label.setFont( new Font("Arial", Font.BOLD, 16) );
        label.setForeground( Color.WHITE );
        myLabelPanel.add( label );
        
        
        panel.add( fillerPanelEast, BorderLayout.EAST );
        panel.add( fillerPanelWest, BorderLayout.WEST );
        panel.add( fillerPanelSouth, BorderLayout.SOUTH );
        
        panel.add( myLabelPanel, BorderLayout.NORTH );
        panel.add( getJListPane(), BorderLayout.CENTER );
        
        // Add to main frame
        add( panel );
        
        // Set the location of the panel to the center of the user's screen
        this.setLocationRelativeTo( getRootPane() );
        
        this.setVisible(true);
    }
}
