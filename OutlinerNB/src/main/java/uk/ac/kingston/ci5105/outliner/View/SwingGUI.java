/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uk.ac.kingston.ci5105.outliner.View;
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
    private JLabel myLabel;
    private JLabel myLabel2;
    private JLabel myLabel3;
    
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
       
       
       this.myLabel = new JLabel();
       this.myLabel.setText(Outline.getSections().get(0).getText());
       this.myLabel2 = new JLabel();
       this.myLabel2.setText(Outline.getSections().get(0).getText());
       this.myLabel3 = new JLabel();
       this.myLabel3.setText(Outline.getSections().get(0).getText());
       
       
       this.myPanel = new JPanel();
       this.myPanel.setLayout(new BoxLayout(this.myPanel, BoxLayout.Y_AXIS));
       this.myPanel.add(this.myLabel);
       this.myPanel.add(this.myLabel2);
       this.myPanel.add(this.myLabel3);
       
       this.myFrame.add(this.myPanel);
       
       this.myFrame.setVisible(true);
       
    }
    
}