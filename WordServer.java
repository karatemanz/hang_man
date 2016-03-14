/////////////////////////////////////////////
// Andrew Zundel  >> CS401 >> Assignment 3 //
/////////////////////////////////////////////

// loads and randomizes the word list for the game. //


import java.util.*;
import java.io.*;


public class WordServer {

	private String [] wordList;
	private int wordIndex;
	private int I = 0;
	
	public WordServer(){
		
		
	// loads the words from the file indicated and stores them in an array //
	// then randomizes the array.                                          //	
	}public void loadWords(Scanner S) throws IOException{
		
		//gets the initial size to make the array //
		wordIndex = Integer.parseInt(S.nextLine());
		String word;
		wordList = new String[wordIndex];
		
		// stores the each of the words in the file inside of the word list array //
		for(int i = 0; i < wordIndex; i++){
			
			word = S.nextLine();
			wordList[i] = word;
			
		}
		
		// closes file when done //
		S.close();
		
		Random randomize = new Random();  // Random number generator			
		 
		// loops through the array //
		for (int i = 0; i < wordIndex; i++) {
			
			// stores a random number in the random position variable //
		    int randomPosition = randomize.nextInt(wordIndex);
		    // stores the temporary word //
		    String tempWords = wordList[i];
		    // stores a random word in the array at the original words position //
		    wordList[i] = wordList[randomPosition];
		    // replaces the random position word with the word originally stored //
		    wordList[randomPosition] = tempWords;
		       
		}
	
	// grabs the next word from the array	
	}public String getNextWord(){
		
		String randomWord = null;
		
		if(I < wordIndex){
		
			// grabs each word in the list through the entire array //
			randomWord = wordList[I];
			// a new index variable to cycle through //
			I++;
		
		}else{
			
			// if it doesn't receive a word a blank word is slotted //
			randomWord = "";
			
		}
		
		// returns the random word //
		return randomWord;
	}
	
	
	
	
	
}
