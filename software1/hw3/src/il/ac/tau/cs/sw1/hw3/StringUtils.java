package il.ac.tau.cs.sw1.hw3;

public class StringUtils {
	
	public static String findSortedSequence(String str)
	{
		if (str.equals(""))
		{
			return "";
		}
		if(str.split(" ").length==1)
		{
			return str;		
		}
		String[] arr= str.split(" ");
		String st2=arr[0];
		String st3 ="";
		for (int i =1;i<arr.length;i++)
		{
			if(arr[i-1].compareTo(arr[i])<=0)
			{
				st2+=" "+arr[i];
			}
			else {
				st2=arr[i];
			}
			if (st2.split(" ").length>=st3.split(" ").length)
			{
				st3=st2;
			}
		
			
			
		}
		return st3;
		
	}

	public static String parityXorStrings(String a, String b)
	{
		String result="";
		for(int i = 0 ; i<a.length();i++)
		{
			char current=a.charAt(i);
			int count=0;
			int count2=0;
			for (int j=0;j<a.length();j++)
			{
				if(a.charAt(j)==current)
				{
					count+=1;
				}
			}
			for (int w=0;w<b.length();w++)
			{
				if(b.charAt(w)==current)
				{
					count2+=1;
				}
			}
			if(count%2==1 && count2%2==0)
			{
				result+=current;
			}
		}
		return result;
		
	}
	
	public static boolean isAnagram(String a, String b)
	{
		String a2=a.replace(" ", "");
		String b2=b.replace(" ", "");
		if (a2.length()!=b2.length())
		{
		 return false;
		}
		else
		{
			for(int i =0;i<a2.length();i++)
			{
				if(b2.indexOf(a2.charAt(i))==-1)
				{
					return false;
				}
			}
			return true;
		 
		}
	}
}
