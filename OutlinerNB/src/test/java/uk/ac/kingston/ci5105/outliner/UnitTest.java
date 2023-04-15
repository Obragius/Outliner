/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uk.ac.kingston.ci5105.outliner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import junit.framework.TestCase;
import junit.framework.Assert;
import uk.ac.kingston.ci5105.outliner.Controller.Outliner;
import uk.ac.kingston.ci5105.outliner.Model.*;

/**
 *
 * @author k1801606
 */
public class UnitTest extends TestCase{
    
    public static Outliner createTopLevelSection() // Some code to create the section of the outliner
    {
        Outliner myOutline = new Outliner();
        myOutline.createSection("", null, 0);
        return myOutline;
    }
    
    public void testCreateSection() // Testing the creation of the initial section present in the outliner [R3]
    {
        Outliner myOutline = createTopLevelSection();
        Assert.assertTrue(myOutline.getSections().get(0) instanceof Section);
    }
    
    public void testCreateSubSection() // Testing the creation of sections within a sections [R4]
    {
        Outliner myOutline = createTopLevelSection();
        Section testSection = myOutline.getSections().get(0);
        testSection.createSubSection("",null,0,myOutline);
        Assert.assertTrue(testSection.getContent().get(0) instanceof Section);
    }
    
    public void testEditSection() // Testing changing the text in the outliner section [R8]
    {
        Outliner myOutline = createTopLevelSection();
        Section testSection = myOutline.getSections().get(0);
        testSection.editText("TEST PACKAGE MESSAGE");
        Assert.assertEquals("TEST PACKAGE MESSAGE", testSection.getText());
    }
    
    public void testDeleteSection() // Testing that sections can be deleted [R9]
    {
        Outliner myOutline = createTopLevelSection();
        Section testSection = myOutline.getSections().get(0);
        myOutline.deleteSection(myOutline.getLocalId(testSection));
        Assert.assertFalse(myOutline.getSections().contains(testSection));
    }
    
    public void testMarkSectionComplete() // Testing that user can mark a section to be complete [R10]
    {
        Outliner myOutline = createTopLevelSection();
        Section testSection = myOutline.getSections().get(0);
        testSection.markComplete(true);
        Assert.assertTrue(testSection.isComplete());
        
        testSection.markComplete(false);
        Assert.assertFalse(testSection.isComplete());
    } 
    
    public void testEditSubsection() // Testing that the user can change the text within a subsection [R11]
    {
        Outliner myOutline = createTopLevelSection();
        Section testSection = myOutline.getSections().get(0);
        testSection.createSubSection("",null,0,myOutline);
        Section testSubSection = testSection.getContent().get(0);
        testSubSection.editText("TEST PACKAGE MESSAGE");
        Assert.assertEquals("TEST PACKAGE MESSAGE", testSubSection.getText());
    }
    
    public void testDeleteSubSection() // Testing that the user can delete a section within a section [R12]
    {
        Outliner myOutline = createTopLevelSection();
        Section testSection = myOutline.getSections().get(0);
        testSection.createSubSection("",null,0,myOutline);
        Section testSubSection = testSection.getContent().get(0);
        testSection.deleteSubSection(testSection.getLocalId(testSubSection));
        Assert.assertFalse(testSection.getContent().contains(testSubSection));
        
    }
    
    public void testSaveToFile() throws IOException, JsonProcessingException, URISyntaxException // Testing that the user can save their progress as a file [R13]
    {
        // will return true if the file has been saved sucesfully
        Outliner myOutline = createTopLevelSection();
        myOutline.setName("TEST_OUTLINE");
        Outliner.saveToJSON(myOutline);
        String parentDir = Outliner.class.getProtectionDomain().getCodeSource().getLocation()
    .toURI().getPath();
        String fullPath = parentDir+File.separator+".."+File.separator+myOutline.getName()+".json";
        Assert.assertTrue(new File(fullPath).isFile());
    }
    
    public void testLoadFromFile() throws URISyntaxException, IOException // Testing that the user is able to load saved files and interact with them [R14]
    {
        // will return true if the file has been loaded sucesfully
        Outliner myOutline = createTopLevelSection();
        myOutline.setName("TEST_OUTLINE");
        Outliner.saveToJSON(myOutline);
        String parentDir = Outliner.class.getProtectionDomain().getCodeSource().getLocation()
    .toURI().getPath();
        ObjectMapper myMapper = new ObjectMapper();
        List<Outliner> listOutliner = myMapper.readValue(new File(parentDir+File.separator+".."+File.separator+myOutline.getName()+".json"),new TypeReference<List<Outliner>>(){});
        Outliner myJson = listOutliner.get(0);
        String newJson = myMapper.writeValueAsString(myJson);
        String oldJson = myMapper.writeValueAsString(myOutline);
        Assert.assertEquals(newJson, oldJson);
    }
    
    public void testSearch() // Testing that the user can search text or tag or user assigned tasks [R15]
    {
        // The function will need to be able to return the paths of sections which contain the information searched for
        // Search path will be a ArrayList<Integer> starting from the top level section to the section containing the text or a tag or a user
        // Example {0,2,5} is for Section in Outliner.getSections.get(0).getContent().get(2).getContent().get(5)
        
        
        // TODO - Implement when creating subsections is working
        Assert.assertTrue(true);
    }
    
    public void testAddTag() // Testing that the user can add a tag to a section or a subsection [R16]
    {
        //TODO - Implement
        Assert.assertTrue(true);
    }
    
    public void testEditTag() // Testing taht the user can change the tag on a section or subsection [R17]
    {
        //TODO - Implement
        Assert.assertTrue(true);
    }
    
    public void testSortSections() // Allow the user to create a view object which will sort the sections [R18]
    {
        //TODO - Implement
        Assert.assertTrue(true);
    }
    
    public void testReorderSections() // Allow the user to reorder sections [R19]
    {
        //TODO - Implement
        Assert.assertTrue(true);
    }
    
    
    
}
