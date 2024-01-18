package pongGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class PongPanel extends JPanel {

	private final int WINDOW_WIDTH = 640, WINDOW_HEIGHT = 480;
	private Ball gameBall;
	private Ball InvisibleGameBall;

	private Paddles paddlePC;
	private Paddles paddleUser;
	public int userScore;
	public int PCScore;
	public int numBouncesPaddles = 0;

	private MovingUp actionUp;
	private MovingDown actionDown;
	private StopMoving stopMoving;

	private MovingUpPc actionUpPc;
	private MovingDownPc actionDownPc;
	private StopMovingPc stopMovingPc;
	private StopMovingPc stopMovingPc2;

	private StopMovingPcSpace stopMovingPcSpace;
	private MoveAgainPcSpace moveAgainPcSpace;

	boolean isMovingUpUser = false;
	boolean isMovingDownUser = false;
	boolean isMovingUpPC = false;
	boolean isMovingDownPC = false;
	boolean isTheFirstMov = true;
	private int startingMovX = 1;
	private int startingMovY = 1;

	private boolean shouldPCMove = true;

	private static final String MOVE_UP = "move up";
	private static final String MOVE_DOWN = "move down";
	private static final String STOP_MOVING = "move null";

	private static final String MOVE_UPpc = "move up pc";

	private static final String MOVE_DOWNpc = "move down pc";
	private static final String STOP_MOVINGpc = "move null pc";
	private static final String STOP_MOVINGpc2 = "move null pc2";
	private static final String START_MOVINGpc2 = "move again pc2";

	public PongPanel() {

		Color ballColor = null;
		Color surroundingColor = null;

		setFocusable(true);

		InvisibleGameBall = new Ball(0, 0, 0, 0, 0, 0, Color.black, Color.black);
		System.out.println("Start speed Invisible:" + InvisibleGameBall.getStartSpeed());
		findRandomStartMovValues();

		int randomNum = ThreadLocalRandom.current().nextInt(20, WINDOW_HEIGHT - 20);

		switch (DisplayPanel.getWhichTheme()) {
		case "SOCCER":
			ballColor = Color.BLACK.brighter();
			surroundingColor = Color.WHITE;
			break;

		case "CLASSIC":
			ballColor = Color.YELLOW;
			surroundingColor = Color.YELLOW;
			break;

		case "TENNIS":
			ballColor = Color.GREEN.darker();
			surroundingColor = Color.GREEN;
			break;
		case "SPACE":
			ballColor = Color.YELLOW.brighter();
			surroundingColor = Color.ORANGE.darker().darker();
			break;
		}

		gameBall = new Ball(WINDOW_WIDTH / 2, randomNum, 20, startingMovX, startingMovY,
				InvisibleGameBall.getStartSpeed(), ballColor, surroundingColor);

		paddlePC = new Paddles(WINDOW_WIDTH - 20, WINDOW_HEIGHT / 2 - (WINDOW_HEIGHT / 15), 100,
				DisplayPanel.getPcInitialSpeed(), Color.RED);
		paddleUser = new Paddles(2, WINDOW_HEIGHT / 2 - (WINDOW_HEIGHT / 15), 100, DisplayPanel.getUserInitialSpeed(),
				Color.BLUE);

		paddlesMovementSettings();

	}

	private void paddlesMovementSettings() {

		// up mov
		actionUp = new MovingUp();
		getInputMap().put(KeyStroke.getKeyStroke('w'), MOVE_UP);
		getActionMap().put(MOVE_UP, actionUp);

		stopMoving = new StopMoving();
		getInputMap().put(KeyStroke.getKeyStroke("released  W"), STOP_MOVING);
		getActionMap().put(STOP_MOVING, stopMoving);

		// down mov
		actionDown = new MovingDown();
		getInputMap().put(KeyStroke.getKeyStroke('s'), MOVE_DOWN);
		getActionMap().put(MOVE_DOWN, actionDown);

		getInputMap().put(KeyStroke.getKeyStroke("released S"), STOP_MOVING);
		getActionMap().put(STOP_MOVING, stopMoving);
		//////////////////////////////////////////////////////////////////////////////////
// PC Commands
		// up mov
		if (!(DisplayPanel.getIsPlayerAlone())) {
			stopMovingPc = new StopMovingPc();
			actionUpPc = new MovingUpPc();
			getInputMap().put(KeyStroke.getKeyStroke((char) KeyEvent.VK_UP, 0, false), MOVE_UPpc);
			getActionMap().put(MOVE_UPpc, actionUpPc);

			getInputMap().put(KeyStroke.getKeyStroke((char) KeyEvent.VK_UP, 0, true), STOP_MOVINGpc);
			getActionMap().put(STOP_MOVINGpc, stopMovingPc);

			// down mov
			actionDownPc = new MovingDownPc();
			getInputMap().put(KeyStroke.getKeyStroke((char) KeyEvent.VK_DOWN, 0, false), MOVE_DOWNpc);
			getActionMap().put(MOVE_DOWNpc, actionDownPc);

			getInputMap().put(KeyStroke.getKeyStroke((char) KeyEvent.VK_DOWN, 0, true), STOP_MOVINGpc);
			getActionMap().put(STOP_MOVINGpc, stopMovingPc);

		}

		stopMovingPcSpace = new StopMovingPcSpace();
		moveAgainPcSpace = new MoveAgainPcSpace();
		getInputMap().put(KeyStroke.getKeyStroke((char) KeyEvent.VK_SPACE, 0, false), STOP_MOVINGpc2);
		getActionMap().put(STOP_MOVINGpc2, stopMovingPcSpace);
		getInputMap().put(KeyStroke.getKeyStroke((char) KeyEvent.VK_SPACE, 0, true), START_MOVINGpc2);
		getActionMap().put(START_MOVINGpc2, moveAgainPcSpace);

	}

	private void findRandomStartMovValues() {
		double randNumX = Math.random();

		if (randNumX > 0.5) {
			startingMovX = InvisibleGameBall.getStartSpeed() * -1;
		} else {
			startingMovX = InvisibleGameBall.getStartSpeed();
		}

		double randNumY = Math.random();
		if (randNumY > 0.5) {
			startingMovY = InvisibleGameBall.getStartSpeed() * -1;
		} else {
			startingMovY = InvisibleGameBall.getStartSpeed();
		}
	}

	public void paintComponent(Graphics page) { // creating window of game

		switch (DisplayPanel.getWhichTheme()) {
		case "SOCCER":
			page.setColor(Color.GREEN.darker().darker());
			page.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
			break;

		case "CLASSIC":
			page.setColor(Color.BLACK);
			page.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
			break;

		case "TENNIS":
			page.setColor(Color.BLUE.darker().darker());
			page.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
			break;

		case "SPACE":
			try {
				BufferedImage image = ImageIO
						.read(new File("C:\\Users\\s195398\\eclipse-workspace\\GamesProject\\Image\\space.jpg"));
				page.drawImage(image, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, getFocusCycleRootAncestor());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Something is wrong");
			}
			break;
		}

		page.setColor(Color.WHITE);
		page.setFont(new Font("Arial", Font.PLAIN, 20));
		page.drawString("Scores: [" + userScore + "] / [" + PCScore + "]", (int) (WINDOW_WIDTH * 0.40), 25);

		if (DisplayPanel.getWhichTheme().equals("SOCCER")) {
			page.setColor(Color.WHITE);//////////////////
			page.fillRect(WINDOW_WIDTH / 2, 0, 5, WINDOW_HEIGHT);
			page.drawOval(WINDOW_WIDTH / 2 - 49, WINDOW_HEIGHT / 3 + 20, 100, 100);

			page.fillOval(WINDOW_WIDTH / 2 - 7, WINDOW_HEIGHT / 2 - 16, 20, 20);

			page.drawRect(-1, 2, WINDOW_WIDTH / 4, WINDOW_HEIGHT - 12);
			page.drawRect(WINDOW_WIDTH - WINDOW_WIDTH / 4, 2, WINDOW_WIDTH / 4, WINDOW_HEIGHT - 12);
		}

		if (DisplayPanel.getWhichTheme().equals("TENNIS")) {
			page.drawRect(5, 1, WINDOW_WIDTH - 15, WINDOW_HEIGHT - 15);
			page.drawRect(5, 50, WINDOW_WIDTH - 15, WINDOW_HEIGHT - 115);

			page.drawRect(100, 50, WINDOW_WIDTH - (WINDOW_WIDTH / 3), WINDOW_HEIGHT - 115);
			page.drawLine(100, WINDOW_HEIGHT / 2, 527, WINDOW_HEIGHT / 2);
		}

		if (DisplayPanel.getWhichTheme().equals("SPACE")) {
			
		}

		gameBall.paintComponent(page); // asking gameBall to be drawn in the page?

		paddlePC.PaintComponent(page);
		paddleUser.PaintComponent(page);
	}

//	public static BufferedImage main(String[] args) throws IOException {
//		// JFrame frame = buildFrame();
//
//		final BufferedImage image = ImageIO.read(new File("C:\\Users\\s195398\\Desktop\\img\\space.jpg"));
//
//		JPanel panel = new JPanel() {
//			@Override
//			protected void paintComponent(Graphics g) {
//				super.paintComponent(g);
//				g.drawImage(image, 0, 0, null);
//			}
//		};
//		return image;
//
//	}

	public void gameLogic() {
		if (isTheFirstMov) {
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			isTheFirstMov = false;
		}
		// using the method inside Ball class- Movement
		gameBall.moveBall();
		// System.out.println("I'm working");

		gameBall.bounceEdges(5, WINDOW_HEIGHT - 5); // TOP - BOTTOM limits

		// PC FOLLOWING YBall
		if (DisplayPanel.getIsPlayerAlone() && shouldPCMove == true) {

			if (DisplayPanel.getDifficultyChoice() == 0 || DisplayPanel.getDifficultyChoice() == 1) {
				paddlePC.moveTowards(gameBall.getYBall());
			} else if (DisplayPanel.getDifficultyChoice() == 2 || DisplayPanel.getDifficultyChoice() == 3)
				paddlePC.moveTowardsInteligently(gameBall.getYBall());
		}

		if (paddlePC.isBallColliding(gameBall) == true) {
			numBouncesPaddles++;
			gameBall.reverseX();

			gameBall.setMovementXY(gameBall.getMovementXBall(), paddlePC.whereIsBallColliding(gameBall));

		}

		if (paddleUser.isBallColliding(gameBall) == true) {
			numBouncesPaddles++;
			gameBall.reverseX();

			gameBall.setMovementXY(gameBall.getMovementXBall(), paddleUser.whereIsBallColliding(gameBall));
		}

		if (gameBall.getXBall() < -1) {
			PCScore++;

			reset();
		} else if (gameBall.getXBall() > WINDOW_WIDTH - gameBall.getSize() * 1.25) {
			userScore++;

			if (DisplayPanel.getInitialSpeed() == 13 && DisplayPanel.getIsPlayerAlone() == true)

				paddlePC.changeSizePC(userScore);
			paddleUser.changeSizeUser(-1 * userScore);
			reset();
		}

		// after certain number of bounces, increase ball speed and reset num
		if (numBouncesPaddles == 2) {

			numBouncesPaddles = 0;
			gameBall.increaseSpeed();
			System.out.println("Speed increasing! Current speed: " + gameBall.getSpeed());
			// increase speed
		}

		// CHECKING MOVEMENT OF PADDLES (USER AND PC {USER2})
		if (isMovingUpUser == true && paddleUser.getY() > 5) {
			paddleUser.setXY(paddleUser.getX(), paddleUser.getY() - paddleUser.getSpeed());

		} else if (isMovingDownUser == true && paddleUser.getY() < WINDOW_HEIGHT / 1.2) {
			paddleUser.setXY(paddleUser.getX(), paddleUser.getY() + paddleUser.getSpeed());
		}

		if (isMovingUpPC == true && paddlePC.getY() > 5) {
			paddlePC.setXY(paddlePC.getX(), paddlePC.getY() - paddlePC.getSpeed());

		} else if (isMovingDownPC == true && paddlePC.getY() < WINDOW_HEIGHT / 1.2) {
			paddlePC.setXY(paddlePC.getX(), paddlePC.getY() + paddlePC.getSpeed());

		}

	}

	public void reset() {
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		gameBall.setX(300, 225);
		gameBall.setSpeed(gameBall.startSpeed);
		gameBall.setMovementXY(gameBall.startSpeed, gameBall.startSpeed);

		paddlePC.setXY(paddlePC.getX(), WINDOW_HEIGHT / 2 - (WINDOW_HEIGHT / 15));
		paddleUser.setXY(paddleUser.getX(), WINDOW_HEIGHT / 2 - (WINDOW_HEIGHT / 15));
		numBouncesPaddles = 0;
	}

	public class StopMoving extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {

			isMovingUpUser = false;
			// release state
			isMovingDownUser = false;

		}
	}

	public class MovingUp extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			// System.out.println("I was pressed");
			isMovingUpUser = true;

		}
	}

	public class MovingDown extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			isMovingDownUser = true;

		}
	}

	// pc moving up down and stopping
	public class StopMovingPc extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {

			isMovingUpPC = false;
			isMovingDownPC = false;
			System.out.println("Stop was pressed");

		}
	}

	public class StopMovingPcSpace extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			shouldPCMove = false;

			System.out.println("Stop space was pressed");

		}
	}

	public class MoveAgainPcSpace extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			shouldPCMove = true;

		}
	}

	public class MovingUpPc extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {

			isMovingUpPC = true;

		}
	}

	public class MovingDownPc extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			isMovingDownPC = true;
		}
	}

}
