package assignment5;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Yuan Chang
 * YC23988
 * 15500
 * Rohan Garg
 * rg42255
 * 15500
 * Slip days used: 0
 * Spring 2018
 */

/**
 * This critter fights all critters except those to which it is attracted to.
 * It's direction is based off of which critter it is attracted to.
 * It reproduces with high energy, namely 150 energy points.
 */

import java.util.ArrayList;

//ROHAN's CRITTER

public class Critter4 extends Critter
{
	private int direction;
	private int attractedToCrit;
	private String[] allCrits = {"1", "2", "3", "4", "C", "@"};

	/**
	 * constructor sets direction and which other critter the crit4 is attracted to
	 */
	public Critter4()
	{
		direction = Critter.getRandomInt(8);

		//what critter its attracted to - -never attracted to craig or algae
		attractedToCrit = Critter.getRandomInt(4) + 1;

	}

	/**
	 *
	 * @param oponent
	 * @return boolean of whehter or not this crit wants to fight opponent
	 */
	//attacks all crtiters except those to which its attracted to
	public boolean fight(String oponent)
	{
		if (oponent.equalsIgnoreCase(allCrits[attractedToCrit]))
		{
			return false;
		}
		return true;
	}

	@Override
	/**
	 *
	 * returns string representation of this critter
	 */
	public String toString()
	{
		return "4";
	}

	/**
	 * exectures one timestep for this critter
	 * will walk, might reproduce, and change direction
	 */

	public void doTimeStep()
	{
		//move step
		walk(direction);

		//reproduction
		if (getEnergy() > 150)
		{
			Critter4 child = new Critter4();
			child.attractedToCrit = getRandomInt(5) + 1; //attracted to new kind of critter
			reproduce(child, getRandomInt(8));
		}

		//turn direction based on attraction of critter
		direction = (attractedToCrit * 2) % 8;
	}




	/**
	 * returns appropriate statstics for a list of this type of critter
	 *
	 * @param theseCritters list of critters
	 */
	public static String runStats(java.util.List<Critter> theseCritters)
	{
		String toRet = "";
		int totalNumofCrits = 0;
		int numOfCritsattractedto1 = 0;
		int numOfCritsattractedto2 = 0;
		int numOfCritsattractedto3 = 0;
		int numOfCritsattractedto4 = 0;


		for(Critter crit : theseCritters)
		{
			totalNumofCrits++;
			Critter4 c4 = (Critter4)(crit);


			if(c4.attractedToCrit == 1)
			{
				numOfCritsattractedto1++;
			}
			else if(c4.attractedToCrit == 2)
			{
				numOfCritsattractedto2++;
			}
			else if(c4.attractedToCrit == 3)
			{
				numOfCritsattractedto3++;
			}
			else if(c4.attractedToCrit == 4)
			{
				numOfCritsattractedto4++;
			}



		}

		toRet +=("Critter4\n");
		toRet +=("--------\n");
		if(totalNumofCrits > 0)
        {
            toRet +=("total number of Critter4's:		" + totalNumofCrits + "\n");
            toRet +=("total number of Critter4's attracted to Critter1:		" + numOfCritsattractedto1 + "\n");
            toRet +=("total number of Critter4's attracted to Critter2:		" + numOfCritsattractedto2 + "\n");
            toRet +=("total number of Critter4's attracted to Critter3:		" + numOfCritsattractedto3 + "\n");
            toRet +=("total number of Critter4's attracted to Critter4:		" + numOfCritsattractedto4 + "\n");

        }
        else
        {
            toRet +=("ALL DEAD\n");
        }

        return toRet;
	}

	@Override
	public CritterShape viewShape()
	{
		return null;
	}
}
