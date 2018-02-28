/**
*
* HighScoreList.java - GamePanel class for Project 05
*
* @author Brian Etzel
* @version 1.0
*/
import java.util.LinkedList;
import java.io.*;

@SuppressWarnings("unchecked")
public class HighScoreList
{
	private LinkedList<HighScore>[] scoreLists;
	
	public HighScoreList()
	{
		this.scoreLists = new LinkedList[8];
		
		scoreLists[0] = new LinkedList<HighScore>();
		scoreLists[1] = new LinkedList<HighScore>();
		scoreLists[2] = new LinkedList<HighScore>();
		scoreLists[3] = new LinkedList<HighScore>();
		scoreLists[4] = new LinkedList<HighScore>();
		scoreLists[5] = new LinkedList<HighScore>();
		scoreLists[6] = new LinkedList<HighScore>();
		scoreLists[7] = new LinkedList<HighScore>();
		scoreLists[8] = new LinkedList<HighScore>();
	}
	
	/**
	* Adds the high score to the proper LinkedList
	*
	* @param highScore - the HighScore to be added
	* @parm numDisks - used to select the difficulty level for high score based on number of disks used
	*/
	public void addToList(HighScore highScore, int numDisks)
	{
		scoreLists[numDisks].add(highScore);
	}
	
	/**
	* Gets high score list to specfic difficulty of game
	*
	* @param numDisks - The number of disks the game was played with
	* @return A single LinkedList of high scores
	*/
	public LinkedList<HighScore> getScores(int numDisks)
	{
		LinkedList<HighScore> desiredList = new LinkedList<HighScore>();
		
		desiredList = scoreLists[numDisks];
		
		return desiredList;
	}
	
	/**
	* Saves a LinkedList of HighScore as a Serializable file
	*
	* @param scores - LinkedList<HighScore>
	*/
	private void save(LinkedList<HighScore> scores) throws IOException, ClassNotFoundException
	{
		FileOutputStream fos = new FileOutputStream("highscore");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(scores);
		oos.flush();
		oos.close();
	}
	
	/**
	* Loads a Serializable instance of a LinkedList<HighScore>
	*
	* @return LinkedList<HighScore>
	*/
	@SuppressWarnings("unchecked")
	private LinkedList<HighScore> load() throws IOException, ClassNotFoundException, InterruptedException
	{
		LinkedList<HighScore> scores = new LinkedList<HighScore>();
			try {
				FileInputStream fis = new FileInputStream("highscore");
				ObjectInputStream ois = new ObjectInputStream(fis);
				scores = (LinkedList<HighScore>) ois.readObject();
				fis.close();
				ois.close();
			
			} catch (Exception e) {
				//return empty scoreLists if not found
			}
		
		return scores;
	}
}