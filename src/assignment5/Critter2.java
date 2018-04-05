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

//ANDY's CRITTER

import javafx.scene.paint.Color;

/**
 This Critter always fights algae, and fights 80% of the time otherwise.

 It reproduces at random and also walks or runs at random.


 **/
public class Critter2 extends Critter
{
    private int direction;
    private int age = 0;
    private double fightRate = 0;
    private int fights = 0;
    private int n = 0;

    /**
     * Constructor just sets direction to only odds
     */
    public Critter2()
    {
        direction = getRandomInt(8) % 2 + 1;
    }

    /**
     * Determines if this critter would like to fight some opponent type of critter
     *
     * @param opponent type
     * @return boolean yes no
     */
    public boolean fight(String opponent)
    {
        n++;

        int chanceOfLook = Critter.getRandomInt(2);
        if(chanceOfLook ==1)
        {
            int randomDir = Critter.getRandomInt(8);
            this.look(randomDir, true);
        }

        if (opponent.equalsIgnoreCase("Algae") || getRandomInt(100) > 20)
        {
            fights++;
            fightRate = (double) fights / (double) n;
            return true;
        }

        return false;
    }

    /**
     * @return 1 char string override of Critter toString
     */
    @Override
    public String toString()
    {
        return "2";
    }

    /**
     * Decides to reproduce or walk or run, updates instance variables
     */
    public void doTimeStep()
    {
        age++;
        direction = getEnergy() % 8;

        if (getEnergy() > 0)
        {
            if (getRandomInt(getEnergy() + 1) * 2 > 150)
            {
                Critter2 baby = new Critter2();
                reproduce(baby, (direction + getRandomInt(8) % 8 + 1));
            }
        }

        if (getRandomInt(2) % 2 == 1)
        {
            if (getRandomInt(2) % 2 == 1)
            {
                walk(direction);
            } else
            {
                run(direction);
            }
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
        int highAge = 0;
        int totalAge = 0;

        int mostFights = 0;
        double totalFightRate = 0;
        int n = 0;

        int highEnergy = 0;
        int totalEnergy = 0;

        for (Critter c : theseCritters)
        {
            n += 1;
            Critter2 c1 = (Critter2) (c);

            if (c1.age > highAge)
            {
                highAge = c1.age;
            }
            totalAge += c1.age;

            if (c1.fights > mostFights)
            {
                mostFights = c1.fights;
            }

            if(c1.getEnergy() > highEnergy)
            {
                highEnergy = c1.getEnergy();
            }
            totalEnergy += c1.getEnergy();


            totalFightRate += c1.fightRate;
        }

        toRet += ("Critter2\n");
        toRet +=("--------\n");
        if(n>0)
        {
            toRet +=("oldest:		" + highAge +"\n");
            toRet +=("avg age:	" + (double) (totalAge / n + '\n'));
            toRet +=("most fights:" + mostFights + " fights" + '\n');
            toRet +=("high fightrate:	" + (totalFightRate / (double) n) + '\n');

            toRet +=("high energy: " + highEnergy + '\n');
            toRet +=("avg energy: " + ((double) totalEnergy / (double) n) + '\n');
        }
        else
        {
            toRet +=("ALL DEAD :(" + '\n');
        }

        return toRet;
    }

    @Override
    public CritterShape viewShape()
    {
        return CritterShape.SQUARE;
    }

    public javafx.scene.paint.Color viewOutlineColor()
    {
        return Color.RED;
    }
}
