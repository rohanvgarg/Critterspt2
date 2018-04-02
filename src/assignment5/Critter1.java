package assignment5;


/**
This Critter always fights algae, and runs on default rather than fights.
It reproduces on years that are multiples of 4, else it walks.

 **/

public class Critter1 extends Critter
{
    private int direction;
    private int age = 0;
    private int offspring = 0;

    /**
     * Constructor sets direction
     */
    public Critter1()
    {
        direction = Critter.getRandomInt(4) * 2;
    }

    /**
     * Determines if critter would like to fight some opponent
     *
     * @param opponent
     * @return boolean of whether this would like to fight
     */
    public boolean fight(String opponent)
    {
        if (opponent.equalsIgnoreCase("Algae"))
        {
            return true;
        }

        if (getEnergy() < 100)
        {
            run(direction);
            return false;
        }
        return true;
    }

    /**
     * Overrides proper toString
     *
     * @return 1 char string
     */
    @Override
    public String toString()
    {
        return "1";
    }

    /**
     * Does one step of time, could reproduce or walk, updates instance variables
     */
    public void doTimeStep()
    {
        age++;
        if (age % 4 == 0 && getEnergy() > 50)
        {
            Critter1 baby = new Critter1();
            reproduce(baby, direction);
            direction = getRandomInt(8);
            offspring++;
        } else
        {
            walk(direction);
            direction = getRandomInt((direction + 1) % 9);
        }
    }

    /**
     * returns appropriate statistics for a list of this type of critter
     *
     * @param theseCritters list of critters
     */
    public static String runStats(java.util.List<Critter> theseCritters)
    {
        int highAge = 0;
        int totalAge = 0;

        int mostKids = 0;
        int totalKids = 0;

        int highEnergy = 0;
        int totalEnergy = 0;
        int n = 0;

        for (Critter c : theseCritters)
        {
            n += 1;
            Critter1 c1 = (Critter1) (c);

            if (c1.age > highAge)
            {
                highAge = c1.age;
            }
            totalAge += c1.age;

            if (c1.offspring > mostKids)
            {
                mostKids = c1.offspring;
            }
            totalKids += c1.offspring;

            if(c1.getEnergy() > highEnergy)
            {
                highEnergy = c1.getEnergy();
            }
            totalEnergy += c1.getEnergy();
        }

        System.out.println("Critter1");
        System.out.println("--------");
        if(n > 0)
        {
            System.out.println("oldest:		" + highAge);
            System.out.println("avg age:	" + (double) (totalAge / n));
            System.out.println("best parent:" + mostKids + " kids");
            System.out.println("avg kids:	" + ((double) totalKids / (double) n) + " kids");
            System.out.println("high energy: " + highEnergy);
            System.out.println("avg energy: " + ((double) totalEnergy / (double) n));
        }
        else
        {
            System.out.println("ALL DEAD :(");
        }
    }

    @Override
    public CritterShape viewShape()
    {
        return null;
    }
}
