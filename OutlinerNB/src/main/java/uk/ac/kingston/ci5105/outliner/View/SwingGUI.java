/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uk.ac.kingston.ci5105.outliner.View;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import uk.ac.kingston.ci5105.outliner.Model.*;
import uk.ac.kingston.ci5105.outliner.Controller.*;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.Timer;
/**
 * This is a View Class which is used to show the outline to the user
 * @author k1801606
 */
public class SwingGUI extends JFrame implements MouseListener, KeyListener
{
    // Main frame of the program
    /**
    * The frame for the GUI
    */
    private JFrame myFrame;
    // The panel which contains all the labels with the sections
    /**
    * The panel for the GUI
    */
    private JPanel myPanel;
    // The scroll pane to allow for scrolling
    /**
    * The pane user for scrolling the GUI
    */
    private JScrollPane myScrollPane;
    // ArrayList which contains all Jlabels, it is proceduraly generated
    // by accessing the outliner object
    /**
    * The container for all the labels in the GUI
    */
    private ArrayList<JLabel> allJLabels;
    // Holds the outline object so that it can be accessed
    // anywhere within the class
    /**
    * The outline currently loaded in the GUI
    */
    private Outliner myOutline;
    // Boolean value to allow for backspace to be pressed down and
    // work with keyTyped method
    /**
    * The flag for pressed backspace
    */
    private boolean backspace;
    // to keep track of the type character
    /**
    * The flag for typing character
    */
    private boolean typeChar;
    // to keep track of the index at which typing character is
    /**
    * The index for typing character
    */
    private int typeIndex;
    // to keep track of ctrl press
    /**
    * The flag for Ctrl key
    */
    private boolean ctrl;
    // this will keep track if there was an update
    /**
    * The flag for a change in the model
    */
    private boolean changeMade;
    // this is the local timer
    /**
    * The timer for the typing character
    */
    private Timer typingSpace;
    
    /**
    * This is the main method for the GUI, it is called by the Outliner and 
    * returns the GUI object constructed
     * @param args main args provided
     * @param Outline for which to create the GUI object
     * @return SwingGUI object constructed
    */
    public static SwingGUI main(String[] args, Outliner Outline)
    {
        // Initiates the GUI object with the Outline provided
        SwingGUI myGUI = new SwingGUI(Outline);
        return myGUI;
    }
    
    /**
    * This is the constructor, it sets up the basic frame in which the application
    * will be displayed
    * It also sets up the timer for the typing character and menu bar
    * At the end it calls the draw method
     * @param Outline for which to create the GUI object
    */
    public SwingGUI(Outliner Outline)
    {
       // Basic setup for the frame
       this.myFrame = new JFrame();
       this.myFrame.setTitle("Outliner");
       this.myFrame.setSize(600,900);
       this.myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
       
       // While the frame is in focus, listen for key presses
       this.myFrame.addKeyListener(this);
       // Set the object-wide outline object
       this.myOutline = Outline;
       
       // Action listener using timer to simulate typing in a jlabel
       ActionListener controlTyper = (ActionEvent e) -> {
           keyTyper();
       };
       this.typingSpace = new Timer(500, controlTyper);
       this.typingSpace.setRepeats(true);
       this.typingSpace.start();
       
       //Menu bar
       JMenuBar menuBar = new JMenuBar();
       JMenu fileMenu = new JMenu("File");
       JMenu sectionMenu = new JMenu("Section");
       menuBar.add(fileMenu);
       menuBar.add(sectionMenu);
       JMenuItem loadItem = new JMenuItem("Load File");
       JMenuItem saveItem = new JMenuItem("Save File");
       JMenuItem newItem = new JMenuItem("New Outline");
       JMenuItem renameItem = new JMenuItem("Rename File");
       JMenuItem userEdit = new JMenuItem("User Edit");
       JMenuItem addTag = new JMenuItem("Add Tag");
       JMenuItem removeTag = new JMenuItem("Remove Tag");
       JMenuItem editDate = new JMenuItem("Edit Date");
       JMenuItem findText = new JMenuItem("Find Text");
       ActionListener controlLoadItem = (ActionEvent e) -> {
           try {
               loadItemEvent(e);
           } catch (URISyntaxException ex) {
               Logger.getLogger(SwingGUI.class.getName()).log(Level.SEVERE, null, ex);
           }
       };
       
       ActionListener controlNewItem = new ActionListener() { 
           @Override
           public void actionPerformed(ActionEvent e) {
               newItemEvent(e);
           }
       };
       
       ActionListener controlSaveItem = new ActionListener() { 
           @Override
           public void actionPerformed(ActionEvent e) {
               try {
                   saveItemEvent();
               } catch (URISyntaxException ex) {
                   Logger.getLogger(SwingGUI.class.getName()).log(Level.SEVERE, null, ex);
               } catch (IOException ex) {
                   Logger.getLogger(SwingGUI.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
       };
       
       ActionListener controlEditUser = new ActionListener() { 
           @Override
           public void actionPerformed(ActionEvent e) {
               addUserEvent();
           }
       };
       
       ActionListener controlAddTag = new ActionListener() { 
           @Override
           public void actionPerformed(ActionEvent e) {
               addTagEvent();
           }
       };
       
       ActionListener controlRemoveTag = new ActionListener() { 
           @Override
           public void actionPerformed(ActionEvent e) {
               removeTagEvent();
           }
       };
       ActionListener controlEditDate = new ActionListener() { 
           @Override
           public void actionPerformed(ActionEvent e) {
               editDateEvent();
           }
       };
       ActionListener controlFindText = new ActionListener() { 
           @Override
           public void actionPerformed(ActionEvent e) {
               findTextEvent();
           }
       };
       ActionListener controlRenameItem = new ActionListener() { 
           @Override
           public void actionPerformed(ActionEvent e) {
               renameItemEvent();
           }
       };
       loadItem.addActionListener(controlLoadItem);
       saveItem.addActionListener(controlSaveItem);
       newItem.addActionListener(controlNewItem);
       userEdit.addActionListener(controlEditUser);
       addTag.addActionListener(controlAddTag);
       removeTag.addActionListener(controlRemoveTag);
       editDate.addActionListener(controlEditDate);
       findText.addActionListener(controlFindText);
       renameItem.addActionListener(controlRenameItem);
       
       fileMenu.add(loadItem);
       fileMenu.add(saveItem);
       fileMenu.add(newItem);
       fileMenu.add(renameItem);
       fileMenu.add(findText);
       sectionMenu.add(userEdit);
       sectionMenu.add(addTag);
       sectionMenu.add(removeTag);
       sectionMenu.add(editDate);
       this.myFrame.setJMenuBar(menuBar);
       
       // Call method to generate all the labels
       reDrawScreen();
       
    }
    
    //method which will load a saved outline
    /**
    * This a menu bar method to load a saved outline
     * @param e ActionEvent of the event fired
     * @throws java.net.URISyntaxException indicate that a string could not be parsed as a URI reference
    */
    public void loadItemEvent(ActionEvent e) throws URISyntaxException
    {
        String value = JOptionPane.showInputDialog(this.myFrame,"Enter file name","My Outline");
        if (value != null && value != "")
        {
            try
            {
               Outliner.loadJsonFromFile(value);
               reDrawScreen();
               Outliner.setSelected(-1);
               Outliner.saveForCtrlZ(this.myOutline);
               this.myOutline.resetSelected();
               this.typeChar = false;
            } catch (IOException ex) 
            {
              System.out.println("File not found");
            }
        }
    }
    
    
    // this method will call to save the current outline
    /**
    * This a menu bar method to save a current outline
     * @throws java.net.URISyntaxException indicate that a string could not be parsed as a URI reference
     * @throws java.io.IOException File in file system exception
    */
    public void saveItemEvent() throws URISyntaxException, IOException
    {
        Outliner.saveToJSON(this.myOutline);
    }
    
    // this method will create new outline and load it
    /**
    * This a menu bar method to create and load a new Outline
     * @param e ActionEvent of the event fired
    */
    public void newItemEvent(ActionEvent e)
    {
        Outliner Outline = new Outliner();
        String value = JOptionPane.showInputDialog(this.myFrame,"Enter file name","My Outline");
        if (value != null && value != "")
        {
            this.myOutline = Outline;
            this.myOutline.setName(value);
            Outliner.setSectionCountImidiate(0);
            Outliner.setSelected(-1);
            this.myOutline.resetSelected();
            this.typeChar = false;
            reDrawScreen();
        }
    }
    
    /**
    * This a menu bar method to add or delete a user to a section 
    */
    public void addUserEvent()
    {
        String value = JOptionPane.showInputDialog(this.myFrame,"Enter user name","");
        if (value != null)
        {
           if (Outliner.getSelected() != -1)
           {
               Section givenSection = Outliner.getAllSections().get(Outliner.getSelected());
               if (value.equals(""))
               {
                   givenSection.setUser(null);
               }
               else
               {
                    givenSection.setUserCreate(value);
               }
           }
        }
    }
    
    /**
    * This a menu bar method to add a tag to a section 
    */
    public void addTagEvent()
    {
        String value = JOptionPane.showInputDialog(this.myFrame,"Enter new tag","");
        if (value != null && value != "")
        {
           if (Outliner.getSelected() != -1)
           {
               Section givenSection = Outliner.getAllSections().get(Outliner.getSelected());
               givenSection.addTag(value);
           }
        }
    }
    
    /**
    * This a menu bar method to delete a tag from a section 
    */
    public void removeTagEvent()
    {
        String value = JOptionPane.showInputDialog(this.myFrame,"Enter new tag","");
        if (value != null && value != "")
        {
           if (Outliner.getSelected() != -1)
           {
               Section givenSection = Outliner.getAllSections().get(Outliner.getSelected());
               givenSection.deleteTag(value);
           }
        }
    }
    
    /**
    * This a menu bar method to add or delete a date to a section 
    */
    public void editDateEvent()
    {
        String value = JOptionPane.showInputDialog(this.myFrame,"Enter new date","");
        if (value != null)
        {
           if (Outliner.getSelected() != -1)
           {
               Section givenSection = Outliner.getAllSections().get(Outliner.getSelected());
               if (value.equals(""))
               {
                   givenSection.setDate(null);
               }
               else
               {
                givenSection.setDate(value);
               }
           }
        }
    }
    
    /**
    * This a menu bar method to search for a section with some text
    */
    public void findTextEvent()
    {
        String value = JOptionPane.showInputDialog(this.myFrame,"Enter new tag","");
        if (value != null && value != "")
        {
            Section foundSection = Outliner.lookForText(value);
            if (foundSection != null)
            {
                Outliner.setSelected(foundSection.getId());
                this.typeIndex = Outliner.getAllSections().get(Outliner.getSelected()).getText().length();
                this.typeChar = false;
                this.myOutline.resetSelected();
            }
        }
    }
    
    /**
    * This a menu bar method to rename the outline
    */
    public void renameItemEvent()
    {
        String value = JOptionPane.showInputDialog(this.myFrame,"Enter new name","");
        if (value != null && value != "")
        {
            this.myOutline.setName(value);
            try {
                this.saveItemEvent();
            } catch (URISyntaxException ex) {
                Logger.getLogger(SwingGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SwingGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
    * Setter for the outline
     * @param myOutline to set
    */
    public void setOutline(Outliner myOutline)
    {
        this.myOutline = myOutline;
    }
    
    /**
    * Draw method for the GUI, gathers all the labels and add them to a scroll pane
    * which is then passed to the frame
    */
    public void reDrawScreen()
    {
       // make sure to remove all existing labels
        int positionY = 0;
        int positionX = 0;
        if (this.myPanel != null)
        {
            if (Outliner.getSelected() != -1)
            {
                positionY = this.myScrollPane.getVerticalScrollBar().getValue();
                positionX = this.myScrollPane.getHorizontalScrollBar().getValue();
            }
            this.myPanel.removeAll();
        }
        
       // Get all jlabels using the two recursive methods
       allJLabels = constructJLabel(); 
       
       this.myPanel = new JPanel();
       this.myPanel.setLayout(new BoxLayout(this.myPanel, BoxLayout.Y_AXIS));
       
       if (allJLabels != null)
       {
        for (int i = 0; i < allJLabels.size(); i++)
        {
            this.myPanel.add(allJLabels.get(i));
        }
       }
       
       this.myScrollPane = new JScrollPane(this.myPanel);
       this.myScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
       this.myFrame.setContentPane(this.myScrollPane);
       this.myScrollPane.getVerticalScrollBar().setValue(positionY);
       this.myScrollPane.getHorizontalScrollBar().setValue(positionX);
       
       this.myFrame.setVisible(true);
    }
    
    /**
    * method to gather all JLabels
     * @return A list of JLabels found
    */
    public ArrayList<JLabel> constructJLabel()
    {
        // If the outline is empty return nothing
        if (this.myOutline.getSections().size() == 0)
        {
            return null;
        }
        // Else for each top level section, return all the text it produces
        else
        {
            ArrayList myLabelList = new ArrayList();
            for (int i = 0; i < this.myOutline.getSections().size();i++)
            {
                myLabelList.addAll(this.constructSectionJLabel(this.myOutline.getSections().get(i),0));
            }
            return myLabelList;
        }
    }
    
    /**
    * Recursive method to gather all labels to represent the Outline
     * @param givenSection for which to construct JLabels
     * @param level of the Section to control indent
     * @return A list of JLabels found
    */
    public ArrayList<JLabel> constructSectionJLabel(Section givenSection, Integer level)
    {
        ArrayList myLabelList = new ArrayList();
        // Construct the level indent text to add to all text in this level
        String addedText = "-  ";
        String emptyText = "";
            for (int i = 0;i < level;i++)
            {
                addedText += "      ";
                emptyText += "      ";
            }
        // If the section is completed add the completed tag on the end of line
        String completedTag = "";
        if (givenSection.isComplete())
        {
            completedTag = "               | Complete";
        }
        String user = "Assigned to ";
        if (givenSection.getUser() != null)
        {
            user += givenSection.getUser().get(0).getName();
            JLabel userLabel = new JLabel();
            userLabel.setOpaque(true);
            userLabel.setFont(new Font("Verdana",Font.ITALIC,12));
            userLabel.setText(emptyText+user);
            if (givenSection.isSelected())
            {
                userLabel.setBackground(new Color(185, 193, 250));
            }
            myLabelList.add(userLabel);
        }
        // If any other tags present
        if (givenSection.getTag().isEmpty() != true)
        {
            String manyTags = "Tags = ";
            for (int i = 0; i < givenSection.getTag().size();i++)
            {
                manyTags += "["+givenSection.getTag().get(i)+"]";
            }
            JLabel tagsLabel = new JLabel();
            tagsLabel.setOpaque(true);
            tagsLabel.setFont(new Font("Verdana",Font.ITALIC,12));
            tagsLabel.setText(emptyText+manyTags);
            if (givenSection.isSelected())
            {
                tagsLabel.setBackground(new Color(185, 193, 250));
            }
            myLabelList.add(tagsLabel);
        }
        // if date is set display the date
        String date = "Date:";
        if (givenSection.getDate() != null)
        {
            date += givenSection.getDate();
            JLabel dateLabel = new JLabel();
            dateLabel.setOpaque(true);
            dateLabel.setFont(new Font("Verdana",Font.ITALIC,12));
            dateLabel.setText(emptyText+date);
            if (givenSection.isSelected())
            {
                dateLabel.setBackground(new Color(185, 193, 250));
            }
            myLabelList.add(dateLabel);
        }
        // If the section doesn't have child nodes, only return this section text
        if (givenSection.getContent().size() == 0)
        {
            if (givenSection.isHidden() == false)
            {
                JLabel myLabel = new JLabel();
                myLabel.setOpaque(true);
                if (givenSection.isSelected())
                {
                    myLabel.setBackground(new Color(185, 193, 250));
                }
                if (givenSection.isHidden())
                {
                    myLabel.hide();
                }
                myLabel.setText(addedText+givenSection.getText()+completedTag);
                Integer id = givenSection.getId();
                myLabel.setName(id.toString());
                myLabel.addMouseListener(this);
                myLabelList.add(myLabel);
            }
            return myLabelList;
        }
        // If the section has child nodes, return this section text and recursevly call this method
        else
        {
            ArrayList mySubLabelList = new ArrayList();
            for (int i = 0; i < givenSection.getContent().size();i++)
            {
                mySubLabelList.addAll(this.constructSectionJLabel(givenSection.getContent().get(i),level+1));
            }
            if (givenSection.isHidden() == false)
            {
                JLabel myLabel = new JLabel();
                myLabel.setOpaque(true);
                if (givenSection.isSelected())
                {
                    myLabel.setBackground(new Color(185, 193, 250));
                }
                if (givenSection.isHidden())
                {
                    //myLabel.hide();
                }
                myLabel.setText(addedText+givenSection.getText()+completedTag);
                Integer id = givenSection.getId();
                myLabel.setName(id.toString());
                myLabel.addMouseListener(this);
                myLabelList.add(myLabel);
            }
            myLabelList.addAll(mySubLabelList);
            return myLabelList;
        }
    }

    /**
    * Overriden method to allow to select a section with a mouse
    */
    @Override
    public void mouseClicked(MouseEvent e) {
        // Code to select a section so that it can be edited
        Component firedLabel = e.getComponent();
        Section mySection = Outliner.getAllSections().get(Integer.parseInt(firedLabel.getName()));
        int sectionID = mySection.getId();
        if (this.typeChar)
        {
            keyTyperRemover();
        }
        Outliner.setSelected(sectionID);
        this.myOutline.resetSelected();
        this.typeIndex = Outliner.getAllSections().get(sectionID).getText().length();
        reDrawScreen();
    }

    /**
    * Overriden method empty
    */
    @Override
    public void mousePressed(MouseEvent e) {
    }

    /**
    * Overriden method empty
    */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
    * Overriden method empty
    */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
    * Overriden method empty
    */
    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int sectionId = Outliner.getSelected();
        if (sectionId != -1)
        {
            // Check to remove the typing character before modifying string
            if (this.typeChar)
            {
                keyTyperRemover();
            }
            // Update type index after the typing character has been removed
             // Listens for backspace and removes characters if it is active
            if (this.backspace)
            {
                if (this.typeIndex != 0)
                {
                    Section mySection = Outliner.getAllSections().get(sectionId);
                    String myOldText = mySection.getText();
                    String leftText = myOldText.substring(0, this.typeIndex);
                    String rightText = myOldText.substring(this.typeIndex);
                    String myNewText = leftText.substring(0, leftText.length()-1)+rightText;
                    mySection.editText(myNewText);
                    this.typeIndex -= 1;
                    reDrawScreen();
                    try {
                        Outliner.saveForCtrlZ(this.myOutline);
                    } catch (JsonProcessingException ex) {
                        Logger.getLogger(SwingGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        try {
            keyHandler(e,1);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(SwingGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SwingGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(SwingGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (e.getKeyCode() == 90 && this.ctrl && Outliner.getSelected() == -1)
        {
            Outliner.setCtrlEvent(true);
            try {
                Outliner.loadPrevious();
            } catch (JsonProcessingException ex) {
                Logger.getLogger(SwingGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            Outliner.setSelected(-1);
            this.myOutline.resetSelected();
            this.typeChar = false;
        }
        if (e.getKeyCode() == 88 && this.ctrl && Outliner.getSelected() == -1)
        {
            Outliner.setCtrlEvent(true);
            try {
                Outliner.loadNext();
            } catch (JsonProcessingException ex) {
                Logger.getLogger(SwingGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            Outliner.setSelected(-1);
            this.myOutline.resetSelected();
            this.typeChar = false;
        }
        // Handle create top level section at the outline
        if (e.getKeyCode() == 10 && Outliner.getSelected() == -1)
        {
            this.myOutline.createSection("", null, Outliner.getSectionCount());
            Section newSection = this.myOutline.getSections().get(this.myOutline.getSections().size()-1);
            this.myOutline.setLeadingSection(this.myOutline.getSections().size()-1);
            newSection.setId(Outliner.getSelected()+1);
            Outliner.setSelected(0);
            this.myOutline.resetSelected();
            Outliner.reassignId(this.myOutline);
            try {
                Outliner.saveForCtrlZ(this.myOutline);
            } catch (JsonProcessingException ex) {
                Logger.getLogger(SwingGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // Select the first element
        if (e.getKeyCode() == 40 && Outliner.getSelected() == -1)
        {
            if (Outliner.getAllSections().isEmpty() == false)
            {
                Outliner.setSelected(0);
                this.typeChar = false;
                this.myOutline.resetSelected();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        try {
            keyHandler(e,2);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(SwingGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SwingGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(SwingGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
    * Method which reacts to all key-presses of the application ,it checks all
    * flags and acts accordingly
     * @param e KeyEvent containing the relevant key code
     * @param keyDetector The type of method called it key down or key up
     * @throws com.fasterxml.jackson.core.JsonProcessingException JSON exception
     * @throws java.net.URISyntaxException indicate that a string could not be parsed as a URI reference
    */
    public void keyHandler(KeyEvent e, int keyDetector) throws JsonProcessingException, IOException, URISyntaxException
    {
        this.typingSpace.stop();
        int sectionId = Outliner.getSelected();
        if (sectionId != -1)
        {
            // Check to remove the typing character before modifying string
            if (this.typeChar)
            {
                keyTyperRemover();
            }
            if (keyDetector == 1)
            {
                // For ctrl key to allow to move the sections
                if (e.getKeyCode() == 17)
                {
                    this.ctrl = true;
                }
                // For all characters which can be appended to the text
                if (e.getKeyCode() > 40 && e.getKeyCode() < 144 || e.getKeyCode() == 32 || e.getKeyCode() == 222)
                {
                    if (e.getKeyCode() == 83 && this.ctrl)
                    {
                        this.saveItemEvent();
                    }
                    else if (e.getKeyCode() == 90 && this.ctrl)
                    {
                        Outliner.setCtrlEvent(true);
                        Outliner.loadPrevious();
                        reDrawScreen();
                        Outliner.setSelected(-1);
                        this.myOutline.resetSelected();
                        this.typeChar = false;
                    }
                    else if (e.getKeyCode() == 67 && this.ctrl)
                    {
                        Section mySection = Outliner.getAllSections().get(sectionId);
                        if (mySection.isComplete())
                        {
                            mySection.markComplete(false);
                        }
                        else
                        {
                            mySection.markComplete(true);
                        }
                        System.out.println(mySection.isComplete());
                        this.changeMade = true;
                    }
                    else if (this.ctrl != true)
                    {
                        Section mySection = Outliner.getAllSections().get(sectionId);
                        mySection.addChar(e.getKeyChar(),this.typeIndex);
                        this.typeIndex += 1;
                        this.changeMade = true;
                    }
                }
                // For backspace
                if (e.getKeyCode() ==  KeyEvent.VK_BACK_SPACE)
                {
                    this.backspace = true;
                }
                // For arrow up and arrow down
                if (e.getKeyCode() == 38 || e.getKeyCode() == 40)
                {
                    if (this.ctrl)
                    {
                        // move the section within it's parent
                        if (Outliner.getSelected() != -1)
                        {
                            Section mySection = Outliner.getAllSections().get(sectionId);
                            if (mySection.getParent() != null)
                            {
                                Section parentSection = mySection.getParent();
                                if (parentSection.getContent().size() > 1)
                                {
                                    if (e.getKeyCode() == 38)
                                    {
                                        if (parentSection.getLocalId(mySection) > 0)
                                        {
                                            int newID = parentSection.getLocalId(mySection)-1;
                                            parentSection.deleteSubSection(newID+1);
                                            parentSection.setMiddleSectionWithoutCreate(mySection,newID);
                                            Outliner.reassignId(this.myOutline);
                                            Outliner.setSelected(mySection.getId());
                                            this.myOutline.resetSelected();
                                            this.typeIndex = Outliner.getAllSections().get(Outliner.getSelected()).getText().length();
                                            this.typeChar = false;
                                        }
                                    }
                                    else
                                    {
                                        if (parentSection.getLocalId(mySection) < parentSection.getContent().size()-1)
                                        {
                                            int newID = parentSection.getLocalId(mySection)+1;
                                            parentSection.deleteSubSection(newID-1);
                                            parentSection.setMiddleSectionWithoutCreate(mySection,newID);
                                            Outliner.reassignId(this.myOutline);
                                            Outliner.setSelected(mySection.getId());
                                            this.myOutline.resetSelected();
                                            this.typeIndex = Outliner.getAllSections().get(Outliner.getSelected()).getText().length();
                                            this.typeChar = false;
                                        }
                                    }
                                }
                            }
                            else
                            {
                                if (this.myOutline.getSections().size() > 1)
                                {
                                    if (e.getKeyCode() == 38)
                                    {
                                        if (this.myOutline.getLocalId(mySection) > 0)
                                        {
                                            int newID = this.myOutline.getLocalId(mySection)-1;
                                            this.myOutline.deleteSection(newID+1);
                                            this.myOutline.setMiddleSectionWithoutCreate(mySection,newID);
                                            Outliner.reassignId(this.myOutline);
                                            Outliner.setSelected(mySection.getId());
                                            this.myOutline.resetSelected();
                                            this.typeIndex = Outliner.getAllSections().get(Outliner.getSelected()).getText().length();
                                            this.typeChar = false;
                                        }
                                    }
                                    else
                                    {
                                        if (this.myOutline.getLocalId(mySection) < this.myOutline.getSections().size()-1)
                                        {
                                            int newID = this.myOutline.getLocalId(mySection)+1;
                                            this.myOutline.deleteSection(newID-1);
                                            this.myOutline.setMiddleSectionWithoutCreate(mySection,newID);
                                            Outliner.reassignId(this.myOutline);
                                            Outliner.setSelected(mySection.getId());
                                            this.myOutline.resetSelected();
                                            this.typeIndex = Outliner.getAllSections().get(Outliner.getSelected()).getText().length();
                                            this.typeChar = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        if (e.getKeyCode() == 38)
                        {
                            if (Outliner.getSelected()-1 != -1)
                            {
                                // Set the new selected and reset selected for each section
                                int checkHidden = 0;
                                while(Outliner.getAllSections().get(Outliner.getSelected()-1+checkHidden).isHidden())
                                {
                                    checkHidden-=1;
                                }
                                Outliner.setSelected(Outliner.getSelected()-1+checkHidden);
                                this.typeIndex = Outliner.getAllSections().get(Outliner.getSelected()).getText().length();
                                this.myOutline.resetSelected();
                            }
                        }
                        else
                        {
                            if (Outliner.getSelected()+1 != Outliner.getSectionCount())
                            {
                                // Set the new selected and reset selected for each section
                                int checkHidden = 0;
                                while(Outliner.getAllSections().get(Outliner.getSelected()+1+checkHidden).isHidden())
                                {
                                    checkHidden+=1;
                                }
                                Outliner.setSelected(Outliner.getSelected()+1+checkHidden);
                                this.typeIndex = Outliner.getAllSections().get(Outliner.getSelected()).getText().length();
                                this.myOutline.resetSelected();
                            }
                        }
                    }
                }
                if (e.getKeyCode() == 37 || e.getKeyCode() == 39)
                {
                    if (this.ctrl)
                    {
                        if (e.getKeyCode() == 37)
                        {
                            Section mySection = Outliner.getAllSections().get(sectionId);
                            Section addedSection;
                            if (mySection.getParent()!= null )
                            {
                                if (mySection.getParent().getParent() == null)
                                {
                                    addedSection = this.myOutline.moveSectionToTop(mySection,this.myOutline);
                                }
                                else
                                {
                                    addedSection = mySection.moveSectionToParent(mySection.getParent(),this.myOutline);
                                }
                            }
                            Outliner.setSelected(mySection.getId());
                            Outliner.reassignId(this.myOutline);
                            this.myOutline.resetSelected();
                            this.typeIndex = Outliner.getAllSections().get(Outliner.getSelected()).getText().length();
                            this.typeChar = false;
                        }
                        else
                        {
                            Section mySection = Outliner.getAllSections().get(sectionId);
                            Section addedSection;
                            if (mySection.getParent() == null)
                            {
                                addedSection = this.myOutline.moveSectionToBottom(mySection,this.myOutline);
                            }
                            else
                            {
                                addedSection = mySection.createContainerToMoveTo(this.myOutline);
                            }
                            Outliner.setSelected(mySection.getId());
                            Outliner.reassignId(this.myOutline);
                            this.myOutline.resetSelected();
                            this.typeIndex = Outliner.getAllSections().get(Outliner.getSelected()).getText().length();
                            this.typeChar = false;
                        }
                        this.changeMade = true;
                    }
                    else
                    {
                        if (e.getKeyCode() == 37)
                        {
                            if (this.typeIndex - 1 != -1)
                            {
                                this.typeIndex = this.typeIndex - 1;
                            }
                        }
                        else
                        {
                            if (this.typeIndex + 1 != Outliner.getAllSections().get(Outliner.getSelected()).getText().length()+1)
                            {
                                this.typeIndex = this.typeIndex + 1;
                            }
                        }
                    }
                }
                // If the key code is 10, we create a new section and place it in the same level as the selected
                if (e.getKeyCode() == 10)
                {
                    // check if the section is at the top level
                    Section currentSection = Outliner.getAllSections().get(Outliner.getSelected());
                    Section addedSection;
                    if (currentSection.getParent() != null)
                    {
                        addedSection = currentSection.createParentSection(this.myOutline);
                    }
                    else
                    {
                        addedSection = this.myOutline.createSectionAtId("", null, Outliner.getSectionCount(),this.myOutline);
                    }
                    Outliner.setSelected(addedSection.getId());
                    this.myOutline.resetSelected();
                    this.typeIndex = Outliner.getAllSections().get(Outliner.getSelected()).getText().length();
                    this.typeChar = false;
                    this.changeMade = true;
                }
                // If the user presses ESCAPE character, it unselects the section
                if (e.getKeyCode() == 27)
                {
                    Outliner.setSelected(-1);
                    this.myOutline.resetSelected();
                }
                // If the user has pressed DELETE character, delete currently selected Section
                if (e.getKeyCode() == 127)
                {
                    Section givenSection = Outliner.getAllSections().get(Outliner.getSelected());
                    if (Outliner.getAllSections().get(Outliner.getSelected()).getParent() != null)
                    {
                       Outliner.setSectionCount(-1);
                       Outliner.deleteAtId(givenSection.getId());
                       givenSection.getParent().deleteSubSectionWithMove(givenSection.getParent().getLocalId(givenSection));
                       Outliner.setSelected(givenSection.getParent().getId());
                       this.myOutline.resetSelected();
                    }
                    else
                    {
                       Outliner.setSectionCount(-1);
                       Outliner.deleteAtId(givenSection.getId());
                       this.myOutline.deleteTopLevelSection(this.myOutline.getLocalId(givenSection));
                       Outliner.setSelected(-1);
                       this.myOutline.resetSelected();
                    }
                    if (Outliner.getSelected() != -1)
                    {
                        this.typeIndex = Outliner.getAllSections().get(Outliner.getSelected()).getText().length();
                    }
                    this.typeChar = false;
                    this.changeMade = true;
                }
                if (this.changeMade)
                {
                    Outliner.saveForCtrlZ(this.myOutline);
                }
            }
            else if (keyDetector == 2)
            {
                // For ctrl key to allow to move the sections
                if (e.getKeyCode() == 17)
                {
                    this.ctrl = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                {
                    this.backspace = false;
                }
            }
        }
        this.changeMade = false;
        Outliner.reassignId(this.myOutline);
        reDrawScreen();
        this.typingSpace.restart();
    }
    
    
    /**
    * Method called by the timer to type or remove the typing character
    */
    public void keyTyper()
    {
        int sectionId = Outliner.getSelected();
        if (sectionId != -1)
        {
                if (this.typeChar)
                {
                    keyTyperRemover();
                }
                else
                {
                    Section mySection = Outliner.getAllSections().get(sectionId);
                    if (mySection.isHidden() == false)
                    {
                        try 
                        {
                        String myOldText = mySection.getText();
                        String leftText = myOldText.substring(0, this.typeIndex);
                        String rightText = myOldText.substring(this.typeIndex);
                        String myNewText = leftText+"|"+rightText;
                        mySection.editText(myNewText);
                        this.typeChar = true;
                        } catch (StringIndexOutOfBoundsException e)
                        {
                            System.out.println("String fault");
                        }
                    }
                }
                reDrawScreen();
        }
    }
    
    /**
    * Method called by keyTyper to remove the typing character
    */
    public void keyTyperRemover()
    {
        int sectionId = Outliner.getSelected();
        Section mySection = Outliner.getAllSections().get(sectionId);
        if (mySection.isHidden() == false)
        {
            String myOldText = mySection.getText();
            String leftText = myOldText.substring(0, this.typeIndex+1);
            String rightText = myOldText.substring(this.typeIndex+1);
            String myNewText = leftText.substring(0, leftText.length()-1)+rightText;
            mySection.editText(myNewText);
            this.typeChar = false;
        }
    }
    
}