/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uk.ac.kingston.ci5105.outliner.Model;

/**
 * This is a Model Class which is used to represent user in the outline
 * @author k1801606
 */
public class User {
    
    /**
    * Text which is stored within a user and treated as the name of the user
    */
    private String name;
    /**
    * Should store the id of all section the user is assigned to (Not in use)
    */
    private int[] tasks;
    
    /**
    * Empty constructor
    */
    public User()
    {
        
    }

    /**
    * Getter for the name
    * @return String of the name
    */
    public String getName() 
    {
        return name;
    }

    /**
    * Setter for the name
     * @param name to set
    */
    public void setName(String name) 
    {
        this.name = name;
    }

    /**
    * Getter for the tasks
    * @return Array of integers
    */
    public int[] getTasks() 
    {
        return tasks;
    }

    /**
    * Setter for the tasks
     * @param tasks array to set
    */
    public void setTasks(int[] tasks) 
    {
        this.tasks = tasks;
    }
    
    
    
}
