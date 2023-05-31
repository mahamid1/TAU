package il.ac.tau.cs.sw1.ex2;

public class Assignment02Q02 {

	public static void main(String[] args) {
		// do not change this part below
		double piEstimation = 0.0;
		double x=1.0;
		for(int i=0;i<Integer.parseInt(args[0]);i++)
		{
			double y =1 / x;
			if(i%2==0)
			{
				piEstimation=piEstimation+y;
				
			}
			else
			{
				piEstimation=piEstimation-y;
			}
			x+=2;
		}
		piEstimation=piEstimation*4;
		
		
		
		
		// do not change this part below
		System.out.println(piEstimation + " " + Math.PI);

	}

}
