package il.ac.tau.cs.sw1.ex9.starfleet;

public class Cylon implements CrewMember {
	private String name;
	private int age;
	private int yearInService;
	private int modelNumber;

	public Cylon(String name, int age, int yearsInService, int modelNumber) {
		this.name=name;
		this.age=age;
		this.yearInService=yearsInService;
		this.modelNumber=modelNumber;
		
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
		return this.yearInService;
	}
	public int getModelNumber()
	{
		return this.modelNumber;
	}
	public String toString()
	{
		return "Cylon\n"+"\tName="+this.getName()+"\n\tAge="+this.getAge()+"\n\tyearsInService="+this.getYearsInService()+"\n\tmodelNumber="+this.getModelNumber();
		
	}

}
