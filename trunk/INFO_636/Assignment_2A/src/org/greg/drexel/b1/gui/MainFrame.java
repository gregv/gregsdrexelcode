package org.greg.drexel.b1.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Greg Vannoni
 * @class INFO 636
 * Purpose: 
 *  - Display selection box for user to select Java source file
 *  - Provide a framework for displaying an ArrayList to a user using a JList
 *  
 *  @version 2.0
 *  Notes:
 *  
 * 
 */
public class MainFrame extends JFrame
{

    // Configurable variables
    private final String WINDOW_TITLE = "INFO 636 - Program 2A - Basic LOC Counter";
    private final Dimension DEFAULT_WINDOW_SIZE = new Dimension(800,600);
    
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
    private JScrollPane getJListScrollPane()
    {
        
        JList list = new JList( jListModel );
        JScrollPane listScrollPane = new JScrollPane( list );
        
        return listScrollPane;
    }
    
    
    /**
     * Method: getFileLocation<br/>
     * Ask the user where to read the Java source file from
     *
     * @return the absolute path of the file the user wants count LOC
     */
    public File getFileLocation()
    {
        File location = null;

        JFileChooser chooser = new JFileChooser();
        
        // Filter out everything but .java files by default.
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JAVA files", "java");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showDialog(this, "Select Java Source File" );
        
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
        }
        else
        {
            System.exit(-2);
        }

        location = chooser.getSelectedFile();
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
        JPanel panel = new JPanel( new BorderLayout(3, 0) ); //Pad horizontally by 3, 0 on the vertical
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
        JLabel label = new JLabel("Lines of Code");
        label.setFont( new Font("Arial", Font.BOLD, 16) );
        label.setForeground( Color.WHITE );
        myLabelPanel.add( label );
        
        
        panel.add( fillerPanelEast, BorderLayout.EAST );
        panel.add( fillerPanelWest, BorderLayout.WEST );
        panel.add( fillerPanelSouth, BorderLayout.SOUTH );
        
        panel.add( myLabelPanel, BorderLayout.NORTH );
        panel.add( getJListScrollPane(), BorderLayout.CENTER );
        
        // Add to main frame
        add( panel );
        
        // Set the location of the panel to the center of the user's screen
        this.setLocationRelativeTo( getRootPane() );
        
        this.setVisible(true);
    }
} // end class
