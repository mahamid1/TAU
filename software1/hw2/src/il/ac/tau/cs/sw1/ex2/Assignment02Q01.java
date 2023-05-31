package il.ac.tau.cs.sw1.ex2;

public class Assignment02Q01 {

	public static void main(String[] args) {
		for(int i=0;i<args.length;i++)
		{
			int x=args[i].charAt(0);
			if(x%3==0 & x%2==0)
			{
				System.out.println(args[i].charAt(0));
			}
		}
		
	}

}
