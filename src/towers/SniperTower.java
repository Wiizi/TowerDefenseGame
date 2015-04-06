package towers;

public class SniperTower extends Tower{
		
	public SniperTower(double x, double y){
		super(x,y);
		this.buyingCost =newBuyingCost;
		this.refundValue = newRefundValue;
		this.upgradeCost = newUpgradeCost;
		this.reloadTime = newReloadTime;
		this.range = newRange;
		this.power = newPower;
		this.towerType = type.SNIPER;
		
	}
	
	private static int newBuyingCost = 300;
	private static int newRefundValue = 250;
	private static int newUpgradeCost = 400;
	private static int newReloadTime = 5;
	private static double newRange = 300;
	private static int newPower = 10;


}



