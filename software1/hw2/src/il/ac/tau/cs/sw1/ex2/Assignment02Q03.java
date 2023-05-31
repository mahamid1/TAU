package il.ac.tau.cs.sw1.ex2;

public class Assignment02Q03 {

	public static void main(String[] args) {
		int numOfEven = 0;
		int n = Integer.parseInt(args[0]);
		System.out.println("The first "+ n +" Fibonacci numbers are:");
		for(int i=0;i<n;i++)
		{
			if(fib(i)%2==0)
			{
				numOfEven+=1;	
			}
			if(i==n-1)
			{
				System.out.println(fib(i));
			}
			else 
			{
				System.out.print(fib(i)+" ");
			}
		}
		System.out.println("The number of even numbers is: "+numOfEven);

	}
	public static int fib(int n) // function that compute the n`th Fibonacci number
	{
		if(n<=1)
		{
			return 1;
		}
		return fib(n-1)+fib(n-2);
	}

}
