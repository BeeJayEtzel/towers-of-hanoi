/**
*
* TowersOfHanoi.java - GamePanel class for Project 03
*
* @author Brian Etzel
* @version 1.0
*/
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JLabel;

 
public class TowersOfHanoi{
    
	private final int WINDOW_WIDTH = 1000;
	private final int WINDOW_HEIGHT = 625;
		
    private JFrame frame = new JFrame();
    private ClassicHanoi classic;
	private TwoColorHanoi twoColor;
	private ThreeColorHanoi threeColor;
      
    public TowersOfHanoi(String gameType) {
		frame.setTitle("Towers of Hanoi");
		int numDisks = getNumDisks();
		
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT); 
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		switch (gameType) 
		{
			case "classic":
				classic = new ClassicHanoi(numDisks);
				frame.add(classic);
				break;
			case "twoColor":
				twoColor = new TwoColorHanoi(numDisks);
				frame.add(twoColor);
				break;
			case "threeColor":
				threeColor = new ThreeColorHanoi(numDisks);
				frame.add(threeColor);
				break;
			
		}

		
    }
	
	private int getNumDisks() 
	{
        int numDisks = 3;
		
		Object[] options1 = { "OK" };

        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter the number of disks (3-8): "));
        JTextField textField = new JTextField(10);
        panel.add(textField);

        int accepted = JOptionPane.showOptionDialog(null, panel, "Towers of Hanoi",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options1, null);
        if (accepted == JOptionPane.YES_OPTION){
			numDisks = Integer.parseInt(textField.getText());
        }
		
		if (numDisks < 3 || numDisks > 8) {
			numDisks = 3;
		}
		
		return numDisks;
    }
	
}