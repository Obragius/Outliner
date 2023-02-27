/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uk.ac.kingston.ci5105.outliner;

import junit.framework.TestCase;
import junit.framework.Assert;
import uk.ac.kingston.ci5105.outliner.Controller.Outliner;
import uk.ac.kingston.ci5105.outliner.Model.*;

/**
 *
 * @author k1801606
 */
public class UnitTest extends TestCase{
    
    public static void createTopLevelSection() // Some code to create the section of the outliner
    {
        Outliner.createSection("", null, null, 0);
    }
    
    public void testCreateSection() // Testing the creation of the initial section present in the outliner [R3]
    {
        createTopLevelSection();
        Assert.assertTrue(Outliner.getSections().get(0) instanceof Section);
    }
    
    public void testCreateSubSection() // Testing the creation of sections within a sections [R4]
    {
        createTopLevelSection();
        Section testSection = Outliner.getSections().get(0);
        testSection.createSubSection("",null,null,0);
        Assert.assertTrue(testSection.getContent().get(0) instanceof Section);
    }
    
    public void testEditSection() // Testing changing the text in the outliner section [R8]
    {
        createTopLevelSection();
        Section testSection = Outliner.getSections().get(0);
        testSection.editText("TEST PACKAGE MESSAGE");
        Assert.assertEquals("TEST PACKAGE MESSAGE", testSection.getText());
    }
    
    public void testDeleteSection() // Testing that sections can be deleted [R9]
    {
        createTopLevelSection();
        Section testSection = Outliner.getSections().get(0);
        Outliner.deleteSection(testSection.getId());
        Assert.assertFalse(Outliner.getSections().contains(testSection));
    }
    
    public void testMarkSectionComplete() // Testing that user can mark a section to be complete [R10]
    {
        createTopLevelSection();
        Section testSection = Outliner.getSections().get(0);
        testSection.markComplete(true);
        Assert.assertTrue(testSection.isComplete());
        
        testSection.markComplete(false);
        Assert.assertFalse(testSection.isComplete());
    } 
    
    public void testEditSubsection() // Testing that the user can change the text within a subsection [R11]
    {
        createTopLevelSection();
        Section testSection = Outliner.getSections().get(0);
        testSection.createSubSection("",null,null,0);
        Section testSubSection = testSection.getContent().get(0);
        testSubSection.editText("TEST PACKAGE MESSAGE");
        Assert.assertEquals("TEST PACKAGE MESSAGE", testSubSection.getText());
    }
    
    public void testDeleteSubSection() // Testing that the user can delete a section within a section [R12]
    {
        createTopLevelSection();
        Section testSection = Outliner.getSections().get(0);
        testSection.createSubSection("",null,null,0);
        Section testSubSection = testSection.getContent().get(0);
        testSection.deleteSubSection(testSubSection.getId());
        Assert.assertFalse(testSection.getContent().contains(testSubSection));
        
    }
    
    public void testSaveToFile() // Testing that the user can save their progress as a file [R13]
    {
        // will return true if the file has been saved sucesfully
        Assert.assertTrue(Outliner.saveToFile("TEST_SAVE"));
    }
    
    public void testLoadFromFile() // Testing that the user is able to load saved files and interact with them [R14]
    {
        // will return true if the file has been loaded sucesfully
        Assert.assertTrue(Outliner.loadFromFile("TEST_SAVE"));
    }
    
    public void testSearch() // Testing that the user can search text or tag or user assigned tasks [R15]
    {
        // The function will need to be able to return the paths of sections which contain the information searched for
        // Search path will be a ArrayList<Integer> starting from the top level section to the section containing the text or a tag or a user
        // Example {0,2,5} is for Section in Outliner.getSections.get(0).getContent().get(2).getContent().get(5)
        
        
        // TODO - Implement when creating subsections is working
        Assert.assertTrue(false);
    }
    
    public void testAddTag() // Testing that the user can add a tag to a section or a subsection [R16]
    {
        //TODO - Implement
        Assert.assertTrue(false);
    }
    
    public void testEditTag() // Testing taht the user can change the tag on a section or subsection [R17]
    {
        //TODO - Implement
        Assert.assertTrue(false);
    }
    
    public void testSortSections() // Allow the user to create a view object which will sort the sections [R18]
    {
        //TODO - Implement
        Assert.assertTrue(false);
    }
    
    public void testReorderSections() // Allow the user to reorder sections [R19]
    {
        //TODO - Implement
        Assert.assertTrue(false);
    }
    
    
    
}
