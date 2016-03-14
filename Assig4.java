import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.naming.NameNotFoundException;
import javax.swing.*;

public class Assig4{
	
	// creates instance variables for access over the GUI
	private HangPlayer player;
	private WordServer wordList;
	// variables that create the GUI
	private JFrame Window;
	private infoPanelType infoPanel;
	private displayPanelType displayPanel;
	private runPanelType runPanel;
	// more instance variables
	private String user;
	private int win, loss, guesses = 7, toggle = 0;
	private StringBuilder GuessWord, Guess, NewWord;
	private char [] wordPiece;
	private char [] blanks;
	private boolean run;
	private int numGuess;
	
	public Assig4() throws IOException{
		
		// creates three panels that breaks the GUI into sections
		infoPanel = new infoPanelType();
		displayPanel = new displayPanelType();
		runPanel = new runPanelType();
		
		// adds sections to the Window and sets some starting characteristics to the Window
		Window = new JFrame("The Hangman Game");
		Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Window.setSize(875,350);
		//assigns layouts of the panels
		Window.add(infoPanel, BorderLayout.WEST);
		Window.add(displayPanel, BorderLayout.CENTER);
		Window.add(runPanel, BorderLayout.EAST);
		Window.setVisible(true);
		
	}
	 
class infoPanelType extends JPanel{
	
	// static and dynamic top variables //
	private JLabel statusLabel, infoLabel, guessesLabel;
	private JLabel status, info, guessesLeft;
	// static and dynamic bottom variables //
	private JLabel wordLabel, guessLabel;
	private JLabel word, guessCurrent;
	// text insert //
	private JLabel myGLabel;
	private JTextField myGuess;
	
	
	
	public infoPanelType() throws IOException{
	
		// Sets the layout of the 1st panel and determines initial state of panel
		setLayout(new GridLayout(6,2));
		statusLabel = new JLabel("Game Status:");
		status = new JLabel("Game Not Started!");
		infoLabel = new JLabel("Game Info:");
		info = new JLabel("Welcome To Hangman!");
		wordLabel = new JLabel("Current Word:");
		word = new JLabel("");
		guessLabel = new JLabel("Guess:");
		guessCurrent = new JLabel("");
		myGLabel = new JLabel("Enter Guess:");
		myGuess = new JTextField();
			myGuess.setEnabled(false);
		guessesLabel = new JLabel("Guesses Left:");
		guessesLeft = new JLabel("");
		StatusListener statListen = new StatusListener();
			
		myGuess.addActionListener(statListen);
		
		//adds the labels and textfields to the panel
		add(statusLabel);
		add(status);
		add(infoLabel);
		add(info);
		add(wordLabel);
		add(word);
		add(guessLabel);
		add(guessCurrent);
		add(myGLabel);
		add(myGuess);
		add(guessesLabel);
		add(guessesLeft);
		
	}
	private class StatusListener implements ActionListener{
		
		// listens for input in the JText feild and processes
		public void actionPerformed(ActionEvent e){
			
			if(e.getSource() == myGuess){
				
				int yes = 0;
				
				// variables for input manipulation //
				char guess = 0;
				String letter, input;
				
				// accepts the input from the text field
				input = myGuess.getText();
				numGuess++;
				
					// manipulates the guess for processing
					letter = input.toUpperCase();
					guess = letter.charAt(0);
					
					// keeps track of the number of guesses and sets a change in state
					if(numGuess > 0){
						
						toggle = 3;
						runPanel.wordButton.setText("Next Word");
						
					}
					
					
					//processes the Guess
					if(Guess.indexOf(letter) == -1){
						
						// checks each guess to the word and fills in blanks if match //
						for(int i = 0; i < NewWord.length(); i++){
						
							// checks if the guess is a part of the word
							if(guess == wordPiece[i]){
					
								blanks[i] = wordPiece[i];
								GuessWord.setCharAt(i, blanks[i]);
								// counts times letter appears //
								yes++;
							
							}
						}
						
				
							// a variable appears once or more than once //
							if(yes >= 1){
						
								//changes the status update of GUI
								info.setText("Great! " + guess + " has appeared!");
								Guess.append(guess);
								// resets appearence variable //
								yes = 0;
								
								//updates the GUI
								guessCurrent.setText(Guess.toString());
								word.setText(GuessWord.toString());
								myGuess.setText("");
								displayPanel.updateDisplay();
								
							// a variable doesn't appear //
							}else if(yes == 0){
						
								// changes status update of GUI
								info.setText("Sorry but " + guess + " isn't in the word!");
								Guess.append(guess);
								--guesses;
								
								//updates the GUI
								guessesLeft.setText("" + guesses);
								guessCurrent.setText(Guess.toString());
								myGuess.setText("");
								displayPanel.updateDisplay();
										
							}
							
					// warning if letter is used //		
					}else{
					
						info.setText("The letter " + guess + " has already been used!");
						myGuess.setText("");
						
					}	
					
						// variable to test if the player has guessed the correct answer
						run = !(GuessWord.toString().equals(NewWord.toString()));
					
							// if they got it right
							if(!run){
								
								status.setText("Game Over");
								info.setText("Congrats, You Got It!");
								myGuess.setEnabled(false);
									// the user has guessed the word //
									win++;
									guesses = 7;
								toggle = 2;
								runPanel.wordButton.setText("New Word");
									
							}else if(run && guesses == 0){
								
								info.setText("Better Luck Next Time! The Word Was:");
								word.setText(NewWord.toString());
								myGuess.setEnabled(false);
									// gets to the end of guesses and doesn't get answer //
									loss++;
									guesses = 7;
								toggle = 2;
								runPanel.wordButton.setText("New Word");
									
							}
				
			}
			
			
			
		} // actionPerformed
		
	} // StatusListener 
	
} // infoPanelType
class displayPanelType extends JPanel{
	
	// instance variables for the panel
	private JLabel display;
	private ImageIcon hangfigure;
	
	public displayPanelType() throws IOException{
		
		// creates an initial size of the panel and layout
		setSize(80,80);
		setLayout(new FlowLayout());
		display = new JLabel();
		
		// stores a default image into the hangfigure ImageIcon object
		hangfigure = new ImageIcon("default.gif");
		// holds the ImageIcon's image and positions it
		display = new JLabel(hangfigure, JLabel.CENTER);
		
		// adds the object to the screen
		add(display);
		
	}public void updateDisplay(){
	
		// the guesses determine which image is shown and each image contains different gif's of the
		// hangman
		if(guesses == 7){
			
			hangfigure = new ImageIcon("default.gif");
		
		}else if(guesses == 6){
			// adds head element
			hangfigure = new ImageIcon("head.gif");
			
		}else if(guesses == 5){
			// adds body element
			hangfigure = new ImageIcon("body.gif");
			
		}else if(guesses == 4){
			// adds right arm element
			hangfigure = new ImageIcon("rightarm.gif");
			
		}else if(guesses == 3){
			// adds left arm element
			hangfigure = new ImageIcon("leftarm.gif");
			
		}else if(guesses == 2){
			// adds right leg element
			hangfigure = new ImageIcon("rightleg.gif");
			
		}else if(guesses == 1){
			// adds left leg element
			hangfigure = new ImageIcon("leftleg.gif");
			
		}else if(guesses == 0){
			// add hanged element
			hangfigure = new ImageIcon("finished.gif");
			
		}
		
		// sets the image to the new display when update is called
		display.setIcon(hangfigure);
		
	}
	
	
} // displayPanelType
class runPanelType extends JPanel{
	
	// instance variables for the panel
	private JButton playerButton, gameButton, wordButton;
	private String words;
	private boolean found;
	
	public runPanelType() throws IOException{
		
		// sets layout and initial condition of the game buttons
		setLayout(new GridLayout(3,1));
		playerButton = new JButton("Start Game");
		gameButton = new JButton("End Game");
			gameButton.setEnabled(false);
		wordButton = new JButton("New Word");
			wordButton.setEnabled(false);
		ControlListener Controller = new ControlListener();
		
		// adds the listener to the button
		playerButton.addActionListener(Controller);
		gameButton.addActionListener(Controller);
		wordButton.addActionListener(Controller);
		
		// adds the buttons to the panel
		add(playerButton);
		add(gameButton);
		add(wordButton);	
		
	}
	private class ControlListener implements ActionListener{
		
			public void actionPerformed(ActionEvent e){
				
				if(e.getSource() == playerButton){
					
					// start game state //
					if(toggle == 0){
					
						toggle = 1;	// next player state
						Scanner wordBank = null;
						player = new HangPlayer();
						wordList = new WordServer();
						
						// asks for word file 
						words = (String)JOptionPane.showInputDialog(null, "Input Word Bank File (.txt): ", "text file with words");
							
								// nested try-catch for file input ////////////////////////////////////
								try{
									wordBank = new Scanner(new FileInputStream(words));
								}catch(FileNotFoundException fnf){
									
									// defaults to default word file words.txt 
									try{
										
										wordBank = new Scanner(new FileInputStream("words.txt"));
										
									}catch(FileNotFoundException fnf2){}
									
										JOptionPane.showMessageDialog(null, "A default word list has been used!", "WORDBANK", JOptionPane.PLAIN_MESSAGE);

								}
								//////////////////////////////////////////////////// end nested try-catch
								try{
									wordList.loadWords(wordBank);
								}catch(IOException ex){}
							
						// sets button text		
						playerButton.setText("Next Player");
						infoPanel.status.setText("Ready for Player");
						gameButton.setEnabled(true);
					
					// next player state //	
					}else if(toggle == 1){
						
						toggle = 2;
						JOptionPane.showMessageDialog(null, "Loading Players....", "Player Status", JOptionPane.PLAIN_MESSAGE);
						
								try{
									player.loadPlayers();
								}catch(IOException ex){}
							
						user = (String)JOptionPane.showInputDialog(null, "Login Here Please: ", "LOGIN");
						
						// tests for a boolean value for the user
						found = player.searchPlayer(user);
						infoPanel.status.setText("Game Ready");
						playerButton.setEnabled(false);
						gameButton.setText("End Player");
						wordButton.setEnabled(true);
							
						if(found){
						
							infoPanel.info.setText("Welcome Back " + user);
							// retrieves the found players stats //
							HangPlayer oldPlayer = new HangPlayer();
							win = player.getWins();
							loss = player.getLose();
							
						}else{
							
							infoPanel.info.setText("Welcome " + user);
							// creates new player for user //
							HangPlayer newPlayer = new HangPlayer();
							win = 0;
							loss = 0;

						}
						
							
					}
					
					
				}else if(e.getSource() == gameButton){
					
					// end game state //
					if(toggle == 1){
						
						//asks the save and quit option
						int option = JOptionPane.showConfirmDialog(null, "Would You Like To Save and Quit?", "QUIT", JOptionPane.YES_NO_OPTION);
						
						// dictates what executes on users choice
						if(JOptionPane.YES_OPTION == option){
							
							JOptionPane.showMessageDialog(null, "Come Back Soon!", "PROMPT", JOptionPane.PLAIN_MESSAGE);
							System.exit(0);
							
						}else{
							
							JOptionPane.showMessageDialog(null, "Okey dokey then!!", "PROMPT", JOptionPane.PLAIN_MESSAGE);
							
						}
						
					// end player state //
					}else if(toggle == 2 || toggle == 3){
						
						int option2 = -1, option4 = -1;
						
						if(toggle == 2){
							
							option2 = JOptionPane.showConfirmDialog(null, "Would you really like to quit " + user + "?", "PROMPT", JOptionPane.YES_NO_OPTION);
								
								
						}else if(toggle == 3){
							
							option4 = JOptionPane.showConfirmDialog(null, "Quitting here counts as a loss?", "PROMPT", JOptionPane.YES_NO_OPTION);
							
						}
								
						if(JOptionPane.YES_OPTION == option2){
							
								toggle = 1;
								gameButton.setText("End Game");
								playerButton.setEnabled(true);
								wordButton.setEnabled(false);
								displayPanel.updateDisplay();
							
							if(found){
								
								HangPlayer oldPlayer = new HangPlayer(user, win, loss);
								try{
									player.update(oldPlayer);
								}catch(IOException ex){}
								
								JOptionPane.showMessageDialog(null, oldPlayer.toString(), "PROMPT", JOptionPane.PLAIN_MESSAGE);
								
							}else{
								HangPlayer newPlayer = new HangPlayer(user, win, loss);
								try{
									player.save(newPlayer);
								}catch(IOException ex){}
								
								JOptionPane.showMessageDialog(null, newPlayer.toString(), "PROMPT", JOptionPane.PLAIN_MESSAGE);
							}
							
						}else if(JOptionPane.YES_OPTION == option4){
							
							loss++;
							toggle = 1;
							gameButton.setText("End Game");
							playerButton.setEnabled(true);
							wordButton.setEnabled(false);
							
							
						}else{
							
							JOptionPane.showMessageDialog(null, "Okey dokey then!!", "PROMPT", JOptionPane.PLAIN_MESSAGE);
							
						}
						
						
					}
					
					
				}else if(e.getSource() == wordButton){
					
						
						// makes three sb's for guess bank, new word, and model word
						NewWord = new StringBuilder();
						GuessWord = new StringBuilder();
						Guess = new StringBuilder();

						//Signals the first or next word //	
						NewWord.append(wordList.getNextWord().toUpperCase());
						
						wordPiece = new char[NewWord.length()];
						blanks = new char[NewWord.length()];
						
						
						// makes a word model for the new word with '_'(blanks) and as chars //
						for(int i = 0; i < NewWord.length(); i++){
							
							blanks[i] = '_';
							GuessWord.append(blanks[i]);
							GuessWord.setCharAt(i, blanks[i]);
							wordPiece[i] = NewWord.charAt(i);
							
						}
						
						
					// new word state //
					if(toggle == 2){
					
						// sets the conditions //
						
						infoPanel.status.setText("Game In Progress");
						infoPanel.myGuess.setEnabled(true);
						infoPanel.guessesLeft.setEnabled(true);
						infoPanel.info.setText("Here is your word!");
						
						//outputs word model //
						infoPanel.word.setText(GuessWord.toString());
						infoPanel.guessesLeft.setText("" + guesses);
						
					}else if(toggle == 3){
						
						// warning if user moves to next word mid-word
						int option3 = JOptionPane.showConfirmDialog(null, "Moving to the next word will count as a loss!", "PROMPT", JOptionPane.YES_NO_OPTION);
						if(JOptionPane.YES_OPTION == option3){
							
							// handles accordingly to the yes option
							guesses = 7;
							loss++;
							displayPanel.updateDisplay();
							infoPanel.guessCurrent.setText("");
							
							// sets the conditions //
							infoPanel.status.setText("Game In Progress");
							infoPanel.myGuess.setEnabled(true);
							infoPanel.guessesLeft.setEnabled(true);
							infoPanel.info.setText("Here is your word!");
							
							//outputs word model //
							infoPanel.word.setText(GuessWord.toString());
							infoPanel.guessesLeft.setText("" + guesses);
							
						}
						
						//NO option has no implications, or state changes. So the game continues.
						
					}
					
				}
						
			} // actionP
		
		} // ControlListener
		
	} // runPanelType
	


	//single method call for the main //
	public static void main(String[] args) throws IOException{
	
		new Assig4();
	
	}
	
} // Assign4