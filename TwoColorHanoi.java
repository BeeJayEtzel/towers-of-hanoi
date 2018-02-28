/**
*
* TwoColorHanoi.java - Two Stack implementation of Towers of Hanoi
*
* @author Brian Etzel
* @version 1.0
*/

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.*;

import java.util.Stack;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Collections;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.Timer;

@SuppressWarnings("unchecked")
public class TwoColorHanoi extends JPanel implements MouseListener, MouseMotionListener, ActionListener{
    
	
    private Stack<Rectangle> towerStack[] = new Stack[3];
    private Stack<Color> diskColor[] = new Stack[3];
    private Rectangle top;
    private Color topColor;
    private double diskXPos;
    private double diskYPos;
    private double diskWidth;
    private boolean draggable;
	
	private int clickedTower;
	private int releasedTower;
	
	public static int numMoves;
	public static int minimumMoves;
	private int numDisks;
	
	private long startTime;
	private long endTime;
	private LinkedList<HighScore>[] scores = new LinkedList[8];
	
	private Timer time;
	private int seconds;
	private int tens;
	private int minutes;
	
	private final int DISK_WIDTH_FACTOR = 25;
	private final double DISK_HEIGHT = 20;
	private final double STARTING_X = 165;
	private final double STARTING_Y = 575;
	private final int TOWER_ONE_AREA = 315;
	private final int TOWER_THREE_AREA = 650;
	private final int MAX_NAME_LENGTH = 12;
    
	/**
	* Constructor of GamePanel
	*
	* @param numDisks - The number of disks to play Towers of Hanoi with
	*/
    public TwoColorHanoi(int _numDisks) 
	{
		this.numDisks = _numDisks;
		this.minimumMoves = (int)Math.pow(2, numDisks) - 1;
		this.startTime = System.currentTimeMillis();
		
		this.numMoves = 0;
		this.diskXPos = 0.0; 
		this.diskYPos = 0.0;  
		this.diskWidth = 0.0;  
		
		towerStack[0] = new Stack<Rectangle>();
		towerStack[1] = new Stack<Rectangle>();
		towerStack[2] = new Stack<Rectangle>();
	
		diskColor[0] = new Stack<Color>();
		diskColor[1] = new Stack<Color>();
		diskColor[2] = new Stack<Color>();
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		seconds = 0;
		tens = 0;
		minutes = 0;
		time = new Timer(1000, this);
		time.start();
		
		try {
			scores = load();
		} catch(Exception ex) {
			
		}
		launchGame();
    }
    
	/**
	* Builds and launches Towers of Hanoi game panel
	*/
    public void launchGame()
	{
		Color colors[] = {Color.DARK_GRAY, Color.WHITE, Color.DARK_GRAY, Color.WHITE, Color.DARK_GRAY, Color.WHITE, Color.DARK_GRAY, Color.WHITE};
		Color colorsStackTwo[] = {Color.WHITE, Color.DARK_GRAY, Color.WHITE, Color.DARK_GRAY, Color.WHITE, Color.DARK_GRAY, Color.WHITE, Color.DARK_GRAY};
		
		for (int i = 0; i < numDisks; i++) {
			Rectangle disk = new Rectangle();
			
			int diskWidth = (numDisks * DISK_WIDTH_FACTOR) - (20 * i);
			disk.setFrame(STARTING_X - diskWidth / 2, STARTING_Y - (i * 20), diskWidth, 20);
			towerStack[0].push(disk);
			diskColor[0].push(colors[i]);

		}
		
		for (int i = 0; i < numDisks; i++) {
			Rectangle disk = new Rectangle();
			
			int diskWidth = (numDisks * DISK_WIDTH_FACTOR) - (20 * i);
			disk.setFrame(STARTING_X * 3 - diskWidth / 2, STARTING_Y - (i * 20), diskWidth, 20);
			towerStack[1].push(disk);
			diskColor[1].push(colorsStackTwo[i]);

		}
		repaint();
    }
    
	/**
	* Invoked every second to update time
	*/
	public void actionPerformed(ActionEvent e)
	{
		seconds++;
		
		if (seconds % 10 == 0) {
			tens++;
			seconds = 0;
			if (tens % 6 == 0){
				minutes++;
				tens = 0;
			}
		}
		repaint();
	}
	/**
	* Invoked when mouse is pressed
	*/
    public void mousePressed(MouseEvent e)
	{
		Point point = e.getPoint();
		int n = whichTower(point);
		
		clickedTower = whichTower(point);
		
		if (!towerStack[n].empty()){
			top = towerStack[n].pop();
			topColor = diskColor[n].pop();
		}
    }
     
	/**
	* Determines if a disk can be placed on top of tower
	*/
    public void mouseReleased(MouseEvent e)
	{
		int newTower;
		double y = this.getHeight() - DISK_HEIGHT;
		
		releasedTower = whichTower(e.getPoint());
		newTower = releasedTower;
		
		if (!towerStack[releasedTower].empty()){
			if (towerStack[releasedTower].peek().getWidth() >= top.getWidth()){
				y = towerStack[releasedTower].peek().getY() - DISK_HEIGHT;
				if (hasMoved()){
					numMoves++;
				}
			}
			else{
				JOptionPane.showMessageDialog(this, "Disks can only be placed on empty towers, or bigger disks!", "Tower Of Hanoi", JOptionPane.ERROR_MESSAGE);
				numMoves++;
				newTower = clickedTower;
				if (!towerStack[clickedTower].empty()){
					y = towerStack[clickedTower].peek().getY() - DISK_HEIGHT;
				}	
			}
		}
		else{
			y = getHeight() - DISK_HEIGHT;
			if (hasMoved()) {
				numMoves++;
			}
		}
				
		placeDisk(newTower,y);
		repaint();
		/*	
		if (gameOver()){
			endGame();
		}*/
    }
	
	/**
	* Places a disk onto the proper tower
	*
	* @param tower - The tower the disk will be placed on
	* @param y - the y value
	*/
	private void placeDisk(int tower, double y)
	{
		double x = (getWidth() / 6 + (getWidth() / 3) * tower - top.getWidth() / 2);
		top.setFrame(x, y, top.getWidth(), top.getHeight());
		towerStack[tower].push(top);
		diskColor[tower].push(topColor);
         
		top = null;
		topColor = Color.WHITE;
	}
    
	/**
	* Repaints disk as mouse is being dragged
	*/
    public void mouseDragged(MouseEvent e)
	{
		int mouseX = e.getX();
		int mouseY = e.getY();
		if (top != null){
			top.setFrame(mouseX, mouseY, top.getWidth(), top.getHeight());
			repaint();
		}
    }
     
	/**
	* Paints the Towers of Hanoi game panel
	*/
    public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, getWidth(), getHeight());
      
		int x1 = 165;
		int y1 = getHeight() - 200; 
		int y2 = getHeight();
      
		g2d.setColor(new Color(102,51,0));
		g2d.setStroke(new BasicStroke(10)); 
		
		g2d.drawLine(x1, y1 ,x1, y2);
		g2d.drawLine((3 * x1), y1, (3 * x1), y2);
		g2d.drawLine((5 * x1), y1, (5 * x1), y2);
		g2d.drawLine(0, y2, getWidth(), y2);
		
		g2d.setColor(topColor);
      
		if (top != null){
			g2d.fill(top);
		}
      
		drawTower(g2d,0);
		drawTower(g2d,1);
		drawTower(g2d,2);
		
		//Score & Time information
		Font font = new Font("monospace", Font.BOLD, 15);
		
		g.setColor(Color.RED);
		g.setFont(font);
		g.drawString("Moves: " + String.valueOf(numMoves), 25, 25);
		int stringWidth = g.getFontMetrics().stringWidth("Minimum Moves Required:             ");
		g.drawString("Minimum Moves Required: " + minimumMoves, getWidth() - stringWidth, 25);
		g.drawString(minutes + ":" + tens + seconds, getWidth() / 2, 25);
    }
    
	/**
	* Paints the towers, with disks, if necessary
	*/	
    private void drawTower(Graphics2D g2d, int n)
	{
		if (!towerStack[n].empty()){
			for (int i = 0; i < towerStack[n].size(); i++){
				g2d.setColor(diskColor[n].get(i));
				g2d.fill(towerStack[n].get(i));
			}
		}
    }
    
	/**
	* Determines the area around the tower where mouse was released clicked
	*
	* @param p - Point object of where mouse was released/clicked
	* @return An integer value determining which tower was acted upon
	*/
    private int whichTower(Point point)
	{ 
		int mouseX = (int) point.getX();
		int whichTower = -1;
		
		if (mouseX <= TOWER_ONE_AREA){
			whichTower = 0;
		}
		else if (mouseX > TOWER_ONE_AREA && mouseX < TOWER_THREE_AREA){
			whichTower = 1;
		}
		else if (mouseX >= TOWER_THREE_AREA){
			whichTower = 2;
		}

		return whichTower;
    }
	
	/**
	* Determines if a disk has moved from original tower for purposes of
	* increasing the number of moves made
	*
	* @return Boolean determining if disk has moved.
	*/
	private boolean hasMoved()
	{
		boolean hasMoved = false;
		
		if (clickedTower != releasedTower) {
			hasMoved = true;
		}
		
		return hasMoved;
	}
	
	/**
	* Determines if the game has been won
	*/
	private boolean gameOver()
	{
		boolean gameOver = false;

		if (towerStack[2].size() == (numDisks)){
			gameOver = true;
		}
		
		return gameOver;
	}
	
	/**
	* Starts the process of ending a game
	*/
	private void endGame()
	{
		this.endTime = System.currentTimeMillis();
		HighScore newScore = new HighScore(getPlayerName(), calculateScore());
		scores[numDisks].add(newScore);
		Collections.sort(scores[numDisks]);
		try {
			save(scores);
		} catch (Exception ex) {

		}
		showScore();

	}
	
	/**
	* Gets the name of the player for the high score list
	*
	* @return Name of the player as a string
	*/
	public String getPlayerName()
	{
		String playerName = "";
		
		Object[] options1 = { "OK" };

        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter your name for the high score list: "));
        JTextField textField = new JTextField(10);
        panel.add(textField);

        int accepted = JOptionPane.showOptionDialog(null, panel, "Towers of Hanoi",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options1, null);
        if (accepted == JOptionPane.YES_OPTION){
			playerName = textField.getText();
        }
		
		if (playerName.length() > MAX_NAME_LENGTH){
			playerName = trimName(playerName);
		}
		
		return playerName;
	}
	
	/**
	* Trims the size of the name down to the maximum length if necessary
	* 
	* @param longName - The name to be trimmed down
	* @return new name string of the maximum length
	*/
	private String trimName(String longName)
	{
		String trimmedName = "";
		for (int i = 0; i < MAX_NAME_LENGTH; i++){
			trimmedName += longName.charAt(i);
		}
		
		return trimmedName;
	}
	
	/**
	* Displays End Game window
	*/
	private void showScore()
	{
		Object[] options1 = { "Play again?",
                "Exit Game" };

		String display = buildDisplay();
		
        JPanel panel = new JPanel();
        panel.add(new JLabel(display));

        int accepted = JOptionPane.showOptionDialog(null, panel, "High Scores",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options1, null);
        if (accepted == JOptionPane.YES_OPTION){
			playAgain();
        }
		else if (accepted == JOptionPane.NO_OPTION){
			System.exit(0);
		}

	}
	
	/**
	* Formats the display for High Score list. 
	*
	* @return String containing the High Score List display format
	*/
	private String buildDisplay()
	{
		String display  = "<html><body>";
		
		Iterator<HighScore> itr = scores[numDisks].iterator();
		int i = 0;
		while (i < 10){
			if (itr.hasNext()){
				display += "<br>" + (i + 1) + ": " + itr.next().toString();
				
			}
			i++;
		}
		
		display += "</body></html>";
		
		return display;
	}
	
	/**
	* Prepares panel for New Game if necessary
	*/
	private void playAgain()
	{
		while (!towerStack[2].empty()){
			towerStack[2].pop();
		}
		
		startTime = System.currentTimeMillis();
		numMoves = 0;
		
		launchGame();
	}
	
	/**
	* Calculates the score for the game.
	*/
	private int calculateScore()
	{
		int highScore;
		int timeDifference = (int)(endTime - startTime);
		
		highScore = ((numMoves - minimumMoves) +  1) * (timeDifference);
		
		return highScore;
	}
	
	/**
	* Saves a LinkedList of HighScore as a Serializable file
	*
	* @param scores - An array of LinkedList - LinkedList<HighScore>[]
	*/
	private void save(LinkedList<HighScore>[] scores) throws IOException, ClassNotFoundException
	{
		try {
			FileOutputStream fos = new FileOutputStream("highscore");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(scores);
			oos.flush();
		} catch (Exception ex) {
			
		}
	}

	/**
	* Loads a Serializable instance of a LinkedList<HighScore>
	*
	* @return LinkedList<HighScore>
	*/
	@SuppressWarnings("unchecked")
	private LinkedList<HighScore>[] load() throws IOException, ClassNotFoundException, InterruptedException
	{
		LinkedList<HighScore>[] scores = new LinkedList[8];
		
			try {
				FileInputStream fis = new FileInputStream("highscore");
				ObjectInputStream ois = new ObjectInputStream(fis);
				scores = (LinkedList<HighScore>[]) ois.readObject();
				fis.close();
				ois.close();
			
			} catch (Exception e) {
				//return null object if not found
			}
		
		return scores;
	}	
	
	/**
	* Get minimumMoves
	*/
	public int getMinimumMoves()
	{
		return minimumMoves;
	}
	
	/**
	* Invoked when the mouse is clicked
	*/
	public void mouseClicked(MouseEvent e)
	{
		
	}
	
	/**
	* Invoked when the mouse is entered
	*/
	public void mouseEntered(MouseEvent e)
	{
		
	}
    
	/**
	* Invoked when the mouse is exited
	*/
    public void mouseExited(MouseEvent e)
	{
		
	}
    
	/**
	* Invoked when the mouse is moved
	*/
    public void mouseMoved(MouseEvent e)
	{
		
	}
	
}