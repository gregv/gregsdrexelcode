package org.greg.drexel.b1.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.greg.drexel.b1.io.MyFileWriter;
import org.greg.drexel.b1.types.FileModeType;
import org.greg.drexel.b1.types.FileModifyType;

/**
 * @author Greg Vannoni
 * @class INFO 636
 * Purpose: Display dialog and input boxes to the user in order to:
 *  1. Obtain input to write (Real numbers) into a file
 *  2. Display the contents of a file (with Real numbers)
 *  3. New in 2B: Ask user if they want to Modfy, Insert, Replace, Accept or Accept each line in a file
 *  
 *  @version 3.0
 *  Notes:
 *  
 * 
 */
public class MainFrame extends JFrame
{

    // Configurable variables
    private final String WINDOW_TITLE = "INFO 636 - Program 2B - Number Retreiver/Modifier";
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
        for( Object o : arrayList )
        {
            jListModel.addElement( o );
        }
    }
    
    
    /**
     * Method: getJListAsArrayList<br/>
     * Convert the JList to an ArrayList<String>
     *
     * @return ArrayList<String> with JList contents
     */
    public ArrayList<String> getJListAsArrayList( )
    {
    	ArrayList<String> result = new ArrayList<String>();
    	
    	for( Object o : jListModel.toArray() )
    	{
    		result.add( o.toString() );
    	}
    	
    	return result;
    }
    
    
    /**
     * Method: displayArrayList<br/>
     * Display an ArrayList of Strings in the JList
     *
     * @param arrayList<String> - the ArrayList to display in the JList
     */
    public void displaySingleString( Object strToDisplay )
    {
    	jListModel.addElement( strToDisplay );
    }
    
    
    /**
     * Method: removeSingleString<br/>
     * Remove a string (one row) from the JList
     *
     * @param strToRemove - The string to remove from the JList
     */
    public void removeSingleString( Object strToRemove )
    {
    	jListModel.removeElement( strToRemove );
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
     * Method: getModifyAction<br/>
     * Ask the user if they want to:
     *  - Accept - Print a single line of the file
     *  - Accept All - Print the remainder of the lines in the file
     *  - Delete - Remove the current row from the JList
     *  - Insert - Insert a row after the row currently displayed
     *  - Replace - Replace the current line with with user input
     *
     * @return the FileModifyType of modification the user wants to perform
     */
    public FileModifyType getModifyAction()
    {
        FileModifyType[] options = { FileModifyType.ACCEPT, FileModifyType.ACCEPT_ALL, FileModifyType.DELETE, FileModifyType.INSERT, FileModifyType.REPLACE };
        int selection = JOptionPane.showOptionDialog(this, "Select mode", "Mode Selection", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, FileModeType.READ );
        
        // User pressed X (close) button instead of READ or WRITE or MODIFY
        if( selection < 0 )
        {
           System.exit(-5);
        }
        
        return options[selection];
    }
    
    
    /**
     * Method: getReadWriteMode<br/>
     * Ask the user if they want to READ or WRITE or MODIFY to a given file
     *
     * @return the selection of READ or WRITE or MODIFY or exit if the X is pressed on the window
     */
    public FileModeType getReadWriteModifyMode()
    {
        FileModeType[] options = { FileModeType.MODIFY, FileModeType.READ, FileModeType.WRITE};
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
    	String prompt = "Input number";
    	
    	if( identifier >= 1 )
    	{
    		prompt += " " + identifier;
    	}
    	
       String input = JOptionPane.showInputDialog( prompt );
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
    public ArrayList<String> getNumbersInput( Integer quantityOfNumbersToInput )
    {
        ArrayList<String> numbersInputted = new ArrayList<String>();
        
        for( int i=0; i<quantityOfNumbersToInput; i++ )
        {
            String input = getSingleNumberInput( i+1 ) + "";
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
    public String getFileLocation(String title, String filtername, String filterfile)
    {
        String location = null;

        JFileChooser chooser = new JFileChooser();
        
        // Filter out everything but .txt files by default.
        FileNameExtensionFilter filter = new FileNameExtensionFilter(filtername, filterfile);
        chooser.setFileFilter(filter);
        int returnVal = chooser.showDialog( this, title );
        
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
     * Method: initializeAndDisplayWithPrompt<br/>
     * Initialize the display as normal, but for each line in the, prompt the user
     * for input.
     * Once the user is done, allow them to save this new output as a file.
     *
     * @param fileContents - The contents of the file in an ArrayList<String>
     */
	public void initializeAndDisplayWithPrompt( ArrayList<String> fileContents )
    {
    	initializeAndDisplay();
    	
    	FileModifyType selection = null;
    	
    	for( int i=0; i<fileContents.size(); i++ )
    	{
    		Object s = fileContents.get(i);
    		
    		displaySingleString( s.toString() );
    		
    		if( selection != FileModifyType.ACCEPT_ALL )
    		{
    			// This is a blocking call - nothing will happen until user selection is made
    			selection = getModifyAction();
    		}

    		
    		// Handle the different selections from the user
    		if( selection == FileModifyType.ACCEPT )
    		{
    			// Don't need to do anything
    		}
    		else if( selection == FileModifyType.ACCEPT_ALL )
    		{
    			// Don't need to do anything
    			// Bypass asking for more input print out everything else
    		}
    		else if( selection == FileModifyType.DELETE )
    		{
    			removeSingleString( s );
    		}
    		else if( selection == FileModifyType.INSERT )
    		{
    			Double singleNumber = getSingleNumberInput(-1);
    			displaySingleString( singleNumber );
    		}
    		else if( selection == FileModifyType.REPLACE )
    		{
    			Double singleNumber = getSingleNumberInput(-1);
    			removeSingleString( s );
    			displaySingleString( singleNumber );
    		}
    		else
    		{
    			System.err.println("Unsupported file modify type!");
    		}
    	}
    	
    }
    
	
	/**
     * Method: displaySaveAsDialog<br/>
     * Allow the user to save the file either as a new file or as the existing one.
     *
     */
    private void displaySaveAsDialog()
    {
    	String saveAsLocation = getFileLocation("Save file as", ".txt files", "txt" );
    	try {
			MyFileWriter fileWriter = new MyFileWriter( saveAsLocation );
			System.out.println("Saving these contents: " + getJListAsArrayList() );
			fileWriter.setFileContents( getJListAsArrayList() );
			System.out.println("Saved @ " + saveAsLocation );
		} catch (IOException e) {
			e.printStackTrace();
		}
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
        
        // Setup the File > Save As.. functionality for Assignment 2B
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        
        JMenuItem menuItem = new JMenuItem();
        menuItem.setText("Save As...");
        menuItem.addActionListener( new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				displaySaveAsDialog();
			}
        	
        });
        menu.add( menuItem );
        
        menuBar.add( menu );
        setJMenuBar( menuBar );
        
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
