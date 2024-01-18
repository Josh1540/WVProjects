package pongGame;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DisplayPanel extends JPanel {

	private static int ballInitialSpeed = 0;
	private static int PcInitialSpeed = 0;
	private static int UserInitialSpeed = 0;

	private static String whichTheme = "";

	private static boolean isPlayerAlone = false;
	private static int difficultyChoice = 0;

	public DisplayPanel() {
		Object[] options = { "EASY", "NORMAL", "HARD", "INSANE" };
		// 5 7 9 15
		Object[] optionsThemes = { "Classic", "Soccer", "Tennis", "Space" };

		int themeChoice = JOptionPane.showOptionDialog(this, "Which theme do you want to play on?",
				"Before we start, let's make sure we agree on the following:", JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, optionsThemes, optionsThemes[optionsThemes.length - 1]);

		switch (themeChoice) {
		case 0:
			whichTheme = "CLASSIC";
			break;
		case 1:
			whichTheme = "SOCCER";
			break;
		case 2:
			whichTheme = "TENNIS";
			break;
		case 3:
			whichTheme = "SPACE";
			break;
		}

		int playerNumber = JOptionPane.showConfirmDialog(this, "Do you have a Player2 ?", "PS: It's okay to be alone",
				JOptionPane.YES_NO_OPTION, JOptionPane.DEFAULT_OPTION);
		if (playerNumber == 0) {
			// YES
			isPlayerAlone = false;
			System.out.println("Player2 present");
		} else {
			// NO
			isPlayerAlone = true;
			System.out.println("Player2 not present");
		}

		difficultyChoice = JOptionPane.showOptionDialog(this,
				"How hard do you want the Pong game to be? (EASY / NORMAL / HARD / INSANE)",
				"Before we start, let's make sure we agree on the following:", JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[3]);

		switch (difficultyChoice) {
		case 0:
			System.out.println("Easy pressed");
			ballInitialSpeed = 5;

			UserInitialSpeed = 10;// PC/PLAYER SPEED = 9
			PcInitialSpeed = 10;

			break;

		case 1:
			System.out.println("Normal pressed");
			ballInitialSpeed = 7;

			PcInitialSpeed = 11;
			UserInitialSpeed = 10; // PC/PLAYER SPEED = 10
			break;

		case 2:
			System.out.println("Hard  pressed");
			ballInitialSpeed = 9;

			PcInitialSpeed = 13; // PC SPEED = 13
			UserInitialSpeed = 11;// PLAYER SPEED = 11
			break;

		case 3:
			System.out.println("Insane pressed");
			ballInitialSpeed = 13;

			PcInitialSpeed = 16; // PC SPEED 16
			UserInitialSpeed = 13; // PLAYER 12
			break;
		}

	}

	public static int getInitialSpeed() {
		System.out.println("Ball Initial Speed:" + ballInitialSpeed);
		return ballInitialSpeed;
	}

	public static int getPcInitialSpeed() {
		return PcInitialSpeed;
	}

	public static int getUserInitialSpeed() {
		return UserInitialSpeed;
	}

	public static boolean getIsPlayerAlone() {
		return isPlayerAlone;
	}

	public static String getWhichTheme() {
		return whichTheme;
	}

	public static int getDifficultyChoice() {
		return difficultyChoice;
	}

}
