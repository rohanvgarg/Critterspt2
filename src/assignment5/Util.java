package assignment5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Util
{

    protected static ObservableList<String> getAllCritterClasses()
    {
        ObservableList<String> allCritters = FXCollections.observableArrayList();

        File workingDir = new File("./src/assignment5");

        for (int i = 0; i < workingDir.list().length; i++)
        {
            Class critterClass = Critter.class;
            try
            {
                String className = workingDir.list()[i];
                String critterName = className.substring(0, className.length() - 5); //have to remove ".java" part
                String fullyQualifiedName = "assignment5." + critterName;
                Class potentialCritter = Class.forName(fullyQualifiedName); //will not produce null as all classes for project are in this directory
                if (critterClass.isAssignableFrom(potentialCritter) && !className.equals("Critter.java"))
                    allCritters.add(critterName);
            }
            //
            catch (Exception e)
            {
            }
        }

        return allCritters;
    }


}
