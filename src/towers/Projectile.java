package towers;

public class Projectile {
	
	private double xLoc;
	private double yLoc;
	private double xDest;
	private double yDest;
	private double xInit;
	private double yInit;
	private long totalTime;
	private long startTime;
	private double error = 3;
	private int power;
	private boolean freeze;
	private final long speed = 50;
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
		double yDist = yInit-yDest;
		double xDist = xInit-xDest;
		double dist = Math.sqrt(xDist*xDist+yDist*yDist);
		totalTime = ((long)dist)/speed *1000;
		startTime = System.currentTimeMillis();
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
			double xDist = xLoc-xDest;
			double yDist = yLoc-yDest;
			double dist = Math.sqrt(xDist*xDist+yDist*yDist);
			double time = dist/speed;
			xLoc = xInit + ((xDest - xInit)*(System.currentTimeMillis()-startTime)/totalTime);
			yLoc = yInit + ((yDest - yInit)*(System.currentTimeMillis()-startTime)/totalTime);
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
