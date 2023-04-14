/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uk.ac.kingston.ci5105.outliner.Model;

/**
 *
 * @author lolki
 */
public class User {
    
    private String name;
    private int[] tasks;
    
    public User()
    {
        
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public int[] getTasks() 
    {
        return tasks;
    }

    public void setTasks(int[] tasks) 
    {
        this.tasks = tasks;
    }
    
    
    
}
