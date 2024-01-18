package pongGame;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {

	public static void main(String[] args) throws IOException {
		JFrame frame = buildFrame();

		final BufferedImage image = ImageIO.read(new File("C:\\Users\\s195398\\Desktop\\img\\space.jpg"));

		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(image, 0, 0, null);
			}
		};

		frame.add(panel);
	}

	private static JFrame buildFrame() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(500, 200);
		frame.setVisible(true);
		return frame;
	}

}
