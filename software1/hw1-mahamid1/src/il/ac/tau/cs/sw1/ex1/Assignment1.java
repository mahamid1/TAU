package il.ac.tau.cs.sw1.ex1;

public class Assignment1 {
	public static void main(String[] args){
		
		if(args.length!=3 )
		{
			System.out.println("Invalid input!");
		}
		else
		{
			int a =Integer.parseInt(args[0]);
			int b =Integer.parseInt(args[1]);
			int c =Integer.parseInt(args[2]);
			if(a>0 & b>0 & c>0)
			
			{
				int x1= (a*a);
				int x2= (b*b);
				int x3= (c*c);
				if(a+b>c & b+c>a & c+a>b)
				{
					if (x1+x2==x3 || x2+x3==x1 ||x1+x3==x2)
					{
						System.out.println("The input"+" ("+a+","+b+","+c+") defines a valid right triangle!");
					}
					else 
					{
						System.out.println("The input ("+a+", "+b+", "+c+") defines a valid triangle!");
					}
				
				
				}
				else
				{

					System.out.println("The input ("+a+", "+b+", "+c+") does not define a valid triangle!");
				}
				
			}
			else
			{

				System.out.println("Invalid input!");
				
			}
					
			
		}
		

	}
}

