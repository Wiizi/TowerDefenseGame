package towers; import towers.Tower.type;
import critters.Critter;

class FreezeTower extends Tower
{
	private static int newBuyingCost = 200;
	private static int newRefundValue = 180;
	private static int newUpgradeCost = 300;
	private static int newReloadTime = 2;

	public FreezeTower(double xPos, double yPos) {
		super(xPos, yPos);
		this.freezeTower = true;
		this.buyingCost = newBuyingCost;
		this.refundValue= newRefundValue;
		this.upgradeCost = newUpgradeCost;
		this.reloadTime = newReloadTime;
		this.towerType= type.FREEZE;
		
	}

}
