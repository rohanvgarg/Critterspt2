package assignment5;

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

	protected static List<String> getAllCritterClasses()
	{
		File f = new File(".");

		List<File> cTypes = new ArrayList<File>();

		getAllClassFiles(cTypes, ".");

		ArrayList<String> cNames = new ArrayList<String>();
		for (int i = 0; i < cTypes.size(); i++)
		{
			try
			{
				String className = cTypes.get(i).getName().replace(".class", "");
				Class<?> testingClass = Class.forName(myPackage + "." + className);
				if (testingClass.newInstance() instanceof assignment5.Critter)
				{
					cNames.add(className);
				}
			} catch (Exception e)
			{
			}
		}
		return cNames;
	}

	//called recursively to get all .class files
	protected static void getAllClassFiles(List<File> f, String path)
	{
		File critter = new File(path);
		//get all the files from a directory
		File[] filesList = critter.listFiles();
		for (File file : filesList)
		{
			if (file.isDirectory())
			{
				getAllClassFiles(f, file.getAbsolutePath());
			}
			if (file.isFile() && file.getName().endsWith(".class"))
			{
				f.add(file);
			}
		}
	}



}
