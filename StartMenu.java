/*
* StartMenu.java- StartMenu for AirPong
*
* @author Brian Etzel
* @version 1.0
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Stack;
import java.awt.Font;
import java.io.*;

public class StartMenu {
	
	private JFrame mainFrame;
	private JPanel textPanel;
	private JPanel buttonPanel;
	private JLabel headerLabel;
	private JLabel answerLabel;
	
	private final int WINDOW_WIDTH = 1000;
	private final int WINDOW_HEIGHT = 625;

	/**
	* Constructor for StartMenu class
	*/
	public StartMenu()
	{
		prepareGUI();
	}
	
	/**
	* Sets the stage for the Start Menu GUI
	*/
	public void prepareGUI()
	{
		mainFrame = new JFrame("Towers of Hanoi - Project 5");
		mainFrame.setLayout(new GridLayout(2,1));
		mainFrame.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		mainFrame.setBackground(Color.BLACK);

		textPanel = new JPanel();
		textPanel.setBackground(Color.BLACK);
		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.BLACK);
		
		textPanel.setLayout(new GridBagLayout());
	  
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
	        System.exit(0);
			}        
		});    
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5,1,5,1);
		
		Font font = new Font("monospace", Font.BOLD, 50);
		
		
		headerLabel = new JLabel("Towers of Hanoi",JLabel.CENTER );
		headerLabel.setFont(font);
		headerLabel.setForeground(Color.RED);
		gbc.gridx = 0;
		gbc.gridy = 0;
		textPanel.add(headerLabel);
		
		JButton classic = new JButton("Classic");
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		buttonPanel.add(classic, gbc);
		
		JButton twoColor = new JButton("Two Color Hanoi");
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		buttonPanel.add(twoColor, gbc);
		
		JButton threeColor = new JButton("Three Color Hanoi");
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		buttonPanel.add(threeColor, gbc);
		
		JButton exit = new JButton("Exit");
		gbc.gridx = 3;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		buttonPanel.add(exit, gbc);


		classic.setActionCommand("classic");
		twoColor.setActionCommand("twoColor");
		threeColor.setActionCommand("threeColor");
		exit.setActionCommand("exit");
	
		classic.addActionListener(new ButtonClickListener());
		twoColor.addActionListener(new ButtonClickListener());
		threeColor.addActionListener(new ButtonClickListener());
		exit.addActionListener(new ButtonClickListener());
		
		
		mainFrame.add(textPanel);
		mainFrame.add(buttonPanel);
		mainFrame.setVisible(true);
		
	}
	
    /**
	* Button Click Listener for Start Menu
	*/
    private class ButtonClickListener implements ActionListener{
		
	   /**
	   * Creats an instance of the ButtonClickListener
	   */
	   public ButtonClickListener()
	   {
		
	   }
	   
	    /**
		* Performs requitse action upon button click
		*/
		public void actionPerformed(ActionEvent e){
			String command = e.getActionCommand();  
			if( command.equals( "classic" ))  {
				TowersOfHanoi toh = new TowersOfHanoi("classic");
				mainFrame.setVisible(false);
			}
			else if (command.equals("twoColor")){
				TowersOfHanoi toh = new TowersOfHanoi("twoColor");
			}
			else if (command.equals("threeColor")){
				TowersOfHanoi toh = new TowersOfHanoi("threeColor");
			}
			else if (command.equals("exit")){
				System.exit(0);
			}
		}
	}
} 