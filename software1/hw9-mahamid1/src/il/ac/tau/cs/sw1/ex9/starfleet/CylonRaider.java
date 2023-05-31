package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class CylonRaider implements Spaceship {
	private String name;
	private int commissionYear;
	private float maximalSpeed;
	private Set<Cylon> crewMembers;
	private List<Weapon> weapons;
	private int FirePower=10;
	private int startingCost=3500;
	

	public CylonRaider(String name, int commissionYear, float maximalSpeed, Set<Cylon> crewMembers,
			List<Weapon> weapons) {
		this.name=name;
		this.commissionYear=commissionYear;
		this.maximalSpeed=maximalSpeed;
		this.crewMembers=crewMembers;
		this.weapons=weapons;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getCommissionYear() {
		return this.commissionYear;
	}

	@Override
	public float getMaximalSpeed() {
		return this.maximalSpeed;
	}

	@Override
	public int getFirePower() {
		int sum=this.FirePower;
		for(Weapon weapon:this.weapons)
		{
			sum+=weapon.getFirePower();
		}
		return sum;
	}

	@Override
	public Set<? extends CrewMember> getCrewMembers() {
		return this.crewMembers;
	}

	@Override
	public int getAnnualMaintenanceCost() {
		int sum=this.startingCost;
		sum+=this.crewMembers.size()*500;
		sum+=this.maximalSpeed*1200;
		for(Weapon weapon: this.weapons)
		{
			sum+=weapon.getAnnualMaintenanceCost();
		}
		return sum;
	}
	public List<Weapon> getWeapon()
	{
		return this.weapons;
	}
	public String toString() {
		return "CylonRaider\n"+"\tName="+this.getName()+"\n\tCommissionYear="+this.getCommissionYear()+"\n\tMaximalSpeed="+this.getMaximalSpeed()+
				"\n\tFirePower="+this.getFirePower()+"\n\tCrewMembers="+this.getCrewMembers().size()+"\n\tAnnualMaintenanceCost="+this.getAnnualMaintenanceCost()+
				"\n\tWeaponArray="+this.getWeapon();
		}
	

	

}
