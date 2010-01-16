package org.greg.drexel.b1.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
 * Purpose: Display dialog and input boxes to the user in order to:
 *  1. Obtain input to write (Real numbers) into a file
 *  2. Display the contents of a file (with Real numbers)
 *  
 *  @version 1.0
 *  Notes:
 *  
 * 
 */
public class MainFrame extends JFrame
{

    // Configurable variables
    private final String WINDOW_TITLE = "INFO 636 - Program 1B - Number Retreiver";
    private final Dimension DEFAULT_WINDOW_SIZE = new Dimension(500,500);
    
    
    
    private static final long serialVersionUID = 1L;
    private DefaultListModel jListModel = null;
    
    /**
     * Method: MainFrame<br/>
     * Main constructor for initialization
     * 
     */
    public MainFrame()
    {
        jListModel = new DefaultListModel();
    }

    
    /**
     * Method: displayError<br/>
     * Displays an error dialog box to the user.
     * 
     * @param message - The message to display to the user
     * @param title - The title of the dialog box
     */
    public void displayError( String message, String title )
    {
        JOptionPane.showMessageDialog(this, message,title, JOptionPane.ERROR_MESSAGE );
    }
    
    /**
     * Method: displayWarning<br/>
     * Displays a warning dialog box to the user.
     *
     * @param message - The message to display to the user
     * @param title - The title of the dialog box
     */
    public void displayWarning( String message, String title )
    {
        JOptionPane.showMessageDialog(this, message,title, JOptionPane.WARNING_MESSAGE );
    }
    
    
    /**
     * Method: displayArrayList<br/>
     * Display an ArrayList of Strings in the JList
     *
     * @param arrayList<String> - the ArrayList to display in the JList
     */
    public void displayArrayList( ArrayList<String> arrayList )
    {
        for( String s : arrayList )
        {
            jListModel.addElement( s );
        }
    }
    
    
    /**
     * Method: getJListPane<br/>
     * Get the JList (initialized with the jListModel) for the GUI
     *
     * @return JList
     */
    private JList getJListPane()
    {
        
        JList list = new JList( jListModel );
        return list;
    }
    
    
    
    /**
     * Method: getReadWriteMode<br/>
     * Ask the user if they want to READ or WRITE to a given file
     *
     * @return the selection of READ or WRITE or exit if the X is pressed on the window
     */
    public FileModeType getReadWriteMode()
    {
        FileModeType[] options = { FileModeType.READ, FileModeType.WRITE };
        int selection = JOptionPane.showOptionDialog(this, "Select mode", "Mode Selection", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, FileModeType.READ );
        
        // User pressed X button instead of READ or WRITE
        if( selection < 0 )
        {
           System.exit(-5);
        }
        
        return options[selection];
    }
    
    
    /**
     * Method: getSingleNumberInput<br/>
     * Ask the user for input of a Double (Real number) and enforce entry
     *
     * @param identifier - the special identifier to display to the user
     * @return the Double/Real number value the user entered
     */
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
    
    
    /**
     * Method: getNumbersInput<br/>
     * Ask the user for a given set of numbers as determined by quantityOfNumbersToInput
     *
     * @param quantityOfNumbersToInput - How many Real numbers to ask the user for
     * @return an ArrayList<Double> of numbers the user input
     */
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
    
    /**
     * Method: getQuantityOfNumbers<br/>
     * Ask the user for how many numbers they are going to input
     *
     * @return the quantity of numbers the user is planning to input
     */
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
    
    /**
     * Method: getFileLocation<br/>
     * Ask the user where to READ/WRITE the file from
     *
     * @return the absolute path of the file the user wants to READ/WRITE from
     */
    public String getFileLocation()
    {
        String location = null;

        JFileChooser chooser = new JFileChooser();
        
        // Filter out everything but .txt files by default.
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT files", "txt");
        chooser.setFileFilter(filter);
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
     * Method: initializeAndDisplay<br/>
     * Initialize GUI components
     *
     */
    public void initializeAndDisplay()
    {
        this.setSize( DEFAULT_WINDOW_SIZE );
        this.setTitle( WINDOW_TITLE );
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
} // end class
