/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uk.ac.kingston.ci5105.outliner.View;
import java.awt.Color;
import java.awt.Component;
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
/**
 *
 * @author lolki
 */
public class SwingGUI extends JFrame implements MouseListener
{
    private JFrame myFrame;
    private JTextArea myText;
    private JPanel myPanel;
    private ArrayList<JLabel> allJLabels;
    private Outliner myOutline;
    
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
       this.myOutline = Outline;
       
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
        System.out.println("Clicked");
        Component firedLabel = e.getComponent();
        System.out.println(firedLabel.getName()); 
        Section mySection = this.myOutline.getAllSections().get(Integer.parseInt(firedLabel.getName()));
        int sectionID = mySection.getId();
        Outliner.setSelected(sectionID);
        this.myOutline.resetSelected();
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
    
}