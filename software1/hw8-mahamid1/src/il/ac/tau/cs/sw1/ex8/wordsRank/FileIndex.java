package il.ac.tau.cs.sw1.ex8.wordsRank;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import il.ac.tau.cs.sw1.ex8.histogram.HashMapHistogram;
import il.ac.tau.cs.sw1.ex8.histogram.IHistogram;
import il.ac.tau.cs.sw1.ex8.wordsRank.RankedWord.rankType;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class FileIndex {
	
	public static final int UNRANKED_CONST = 30;
	private HashMap<String,HashMapHistogram<String>> currentFile;
	
	

	/*
	 * @pre: the directory is no empty, and contains only readable text files
	 */
  	public void indexDirectory(String folderPath) {
		//This code iterates over all the files in the folder. add your code wherever is needed
  		List<String> listOfWords;
		File folder = new File(folderPath);
		File[] listFiles = folder.listFiles();
		HashMap<String,HashMapHistogram<String>> current=new HashMap<>();
		for (File file : listFiles) {
			// for every file in the folder
			
			if (file.isFile()) {
				try {
					listOfWords=FileUtils.readAllTokens(file);
					HashMapHistogram<String> tmp=new HashMapHistogram<String>();
					tmp.addAll(listOfWords);
					current.put(file.getName(),tmp);
				}
				catch(Exception e)
				{
					
				}
			}
		}
		this.currentFile=current;
	}
	
  	/*
	 * @pre: the index is initialized
	 * @pre filename is a name of a valid file
	 * @pre word is not null
	 */
	public int getCountInFile(String filename, String word) throws FileIndexException{
		if(this.currentFile.containsKey(filename))
		{
			return this.currentFile.get(filename).getCountForItem(word.toLowerCase());
		}
		throw new FileIndexException(filename);
	}
	
	/*
	 * @pre: the index is initialized
	 * @pre filename is a name of a valid file
	 * @pre word is not null
	 */
	public int getRankForWordInFile(String filename, String word) throws FileIndexException{
		int i=0;
		try {
			if(this.currentFile.containsKey(filename))
			{
				for(String str:this.currentFile.get(filename))
				{
					if(str.equals(word))
						return i+1;
					i+=1;
				}
			}
			else
			{
				return this.currentFile.get(filename).getItemsSet().size()+ UNRANKED_CONST;
			}
		}
		catch(Exception e)
		{
			throw new FileIndexException(filename);
		}
		return i;
	}
	
	/*
	 * @pre: the index is initialized
	 * @pre word is not null
	 */
	public int getAverageRankForWord(String word){
		HashMap<String,Integer> mapforword=new HashMap<>();
		for(String filename:this.currentFile.keySet())
		{
			try {
				mapforword.put(filename,this.getRankForWordInFile(filename, word));
			}
			catch(FileIndexException e)
			{
			}
		}
		RankedWord R1=new RankedWord(word,mapforword);
		return R1.getRankByType(RankedWord.rankType.average);
	}
	
	
	public List<String> getWordsWithAverageRankSmallerThanK(int k){
		List<String> result = new ArrayList<String>();
		for(String file:currentFile.keySet())
		{
			for(String word: currentFile.get(file).getItemsSet())
			{
				if(this.getAverageRankForWord(word)<k)
				{
					result.add(word);
				}
			}
		}
		return result; 
	}
	public int getminRankForWord(String word){
		HashMap<String,Integer> mapforword=new HashMap<>();
		for(String filename:this.currentFile.keySet())
		{
			try {
				mapforword.put(filename,this.getRankForWordInFile(filename, word));
			}
			catch(FileIndexException e)
			{
			}
		}
		RankedWord R1=new RankedWord(word,mapforword);
		return R1.getRankByType(RankedWord.rankType.min);
	}
	public List<String> getWordsWithMinRankSmallerThanK(int k){
		List<String> result = new ArrayList<String>();
		for(String file:currentFile.keySet())
		{
			for(String word: currentFile.get(file).getItemsSet())
			{
				if(this.getminRankForWord(word)<k && !result.contains(word))
				{
					result.add(word);
				}
			}
		}
		return result;	
	}
	public int getmaxRankForWord(String word){
		HashMap<String,Integer> mapforword=new HashMap<>();
		for(String filename:this.currentFile.keySet())
		{
			try {
				mapforword.put(filename,this.getRankForWordInFile(filename, word));
			}
			catch(FileIndexException e)
			{
			}
		}
		RankedWord R1=new RankedWord(word,mapforword);
		return R1.getRankByType(RankedWord.rankType.max);
	}
	public List<String> getWordsWithMaxRankSmallerThanK(int k){
		List<String> result = new ArrayList<String>();
		for(String file:currentFile.keySet())
		{
			for(String word: currentFile.get(file).getItemsSet())
			{
				if(this.getmaxRankForWord(word)<k && !result.contains(word))
				{
					result.add(word);
				}
			}
		}
		return result; 
	}

}
