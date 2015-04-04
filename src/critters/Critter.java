package critters;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


abstract public class Critter{

	public enum type{GRUNT, SCOUT, ARMORED, TANK, BOSS};
	
	private double 		health;
	private double 		speed;
	private double 		modifier	= 0;
	private int			reward;
	private double 		armor;
	private float 		XLoc;
	private float		YLoc;
	private double		PrevXLoc;
	private double		PrevYLoc;
	private String		name;
	private boolean 	alive;
	public boolean		canMove = true;
	private int[][] 	locations;
	private int 		locationIncrementer = 0;
	private boolean 	visible = false;
	protected type		critterType;
	private List<CrObserver> critterObservers;
	public enum direction {LEFT, RIGHT, UP, DOWN};

	direction critterDirection;

	//initialize critter at the start 
	public Critter(int[][] pLocations, double pHealth, double pArmor, double pSpeed, int pReward, String pName){
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
		critterType = type.GRUNT;

	}

/*
	public int[] move(int[][] map){
		org.junit.Assert.assertNotNull(map);
		canMove = true;
		//find next x position
		try{
			//try to move forward
			if(map[XLoc+1][YLoc]==1 && !previousLocation(XLoc+1,YLoc) ){
				updateLocation();
				XLoc = XLoc+1;
				int[] Location = {XLoc, YLoc};
				return Location;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){}
		//try to move backwards
		try{
			if(map[XLoc-1][YLoc]==1 && !previousLocation(XLoc-1,YLoc) ){
				updateLocation();
				XLoc = XLoc-1;
				int[] Location = {XLoc, YLoc};
				return Location;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){}



		//find next y position
		try{
			//try to move up
			if(map[XLoc][YLoc+1]==1 && !previousLocation(XLoc,YLoc+1) ){
				updateLocation();
				YLoc = YLoc+1;
				int[] Location = {XLoc, YLoc};
				return Location;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){}

		try{
			//try to move down
			if(map[XLoc][YLoc-1]==1 && !previousLocation(XLoc,YLoc-1) ){
				updateLocation();
				YLoc = YLoc-1;
				int[] Location = {XLoc, YLoc};
				return Location;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){}


		
		//return new updated position so that it can be updated by game 

		canMove = false;
		int[] Location = {XLoc, YLoc};
		return Location;
	}
*/
	
	public void move(){

		if(locationIncrementer ==0)
		{
			visible = true;
		}
		try{

			if(!(XLoc>locations[locationIncrementer+1][0]-speed&&XLoc<locations[locationIncrementer+1][0]+speed) ){
				if(XLoc<=locations[locationIncrementer+1][0]){
					XLoc += speed;
					critterDirection = direction.RIGHT;
				}
				else if(XLoc>=locations[locationIncrementer+1][0])
				{
					XLoc -= speed;
					critterDirection = direction.LEFT;
				}
			}
			else if(!(YLoc>=locations[locationIncrementer+1][1]-speed&&YLoc<=locations[locationIncrementer+1][1]+speed) ){
				if(YLoc<=locations[locationIncrementer+1][1]){
					YLoc += speed;
					critterDirection = direction.DOWN;
				}
				else if(YLoc>=locations[locationIncrementer+1][1]){
					YLoc -= speed;
					critterDirection = direction.UP;
				}
			}
			else{
				locationIncrementer++;
			}


		}
		catch(IndexOutOfBoundsException e){
			visible=false;
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

	//this method determines if the critter is trying to move into the location it previously occupied
	private boolean previousLocation(double d, double yLoc2){
		if( d==PrevXLoc && yLoc2 ==PrevYLoc){
			return true;
		}

		return false;
	}
	//this method updates the location of both previous location variables each time the critter moves
	public void updateLocation(){
		PrevXLoc = XLoc;
		PrevYLoc = YLoc;
	}

	//method checks if critter has reach endpoint
	public boolean isCritterAtEndpoint(int[] exit){
		if(XLoc == exit[0] && YLoc == exit[1])
			return true;

		return false;
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
