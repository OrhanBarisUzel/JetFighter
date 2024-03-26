

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DataManager{
	// Variables
	private FileInputStream fileReader;
	private FileWriter FileWriter;
	private String FileName = "Player.txt";
	private DataInputStream DataInputStream;
	private BufferedReader BReader;
	private String tempString;
	private int numberOfUser;
	private Player tempPlayer;
	
	public DataManager(){
		 initializeManager();
	}
	
	public void initializeManager(){
		try{
			if(!Paths.get(FileName).toFile().exists()){				
				// If file doesn't exist create new file
				FileWriter = new FileWriter(FileName);
			}
			else{
				FileWriter = new FileWriter(FileName, true);
			}
			
			// File Read
			fileReader = new FileInputStream(FileName);
			DataInputStream = new DataInputStream(fileReader);
			BReader = new BufferedReader(new InputStreamReader(DataInputStream));

		}
		catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
	}
	
	public void close(){
		try {
			BReader.close();
			DataInputStream.close();
			fileReader.close();
			FileWriter.flush();
			FileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean registerUser(String name, String pass){
		if(checkUserIsExist(name,pass)){
			String tempStr = numberOfUser + " " + name + " " + pass + " 0\n";
			System.out.println(tempStr);
			try {
				FileWriter.write(tempStr);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			close();
			return true;
		}
		else{
			close();
			return false;			
		}
	}
	
	public boolean checkUserIsExist(String name, String password){
		numberOfUser = 0;
		try {
			while((tempString = BReader.readLine()) != null){
				String[] Array = tempString.split(" ");
				if(Array[1].equals(name) && Array[2].equals(password)){
					return false;
				}
				numberOfUser++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	

	
	public void updateScore(String name, String score){
		try {
			String writeString = "";
			ArrayList<String> playerStringList = new ArrayList<String>();
			
			while((tempString = BReader.readLine()) != null){
				String[] Array = tempString.split(" ");
				if(Array[1].equals(name)){
					if(Integer.parseInt(Array[3])< Integer.parseInt(score)){
						// If current score higher than old score update with new record
						writeString = Array[0] + " " + Array[1] + " " + Array[2] + " " + score;						
					}
					else{
						// Keep old score
						writeString = Array[0] + " " + Array[1] + " " + Array[2] + " " + Array[3];
					}
				}
				else{
					writeString = tempString;
				}
				playerStringList.add(writeString);
			}
			close();
			FileWriter = new FileWriter(FileName);
			for(int i=0; i<playerStringList.size(); i++){
				FileWriter.write(playerStringList.get(i) + "\n");
			}			
			close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	public Player getPlayer(String name, String pass){
		try {
			while((tempString = BReader.readLine()) != null){
				String[] sArr = tempString.split(" ");
				if(sArr[1].equals(name) && sArr[2].equals(pass)){
					tempPlayer = new Player(Integer.parseInt(sArr[0]),sArr[1], sArr[2], Integer.parseInt(sArr[3]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		close();
		return tempPlayer;
	}
	
	public ArrayList<Player> getHighScores(){
		ArrayList<Player> playersScores = new ArrayList<Player>();
		ArrayList<Player> playerHighScores = new ArrayList<Player>();
		try {
			while((tempString = BReader.readLine()) != null){
				String[] sArr = tempString.split(" ");
				playersScores.add(new Player(Integer.parseInt(sArr[0]),sArr[1], sArr[2], Integer.parseInt(sArr[3])));
			}
			
			for(int i=0; i<3; i++){
				// Find maximum score
				int max = 0; // Index number of the high score
				for(int u=1; u<playersScores.size(); u++){
					if(playersScores.get(u).getScore() > playersScores.get(max).getScore()){
						max = u;
					}
				}
				playerHighScores.add(playersScores.get(max));
				playersScores.remove(max);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		close();
		System.out.println(playerHighScores);
		return playerHighScores;
	}


	
}