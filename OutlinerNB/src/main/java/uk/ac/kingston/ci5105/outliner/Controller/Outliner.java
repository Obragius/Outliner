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
 * This is a Controller Class which is used to control the application as well 
 * as being instantiated to provide the top level container for the section
 * items
 * <p> This class statically stores currently loaded GUI object as well as
 * statically stores Sections which are present in the Outline </p>
 * <p> The object of the Outline is stored in the GUI to be changed and mimics
 * behaviour of a Section object. It is not statical to make it easier to 
 * convert to JSON to store and reload </p>
 * @author k1801606
 */
public class Outliner {
    
    // Name of the outline object, this will be used as the default for saving
    /**
    * The name of the outline used to save and load files with outline
    */
    private String name;
    // This should be the date the object has been modified
    /**
    * The date the object has been modified (Not in use)
     */
    private String date;
    // This contains Array of top level sections, which follow
    // hyrearchi and contain child sub-sections
    /**
    * ArrayList of top-level sections. These sections don't have parent objects
    */
    private ArrayList<Section> sections = new ArrayList();
    // This does not follow the hierachi and presents all 
    // sections as they dispay in the GUI
    /**
     * Static ArrayList which stores all the sections present in the outline
     * sequently, used to target sections using their id which corresponds
     * to their index within this ArrayList
     */
    private static ArrayList<Section> allSections = new ArrayList();
    // Keeps track of the number of sections in the outline
    /**
    * Statically stores the number of sections present in the loaded outline
    * Is updated when a new Outline is loaded
    */
    private static int sectionCount = 0;
    // Keeps track of the selected sections, so that it can be modified
    /**
    * Statically stores the id of the Section currently selected by the user
    * using the GUI. -1 means that no Section is selected, other integers
    * represent index inside allSections ArrayList
    */
    private static int sectionSelected = -1;
    // This will store JSON objects to allow to come back to previous state of the program
    /**
    * Statically stores JSON strings to allow to move back and forth between
    * different states of the Outline. Stores up to 100 JSON strings, then 
    * pops index 0 and appends last change on the end
    */
    private static ArrayList<String> allChanges = new ArrayList();
    // This will tell if the changes need to be save or if the ctrl z and ctrl x are used
    /**
    * True if the user is using Ctrl Key to navigate allChanges ArrayList
    */
    private static boolean ctrlEvent;
    // This will keep track of the ctrl z index
    /**
    * Stores the current index of the state loaded from the allChanges ArrayList
    */
    private static int ctrlIndex;
    // This will help reassign all id's
    /**
    * Static counter used by two static methods of reassignId and gatherSections
    * is used for easy of access between the two methods
    */
    private static int idCount;
    //This will store the gui object which is reponsible for all displays
    /**
    * Statically stores the GUI object currently used to display the Outline to the user
    */
    private static SwingGUI myGUI;
    
    
    /**
    * Main method which is used to run the application
    * It calls the onStartUp method
    * @param args Arguments given to the application
     * @throws com.fasterxml.jackson.core.JsonProcessingException JSON exception
     * @throws java.net.URISyntaxException indicate that a string could not be parsed as a URI reference
    */
    public static void main(String[] args) throws JsonProcessingException, URISyntaxException, IOException
    {
        Outliner.onStartUp();
    }
    
    /**
    * This method will start the application, create a new Outliner object
    * Load the first state to the allCghanges list
    * And load the GUI which will display the outline to static myGUI attribute
     * @throws com.fasterxml.jackson.core.JsonProcessingException JSON exception
     * @throws java.net.URISyntaxException indicate that a string could not be parsed as a URI reference
    */
    public static void onStartUp() throws JsonProcessingException, IOException, URISyntaxException
    {
        // Create main parent section and make it empty
        Outliner Outline = new Outliner();
        
        Outliner.saveForCtrlZ(Outline);
        // Run the view
        Outliner.myGUI = SwingGUI.main(null,Outline);
    }
    
    /**
    * Simple empty constructor
    */
    public Outliner()
    {
        
    }
    
    /**
    * Simple create section method which creates a top level section and appends
    * it to the end of sections list
    * @param text Text value to be stored in the section and displayed to the user
    * @param user List of users to be assigned to the Section , can be null
    * @param priority Can show the priority of the section (Unused)
    */
    public void createSection(String text,ArrayList<User> user, int priority)
    {
        // Create a section using the provided parameters
        // and give it a unqiue runtime id
        Section newSection = new Section(text, user, priority, new ArrayList(), Outliner.getSectionCount(), 0,null);
        this.sections.add(newSection);
        Outliner.setSectionCount(1);
        Outliner.reassignId(this);
    }
    
    /**
    * Method to create a new section at the top level of the Outline and move it
    * to a certain id provided
    * @param text Text value to be stored in the section and displayed to the user
    * @param user List of users to be assigned to the Section , can be null
    * @param priority Can show the priority of the section (Unused)
    * @param myOutline Currently loaded outline object
    * @return Section object which have been created to select it in the GUI
    */
    public Section createSectionAtId(String text,ArrayList<User> user, int priority, Outliner myOutline)
    {
        // Create a section using the provided parameters
        // and give it a unqiue runtime id
        Section newSection = new Section(text, user, priority, new ArrayList(), Outliner.getSectionCount(), 0,null);
        this.sections.add(newSection);
        int thisSectionId = this.getLocalId(Outliner.getAllSections().get(Outliner.getSelected()));
        newSection.setId(this.sections.get(thisSectionId+1).getId());
        this.setMiddleSection(newSection, thisSectionId+1);
        Outliner.setSectionCount(1);
        Outliner.reassignId(myOutline);
        return newSection;
    }
    
    /**
    * Method to remove a section from the top level of the outline given its ID
    * @param sectionID Id of the section to be deleted
    */
    public void deleteSection(int sectionID)
    {
        this.sections.remove(sectionID);
    }
    
    /**
    * Method to remove a top-level section and make sure all child Sections are
    * moved to the top-level
    * @param sectionID Id of the section to be deleted
    */
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
    /**
    * Method to save the current Outline to a file located in the target folder
    * of the project.The name of the JSON file will match the name of the Outline
    * @param outline Outliner to be saved in the file of .json format
     * @throws com.fasterxml.jackson.core.JsonProcessingException JSON exception
     * @throws java.net.URISyntaxException indicate that a string could not be parsed as a URI reference
    */
    public static void saveToJSON(Outliner outline) throws JsonProcessingException, URISyntaxException, IOException
    {
        ObjectMapper myMapper = new ObjectMapper();
        ArrayList myList = new ArrayList();
        myList.add(outline);
        String parentDir = Outliner.class.getProtectionDomain().getCodeSource().getLocation()
    .toURI().getPath();
        myMapper.writeValue(new File(parentDir+File.separator+".."+File.separator+outline.getName()+".json"),myList);
    }
    
    /**
    * Method to set ctrlEvent which is used to prevent saving changes when user
    * is using Ctrl+z or Ctrl+x
    * @param value true or false to set the boolean attribute
    */
    public static void setCtrlEvent(boolean value)
    {
        Outliner.ctrlEvent = value;
    }
    
    // This method will save the last 25 states of the outline to allow to go back
    /**
    * Method used to populate allChanges list with JSON strings using the 
    * current outline state, to be able to come back to that state at a later 
    * time
    * The JSON file always contains a list of length 1
    * @param outline Outliner object to be saved
     * @throws com.fasterxml.jackson.core.JsonProcessingException JSON exception
    */
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
    
    /**
    * Method to load a previous state of the outline from allChanges list using
    * ctrlIndex attribute of the class
     * @throws com.fasterxml.jackson.core.JsonProcessingException JSON exception
    */
    public static void loadPrevious() throws JsonProcessingException
    {
        if (Outliner.ctrlIndex != 0)
        {
            Outliner.ctrlIndex -= 1;
        }
        Outliner.loadJsonToOutline(Outliner.getJSON(Outliner.ctrlIndex));
        
    }
    
    /**
    * Method to load a next state of the outline from allChanges list using
    * ctrlIndex attribute of the class
     * @throws com.fasterxml.jackson.core.JsonProcessingException JSON exception
    */
    public static void loadNext() throws JsonProcessingException
    {
        if (Outliner.ctrlIndex+1 < Outliner.allChanges.size())
        {
            Outliner.ctrlIndex += 1;
        }
        Outliner.loadJsonToOutline(Outliner.getJSON(Outliner.ctrlIndex));
    }
    
    
    /**
    * Method that returns a JSON based on the provided index
     * @param id The index of the JSON in allChanges list
     * @return String of the JSON at that index
    */
    public static String getJSON(int id)
    {
        return Outliner.allChanges.get(id);
    }
    
    /**
    * Method to load an Outliner object stored in the target folder of the
    * project with a matching name to the provided String     
    * The JSON file always contains a list of length 1
     * @param outlineName The name of file JSON is stored in
     * @throws java.io.IOException File in file system exception
     * @throws java.net.URISyntaxException indicate that a string could not be parsed as a URI reference
    */
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
    /**
    * Method to load an Outliner object from a JSON given.Is used for implementation
    * of previous and next state load calls
     * @param givenJson JSON String containing the Outliner object to load
     * @throws com.fasterxml.jackson.core.JsonProcessingException JSON exception
    */
    public static void loadJsonToOutline(String givenJson) throws JsonProcessingException
    {
        ObjectMapper myMapper = new ObjectMapper();
        Outliner myOutline = myMapper.readValue(givenJson,Outliner.class);
        Outliner.reassignId(myOutline);
        Outliner.sectionCount = Outliner.getAllSections().size();
        Outliner.myGUI.setOutline(myOutline);
    }
    
    /**
    * Method to move a section from a second level to the top-level of the Outliner
    * This method is called when no Sections are deleted
     * @param section The Section to be moved to the top-level
     * @param myOutline The outline Object to move it in, used for reassignId
     * @return Section that has been moved
    */
    public Section moveSectionToTop(Section section, Outliner myOutline)
    {
        this.setMiddleSectionWithoutCreate(section, this.getLocalId(section.getParent())+1);
        section.getParent().deleteSubSection(section.getParent().getLocalId(section));
        Outliner.reassignId(myOutline);  
        section.setParent(null);
        return section;
    }
    
    /**
    * Method to move a section from a top-level to the second level of the Outliner
    * This method is called when no Sections are deleted
    * If there are no sections to set as parent new Hidden section is created
    * to act as an invisible container
     * @param section The Section to be moved to the second level
     * @param myOutline The outline Object to move it in, used for reassignId
     * @return Section that has been moved
    */
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
    
    /**
    * Method to return the ID of the given section within Outline.sections list
     * @param givenSection Section which local id is required
     * @return int id of the given section within the outline object
    */
    public int getLocalId(Section givenSection)
    {
        return this.sections.indexOf(givenSection);
    }
    
    /**
    * Method to reset the count of the Sections present in the Outline is used 
    * when a new outline is loaded from a file or JSON
     * @param count The integer value to be set
    */
    public static void setSectionCountImidiate(int count)
    {
        Outliner.sectionCount = count;
    }

    /**
    * Getter for name attribute
     * @return String name
    */
    public String getName() 
    {
        return this.name;
    }

    /**
    * Setter for name attribute
     * @param name New name to set to
    */
    public void setName(String name) 
    {
        this.name = name;
    }

    /**
    * Getter for date attribute
     * @return String date
    */
    public String getDate() 
    {
        return this.date;
    }

    /**
    * Setter for date attribute
     * @param date New date to set to
    */
    public void setDate(String date) 
    {
        this.date = date;
    }
   
    /**
    * Setter for sectionSelected attribute
     * @param id New int to set to
    */
    // Sets the id of sections which is currently selected
    public static void setSelected(int id)
    {
        Outliner.sectionSelected = id;
    }
    
    /**
    * Getter for sectionSelected attribute
     * @return integer for the id of the selected section
    */
    public static int getSelected()
    {
        return Outliner.sectionSelected;
    }
    
    // Changes the selected state boolean within the sections object
    /**
    * Goes through each section un-selecting it, and selects a new section if 
    * it id matches the sectionSelected attribute value
    */
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
    /**
    * Method used to reassign id values and reset parent relationship to all
    * sections, called all the time something changes in the model regarding
    * sections in top-level and lower levels
     * @param myOutline The outline object to reassign id for
    */
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
    /**
    * Support method using recursion to gather all child sections and assign 
    * correct id and parent to them based on this outline
     * @param givenSection The section for which to gather all child nodes
     * @return ArrayList of either just this section or all child section as well as parent sections
    */
    public static ArrayList<Section> gatherSections(Section givenSection)
    {
        ArrayList<Section> newList = new ArrayList();
        if (givenSection.getContent().isEmpty())
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
    
    /**
    * Method to delete a section t given id within the whole outline
     * @param index at which to delete the section
    */
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
    
    /**
    * Method to add a section on the end of the Outline
     * @param section object to add to the outline
    */
    public static void addSection(Section section)
    {
        Outliner.allSections.add(section);
    }
    
    /**
    * Method to remove the first matching section from the Outline
     * @param section object to remove from the outline
    */
    public static void removeSection(Section section)
    {
        Outliner.allSections.remove(section);
    }
    
    /**
    * Method in high demand, returns a list of all sections in the outline
     * @return ArrayList of all sections currently loaded
    */
    public static ArrayList<Section> getAllSections()
    {
        return Outliner.allSections;
    }
    
    /**
    * Method to return all real Sections ignoring empty containers (Not in use)
     * @return ArrayList of all visible sections currently loaded
    */
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
    
    /**
    * Method to return sections in this Outliner object
     * @return ArrayList of all top-level sections
    */
    public ArrayList<Section> getSections() 
    {
        return this.sections;
    }

    /**
    * Method to set a new list to list of top-level sections
     * @param sections new list to set to
    */
    public void setSections(ArrayList<Section> sections) 
    {
        this.sections = sections;
    }
    
    /**
    * Method to return how many sections are currently in the outline
     * @return int of sections loaded
    */
    public static int getSectionCount()
    {
        return Outliner.sectionCount;
    }
    
    // Gives each section a unqiue ID, should not go down, as
    // it may lead to two sections having the same id
    /**
    * Method to increment or decrement the number of sections in the outline
     * @param value by which to change the current count
     * @return number of sections before the change
    */
    public static int setSectionCount(int value)
    {
        int oldSectionCount = Outliner.sectionCount;
        Outliner.sectionCount += value;
        return oldSectionCount;
    }
    
    /**
    * Method to set a section in the top level at index 0 within this object
     * @param id of the section to move to index 0 within this object
    */
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
    
    /**
    * Method to set a section at certain index within this object
    * This method WILL delete the section at the end of this object container
    * because it is used when the section IS created at the end of the container
     * @param givenSection the section to set to the new index
     * @param newID the new index to move the section to
    */
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
    
    /**
    * Method to set a section at certain index within this object
    * This method will NOT delete the section at the end of this object container
    * because it is used when the section is NOT created at the end of the container
     * @param givenSection the section to set to the new index
     * @param newID the new index to move the section to
    */
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
