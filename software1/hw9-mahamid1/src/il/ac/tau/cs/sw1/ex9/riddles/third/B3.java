package il.ac.tau.cs.sw1.ex9.riddles.third;

public class B3 extends A3 {
	public B3(String str)
	{
		super(str);
	}
	public void foo(String str) throws B3
	{
		throw new B3(str);
	}
	public String getMessage()
	{
		return this.s;
	}
	
}