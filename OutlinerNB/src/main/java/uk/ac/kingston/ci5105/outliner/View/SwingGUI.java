/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uk.ac.kingston.ci5105.outliner.View;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import uk.ac.kingston.ci5105.outliner.Model.*;
import uk.ac.kingston.ci5105.outliner.Controller.*;


import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.Timer;
/**
 *
 * @author lolki
 */
public class SwingGUI extends JFrame implements MouseListener, KeyListener
{
    // Main frame of the program
    private JFrame myFrame;
    // Unused text area, maybe needed later
    private JTextArea myText;
    // The panel which contains all the labels with the sections
    private JPanel myPanel;
    // ArrayList which contains all Jlabels, it is proceduraly generated
    // by accessing the outliner object
    private ArrayList<JLabel> allJLabels;
    // Holds the outline object so that it can be accessed
    // anywhere within the class
    private Outliner myOutline;
    // Boolean value to allow for backspace to be pressed down and
    // work with keyTyped method
    private boolean backspace;
    // to keep track of the type character
    private boolean typeChar;
    // to keep track of the index at which typing character is
    private int typeIndex;
    
    public static void main(String[] args, Outliner Outline)
    {
        // Initiates the GUI object with the Outline provided
        SwingGUI myGUI = new SwingGUI(Outline);
    }
    
    public SwingGUI(Outliner Outline)
    {
       // Basic setup for the frame
       this.myFrame = new JFrame();
       this.myFrame.setTitle("Outliner");
       this.myFrame.setSize(600,1200);
       this.myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // While the frame is in focus, listen for key presses
       this.myFrame.addKeyListener(this);
       // Set the object-wide outline object
       this.myOutline = Outline;
       
       ActionListener controlTyper = new ActionListener() { 
           @Override
           public void actionPerformed(ActionEvent e) {
               keyTyper();
           }
       };
       Timer typingSpace = new Timer(500, controlTyper);
       typingSpace.setRepeats(true);
       typingSpace.start();
       
       // Call method to generate all the labels
       reDrawScreen();
       
    }
    
    public void reDrawScreen()
    {
       // Get all jlabels using the two recursive methods
       allJLabels = constructJLabel(); 
       
       this.myPanel = new JPanel();
       this.myPanel.setLayout(new BoxLayout(this.myPanel, BoxLayout.Y_AXIS));
       
       for (int i = 0; i < allJLabels.size(); i++)
       {
           this.myPanel.add(allJLabels.get(i));
       }
       
       
       this.myFrame.add(this.myPanel);
       
       this.myFrame.setVisible(true);
    }
    
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
    
    public ArrayList<JLabel> constructSectionJLabel(Section givenSection, Integer level)
    {
        // Construct the level indent text to add to all text in this level
        String addedText = "";
            for (int i = 0;i < level;i++)
            {
                addedText += "      ";
            }
        JLabel myLabel = new JLabel();
        myLabel.setOpaque(true);
        if (givenSection.isSelected())
        {
            myLabel.setBackground(Color.red);
        }
        myLabel.setText(addedText+givenSection.getText());
        myLabel.addMouseListener(this);
        Integer id = givenSection.getId();
        myLabel.setName(id.toString());
        // If the section doesn't have child nodes, only return this section text
        if (givenSection.getContent().size() == 0)
        {
            ArrayList myLabelList = new ArrayList();
            myLabelList.add(myLabel);
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
            ArrayList myLabelList = new ArrayList();
            myLabelList.add(myLabel);
            myLabelList.addAll(mySubLabelList);
            return myLabelList;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Code to select a section so that it can be edited
        Component firedLabel = e.getComponent();
        Section mySection = this.myOutline.getAllSections().get(Integer.parseInt(firedLabel.getName()));
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

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

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
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyHandler(e,1);
        // Handle create top level section at the outline
        if (e.getKeyCode() == 10 && Outliner.getSelected() == -1)
        {
            System.out.println(Outliner.getSelected());
            this.myOutline.createSection("This is a new section", null, null, Outliner.getSectionCount());
            Section newSection = this.myOutline.getSections().get(this.myOutline.getSections().size()-1);
            this.myOutline.setLeadingSection(this.myOutline.getSections().size()-1);
            newSection.setId(Outliner.getSelected()+1);
            Outliner.reassignId(Outliner.getSelected(), newSection);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyHandler(e,2);
    }
    
    public void keyHandler(KeyEvent e, int keyDetector)
    {
        System.out.println(e.getKeyCode());
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
                // For all characters which can be appended to the text
                if (e.getKeyCode() > 40 && e.getKeyCode() < 144 || e.getKeyCode() == 32)
                {
                    Section mySection = Outliner.getAllSections().get(sectionId);
                    mySection.addChar(e.getKeyChar(),this.typeIndex);
                    this.typeIndex += 1;
                }
                // For backspace
                if (e.getKeyCode() ==  KeyEvent.VK_BACK_SPACE)
                {
                    this.backspace = true;
                }
                // For arrow up and arrow down
                if (e.getKeyCode() == 38 || e.getKeyCode() == 40)
                {
                    if (e.getKeyCode() == 38)
                    {
                        if (Outliner.getSelected()-1 != -1)
                        {
                            // Set the new selected and reset selected for each section
                            Outliner.setSelected(Outliner.getSelected()-1);
                            this.typeIndex = Outliner.getAllSections().get(Outliner.getSelected()).getText().length();
                            this.myOutline.resetSelected();
                        }
                    }
                    else
                    {
                        if (Outliner.getSelected()+1 != Outliner.getSectionCount())
                        {
                            // Set the new selected and reset selected for each section
                            Outliner.setSelected(Outliner.getSelected()+1);
                            this.typeIndex = Outliner.getAllSections().get(Outliner.getSelected()).getText().length();
                            this.myOutline.resetSelected();
                        }
                    }
                }
                if (e.getKeyCode() == 37 || e.getKeyCode() == 39)
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
                // If the key code is 10, we create a new section and place it in the same level as the selected
                if (e.getKeyCode() == 10)
                {
                    System.out.println(Outliner.getSelected());
                    Section currentSection = Outliner.getAllSections().get(Outliner.getSelected());
                    currentSection.createSubSection("This is a new section", null, null, Outliner.getSectionCount());
                    Section newSection = currentSection.getContent().get(currentSection.getContent().size()-1);
                    currentSection.setLeadingSection(currentSection.getContent().size()-1);
                    newSection.setId(Outliner.getSelected()+1);
                    Outliner.reassignId(Outliner.getSelected(), newSection);
                    
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
                       this.myOutline.deleteTopLevelSection(givenSection.getId());
                       Outliner.setSelected(-1);
                       this.myOutline.resetSelected();
                    }
                    if (Outliner.getSelected() != -1)
                    {
                        this.typeIndex = Outliner.getAllSections().get(Outliner.getSelected()).getText().length();
                    }
                    this.typeChar = false;
                }
            }
            else if (keyDetector == 2)
            {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                {
                    this.backspace = false;
                }
            }
        }
        reDrawScreen();
    }
    
    
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
                String myOldText = mySection.getText();
                String leftText = myOldText.substring(0, this.typeIndex);
                String rightText = myOldText.substring(this.typeIndex);
                String myNewText = leftText+"|"+rightText;
                mySection.editText(myNewText);
                this.typeChar = true;
            }
            reDrawScreen();
        }
    }
    
    public void keyTyperRemover()
    {
        int sectionId = Outliner.getSelected();
        Section mySection = Outliner.getAllSections().get(sectionId);
        String myOldText = mySection.getText();
        String leftText = myOldText.substring(0, this.typeIndex+1);
        String rightText = myOldText.substring(this.typeIndex+1);
        String myNewText = leftText.substring(0, leftText.length()-1)+rightText;
        mySection.editText(myNewText);
        this.typeChar = false;
    }
    
}