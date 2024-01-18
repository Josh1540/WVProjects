package pongGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Ball {
	// declare aspects of the ball
	private int x;
	private int y;
	private int movementX;
	private double movementY;
	private int size;
	private int speed;
	private final int MAX_SPEED = 20;
	public int startSpeed = DisplayPanel.getInitialSpeed();

	private Color colorBall;
	private Color surroundingColor;

	public Ball(int x, int y, int size, int movementX, int movementY, int speed, Color color, Color surrounding) {
		this.x = x;
		this.y = y;
		this.movementX = movementX;
		this.movementY = movementY;
		this.size = size;
		this.speed = speed;
		this.colorBall = color;
		this.surroundingColor = surrounding;
	}

	public void paintComponent(Graphics ball) {
		ball.setColor(colorBall);
		ball.fillOval(x, y, size, size);

		Graphics2D surroundingBall = (Graphics2D) ball;
		ball.setColor(surroundingColor);
		surroundingBall.setStroke(new BasicStroke(2));
		surroundingBall.drawOval(x, y, size, size);
		
		
		//ball.drawOval(x, y, size, size);
		
	}

	public void moveBall() {
		x += movementX; // adding movement so the x and y gets bigger (movement of the ball)
		y += movementY;
//		System.out.println("x:" + x + "y:"+ y);
//		System.out.println("Mx:" + movementX + "My:"+ movementY);

	}

	public void bounceEdges(int top, int bottom) { // Y locations of the bouncing area
		if (y > bottom - size * 1.5) {
			reverseY();
		} else if (y < top) {
			reverseY();
		}
	}

	public void increaseSpeed() {
		if (speed < MAX_SPEED) {
			speed++;
			setMovementXY(movementX / Math.abs(movementX) * speed, movementY / Math.abs(movementY) * speed); // set
																												// direction
																												// and
																												// magnitude
			System.out.println();
		}
	}

	public void reverseX() {
		movementX = movementX * -1;
	}

	public void reverseY() {
		movementY = movementY * -1;

	}

	public void setX(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setStartSpeed(int startSpeed) {
		this.startSpeed = startSpeed;
	}

	public void setMovementXY(int movementX, int movementY) {
		this.movementX = movementX;
		this.movementY = movementY;
	}

	public int getYBall() {
		return y;
	}

	public int getXBall() {
		return x;
	}

	public double getMovementYBall() {
		return movementY;
	}

	public int getMovementXBall() {
		return movementX;
	}

	public int getSize() {
		return size;
	}

	public int getSpeed() {
		return speed;
	}

	public int getStartSpeed() {
		return startSpeed;
	}

	public void setMovementXY(int movementXBall, double whereIsBallColliding) {
		this.movementX = movementXBall;
		this.movementY = whereIsBallColliding;

	}

}
