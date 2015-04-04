package towers;

public class Projectile {
	
	private double XLoc;
	private double YLoc;
	private double XDest;
	private double YDest;
	private double XInit;
	private double YInit;
	private int power;
	private boolean freeze;
	private final double speed = 10;
	
	
	public Projectile(double pXInit, double pYInit, double pXDest, double pYDest, int pPower, boolean pFreeze){
		XInit = pXInit;
		XLoc = pXInit;
		XDest = pXDest;
		YInit= pYInit;
		YLoc = pYInit;
		YDest = pYDest;
		power = pPower;
		freeze = pFreeze;
	}
	
	public double angleOfProjectileInDegrees(){
		return (180/Math.PI)*Math.atan2(YDest-YInit, XDest-XInit);
	}

	
}
