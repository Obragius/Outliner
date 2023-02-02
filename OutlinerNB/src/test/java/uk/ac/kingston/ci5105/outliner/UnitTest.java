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
    
    public void testCreateSection() // Testing the creation of the initial section present in the outliner
    {
        Outliner.createSection("", null, null, 0, null);
        Assert.assertTrue(Outliner.getSections().get(0) instanceof Section);
    }
    
    public void testCreateSubSection() // Testing the creation of sections within a sections
    {
        Section testSection = Outliner.getSections().get(0);
        testSection.createSubSection("",null,null,0,null);
        Assert.assertTrue(testSection.getContent().get(0) instanceof Section);
    }
    
    public void testEditSection() // Testing changing the text in the outliner section
    {
        Section testSection = Outliner.getSections().get(0);
        testSection.setText("TEST PACKAGE MESSAGE");
        Assert.assertEquals("TEST PACKAGE MESSAGE", testSection.getText());
    }
    
}
