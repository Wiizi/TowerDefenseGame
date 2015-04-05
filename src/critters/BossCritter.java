package critters;

import critters.Critter.type;

public class BossCritter extends Critter {

	public BossCritter(int[][] Locations) {
		
		super(Locations, health, armor, speed, reward, name, type.BOSS);

	}

	private static String		name		= "Boss";
	private static double 		health 		= 50;
	private static double 		speed		= 0.2;
	private static int			reward		= 200;
	private static double		armor		= 5;


}

