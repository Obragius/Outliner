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
    private static ArrayList<Section> allSections = new ArrayList();
    private static int sectionCount = 0;
    
    
    public static void main(String[] args)
    {
        Outliner.onStartUp();
    }
    
    public static void onStartUp()
    {
        // Create main parent section and make it empty
        Outliner Outline = new Outliner();
        Outline.createSection("I love dogs",null,null,Outliner.sectionCount);
        Section firstSection = Outline.getSections().get(0);
        firstSection.createSubSection("I love cute dogs", null, null, Outliner.sectionCount);
        Section secondSection = firstSection.getContent().get(0);
        secondSection.createSubSection("Cute dogs are the best", null, null, Outliner.sectionCount);
        firstSection.createSubSection("Cute dogs are great", null, null, Outliner.sectionCount);
        Outline.createSection("I love cats",null,null,Outliner.sectionCount);
        Outline.setName("My outline");
        
        
        // Run the view
        SwingGUI.main(null,Outline);
    }
    
    public void createSection(String text,User[] user, String[] tag, int priority)
    {
        // Create a section using the provided parameters
        // and give it a unqiue runtime id
        Section newSection = new Section(text, user, tag, priority, new ArrayList(), this.getSectionCount(), 0);
        this.sections.add(newSection);
        Outliner.setSectionCount();
        Outliner.addSection(newSection);
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
    
    public static void addSection(Section section)
    {
        Outliner.allSections.add(section);
    }
    
    public static void removeSection(Section section)
    {
        Outliner.allSections.remove(section);
    }
    
    public static ArrayList<Section> getAllSections()
    {
        return Outliner.allSections;
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
