/**
*
* HighScore.java - Class object to represent a High Score
*
* @author Brian Etzel
* @version 1.0
*/
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class HighScore implements Serializable, Comparable<HighScore>
{
	private String name;
	private int score;
	
	/**
	* Constructs a high score
	*
	* @param _name - name of the scoreholder
	* @param _score - the score
	*/
	public HighScore(String _name, int _score)
	{
		this.name = _name;
		this.score = _score;
	}
	
	/**
	* Sets the value of score
	*
	* @param newScore - the new score to be updated
	*/
	public void setScore(int newScore)
	{
		this.score = newScore;
	}
	
	/**
	* Sets the value of the name
	*
	* @param newName - the new name to be updated
	*/
	public void setName(String newName)
	{
		this.name = newName;
	}
	
	/**
	* Returns the name as a string
	*/
	public String getName()
	{
		return this.name;
	}
	
	/**
	* Returns the score as an integer
	*/
	public int getScore()
	{
		return this.score;
	}
	
	/**
	* Compares the values of HighScore instances by score
	*
	* @param secondScore - the object instance to be compared against
	* @return integer value representing the size compared
	*/
	public int compareTo(HighScore secondScore)
	{
		int compared = 0;
		if (this.score > secondScore.getScore()){
			compared = 1;
		}
		else if (this.score < secondScore.getScore()){
			compared = -1;
		}
		
		return compared;
	}
	/**
	* Formats the object for printing to the screen
	*
	* @return formatted string
	*/
	public String toString()
	{
		String output = "";
		output += "\n" + this.name + ": " + this.score;
		
		return output;
	}
}