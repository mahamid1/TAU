package il.ac.tau.cs.sw1.ex5;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class BigramModel {
	public static final int MAX_VOCABULARY_SIZE = 14500;
	public static final String VOC_FILE_SUFFIX = ".voc";
	public static final String COUNTS_FILE_SUFFIX = ".counts";
	public static final String SOME_NUM = "some_num";
	public static final int ELEMENT_NOT_FOUND = -1;
	
	String[] mVocabulary;
	int[][] mBigramCounts;
	
	// DO NOT CHANGE THIS !!! 
	public void initModel(String fileName) throws IOException{
		mVocabulary = buildVocabularyIndex(fileName);
		mBigramCounts = buildCountsArray(fileName, mVocabulary);
		
	}
	
	
	
	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public boolean CheckInt(String str1)
	{
		String str ="0123456789";
		for (int i=0;i<str1.length();i++)
		{
			if(str.indexOf(str1.charAt(i))==-1)
			{
				return false;
			}
		}
		return true;
	}
	public boolean CheckExist(String str1)
	{
		String str ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int cnt=0;
		for (int i=0;i<str1.length();i++)
		{
			if(str.indexOf(str1.charAt(i))!=-1)
			{
				cnt+=1;
			}
		}
		return cnt!=0;
	}
	public boolean InArray(String[] arr,String str)
	{
		if(arr.length==0)
		{
			return false;
		}
		for(int i=0;i<arr.length;i++)
		{
			if(arr[i]==null)
			{
				return false;
			}
			if(arr[i].equals(str.toLowerCase()))
			{
				return true;
			}
		}
		return false;
	}
	public String[] buildVocabularyIndex(String fileName) throws IOException{ // Q 1
		String[] result =new String[MAX_VOCABULARY_SIZE];
		int index=0;
		BufferedReader f1=new BufferedReader(new FileReader(fileName));
		String currentLine =f1.readLine();
		while(currentLine!=null && index<MAX_VOCABULARY_SIZE)
		{
			
			String[] tmp=currentLine.split(" ");
			for(int i=0;i<tmp.length;i++)
			{
				if(index<MAX_VOCABULARY_SIZE)					
				{
					if(CheckInt(tmp[i]))
					{
						result[index]=SOME_NUM;
						index+=1;
					}
					if(CheckExist(tmp[i]))					
					{
						if(!InArray(result,tmp[i]))
						{
							result[index]=tmp[i].toLowerCase();
							index+=1;	
						}
					}
				}
				
			}
			 currentLine =f1.readLine();
		}
		String[] result1;
		if(index<MAX_VOCABULARY_SIZE)
		{
			 result1=Arrays.copyOf(result, index);
		}
		else{result1=result;}
		f1.close();
		
		return result1;
	}
	
	
	
	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public int[][] buildCountsArray(String fileName, String[] vocabulary) throws IOException{ // Q - 2
		int[][] result=new int[vocabulary.length][vocabulary.length];
		BufferedReader f1 = new BufferedReader(new FileReader(fileName));
		String currentLine=f1.readLine();
		while(currentLine!=null)
		{
			String[] arr=currentLine.split(" ");
			for(int i=0;i<arr.length-1;i++)
			{
				String str1=arr[i].toLowerCase();
				String str2=arr[i+1].toLowerCase();
				if(CheckInt(str1))
				{
					str1=SOME_NUM;
				}
				if(CheckInt(str2))
				{
					str2=SOME_NUM;
				}
				int index1=getindex(vocabulary,str1);
				int index2=getindex(vocabulary,str2);
				if(index1!=-1 && index2!=-1)
				{
					result[index1][index2]+=1;
				}				
			}
			currentLine=f1.readLine();
		}
		f1.close();
		return result;

	}
	
	
	/*
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: fileName is a legal file path
	 */
	public void saveModel(String fileName) throws IOException{ // Q-3
		BufferedWriter r1=new BufferedWriter(new FileWriter(fileName+VOC_FILE_SUFFIX));
		r1.write(mVocabulary.length+" words"+"\r\n");
		for(int i=0;i<mVocabulary.length;i++)
		{
			r1.write(i+","+mVocabulary[i]+"\r\n");
		}
		r1.close();
		BufferedWriter r2=new BufferedWriter(new FileWriter(fileName+COUNTS_FILE_SUFFIX));
		for(int i=0;i<mBigramCounts.length;i++)
		{
			for(int j=0;j<mBigramCounts.length;j++)
			{
				if(mBigramCounts[i][j]!=0)
				{
					r2.write(i+","+j+":"+mBigramCounts[i][j]+"\r\n");
				}
			}
			
		}
		r2.close();
	}
	
	
	
	/*
	 * @pre: fileName is a legal file path
	 */
	public void loadModel(String fileName) throws IOException{ // Q - 4
		BufferedReader f1=new BufferedReader(new FileReader(fileName+VOC_FILE_SUFFIX));
		int size = Integer.parseInt(f1.readLine().split(" ")[0]);
		int index=0;
		String line=f1.readLine();
		while(line!=null)
		{
			mVocabulary[index]=line.split(",")[1];
			index+=1;
			line=f1.readLine();
		}
		f1.close();
		BufferedReader f2=new BufferedReader(new FileReader(fileName+COUNTS_FILE_SUFFIX));
		line=f2.readLine();
		while(line!=null)
		{
			String[] arr=line.split(":");
			int index1=Integer.parseInt(String.valueOf(arr[0].charAt(0)));
			int index2=Integer.parseInt(String.valueOf(arr[0].charAt(2)));
			mBigramCounts[index1][index2]=Integer.parseInt(arr[1]);
			line=f2.readLine();
		}
		f2.close();
		
	}

	
	
	/*
	 * @pre: word is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: word is in lowercase
	 * @post: $ret = -1 if word is not in vocabulary, otherwise $ret = the index of word in vocabulary
	 */
	public int getWordIndex(String word){  // Q - 5
		for(int i=0;i<mVocabulary.length;i++)
		{
			if(mVocabulary[i].equals(word))
			{
				return i;
			}
		}
		return ELEMENT_NOT_FOUND;
	}
	
	
	
	/*
	 * @pre: word1, word2 are in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post: $ret = the count for the bigram <word1, word2>. if one of the words does not
	 * exist in the vocabulary, $ret = 0
	 */
	public int getBigramCount(String word1, String word2){ //  Q - 6
		int index1=-1;
		int index2=-1;
		for(int i=0;i<mVocabulary.length;i++)
		{
			if(mVocabulary[i].equals(word1))
			{
				index1=i;
			}
			if(mVocabulary[i].equals(word2))
			{
				index2=i;
			}
	
		}
		if(index1==-1 || index2==-1)
		{
			return 0;
		}
		
		
		
		return mBigramCounts[index1][index2];
	}
	
	
	/*
	 * @pre word in lowercase, and is in mVocabulary
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post $ret = the word with the lowest vocabulary index that appears most fequently after word (if a bigram starting with
	 * word was never seen, $ret will be null
	 */
	public int getindex(String[] arr,String word)
	{
		int index=-1;
		for(int i=0;i<arr.length;i++)
		{
			if(arr[i]!=null)
			{
				if (arr[i].equals(word))
				{
					index=i;
				}
			}
		}
		return index;
	}
	
	public String getMostFrequentProceeding(String word){ //  Q - 7
		int index1=getindex(mVocabulary,word);
		int max=0;
		int current=-1;
		for(int i=0;i<mBigramCounts.length;i++)
		{
			if(mBigramCounts[index1][i]>max)
			{
				max=mBigramCounts[index1][i];
				current =i;
			}	
		}
		if(max==0)
		{
			return null;
		}
		return mVocabulary[current];
	}
	
	
	/* @pre: sentence is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: each two words in the sentence are are separated with a single space
	 * @post: if sentence is is probable, according to the model, $ret = true, else, $ret = false
	 */
	public boolean isLegalSentence(String sentence){  //  Q - 8
		String [] arr =sentence.split(" ");
		if(arr.length==0)
		{
			return true;
		}
		if(arr.length==1)
		{
			int index=getindex(mVocabulary,arr[0]);
			return index!=-1;
		}
		for(int i=0;i<arr.length-1;i++)
		{
			int index1=getindex(mVocabulary,arr[i]);
			int index2=getindex(mVocabulary,arr[i+1]);
			if(index1==-1 || index2==-1)
			{
				return false;
			}
			if(mBigramCounts[index1][index2]==0)
			{
				return false;
			}
			
		}
		return true;
	}
	
	
	
	/*
	 * @pre: arr1.length = arr2.legnth
	 * post if arr1 or arr2 are only filled with zeros, $ret = -1, otherwise calcluates CosineSim
	 */
	public static double calcCosineSim(int[] arr1, int[] arr2){ //  Q - 9
		double total=0;
		double sum1=0;
		double sum2=0;
		int check1=0;
		int check2=0;
		for(int i=0;i<arr1.length;i++)
		{
			if(arr1[i]!=0)
			{
				check1=1;
			}
				
			if(arr2[i]!=0)
			{
				check2=1;
			}
		}
		if(check1==0 || check2==0)
		{
			return -1;
		}
		for(int i=0;i<arr1.length;i++)
		{
			total+=(arr1[i]*arr2[i]);
			sum1+=Math.pow(arr1[i], 2);
			sum2+=Math.pow(arr2[i], 2);
		}
		return total/Math.sqrt(sum1*sum2);
	}

	/*
	 * @pre: word is in vocabulary
	 * @pre: the method initModel was called (the language model is initialized), 
	 * @post: $ret = w implies that w is the word with the largest cosineSimilarity(vector for word, vector for w) among all the
	 * other words in vocabulary
	 */
	public String getClosestWord(String word){ //  Q - 10
		if(mVocabulary.length==1)
		{
			return word;
		}
		int index=getWordIndex(word);
		double max=-1;
		int MaxIndex=-1;
		int[] arr=new int[mVocabulary.length];
		for(int i=0;i<mBigramCounts.length;i++)
		{
			arr[i]=mBigramCounts[index][i];
		}
		for(int i=0;i<mVocabulary.length;i++)
		{
			if(i!=index)
			{
				int[] current=new int [mVocabulary.length];
				for(int j=0;j<mBigramCounts.length;j++)
				{
					current[j]=mBigramCounts[i][j];
				}
				double sum=calcCosineSim(arr,current);
				if(sum>max)
				{
					max=sum;
					MaxIndex=i;
				}		
				
			}
		}
			return mVocabulary[MaxIndex];	
	}
	
}
	

