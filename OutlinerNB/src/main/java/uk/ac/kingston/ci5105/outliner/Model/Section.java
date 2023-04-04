/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uk.ac.kingston.ci5105.outliner.Model;
import java.util.ArrayList;
import uk.ac.kingston.ci5105.outliner.Controller.Outliner;

/**
 *
 * @author lolki
 */
public class Section {
    
    private String text;
    private User[] user;
    private String[] tag;
    private int priority;
    private ArrayList<Section> content;
    private int id;
    private boolean complete;
    private int level;
    private boolean selected;

    public Section(String text, User[] user, String[] tag, int priority, ArrayList<Section> content, int id, int level)
    {
        this.text = text;
        this.user = user;
        this.tag = tag;
        this.priority = priority;
        this.content = content;
        this.id = id;
        this.level = level;
        this.selected = false;
    }
    
    public void createSubSection(String text, User[] user, String[] tag, int priority)
    {
       Section newSection = new Section(text, user, tag, priority, new ArrayList(), Outliner.getSectionCount(),this.level+1);
       this.content.add(newSection);
       Outliner.setSectionCount();
       Outliner.addSection(newSection);
    }
    
    public void editText(String textValue)
    {
        setText(textValue);
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

    public void setId(int id) 
    {
        this.id = id;
    }
    
}
