/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uk.ac.kingston.ci5105.outliner.Controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import uk.ac.kingston.ci5105.outliner.Model.*;
import uk.ac.kingston.ci5105.outliner.View.*;
import java.util.ArrayList;
import java.util.List;


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
    // This will store JSON objects to allow to come back to previous state of the program
    private static ArrayList<String> allChanges = new ArrayList();
    // This will tell if the changes need to be save or if the ctrl z and ctrl x are used
    private static boolean ctrlEvent;
    // This will keep track of the ctrl z index
    private static int ctrlIndex;
    // This will help reassign all id's
    private static int idCount;
    //This will store the gui object which is reponsible for all displays
    private static SwingGUI myGUI;
    
    
    public static void main(String[] args) throws JsonProcessingException, URISyntaxException, IOException
    {
        Outliner.onStartUp();
    }
    
    public static void onStartUp() throws JsonProcessingException, IOException, URISyntaxException
    {
        // Create main parent section and make it empty
        Outliner Outline = new Outliner();
        
        Outliner.saveForCtrlZ(Outline);
        // Run the view
        Outliner.myGUI = SwingGUI.main(null,Outline);
    }
    
    public void createSection(String text,ArrayList<User> user, int priority)
    {
        // Create a section using the provided parameters
        // and give it a unqiue runtime id
        Section newSection = new Section(text, user, priority, new ArrayList(), this.getSectionCount(), 0,null);
        this.sections.add(newSection);
        Outliner.setSectionCount(1);
        Outliner.reassignId(this);
    }
    
    public Section createSectionAtId(String text,ArrayList<User> user, int priority, Outliner myOutline)
    {
        // Create a section using the provided parameters
        // and give it a unqiue runtime id
        Section newSection = new Section(text, user, priority, new ArrayList(), this.getSectionCount(), 0,null);
        this.sections.add(newSection);
        int thisSectionId = this.getLocalId(Outliner.getAllSections().get(Outliner.getSelected()));
        int nextSectionId = this.sections.get(thisSectionId+1).getId();
        newSection.setId(this.sections.get(thisSectionId+1).getId());
        this.setMiddleSection(newSection, thisSectionId+1);
        Outliner.setSectionCount(1);
        Outliner.reassignId(myOutline);
        return newSection;
    }
    
    // Delete a top level section from the Outline object
    public void deleteSection(int sectionID)
    {
        this.sections.remove(sectionID);
    }
    
    public void deleteTopLevelSection(int sectionID)
    {
         // Before deleting a section at the top level, move all children nodes to the top level
        ArrayList<Section> myNewSections = new ArrayList();
        for (int i = 0; i < this.sections.get(sectionID).getContent().size();i++)
        {
            myNewSections.add(this.sections.get(sectionID).getContent().get(i));
            // make sure that their parent is set to null
            this.sections.get(sectionID).getContent().get(i).setParent(null);
        }
        for (int i = 0; i < this.sections.size(); i++)
        {
            myNewSections.add(this.sections.get(i));
        }
        myNewSections.remove(this.sections.get(sectionID));
        this.setSections(myNewSections);
    }
    
    // This will be used to create a json version of this object and save them
    public static void saveToJSON(Outliner outline) throws JsonProcessingException, URISyntaxException, IOException
    {
        ObjectMapper myMapper = new ObjectMapper();
        ArrayList myList = new ArrayList();
        myList.add(outline);
        String parentDir = Outliner.class.getProtectionDomain().getCodeSource().getLocation()
    .toURI().getPath();
        myMapper.writeValue(new File(parentDir+File.separator+".."+File.separator+outline.getName()+".json"),myList);
    }
    
    public static void setCtrlEvent(boolean value)
    {
        Outliner.ctrlEvent = value;
    }
    
    // This method will save the last 25 states of the outline to allow to go back
    public static void saveForCtrlZ(Outliner outline) throws JsonProcessingException
    {
        if (Outliner.ctrlEvent == false)
        {
            ObjectMapper myMapper = new ObjectMapper();
            String myJson = myMapper.writeValueAsString(outline);
            ArrayList newChanges = new ArrayList();
            if (Outliner.allChanges.size() >= 100)
            {
                for (int i = 1; i < Outliner.allChanges.size();i++)
                {
                    newChanges.add(Outliner.allChanges.get(i));
                }
                newChanges.add(myJson);
                System.out.println("New change saved");
                Outliner.allChanges = newChanges;
            }
            else
            {
                Outliner.allChanges.add(myJson);
            }
            Outliner.ctrlIndex = Outliner.allChanges.size()-1;
        }
        else
        {
            Outliner.ctrlEvent = false;
        }
    }   
    
    public static void loadPrevious() throws JsonProcessingException
    {
        System.out.println("loading previous");
        if (Outliner.ctrlIndex != 0)
        {
            Outliner.ctrlIndex -= 1;
        }
        Outliner.loadJsonToOutline(Outliner.getJSON(Outliner.ctrlIndex));
        
    }
    
    public static void loadNext() throws JsonProcessingException
    {
        System.out.println("loading next");
        if (Outliner.ctrlIndex+1 < Outliner.allChanges.size())
        {
            Outliner.ctrlIndex += 1;
        }
        Outliner.loadJsonToOutline(Outliner.getJSON(Outliner.ctrlIndex));
    }
    
    
    
    public static String getJSON(int id)
    {
        return Outliner.allChanges.get(id);
    }
    
    public static void loadJsonFromFile(String outlineName) throws IOException, URISyntaxException
    {
        ObjectMapper myMapper = new ObjectMapper();
        String parentDir = Outliner.class.getProtectionDomain().getCodeSource().getLocation()
    .toURI().getPath();
        List<Outliner> listOutliner = myMapper.readValue(new File(parentDir+File.separator+".."+File.separator+outlineName+".json"),new TypeReference<List<Outliner>>(){});
        Outliner newOutline = listOutliner.get(0);
        Outliner.reassignId(newOutline);
        Outliner.sectionCount = Outliner.getAllSections().size();
        Outliner.myGUI.setOutline(newOutline);
    }
    
    //This will be used to reaload json into the new object
    public static void loadJsonToOutline(String givenJson) throws JsonProcessingException
    {
        ObjectMapper myMapper = new ObjectMapper();
        Outliner myOutline = myMapper.readValue(givenJson,Outliner.class);
        Outliner.reassignId(myOutline);
        Outliner.sectionCount = Outliner.getAllSections().size();
        Outliner.myGUI.setOutline(myOutline);
    }
    
    public Section moveSectionToTop(Section section, Outliner myOutline)
    {
        this.setMiddleSectionWithoutCreate(section, this.getLocalId(section.getParent())+1);
        section.getParent().deleteSubSection(section.getParent().getLocalId(section));
        Outliner.reassignId(myOutline);  
        section.setParent(null);
        return section;
    }
    
    public Section moveSectionToBottom(Section section, Outliner myOutline)
    {
        if (this.getLocalId(section)-1 != -1)
        {
            
            Section newParent = Outliner.getAllSections().get(section.getId()-1);
            newParent.setMiddleSectionWithoutCreate(section, newParent.getContent().size());
            this.deleteSection(this.getLocalId(section));
            section.setParent(newParent);
            Outliner.reassignId(myOutline);  
        }
        else
        {
            this.createSection("", null, Outliner.getSectionCount());
            Section newSection = this.getSections().get(this.getSections().size()-1);
            int thisSectionID = this.getLocalId(section);
            this.setMiddleSection(newSection,thisSectionID+1);
            Outliner.reassignId(myOutline);
            newSection.setHidden(true);
            ArrayList<Section> newList = new ArrayList();
            newList.add(section);
            newSection.setContent(newList);
            this.deleteSection(this.getLocalId(section));
            section.setParent(newSection);
        }
        return section;
    }
    
    public int getLocalId(Section givenSection)
    {
        return this.sections.indexOf(givenSection);
    }
    
    public static void setSectionCountImidiate(int count)
    {
        Outliner.sectionCount = count;
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
    
    // new reasign id method 
    public static void reassignId(Outliner myOutline)
    {
        Outliner.idCount = 0;
        ArrayList<Section> newList = new ArrayList();
        for (int i = 0; i < myOutline.sections.size(); i++)
        {
            if (myOutline.sections.size() != 0)
            {
                myOutline.sections.get(i).setParent(null);
                newList.addAll(Outliner.gatherSections(myOutline.sections.get(i)));
            }
        }
        Outliner.allSections = newList;
    }
    
    // This method will also set parent sections correctly
    public static ArrayList<Section> gatherSections(Section givenSection)
    {
        ArrayList<Section> newList = new ArrayList();
        if (givenSection.getContent().size() == 0)
        {
            newList.add(givenSection);
            givenSection.setId(Outliner.idCount);
            Outliner.idCount += 1;
        }
        else
        {
            newList.add(givenSection);
            givenSection.setId(Outliner.idCount);
            Outliner.idCount += 1;
            for (int i = 0; i < givenSection.getContent().size();i++)
            {
                givenSection.getContent().get(i).setParent(givenSection);
                newList.addAll(Outliner.gatherSections(givenSection.getContent().get(i)));
            }   
        }
        return newList;
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
    
    public static ArrayList<Section> getAllNotHidden()
    {
        ArrayList myList = new ArrayList();
        for (int i = 0; i < Outliner.allSections.size();i++)
        {
            if (Outliner.allSections.get(i).isHidden() == false)
            {
                myList.add(Outliner.allSections.get(i));
            }
        }
        return myList;
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
    
    public void setMiddleSection (Section givenSection, int newID)
    {
        ArrayList newList = new ArrayList();
        for (int i = 0; i <= newID-1;i++)
        {
            newList.add(this.sections.get(i));
        }
        newList.add(givenSection);
        for (int i = newID; i < this.sections.size()-1;i++)
        {
            newList.add(this.sections.get(i));
        }
        this.sections = newList;
    }
    
    public void setMiddleSectionWithoutCreate(Section givenSection, int newID)
    {
        ArrayList newList = new ArrayList();
        for (int i = 0; i <= newID-1;i++)
        {
            newList.add(this.sections.get(i));
        }
        newList.add(givenSection);
        for (int i = newID; i < this.sections.size();i++)
        {
            newList.add(this.sections.get(i));
        }
        this.sections = newList;
    }
    
}
