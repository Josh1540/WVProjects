package pongGame;

import java.awt.Color;
import java.awt.Graphics;

public class Paddles {
	// declare variables

	public int height;
	public int x;
	public int y;
	public int speed;
	public Color color;

	// constant - thing on the paddle that not gonna change

	public final int PADDLE_WIDTH = 12;

	public Paddles(int x, int y, int height, int speed, Color color) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.speed = speed;
		this.color = color;
	}

	public void PaintComponent(Graphics paddles) {
		paddles.setColor(color);

		paddles.fillRect(x, y, PADDLE_WIDTH, height);
	}

	public void moveTowards(int moveToY) {
		int centerY = y + height / 2;

		// checking if the center of the paddle is withing the range (speed int) not
		// gonna move it

		if (Math.abs(centerY - moveToY) > speed) {

			// center bigger than point to move = go up screen
			if (centerY > moveToY) {
				y = y - speed;

				// centers smaller than point to move 2 = go down screen
			} else if (centerY < moveToY) {
				y = y + speed;
			}
		}
	}

	public void moveTowardsInteligently(int moveToY) {
		int centerY = y + height / 2;

		if (Math.abs(centerY - moveToY) > speed) {
			System.out.println();
			if (moveToY > centerY || moveToY > y + height) {

				y = y + speed;

			} else if (moveToY < centerY || moveToY < y + height) {

				y = y - speed;
			}
		}
	}

	public boolean isBallColliding(Ball b) {
		int rightX = x + PADDLE_WIDTH;
		int bottomY = y + height;

		if (b.getXBall() > (x - b.getSize()) && b.getXBall() < rightX) { // checking if xBall is between the x(left
																			// side) and x(Right side)
			if (b.getYBall() > y - b.getSize() && b.getYBall() < bottomY) { // checking if yBall is between the y(top
																			// side) and
				// y(bottom side)
				return true;
			}

		}
		return false;
	}

	public double whereIsBallColliding(Ball b) {
		int yMiddlePaddle = y + (height / 2);
//					Yball more than yPaddle and  yBall  less than yMiddlePaddle

		if (b.getYBall() > y - b.getSize() && b.getYBall() < (y + height)) {

			int position = b.getYBall() - yMiddlePaddle;
			System.out.println("Position: " + position);

			double proportion = position / ((double) height / 2);
			System.out.println("proportion: " + proportion);

			double deltaY = 15 * proportion;
			System.out.println("deltaY: " + deltaY);
			System.out.println();

			// System.out.println(w);

//			double s = ((double) (b.getYBall() + b.getSize()) - y) / (double) y;
//			System.out.println((double) (b.getYBall() + b.getSize()) - y + "  " + (double) y);
//			// MIN --- 0 / MIN value

			return deltaY;

		} else {
			return 123;
		}

	}

	public void changeSizePC(int i) {
		this.height = height + (4 * i);
		PongPanel pongPanel = new PongPanel();
		pongPanel.repaint();

	}

	public void changeSizeUser(int i) {
		this.height = height + (2 * i);
		PongPanel pongPanel = new PongPanel();
		pongPanel.repaint();

	}

	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSpeed() {
		return speed;
	}

}
