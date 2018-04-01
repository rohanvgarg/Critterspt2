package assignment5;

import javax.print.DocFlavor;
import java.util.Iterator;
import java.util.List;

public abstract class Critter
{
	private static String myPackage;
	private static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	private static java.util.Random rand = new java.util.Random();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static
	{
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	private int energy = 0;
	private int x_coord;
	private int y_coord;
	private boolean canMoveThisTurn;

	public static int getRandomInt(int max)
	{
		return rand.nextInt(max);
	}

	public static void setSeed(long new_seed)
	{
		rand = new java.util.Random(new_seed);
	}

	/* rest is unchanged from Project 4 */

	private boolean sameLocation(Critter crit2)
	{
		if (x_coord == crit2.x_coord && y_coord == crit2.y_coord)
		{
			return true;
		}
		return false;
	}

	public static void worldTimeStep()
	{
		for (Critter crit : population)
		{
			crit.canMoveThisTurn = true;
			crit.doTimeStep();
		}

		for (int i = 0; i < population.size(); i++)
		{
			for (int j = 0; j < population.size(); j++)
			{
				if (i != j)
				{
					Critter critter1 = population.get(i);
					Critter critter2 = population.get(j);

					//encounter to resolve
					if (critter1.sameLocation(critter2))
					{
						boolean crit1wants = true;
						boolean crit2wants = true;

						if (critter1.canMoveThisTurn)
						{
							crit1wants = critter1.fight(critter2.toString());
						}

						if (critter2.canMoveThisTurn)
						{
							crit2wants = critter2.fight(critter1.toString());
						}
						if ((critter1.energy >= 0) && (critter2.energy >= 0))
						{
							if (critter1.sameLocation(critter2))
							{
								if (critter1.energy <= 0)
								{
									break;
								}
								if (critter2.energy <= 0)
								{
									break;
								}
								int rollCrit1 = (getRandomInt(critter1.energy)) * ((crit1wants) ? 1 : 0);
								int rollCrit2 = (getRandomInt(critter2.energy)) * ((crit2wants) ? 1 : 0);

								if (rollCrit1 >= rollCrit2)
								{
									critter1.energy += (critter2.energy / 2);
									critter2.energy = 0;
								} else
								{
									critter2.energy += (critter1.energy / 2);
									critter1.energy = 0;
								}
							}
						}
					}
				}
			}
		}

		//deduct energy
		for (Critter crit : population)
		{
			crit.energy -= Params.rest_energy_cost;
		}

		//generate algae
		for (int i = 0; i < Params.refresh_algae_count; i++)
		{
			try
			{
				makeCritter("Algae");
			} catch (InvalidCritterException e)
			{
				e.printStackTrace();
			}
		}

		//add babies
		for (Critter babe : babies)
		{
			population.add(babe);
		}
		babies.clear();

		//remove dead critters
		for (Iterator<Critter> it = population.iterator(); it.hasNext(); )
		{
			Critter crit = it.next();

			if (crit.energy <= 0)
			{
				it.remove();
			}
		}
	}

	public static void displayWorld(Object pane)
	{
	}

	/* create and initialize a Critter subclass
	 * critter_class_name must be the name of a concrete subclass of Critter, if not
	 * an InvalidCritterException must be thrown
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException
	{
	}

	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException
	{
		return null;
	}

	public static void runStats(List<Critter> critters)
	{
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld()
	{
	}

	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background
	 *
	 * critters must override at least one of the following three methods, it is not
	 * proper for critters to remain invisible in the view
	 *
	 * If a critter only overrides the outline color, then it will look like a non-filled
	 * shape, at least, that's the intent. You can edit these default methods however you
	 * need to, but please preserve that intent as you implement them.
	 */
	public javafx.scene.paint.Color viewColor()
	{
		return javafx.scene.paint.Color.WHITE;
	}

	public javafx.scene.paint.Color viewOutlineColor()
	{
		return viewColor();
	}

	public javafx.scene.paint.Color viewFillColor()
	{
		return viewColor();
	}

	public abstract CritterShape viewShape();

	protected final String look(int direction, boolean steps)
	{
		int xLook = this.x_coord;
		int yLook = this.y_coord;
		int numsteps = 0;

		if (steps == false)
		{
			numsteps = 1;
		} else
		{
			numsteps = 2;
		}

		switch (direction)
		{
			case 0:
				xLook += numsteps;
				break;
			case 1:
				yLook -= numsteps;
				xLook += numsteps;
				break;
			case 2:
				yLook -= numsteps;
				break;
			case 3:
				yLook -= numsteps;
				xLook -= numsteps;
				break;
			case 4:
				xLook -= numsteps;
				break;
			case 5:
				yLook += numsteps;
				xLook -= numsteps;
				break;
			case 6:
				yLook += numsteps;
				break;
			case 7:
				yLook += numsteps;
				xLook += numsteps;
				break;
			default:
				break;
		}

		xLook = xLook %Params.world_width;	// keeps critter on the board
		yLook = yLook %Params.world_height;

		String toRet = occupied(xLook, yLook);

		energy-=Params.look_energy_cost;

		//TODO IMPLEMENT MOVING FUNCTIONALITY

		return toRet;
	}

	private String occupied(int xLook, int yLook)
	{
		for(Critter c: population)
		{
			if(c.x_coord == xLook && c.y_coord == yLook)
			{
				return c.toString();
			}
		}
		return null;
	}

	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString()
	{
		return "";
	}

	protected int getEnergy()
	{
		return energy;
	}

	protected final void walk(int direction)
	{
		move(direction, 1);
		energy -= Params.walk_energy_cost;

	}

	protected final void run(int direction)
	{
		move(direction, 2);
		energy -= Params.run_energy_cost;
	}

	private void move(int dir, int dist)
	{
		canMoveThisTurn = false;
		//The 0 direction is straight right (increasing x, no change in y). The 1 direction
		//is diagonally up and to the right (y will decrease in value, x will increase).
		switch (dir)
		{
			case 0:
				x_coord += dist;
				break;
			case 1:
				y_coord -= dist;
				x_coord += dist;
				break;
			case 2:
				y_coord -= dist;
				break;
			case 3:
				y_coord -= dist;
				x_coord -= dist;
				break;
			case 4:
				x_coord -= dist;
				break;
			case 5:
				y_coord += dist;
				x_coord -= dist;
				break;
			case 6:
				y_coord += dist;
				break;
			case 7:
				y_coord += dist;
				x_coord += dist;
				break;
			default:
				break;
		}

		//wrap around
		if (x_coord >= Params.world_width)
		{
			x_coord = x_coord % Params.world_width;
		} else if (x_coord < 0)
		{
			x_coord += Params.world_width;
		}
		if (y_coord >= Params.world_height)
		{
			y_coord = y_coord % Params.world_height;
		} else if (y_coord < 0)
		{
			y_coord += Params.world_height;
		}
	}
	/* Alternate displayWorld, where you use Main.<pane> to reach into your
	   display component.
	   // public static void displayWorld() {}
	*/

	protected final void reproduce(Critter offspring, int direction)
	{
		if (energy >= Params.min_reproduce_energy)
		{
			offspring.energy = energy / 2;
			energy = energy / 2 + energy % 2;        //energy/=2 round up

			offspring.x_coord = x_coord;
			offspring.y_coord = y_coord;
			offspring.move(direction, 1);

			babies.add(offspring);
		} else
		{
			return;
		}
	}
	public abstract void doTimeStep();

	public abstract boolean fight(String oponent);

	/* NEW FOR PROJECT 5 */
	public enum CritterShape
	{
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}

	/* the TestCritter class allows some critters to "cheat". If you want to
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here.
	 *
	 * NOTE: you must make sure thath the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctup update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter
	{
		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation()
		{
			return population;
		}

		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies()
		{
			return babies;
		}

		protected void setEnergy(int new_energy_value)
		{
			super.energy = new_energy_value;
		}

		protected int getX_coord()
		{
			return super.x_coord;
		}

		protected void setX_coord(int new_x_coord)
		{
			super.x_coord = new_x_coord;
		}

		protected int getY_coord()
		{
			return super.y_coord;
		}

		protected void setY_coord(int new_y_coord)
		{
			super.y_coord = new_y_coord;
		}
	}


}
