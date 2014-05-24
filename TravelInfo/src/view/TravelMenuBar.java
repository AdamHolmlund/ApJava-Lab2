package view;

import javax.swing.*;

@SuppressWarnings("serial")
public class TravelMenuBar extends JMenuBar {

	public JMenuItem refresh;
	public JMenuItem saveSettings;
	public JMenuItem exit;
	public JMenuItem about;

	/**
	 * Constructs the program's menu bar and adds listeners to it
	 * 
	 * @param controller
	 *            is the controller associated with the menu bar
	 */
	public TravelMenuBar() {

		// Create the "file" menu and add items to it
		JMenu file = new JMenu("File");

		refresh = new JMenuItem("Refresh table");

		saveSettings = new JMenuItem("Save settings");

		exit = new JMenuItem("Exit");

		file.add(refresh);
		file.add(saveSettings);
		file.add(exit);

		// Create the "help" menu and add items to it
		JMenu help = new JMenu("Help");
		about = new JMenuItem("About");

		help.add(about);
		this.add(file);
		this.add(help);
	}
}