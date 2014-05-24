package view;

import javax.swing.*;

import java.io.*;
import java.util.*;
import java.awt.Color;
import java.awt.Dimension;

public class AboutFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private String filepath = "./res/about.txt";
	private JTextArea textarea;
	private String[] lines = new String[20];
	public static String newline = System.getProperty("line.separator");

	/**
	 * Constructs a frame that will hold the information about the program
	 */
	public AboutFrame() {
		this.setSize(new Dimension(500, 330));
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		try {
			File file = new File(filepath);
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(file);
			int i = 0;
			while (sc.hasNextLine()) {
				lines[i] = sc.nextLine();
				i++;
			}
			System.out.println(lines[1]);

		} catch (FileNotFoundException e) {
			System.out.println("Could not find 'About'-file");
		}
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);

		createTextArea();

		panel.add(textarea);
		this.add(panel);
	}

	/**
	 * Creates the JTextArea that is displayed in the AboutFrame from a local
	 * file
	 */
	private void createTextArea() {
		textarea = new JTextArea();
		textarea.setEditable(false);
		textarea.setFocusable(false);
		textarea.setLineWrap(true);
		textarea.setWrapStyleWord(true);
		textarea.setSize(new Dimension(420, 340));
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while (lines[i] != null) {
			sb.append(lines[i] + newline);
			i++;
		}
		textarea.append(sb.toString());

	}

}