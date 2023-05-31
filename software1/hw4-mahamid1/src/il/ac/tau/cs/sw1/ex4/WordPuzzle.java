	package il.ac.tau.cs.sw1.ex4;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class WordPuzzle {
	public static final char HIDDEN_CHAR = '_';
	public static final int MAX_VOCABULARY_SIZE = 3000;

	
	/*
	 * @pre: template is legal for word
	 */
	public static char[] createPuzzleFromTemplate(String word, boolean[] template) { // Q - 1
		char [] result=new char[word.length()];
		for(int i=0;i<word.length();i++)
		{
			if(template[i]==true)
			{
				result[i]=HIDDEN_CHAR;
			}
			else
			{
				result[i]=word.charAt(i);
			}
			
		}
		return result;
	}

	public static boolean checkLegalTemplate(String word, boolean[] template) { // Q - 2
		int counter=0;
		if(word.length()!=template.length)
		{
			return false;
		}
		for(int i=0;i<template.length;i++)
		{	
			if(template[i]==true)
			{
				counter+=1;
			}
			else
			{
				counter-=1;
			}
			
		}
		if(counter==template.length || counter==(-1)*template.length)
		{
			return false;
		}
		char [] tmp=word.toCharArray();
		for(int i=0;i<template.length-1;i++)
		{
			for(int j=i+1;j<template.length;j++)
			{
				if(tmp[i]==tmp[j] && template[i]!=template[j])
				{
					return false;
				}
			}
		}
		return true;
	}
	
	/*
	 * @pre: 0 < k < word.lenght(), word.length() <= 10
	 */
	public static String convert(boolean[] arr1)//convert from boolean array to binary str
	{
		String str="";
		for(int i=0;i<arr1.length;i++)
		{
			if(arr1[i]==true)
			{
				str+='1';
			}
			else
			{
				str+='0';
			}
		}
		return str;
	}
	public static boolean distanceK(int k,boolean[] arr1, boolean []arr2)//checks if arr2 has k hidden chars
	{
		int c=k;
		for (int i =0;i<arr1.length;i++)
		{
			if(arr1[1]!=arr2[i])
			{
				c-=1;
			}
		}
		if(c==0)
		{
			return true;
		}
		return false;
	}
	
	public static boolean[][] getAllLegalTemplates(String word, int k){  // Q - 3
		int n =word.length();
		int crt=0;
		int l=0;
		boolean[] arr1=new boolean[word.length()];
		for(int i=0;i<word.length();i++)
		{
			arr1[i]=false;
		}
		 for (int i = 0; i < Math.pow(2, n); i++) {
		        String bin = Integer.toBinaryString(i);
		        while (bin.length() < n)
		            bin = "0" + bin;
		        char[] chars = bin.toCharArray();
		        boolean[] boolArray = new boolean[n];
		        for (int j = 0; j < chars.length; j++) {
		            boolArray[j] = chars[j] == '0' ;
		        }
		        if(distanceK(k,arr1,boolArray)==true && checkLegalTemplate(word,boolArray))
		        {
		        	crt+=1;
		        }      
		    }
		 boolean [][] result=new boolean[crt][word.length()];
		 for (int i = 0; i < Math.pow(2, n); i++) {
		        String bin = Integer.toBinaryString(i);
		        while (bin.length() < n)
		            bin = "0" + bin;
		        char[] chars = bin.toCharArray();
		        boolean[] boolArray = new boolean[n];
		        for (int j = 0; j < chars.length; j++) {
		            boolArray[j] = chars[j] == '0';
		        }
		        if(distanceK(k,arr1,boolArray)==true && checkLegalTemplate(word,boolArray))
		        {
		        	result[l]=boolArray;
		        	l+=1;
		        }      
		    }
		 Arrays.sort(result,(a,b)->Integer.compare(Integer.parseInt(convert(a)), Integer.parseInt(convert(b)))); 
		return result;
	}
	
	
	/*
	 * @pre: puzzle is a legal puzzle constructed from word, guess is in [a...z]	
	 */
	public static int applyGuess(char guess, String word, char[] puzzle) { // Q - 4
		int result=0;
		for(int i=0;i<word.length();i++)
		{
			if(word.charAt(i)==guess && puzzle[i]==HIDDEN_CHAR)
			{
				result+=1;
				puzzle[i]=guess;
			}
		}
		return result ;
	}
	

	/*
	 * @pre: puzzle is a legal puzzle constructed from word
	 * @pre: puzzle contains at least one hidden character. 
	 * @pre: there are at least 2 letters that don't appear in word, and the user didn't guess
	 */
	public static char alpha(int i)// returns the i`th letter in the alphabet (i<=25) (array[0]=a ) 
	{
		char [] arr= "abcdefghijklmnopqrstuvwxyz".toCharArray();
		return arr[i];
	}

	public static char[] getHint(String word, char[] puzzle, boolean[] already_guessed) { // Q - 5
		Random r =new Random();
		int first= r.nextInt(26);
		while(already_guessed[first]==true || word.indexOf(alpha(first))!=-1)
		{
			first=r.nextInt(26);
		}
		int second= r.nextInt(word.length());
		while(puzzle[second]!=HIDDEN_CHAR)
		{
			second=r.nextInt(word.length());
		}
		if(alpha(first)<word.charAt(second))
		{
			char[] arr= {alpha(first),word.charAt(second)};
			return arr;
		}
		char[] arr={word.charAt(second),alpha(first)};
		return arr;
	}

	
	public static char[] convertFromBool(String str,boolean[] arr)
	{
		char[] result=new char[str.length()];
		for(int i=0;i<arr.length;i++)
		{
			if(arr[i]==false)
			{
				result[i]=str.charAt(i);
			}
			else
			{
				result[i]=HIDDEN_CHAR;
			
			}
		}
		return result;
	}
	public static boolean[] convertFromChar(String str, char[] arr)
	{
		boolean[] tmp = new boolean[arr.length];
		for(int i =0;i<arr.length;i++)
		{
			if(arr[i]==HIDDEN_CHAR)
			{
				tmp[i]=true;
			}
			else 
			{
				tmp[i]=false;
			}
		}
		return tmp;
	}
	public static char[] mainTemplateSettings(String word, Scanner inputScanner) { // Q - 6
		printSettingsMessage();
		printSelectTemplate();
		int x = inputScanner.nextInt();
		while(true)
		{
			if(x==1) 
			{
				printSelectNumberOfHiddenChars();
				int y=inputScanner.nextInt();
				Random r =new Random();
				if(getAllLegalTemplates(word,y).length!=0)
				{
					boolean[] tmp=getAllLegalTemplates(word,y)[r.nextInt(getAllLegalTemplates(word,y).length)];
					char[] result=convertFromBool(word,tmp);
					return result;
				}
				else
				{
					printWrongTemplateParameters();
					printSelectTemplate();
				}
			}
		
			if(x==2)
			{
				printEnterPuzzleTemplate();
				String str= inputScanner.next();
				String[] arr=str.split(",");
				char[] tmp=new char[arr.length];
				for(int i=0;i<arr.length;i++)
				{
					if(arr[i].equals("X"))
					{
						tmp[i]=word.charAt(i);
					}
					else 
					{
						tmp[i]=HIDDEN_CHAR;
					}
				}
				boolean[] arr2=convertFromChar(word,tmp);
				if(checkLegalTemplate(word,arr2)==true )
				{
					return tmp;
				}
				else
				{
					printWrongTemplateParameters();
					printSelectTemplate();
				}
			
			}
			x=inputScanner.nextInt();
		}
		
	}
	public static int checkHidden(char[] puzzle)
	{
		int c=0;
		for(int i=0;i<puzzle.length;i++)
		{
			if(puzzle[i]==HIDDEN_CHAR)
			{
				c+=1;
			}
		}
		return c;
	}
	public static void mainGame(String word, char[] puzzle, Scanner inputScanner){ // Q - 7
		printGameStageMessage();
		int counter=3+checkHidden(puzzle);
		printPuzzle(puzzle);
		printEnterYourGuessMessage();
		boolean[] alphabet=new boolean[26];
		String str="abcdefghijklmnopqrstuvwxyz";
		int tmp=0;
		for(int i =0;i<26;i++)
		{
			alphabet[i]=false;
		}
		char guess=inputScanner.next().charAt(0);
		while(counter!=0)
		{

			if(guess=='H')
			{
				printHint(getHint(word,puzzle,alphabet));
				printEnterYourGuessMessage();
			}
			else
			{
				alphabet[str.indexOf(guess)]=true;
				if(str.indexOf(guess)!=-1)
				{
					int y = checkHidden(puzzle);
					int x=applyGuess(guess,word,puzzle);
					
					counter-=1;
					if(y==x && x!=0)
					{
						printWinMessage();
						tmp+=1;
						break;
					}
					else
					{
						if(x!=0) 
						{
							printCorrectGuess(counter);
							printPuzzle(puzzle);
							printEnterYourGuessMessage();
						}
						else 
						{
							printWrongGuess(counter);
							printPuzzle(puzzle);
							printEnterYourGuessMessage();
						}
							
					}
				}
			}
			 guess=inputScanner.next().charAt(0);

		}
		if(tmp==0)
		{
			printGameOver();	
		}
	}
				
				


/*************************************************************/
/********************* Don't change this ********************/
/*************************************************************/

	public static void main(String[] args) throws Exception { 
		if (args.length < 1){
			throw new Exception("You must specify one argument to this program");
		}
		String wordForPuzzle = args[0].toLowerCase();
		if (wordForPuzzle.length() > 10){
			throw new Exception("The word should not contain more than 10 characters");
		}
		Scanner inputScanner = new Scanner(System.in);
		char[] puzzle = mainTemplateSettings(wordForPuzzle, inputScanner);
		mainGame(wordForPuzzle, puzzle, inputScanner);
		inputScanner.close();
	}


	public static void printSettingsMessage() {
		System.out.println("--- Settings stage ---");
	}

	public static void printEnterWord() {
		System.out.println("Enter word:");
	}
	
	public static void printSelectNumberOfHiddenChars(){
		System.out.println("Enter number of hidden characters:");
	}
	public static void printSelectTemplate() {
		System.out.println("Choose a (1) random or (2) manual template:");
	}
	
	public static void printWrongTemplateParameters() {
		System.out.println("Cannot generate puzzle, try again.");
	}
	
	public static void printEnterPuzzleTemplate() {
		System.out.println("Enter your puzzle template:");
	}


	public static void printPuzzle(char[] puzzle) {
		System.out.println(puzzle);
	}


	public static void printGameStageMessage() {
		System.out.println("--- Game stage ---");
	}

	public static void printEnterYourGuessMessage() {
		System.out.println("Enter your guess:");
	}

	public static void printHint(char[] hist){
		System.out.println(String.format("Here's a hint for you: choose either %s or %s.", hist[0] ,hist[1]));

	}
	public static void printCorrectGuess(int attemptsNum) {
		System.out.println("Correct Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWrongGuess(int attemptsNum) {
		System.out.println("Wrong Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWinMessage() {
		System.out.println("Congratulations! You solved the puzzle!");
	}

	public static void printGameOver() {
		System.out.println("Game over!");
	}

}
