package assignment5;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Util
{
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
