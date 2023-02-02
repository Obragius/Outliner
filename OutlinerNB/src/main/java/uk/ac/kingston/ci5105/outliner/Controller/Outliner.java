/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uk.ac.kingston.ci5105.outliner.Controller;
import uk.ac.kingston.ci5105.outliner.Model.*;
import java.util.ArrayList;


/**
 *
 * @author lolki
 */
public class Outliner {
    
    private static String name;
    private static String date;
    private static ArrayList<Section> sections = new ArrayList();
    private static int sectionCount = 0;
    
    
    public static void main(String[] args)
    {
        Outliner.onStartUp();
    }
    
    public static void onStartUp()
    {
        // Create main parent section and make it empty
        Outliner.createSection("",null,null,0,null);
    }
    
    public static void createSection(String text,User[] user, String[] tag, int priority, ArrayList<Section> content)
    {
        // Create a section using the provided parameters
        // and give it a unqiue runtime id
        Outliner.sections.add(new Section(text, user, tag, priority, content, Outliner.sectionCount));
    }

    public static String getName() 
    {
        return name;
    }

    public static void setName(String name) 
    {
        Outliner.name = name;
    }

    public static String getDate() 
    {
        return date;
    }

    public static void setDate(String date) 
    {
        Outliner.date = date;
    }

    public static ArrayList<Section> getSections() 
    {
        return sections;
    }

    public static void setSections(ArrayList<Section> sections) 
    {
        Outliner.sections = sections;
    }
    
    
    
}
