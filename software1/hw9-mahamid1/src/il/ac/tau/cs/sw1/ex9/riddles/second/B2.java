package il.ac.tau.cs.sw1.ex9.riddles.second;

public class B2 extends A2 {
	public B2()
	{
		//nothing
	}
	public B2(boolean b)
	{
		//nothing
	}
	public A2 getA(boolean randomBool)
	{
		return new B2(randomBool);
	}
	
}
