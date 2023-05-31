package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class Fighter implements Spaceship {
	private String name;
	private int	commissionYear;
	private float maximalSpeed;
	private Set<CrewMember> crewMembers;
	private List<Weapon> weapons;
	private int FirePower=10;
	private int StartingCost=2500;
	
	
	public Fighter(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, List<Weapon> weapons){
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
		for(Weapon weapon:weapons)
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
		int sum=this.StartingCost;
		for(Weapon weapon:this.weapons)
		{
			sum+=weapon.getAnnualMaintenanceCost();
		}
		sum+=Math.round(1000*this.maximalSpeed);
		return sum;
	}
	public List<Weapon> getWeapon() 
	{
		return this.weapons;
	}
	public String toString() {
		return "Fighter\n"+"\tName="+this.getName()+"\n\tCommissionYear="+this.getCommissionYear()+"\n\tMaximalSpeed="+this.getMaximalSpeed()+
				"\n\tFirePower="+this.getFirePower()+"\n\tCrewMembers="+this.getCrewMembers().size()+"\n\tAnnualMaintenanceCost="+this.getAnnualMaintenanceCost()+
				"\n\tWeaponArray="+this.getWeapon();
		}
	

	
	
}
