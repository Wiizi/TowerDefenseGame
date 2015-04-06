package critters;

import java.util.ArrayList;
import java.util.List;


abstract public class Critter{

	public enum type{GRUNT, SCOUT, ARMORED, TANK, BOSS};
	public enum direction {LEFT, RIGHT, UP, DOWN};
	
	private double 		health;
	private double 		speed;
	private double 		modifier	= 0;
	private int			reward;
	private double 		armor;
	private float 		XLoc;
	private float		YLoc;
	private String		name;
	private boolean 	alive;
	public boolean		canMove = true;
	private int[][] 	locations;
	private int 		locationIncrementer = 0;
	private boolean 	visible = false;
	private boolean 	atEndPoint = false;
	protected type		critterType;
	private List<CrObserver> critterObservers;
	private double distanceTravelled;



	direction critterDirection;

	//initialize critter at the start 
	public Critter(int[][] pLocations, double pHealth, double pArmor, double pSpeed, int pReward, String pName, type pCritterType){
		health =pHealth;
		armor = pArmor;
		speed =pSpeed;
		reward = pReward;
		name = pName;
		XLoc = pLocations[0][0];
		YLoc = pLocations[0][1];
		alive = true;
		locations = pLocations;
		critterObservers = new ArrayList<CrObserver>();
		critterType = pCritterType;
		distanceTravelled=0;

	}


	
	public void move(){

		if(locationIncrementer ==0)
		{
			visible = true;
		}

		try{

			if(!(XLoc>locations[locationIncrementer+1][0]-speed&&XLoc<locations[locationIncrementer+1][0]+speed) ){
				if(XLoc<=locations[locationIncrementer+1][0]){
					XLoc += speed;
					distanceTravelled+=speed;
					critterDirection = direction.RIGHT;
				}
				else if(XLoc>=locations[locationIncrementer+1][0])
				{
					XLoc -= speed;
					distanceTravelled+=speed;
					critterDirection = direction.LEFT;
				}
			}
			else if(!(YLoc>=locations[locationIncrementer+1][1]-speed&&YLoc<=locations[locationIncrementer+1][1]+speed) ){
				if(YLoc<=locations[locationIncrementer+1][1]){
					YLoc += speed;
					distanceTravelled+=speed;
					critterDirection = direction.DOWN;
				}
				else if(YLoc>=locations[locationIncrementer+1][1]){
					YLoc -= speed;
					distanceTravelled+=speed;
					critterDirection = direction.UP;
				}
			}
			else{
				locationIncrementer++;
			}


		}
		catch(IndexOutOfBoundsException e){
			visible=false;
			atEndPoint = true;
		}
	}


	public void takeDamage(double damage){


		health = health - damage/armor;
		if(health <= 0){
			alive = false;
			visible = false;
		}
		
		//every time the critter takes damage, tell the observers

		notifyObservers();

	}

	

	//observer classes
	public void addObserver(CrObserver o){
		if(o != null)
			if(!critterObservers.contains(o))
				critterObservers.add(o);


	}

	public void notifyObservers(){
		for(CrObserver o :critterObservers){
			o.update();
		}
	}

	public void removeObserver(CrObserver o){
		critterObservers.remove(o);
	}


	//Getters and Setters

	
	public String getName() {
		return name;
	}
	
	
	
	

	public double getDistanceTravelled() {
		return distanceTravelled;
	}



	public boolean isAtEndPoint() {
		return atEndPoint;
	}



	public double getSpeed() {
		return speed;
	}

	public direction getCritterDirection() {
		return critterDirection;
	}

	public boolean isVisible() {
		return visible;
	}


	public double getHealth() {
		return health;
	}


	public float getXLoc() {
		return XLoc;
	}


	public float getYLoc() {
		return YLoc;
	}
	

	public double getModifier() {
		return modifier;
	}


	public int getReward() {
		return reward;
	}


	public boolean isAlive() {
		return alive;
	}


	public boolean CanMove() {
		return canMove;
	}
	

	public type getType(){
		return critterType;
	}



}


