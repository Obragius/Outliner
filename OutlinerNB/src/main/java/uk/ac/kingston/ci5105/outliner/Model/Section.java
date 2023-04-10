/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uk.ac.kingston.ci5105.outliner.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import uk.ac.kingston.ci5105.outliner.Controller.Outliner;

/**
 *
 * @author lolki
 */
public class Section {
    
    // This holds the text value of the section, the text that is being diplayed
    private String text;
    // This is the user object which is related to this section
    private User[] user;
    // This is array of tags which are associated with this section
    private String[] tag;
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
    // This will store the pointer to the parent object
    @JsonIgnore
    private Section parent;

    public Section(String text, User[] user, String[] tag, int priority, ArrayList<Section> content, int id, int level, Section parent)
    {
        this.text = text;
        this.user = user;
        this.tag = tag;
        this.priority = priority;
        this.content = content;
        this.id = id;
        this.level = level;
        this.selected = false;
        this.parent = parent;
    }
    
    public void createSubSection(String text, User[] user, String[] tag, int priority)
    {
       Section newSection = new Section(text, user, tag, priority, new ArrayList(), Outliner.getSectionCount(),this.level+1,this);
       this.content.add(newSection);
       Outliner.setSectionCount(1);
       Outliner.addSection(newSection);
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

    public User[] getUser() 
    {
        return user;
    }

    public void setUser(User[] user) 
    {
        this.user = user;
    }

    public String[] getTag() 
    {
        return tag;
    }

    public void setTag(String[] tag) 
    {
        this.tag = tag;
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
    
}
