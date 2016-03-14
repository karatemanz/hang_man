/////////////////////////////////////////////
// Andrew Zundel  >> CS401 >> Assignment 3 //
/////////////////////////////////////////////

// Stores each players from file inside of a convenient ArrayList to be manipulated //
// by adding and updating the players. Also saves and searches players to           //
// check for certain conditions.                                                    //


import java.util.*;
import java.io.*;

public class HangPlayer{
	
	// instance variables for the characteristics of the players and storage //
	private Scanner fplayers;
	private String name;
	private int win;
	private int loss;
	private int curPIndex;
	private ArrayList<HangPlayer> P;
	private boolean status = true;
	
	public HangPlayer(String n, int w, int l){
		
		// construction of players //
		name = n;
		win = w;
		loss = l;
		
	}public HangPlayer(){
		
		// default constructor //
		
	}public void loadPlayers() throws IOException{
		
		// creates a list of hang player artists //
		P = new ArrayList<HangPlayer>();
		fplayers = new Scanner(new File("players.txt"));
		
			// reads from file if info to read in //
			while(fplayers.hasNextLine()){
		
				// gathers players to store inside the list //
				name = fplayers.nextLine();
				win = Integer.parseInt(fplayers.nextLine());
				loss = Integer.parseInt(fplayers.nextLine());
				// creates a player object to stand for each set of variables //
				HangPlayer player = new HangPlayer(name, win, loss);
				// stores all of the players in an array list //
				P.add(player);
				status = true;
		
			}
		
		// closes file //
		fplayers.close();

	// searches for a previous player and outputs result //	
	}public boolean searchPlayer(String user){
		
		// checks each player in the array list //
		for(int i = 0; i < P.size(); i++){
			
			// makes a current player model and fetches player//
			HangPlayer currentP;
			currentP = P.get(i);
			// gets the name of current player //
			name = currentP.getName();
			
			// tests name of current player to the users entry //
			if(name.toUpperCase().equals(user.toUpperCase())){
				
				// welcomes previous player and gives them stats //
				System.out.println("Welcome back " + name + ", here are your previous results!");
				System.out.println(currentP.toString());
				win = currentP.getWins();
				loss = currentP.getLose();
				curPIndex = i;
				status = true;
				// returns true value //
				return true;
		
			}
		
			
		}
		// if no such player was found //
		if(!(name.toUpperCase().equals(user.toUpperCase()))){
				
				// welcomes them //
				System.out.println("Welcome " + user + " to Hangman!");
				
			}
		// returns false value (not found)//
		return false;
		
	// takes the players and save any new players to the file //	
	}public void save(HangPlayer AddPlay) throws IOException{
		
		// adds new player to list //
		P.add(AddPlay);
		// opens file to write to //
		PrintWriter saving = new PrintWriter(new File("players.txt"));
		
			// loops all(each) value of P //
			for(HangPlayer player : P){
				
				// prints each player to the file (with file format)//
				saving.println(player.toFile());
				
			}
		
		// closes the file //	
		saving.close();
		
	// updates the file with previous player data on it //	
	}public void update(HangPlayer RePlay) throws IOException{
		
		// sets the currentPlayer (search player outcome) to and reference RePlay that has //
		// updated information of the player                                               //
		P.set(curPIndex, RePlay);
		// opens the file to write to it //
		PrintWriter updating = new PrintWriter(new File("players.txt"));
		
			// loops all(each) values of P //
			for(HangPlayer player : P){
				
				// prints the updated information to the file //
				updating.println(player.toFile());
				
			}
			
		// closes the file //
		updating.close();
		
	// the following communicate as accessor to the HangPlayer's //	
	}public boolean getStatus(){
		
		return status;
		
	}public String getName(){
		
		return name;
	
	}public int getWins(){
		
		return win;
		
	}public int getLose(){
		
		return loss;
		
	}public String toString(){
		
		StringBuilder S = new StringBuilder();
		S.append(name);
		S.append("\nWins  : " + win);
		S.append("\nLosses: " + loss + "\n");
		return S.toString();
		
	}public String toFile(){ /// My toString2 option
		
		StringBuffer SB = new StringBuffer();
		SB.append(name + "\n");
		SB.append(win + "\n");
		SB.append(loss);
		return SB.toString();
		
	}
	////////////////////////////////////////////////////////////////
}
