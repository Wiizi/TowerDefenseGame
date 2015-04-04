package towers;

import critters.Critter;

public class Projectile {
	
	private double xLoc;
	private double yLoc;
	private double xDest;
	private double yDest;
	private double xInit;
	private double yInit;
	private long totalTime;
	private long startTime;
	private final double error = 1;
	private int power;
	private boolean freeze;
	private final long speed = 20;
	private boolean arrivedAtTarget = false;
	private Critter targetCritter;
	
	public Projectile(double pXInit, double pYInit, double pXDest, double pYDest, int pPower, boolean pFreeze, Critter pTargetCritter){
		xInit = pXInit;
		xLoc = pXInit;
		xDest = pXDest;
		yInit= pYInit;
		yLoc = pYInit;
		yDest = pYDest;
		power = pPower;
		freeze = pFreeze;
		arrivedAtTarget = false;
		double yDist = yInit-yDest;
		double xDist = xInit-xDest;
		double dist = Math.sqrt(xDist*xDist+yDist*yDist);
		totalTime = ((long)dist)/speed *1000;
		startTime = System.currentTimeMillis();
		targetCritter = pTargetCritter;
	}
	
	public double angleOfProjectileInDegrees(){
		return (180/Math.PI)*Math.atan2(yDest-yInit, xDest-xInit);
	}

	public double angleOfProjectileInRadians(){
		return Math.atan2(yDest-yInit, xDest-xInit);
	}

	public void move(){
		
		//projectile has hit
		if (Math.abs(xLoc - xDest)< 5 || Math.abs(yLoc - yDest)< 5){
			arrivedAtTarget = true;
			targetCritter.takeDamage(power);
		}
		else{
			xLoc += speed*Math.cos(angleOfProjectileInRadians());

			yLoc += speed*Math.sin(angleOfProjectileInRadians());
		}
	}
	
	public boolean hasArrived(){
		return arrivedAtTarget;
	}
	
	public double getX(){
		return this.xLoc;
	}
	public double getY(){
		return this.yLoc;
	}
	
	public long getSpeed(){
		return this.speed;
	}
	
}
