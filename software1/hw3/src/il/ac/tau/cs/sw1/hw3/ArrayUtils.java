package il.ac.tau.cs.sw1.hw3;

public class ArrayUtils {
	
	public static int[][] transposeSecondaryMatrix(int[][] m)
	{
		int [][] tmp= new int[m[0].length][m.length];
		for (int i = m.length; i>0;i--)
		{
			for (int j = m[i-1].length;j>0;j--)
			{
				tmp[m[0].length-j][m.length-i]=m[i-1][j-1];
			
			}
		}
		return tmp;
		
	}
	
	public static int[] shiftArrayCyclic(int[] array, int move, char direction)
	{
		if (move >0 && ( direction=='R' || direction=='L'))
		{
			int [] tmp = new int[array.length];
			int newmove= move%tmp.length;
			if (direction=='R')
			{
				for( int i =0 ;i<array.length;i++)
				{
					tmp[(i+newmove)%tmp.length]=array[i];
					
				}
				array=tmp.clone();
			}
			else
			{
				for( int i =0 ;i<array.length;i++)
				{
					tmp[(i-newmove)%tmp.length]=array[i];
					
				}
				array=tmp.clone();
				
			}
			
		}
		return array;
		
	}
	
	public static int alternateSum(int[] array)
	{
		int maxsum=0;
		for (int i=0;i<array.length; i++)
		{
			int currentsum=array[i];
			for(int j =i+1;j<array.length;j++)
			{
				if ((j%i)%2==1)
				{
					currentsum-=array[j];
					
				}
				else
				{
					currentsum+=array[j];
				}	
				if (currentsum>=maxsum)
				{
					maxsum=currentsum;
				}
			}
			
		
		}
		if(maxsum>=0)
		{
			return maxsum;	
		}
		return 0;
		
	
		
	}
	
	public static int findPath(int[][] m, int i, int j)
	{
		int new_i=Math.max(i,j);
		int new_j=Math.min(i,j);
		int[][] new_m=new int[m.length][m.length];
		for (int k=0;k<m.length;k++) {
			for (int p=0;p<m.length;p++) {
				new_m[k][p]=m[k][p];
			}
		}
		if (new_m[new_i][new_j]==1) {
			return 1;
		}
		else {
			for (int k=0;k<new_m[new_i].length;k++) {
				if (k!=new_i && m[new_i][k]==1) {
					new_m[new_i][k]=0;
					new_m[k][new_i]=0;
					return findPath(new_m,new_j,k);
				}
			}
		}
		return 0;
		
	}

		
	

}
