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
    
    // Name of the outline object, this will be used as the default for saving
    private String name;
    // This should be the date the object has been modified
    private String date;
    // This contains Array of top level sections, which follow
    // hyrearchi and contain child sub-sections
    private ArrayList<Section> sections = new ArrayList();
    // This does not follow the hierachi and presents all 
    // sections as they dispay in the GUI
    private static ArrayList<Section> allSections = new ArrayList();
    // Keeps track of the number of sections in the outline
    private static int sectionCount = 0;
    // Keeps track of the selected sections, so that it can be modified
    private static int sectionSelected = -1;
    
    
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
        Section newSection = new Section(text, user, tag, priority, new ArrayList(), this.getSectionCount(), 0,null);
        this.sections.add(newSection);
        Outliner.setSectionCount(1);
        Outliner.addSection(newSection);
    }
    
    // Delete a top level section from the Outline object
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
   
    // Sets the id of sections which is currently selected
    public static void setSelected(int id)
    {
        Outliner.sectionSelected = id;
    }
    
    public static int getSelected()
    {
        return Outliner.sectionSelected;
    }
    
    // Changes the selected state boolean within the sections object
    public void resetSelected()
    {
       System.out.println(Outliner.sectionCount);
       for (int i = 0; i < Outliner.sectionCount; i++)
       {
           if (i != Outliner.sectionSelected)
           {
               Outliner.allSections.get(i).unSelect();
           }
           else
           {
               Outliner.allSections.get(i).markSelected();
           }
       }
    }
    
    public static void reassignId(int index, Section givenSection)
    {
        ArrayList newList = new ArrayList();
        for (int i = 0; i <= index; i++)
        {
            newList.add(Outliner.allSections.get(i));
        }
        
        newList.add(givenSection);
        for (int i = index+1; i < Outliner.sectionCount-1; i++)
        {
            Outliner.allSections.get(i).setId(Outliner.allSections.get(i).getId()+1);
            newList.add(Outliner.allSections.get(i));
        }
        Outliner.allSections = newList;
    }
    
    public static void deleteAtId(int index)
    {
        ArrayList<Section> newList = new ArrayList();
        for (int i = 0; i < index; i++)
        {
            newList.add(Outliner.allSections.get(i));
        }
        for (int i = index+1; i <= Outliner.sectionCount; i++)
        {
            Outliner.allSections.get(i).setId(Outliner.allSections.get(i).getId()-1);
            newList.add(Outliner.allSections.get(i));
        }
        for (int i = 0; i < Outliner.sectionCount; i++)
        {
            System.out.println(newList.get(i).getText());
        }
        Outliner.allSections = newList;
        
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
    
    // Gives each section a unqiue ID, should not go down, as
    // it may lead to two sections having the same id
    public static int setSectionCount(int value)
    {
        int oldSectionCount = Outliner.sectionCount;
        Outliner.sectionCount += value;
        return oldSectionCount;
    }
    
    public void setLeadingSection (int id)
    {
        ArrayList newList = new ArrayList();
        newList.add(this.sections.get(id));
        for (int i = 0; i < this.sections.size();i++)
        {
            if (i != id)
            {
                newList.add(this.sections.get(i));
            }
        }
        this.sections = newList;
    }
    
    
    
    
}
