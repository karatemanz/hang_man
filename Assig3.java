/////////////////////////////////////////////
// Andrew Zundel  >> CS401 >> Assignment 3 //
/////////////////////////////////////////////

// This is the main program of the assignment that will operate the processes of the game //
// and directly talk with the player of the game. Also manages starting the game and      //
// establishing wins and losses.                                                          //

import java.util.*;
import java.io.*;

	
public class Assig3 {
	
	// Scanner for keyboard input //
	public static Scanner key = new Scanner(System.in);

	public static void main(String[] args) throws IOException{
		
		System.out.println("Please input the name of your word file: ");
		String fileName = key.nextLine();
		
		File f = new File(fileName);
		if(!(f.exists() && !f.isDirectory())){
			
			System.out.println("File was not found or doesn't exist!\nPlease relaunch and try again!");
			System.exit(0);
			
		}
		// catches an empty answers //
		if(fileName.isEmpty()){
			
			fileName = "words.txt";
			
		}
		// creates Sanner for the wordbank file and keyboard input //
		Scanner wordF = new Scanner(new FileInputStream(fileName));
		
		
		// creates variables for condition of program and some initial conditions //
		String name;
		boolean found,status;
		// creates HangPlayer object and constructs a word bank //
		HangPlayer player = new HangPlayer();
		WordServer words = new WordServer();
		int loss, win;
		
		// loads the stored players into an ArrayList and a randomized word bank //
		player.loadPlayers();
		words.loadWords(wordF);
		
			// Intro and Asks for user input of name and or quitting //
			System.out.println("Welcome to a friendly game of Hangman!");
			System.out.println("Please Enter Name (enter 'q' to quit)");
			name = key.nextLine();

			// runs if the player didn't choose to quit //
			if(!name.toUpperCase().equals("Q")){
				// searches the array for the name in the ArrayList (found old player(found) or didnt't find(!found))//
				found = player.searchPlayer(name);
				// checks the overall status of the HangPlayer class (play game if true)//
				status = player.getStatus();
		
				// if a player is entered //
				if(status){
					// finds player and plays as older player//
					if(found){
						
						// retrieves the found players stats //
						HangPlayer oldPlayer = new HangPlayer();
						win = player.getWins();
						loss = player.getLose();
						// plays game in reference to old player //
						oldPlayer = game(name, words, win, loss);
						// updates player file //
						player.update(oldPlayer);
					
					// if new player is entered //
					}else{
						
						// creates new player for user //
						HangPlayer newPlayer = new HangPlayer();
						win = 0;
						loss = 0;
						// plays game with initial values //
						newPlayer = game(name, words, win, loss);
						// adds player and saves to file //
						player.save(newPlayer);
					}
				}	
			// player quits //
			}else{
				
				System.out.println("Thank you for your time!");
				
			}	
		
	}public static HangPlayer game(String name, WordServer wordbank, int win, int loss) throws IOException{
		
		// variables that determine next word and guesses //
		boolean nextWord = true;
		int yes = 0;
		
		// simple intro to the game //
		System.out.println("::Hangman::");
		System.out.println(">>Guess letters to figure out the word.");
		System.out.println(">>You get 7 guesses per word\n");
		System.out.println();
		
		// loops for multiple words until user quits //
		while(nextWord){
			
			// makes three sb's for guess bank, new word, and model word
			StringBuilder NewWord = new StringBuilder();
			StringBuilder GuessWord = new StringBuilder();
			StringBuilder Guess = new StringBuilder();
			
			//Signals the first or next word //	
			NewWord.append(wordbank.getNextWord().toUpperCase());
			char [] word = new char[NewWord.length()];
			char [] blanks = new char[NewWord.length()];
			// variables for input manipulation //
			char guess = 0;
			int G = 3;
			String letter, input;
			
			// makes a word model for the new word with '_'(blanks) and as chars //
			for(int i = 0; i < NewWord.length(); i++){
				
				blanks[i] = '_';
				GuessWord.append(blanks[i]);
				GuessWord.setCharAt(i, blanks[i]);
				word[i] = NewWord.charAt(i);
				
			}
			
		// variables to control outcome's of the guessing // 
		boolean run = true, penalty = false;
		// establish guesses and a sentinels //
		int guesses = 7, numGuess = 0;
			// loop for running under limited guesses //
			do{
				
				// tests running variable conditions //
				run = !(GuessWord.toString().equals(NewWord.toString()));
				if(run && guesses != 0){
				
				// Beginning of guesses by the user //
				System.out.println("Your word is: " + GuessWord.toString());
				System.out.println("You have " + guesses + " guesses left!");
				System.out.println("So far you have guessed:");
				System.out.println(Guess.toString());
				System.out.println("Please make a guess:");
				System.out.println("(Enter 0 to quit this word!)");
				System.out.println("(Enter 1 to quit the game!)");
				input = key.nextLine();
				
					if(input.isEmpty()) {
					
						System.out.println("Error Invalid Input...");
						System.out.println("Quitting now! Please restart to play again!");
						System.exit(0);
						
					}
					
					
					// seperates the input variable to test different cases //
					letter = input.toUpperCase();
					guess = letter.charAt(0);
					if(letter.matches(".*\\d+.*")){
					G = Integer.parseInt(letter);
					}
				
					
				// if the user doesn't quit game or word //	
				if(G != 0 && G != 1){	
					// when a initial guess is made quitting in any way is penalized //
					penalty = true;
					// counts guesses without manipulating 'guesses' variable //
					numGuess++;
					// searches the Guess sb for words already used //
					if(Guess.indexOf(letter) == -1){
					
							// checks each guess to the word and fills in blanks if match //
							for(int i = 0; i < NewWord.length(); i++){
							
								if(guess == word[i]){
						
									blanks[i] = word[i];
									GuessWord.setCharAt(i, blanks[i]);
									// counts times letter appears //
									yes++;
								
								}
							}
							
					
								// a variable appears once or more than once //
								if(yes >= 1){
							
									System.out.println("Great! " + guess + " has appeared in the word below!");
									Guess.append(guess);
									// resets appearence variable //
									yes = 0;
							
								// a variable doesn't appear //
								}else if(yes == 0){
							
									System.out.println("Sorry but " + guess + " is not part of the word!");
									Guess.append(guess);
									--guesses;
								
								}
								
						// warning if letter is used //		
						}else{
						
						System.out.println("That letter has already been used!");
					
						}		
								
											
					}		
				
				// rechecks the running variable //
				run = !(GuessWord.toString().equals(NewWord.toString()));
				// the system becomes true and word is completed //
				if(!run){
					
					System.out.println("\nCongrats, You Got It!");
					System.out.println("The Correct Word Is " + NewWord.toString() + "\n");
						// the user has guessed the word //
						win++;
						guesses = 0;
					System.out.println("Wins: " + win + " Losses: " + loss + "\n");
				
				// the system is false and out of guesses //	
				}else if(run && guesses == 0 && penalty){
					
					System.out.println("\nThe Word Was: " + NewWord.toString());
					System.out.println("Sorry, Better Luck Next Time!\n");
						// gets to the end of guesses and doesn't get answer //
						loss++;
						guesses = 0;
					System.out.println("Wins: " + win + " Losses: " + loss + "\n");
						
				// the player quits a word during a word //	
				}else if((run && G == 0) && penalty && numGuess > 0){
					
					System.out.println("\nThe Word Was: " + NewWord.toString());
					System.out.println("Nice Try (sadly this counts as a loss)\n");
						// next word in the middle of word //
						nextWord = true;
						guesses = 0;
						// penalty //
						loss++;
					System.out.println("Wins: " + win + " Losses: " + loss + "\n");
						
				// the player quits a word before the word //	
				}else if((run && G == 0) && guesses == 7){
					
					System.out.println("\nThe Word Was: " + NewWord.toString());
					System.out.println("See you next time! Thanks For Playing!\n");
						// next word no itteration //
						nextWord = true;
						guesses = 0;
					System.out.println("Wins: " + win + " Losses: " + loss + "\n");
					
				// the player quits the game once word is started //	
				}else if((run && G == 1) && guesses < 7 && penalty){
					
					System.out.println("\nThe Word Was: " + NewWord.toString());
					System.out.println("Nice Try (sadly this counts as a loss).");
					System.out.println("See you next time! Thanks For Playing!\n");
						// quit in the middle of word //
						nextWord = false;
						guesses = 0;
						// penalty //
						loss++;
					System.out.println("Wins: " + win + " Losses: " + loss + "\n");
						
				// the player quits before the word starts //	
				}else if((run && G == 1) &&  guesses == 7){
					
					System.out.println("\nThe Word Was: " + NewWord.toString());
					System.out.println("See you next time! Thanks For Playing!");
						// quit with no itteration //
						nextWord = false;
						guesses = 0;
					System.out.println("Wins: " + win + " Losses: " + loss + "\n");
					
				}
				
				
				}	
				
			// once out of guesses or the guesses are set to 0 to kick out //	
			}while(guesses != 0);
			
		
		}
		// the data is stored in the player object to be passed for processing //
		HangPlayer played = new HangPlayer(name, win, loss);
		return played;
			
	}
}
