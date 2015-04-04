package towers;

import game.Player;

public class Tower {	
	
	private int buyingCost;
	private int refundValue;
	private double range;
	private double power;
	private double rateofFire;
	private int level;
	private int upgradeCost;
	private int xPos;
	private int yPos;

	public Tower(int buyingCost, int refundValue, double range,
			double power, double rateofFire, int level, int upgradeCost, int xPos, int yPos) {
		// TODO Auto-generated constructor stub
		this.buyingCost = buyingCost;
		this.refundValue = refundValue;
		this.range = range;
		this.power = power;
		this.rateofFire = rateofFire;
		this.level = level;
		this.upgradeCost = upgradeCost;
		this.xPos = xPos;
		this.yPos = yPos;
	}
	public Tower(int xPos, int yPos){
		this.buyingCost = 100;
		this.refundValue = 90;
		this.range = 20;
		this.power = 20;
		this.rateofFire = 8;
		this.level = 1;
		this.upgradeCost = 200;
		this.xPos = xPos;
		this.yPos = yPos;
	}
	

	public void refundTower() { // credit of this tower's salvage value
											// is added to the user's account.
		Player.addCredits(this.refundValue);
	}

	public int getBuyingCost() { // getters and setters for the private
									// attributes of this object.
		return this.buyingCost;
	}

	public void setBuyingCost(int buyingCost) {
		this.buyingCost = buyingCost;
	}

	public double getRefundValue() {
		return this.refundValue;
	}

	public void setRefundValue(int refundValue) {
		this.refundValue = refundValue;
	}

	public double getRange() {
		return this.range;
	}

	public void setRange(double range) {
		this.range = range;
	}

	public double getPower() {
		return this.power;
	}

	public void setPower(double power) {
		this.power = power;
	}

	public double getRateofFire() {
		return this.rateofFire;
	}

	public void setRateofFire(double rateofFire) {
		this.rateofFire = rateofFire;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public double getUpgradeCost() {
		return this.upgradeCost;
	}

	public void setUpgradeCost(int upgradeCost) {
		this.upgradeCost = upgradeCost;
	}
	
	public int getX(){
		return this.xPos;
	}
	
	public int getY(){
		return this.yPos;
	}
	
}
