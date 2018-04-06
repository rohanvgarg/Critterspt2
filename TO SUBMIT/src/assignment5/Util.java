/* CRITTERS Utils Util.java
 * EE422C Project 5 submission by
 * Yuan Chang
 * YC23988
 * 15500
 * Rohan Garg
 * RG42255
 * 15500
 * Slip days used: <0>
 * Spring 2018
 */

package assignment5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;


public class Util
{
    /**
     * Written by Andy
     * @return list of all Critter types
     */
    protected static ObservableList<String> getAllCritterClasses()
    {
        ObservableList<String> toRet = FXCollections.observableArrayList();
        Class critterClass = Critter.class;
        File dir = new File("./src/assignment5");

        for(int i = 0; i < dir.listFiles().length; i++)
        {
            try
            {
                File curClass = dir.listFiles()[i];

                Class testClass = Class.forName("assignment5." + curClass.getName().substring(0, curClass.getName().length()-5));

                if(critterClass.isAssignableFrom(testClass))
                {
                    toRet.add(curClass.getName().substring(0, curClass.getName().length()-5));
                }
            }
            catch (Exception e)
            {
            }
        }
        toRet.removeAll("Critter");

        return toRet;
    }


}
