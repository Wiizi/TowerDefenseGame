package temp;
import java.util.*;

public abstract class tower { // master class of towers.
	private double buyingCost;
	private double refundValue;
	private double range;
	private double power;
	private double rateofFire;
	private int level;
	private double upgradeCost;

	public tower(double buyingCost, double refundValue, double range,
			double power, double rateofFire, int level, double upgradeCost) {
		// TODO Auto-generated constructor stub
		this.buyingCost = buyingCost;
		this.refundValue = refundValue;
		this.range = range;
		this.power = power;
		this.rateofFire = rateofFire;
		this.level = level;
		this.upgradeCost = upgradeCost;
	}

	public abstract void attack(List<critter> listofCritter); // attack the
																// critter
																// that's
																// closest
																// (smallest
																// distance)
																// from the
																// tower,
	// it is abstract method and is implemented differently in the 3 type of
	// towers, long range tower, bomb tower, and freeze tower.

	public void upgrade(account x) { // upgrade the current tower with
										// increasing levels and abilities and
										// deduce the credit from user.
		if (x.credit - upgradeCost >= 0) {
			this.level++;
			x.credit = x.credit - upgradeCost;
			this.range = this.range * 2;
			this.power = this.power * 2;
			this.rateofFire = this.rateofFire * 2;
			this.refundValue = this.refundValue * 2;
		} else {
			System.out.println("Not Sufficient Credit");
		}
	}

	public void refundTower(account x) { // credit of this tower's salvage value
											// is added to the user's account.
		x.credit += this.refundValue;
	}

	public double getBuyingCost() { // getters and setters for the private
									// attributes of this object.
		return this.buyingCost;
	}

	public void setBuyingCost(double buyingCost) {
		this.buyingCost = buyingCost;
	}

	public double getRefundValue() {
		return this.refundValue;
	}

	public void setRefundValue(double refundValue) {
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

	public void setUpgradeCost(double upgradeCost) {
		this.upgradeCost = upgradeCost;
	}

}
