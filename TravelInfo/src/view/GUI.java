package view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.BadLocationException;

import model.SearchTextField;
import model.TravelTableModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class GUI extends JFrame {

	private static final long serialVersionUID = 8200021027658677314L;
	private JPanel mainpanel, topPanel, bottompanel;
	private SearchTextField searchfield;
	private JButton updateButton;
	private TravelTableModel tablemodel;
	private JTable table;
	private TableRowSorter<TableModel> rowSorter;
	private String searchFieldHint = "Search";
	private JLabel statusLabel = new JLabel("...");
	private JComboBox<String> intervalsBox = new JComboBox<String>();
	private String[] comboBoxStrings = { "30", "60", "90", "120" };
	private TravelMenuBar menubar = new TravelMenuBar();

	/**
	 * Builds a JFrame with a main panel, which in turn contains three
	 * sub-panels
	 */
	public GUI() {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(700, 480));
		this.pack();
		this.setVisible(true);
		this.setTitle("Travel Info");
		this.setJMenuBar(menubar);

		mainpanel = new JPanel();
		mainpanel.setLayout(new BorderLayout());

		mainpanel.add(buildTopPanel(), BorderLayout.NORTH);
		mainpanel.add(buildMidPanel(), BorderLayout.CENTER);
		mainpanel.add(buildBottomPanel(), BorderLayout.SOUTH);

		add(mainpanel);
		pack();
	}

	/**
	 * Builds the topmost panel of the view, containing the search field and the
	 * search button
	 * 
	 * @return the top panel as a JPanel object
	 */
	private JPanel buildTopPanel() {
		topPanel = new JPanel();

		searchfield = (SearchTextField) buildSearchField();

		topPanel.add(searchfield);

		return topPanel;
	}

	/**
	 * Builds the middle panel of the view, containing the table of offers
	 * 
	 * @return the mid panel as a JPanel object
	 */
	private JPanel buildMidPanel() {
		tablemodel = new TravelTableModel();
		table = new JTable(tablemodel);
		table.setShowGrid(false);
		TableRowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(
				tablemodel);
		this.rowSorter = rowSorter;

		JScrollPane scroll = new JScrollPane(table);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(scroll, BorderLayout.CENTER);
		table.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		table.setRowSorter(rowSorter);
		return panel;
	}

	/**
	 * Builds the bottom panel of the view, containing the update button and the
	 * update frequency setter
	 * 
	 * @return the bottom panel as a JPanel object
	 */
	private JPanel buildBottomPanel() {
		bottompanel = new JPanel();
		bottompanel.setLayout(new GridLayout());

		// Create a panel for the label to the bottom left
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BorderLayout());
		labelPanel.add(statusLabel, BorderLayout.WEST);

		// Create a mid panel for the update button and the
		// JComboBox specifying update intervals
		JPanel updatePanel = new JPanel();
		updateButton = new JButton("Update");
		intervalsBox = new JComboBox<String>(comboBoxStrings);
		intervalsBox.setPrototypeDisplayValue("XXXXXX");

		updatePanel.add(updateButton);
		updatePanel.add(new JLabel(" every "));
		updatePanel.add(intervalsBox);
		updatePanel.add(new JLabel(" minutes"));

		// Add components to bottom panel

		bottompanel.add(labelPanel);
		bottompanel.add(updatePanel);
		return bottompanel;

	}

	/**
	 * Constructs the search field for the view
	 * 
	 * @return the search field as a JTextField
	 */
	private JTextField buildSearchField() {
		Dimension searchDim = new Dimension(450, 30);
		JTextField searchField = new SearchTextField(searchFieldHint);

		searchField.getDocument().addDocumentListener(
				new SearchDocumentListener(rowSorter, searchField));
		searchField.setPreferredSize(searchDim);
		return searchField;
	}

	/**
	 * Sets the text of the status label to the bottom left
	 * 
	 * @param s
	 *            the string to be displayed
	 */
	public void setStatusLabel(String s) {
		statusLabel.setText(s);
		bottompanel.repaint();
	}

	/**
	 * @return the table model of the view
	 */
	public TravelTableModel getTableModel() {
		return tablemodel;
	}

	/**
	 * Adds an ActionListener to the "About" JMenuBarItem
	 * 
	 * @param listener
	 *            the added listener
	 */
	public void addAboutListener(ActionListener listener) {
		menubar.about.addActionListener(listener);
	}

	/**
	 * Adds an ActionListener to the "Save settings" JMenuBarItem
	 * 
	 * @param listener
	 *            the added listener
	 */
	public void addSaveListener(ActionListener listener) {
		menubar.saveSettings.addActionListener(listener);
	}

	/**
	 * Adds an ActionListener to the "Exit" JMenuBarItem
	 * 
	 * @param listener
	 *            the added listener
	 */
	public void addExitListener(ActionListener listener) {
		menubar.exit.addActionListener(listener);
	}

	/**
	 * Adds an ActionListener to the items which can update the table
	 * 
	 * @param listener
	 *            the added listener
	 */
	public void addUpdateButtonListener(ActionListener listener) {
		updateButton.addActionListener(listener);
		menubar.refresh.addActionListener(listener);
	}

	/**
	 * Adds a MouseListener to the table
	 */
	public void addTravelTableMouseListener(MouseListener listener) {
		table.addMouseListener(listener);
	}

	public void addIntervalChangeListener(ActionListener listener) {
		intervalsBox.addActionListener(listener);
	}

	/**
	 * A DocumentListener for the searchfield connected to the TableRowSorter of
	 * the table model
	 */
	private class SearchDocumentListener implements DocumentListener {

		private TableRowSorter<TableModel> rowSorter;
		private JTextField filterText;

		public SearchDocumentListener(TableRowSorter<TableModel> rowSorter,
				JTextField filterText) {
			super();
			this.rowSorter = rowSorter;
			this.filterText = filterText;
		}

		/**
		 * Change the filter text for the TableRowSorter every time the text in
		 * the texfield is changed
		 */
		@Override
		public void changedUpdate(DocumentEvent e) {
			newFilter(rowSorter, filterText);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			try {
				if (!(e.getDocument().getText(0, e.getLength())
						.equals(searchFieldHint))) {
					newFilter(rowSorter, filterText);
				}
			} catch (BadLocationException e1) {
				System.out.println(e1);
			}
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			newFilter(rowSorter, filterText);
		}

	}

	/**
	 * Creates a new filter for the TableRowSorter connected to the table model
	 * 
	 * @param rowSorter
	 *            the TableRowSorter to be modified
	 * @param filterText
	 *            the new filter text
	 */
	private void newFilter(TableRowSorter<TableModel> rowSorter,
			JTextField filterText) {
		RowFilter<TableModel, Object> rf = null;
		try {
			rf = RowFilter.regexFilter(filterText.getText(), 0);
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		this.rowSorter.setRowFilter(rf);
	}

}
