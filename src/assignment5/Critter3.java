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
 * This critter fights on a 50/50 random probaility.
 * It's direction is based off of its predator-prey attribute.
 * It reproduces with low energy, namely 75 energy points.
 */

//ROHAN's CRITTER
public class Critter3 extends Critter
{
    private int direction;
    private int age;
    private boolean pred;

    /**
     * Constructor sets direction and predator attributes
     */

    public Critter3()
    {
        direction = Critter.getRandomInt(8);
        int middleMan = Critter.getRandomInt(100);

        pred = true;

        if (middleMan > 1)
        {
            pred = false;
        }
    }


    /**
     * Determines if critter would like to fight some opponent
     *
     * @param oponent
     * @return boolean of whether this would like to fight
     */

    //fights with 50/50 probability
    public boolean fight(String oponent)
    {
        int roll = Critter.getRandomInt(2);
        if (roll == 0)
        {
            return true;
        } else
            return false;
    }


    @Override
    /**
     *
     * returns string representation of critter
     */
    public String toString()
    {
        return "3";
    }

    //ROhan can make changes

    /**
     * Executes one time step for this critter, will walk, change direction and reproduce
     */

    public void doTimeStep()
    {
        //walk
        walk(direction);

        //reproduction
        if (getEnergy() > 75)
        {
            Critter3 child = new Critter3();
            child.pred = !pred;     //in this case the apple falls far from the tree
            reproduce(child, getRandomInt(8));
        }
        //if prey pick a random direction;
        if (!pred)
        {
            direction = (getRandomInt(8) + 4 % 8);
        } else
        {
            direction = (getRandomInt(8) - 4 % 8);
        }

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
        int numofPreds = 0;
        int numofPrey = 0;

        for (Critter crit : theseCritters)
        {
            totalNumofCrits++;
            Critter3 crit3 = (Critter3) (crit);

            if (crit3.pred == true)
            {
                numofPreds++;
            } else
                numofPrey++;
        }

        toRet += ("Critter3\n");
		toRet +=("--------\n");
        if (totalNumofCrits > 0)
        {
			toRet +=("total number of Critter3's:		" + totalNumofCrits + "\n");
			toRet +=("total number of Predators:		" + numofPreds + "\n");
			toRet +=("total number of Prey:		" + numofPrey + "\n");
			toRet +=("ratio of predators to prey:	" + (((double) numofPreds / (double) numofPrey)) * 100.0 + "\n");
        } else
        {
			toRet +=("ALL DEAD :( \n");
        }

        return toRet;
    }

    @Override
    public CritterShape viewShape()
    {
        return null;
    }
}
