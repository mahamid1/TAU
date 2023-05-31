package il.ac.tau.cs.sw1.ex9.riddles.first;

public class B1 extends A1 {
	private C1 c;
	public B1()
	{
		this.c=new C1();
	}
	public boolean foo()
	{
		return !c.foo();
	}
	
}
