package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Set;

public class TransportShip implements Spaceship{
	private String name;
	private int commissionYear;
	private float maximalSpeed;
	private Set<CrewMember> crewMembers;
	private int cargoCapacity;
	private int passengerCapacity;
	private int FirePower=10;
	private int StartingCost=3000;

	
	public TransportShip(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, int cargoCapacity, int passengerCapacity){
		this.name=name;
		this.commissionYear=commissionYear;
		this.maximalSpeed=maximalSpeed;
		this.crewMembers=crewMembers;
		this.cargoCapacity=cargoCapacity;
		this.passengerCapacity=passengerCapacity;
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
		return this.FirePower;
	}

	@Override
	public Set<? extends CrewMember> getCrewMembers() {
		return this.crewMembers;
	}

	@Override
	public int getAnnualMaintenanceCost() {
		return this.StartingCost+5*this.cargoCapacity+3*this.passengerCapacity;
	}
	public int getCargoCapacity()
	{
		return this.cargoCapacity;
	}
	public int getPassengerCapacity()
	{
		return this.passengerCapacity;
	}
	public String toString() {
		return "TransportShip\n"+"\tName="+this.getName()+"\n\tCommissionYear="+this.getCommissionYear()+"\n\tMaximalSpeed="+this.getMaximalSpeed()+
				"\n\tFirePower="+this.getFirePower()+"\n\tCrewMembers="+this.getCrewMembers().size()+"\n\tAnnualMaintenanceCost="+this.getAnnualMaintenanceCost()+
				"\n\tCargoCapacity="+this.getCargoCapacity()+"\n\tPassengerCapacity="+this.getPassengerCapacity();
		}

}
