/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uk.ac.kingston.ci5105.outliner.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import uk.ac.kingston.ci5105.outliner.Controller.Outliner;

/**
 * This is a Model Class which is used to represent section in the outline
 * @author k1801606
 */
public class Section {
    
    // This holds the text value of the section, the text that is being diplayed
    /**
    * Text which is stored within a section and is displayed to the user
    */
    private String text;
    // This is the user object which is related to this section
    /**
    * A list of users attached to this section (Only one user can be set right now)
    */
    private ArrayList<User> user;
    // This is array of tags which are associated with this section
    /**
    * Text list of all tags the user adds to the section
    */
    private ArrayList<String> tag;
    // String for the date
    /**
    * Attribute that stores a string of due date for the section
    */
    private String date;
    // This is the priority of the section
    /**
    * integer representing the priority of a section (Not in use)
    */
    private int priority;
    // This arraylist contains all the child sections of this sections
    /**
    * A list which stores all child sections of this section
    */
    private ArrayList<Section> content;
    // This is the unqiue id of the section so that it can easily be targeted
    /**
    * An id of this section within the whole Outline
    */
    private int id;
    // This will display that the sections is complete in the gui
    /**
    * Boolean for if the section is completed
    */
    private boolean complete;
    // This will tell at which indent level the section is 
    /**
    * Integer for the indent of this section, each child has one level higher than the parent
    */
    private int level;
    // This will tell if the section is selected
    /**
    * True if section is selected, false if not selected
    */
    private boolean selected;
    // This will tell if the container needs to be hidden
    /**
    * Used if the section object is used as a container for other sections
    */
    private boolean hidden;
    // This will store the pointer to the parent object
    @JsonIgnore
    /**
    * Stores the pointer to the parent section used in some methods
    */
    private Section parent;

    
    /**
    * Simple constructor setting all default values
    * @param text Text value to be stored in the section and displayed to the user
    * @param user List of users to be assigned to the Section , can be null
    * @param priority Can show the priority of the section (Unused)
     * @param content List of all child section
     * @param id the global id of this section
     * @param level the indent level of this section
     * @param parent section of this section
    */
    public Section(String text, ArrayList<User> user, int priority, ArrayList<Section> content, int id, int level, Section parent)
    {
        this.text = text;
        this.user = user;
        this.tag = new ArrayList();
        this.priority = priority;
        this.content = content;
        this.id = id;
        this.level = level;
        this.selected = false;
        this.parent = parent;
        this.hidden = false;
    }
    
    /**
    * Simple constructor empty constructor used for JSON
    */
    public Section()
    {
        
    }
    
    /**
    * Simple create section method which creates a top level section and appends
    * it to the end of sections list
    * @param text Text value to be stored in the section and displayed to the user
    * @param user List of users to be assigned to the Section , can be null
    * @param priority Can show the priority of the section (Unused)
     * @param myOutline The outline in which the section is created
    */
    public void createSubSection(String text, ArrayList<User> user, int priority, Outliner myOutline)
    {
       Section newSection = new Section(text, user, priority, new ArrayList(), Outliner.getSectionCount(),this.level+1,this);
       this.content.add(newSection);
       Outliner.setSectionCount(1);
       Outliner.reassignId(myOutline);
       
    }
    
    /**
    * Method to create a new section at the same level as the this section
    * @param myOutline Currently loaded outline object
    * @return Section object which have been created to select it in the GUI
    */
    public Section createParentSection(Outliner myOutline)
    {
        this.parent.createSubSection("", null, Outliner.getSectionCount(),myOutline);
        Section newSection = this.parent.getContent().get(this.parent.getContent().size()-1);
        int thisSectionID = this.parent.getLocalId(this);
        this.parent.setMiddleSection(newSection,thisSectionID+1);
        Outliner.reassignId(myOutline);
        return newSection;
    }
    
    /**
    * Method to create a section and move all children nodes to the parent 
    * @param myOutline Currently loaded outline object
    * @return Section object which have been created to select it in the GUI
    */
    public Section createContainerToMoveTo(Outliner myOutline)
    {
        if (this.parent.getLocalId(this)-1 != -1)
        {
            Section newParent = this.parent.getContent().get(this.parent.getLocalId(this)-1);
            newParent.setMiddleSectionWithoutCreate(this, newParent.getContent().size());
            this.parent.deleteSubSection(this.parent.getLocalId(this));
            this.setParent(newParent);
            Outliner.reassignId(myOutline);
        }
        else
        {
            this.parent.createSubSection("", null, Outliner.getSectionCount(),myOutline);
            Section newSection = this.parent.getContent().get(this.parent.getContent().size()-1);
            int thisSectionID = this.parent.getLocalId(this);
            newSection.setId(this.getId());
            this.parent.setMiddleSection(newSection,thisSectionID+1);
            Outliner.reassignId(myOutline);
            newSection.setHidden(true);
            ArrayList<Section> newList = new ArrayList();
            newList.add(this);
            newSection.setContent(newList);
            this.parent.deleteSubSection(this.parent.getLocalId(this));
            this.setParent(newSection);
            Outliner.reassignId(myOutline);
        }
        return this;
    }
    
    /**
    * Method to move all sections to the parent section, used when section is deleted
     * @param givenSection a section for which to move all children nodes
    * @param myOutline Currently loaded outline object
    * @return Section object which have been created to select it in the GUI
    */
    public Section moveSectionToParent(Section givenSection, Outliner myOutline)
    {
        if (this.parent.isHidden() && this.parent.getContent().size() == 1)
        {
            Outliner.setSectionCount(-1);
            Outliner.deleteAtId(givenSection.getId());
            givenSection.getParent().deleteSubSectionWithMove(givenSection.getParent().getLocalId(givenSection));
            Outliner.reassignId(myOutline);
        }
        else
        {
            this.parent.getParent().setMiddleSectionWithoutCreate(this, this.parent.getParent().getLocalId(this.parent)+1);
            this.parent.deleteSubSection(this.parent.getLocalId(this));
            Outliner.reassignId(myOutline); 
            this.setParent(this.parent.getParent());
        }
        return this;
    }
    
    /**
    * Method to move all sections to the parent section and delete this section
     * @param sectionID the local id of the section to delete
    */
    public void deleteSubSectionWithMove(int sectionID)
    {
         // Before deleting a section at the level, move all children nodes to the parent level
        ArrayList<Section> myNewSections = new ArrayList();
        for (int i = 0; i < sectionID; i++)
        {
            myNewSections.add(this.content.get(i));
        }
        for (int i = 0; i < this.content.get(sectionID).getContent().size();i++)
        {
            myNewSections.add(this.content.get(sectionID).getContent().get(i));
            // make sure that their parent is set to null
            this.content.get(sectionID).getContent().get(i).setParent(this);
        }
        for (int i = sectionID; i < this.content.size(); i++)
        {
            myNewSections.add(this.content.get(i));
        }
        myNewSections.remove(this.content.get(sectionID));
        this.setContent(myNewSections);
    }
    
    /**
    * Method to set a new value to the text attribute
     * @param textValue a new String value
    */
    public void editText(String textValue)
    {
        setText(textValue);
    }
    
    /**
    * Method to add a character at a certain id within the text attribute
     * @param givenChar The character to add to the text
     * @param typeIndex The index at which to add the character
    */
    public void addChar(char givenChar, int typeIndex)
    {
        String myOldText = this.getText();
        String leftText = myOldText.substring(0, typeIndex);
        String rightText = myOldText.substring(typeIndex);
        String myNewText = leftText+givenChar+rightText;
        this.editText(myNewText);
    }
    
    /**
    * Method to mark section as complete or incomplete
     * @param value true or false value for the attribute
    */
    public void markComplete(boolean value)
    {
        this.complete = value;
    }
    
    /**
    * Method to return a true or false for complete
     * @return the boolean for if the section is complete
    */
    public boolean isComplete()
    {
        return this.complete;
    }
    
    /**
    * Method to set section as selected for highliting
    */
    public void markSelected()
    {
        this.selected = true;
    }
    
    /**
    * Method to mark section as hidden or not hidden
     * @param value true or false value for the attribute
    */
    public void setHidden(boolean value)
    {
        this.hidden = value;
    }
    
    /**
    * Method to return a true or false for hidden
     * @return the boolean for if the section is hidden
    */
    public boolean isHidden()
    {
        return this.hidden;
    }
    
    /**
    * Method to return a true or false for selected
     * @return the boolean for if the section is selected
    */
    public boolean isSelected()
    {
        return this.selected;
    }
    
    /**
    * Method to unset section as selected for highliting
    */
    public void unSelect()
    {
        this.selected = false;
    }
    
    /**
    * Method to remove a section from the content of this Section
    * @param sectionID Id of the section to be deleted
    */
    public void deleteSubSection(int sectionID)
    {
        this.content.remove(sectionID);
    }
    
    /**
    * Method to return the pointer to the parent container of this section
    * Can be null if it is a top level section
     * @return pointer to parent Section
    */
    public Section getParent()
    {
        return parent;
    }
    
    /**
    * Method to set a new pointer for the parent section
     * @param section New parent section
    */
    public void setParent(Section section)
    {
        this.parent = section;
    }
    
    /**
    * Method to return the level of this section
     * @return level of this section
    */
    public int getLevel()
    {
        return this.level;
    }
    
    /**
    * Method to set a new level for this section
     * @param level to set
    */
    public void setLevel(int level)
    {
        this.level = level;
    }
    
    /**
    * Method to return the string stored in this section
     * @return String of the text stored
    */
    public String getText() 
    {
        return text;
    }

    /**
    * Method to set new String value of the text
     * @param text String to set
    */
    public void setText(String text) 
    {
        this.text = text;
    }
    
    /**
    * Method to return the date string stored in this section
     * @return String of the date stored
    */
    public String getDate() 
    {
        return date;
    }

    /**
    * Method to set new String value of the date
     * @param date String to set
    */
    public void setDate(String date) 
    {
        this.date = date;
    }

    /**
    * Method to return the list of all users
     * @return ArrayList of all users assigned this section
    */
    public ArrayList<User> getUser() 
    {
        return user;
    }

    /**
    * Method to set the list of all users
     * @param user list to set
    */
    public void setUser(ArrayList<User> user) 
    {
        this.user = user;
    }
    
    /**
    * Method to create a new user and set it as the only user assigned
     * @param name of the user assigned
    */
    public void setUserCreate( String name)
    {
        User myUser = new User();
        myUser.setName(name);
        ArrayList<User> myList = new ArrayList();
        myList.add(myUser);
        
        this.user = myList;
    }

    /**
    * Method to return the list of all tags
     * @return ArrayList of all tags assigned this section
    */
    public ArrayList<String> getTag() 
    {
        return tag;
    }

    /**
    * Method to set the list of all tags
     * @param tag list to set
    */
    public void setTag(ArrayList<String> tag) 
    {
        this.tag = tag;
    }
    
    /**
    * Method to add a single tag to the list of tags
     * @param value String of new tag to add
    */
    public void addTag(String value)
    {
        this.tag.add(value);
    }
    
    /**
    * Method to remove a single tag to the list of tags
     * @param value String of tag to remove
    */
    public void deleteTag(String value)
    {
        if (this.tag.contains(value))
        {
            this.tag.remove(value);
        }
    }

    /**
    * Method to return integer of the priority of this Section
     * @return int of priority
    */
    public int getPriority() 
    {
        return priority;
    }

    /**
    * Method to set new priority value
     * @param priority to set
    */
    public void setPriority(int priority) 
    {
        this.priority = priority;
    }

    /**
    * Method to return the list of all section stored in this Section
     * @return ArrayList of all Sections assigned to this section
    */
    public ArrayList<Section> getContent() 
    {
        return content;
    }

    /**
    * Method to set the list of all section stored in this Section
     * @param content the new list to set
    */
    public void setContent(ArrayList<Section> content) 
    {
        this.content = content;
    }

    /**
    * Method to return the id of this section within the whole Outline
     * @return integer of this section id
    */
    public int getId() 
    {
        return id;
    }
    
    /**
    * Method to return the ID of the given section within Outline.sections list
     * @param givenSection Section which local id is required
     * @return int id of the given section within the outline object
    */
    public int getLocalId(Section givenSection)
    {
        return this.content.indexOf(givenSection);
    }

    /**
    * Method to set the id of this section within the whole Outline
     * @param id which to set for this section
    */
    public void setId(int id) 
    {
        this.id = id;
    }
    
    /**
    * Method to set a section in the top level at index 0 within this object
     * @param id of the section to move to index 0 within this object
    */
    public void setLeadingSection (int id)
    {
        ArrayList newList = new ArrayList();
        newList.add(this.content.get(id));
        for (int i = 0; i < this.content.size();i++)
        {
            if (i != id)
            {
                newList.add(this.content.get(i));
            }
        }
        this.content = newList;
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
            newList.add(this.content.get(i));
        }
        newList.add(givenSection);
        for (int i = newID; i < this.content.size()-1;i++)
        {
            newList.add(this.content.get(i));
        }
        this.content = newList;
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
            newList.add(this.content.get(i));
        }
        newList.add(givenSection);
        for (int i = newID; i < this.content.size();i++)
        {
            newList.add(this.content.get(i));
        }
        this.content = newList;
    }
    
}
