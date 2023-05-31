package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;


public class Bomber implements Spaceship{
	private String name;
	private int commissionYear;
	private float maximalSpeed;
	private Set<CrewMember> crewMembers;
	private List<Weapon> weapons;
	private int numberOfTechnicians;
	private	int StartingCost=5000;
	private  int FirePower=10;

	public Bomber(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, List<Weapon> weapons, int numberOfTechnicians){
		this.name=name;
		this.commissionYear=commissionYear;
		this.maximalSpeed=maximalSpeed;
		this.crewMembers=crewMembers;
		this.weapons=weapons;
		this.numberOfTechnicians=numberOfTechnicians;
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
		for(Weapon weapon : this.weapons)
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
		int sum=0;
		for(Weapon weapon:this.weapons)
		{
			sum+=weapon.getAnnualMaintenanceCost();
		}
		sum=(int)Math.round((double) sum*(1-(this.numberOfTechnicians*0.1)));
		sum+=this.StartingCost;
		return sum;
	}
	public  List<Weapon> getWeapon(){
		return this.weapons;
	}
	public int getNumberOfTechnicians() {
		return this.numberOfTechnicians;
	}
	public String toString()
	{
		return "Bomber\n"+"\tName="+this.getName()+"\n\tCommissionYear="+this.getCommissionYear()+"\n\tMaximalSpeed="+this.getMaximalSpeed()+
				"\n\tFirePower="+this.getFirePower()+"\n\tCrewMembers="+this.getCrewMembers().size()+"\n\tAnnualMaintenanceCost="+this.getAnnualMaintenanceCost()+
				"\n\tWeaponArray="+this.getWeapon()+"\n\tNumberOfTechnicians="+this.getNumberOfTechnicians();
	}


}
