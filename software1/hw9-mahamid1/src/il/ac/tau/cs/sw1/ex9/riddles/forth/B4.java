package il.ac.tau.cs.sw1.ex9.riddles.forth;

import java.util.Iterator;

public class B4 implements Iterator<String> {
	private String[] strings;
	private int index;
	public B4(String[] strings, int k)
	{
		String[] curr=new String[strings.length*k];
		for(int i=0;i<strings.length;i++)
		{
			for(int j=0;j<k;j++)
			{
				curr[i+j*k]=strings[i];
			}
		
		}
		this.index=0;
		this.strings=curr;
		
	}
	@Override
	public boolean hasNext()
	{
		if(this.index+1 <this.strings.length)
		{
			return true;
		}
		return false;
	}
	@Override
	public String next()
	{
		this.index+=1;
		return this.strings[index];
	}
	
}
