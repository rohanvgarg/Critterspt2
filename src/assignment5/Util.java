package assignment5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Util
{

    protected static double gridLineWidth = 10;
    protected static double screenHeight = Math.max(100, Math.min(Params.world_height * 25, 1200));
    protected static double screenWidth = Math.max(100, Math.min(Params.world_width * 25, 1200));
    protected static double widthBetween = ((screenWidth - (gridLineWidth * 1.0)) / (Params.world_width));
    protected static double heightBetween = ((screenHeight - (gridLineWidth * 1.0)) / (Params.world_height));
    protected static double spaceBetween = Math.min(widthBetween, heightBetween);


    //Shape final constants to draw normalized to 1 by 1 square
    protected final static double[][] triangle = {{0.50, 0.06}, {0.06, 0.78}, {0.94, 0.78}};
    protected final static double[][] square = {{0.12, 0.12}, {0.12, 0.88}, {0.88, 0.88}, {0.88, 0.12}};
    protected final static double[][] diamond = {{0.50, 0.06}, {0.25, 0.50}, {0.50, 0.94}, {0.75, 0.50}};
    protected final static double[][] star = {{0.20, 0.95}, {0.50, 0.75}, {0.80, 0.95}, {0.68, 0.60}, {0.95, 0.40}, {0.62, 0.38}, {0.50, 0.05}, {0.38, 0.38}, {0.05, 0.40}, {0.32, 0.64}};


    public static String myPackage = assignment5.Critter.class.getPackage().toString().split(" ")[1];

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
                if (critterClass.isAssignableFrom(potentialCritter) && !className.equals("assignment5/Critter.java"))
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
