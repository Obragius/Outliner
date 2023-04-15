/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uk.ac.kingston.ci5105.outliner.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import uk.ac.kingston.ci5105.outliner.Controller.Outliner;

/**
 * This is a Controller Class which is used to control the application as well 
 * as being instanciated to provide the top level container for the section
 * items
 * <p> This class statically stores currently loaded 
 * @author k1801606
 */
public class Section {
    
    // This holds the text value of the section, the text that is being diplayed
    private String text;
    // This is the user object which is related to this section
    private ArrayList<User> user;
    // This is array of tags which are associated with this section
    private ArrayList<String> tag;
    // String for the date
    private String date;
    // This is the priority of the section
    private int priority;
    // This arraylist contains all the child sections of this sections
    private ArrayList<Section> content;
    // This is the unqiue id of the section so that it can easily be targeted
    private int id;
    // This will display that the sections is complete in the gui
    private boolean complete;
    // This will tell at which indent level the section is 
    private int level;
    // This will tell if the section is selected
    private boolean selected;
    // This will tell if the container needs to be hidden
    private boolean hidden;
    // This will store the pointer to the parent object
    @JsonIgnore
    private Section parent;

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
    
    public Section()
    {
        
    }
    
    public void createSubSection(String text, ArrayList<User> user, int priority, Outliner myOutline)
    {
       Section newSection = new Section(text, user, priority, new ArrayList(), Outliner.getSectionCount(),this.level+1,this);
       this.content.add(newSection);
       Outliner.setSectionCount(1);
       Outliner.reassignId(myOutline);
       
    }
    
    public Section createParentSection(Outliner myOutline)
    {
        this.parent.createSubSection("", null, Outliner.getSectionCount(),myOutline);
        Section newSection = this.parent.getContent().get(this.parent.getContent().size()-1);
        int thisSectionID = this.parent.getLocalId(this);
        this.parent.setMiddleSection(newSection,thisSectionID+1);
        Outliner.reassignId(myOutline);
        return newSection;
    }
    
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
    
    public void editText(String textValue)
    {
        setText(textValue);
    }
    
    public void addChar(char givenChar, int typeIndex)
    {
        String myOldText = this.getText();
        String leftText = myOldText.substring(0, typeIndex);
        String rightText = myOldText.substring(typeIndex);
        String myNewText = leftText+givenChar+rightText;
        this.editText(myNewText);
    }
    
    public void markComplete(boolean value)
    {
        this.complete = value;
    }
    
    public boolean isComplete()
    {
        return this.complete;
    }
    
    public void markSelected()
    {
        this.selected = true;
    }
    
    public void setHidden(boolean value)
    {
        this.hidden = value;
    }
    
    public boolean isHidden()
    {
        return this.hidden;
    }
    
    public boolean isSelected()
    {
        return this.selected;
    }
    
    public void unSelect()
    {
        this.selected = false;
    }
    
    public void deleteSubSection(int sectionID)
    {
        this.content.remove(sectionID);
    }
    
    public Section getParent()
    {
        return parent;
    }
    
    public void setParent(Section section)
    {
        this.parent = section;
    }
    
    public int getLevel()
    {
        return this.level;
    }
    
    public void setLevel(int level)
    {
        this.level = level;
    }
    

    public String getText() 
    {
        return text;
    }

    public void setText(String text) 
    {
        this.text = text;
    }
    
    public String getDate() 
    {
        return date;
    }

    public void setDate(String date) 
    {
        this.date = date;
    }

    public ArrayList<User> getUser() 
    {
        return user;
    }

    public void setUser(ArrayList<User> user) 
    {
        this.user = user;
    }
    
    public void setUserCreate( String name)
    {
        User myUser = new User();
        myUser.setName(name);
        ArrayList<User> myList = new ArrayList();
        myList.add(myUser);
        
        this.user = myList;
    }

    public ArrayList<String> getTag() 
    {
        return tag;
    }

    public void setTag(ArrayList<String> tag) 
    {
        this.tag = tag;
    }
    
    public void addTag(String value)
    {
        this.tag.add(value);
    }
    
    public void deleteTag(String value)
    {
        if (this.tag.contains(value))
        {
            this.tag.remove(value);
        }
    }

    public int getPriority() 
    {
        return priority;
    }

    public void setPriority(int priority) 
    {
        this.priority = priority;
    }

    public ArrayList<Section> getContent() 
    {
        return content;
    }

    public void setContent(ArrayList<Section> content) 
    {
        this.content = content;
    }

    public int getId() 
    {
        return id;
    }
    
    public int getLocalId(Section givenSection)
    {
        return this.content.indexOf(givenSection);
    }

    public void setId(int id) 
    {
        this.id = id;
    }
    
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
