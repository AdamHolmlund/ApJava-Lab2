package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComboBox;
import javax.swing.JTable;
import model.Offer;
import model.TravelTableModel;
import model.XMLParser;
import view.AboutFrame;
import view.GUI;
import view.OfferFrame;

public class Controller {

	private long sleeptime = 1800000;
	private GUI gui;
	private Thread parser;
	private Timer timer;
	private TravelTableModel tablemodel;

	/**
	 * Creates a controller for the program. The controller starts actions and
	 * handles communications between model and view. It also has a timer which
	 * updates the table on a given interval
	 * 
	 * @param gui
	 *            the view
	 * @param model
	 *            the model
	 */
	public Controller(final GUI gui) {
		loadSettings();
		this.gui = gui;
		this.tablemodel = gui.getTableModel();
		// Create and start the timer
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				parser = new XMLParser(gui.getTableModel());
				parser.start();
			}
		}, 0, sleeptime);

		// Add listeners to components
		this.gui.addUpdateButtonListener(new UpdateButtonListener());
		this.gui.addTravelTableMouseListener(new TravelTableMouseListener());
		this.gui.addExitListener(new ExitListener());
		this.gui.addAboutListener(new AboutListener());
		this.gui.addSaveListener(new SaveListener());
		this.gui.addIntervalChangeListener(new IntervalChangeListener());

	}

	/**
	 * Loads the settings from a text file. If no settings are found, sleeptime
	 * is set to default value (1800000 milliseconds).
	 */
	private void loadSettings() {
		System.out.println("Loading settings...");
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("./res/savefile.txt"));
			sleeptime = scanner.nextInt();
		} catch (FileNotFoundException e) {
			sleeptime = 1800000;
			System.out
					.println("Settings could not be loaded - default value set");
		}
	}

	/**
	 * Updates the offer table in the GUI
	 */
	class UpdateButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			timer.cancel();
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					parser = new XMLParser(tablemodel);
					parser.start();
				}

			}, 0, sleeptime);
		}
	}

	/**
	 * When the table with the listener is double-clicked, the controller goes
	 * through all the offers to find one which has identical fields as the
	 * highlighted table row. When the right offer is found, an OfferFrame
	 * displaying its properties is opened
	 */
	class TravelTableMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				JTable target = (JTable) e.getSource();
				int row = target.getSelectedRow();
				Offer[] offers = tablemodel.getOfferList();

				for (int i = 0; i < offers.length; i++) {
					if (offers[i].getDestination() == target.getValueAt(row, 0)
							&& offers[i].getDepartureDate() == target
									.getValueAt(row, 1)
							&& offers[i].getCurrentPrice() == target
									.getValueAt(row, 2)) {
						new OfferFrame(offers[i]);
					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

	/**
	 * Closes the program when an action is performed
	 */
	class ExitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	/**
	 * Opens an AboutFrame when an action is performed
	 */
	class AboutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new AboutFrame();
		}
	}

	/**
	 * Sets the value for how long the timer will wait until next parsing. Also
	 * cancels the timer and parses again.
	 */
	class IntervalChangeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox<?> comboBox = (JComboBox<?>) e.getSource();
			System.out.println("Intervals changed to "
					+ comboBox.getSelectedItem());
			long newTime = Integer
					.parseInt((String) comboBox.getSelectedItem());
			sleeptime = newTime * 60000;

			timer.cancel();
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					parser = new XMLParser(tablemodel);
					parser.start();
				}

			}, 0, sleeptime);

		}
	}

	/**
	 * Saves the settings for the program into a text file when an action is
	 * performed
	 */
	class SaveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Writer wr;
			try {
				wr = new FileWriter("./res/savefile.txt");
				wr.write(new Integer((int) sleeptime).toString());
				wr.close();

			} catch (IOException exc) {
				System.out.println("Could not save settings!");
			}
		}
	}
}
