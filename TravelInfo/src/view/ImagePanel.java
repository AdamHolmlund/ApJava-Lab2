package view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image;

	/**
	 * Constructs a panel containing an image
	 * 
	 * @param gui
	 *            is the GUI associated with the panel
	 * @param row
	 *            is the row from which the link to the image is gotten
	 */
	public ImagePanel(URL url) {
		try {
			image = ImageIO.read(url);
		} catch (IOException ex) {
			System.out.println("Could not load image");
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	}

}