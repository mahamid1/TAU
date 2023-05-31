package il.ac.tau.cs.sw1.ex9.starfleet;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


public class StealthCruiser extends Fighter {
	public static int current=0;
	
	public StealthCruiser(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, List<Weapon> weapons) {
		super(name,commissionYear,maximalSpeed,crewMembers,weapons);
		StealthCruiser.current++;
		
	}

	public StealthCruiser(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers){
		this(name, commissionYear, maximalSpeed, crewMembers, new ArrayList<Weapon>(Arrays.asList(new Weapon ("Laser Cannons",10,100))));
		
	}
	public int getAnnualMaintenanceCost()
	{
		return StealthCruiser.current*50 + super.getAnnualMaintenanceCost();
	}
	public String toString() {
		return "StealthCruiser\n"+"\tName="+this.getName()+"\n\tCommissionYear="+this.getCommissionYear()+"\n\tMaximalSpeed="+this.getMaximalSpeed()+
				"\n\tFirePower="+this.getFirePower()+"\n\tCrewMembers="+this.getCrewMembers().size()+"\n\tAnnualMaintenanceCost="+this.getAnnualMaintenanceCost()+
				"\n\tWeaponArray="+this.getWeapon();
		}
}


