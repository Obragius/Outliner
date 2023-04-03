/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uk.ac.kingston.ci5105.outliner.Controller;
import uk.ac.kingston.ci5105.outliner.Model.*;
import uk.ac.kingston.ci5105.outliner.View.*;
import java.util.ArrayList;


/**
 *
 * @author lolki
 */
public class Outliner {
    
    private String name;
    private String date;
    private ArrayList<Section> sections = new ArrayList();
    private static int sectionCount = 0;
    
    
    public static void main(String[] args)
    {
        Outliner.onStartUp();
    }
    
    public static void onStartUp()
    {
        // Create main parent section and make it empty
        Outliner Outline = new Outliner();
        Outline.createSection("I love dogs",null,null,0);
        Outline.createSection("I love cats",null,null,0);
        Outline.setName("My outline");
        
        
        // Run the view
        SwingGUI.main(null,Outline);
    }
    
    public void createSection(String text,User[] user, String[] tag, int priority)
    {
        // Create a section using the provided parameters
        // and give it a unqiue runtime id
        this.sections.add(new Section(text, user, tag, priority, new ArrayList(), this.getSectionCount()));
    }
    
    public void deleteSection(int sectionID)
    {
        this.sections.remove(sectionID);
    }

    public String getName() 
    {
        return this.name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getDate() 
    {
        return this.date;
    }

    public void setDate(String date) 
    {
        this.date = date;
    }

    public ArrayList<Section> getSections() 
    {
        return this.sections;
    }

    public void setSections(ArrayList<Section> sections) 
    {
        this.sections = sections;
    }
    
    public static int getSectionCount()
    {
        return Outliner.sectionCount;
    }
    
    public static int setSectionCount()
    {
        int oldSectionCount = Outliner.sectionCount;
        Outliner.sectionCount += 1;
        return oldSectionCount;
    }
    
    
    
    
}
