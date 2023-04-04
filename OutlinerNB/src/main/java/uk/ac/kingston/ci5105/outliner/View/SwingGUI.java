/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uk.ac.kingston.ci5105.outliner.View;
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
/**
 *
 * @author lolki
 */
public class SwingGUI extends JFrame 
{
    private JFrame myFrame;
    private JTextArea myText;
    private JPanel myPanel;
    private ArrayList<JLabel> allJLabels;
    
    public static void main(String[] args, Outliner Outline)
    {
        new SwingGUI(Outline);
    }
    
    public SwingGUI(Outliner Outline)
    {
        // Basic setup for the frame
       this.myFrame = new JFrame();
       this.myFrame.setTitle("Outliner");
       this.myFrame.setSize(600,1200);
       this.myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
       reDrawScreen(Outline);
       
    }
    
    public void reDrawScreen(Outliner Outline)
    {
       // Get all jlabels using the two recursive methods
       allJLabels = constructJLabel(Outline); 
       
       this.myPanel = new JPanel();
       this.myPanel.setLayout(new BoxLayout(this.myPanel, BoxLayout.Y_AXIS));
       
       for (int i = 0; i < allJLabels.size(); i++)
       {
           this.myPanel.add(allJLabels.get(i));
       }
       
       
       this.myFrame.add(this.myPanel);
       
       this.myFrame.setVisible(true);
    }
    
    public ArrayList<JLabel> constructJLabel(Outliner Outline)
    {
        // If the outline is empty return nothing
        if (Outline.getSections().size() == 0)
        {
            return null;
        }
        // Else for each top level section, return all the text it produces
        else
        {
            ArrayList myLabelList = new ArrayList();
            for (int i = 0; i < Outline.getSections().size();i++)
            {
                myLabelList.addAll(this.constructSectionJLabel(Outline.getSections().get(i),0));
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
        // If the section doesn't have child nodes, only return this section text
        if (givenSection.getContent().size() == 0)
        {
            JLabel myLabel = new JLabel();
            myLabel.setText(addedText+givenSection.getText());
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
            JLabel myLabel = new JLabel();
            myLabel.setText(addedText+givenSection.getText());
            ArrayList myLabelList = new ArrayList();
            myLabelList.add(myLabel);
            myLabelList.addAll(mySubLabelList);
            return myLabelList;
        }
    }
    
}