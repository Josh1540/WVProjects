package pongGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Main {

	final static int DELAY = 30;

	public static void main(String args[]) {
		JFrame frame = new JFrame("Pong");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setBackground(Color.GREEN);

		// adding JPanel to the JFrame
		DisplayPanel dPanel = new DisplayPanel();

		PongPanel pongPanel = new PongPanel();

//		frame.getContentPane().add(dPanel);

		frame.getContentPane().add(pongPanel);

		frame.pack();
		frame.setVisible(true);

		// SIZE
		frame.setSize(new Dimension(650, 510));

		// creates times, make action happen after DELAY time, using the ActionListener
		// just created
		Timer timer = new Timer(DELAY, new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// calling the game logic to activate
				pongPanel.gameLogic();

				// action performed:
				pongPanel.repaint();
			}
		});
		timer.start();// starting the times
	}
}

//Add keyboard controls XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

//Make it so two people can play against each other using different keys on one keyboard.XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

//Randomize the starting location and direction of the ball XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

//Have the paddles grow or shrink depending on the situation XXXXXXXXXXXXX

//Allow the user to select different difficulty levels or challenges at the beginning of the gameXXXXXXXXXXXXXXX

//Change the paddle bounce logic so the ball bounces off the paddle at different angles based on where it hit the paddleXXXXXX

//Give the pong game themes, so it looks like a tennis court or a soccer field  XXXXXXXXXXX

//Make the AI more intelligent, so it can hit the higher speed balls more often

//Create unique balls with special abilities that randomly spawn, if you hit them, you get some type of special effect or ability

//Make the ball or paddles change colors as its speed increases

//Give the user 3 lives and record their highest score in a list, saved as a file on the user's hard drive.
