// CS 0401 Fall 2014 
// This program will test your WordServer class.  You must use this code as is, and
// your output should be a pseudo-random sequence of the words in each file.  Your
// output does not have to match mine exactly (since it should be pseudo-random) but
// the results for each run should NOT necessarily be the same.
/////////////////////////////////////////////
// Andrew Zundel  >> CS401 >> Assignment 3 //
/////////////////////////////////////////////


import java.util.*;
import java.io.*;

public class WordServerTest
{
	public static void main(String [] args) throws IOException
	{
		System.out.println("Testing with a3words.txt:");
		testWords("a3words.txt");
		System.out.println();
		System.out.println("Testing with a3words.txt:");
		testWords("a3words.txt");
		System.out.println();
		System.out.println("Testing with testwords.txt:");
		testWords("testwords.txt");
		System.out.println();
		System.out.println("Testing with testwords.txt:");
		testWords("testwords.txt");
	}
	
	public static void testWords(String fName) throws IOException
	{
		WordServer ws = new WordServer();
		String word;
		Scanner fScan = new Scanner(new FileInputStream(fName));
		ws.loadWords(fScan);

		word = ws.getNextWord();
		while (word != "") 
		{
			word = word.toUpperCase();
			System.out.println("The next word is: " + word);
			word = ws.getNextWord();
		}
	}
}