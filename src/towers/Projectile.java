package towers;

public class Projectile {
	
	private double xLoc;
	private double yLoc;
	private double xDest;
	private double yDest;
	private double xInit;
	private double yInit;
	private double error = 3;
	private int power;
	private boolean freeze;
	private final double speed = 5;
	private boolean arrivedAtTarget = false;
	
	
	public Projectile(double pXInit, double pYInit, double pXDest, double pYDest, int pPower, boolean pFreeze){
		xInit = pXInit;
		xLoc = pXInit;
		xDest = pXDest;
		yInit= pYInit;
		yLoc = pYInit;
		yDest = pYDest;
		power = pPower;
		freeze = pFreeze;
		arrivedAtTarget = false;
		System.out.println("Projectile created!");
	}
	
	public double angleOfProjectileInDegrees(){
		return (180/Math.PI)*Math.atan2(yDest-yInit, xDest-xInit);
	}

	public void move(){
		
		//projectile has hit
		if (Math.abs(xLoc - xDest)< error || Math.abs(yLoc - yDest)< error){
			arrivedAtTarget = true;
		}
		else{
			xLoc += ((xDest - xInit)*speed/100);
			yLoc += ((yDest - yInit)*speed/100);
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
	
}
