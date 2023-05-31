package il.ac.tau.cs.sw1.ex9.starfleet;

public class CrewWoman implements CrewMember {
	private int age;
	private int yearsInService;
	private String name;

	
	public CrewWoman(int age, int yearsInService, String name){
		this.age=age;
		this.yearsInService=yearsInService;
		this.name=name;
	}


	@Override
	public String getName() {
		return this.name;
	}


	@Override
	public int getAge() {
		return this.age;
	}


	@Override
	public int getYearsInService() {
		return this.yearsInService;
	}
	public String toString()
	{
		return "CrewWoman\n"+"\tName="+this.getName()+"\n\tAge="+this.getAge()+"\n\tyearsInService="+this.getYearsInService();
	}

}
