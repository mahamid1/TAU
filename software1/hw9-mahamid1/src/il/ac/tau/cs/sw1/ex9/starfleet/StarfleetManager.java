package il.ac.tau.cs.sw1.ex9.starfleet;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


class SortByFire implements Comparator<Spaceship>
{

	@Override
	public int compare(Spaceship o1, Spaceship o2) {
		if(o1.getFirePower()!=o2.getFirePower())
			return o2.getFirePower()-o1.getFirePower();	
		else if(o2.getCommissionYear()!=o1.getCommissionYear())
		{
			return o2.getCommissionYear()-o1.getCommissionYear();
		}
		return o2.getClass().getSimpleName().compareTo(o1.getClass().getSimpleName());
	}
	
}
class SortBycommission implements Comparator<Spaceship>
{

	@Override
	public int compare(Spaceship o1, Spaceship o2) {
		return o2.getCommissionYear()-o1.getCommissionYear();
	}

			
	}
class SortByname implements Comparator<Spaceship>
{

	@Override
	public int compare(Spaceship o1, Spaceship o2) {
		return o1.getName().compareTo(o2.getName()) ;
	}

			
	}
class sortbyvalue implements Comparator<Map.Entry<OfficerRank, Integer>>
{

	@Override
	public int compare(Entry<OfficerRank, Integer> o1, Entry<OfficerRank, Integer> o2) {
		if(o1.getValue()!=o2.getValue())
			return o1.getValue()-o2.getValue();
		return o1.getKey().compareTo(o2.getKey());
	}
	}



public class StarfleetManager {

	/**
	 * Returns a list containing string representation of all fleet ships, sorted in descending order by
	 * fire power, and then in descending order by commission year, and finally in ascending order by
	 * name
	 */
	
		
	
	public static List<String> getShipDescriptionsSortedByFirePowerAndCommissionYear (Collection<Spaceship> fleet) {
		List<String> result = new ArrayList<>();
		List<Spaceship> tmp=new ArrayList<>();
		for(Spaceship ship:fleet)
		{
			tmp.add(ship);
		}
		
		Collections.sort(tmp,new SortByFire());
		for(Spaceship ship:tmp)
		{
			result.add(ship.toString());
		}
		return result;	
		
	}

	/**
	 * Returns a map containing ship type names as keys (the class name) and the number of instances created for each type as values
	 */
	public static Map<String, Integer> getInstanceNumberPerClass(Collection<Spaceship> fleet) {
		Map<String,Integer> curr=new HashMap<>();
		for(Spaceship ship:fleet)
		{
			if(curr.containsKey(ship.getClass().getSimpleName()))
			{
				curr.put(ship.getClass().getSimpleName(),curr.get(ship.getClass().getSimpleName())+1);
			}
			else
			{
				curr.put(ship.getClass().getSimpleName(),1);
			}
		}
		return curr;
	}


	/**
	 * Returns the total annual maintenance cost of the fleet (which is the sum of maintenance costs of all the fleet's ships)
	 */
	public static int getTotalMaintenanceCost (Collection<Spaceship> fleet) {
		int sum=0;
		for(Spaceship ship:fleet)
		{
			sum+=ship.getAnnualMaintenanceCost();
		}
		return sum;

	}


	/**
	 * Returns a set containing the names of all the fleet's weapons installed on any ship
	 */
	public static Set<String> getFleetWeaponNames(Collection<Spaceship> fleet) {
		Set<String> AllNames=new HashSet<>();
		for(Spaceship ship : fleet)
		{
			if(ship instanceof Fighter)
			{
				for(Weapon weapon: ((Fighter)ship).getWeapon())
				{
					AllNames.add(weapon.getName());
				}
			}
			if(ship instanceof Bomber)
			{
				for(Weapon weapon : ((Bomber)ship).getWeapon())
				{
					AllNames.add(weapon.getName());
				}
			}
			if(ship instanceof ColonialViper)
			{
				for(Weapon weapon : ((ColonialViper)ship).getWeapon())
				{
					AllNames.add(weapon.getName());
				}
			}
			if(ship instanceof CylonRaider)
			{
				for(Weapon weapon : ((CylonRaider)ship).getWeapon())
				{
					AllNames.add(weapon.getName());
				}
			}
			
		}
		return AllNames;

	}

	/*
	 * Returns the total number of crew-members serving on board of the given fleet's ships.
	 */
	public static int getTotalNumberOfFleetCrewMembers(Collection<Spaceship> fleet) {
		int sum=0;
		for(Spaceship ship:fleet)
		{
			sum+=ship.getCrewMembers().size();
		}
		return sum;

	}

	/*
	 * Returns the average age of all officers serving on board of the given fleet's ships. 
	 */
	public static float getAverageAgeOfFleetOfficers(Collection<Spaceship> fleet) {
		int cnt=-1;
		int ages=0;
		for(Spaceship ship :fleet)
		{
			for(CrewMember member :ship.getCrewMembers())
			{
				if(member instanceof Officer)
				{
					if(cnt<0)
					{
						cnt=1;
					}
					else {
					cnt+=1;
					}
					ages+=member.getAge();
				}
			}
		}
		if(cnt<0)
			return 0; // no officers on board
		float avg=((float)ages)/ ((float)cnt);
		return avg;
	}

	/*
	 * Returns a map mapping the highest ranking officer on each ship (as keys), to his ship (as values).
	 */
	public static Map<Officer, Spaceship> getHighestRankingOfficerPerShip(Collection<Spaceship> fleet) {
		Map<Officer,Spaceship> result=new HashMap<>();
		Officer max = null;
		for(Spaceship ship : fleet)
		{
			int x=0;
		
			for(CrewMember member :ship.getCrewMembers())
			{
				if(member instanceof Officer)
				{
				if(x==0)
				{
					max=((Officer) member);
					x+=1;
				}
				else if(((Officer)member).getRank().compareTo(max.getRank())>0)
				{
					max=((Officer)member);
				}
	
				}
			}
			result.put(max,ship);
		}
		return result;

	}

	/*
	 * Returns a List of entries representing ranks and their occurrences.
	 * Each entry represents a pair composed of an officer rank, and the number of its occurrences among starfleet personnel.
	 * The returned list is sorted ascendingly based on the number of occurrences.
	 */
	public static List<Map.Entry<OfficerRank, Integer>> getOfficerRanksSortedByPopularity(Collection<Spaceship> fleet) {
		Map<OfficerRank,Integer> tmp=new HashMap<>();
		List<Map.Entry<OfficerRank, Integer>> result=new ArrayList<>();
		for(Spaceship ship: fleet)
		{
			for(CrewMember member :ship.getCrewMembers())
			{
				if(member instanceof Officer)
				{
					if(tmp.containsKey(((Officer)member).getRank()))
					{
						tmp.put(((Officer)member).getRank(), tmp.get(((Officer)member).getRank())+1);
					}
					else
					{
						tmp.put(((Officer)member).getRank(), 1);
					}			
				}
			}
		}
		for(Map.Entry<OfficerRank, Integer> entry:tmp.entrySet())
		{
			result.add(entry);
		}
		Collections.sort(result,new sortbyvalue());
		
		
		

		return result;
	}

}
