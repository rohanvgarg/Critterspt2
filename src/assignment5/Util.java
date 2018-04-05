package assignment5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Util
{


    /**
     * Written by Andy
     * @return
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
